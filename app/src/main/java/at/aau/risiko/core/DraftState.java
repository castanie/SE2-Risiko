package at.aau.risiko.core;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;

import at.aau.risiko.MapActivity;
import at.aau.risiko.R;

public class DraftState extends State {


    int availableStrength;
    Country clicked;
    Player p = game.getPlayers()[game.getIndex()];

    /* The constructor must calculate the armies available to
     the player.*/

    public DraftState(Game game) {
        super(game);
        Log.i("GAME STATE", "Transitioned into DraftState.");
        
        this.availableStrength = CalculateStrength();

        game.showToast(availableStrength + " armies available to reinforce your countries");

        game.setProgress(1);
    }

    private int CalculateStrength() {
        int occupiedCountries = p.getOccupied().size();
        int strength = occupiedCountries / 3;
        if (occupiedCountries == 0) {
            game.showToast("You have lost the game!");
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

        HashMap<Integer, Country> occupiedCountries = p.getOccupied();

        if (occupiedCountries.containsKey(view.getId())) {

            int oldArmies = clicked.getArmies();
            int newArmies = oldArmies + 1;
            clicked.setArmies(newArmies);

            Button button = (Button) view;
            button.setText(Integer.toString(newArmies));
            p.setAvailable(availableStrength--);

            game.showToast(availableStrength + " armies available to reinforce your countries");

            if (availableStrength == 0) {
                changeState();
            }

        } else {
            game.showToast("Choose one of your occupied Countries");
        }
    }

    @Override
    public void changeState() {
        game.setState(new AttackState(game));
        // game.sendMessage(null);
    }
}
