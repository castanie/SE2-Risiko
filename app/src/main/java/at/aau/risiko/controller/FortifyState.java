package at.aau.risiko.controller;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import at.aau.core.Country;
import at.aau.core.Player;
import at.aau.server.dto.TurnMessage;
import at.aau.server.dto.UpdateMessage;

public class FortifyState extends State {

    private final Player player = game.getCurrentPlayer();
    private Country donor;
    private Button donorButton;
    private Country recipient;
    private Button recipientButton;

    public FortifyState(Game game) {
        super(game);
        Log.i("GAME STATE", "Transitioned into FortifyState.");

        donor = null;
        recipient = null;

        game.setProgress(3);
        game.setInfo("Move");
        game.setNextButtonClickable(true);
    }

    /**
     * handleInput():
     * Select donor country.
     * Select recipient country.
     * Change state if selection is valid, otherwise reset.
     * <p>
     * changeState():
     * Send message to server, then transition to ObserverState.
     */

    // Methods:
    @Override
    public void handleInput(View view) {

        Country clicked = game.buttonMap.get(view.getId());

        List<Country> occupiedCountries = player.getOccupied();

        // Make sure Player actually holds the Country:
        if (occupiedCountries.contains(game.buttonMap.get(view.getId()))) {

            // Executed on first click:
            if (donor == null) {
                donor = clicked;
                donorButton = (Button) view;
                game.setCountryButtonHighlight(view);
            }

            // Executed on second click:
            else if (recipient == null) {
                if (clicked.getNeighbors().contains(donor)) {
                    recipient = clicked;
                    recipientButton = (Button) view;

                    if (donor.getArmies() > 1) {
                        //move one Army from donor to recipient
                        int movingArmies = donor.getArmies() / 2;
                        int donorArmies = donor.getArmies() - movingArmies;
                        int recipientArmies = recipient.getArmies() + movingArmies;

                        donor.setArmies(donorArmies);
                        recipient.setArmies(recipientArmies);

                        donorButton.setText(Integer.toString(donorArmies));
                        recipientButton.setText(Integer.toString(recipientArmies));

                        game.sendMessage(new UpdateMessage(donor.getName(), donor.getArmies(), game.getIndex()));
                        game.sendMessage(new UpdateMessage(recipient.getName(), recipient.getArmies(), game.getIndex()));

                        changeState();
                    } else {
                        game.showSnackbar("Not enough armies to move");
                    }
                } else {
                    game.showSnackbar("You can only move armies between neighbouring countries!");
                    donor = null;
                    donorButton = null;
                    recipient = null;
                    recipientButton = null;
                }

                // Reset highlighting:
                game.resetCountryButtonHighlight();

            } else {
                donor = null;
                donorButton = null;
                recipient = null;
                recipientButton = null;
            }

        } else {

            game.showSnackbar("You can move armies only between your own countries!");
        }
    }

    @Override
    public void changeState() {
        game.setState(new ObserveState(game));
        game.sendMessage(new TurnMessage());
        game.setNextButtonClickable(false);
    }

}
