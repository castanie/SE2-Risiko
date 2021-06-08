package at.aau.risiko.controller;

import android.content.res.ColorStateList;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import at.aau.server.dto.TurnMessage;
import at.aau.server.dto.UpdateMessage;

public class SetupState extends State {

    public SetupState(Game game) {
        super(game);
        Log.i("GAME STATE", "Transitioned into SetupState.");
        //TODO Auto-generated constructor stub
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
    @Override
    public void handleInput(View view) {
        game.getPlayers()[game.getIndex()].getOccupied().add(game.buttonMap.get(view.getId()));
        game.getAvailableCountries().remove(game.buttonMap.get(view.getId()));
        Button button = (Button) view;
        //button.setBackgroundTintList(ColorStateList.valueOf(game.getPlayers()[game.getIndex()].getColor()));
        // button.setText("0");

        // TODO: CHANGE HARDCODED NAME AND COLOR!
        game.sendMessage(new UpdateMessage(null, game.buttonMap.get(view.getId()).getName(), game.buttonMap.get(view.getId()).getArmies()));
        changeState();
    }

    @Override
    public void changeState() {
        game.setState(new ObserveState(game));
        game.sendMessage(new TurnMessage());
    }

}
