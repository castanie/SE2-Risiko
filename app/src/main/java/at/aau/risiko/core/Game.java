package at.aau.risiko.core;

import android.view.View;

public class Game {

    // Game is the local instance of the controller.

    State state;
    Player[] players;
    int index = 0;

    public Game(Player[] players) {
        this.state = null;
        this.players = players;
    }


    // Methods:
    
    public void handleInput(View view) {
        state.handleInput(view);
    }

    public void changeState() {
        state.changeState();
    }

}
