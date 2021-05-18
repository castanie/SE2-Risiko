package at.aau.risiko.core;

public class FortifyState extends State {

    Country donor;
    Country recipient;

    public FortifyState(Game game) {
        super(game);
        //TODO Auto-generated constructor stub
    }

    /**
     * 
     * handleInput():
     * Select donor country.
     * Select recipient country.
     * Change state if selection is valid, otherwise reset.
     * 
     * changeState():
     * Send message to server, then transition to ObserverState.
     * 
     */

    // Methods:

    @Override
    public void handleInput() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void changeState() {
        // TODO Auto-generated method stub
        
    }
    
}
