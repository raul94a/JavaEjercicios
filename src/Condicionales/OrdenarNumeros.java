package Condicionales;

import java.util.Random;

public class OrdenarNumeros {

    public static void main(String[] args) {
        int a = (int) (Math.random() * 10) + 1;
        int b = (int) (Math.random() * 10) + 1;
        int c = (int) (Math.random() * 10) + 1;
        int suma = a + b + c;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);

        int max = a;
        if(b > a){
            max = b;
            if(c > b){
                max = c;
            }

        }
        else  {
            if(c > a){
                max = c;
            }
        }


        int min = a;
        if(b < a){
            min = b;
            if( c < b){
                min = c;
            }
        }
        else  {
            if(c < a){
                min = c;
            }
        }
        System.out.println("res");
        System.out.println(max);
        System.out.println(suma - max - min);
        System.out.println(min);


    }
}
