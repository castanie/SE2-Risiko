package at.aau.risiko.core;

public abstract class State {

    Game game;

    public State(Game game) {
        this.game = game;
    }

    
    // Methods:

    public abstract void handleInput();
    public abstract void changeState();

}
