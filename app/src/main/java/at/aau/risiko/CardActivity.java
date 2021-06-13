package at.aau.risiko;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import at.aau.core.CardList;
import at.aau.core.HandDeck;
import at.aau.server.dto.ExchangeMessage;
import at.aau.server.kryonet.GameClient;


public class CardActivity extends AppCompatActivity {


    //initialization of necessary variables
    CardList cardDeck;
    HandDeck handDeck;
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

        cardDeck = new CardList();
        handDeck = new HandDeck();
        cardDeck.fillUpCardlistForStart();

        // drawCards();
        updateDataForShowingHandDeck();
        showHandDeck();


    }

    public void drawCards() {

        handDeck.addCardToHandDeck(cardDeck.drawCardFromCardList());
        handDeck.addCardToHandDeck(cardDeck.drawCardFromCardList());
        handDeck.addCardToHandDeck(cardDeck.drawCardFromCardList());
        handDeck.addCardToHandDeck(cardDeck.drawCardFromCardList());
        handDeck.addCardToHandDeck(cardDeck.drawCardFromCardList());
        handDeck.addCardToHandDeck(cardDeck.drawCardFromCardList());


    }


    //updates the necessary data for showing the handdeck
    public void updateDataForShowingHandDeck() {
        imgIdsHandDeck = new ArrayList<>();

        for (int i = 0; i < handDeck.size(); i++) {
            String card = handDeck.getCardFromHandDeck(i);

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

    //shows the cards from Handdeck
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

    //sets the pictures of the handDeck to no_card
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

    //updates the necessary data for showing the selected cards
    public void updateDataForShowingSelection() {
        imgIdsSelection = new ArrayList<>();


        for (int i = 0; i < handDeck.sizeOfSelection(); i++) {
            String card = handDeck.getCardFromSelection(i);

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

    //shows the selected cards
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


    //clean selection when clicking on clean-button
    public void cleanSelection(View view) {
        handDeck.deleteAllCardsFromSelection();

        ImageView img1 = findViewById(R.id.id_selected_card_1);
        img1.setImageResource(R.drawable.ca_no_card);

        ImageView img2 = findViewById(R.id.id_selected_card_2);
        img2.setImageResource(R.drawable.ca_no_card);

        ImageView img3 = findViewById(R.id.id_selected_card_3);
        img3.setImageResource(R.drawable.ca_no_card);

    }

    //exchanging cards if possible when clicking on exchange-button
    public void exchangeCards(View view) {

        //correct combination - selection will be exchangend
        if (handDeck.sizeOfSelection() == 3 && cardDeck.checkIfCombinationOfCardsCanBeExchanged(handDeck.getCardFromSelection(0), handDeck.getCardFromSelection(1), handDeck.getCardFromSelection(2))) {

            // Send Server message:
            GameClient.getInstance().sendMessage(new ExchangeMessage(handDeck.getCardFromSelection(0), handDeck.getCardFromSelection(1), handDeck.getCardFromSelection(2)));

            //exchange cards
            cardDeck.exchangeCards(handDeck.getCardFromSelection(0), handDeck.getCardFromSelection(1), handDeck.getCardFromSelection(2));

            //delete cards from Handdeck
            handDeck.deleteCardFromHandDeck(handDeck.getCardFromSelection(0));
            handDeck.deleteCardFromHandDeck(handDeck.getCardFromSelection(1));
            handDeck.deleteCardFromHandDeck(handDeck.getCardFromSelection(2));

            //delete all cards from selection
            handDeck.deleteAllCardsFromSelection();

            ImageView img1 = findViewById(R.id.id_selected_card_1);
            img1.setImageResource(R.drawable.ca_no_card);

            ImageView img2 = findViewById(R.id.id_selected_card_2);
            img2.setImageResource(R.drawable.ca_no_card);

            ImageView img3 = findViewById(R.id.id_selected_card_3);
            img3.setImageResource(R.drawable.ca_no_card);

            updateDataForShowingHandDeck();
            showHandDeck();


            // Richtigen Context zuweisen
            Context context = getApplicationContext();

            // CharSequence mit Text für den Toast erstellen
            CharSequence toastText = "Selection was exchangend - you've got 4 soldiers";

            // Anzeigedauer festlegen: LENGTH_SHORT oder LENGTH_LONG
            int dauer = Toast.LENGTH_SHORT;

            // Toast erstellen und anzeigen
            Toast toast = Toast.makeText(context, toastText, dauer);
            toast.show();

        }


        //wrong combination
        else if (handDeck.sizeOfSelection() == 3 && !cardDeck.checkIfCombinationOfCardsCanBeExchanged(handDeck.getCardFromSelection(0), handDeck.getCardFromSelection(1), handDeck.getCardFromSelection(2))) {

            //delete all cards from selection
            handDeck.deleteAllCardsFromSelection();

            ImageView img1 = findViewById(R.id.id_selected_card_1);
            img1.setImageResource(R.drawable.ca_no_card);

            ImageView img2 = findViewById(R.id.id_selected_card_2);
            img2.setImageResource(R.drawable.ca_no_card);

            ImageView img3 = findViewById(R.id.id_selected_card_3);
            img3.setImageResource(R.drawable.ca_no_card);

            updateDataForShowingHandDeck();
            showHandDeck();


            // Richtigen Context zuweisen
            Context context = getApplicationContext();

            // CharSequence mit Text für den Toast erstellen
            CharSequence toastText = "Selection can't be exchangend - try another combination";

            // Anzeigedauer festlegen: LENGTH_SHORT oder LENGTH_LONG
            int dauer = Toast.LENGTH_SHORT;

            // Toast erstellen und anzeigen
            Toast toast = Toast.makeText(context, toastText, dauer);
            toast.show();

        }


        // show message that at least another card is needed
        else if (handDeck.sizeOfSelection() < 3) {
            // Richtigen Context zuweisen
            Context context = getApplicationContext();

            // CharSequence mit Text für den Toast erstellen
            CharSequence toastText = "Select another Card";

            // Anzeigedauer festlegen: LENGTH_SHORT oder LENGTH_LONG
            int dauer = Toast.LENGTH_SHORT;

            // Toast erstellen und anzeigen
            Toast toast = Toast.makeText(context, toastText, dauer);
            toast.show();

        }


    }

    //adding Cards to Selection
    public void addCard0ToSelection(View view) {

        if (handDeck.size() > 0) {


            handDeck.addCardToSelection(handDeck.getCardFromHandDeck(0));
            updateDataForShowingSelection();
            showSelection();


            // Richtigen Context zuweisen
            Context context = getApplicationContext();

            // CharSequence mit Text für den Toast erstellen
            CharSequence toastText = handDeck.getCardFromHandDeck(0) + " was added to selection";

            // Anzeigedauer festlegen: LENGTH_SHORT oder LENGTH_LONG
            int dauer = Toast.LENGTH_SHORT;

            // Toast erstellen und anzeigen
            Toast toast = Toast.makeText(context, toastText, dauer);
            toast.show();
        }


    }

    public void addCard1ToSelection(View view) {

        if (handDeck.size() > 1) {
            handDeck.addCardToSelection(handDeck.getCardFromHandDeck(1));
            updateDataForShowingSelection();
            showSelection();


            // Richtigen Context zuweisen
            Context context = getApplicationContext();

            // CharSequence mit Text für den Toast erstellen
            CharSequence toastText = handDeck.getCardFromHandDeck(1) + " was added to selection";

            // Anzeigedauer festlegen: LENGTH_SHORT oder LENGTH_LONG
            int dauer = Toast.LENGTH_SHORT;

            // Toast erstellen und anzeigen
            Toast toast = Toast.makeText(context, toastText, dauer);
            toast.show();
        }


    }

    public void addCard2ToSelection(View view) {

        if (handDeck.size() > 2) {

            handDeck.addCardToSelection(handDeck.getCardFromHandDeck(2));
            updateDataForShowingSelection();
            showSelection();


            // Richtigen Context zuweisen
            Context context = getApplicationContext();

            // CharSequence mit Text für den Toast erstellen
            CharSequence toastText = handDeck.getCardFromHandDeck(2) + " was added to selection";

            // Anzeigedauer festlegen: LENGTH_SHORT oder LENGTH_LONG
            int dauer = Toast.LENGTH_SHORT;

            // Toast erstellen und anzeigen
            Toast toast = Toast.makeText(context, toastText, dauer);
            toast.show();
        }
    }

    public void addCard3ToSelection(View view) {

        if (handDeck.size() > 3) {

            handDeck.addCardToSelection(handDeck.getCardFromHandDeck(3));
            updateDataForShowingSelection();
            showSelection();


            // Richtigen Context zuweisen
            Context context = getApplicationContext();

            // CharSequence mit Text für den Toast erstellen
            CharSequence toastText = handDeck.getCardFromHandDeck(3) + " was added to selection";

            // Anzeigedauer festlegen: LENGTH_SHORT oder LENGTH_LONG
            int dauer = Toast.LENGTH_SHORT;

            // Toast erstellen und anzeigen
            Toast toast = Toast.makeText(context, toastText, dauer);
            toast.show();
        }
    }

    public void addCard4ToSelection(View view) {

        if (handDeck.size() > 4) {
            handDeck.addCardToSelection(handDeck.getCardFromHandDeck(4));
            updateDataForShowingSelection();
            showSelection();


            // Richtigen Context zuweisen
            Context context = getApplicationContext();

            // CharSequence mit Text für den Toast erstellen
            CharSequence toastText = handDeck.getCardFromHandDeck(4) + " was added to selection";

            // Anzeigedauer festlegen: LENGTH_SHORT oder LENGTH_LONG
            int dauer = Toast.LENGTH_SHORT;

            // Toast erstellen und anzeigen
            Toast toast = Toast.makeText(context, toastText, dauer);
            toast.show();
        }
    }

    public void addCard5ToSelection(View view) {

        if (handDeck.size() > 5) {
            handDeck.addCardToSelection(handDeck.getCardFromHandDeck(5));
            updateDataForShowingSelection();
            showSelection();


            // Richtigen Context zuweisen
            Context context = getApplicationContext();

            // CharSequence mit Text für den Toast erstellen
            CharSequence toastText = handDeck.getCardFromHandDeck(5) + " was added to selection";

            // Anzeigedauer festlegen: LENGTH_SHORT oder LENGTH_LONG
            int dauer = Toast.LENGTH_SHORT;

            // Toast erstellen und anzeigen
            Toast toast = Toast.makeText(context, toastText, dauer);
            toast.show();
        }
    }

    public void addCard6ToSelection(View view) {

        if (handDeck.size() > 6) {
            handDeck.addCardToSelection(handDeck.getCardFromHandDeck(6));
            updateDataForShowingSelection();
            showSelection();


            // Richtigen Context zuweisen
            Context context = getApplicationContext();

            // CharSequence mit Text für den Toast erstellen
            CharSequence toastText = handDeck.getCardFromHandDeck(6) + " was added to selection";

            // Anzeigedauer festlegen: LENGTH_SHORT oder LENGTH_LONG
            int dauer = Toast.LENGTH_SHORT;

            // Toast erstellen und anzeigen
            Toast toast = Toast.makeText(context, toastText, dauer);
            toast.show();
        }
    }

    public void addCard7ToSelection(View view) {

        if (handDeck.size() > 7) {
            handDeck.addCardToSelection(handDeck.getCardFromHandDeck(7));
            updateDataForShowingSelection();
            showSelection();


            // Richtigen Context zuweisen
            Context context = getApplicationContext();

            // CharSequence mit Text für den Toast erstellen
            CharSequence toastText = handDeck.getCardFromHandDeck(7) + " was added to selection";

            // Anzeigedauer festlegen: LENGTH_SHORT oder LENGTH_LONG
            int dauer = Toast.LENGTH_SHORT;

            // Toast erstellen und anzeigen
            Toast toast = Toast.makeText(context, toastText, dauer);
            toast.show();
        }
    }

    public void addCard8ToSelection(View view) {

        if (handDeck.size() > 8) {
            handDeck.addCardToSelection(handDeck.getCardFromHandDeck(8));
            updateDataForShowingSelection();
            showSelection();


            // Richtigen Context zuweisen
            Context context = getApplicationContext();

            // CharSequence mit Text für den Toast erstellen
            CharSequence toastText = handDeck.getCardFromHandDeck(8) + " was added to selection";

            // Anzeigedauer festlegen: LENGTH_SHORT oder LENGTH_LONG
            int dauer = Toast.LENGTH_SHORT;

            // Toast erstellen und anzeigen
            Toast toast = Toast.makeText(context, toastText, dauer);
            toast.show();
        }
    }

    public void addCard9ToSelection(View view) {

        if (handDeck.size() > 9) {
            handDeck.addCardToSelection(handDeck.getCardFromHandDeck(9));
            updateDataForShowingSelection();
            showSelection();


            // Richtigen Context zuweisen
            Context context = getApplicationContext();

            // CharSequence mit Text für den Toast erstellen
            CharSequence toastText = handDeck.getCardFromHandDeck(9) + " was added to selection";

            // Anzeigedauer festlegen: LENGTH_SHORT oder LENGTH_LONG
            int dauer = Toast.LENGTH_SHORT;

            // Toast erstellen und anzeigen
            Toast toast = Toast.makeText(context, toastText, dauer);
            toast.show();
        }
    }


}