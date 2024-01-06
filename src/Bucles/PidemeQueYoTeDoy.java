package Bucles;

import java.util.Arrays;
import java.util.Scanner;

public class PidemeQueYoTeDoy {

    public static void main(String[] args) {
        String cadena = "";
        String cadenaFinal = "";
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("Dime algo: ");

            cadena = sc.nextLine();
            System.out.println();
            System.out.println(cadena);
            cadenaFinal += cadena + " ";
        }while (!cadena.isEmpty());
    }


}

