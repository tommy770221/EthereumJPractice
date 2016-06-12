package com.tommy;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

/**
 * Created by Tommy on 2016/6/5.
 */
public class ECDSATest {

    public static void main(String[] args) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");

        keyGen.initialize(ecSpec, new SecureRandom());

        KeyPair keyPair = keyGen.generateKeyPair();
        Signature signature = Signature.getInstance("ECDSA", "BC");
        signature.initSign(keyPair.getPrivate(), new SecureRandom());

        byte[] message = "abc".getBytes();
        signature.update(message);

        byte[] sigBytes = signature.sign();
        signature.initVerify(keyPair.getPublic());
        signature.update(message);
        System.out.println(signature.verify(sigBytes));
    }
}
