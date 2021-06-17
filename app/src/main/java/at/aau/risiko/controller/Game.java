package at.aau.risiko.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import at.aau.core.CardList;
import at.aau.core.Country;
import at.aau.core.HandDeck;
import at.aau.core.Player;
import at.aau.risiko.DiceActivityDefender;
import at.aau.risiko.MenuActivity;
import at.aau.risiko.R;
import at.aau.server.dto.BaseMessage;
import at.aau.server.dto.CardMessage;
import at.aau.server.dto.DiceMessage;
import at.aau.server.dto.ExchangeMessage;
import at.aau.server.dto.LostMessage;
import at.aau.server.dto.QuitMessage;
import at.aau.server.dto.ReadyMessage;
import at.aau.server.dto.StartMessage;
import at.aau.server.dto.TurnMessage;
import at.aau.server.dto.UpdateMessage;
import at.aau.server.dto.ConqueredMessage;
import at.aau.server.dto.WonMessage;
import at.aau.server.kryonet.GameClient;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

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
    private boolean hasSetupGame;
    private boolean hasConqueredCountry;

    private final Activity activity;
    HashMap<Integer, Country> buttonMap;
    HashMap<Integer, Player> avatarMap;

    private final String[] attackerWonMessages = {"After a long battle the army of the attacker has won!",
            "We all thought the defenders would win, but the attacking army fought like vikings and prevailed.",
            "Real warriors never give up we could all learn from the way the attackers army just won.",
            "No sacrifice, no victory ... Attacking army won this crushing battle."};

    private final String[] defenderWonMessages = {"After a long battle the army of the defender has won!",
            "We all thought the attackers would win, but the defending army fought like vikings and prevailed.",
            "Real warriors never give up we could all learn from the way the defenders just held their country.",
            "No sacrifice, no victory ... Defending army won this crushing battle."};

    private final int[] playerWonGifs = {R.drawable.gifone, R.drawable.giftwo, R.drawable.gifthree, R.drawable.giffour, R.drawable.giffive, R.drawable.gifsix, R.drawable.gifseven, R.drawable.gifeight, R.drawable.gifnine, R.drawable.giften};

    Random rand = new Random();
    int winMessage;

    public Game(Player[] players, List<Country> countries, HashMap<Integer, Country> buttonMap, HashMap<Integer, Player> avatarMap, Activity activity) {
        this.players = players;
        this.availableCountries = countries;

        availableCards = new CardList();
        availableCards.fillUpCardlistForStart();
        drawnCards = new HandDeck();

        this.currentIndex = 0;
        this.hasSetupGame = false;
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

    public void fadeAvatar(int playerIndex) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO: Set Avatar for current Player:
                for (Map.Entry<Integer, Player> e : avatarMap.entrySet()) {
                    if (e.getValue().equals(players[playerIndex])) {
                        ImageView avatar = activity.findViewById(e.getKey());
                        avatar.setImageTintList(ColorStateList.valueOf(0xFFCACACA));
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
                blinkAnimation.setDuration(800);

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
                Toast toast = Toast.makeText(activity, message, Toast.LENGTH_LONG);
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

    // Show a Snackbar in the MapActivity:
    public void showLongSnackbar(String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.linearLayout), message, 8000);
                snackbar.show();
            }
        });
    }

    public void showWinnerGif() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GifImageView gifView = activity.findViewById(R.id.winnerGif);
                gifView.setImageResource(playerWonGifs[rand.nextInt(10)]);
                gifView.setVisibility(View.VISIBLE);
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
            showSnackbar("You received three country cards!");

            for (int i = 0; i < 3; ++i) {
                String card;
                card = availableCards.drawCardFromCardList();
                drawnCards.addCardToHandDeck(card);
                sendMessage(new CardMessage(card, getIndex()));
            }

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

            // Set current Player:
            setIndex(((TurnMessage) message).playerIndex);

            // Set appropriate State (if it's this Player's turn):
            if (((TurnMessage) message).isCurrentPlayer) {
                if (getCurrentPlayer().getAvailable() > 0) {
                    this.setState(new SetupState(this));
                } else {
                    hasSetupGame = true;
                    this.setState(new DraftState(this));
                }
            }

            // Set Avatar:
            setAvatar();

            // See if this player has lost:
            if (state instanceof LostState) {
                sendMessage(new TurnMessage());
            }
            else if (((TurnMessage) message).isCurrentPlayer && hasSetupGame && getCurrentPlayer().getOccupied().size() == 0) {
                this.setState(new LostState(this));
                sendMessage(new LostMessage(getIndex()));
                sendMessage(new TurnMessage());
            }

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

            if (((UpdateMessage) message).getWonString() != null) {
                winMessage = rand.nextInt(3);
                if(((UpdateMessage) message).getWonString().equals("Attacker")) {
                    showLongSnackbar(attackerWonMessages[winMessage]);
                } else if (((UpdateMessage) message).getWonString().equals("Defender")) {
                    showLongSnackbar(defenderWonMessages[winMessage]);
                }

            }

        }

        // Starts DiceActivity for defending Player:
        else if (message instanceof DiceMessage) {

            // Enter DiceActivity:
            Intent intent = new Intent(activity.getApplicationContext(), DiceActivityDefender.class);
            activity.startActivity(intent);

        }

        // Tells Player to draw a Card:
        else if (message instanceof ConqueredMessage) {

            // Set flag true so awardCards fires:
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

        //
        else if (message instanceof LostMessage) {
            fadeAvatar(((LostMessage) message).playerIndex);
        }

        //
        else if (message instanceof WonMessage) {
            showToast("You have conquered the globe!");

            showWinnerGif();

        }

        //
        else if (message instanceof QuitMessage) {
            activity.startActivity(new Intent(activity, MenuActivity.class));
            // activity.finish();
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