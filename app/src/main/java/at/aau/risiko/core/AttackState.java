package at.aau.risiko.core;

import android.view.View;

public class AttackState extends State {

    private Country attacking;
    private Country defending;

    public AttackState(Game game) {
        super(game);
        //TODO Auto-generated constructor stub
    }

    /**
     * 
     * handleInput():
     * Select attacking country.
     * Select defending country.
     * Send message to server if selection is valid, otherwise reset.
     * [Roll dice ...]
     * Change state if player presses skip button.
     * 
     * changeState():
     * Transition to FortifyState.
     * 
     */
    
    // Methods:

    @Override
    public void handleInput(View view) {
        // TODO Auto-generated method stub
        
        
    }

    @Override
    public void changeState() {
        // TODO Auto-generated method stub
        
    }
    
}
