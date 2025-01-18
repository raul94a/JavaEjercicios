package cryptography;

import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Scanner;

public class HashFunction {

    public static void main(String[] args) {
        System.out.print("Escribe una cadena de texto: ");
        try(var scanner = new Scanner(System.in)){
            var input = scanner.next();
            System.out.println();
            var outputSha = MessageDigest.getInstance("SHA-512");
            outputSha.update(input.getBytes());
            byte [] digest = outputSha.digest();
            System.out.println("Digest: " + digest.toString());
            String hexOutput = HexFormat.of().formatHex(digest);
            System.out.println("Hex: " + hexOutput);

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
