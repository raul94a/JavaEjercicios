package cryptography;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class FileIntegrityChecker {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        String filePath = "src/cryptography/CaesarCipher.java";
        var hash = getHashFromFile(filePath);

        System.out.println(hash);



    }


    public static String getHashFromFile(String filePath) throws IOException, NoSuchAlgorithmException {
        var file = new File(filePath);
        var reader = new FileInputStream(file);

        var bytes = reader.readAllBytes();

        reader.close();
        var digest = MessageDigest.getInstance("SHA-256");
        digest.update(bytes);

        return HexFormat.of().formatHex(digest.digest());
    }
}
