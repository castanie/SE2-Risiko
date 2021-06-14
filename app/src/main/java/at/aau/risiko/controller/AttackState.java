package at.aau.risiko.controller;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import at.aau.core.Country;
import at.aau.core.Player;
import at.aau.risiko.DiceActivityAttacker;
import at.aau.server.dto.CardMessage;
import at.aau.server.dto.DiceMessage;

public class AttackState extends State {

    private Integer occupied;
    private Country attacking;
    private Country defending;

    public AttackState(Game game) {
        super(game);
        Log.i("GAME STATE", "Transitioned into AttackState.");

        occupied = game.getCurrentPlayer().getOccupied().size();
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

        // Executed on first click:
        if (attacking == null) {
            if (game.getCurrentPlayer().getOccupied().contains(clicked)) {
                attacking = clicked;
                game.setCountryButtonHighlight(view);
            } else {

            }
        }

        // Executed on second click:
        else if (defending == null) {

            // Make sure Countries have a common border:
            if (clicked.getNeighbors().contains(attacking)) {

                // Make sure Player is not attacking himself:
                if (!game.getCurrentPlayer().getOccupied().contains(clicked)) {
                    defending = clicked;

                    // Make sure Player has enough armies to attack:
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
                    game.showSnackbar("You cannot attack yourself!");
                    attacking = null;
                    defending = null;
                }

            } else {
                game.showSnackbar("You can only attack neighbouring countries!");
                attacking = null;
                defending = null;
            }

            // Reset highlighting:
            game.resetCountryButtonHighlight();

        } else {
            attacking = null;
            defending = null;
        }
    }

    @Override
    public void changeState() {
        // If Player has conquered a Country, draw Card:
        if (occupied < game.getCurrentPlayer().getOccupied().size()) {
            String card = game.getCardDeck().drawCardFromCardList();
            game.sendMessage(new CardMessage(card));
            game.showSnackbar("You received a new card!");
        }
        game.setState(new FortifyState(game));
    }

}
