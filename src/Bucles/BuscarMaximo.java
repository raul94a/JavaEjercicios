package Bucles;

import java.util.Scanner;

public class BuscarMaximo {
    public static void main(String[] args) {
        // Introduce n números por pantalla y halla el promedio, max y min
        double suma = 0.0;
        int contador = 0;
        int max = 0;
        // MAX_VALUE es el mayor valor que puede tener un numero entero en JAVA
        int min = Integer.MAX_VALUE;
        boolean continuarBucle = true;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Introduce un número: " );
            int numero = scanner.nextInt();
            if(numero >= 0){
                // Incrementamos una unidad el contador
                contador++;
                // sumamos el número al total sumado
                suma += numero;
                // ¿Es el nuevo máximo?
                if(numero > max){
                    max = numero;
                }
                // ¿Es el nuevo mínimo?
                if(numero < min){
                    min = numero;
                }
            }
            // NUMERO NO POSITIVO => EL BUCLE DEBE ACABARSE
            else {
                continuarBucle = false;
            }
        }while (continuarBucle);
        double promedio = suma / contador;
        System.out.println("Promedio: " + promedio);
        System.out.println("Min: " + min);
        System.out.println("Max: " + max);
    }
}
