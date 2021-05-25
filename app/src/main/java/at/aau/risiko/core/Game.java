package at.aau.risiko.core;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import at.aau.risiko.MapActivity;
import at.aau.risiko.R;
import at.aau.risiko.networking.dto.BaseMessage;
import at.aau.risiko.networking.kryonet.GameClient;

public class Game {

    // Game is the local instance of the controller.
    private Context context;
    private State state;
    private Player[] players;
    private int index;

    private List<Country> availableCountries;
    private List<Card> availableCards;

    HashMap<Integer, Country> buttonMap;
    HashMap<Integer, Player> avatarMap;


    public Game(Player[] players, HashMap<Integer, Country> buttonMapping, Context context) {
        this.state = new SetupState(this);
        this.players = players;
        this.index = 0;
        this.availableCountries = new LinkedList<>();
        this.availableCards = new LinkedList<>();
        this.buttonMap = buttonMapping;
        this.context = context;
    }


    // Methods:

    public void handleInput(View view) {
        state.handleInput(view);
    }

    public void changeState() {
        state.changeState();
    }


    // UI updates:

    public void setProgress(int progress) {
        ProgressBar bar = ((MapActivity) context).findViewById(R.id.progressBar);
        bar.setProgress(progress);
    }

    public void showToast(String message) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this.context, message, duration);
        toast.show();
    }


    // Update server:

    public void sendMessage(BaseMessage message) {
        // Send request to server.
        GameClient.getInstance().sendMessage(message);
    }


    // Update client:

    public void handleMessage(BaseMessage message) {
        // Handle response from server.
        // This method should be called by the GameClient.
    }


    // Getters & Setters:

    public State getState() {
        return state;
    }

    public Context getContext() {
        return context;
    }

    public Player[] getPlayers() {
        return players;
    }

    public List<Country> getAvailableCountries() {
        return availableCountries;
    }

    public List<Card> getAvailableCards() {
        return availableCards;
    }

    public int getIndex() {
        return index;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
