package at.aau.risiko.controller;

import android.content.res.ColorStateList;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import at.aau.core.Country;
import at.aau.core.Player;
import at.aau.server.dto.TurnMessage;
import at.aau.server.dto.UpdateMessage;

public class SetupState extends State {

    public SetupState() {
        super();
        Log.i("GAME STATE", "Transitioned into SetupState.");

        game.setProgress(3);
        game.setInfo("Setup");
    }

    /**
     * handleInput():
     * Claim country if free.
     * Change state when successful, otherwise do nothing.
     * <p>
     * changeState():
     * Transition to ObserveState.
     */

    // TODO: Move into Player creation in MapActivity
    /*
    // Methods:
    public void assignArmies() {
        Player p = game.getPlayers()[game.getIndex()];
        if (game.getPlayers().length == 5) {
            p.setAvailable(15);
        } else if (game.getPlayers().length == 4) {
            p.setAvailable(20);
        } else if (game.getPlayers().length <= 3) {
            p.setAvailable(25);
        }
    }
    */
    @Override
    public void handleInput(View view) {

        Button button = (Button) view;

        Player player = game.getCurrentPlayer();
        Country country = game.buttonMap.get(view.getId());

        // If no Player holds the clicked Country:
        if (game.getAvailableCountries().contains(country)) {
            player.getOccupied().add(country);
            game.getAvailableCountries().remove(country);

            button.setBackgroundTintList(ColorStateList.valueOf(player.getColor()));
        }

        // If current Player already holds the clicked Country:
        if (player.getOccupied().contains(country)) {

            int oldArmies = country.getArmies();
            int newArmies = oldArmies + 1;
            country.setArmies(newArmies);

            button.setText(Integer.toString(newArmies));
            player.setAvailable(player.getAvailable() - 1);

            game.showSnackbar(player.getAvailable() + " armies left to place on the board.");
            game.sendMessage(new UpdateMessage(game.buttonMap.get(button.getId()).getName(), game.buttonMap.get(button.getId()).getArmies()));

            changeState();
        }

        // Else the clicked Country belongs to another Player:
        else {
            game.showSnackbar("This is not yours, buddy.");
        }

    }


    @Override
    public void changeState() {
        game.setState(new ObserveState());
        game.sendMessage(new TurnMessage());
    }

}
