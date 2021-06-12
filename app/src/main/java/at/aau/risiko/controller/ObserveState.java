package at.aau.risiko.controller;

import android.util.Log;
import android.view.View;

public class ObserveState extends State {

    public ObserveState() {
        super();
        Log.i("GAME STATE", "Transitioned into ObserveState.");

        game.setProgress(0);
        game.setInfo("Waiting...");
    }

    /**
     * The constructor must notify the Server to broadcast and
     * hand command to the next player.
     * <p>
     * handleInput():
     * Do nothing.
     * <p>
     * changeState():
     * Do nothing.
     */

    // Methods:
    @Override
    public void handleInput(View view) {

    }

    @Override
    public void changeState() {

    }

}
