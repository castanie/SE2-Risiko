package at.aau.risiko.controller;

import android.view.View;

public abstract class State {

    Game game;

    public State() {
        this.game = Game.getInstance();
    }


    // Methods:

    public abstract void handleInput(View view);

    public abstract void changeState();

}
