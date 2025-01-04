package Cuestionario;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Nutriente platano = new Nutriente("platano", 50.2,4.5,0.85);
        Nutriente lentejas = new Nutriente("lentejas", 50.2,4.5,0.85);
        Nutriente tomate = new Nutriente("tomate", 50.2,4.5,0.85);
        Nutriente manzana = new Nutriente("manzana", 50.2,4.5,0.85);
        Nutriente melocoton = new Nutriente("melocoton", 50.2,4.5,0.85);



//        Nutriente[] nutrientes = new Nutriente[5];
//
//        nutrientes[0] = platano;
//        nutrientes[1] = lentejas;
//        nutrientes[2] = tomate;
//        nutrientes[3] = manzana;
//        nutrientes[4] = melocoton;
//        double total = 0.0;
//        for(Nutriente n : nutrientes){
//            total += n.getEnergia();
//        }
//        System.out.println(total);

        ArrayList<Nutriente> nutrientes = new ArrayList<>() {{
            add(platano);
            add(lentejas);
            add(tomate);
            add(manzana);
            add(melocoton);
        }};

        double total = 0.0;
        for(Nutriente n : nutrientes){
            total += n.getEnergia();
        }
        System.out.println(total);

    }
}
