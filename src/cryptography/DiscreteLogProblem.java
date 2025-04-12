package cryptography;

import java.math.BigInteger;
import java.util.HashMap;

public class DiscreteLogProblem {
    public static void main(String[] args) {
        BigInteger p = new BigInteger("13407807929942597099574024998205846127479365820592393377723561443721764030073546976801874298166903427690031858186486050853753882811946569946433649006084171");
        BigInteger g = new BigInteger("11717829880366207009516117596335367088558084999998952205599979459063929499736583746670572176471460312928594829675428279466566527115212748467589894601965568");
        BigInteger h = new BigInteger( "3239475104050450443565264378728065788649097520952449527834792452971981976143292558073856937958553180532878928001494706097394108577585732452307673444020333" );
        long b = (long) Math.pow(2,20);
        HashMap<BigInteger,BigInteger> x1Map = new HashMap<>();
        for (long i = 0; i <= b; i++){
            // h / g^i => h * modInverse(modPow(g,i))
            BigInteger x1 = BigInteger.valueOf(i);
            BigInteger gx1 = g.modPow(x1,p);
            BigInteger gx1Inverse = gx1.modInverse(p);

            BigInteger result = h.multiply(gx1Inverse).mod(p);
            x1Map.put(result,x1);
            if(i % 100000 == 0)
                System.out.println("" + i + "/" + b);

        }
        System.out.println("Diccionario completado. Calculando x0...");
        BigInteger gb = g.modPow(BigInteger.valueOf(b),p);
        BigInteger computedX0 = null;
        BigInteger computedX1 = null;
        for (long i = 0; i <= b; i++){
            BigInteger x0  = BigInteger.valueOf(i);
            BigInteger gbx0 = gb.modPow(x0,p);

            if(x1Map.containsKey(gbx0)){
                computedX0 = x0;
                computedX1 = x1Map.get(gbx0);
                System.out.println("x0 found in iteration " + i);
                break;


            }

            if(i % 100000 == 0)
                System.out.println("" + i + "/" + b);
        }

        if(computedX0 != null){
            BigInteger result = computedX0.multiply(BigInteger.valueOf(b));
            result = result.add(computedX1);
            System.out.println("Result: " + result);
        }
        else{
            System.out.println("No solution found.");
        }
    }
}
