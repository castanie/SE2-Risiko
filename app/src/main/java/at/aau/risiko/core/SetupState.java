package at.aau.risiko.core;

import android.content.res.ColorStateList;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import at.aau.risiko.networking.dto.TurnMessage;

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
     *
     * changeState():
     * Transition to ObserveState.
     */

    // Methods:
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void handleInput(View view) {
        // TODO GET RID OF API DEPENDENCY!
        game.getPlayers()[game.getIndex()].getOccupied().put(view.getId(), game.buttonMap.get(view.getId()));
        game.getAvailableCountries().remove(game.buttonMap.get(view.getId()));
        Button button = (Button) view;
        button.setBackgroundTintList(ColorStateList.valueOf(game.getPlayers()[game.getIndex()].getColor().toArgb()));
        button.setText("0");
        changeState();
    }

    @Override
    public void changeState() {
        // TODO Auto-generated method stub
        game.setState(new ObserveState(game));
        game.sendMessage(new TurnMessage());
    }


}
