package at.aau.risiko.core;

import android.util.Log;
import android.view.View;

public class ObserveState extends State {

    public ObserveState(Game game) {
        super(game);
        Log.i("GAME STATE", "Transitioned into ObserveState.");
        //TODO Auto-generated constructor stub

        game.setProgress(0);
    }

    /**
     * The constructor must notify the Server to broadcast and
     * hand command to the next player.
     *
     * handleInput():
     * Do nothing.
     *
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
