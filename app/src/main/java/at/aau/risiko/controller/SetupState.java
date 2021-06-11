package at.aau.risiko.controller;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import at.aau.core.Player;
import at.aau.server.dto.TurnMessage;
import at.aau.server.dto.UpdateMessage;

public class SetupState extends State {

    public SetupState(Game game) {
        super();
        Log.i("GAME STATE", "Transitioned into SetupState.");

        game.setProgress(3);
        game.setCard("Setup");
    }

    /**
     * handleInput():
     * Claim country if free.
     * Change state when successful, otherwise do nothing.
     * <p>
     * changeState():
     * Transition to ObserveState.
     */

    // Methods:
    public void assignArmys() {
        Player p = game.getPlayers()[game.getIndex()];
        if (game.getPlayers().length == 5) {
            p.setAvailable(25);
        } else if (game.getPlayers().length == 4) {
            p.setAvailable(30);
        } else if (game.getPlayers().length <= 3) {
            p.setAvailable(35);
        }
    }

    @Override
    public void handleInput(View view) {
        game.getPlayers()[game.getIndex()].getOccupied().add(game.buttonMap.get(view.getId()));
        game.getAvailableCountries().remove(game.buttonMap.get(view.getId()));
        Button button = (Button) view;
        //button.setBackgroundTintList(ColorStateList.valueOf(game.getPlayers()[game.getIndex()].getColor()));
        // button.setText("0");

        // TODO: CHANGE HARDCODED NAME AND COLOR!
        game.sendMessage(new UpdateMessage(null, game.buttonMap.get(view.getId()).getName(), game.buttonMap.get(view.getId()).getArmies()));
        assignArmys();
        changeState();
    }


    @Override
    public void changeState() {
        game.setState(new ObserveState());
        game.sendMessage(new TurnMessage());
    }

}
