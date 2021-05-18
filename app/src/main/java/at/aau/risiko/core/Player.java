package at.aau.risiko.core;

import android.graphics.Color;

public class Player {

    String name;
    Color color;
    int armies;
    Country[] occupied;
    Card[] cards;

    public Player(String name) {
        this.name = name;
        this.armies = 0;
    }

}
