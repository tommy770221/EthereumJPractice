package com.tommy;

import org.ethereum.core.Account;
import org.ethereum.crypto.ECKey;
import org.spongycastle.math.ec.custom.sec.SecP256K1Curve;
import org.spongycastle.math.ec.custom.sec.SecP256K1FieldElement;
import org.spongycastle.math.ec.custom.sec.SecP256K1Point;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.security.InvalidKeyException;

/**
 * Created by Tommy on 2016/6/11.
 */
public class MyEthereumKeyTest {
    public static void main(String[] args) throws InvalidKeyException {
        //derive private key and public key to Address
        String privateKey="00a50b2d932b3096a7c1eb9be2f7474e109378104d04cf1c7392533fe73415f5f4";
        String publickeyX="db339a69c85252a72882ab2dd0e78c83bbddab07b233b45a1d3159dec1c7c3a1";
        String publickeyY="216aade07ad4b24c153ca2e99b138b98973109c8de46e363e147d5d7eb043ec0";
        System.out.println("priv key    :   "+(new BigInteger(1, Hex.decode(privateKey)).toString()));
        System.out.println("priv key    :   74651369880197698645911742325133806883668285281733972166339115822058178999796");
        SecP256K1Curve ecCurve=new SecP256K1Curve();
        SecP256K1FieldElement elementX=new SecP256K1FieldElement(new BigInteger(1, Hex.decode(publickeyX)));
        SecP256K1FieldElement elementY=new SecP256K1FieldElement(new BigInteger(1, Hex.decode(publickeyY)));
        SecP256K1Point ecPoint=new SecP256K1Point(ecCurve,elementX,elementY);
        ECKey ecKey=new ECKey(new BigInteger(1, Hex.decode(privateKey)),ecPoint);
        Account accountTwo=new Account();
        accountTwo.init(ecKey);
        System.out.println("Address : "+new String(Hex.encode(accountTwo.getAddress())));
        System.out.println("Address : 49282ceac5b6a764b06247fe0efc86911a3c1ada");
        ECKey ecKeySecond=ECKey.fromPrivate(new BigInteger(1, Hex.decode(privateKey)));
        Account accountThree=new Account();
        accountThree.init(ecKeySecond);
        System.out.println("Address Three        : "+new String(Hex.encode(accountThree.getAddress())));
        System.out.println("pub+priv key Three   : "+accountThree.getEcKey().toStringWithPrivate());

    }
}
