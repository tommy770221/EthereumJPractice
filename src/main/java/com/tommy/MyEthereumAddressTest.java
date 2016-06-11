package com.tommy; /**
 * Created by Tommy on 2016/6/2.
 */
import org.ethereum.config.SystemProperties;
import org.ethereum.core.Account;
import org.ethereum.core.Block;
import org.ethereum.core.TransactionReceipt;
import org.ethereum.crypto.ECKey;
import org.ethereum.facade.Ethereum;
import org.ethereum.facade.EthereumFactory;
import org.ethereum.facade.Repository;
import org.ethereum.listener.EthereumListenerAdapter;
import org.ethereum.samples.FollowAccount;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.util.encoders.Hex;
import sun.security.ec.ECPrivateKeyImpl;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.Provider;
import java.util.List;


public class MyEthereumAddressTest {
      public static void main(String[] args) throws InvalidKeyException {
          //Create Account
          Account account=new Account();
          account.init();
          System.out.println(" Address    : "+new String(Hex.encode(account.getAddress())));

          //account.getEcKey().getPubKey()     A compress key
          System.out.println("public key :   "+account.getEcKey().getPubKey());
          //account.getEcKey().getPubKey()     No  compress key
          System.out.println("public key :   "+account.getEcKey().getPubKeyPoint());
          //ECDSA       Derive the public key from this private key (128 characters / 64 bytes)
          System.out.println("public key  :   "+new String(Hex.encode(account.getEcKey().getPubKeyPoint().getEncoded(false))));

          // EC  CURVE  ECPrivateKeyParameters  BigInteger => toString method
          System.out.println("priv key    :   "+account.getEcKey().getPrivKey());
          // A  random private key (64 characters / 32 bytes)   With Hex.encode =>64characters
          System.out.println("priv key    :   "+new String(Hex.encode(account.getEcKey().getPrivKey().toByteArray())));
          //  Pub + Private  Key    Hex.decode String
          System.out.println("pub+priv key    :   "+account.getEcKey().toStringWithPrivate());



     }

}
