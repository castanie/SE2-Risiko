package at.aau.risiko.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.aau.core.CardList;
import at.aau.core.Country;
import at.aau.core.HandDeck;
import at.aau.core.Player;
import at.aau.risiko.DiceActivityDefender;
import at.aau.risiko.R;
import at.aau.server.dto.BaseMessage;
import at.aau.server.dto.CardMessage;
import at.aau.server.dto.DiceMessage;
import at.aau.server.dto.ExchangeMessage;
import at.aau.server.dto.ReadyMessage;
import at.aau.server.dto.StartMessage;
import at.aau.server.dto.TurnMessage;
import at.aau.server.dto.UpdateMessage;
import at.aau.server.dto.ConqueredMessage;
import at.aau.server.kryonet.GameClient;

public class Game {

    // Game is the local instance of the controller.
    private State state;
    private final Player[] players;
    private final List<Country> availableCountries;

    // TODO: Revise, these might be dangerous.
    // These are the remaining Cards in the staple:
    public static CardList availableCards;
    // These are the Cards drawn by THIS Player:
    public static HandDeck drawnCards;

    private int currentIndex;
    private boolean hasConqueredCountry;

    private final Activity activity;
    HashMap<Integer, Country> buttonMap;
    HashMap<Integer, Player> avatarMap;


    public Game(Player[] players, List<Country> countries, HashMap<Integer, Country> buttonMap, HashMap<Integer, Player> avatarMap, Activity activity) {
        this.players = players;
        this.availableCountries = countries;

        availableCards = new CardList();
        availableCards.fillUpCardlistForStart();
        drawnCards = new HandDeck();

        this.currentIndex = 0;
        this.hasConqueredCountry = false;

        this.activity = activity;
        this.buttonMap = buttonMap;
        this.avatarMap = avatarMap;
    }


    ///////////////////////
    // Delegate methods: //
    ///////////////////////

    public void handleInput(View view) {
        state.handleInput(view);
    }

    public void changeState() {
        state.changeState();
    }


    /////////////////
    // UI updates: //
    /////////////////

    public void setAvatar() {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO: Reset Avatars to default size:
                for (Integer i : avatarMap.keySet()) {
                    ImageView avatar = activity.findViewById(i);
                    avatar.setScaleX(0.8f);
                    avatar.setScaleY(0.8f);
                }

                // TODO: Set Avatar for current Player:
                for (Map.Entry<Integer, Player> e : avatarMap.entrySet()) {
                    if (e.getValue().equals(getCurrentPlayer())) {
                        ImageView avatar = activity.findViewById(e.getKey());
                        avatar.setScaleX(1.0f);
                        avatar.setScaleY(1.0f);
                    }
                }
            }
        });

    }

    // Set the ProgressBar on top of the CardView:
    public void setProgress(int progress) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ProgressBar bar = activity.findViewById(R.id.progressBar);
                bar.setProgress(progress);
            }
        });
    }

    // Set the TextView inside the CardView:
    public void setInfo(String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView card = activity.findViewById(R.id.textViewCard);
                card.setText(message);
            }
        });
    }

    // Enable or disable the button leading to CardActivity:
    public void setCardsButtonClickable(boolean enabled) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button button = activity.findViewById(R.id.buttonCards);
                button.setEnabled(enabled);
            }
        });
    }

    // Enable or disable the button leading to CardActivity:
    public void setNextButtonClickable(boolean enabled) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageButton button = activity.findViewById(R.id.buttonNext);

                if (enabled) {
                    button.setVisibility(View.VISIBLE);
                } else {
                    button.setVisibility(View.GONE);
                }
            }
        });
    }

    public void setCountryButtonHighlight(View view) {
        // Turn on animation for given Country button:
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Animation blinkAnimation = AnimationUtils.loadAnimation(activity, R.anim.blink);
                blinkAnimation.setRepeatCount(Animation.INFINITE);
                blinkAnimation.setRepeatMode(Animation.REVERSE);
                blinkAnimation.setDuration(1000);

                view.startAnimation(blinkAnimation);
            }
        });
    }

    public void resetCountryButtonHighlight() {
        // Reset all Country button animations:
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Integer i : buttonMap.keySet()) {
                    activity.findViewById(i).clearAnimation();
                }
            }
        });
    }

    // Show a Toast in the MapActivity:
    public void showToast(String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    // Show a Snackbar in the MapActivity:
    public void showSnackbar(String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.linearLayout), message, 1000);
                snackbar.show();
            }
        });
    }


    ////////////////////
    // Update server: //
    ////////////////////

    public void sendMessage(BaseMessage message) {
        // Send message to server.
        GameClient.getInstance().sendMessage(message);
    }

    public void awardCards() {
        if (hasConqueredCountry) {
            showSnackbar("You received a country card!");

            String card;
            card = availableCards.drawCardFromCardList();
            drawnCards.addCardToHandDeck(card);
            sendMessage(new CardMessage(card, getIndex()));

            hasConqueredCountry = false;
        }
    }


    ////////////////////
    // Update client: //
    ////////////////////

    public void handleMessage(BaseMessage message) {
        // Should not be handled during a game:
        if (message instanceof StartMessage) {

            // Do nothing.

        }

        // Should not be handled during a game:
        if (message instanceof ReadyMessage) {

            // Do nothing.

        }

        // Wakes Player from sleep and sets UI:
        else if (message instanceof TurnMessage) {
            // TODO: IMPORTANT! Debug random color changes after new Turn!
            Log.i("TURN MESSAGE", "Old: " + getIndex() + ", New: " + ((TurnMessage) message).playerIndex);

            // Set current Player:
            setIndex(((TurnMessage) message).playerIndex);

            // Set appropriate State (if it's this Player's turn):
            if (((TurnMessage) message).isCurrentPlayer) {
                if (getCurrentPlayer().getAvailable() > 0) {
                    this.setState(new SetupState(this));
                } else {
                    this.setState(new DraftState(this));
                }
            }

            // Set Avatar:
            setAvatar();

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
                    button = activity.findViewById(e.getKey());
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

            // Enter DiceActivity:
            Intent intent = new Intent(activity.getApplicationContext(), DiceActivityDefender.class);
            activity.startActivity(intent);

        }

        // Tells Player to draw a Card:
        else if (message instanceof ConqueredMessage) {

            hasConqueredCountry = true;

        }

        // Updates CardList data and sets UI:
        else if (message instanceof CardMessage) {

            // Set Card as drawn:
            Player player = players[((CardMessage) message).playerIndex];
            player.getHandDeck().addCardToHandDeck(((CardMessage) message).cardName);
            availableCards.removeCardFromCardsList(((CardMessage) message).cardName);

        }

        // Updates HandDeck data and sets UI:
        else if (message instanceof ExchangeMessage) {

            // This message exists because the Exchange inside the CardActivity has to find
            // its way into the MapActivity - it gets relayed from one Player to himself!

            Player player = players[((ExchangeMessage) message).playerIndex];

            // Award Player bonus armies:
            player.setAvailable(player.getAvailable() + ((ExchangeMessage) message).bonusArmies);

            // Set Cards as exchanged:
            player.getHandDeck().deleteCardFromHandDeck(((ExchangeMessage) message).cardOne);
            player.getHandDeck().deleteCardFromHandDeck(((ExchangeMessage) message).cardTwo);
            player.getHandDeck().deleteCardFromHandDeck(((ExchangeMessage) message).cardThree);

        }
    }


    ////////////////////////
    // Getters & Setters: //
    ////////////////////////

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
        availableCards.fillUpCardlistForStart();
    }
}