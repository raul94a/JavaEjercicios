package cryptography;

import java.security.*;
import java.util.HexFormat;

public class DigitalSignatureExample
{
    public byte[] generateDigitalSignature(byte[] input, PrivateKey privateKey) throws Exception {
        Signature signature_sha256_rsa = Signature.getInstance("SHA256withRSA");
        signature_sha256_rsa.initSign(privateKey);
        signature_sha256_rsa.update(input);
        return signature_sha256_rsa.sign();
    }

    public KeyPair keyPairGeneratorRSA(int size) throws Exception {
        SecureRandom random = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(size,random);
        return keyPairGenerator.generateKeyPair();
    }

    public boolean checkDigitalSignature(String message, byte[] verifyingSignature, PublicKey publicKey)  {
        try{


            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(message.getBytes());
            return signature.verify(verifyingSignature);
        } catch (Exception e) {
            return false;
        }
    }

    static  String bytesToHex(byte[] bytes){
        return HexFormat.of().formatHex(bytes);
    }

    public static void main(String[] args) throws Exception  {
        var digitalSignature = new DigitalSignatureExample();

        var message = "MESSAGE TO BE SIGNED";
        var kp512 = digitalSignature.keyPairGeneratorRSA(512);
        var kp1024 = digitalSignature.keyPairGeneratorRSA(512 * 2);
        var kp2048 = digitalSignature.keyPairGeneratorRSA(512 * 4);

        var signature512  = digitalSignature.generateDigitalSignature(message.getBytes(), kp512.getPrivate());
        var signature1024  = digitalSignature.generateDigitalSignature(message.getBytes(), kp1024.getPrivate());
        var signature2048  = digitalSignature.generateDigitalSignature(message.getBytes(), kp2048.getPrivate());

        System.out.println("The message for which the signature will be computed is => " + message);

        System.out.println("The length of the messager is " + message.length());

        System.out.println();
        System.out.println("The 512 signature is -> " + bytesToHex(signature512));
        System.out.println("Status of verification -> " + digitalSignature.checkDigitalSignature(message, signature512, kp512.getPublic() ));

        System.out.println();
        System.out.println("The 1024 signature is -> " + bytesToHex(signature1024));
        System.out.println("Status of verification -> " + digitalSignature.checkDigitalSignature(message, signature1024, kp1024.getPublic()));

        System.out.println();
        System.out.println("The 2048 signature is -> " + bytesToHex(signature2048));
        System.out.println("Status of verification -> " + digitalSignature.checkDigitalSignature(message, signature2048, kp2048.getPublic()));



    }
}
