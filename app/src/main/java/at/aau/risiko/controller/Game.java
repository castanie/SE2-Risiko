package at.aau.risiko.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.aau.core.CardList;
import at.aau.core.Country;
import at.aau.core.Player;
import at.aau.risiko.DiceActivityDefender;
import at.aau.risiko.MapActivity;
import at.aau.risiko.R;
import at.aau.server.dto.BaseMessage;
import at.aau.server.dto.DiceMessage;
import at.aau.server.dto.StartMessage;
import at.aau.server.dto.TurnMessage;
import at.aau.server.dto.UpdateMessage;
import at.aau.server.kryonet.GameClient;

public class Game {

    // Game is the local instance of the controller.
    private static Game instance;

    private State state;
    private final Player[] players;
    private final List<Country> availableCountries;
    private final CardList availableCards;

    private int currentIndex;

    private final Activity activity;
    HashMap<Integer, Country> buttonMap;
    HashMap<Integer, Player> avatarMap;


    private Game(Player[] players, List<Country> countries, HashMap<Integer, Country> buttonMap, HashMap<Integer, Player> avatarMap, Activity activity) {
        this.players = players;
        this.availableCountries = countries;
        this.availableCards = new CardList();

        this.currentIndex = 0;

        this.activity = activity;
        this.buttonMap = buttonMap;
        this.avatarMap = avatarMap;
    }

    public static Game getInstance() {
        if (instance == null) {
            Log.i("GAME INSTANCING", "The requested instance is null!");

            // throw new IllegalStateException("The requested instance is null!");
        }

        return instance;
    }

    public static Game getInstance(Player[] players, List<Country> countries, HashMap<Integer, Country> buttonMap, HashMap<Integer, Player> avatarMap, Activity activity) {
        if (instance == null) {
            Log.i("GAME INSTANCING", "A new instance was created.");

            instance = new Game(players, countries, buttonMap, avatarMap, activity);
            instance.setState(new ObserveState());
        }

        return instance;
    }


    // Methods:

    public void handleInput(View view) {
        state.handleInput(view);
    }

    public void changeState() {
        state.changeState();
    }


    // UI updates:

    public void setAvatar(int player) {

        // TODO: Reset Avatars to default size:

        // TODO: Set Avatar for current Player:

    }

    public void setProgress(int progress) {
        ProgressBar bar = ((MapActivity) activity).findViewById(R.id.progressBar);
        bar.setProgress(progress);
    }

    public void setInfo(String message) {
        TextView card = ((MapActivity) activity).findViewById(R.id.textViewCard);
        card.setText(message);
    }

    public void setCards(boolean enabled) {
        Button button = activity.findViewById(R.id.buttonCards);
        button.setEnabled(enabled);
    }

    public void showToast(String message) {
        Toast toast = Toast.makeText(this.activity, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(this.activity.findViewById(R.id.linearLayout), message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    // Update server:

    public void sendMessage(BaseMessage message) {
        // Send message to server.
        GameClient.getInstance().sendMessage(message);
    }


    // Update client:

    public void handleMessage(BaseMessage message) {
        // Should not be handled during a game:
        if (message instanceof StartMessage) {

            // Do nothing.

        }

        // Wakes Player from sleep and sets UI:
        else if (message instanceof TurnMessage) {

            // Set current Player:
            setIndex(((TurnMessage) message).playerIndex);

            // Set appropriate State:
            if (getCurrentPlayer().getAvailable() > 0) {
                this.setState(new SetupState());
            } else {
                this.setState(new DraftState());
            }

            // TODO: Set Avatar:

        }

        // Updates Country data and sets UI:
        else if (message instanceof UpdateMessage) {

            // Find Player:
            Player player = players[((UpdateMessage) message).playerIndex];

            // Find Country and Button:
            Country country = null;
            Button button = null;

            for (Map.Entry<Integer, Country> e : buttonMap.entrySet()) {
                if (e.getValue().getName().equals(((UpdateMessage) message).countryName)) {
                    country = e.getValue();
                    button = (Button) activity.findViewById(e.getKey());
                    break;
                }
            }

            // Update Model data:
            availableCountries.remove(country);
            for (Player p : players) {
                p.getOccupied().remove(country);
            }

            player.getOccupied().add(country);

            country.setColor(player.getColor());
            country.setArmies(((UpdateMessage) message).countryArmies);

            // Update View data:
            button.setBackgroundTintList(ColorStateList.valueOf(country.getColor()));
            button.setText(String.valueOf(country.getArmies()));

        }

        // Starts DiceActivity for defending Player:
        else if (message instanceof DiceMessage) {

            // TODO: Enter DiceActivity:
            Intent intent = new Intent(activity.getApplicationContext(), DiceActivityDefender.class);
            intent.putExtra("amount", 3);
            activity.startActivity(intent);

        }
    }


    // Getters & Setters:

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Context getContext() {
        return activity;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return players[currentIndex];
    }

    public List<Country> getAvailableCountries() {
        return availableCountries;
    }

    public int getIndex() {
        return currentIndex;
    }

    public void setIndex(int index) {
        this.currentIndex = index;
    }

    public CardList getCardDeck() {
        return availableCards;
    }

    public void setCardDeck() {
        this.availableCards.fillUpCardlistForStart();
    }
}