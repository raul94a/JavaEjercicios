package Condicionales;

import java.sql.SQLOutput;

public class EcuacionSegundoGrado {
    public static void main(String[] args) {
        // A*x ^2 + B*x + C = 0
        int a = 5;
        int b = -1;
        int c = 0;

        // Es posible que la ecuación de segundo grado sea de la siguiente forma
        // 0*x^2 + B*x + C= 0
        // En este caso la ecuación quedaría:
        // B*x + C = 0
        // y la aplicación de la ecuación de segundo grado no podría realizarse tan facilmente
        // deberiamos aplicar la siguiente fórmula
        // x = -c / b

        // Otro caso: si C = 0 entonces siempre una de las soluciones será
        // x = 0, ya que a*x^2 + b*x = 0 => x(a*x + b) = 0

        if (a == 0) {
            //Ojo! Si la B vale 0, pero la C no es 0 el probelma NO TIENE SOLUCIÓN ya que tendríamos este caso:
            // C = 0... y si C es distinto que 0 entonces, POR ejemplo, 4 = 0 lo que NO ES POSIBLE
            if (b == 0) {
                System.out.println("El problema no tiene solución");
            } else {

                double resultado = -c / b;
                System.out.println("La solución es " + resultado);
            }
        } else {
            double raizCuadrada = Math.sqrt((b * b) - (4 * a * c));

            double resultadoUno = (-b + raizCuadrada) / (2 * a);
            double resultadoDos = (-b - raizCuadrada) / (2 * a);
            System.out.println("Las soluciones son \nx1 = " + resultadoUno + "\nx2 = " + resultadoDos);
        }
    }
}
