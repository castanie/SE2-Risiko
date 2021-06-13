package at.aau.risiko.controller;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import at.aau.core.Country;
import at.aau.core.Player;
import at.aau.risiko.DiceActivityAttacker;
import at.aau.server.dto.DiceMessage;

public class AttackState extends State {

    private Country attacking;
    private Country defending;

    public AttackState(Game game) {
        super(game);
        Log.i("GAME STATE", "Transitioned into AttackState.");

        attacking = null;
        defending = null;

        game.setProgress(2);
        game.setInfo("Attack");
    }

    /**
     * handleInput():
     * Select attacking country.
     * Select defending country.
     * Send message to server if selection is valid, otherwise reset.
     * [Roll dice ...]
     * Change state if player presses skip button.
     * <p>
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

                defending = clicked;

                if (attacking.getArmies() > 1) {

                    // Find index of the defending Player:
                    int defendingIndex = 0;
                    for (Player p : game.getPlayers()) {
                        if (p.getOccupied().contains(defending)) {
                            break;
                        }
                        ++defendingIndex;
                    }

                    game.sendMessage(new DiceMessage(defendingIndex));
                    game.getContext().startActivity(new Intent(game.getContext(), DiceActivityAttacker.class));
                } else {
                    game.showSnackbar("Not enough armies to attack a country!");
                    attacking = null;
                    defending = null;
                    changeState();
                }
            } else {
                game.showSnackbar("You can only attack neighbouring countries!");
                attacking = null;
                defending = null;
            }
        } else {
            attacking = null;
            defending = null;
        }
    }

    @Override
    public void changeState() {
        game.setState(new FortifyState(game));
    }

}
