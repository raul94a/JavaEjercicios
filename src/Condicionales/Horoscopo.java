package Condicionales;

import java.util.Scanner;

public class Horoscopo {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int puntos = 0;

        System.out.print("En que mes estamos: ");
        String respuesta = scanner.nextLine().toLowerCase();

        if (respuesta.equals("OCTUBRE".toLowerCase())) {
            puntos++;
        }

        System.out.print("Pueblo: ");
        respuesta = scanner.nextLine().toLowerCase();

        if (respuesta.equals("MARTOS".toLowerCase())) {
            puntos++;
        }

        System.out.print("Asignatura: ");
        respuesta = scanner.nextLine().toLowerCase();

        if (respuesta.equals("PROGRAMACION".toLowerCase())) {
            puntos++;
        }

        System.out.println("Fin del cuestionario. Tienes " + puntos + " puntos.");

    }
}
