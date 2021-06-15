package at.aau.risiko.controller;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import at.aau.core.Country;
import at.aau.core.Player;
import at.aau.server.dto.UpdateMessage;

public class DraftState extends State {

    Player player;

    /* The constructor must calculate the armies available to
     the player.*/

    public DraftState(Game game) {
        super(game);
        Log.i("GAME STATE", "Transitioned into DraftState.");

        player = game.getCurrentPlayer();

        if (player.getAvailable() > 0) {
             player.setAvailable(player.getAvailable() + calculateStrength());
        } else {
            player.setAvailable(calculateStrength());
        }

        game.setProgress(1);
        game.setInfo("Strengthen");
        game.setCardsButtonClickable(true);
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

        Button button = (Button) view;

        Player player = game.getCurrentPlayer();
        Country country = game.buttonMap.get(view.getId());

        // If current Player already holds the clicked Country:
        if (player.getOccupied().contains(country)) {

            int oldArmies = country.getArmies();
            int newArmies = oldArmies + 1;
            country.setArmies(newArmies);

            button.setText(Integer.toString(newArmies));
            player.setAvailable(player.getAvailable() - 1);

            game.showSnackbar(player.getAvailable() + " armies left to place on the board.");
            game.sendMessage(new UpdateMessage(game.buttonMap.get(button.getId()).getName(), game.buttonMap.get(button.getId()).getArmies(), game.getIndex()));

            if (player.getAvailable() == 0) {
                changeState();
            }
        }

        // Else the clicked Country belongs to another Player:
        else {
            game.showSnackbar("Choose one of your occupied Countries!");
        }
    }

    @Override
    public void changeState() {
        game.setState(new AttackState(game));
        game.setCardsButtonClickable(false);
    }
}
