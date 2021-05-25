package at.aau.risiko.core;

import android.util.Log;
import android.view.View;

import at.aau.risiko.networking.dto.TextMessage;

public class AttackState extends State {

    private Country attacking;
    private Country defending;

    public AttackState(Game game) {
        super(game);
        Log.i("GAME STATE", "Transitioned into AttackState.");
        //TODO Auto-generated constructor stub
        attacking = null;
        defending = null;

        game.setProgress(2);
    }

    /**
     * handleInput():
     * Select attacking country.
     * Select defending country.
     * Send message to server if selection is valid, otherwise reset.
     * [Roll dice ...]
     * Change state if player presses skip button.
     *
     * changeState():
     * Transition to FortifyState.
     */

    // Methods:
    @Override
    public void handleInput(View view) {
        // TODO Auto-generated method stub
        Country clicked = game.buttonMap.get(view.getId());

        if (attacking == null) {
            attacking = clicked;
        } else if (defending == null) {
            if (clicked.getNeighbors().contains(attacking)) {
                // TODO: SEND MESSAGE TO SERVER AND START DICE STATE
                defending = clicked;
                game.sendMessage(new TextMessage("It's " + attacking.getName() + " against " + defending.getName()));
                changeState();
            } else {
                game.showToast("You can only attack neighbouring countries!");
            }
        } else {
            attacking = null;
            defending = null;
        }

    }

    @Override
    public void changeState() {
        // TODO Auto-generated method stub
        game.setState(new FortifyState(game));
        // game.sendMessage(null);
    }

}
