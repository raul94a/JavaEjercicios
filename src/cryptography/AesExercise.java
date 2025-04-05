package cryptography;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HexFormat;


public class AesExercise {

    static final String AES_CBC = "AES/CBC/PKCS5Padding";
    static final String AES_CTR = "AES/CTR/NoPadding";
    static final String ENCRYPTION_ALGORITHM = "AES";

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String ciphertext = "4ca00ff4c898d61e1edbf1800618fb2828a226d160dad07883d04e008a7897ee2e4b7465d5290d0c0e6c6822236e1daafb94ffe0c5da05d9476be028ad7c1d81";
        final String CBC_KEY = "140b41b22a29beb4061bda66b6747e14";
        final String CTR_KEY = "36f18357be4dbd77f050515c73fcf9f2";
        decryptWithAes(ciphertext,CBC_KEY, AES_CBC);

        // Exercise 2
        ciphertext = "5b68629feb8606f9a6667670b75b38a5b4832d0f26e1ab7da33249de7d4afc48e713ac646ace36e872ad5fb8a512428a6e21364b0c374df45503473c5242a253";
        decryptWithAes(ciphertext,CBC_KEY, AES_CBC);

        // Exercise 3
        ciphertext = "69dda8455c7dd4254bf353b773304eec0ec7702330098ce7f7520d1cbbb20fc388d1b0adb5054dbd7370849dbf0b88d393f252e764f1f5f7ad97ef79d59ce29f5f51eeca32eabedd9afa9329";
        decryptWithAes(ciphertext,CTR_KEY,AES_CTR);
        // Exercise 4
        ciphertext = "770b80259ec33beb2561358a9f2dc617e46218c0a53cbeca695ae45faa8952aa0e311bde9d4e01726d3184c34451";
        decryptWithAes(ciphertext,CTR_KEY, AES_CTR);



    }


    /**
     * @param hexKey is supposed to be a key in Hex format
     *
     * */
    private static void decryptWithAes(String ciphertext, String hexKey, String mode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        HexFormat hf = HexFormat.of();

        byte[] byteKey = hf.parseHex(hexKey);
        byte[] fullHexDecoded = hf.parseHex(ciphertext);

        byte[] INITIALIZATION_VECTOR = Arrays.copyOfRange(fullHexDecoded,0,16);

        byte[] ciphertextBytes = Arrays.copyOfRange(fullHexDecoded,16,fullHexDecoded.length);


        Cipher cipher = Cipher.getInstance(mode);
        Key key = new SecretKeySpec(byteKey, ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(INITIALIZATION_VECTOR));

        System.out.println(new String(cipher.doFinal(ciphertextBytes)));
    }



}
