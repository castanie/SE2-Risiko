package at.aau.risiko.core;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

public class ObserveState extends State {

    public ObserveState(Game game) {
        super(game);
        //TODO Auto-generated constructor stub
    }

    /**
     * 
     * The constructor must notify the Server to broadcast and 
     * hand command to the next player.
     * 
     * handleInput():
     * Do nothing.
     * 
     * changeState():
     * Do nothing.
     * 
     */

    // Methods:

    @Override
    public void handleInput(View view) {
        // TODO Remove!
        Button button = (Button) view;
        button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFBB33")));
        button.setText(Integer.toString(Integer.parseInt(button.getText().toString()) + 1));
    }

    @Override
    public void changeState() {

    }
    
}
