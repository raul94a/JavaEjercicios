package cryptography;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Scanner;

public class AesAlgorithm {

    private static final byte[] INITIALIZATION_VECTOR = {88, 1, 127, -50, 55, 46, 78, 79, 88, 1, 127, -50, 55, -46, -78, 80};
    private static final String SALT = "MY_SALT__________________";
    private static final String PRIVATE_KEY = "MY_PRIVATE_KEY_____________";

    public static String encrypt(String message) {
        try {
            Cipher cipher = initCipher(Cipher.ENCRYPT_MODE);
            return Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("Encryption error", e);
        }
    }

    public static String decrypt(String encryptedMessage) {
        try {
            Cipher cipher = initCipher(Cipher.DECRYPT_MODE);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedMessage)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Encryption error", e);
        }
    }

    private static Cipher initCipher(int mode) throws Exception {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(INITIALIZATION_VECTOR);

        //the container for the secret key
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        //specification parameters (secret key, salt value, iterations, key length)
        KeySpec keySpec = new PBEKeySpec(PRIVATE_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
        //generate the secret key based on the parameters set above
        SecretKey tmp = keyFactory.generateSecret(keySpec);

        //align the secret key with the AES algorithm
        SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");

        //set the algorithm (e.g., AES) and its mode together with its padding type
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(mode, secretKeySpec, ivParameterSpec);

        return cipher;
    }

    public static void main(String[] args) {
        //set the message that we want to encrypt
        System.out.print("Type the message to be encrypted: ");
        var sc = new Scanner(System.in);
        String originalValue = sc.nextLine();

        //perform the encryption
        String encryptedValue = encrypt(originalValue);

        //perform the decryption
        String decryptedValue = decrypt(encryptedValue);

        //show some messages
        System.out.println("Plaintext used for encryption and decryption -> " + originalValue);
        System.out.println("The encryption is -> " + encryptedValue);
        System.out.println("The decryption is -> " + decryptedValue);
    }
}
