package cryptography;

public class PolinomialModularEq {


    public static void main(String[] args) {
        int prime = 11;
        int target = 1;
        int exponent = 2;
        for (int i = 0; i < prime; i++){

            if(target == computeModule(i,exponent,prime)){

                System.out.println("The solution is " + i);
                return;

            }

        }

        System.out.println("There's no solution for target " + target);




    }



    public static int computeModule(int n, int exponent, int modulo){
        return (int) Math.pow(n,exponent) % modulo;
    }
}
