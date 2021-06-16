package at.aau.risiko;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import at.aau.core.CardList;
import at.aau.core.HandDeck;
import at.aau.risiko.controller.Game;
import at.aau.server.dto.CardMessage;
import at.aau.server.dto.ExchangeMessage;
import at.aau.server.kryonet.GameClient;


public class CardActivity extends AppCompatActivity {


    // Initialization of necessary variables
    ArrayList<Integer> imgIdsHandDeck;
    ArrayList<Integer> imgIdsSelection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        Button close = findViewById(R.id.btn_close_cardexchange);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        updateDataForShowingHandDeck();
        showHandDeck();

    }


    // Updates the necessary data for showing the handdeck
    public void updateDataForShowingHandDeck() {
        imgIdsHandDeck = new ArrayList<>();

        for (int i = 0; i < Game.drawnCards.size(); i++) {
            String card = Game.drawnCards.getCardFromHandDeck(i);

            if (card.equals("Alaska")) {
                imgIdsHandDeck.add(R.drawable.ca_alaska);
            }
            if (card.equals("Greenland")) {
                imgIdsHandDeck.add(R.drawable.ca_greenland);
            }
            if (card.equals("Canada")) {
                imgIdsHandDeck.add(R.drawable.ca_canada);
            }
            if (card.equals("Central America")) {
                imgIdsHandDeck.add(R.drawable.ca_central_amerika);
            }
            if (card.equals("Venezuela")) {
                imgIdsHandDeck.add(R.drawable.ca_venezuela);
            }
            if (card.equals("Peru")) {
                imgIdsHandDeck.add(R.drawable.ca_peru);
            }
            if (card.equals("Brazil")) {
                imgIdsHandDeck.add(R.drawable.ca_brazil);
            }
            if (card.equals("Argentina")) {
                imgIdsHandDeck.add(R.drawable.ca_argentina);
            }
            if (card.equals("North Africa")) {
                imgIdsHandDeck.add(R.drawable.ca_north_africa);
            }
            if (card.equals("East Africa")) {
                imgIdsHandDeck.add(R.drawable.ca_east_africa);
            }
            if (card.equals("Congo")) {
                imgIdsHandDeck.add(R.drawable.ca_congo);
            }
            if (card.equals("South Africa")) {
                imgIdsHandDeck.add(R.drawable.ca_south_africa);
            }
            if (card.equals("Scandinavia")) {
                imgIdsHandDeck.add(R.drawable.ca_scandinavia);
            }
            if (card.equals("Ukraine")) {
                imgIdsHandDeck.add(R.drawable.ca_ukraine);
            }
            if (card.equals("Western Europe")) {
                imgIdsHandDeck.add(R.drawable.ca_western_europe);
            }
            if (card.equals("Indonesia")) {
                imgIdsHandDeck.add(R.drawable.ca_indonesia);
            }
            if (card.equals("Western Australia")) {
                imgIdsHandDeck.add(R.drawable.ca_western_australia);
            }
            if (card.equals("Eastern Australia")) {
                imgIdsHandDeck.add(R.drawable.ca_eastern_australia);
            }
            if (card.equals("Siam")) {
                imgIdsHandDeck.add(R.drawable.ca_siam);
            }
            if (card.equals("India")) {
                imgIdsHandDeck.add(R.drawable.ca_india);
            }
            if (card.equals("China")) {
                imgIdsHandDeck.add(R.drawable.ca_china);
            }
            if (card.equals("Mongolia")) {
                imgIdsHandDeck.add(R.drawable.ca_mongolia);
            }
            if (card.equals("Siberia")) {
                imgIdsHandDeck.add(R.drawable.ca_siberia);
            }
            if (card.equals("Ural")) {
                imgIdsHandDeck.add(R.drawable.ca_ural);
            }
            if (card.equals("Middle East")) {
                imgIdsHandDeck.add(R.drawable.ca_middle_east);
            }
            if (card.equals("USA")) {
                imgIdsHandDeck.add(R.drawable.ca_usa);
            }
            if (card.equals("Joker1")) {
                imgIdsHandDeck.add(R.drawable.ca_joker1);
            }
            if (card.equals("Joker2")) {
                imgIdsHandDeck.add(R.drawable.ca_joker2);
            }
        }


    }

    // shows the cards from Handdeck
    public void showHandDeck() {
        setHanddeckPicturesToNoCard();

        for (int i = 0; i < imgIdsHandDeck.size(); i++) {
            if (i == 0) {

                ImageView img = findViewById(R.id.id_handdeck_card_0);
                img.setImageResource(imgIdsHandDeck.get(i));
            }
            if (i == 1) {

                ImageView img = findViewById(R.id.id_handdeck_card_1);
                img.setImageResource(imgIdsHandDeck.get(i));
            }
            if (i == 2) {

                ImageView img = findViewById(R.id.id_handdeck_card_2);
                img.setImageResource(imgIdsHandDeck.get(i));
            }
            if (i == 3) {

                ImageView img = findViewById(R.id.id_handdeck_card_3);
                img.setImageResource(imgIdsHandDeck.get(i));
            }
            if (i == 4) {

                ImageView img = findViewById(R.id.id_handdeck_card_4);
                img.setImageResource(imgIdsHandDeck.get(i));
            }
            if (i == 5) {

                ImageView img = findViewById(R.id.id_handdeck_card_5);
                img.setImageResource(imgIdsHandDeck.get(i));
            }
            if (i == 6) {

                ImageView img = findViewById(R.id.id_handdeck_card_6);
                img.setImageResource(imgIdsHandDeck.get(i));
            }
            if (i == 7) {

                ImageView img = findViewById(R.id.id_handdeck_card_7);
                img.setImageResource(imgIdsHandDeck.get(i));
            }
            if (i == 8) {

                ImageView img = findViewById(R.id.id_handdeck_card_8);
                img.setImageResource(imgIdsHandDeck.get(i));
            }
            if (i == 9) {

                ImageView img = findViewById(R.id.id_handdeck_card_9);
                img.setImageResource(imgIdsHandDeck.get(i));
            }

        }


    }

    // sets the pictures of the handDeck to no_card
    public void setHanddeckPicturesToNoCard() {

        ImageView img0 = findViewById(R.id.id_handdeck_card_0);
        img0.setImageResource(R.drawable.ca_no_card);

        ImageView img1 = findViewById(R.id.id_handdeck_card_1);
        img1.setImageResource(R.drawable.ca_no_card);

        ImageView img2 = findViewById(R.id.id_handdeck_card_2);
        img2.setImageResource(R.drawable.ca_no_card);

        ImageView img3 = findViewById(R.id.id_handdeck_card_3);
        img3.setImageResource(R.drawable.ca_no_card);

        ImageView img4 = findViewById(R.id.id_handdeck_card_4);
        img4.setImageResource(R.drawable.ca_no_card);

        ImageView img5 = findViewById(R.id.id_handdeck_card_5);
        img5.setImageResource(R.drawable.ca_no_card);

        ImageView img6 = findViewById(R.id.id_handdeck_card_6);
        img6.setImageResource(R.drawable.ca_no_card);

        ImageView img7 = findViewById(R.id.id_handdeck_card_7);
        img7.setImageResource(R.drawable.ca_no_card);

        ImageView img8 = findViewById(R.id.id_handdeck_card_8);
        img8.setImageResource(R.drawable.ca_no_card);

        ImageView img9 = findViewById(R.id.id_handdeck_card_9);
        img9.setImageResource(R.drawable.ca_no_card);


    }

    // updates the necessary data for showing the selected cards
    public void updateDataForShowingSelection() {
        imgIdsSelection = new ArrayList<>();


        for (int i = 0; i < Game.drawnCards.sizeOfSelection(); i++) {
            String card = Game.drawnCards.getCardFromSelection(i);

            if (card.equals("Alaska")) {
                imgIdsSelection.add(R.drawable.ca_alaska);
            }
            if (card.equals("Greenland")) {
                imgIdsSelection.add(R.drawable.ca_greenland);
            }
            if (card.equals("Canada")) {
                imgIdsSelection.add(R.drawable.ca_canada);
            }
            if (card.equals("Central America")) {
                imgIdsSelection.add(R.drawable.ca_central_amerika);
            }
            if (card.equals("Venezuela")) {
                imgIdsSelection.add(R.drawable.ca_venezuela);
            }
            if (card.equals("Peru")) {
                imgIdsSelection.add(R.drawable.ca_peru);
            }
            if (card.equals("Brazil")) {
                imgIdsSelection.add(R.drawable.ca_brazil);
            }
            if (card.equals("Argentina")) {
                imgIdsSelection.add(R.drawable.ca_argentina);
            }
            if (card.equals("North Africa")) {
                imgIdsSelection.add(R.drawable.ca_north_africa);
            }
            if (card.equals("East Africa")) {
                imgIdsSelection.add(R.drawable.ca_east_africa);
            }
            if (card.equals("Congo")) {
                imgIdsSelection.add(R.drawable.ca_congo);
            }
            if (card.equals("South Africa")) {
                imgIdsSelection.add(R.drawable.ca_south_africa);
            }
            if (card.equals("Scandinavia")) {
                imgIdsSelection.add(R.drawable.ca_scandinavia);
            }
            if (card.equals("Ukraine")) {
                imgIdsSelection.add(R.drawable.ca_ukraine);
            }
            if (card.equals("Western Europe")) {
                imgIdsSelection.add(R.drawable.ca_western_europe);
            }
            if (card.equals("Indonesia")) {
                imgIdsSelection.add(R.drawable.ca_indonesia);
            }
            if (card.equals("Western Australia")) {
                imgIdsSelection.add(R.drawable.ca_western_australia);
            }
            if (card.equals("Eastern Australia")) {
                imgIdsSelection.add(R.drawable.ca_eastern_australia);
            }
            if (card.equals("Siam")) {
                imgIdsSelection.add(R.drawable.ca_siam);
            }
            if (card.equals("India")) {
                imgIdsSelection.add(R.drawable.ca_india);
            }
            if (card.equals("China")) {
                imgIdsSelection.add(R.drawable.ca_china);
            }
            if (card.equals("Mongolia")) {
                imgIdsSelection.add(R.drawable.ca_mongolia);
            }
            if (card.equals("Siberia")) {
                imgIdsSelection.add(R.drawable.ca_siberia);
            }
            if (card.equals("Ural")) {
                imgIdsSelection.add(R.drawable.ca_ural);
            }
            if (card.equals("Middle East")) {
                imgIdsSelection.add(R.drawable.ca_middle_east);
            }
            if (card.equals("USA")) {
                imgIdsSelection.add(R.drawable.ca_usa);
            }
            if (card.equals("Joker1")) {
                imgIdsSelection.add(R.drawable.ca_joker1);
            }
            if (card.equals("Joker2")) {
                imgIdsSelection.add(R.drawable.ca_joker2);
            }
        }

    }

    // shows the selected cards
    public void showSelection() {
        for (int i = 0; i < imgIdsSelection.size(); i++) {
            if (i == 0) {

                ImageView img = findViewById(R.id.id_selected_card_1);
                img.setImageResource(imgIdsSelection.get(i));
            }
            if (i == 1) {

                ImageView img = findViewById(R.id.id_selected_card_2);
                img.setImageResource(imgIdsSelection.get(i));
            }
            if (i == 2) {

                ImageView img = findViewById(R.id.id_selected_card_3);
                img.setImageResource(imgIdsSelection.get(i));
            }
        }

    }


    // clean selection when clicking on clean-button
    public void cleanSelection(View view) {
        Game.drawnCards.deleteAllCardsFromSelection();

        ImageView img1 = findViewById(R.id.id_selected_card_1);
        img1.setImageResource(R.drawable.ca_no_card);

        ImageView img2 = findViewById(R.id.id_selected_card_2);
        img2.setImageResource(R.drawable.ca_no_card);

        ImageView img3 = findViewById(R.id.id_selected_card_3);
        img3.setImageResource(R.drawable.ca_no_card);

    }

    // exchanging cards if possible when clicking on exchange-button
    public void exchangeCards(View view) {

        // correct combination - selection will be exchangend
        if (Game.drawnCards.sizeOfSelection() == 3 && Game.availableCards.checkIfCombinationOfCardsCanBeExchanged(Game.drawnCards.getCardFromSelection(0), Game.drawnCards.getCardFromSelection(1), Game.drawnCards.getCardFromSelection(2))) {

            // Send Server message:
            GameClient.getInstance().sendMessage(new ExchangeMessage(Game.drawnCards.getCardFromSelection(0), Game.drawnCards.getCardFromSelection(1), Game.drawnCards.getCardFromSelection(2), 5));

            // exchange cards
            Game.availableCards.exchangeCards(Game.drawnCards.getCardFromSelection(0), Game.drawnCards.getCardFromSelection(1), Game.drawnCards.getCardFromSelection(2));

            // delete cards from Handdeck
            Game.drawnCards.deleteCardFromHandDeck(Game.drawnCards.getCardFromSelection(0));
            Game.drawnCards.deleteCardFromHandDeck(Game.drawnCards.getCardFromSelection(1));
            Game.drawnCards.deleteCardFromHandDeck(Game.drawnCards.getCardFromSelection(2));

            // delete all cards from selection
            Game.drawnCards.deleteAllCardsFromSelection();

            ImageView img1 = findViewById(R.id.id_selected_card_1);
            img1.setImageResource(R.drawable.ca_no_card);

            ImageView img2 = findViewById(R.id.id_selected_card_2);
            img2.setImageResource(R.drawable.ca_no_card);

            ImageView img3 = findViewById(R.id.id_selected_card_3);
            img3.setImageResource(R.drawable.ca_no_card);

            updateDataForShowingHandDeck();
            showHandDeck();


            // show text in snackbar
            CharSequence snackText = "Selection was exchangend - you've got 5 soldiers";

            View layout = findViewById(R.id.cardscroller);

            Snackbar snackbar = Snackbar.make(layout, snackText, Snackbar.LENGTH_LONG);
            snackbar.show();

        }


        // wrong combination
        else if (Game.drawnCards.sizeOfSelection() == 3 && !Game.availableCards.checkIfCombinationOfCardsCanBeExchanged(Game.drawnCards.getCardFromSelection(0), Game.drawnCards.getCardFromSelection(1), Game.drawnCards.getCardFromSelection(2))) {

            // delete all cards from selection
            Game.drawnCards.deleteAllCardsFromSelection();

            ImageView img1 = findViewById(R.id.id_selected_card_1);
            img1.setImageResource(R.drawable.ca_no_card);

            ImageView img2 = findViewById(R.id.id_selected_card_2);
            img2.setImageResource(R.drawable.ca_no_card);

            ImageView img3 = findViewById(R.id.id_selected_card_3);
            img3.setImageResource(R.drawable.ca_no_card);

            updateDataForShowingHandDeck();
            showHandDeck();


            // show text in snackbar
            CharSequence snackText = "Selection can't be exchangend - try another combination";

            View layout = findViewById(R.id.cardscroller);

            Snackbar snackbar = Snackbar.make(layout, snackText, Snackbar.LENGTH_LONG);
            snackbar.show();

        }


        // show message that at least another card is needed
        else if (Game.drawnCards.sizeOfSelection() < 3) {

            // show text in snackbar
            CharSequence snackText = "Another card is needed";

            View layout = findViewById(R.id.cardscroller);

            Snackbar snackbar = Snackbar.make(layout, snackText, Snackbar.LENGTH_LONG);
            snackbar.show();

        }


    }

    // adding Cards to Selection
    public void addCard0ToSelection(View view) {

        if (Game.drawnCards.size() > 0) {


            Game.drawnCards.addCardToSelection(Game.drawnCards.getCardFromHandDeck(0));
            updateDataForShowingSelection();
            showSelection();


            // show text in snackbar
            CharSequence snackText = Game.drawnCards.getCardFromHandDeck(0) + " was added to selection";

            View layout = findViewById(R.id.cardscroller);

            Snackbar snackbar = Snackbar.make(layout, snackText, Snackbar.LENGTH_LONG);
            snackbar.show();
        }


    }

    public void addCard1ToSelection(View view) {

        if (Game.drawnCards.size() > 1) {
            Game.drawnCards.addCardToSelection(Game.drawnCards.getCardFromHandDeck(1));
            updateDataForShowingSelection();
            showSelection();


            // snackbar
            CharSequence snackText = Game.drawnCards.getCardFromHandDeck(1) + " was added to selection";

            View layout = findViewById(R.id.cardscroller);

            Snackbar snackbar = Snackbar.make(layout, snackText, Snackbar.LENGTH_LONG);
            snackbar.show();
        }


    }

    public void addCard2ToSelection(View view) {

        if (Game.drawnCards.size() > 2) {

            Game.drawnCards.addCardToSelection(Game.drawnCards.getCardFromHandDeck(2));
            updateDataForShowingSelection();
            showSelection();


            // snackbar
            CharSequence snackText = Game.drawnCards.getCardFromHandDeck(2) + " was added to selection";

            View layout = findViewById(R.id.cardscroller);

            Snackbar snackbar = Snackbar.make(layout, snackText, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void addCard3ToSelection(View view) {

        if (Game.drawnCards.size() > 3) {

            Game.drawnCards.addCardToSelection(Game.drawnCards.getCardFromHandDeck(3));
            updateDataForShowingSelection();
            showSelection();


            // snackbar
            CharSequence snackText = Game.drawnCards.getCardFromHandDeck(3) + " was added to selection";

            View layout = findViewById(R.id.cardscroller);

            Snackbar snackbar = Snackbar.make(layout, snackText, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void addCard4ToSelection(View view) {

        if (Game.drawnCards.size() > 4) {
            Game.drawnCards.addCardToSelection(Game.drawnCards.getCardFromHandDeck(4));
            updateDataForShowingSelection();
            showSelection();


            // snackbar
            CharSequence snackText = Game.drawnCards.getCardFromHandDeck(4) + " was added to selection";

            View layout = findViewById(R.id.cardscroller);

            Snackbar snackbar = Snackbar.make(layout, snackText, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void addCard5ToSelection(View view) {

        if (Game.drawnCards.size() > 5) {
            Game.drawnCards.addCardToSelection(Game.drawnCards.getCardFromHandDeck(5));
            updateDataForShowingSelection();
            showSelection();


            // snackbar
            CharSequence snackText = Game.drawnCards.getCardFromHandDeck(5) + " was added to selection";

            View layout = findViewById(R.id.cardscroller);

            Snackbar snackbar = Snackbar.make(layout, snackText, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void addCard6ToSelection(View view) {

        if (Game.drawnCards.size() > 6) {
            Game.drawnCards.addCardToSelection(Game.drawnCards.getCardFromHandDeck(6));
            updateDataForShowingSelection();
            showSelection();


            // snackbar
            CharSequence snackText = Game.drawnCards.getCardFromHandDeck(6) + " was added to selection";

            View layout = findViewById(R.id.cardscroller);

            Snackbar snackbar = Snackbar.make(layout, snackText, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void addCard7ToSelection(View view) {

        if (Game.drawnCards.size() > 7) {
            Game.drawnCards.addCardToSelection(Game.drawnCards.getCardFromHandDeck(7));
            updateDataForShowingSelection();
            showSelection();


            // snackbar
            CharSequence snackText = Game.drawnCards.getCardFromHandDeck(7) + " was added to selection";

            View layout = findViewById(R.id.cardscroller);

            Snackbar snackbar = Snackbar.make(layout, snackText, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void addCard8ToSelection(View view) {

        if (Game.drawnCards.size() > 8) {
            Game.drawnCards.addCardToSelection(Game.drawnCards.getCardFromHandDeck(8));
            updateDataForShowingSelection();
            showSelection();


            // snackbar
            CharSequence snackText = Game.drawnCards.getCardFromHandDeck(8) + " was added to selection";

            View layout = findViewById(R.id.cardscroller);

            Snackbar snackbar = Snackbar.make(layout, snackText, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void addCard9ToSelection(View view) {

        if (Game.drawnCards.size() > 9) {
            Game.drawnCards.addCardToSelection(Game.drawnCards.getCardFromHandDeck(9));
            updateDataForShowingSelection();
            showSelection();


            // snackbar
            CharSequence snackText = Game.drawnCards.getCardFromHandDeck(9) + " was added to selection";

            View layout = findViewById(R.id.cardscroller);

            Snackbar snackbar = Snackbar.make(layout, snackText, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


}