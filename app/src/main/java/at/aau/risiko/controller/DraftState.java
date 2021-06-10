package at.aau.risiko.controller;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.List;

import at.aau.core.Country;
import at.aau.core.Player;
import at.aau.server.dto.UpdateMessage;

public class DraftState extends State {


    int availableStrength;
    Country clicked;
    Player p = game.getPlayers()[game.getIndex()];

    /* The constructor must calculate the armies available to
     the player.*/

    public DraftState(Game game) {
        super(game);
        Log.i("GAME STATE", "Transitioned into DraftState.");
        game.setCardView("Strengthen");

        if(p.getAvailable() > 0)
        {
            this.availableStrength = p.getAvailable() + CalculateStrength();
        }
        else
        {
            this.availableStrength = CalculateStrength();
        }

        game.setProgress(1);
    }

    private int CalculateStrength() {
        int occupiedCountries = p.getOccupied().size();
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

    // @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void handleInput(View view) {

        clicked = game.buttonMap.get(view.getId());

        List<Country> occupiedCountries = p.getOccupied();

        if (occupiedCountries.contains(game.buttonMap.get(view.getId()))) {

            int oldArmies = clicked.getArmies();
            int newArmies = oldArmies + 1;
            clicked.setArmies(newArmies);

            Button button = (Button) view;
            button.setText(Integer.toString(newArmies));
            p.setAvailable(availableStrength--);

            game.showSnackbar(availableStrength + " armies available to reinforce your countries");
            game.sendMessage(new UpdateMessage("Uno", game.buttonMap.get(view.getId()).getName(), game.buttonMap.get(view.getId()).getArmies()));

            if (availableStrength == 0) {
                changeState();
            }

        } else {
            game.showSnackbar("Choose one of your occupied Countries");
        }
    }

    @Override
    public void changeState() {
        game.setState(new AttackState(game));
    }
}
