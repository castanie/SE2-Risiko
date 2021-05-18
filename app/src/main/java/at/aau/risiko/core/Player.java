package at.aau.risiko.core;

import android.graphics.Color;

public class Player {

    private String name;
    private Color color;
    private int armies;
    private Country[] occupied;
    private Card[] cards;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.armies = 0;
    }


    // Getters & Setters:


    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int getArmies() {
        return armies;
    }

}
