package at.aau.core;

import java.util.LinkedList;
import java.util.List;

public class Player {

    private final String name;
    private final Integer color;
    private List<Country> occupied;
    private int available;
    private HandDeck handDeck;

    //Constructor

    public Player(String name, Integer color) {
        this.name = name;
        this.color = color;
        this.available = 20;
        this.occupied = new LinkedList<>();
        this.handDeck = new HandDeck();
    }

    // Getters & Setters:
    // TODO: add methods to add and remove occupied countries!

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public List<Country> getOccupied() {
        return occupied;
    }

    public HandDeck getHandDeck() {
        return handDeck;
    }
}
