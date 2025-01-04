package Cuestionario;

public class Nutriente {
    String name;
    double carbohydrates;
    double proteins;
    double fats;

    final int ENERGIA_GRAMO_GRASA  = 9;
    final int ENERGIA_GRAMO_HIDRATO = 4;
    final int ENERGIA_GRAMO_PROTEINA = 4;

    public Nutriente(String name, double carbohydrates, double proteins, double fats ){
        this.name = name;
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
        this.fats = fats;
    }

    public double getEnergia() {
        double total = 0.0;
        total += carbohydrates * ENERGIA_GRAMO_HIDRATO;
        total += proteins * ENERGIA_GRAMO_PROTEINA;
        total += fats * ENERGIA_GRAMO_GRASA;

        return total;
    }
}
