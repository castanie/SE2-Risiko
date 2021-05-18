package at.aau.risiko.core;

public class Continent {

    String name;
    int countries;
    int bonus;

    public Continent(String name) {
        this.name = name;
    }


    // Getters & Setters:

    public String getName() {
        return name;
    }

    public int getCountries() {
        return countries;
    }

    public int getBonus() {
        return bonus;
    }

}
