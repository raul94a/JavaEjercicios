package Bucles;

import java.util.Scanner;

public class SumarPrecios {
    public static void main(String[] args) {

        final float MULTIPLICADOR_IVA = 1.21f;
        double total = 0.0;
        boolean terminarPrograma = false;
        //Mientras terminarPrograma sea false, el bucle seguirá ejecutándose
        do {
            System.out.print("Introduce el precio del producto:");
            double precio = new Scanner(System.in).nextDouble();
            if(precio == 0){
                terminarPrograma = true;
            }
            if(precio > 0){
                total += precio;
            }
        }while(!terminarPrograma);

        System.out.println("Total sin IVA: " + total + " euros.");
        System.out.println("Total con IVA: " + (total * MULTIPLICADOR_IVA) + " euros.");

    }
}
