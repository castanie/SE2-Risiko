package at.aau.core;

import java.util.Random;

public class Dice {
    /**
     * int eyeNumber is needed to know which side of the dice should be shown
     * String type = attacker || defender
     * this variable is needed to know on what color the dice should have
     */
    private int eyeNumber;
    private String type;
    private Random rand = new Random();

    public Dice(String type) {
        this.type = type;
    }

    public int diceRoll() {
        eyeNumber = rand.nextInt(6) + 1;
        return eyeNumber;
    }

    public int getEyeNumber() {
        return eyeNumber;
    }

    public void setEyeNumber(int eyeNumber) {
        this.eyeNumber = eyeNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
