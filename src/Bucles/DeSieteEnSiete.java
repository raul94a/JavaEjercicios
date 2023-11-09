package Bucles;

import java.util.Scanner;

public class DeSieteEnSiete {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Introduce un número: ");
        int numeroUno = s.nextInt();
        int numeroDos;

        do {
            System.out.println("Introduce el siguiente número: ");
            numeroDos = s.nextInt();

        }
        while (numeroDos == numeroUno);

        int max = 0;
        int min = 0;
        if (numeroUno > numeroDos) {
            max = numeroUno;
            min = numeroDos;
        } else {
            max = numeroDos;
            min = numeroUno;
        }

        for (int i = min; i <= max; i += 7) {
            System.out.println(i);
        }


    }
}
