package cryptography;

import java.security.SecureRandom;
import java.util.Base64;

/**
 *
 * FIPS 140-2 and RFC 1740
 */
public class SecureRandomGeneratorExample {
    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();

        System.out.println("Random integer: " + secureRandom.nextInt(1000));
        System.out.println("Random integer: " + secureRandom.nextInt());
        System.out.println("Random long integer: " + secureRandom.nextLong(100000000L));
        System.out.println("Random long integer: " + secureRandom.nextLong());
        System.out.println("Random boolean: " + secureRandom.nextBoolean());
        System.out.println("Random double: " + secureRandom.nextDouble());
        System.out.println("Random float: " + secureRandom.nextFloat());
        System.out.println("Random int gaussian: " + secureRandom.nextGaussian());

        byte[] byteArray = new byte[15];
        secureRandom.nextBytes(byteArray);
        System.out.println("Sequence of bytes ");
        System.out.print("[");
        for (Byte b: byteArray){
            System.out.print(b + " ");
        }
        System.out.print("]");

        System.out.println();

        System.out.println("Session ID: " + getSessionId());


    }

    static String getSessionId(){
      var secureRandom = new SecureRandom();
      byte[] sessionBytes = new byte[24];
      secureRandom.nextBytes(sessionBytes);
      return Base64.getUrlEncoder().withoutPadding().encodeToString(sessionBytes);
    }
}
