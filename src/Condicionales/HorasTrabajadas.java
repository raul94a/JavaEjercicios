package Condicionales;

import java.util.Scanner;

public class HorasTrabajadas {
    static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // el final indica que la varible no puede ser cambiada de valor, es una constante
        final int MAX_HORAS_SEMANALES = 40;
        final int SALARIO_HORA_NORMAL = 12;
        final int SALARIO_HORA_EXTRA = 16;

        System.out.print("¿Cuantas horas ha trabajado la persona esta semana? ");
        double horasTotales = scanner.nextDouble();

        System.out.println("\n¡Bien! Esta persona ha trabajado " + horasTotales + " horas. Vamos a hacer unos cálculos." );

        // simplemente, saber si ha trabajado más de 40 horas esta semana
        boolean excedeJornadaSemanal = horasTotales > MAX_HORAS_SEMANALES;

        if(excedeJornadaSemanal){
            System.out.println("Ha excedido el máximo de la jornada semanal. Calculando salario CON horas extras");
            double horasExtras = horasTotales - MAX_HORAS_SEMANALES;
            var total  = SALARIO_HORA_NORMAL * MAX_HORAS_SEMANALES + (SALARIO_HORA_EXTRA * horasExtras);
            System.out.println("El salario es de un total de: " + total + " euros.");

        }

        else {
            System.out.println("No ha excedido el máximo de la jornada semanal. Calculando salario sin horas extras");
            var total = SALARIO_HORA_NORMAL * horasTotales;
            System.out.println("El salario es de un total de: " + total + " euros.");
        }




    }
}
