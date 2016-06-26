package com.tommy;

import org.ethereum.core.BlockchainImpl;
import org.ethereum.core.Repository;
import org.ethereum.core.Transaction;
import org.ethereum.core.TransactionExecutor;
import org.ethereum.crypto.ECKey;
import org.ethereum.crypto.HashUtil;
import org.ethereum.db.RepositoryImpl;
import org.ethereum.facade.EthereumFactory;
import org.spongycastle.util.BigIntegers;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.security.SignatureException;

/**
 * Created by Tommy on 2016/6/20.
 */
public class MySendTransactiontest {

    public static void main(String[] args) throws SignatureException {
        BigInteger value = new BigInteger("1000000000000000000000");
        byte[] privKey = HashUtil.sha3("cat".getBytes());
        ECKey ecKey = ECKey.fromPrivate(privKey);
        byte[] senderPrivKey = HashUtil.sha3("cow".getBytes());
        byte[] gasPrice = Hex.decode("09184e72a000");
        byte[] gas = Hex.decode("4255");
        System.out.println("gasPrice :"+new BigInteger(1,gasPrice).intValue());
        System.out.println("gas      :"+new BigInteger(1,gas).intValue());
        // Tn (nonce); Tp(pgas); Tg(gaslimi); Tt(value); Tv(value); Ti(sender);  Tw; Tr; Ts
        Transaction tx = new Transaction(null, gasPrice, gas, ecKey.getAddress(),
                value.toByteArray(),
                null);
        tx.sign(senderPrivKey);
        System.out.println("v\t\t\t: " + Hex.toHexString(new byte[]{tx.getSignature().v}));
        System.out.println("r\t\t\t: " + Hex.toHexString(BigIntegers.asUnsignedByteArray(tx.getSignature().r)));
        System.out.println("s\t\t\t: " + Hex.toHexString(BigIntegers.asUnsignedByteArray(tx.getSignature().s)));
        System.out.println("RLP encoded tx\t\t: " + Hex.toHexString(tx.getEncoded()));
        // retrieve the signer/sender of the transaction
        ECKey key = ECKey.signatureToKey(tx.getHash(), tx.getSignature().toBase64());
        System.out.println("Tx unsigned RLP\t\t: " + Hex.toHexString(tx.getEncodedRaw()));
        System.out.println("Tx signed   RLP\t\t: " + Hex.toHexString(tx.getEncoded()));
        System.out.println("Signature public key\t: " + Hex.toHexString(key.getPubKey()));
        System.out.println("Sender is\t\t: " + Hex.toHexString(key.getAddress()));
        // assertEquals("cd2a3d9f938e13cd947ec05abc7fe734df8dd826",
        //         Hex.toHexString(key.getAddress()));
        System.out.println(tx.toString());
        BlockchainImpl blockchain=new BlockchainImpl();
        Repository repository=new RepositoryImpl();
        blockchain.setRepository(repository);
        TransactionExecutor txExcutor=MySendTransactiontest.executeTransaction(blockchain,tx);
        System.out.println("Result+"+txExcutor.getResult());

    }
    public static TransactionExecutor executeTransaction(BlockchainImpl blockchain, Transaction tx) {

        Repository track = blockchain.getRepository().startTracking();
        TransactionExecutor executor = new TransactionExecutor(tx, new byte[32], blockchain.getRepository(),
                blockchain.getBlockStore(), blockchain.getProgramInvokeFactory(), blockchain.getBestBlock());

        executor.init();
        executor.execute();
        executor.go();
        executor.finalization();

        track.commit();
        return executor;
    }
}
