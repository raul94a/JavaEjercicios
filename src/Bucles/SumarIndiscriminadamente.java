package Bucles;

import java.util.Scanner;

public class SumarIndiscriminadamente {
//    Escribe un programa que permita ir introduciendo una serie indeterminada de números
//    mientras su suma no supere el valor 10000. Cuando esto último ocurra, se debe mostrar el
//    total acumulado, el contador de los números introducidos y la media.
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        int total  = 0;
        int contador = 0;

        do {
            System.out.print("Introduce un número: ");
            int numero = scanner.nextInt();
            total += numero;
            contador++;
        }while (total < 10000);

        double media = (double) total / contador;
        System.out.println("Cantidad de numeros introducidos: " + contador);
        System.out.println("Suma: " + total );
        System.out.println("Media: " + media);


    }
}
