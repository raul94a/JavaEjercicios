package cryptography;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;


/**
 * KEY GENERATION ALGORITHM:
 * <p>
 * 1. Pick a prime number Q and a Cyclic Group G.
 * 2. Take an int from x ∈ {1, …, q − 1}.
 * 3. Compute h = g^x.
 * <p>
 * The public Key is Pk = (G,q,g,h), whilst the private key is Ks = x
 * <p>
 * ENCRYPTION ALGORITHM:
 * <p>
 * 1. Pick a random y ∈ {1, …q − 1}.
 * 2. Compute s=h^y
 * 3. Compute c1 = g^y
 * 4. Compute c2 = m * s
 * <p>
 * The encryption is the pair c = (c1, c2)
 * <p>
 * DECRYPTION ALGORITHM
 * <p>
 * 1. Compute s=c1^x
 * 2. Compute s^-1
 * 3. Compute m = c2 * s^-1
 * <p>
 * This method can be vulnerable because if the pair is known by an attacker,
 * the message m can be decrypted. So is wise to choose new values for y periodically.
 */
public class ElGamalAlgorithm {

    private BigInteger q, privateKey, g, h;
    private byte[] c1, c2;
    private final int maxLength = 1024;
    private SecureRandom random;

    private void KeyGeneration() {
        random = new SecureRandom();
        q = BigInteger.probablePrime(maxLength, random);
        do {
            privateKey = new BigInteger(maxLength, random);
        } while (privateKey.compareTo(q.subtract(BigInteger.ONE)) >= 0);

        var p2 = q.subtract(BigInteger.ONE).divide(BigInteger.TWO);

        do {
            g = new BigInteger(maxLength, random).mod(q);
        } while (g.modPow(p2, q).compareTo(BigInteger.ONE) == 0);


        h = g.modPow(privateKey, q);


    }

    public void encryption(String message) {
        var byteMesage = message.getBytes(StandardCharsets.UTF_8);
        BigInteger y, s;
        do {
            y = new BigInteger(maxLength, random);
        } while (y.compareTo(q) >= 0);

        s = h.modPow(y, q);
        c1 = g.modPow(y, q).toByteArray();
        c2 = new BigInteger(byteMesage).multiply(s).mod(q).toByteArray();
        System.out.println("Encrypted message [bytes] " + byteToString(c1) + byteToString(c2));
        System.out.println("Encrypted message [text]: " + new String(c1, StandardCharsets.UTF_8) + new String(c2, StandardCharsets.UTF_8));

    }

    public void decryption() {

        BigInteger s = new BigInteger(c1).modPow(privateKey, q);
        BigInteger inverseOfS = s.modInverse(q);
        BigInteger m = inverseOfS.multiply(new BigInteger(c2)).mod(q);
        System.out.println("Decrypted Message [bytes]" + byteToString(m.toByteArray()));
        System.out.println("Decrypted message [text]: " + new String(m.toByteArray(), StandardCharsets.UTF_8));


    }

    public static void main(String[] args) {
        var elGamalAlgorithm = new ElGamalAlgorithm();
        elGamalAlgorithm.KeyGeneration();
        try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Type the message: ");
            var message = reader.readLine();
            System.out.println("ENCRYPTING!!");
            elGamalAlgorithm.encryption(message);
            elGamalAlgorithm.decryption();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String byteToString(byte[] byteArray) {
        StringBuilder recoveredString = new StringBuilder();
        for (byte byteVal : byteArray) {
            recoveredString.append(byteVal);
        }
        return recoveredString.toString();
    }
}
