package at.aau.risiko.controller;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import at.aau.core.Country;
import at.aau.core.Player;
import at.aau.server.dto.UpdateMessage;

public class DraftState extends State {


    int availableStrength;
    Country clicked;
    Player player = game.getCurrentPlayer();

    /* The constructor must calculate the armies available to
     the player.*/

    public DraftState() {
        super();
        Log.i("GAME STATE", "Transitioned into DraftState.");

        if (player.getAvailable() > 0) {
            this.availableStrength = player.getAvailable() + calculateStrength();
        } else {
            this.availableStrength = calculateStrength();
        }

        game.setProgress(1);
        game.setCard("Strengthen");
    }

    private int calculateStrength() {
        int occupiedCountries = player.getOccupied().size();
        int strength = occupiedCountries / 3;
        if (occupiedCountries == 0) {
            // game.showToast("You have lost the game!");
            //change State to lost State
        } else {

            if (strength < 3) {
                strength = 3;
            }
        }
        return strength;
    }

    /**
     * handleInput():
     * Deploy armies in country if owned by player.
     * Change state if all armies have been used.
     * changeState():
     * Transition to AttackState.
     */

    @Override
    public void handleInput(View view) {

        clicked = game.buttonMap.get(view.getId());

        List<Country> occupiedCountries = player.getOccupied();

        if (occupiedCountries.contains(game.buttonMap.get(view.getId()))) {

            int oldArmies = clicked.getArmies();
            int newArmies = oldArmies + 1;
            clicked.setArmies(newArmies);

            Button button = (Button) view;
            button.setText(Integer.toString(newArmies));
            player.setAvailable(availableStrength--);

            game.showSnackbar(availableStrength + " armies available to reinforce your Countries");
            game.sendMessage(new UpdateMessage(game.buttonMap.get(view.getId()).getName(), game.buttonMap.get(view.getId()).getArmies()));

            if (availableStrength == 0) {
                changeState();
            }

        } else {
            game.showSnackbar("Choose one of your occupied Countries");
        }
    }

    @Override
    public void changeState() {
        game.setState(new AttackState());
    }
}
