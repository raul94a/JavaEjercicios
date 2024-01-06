package Bucles;

import java.util.Arrays;
import java.util.Scanner;

public class PidemeQueYoTeDoy {

    public static void main(String[] args) {
        String cadena = "";
        StringBuilder cadenaFinal = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("Dime algo: ");

            cadena = sc.nextLine();
            System.out.println();
            System.out.println(cadena);
            cadenaFinal.append(cadena).append(" ");
        }while (!cadena.isEmpty());

        System.out.println(cadenaFinal);
    }

}

