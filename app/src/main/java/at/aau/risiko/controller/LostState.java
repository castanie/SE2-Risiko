package at.aau.risiko.controller;

import android.util.Log;
import android.view.View;

public class LostState extends State {

    public LostState(Game game) {
        super(game);
        Log.i("GAME STATE", "Transitioned into LostState.");

        game.setProgress(0);
        game.setInfo("\uD83C\uDFF3");
    }


    // Methods:

    @Override
    public void handleInput(View view) {

    }

    @Override
    public void changeState() {

    }
}
