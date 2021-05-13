package at.aau.risiko.cards;

import java.util.ArrayList;
import java.util.Random;

public class CardService {

    ArrayList<Card> cardDeck = initCardListForStart();

    public static ArrayList<Card> initCardListForStart() {

       ArrayList<Card> cardList = CardList.createCardList();
       return cardList;


    }



    public String drawCardFromCardList(){

        String drawnCard = "";
        boolean succes =false;

        while(!succes) {

            int bound = cardDeck.size();
            Random rand = new Random();
            int randomInt = rand.nextInt(bound);
            if(!cardDeck.get(randomInt).isCardIsdrawn()){
                drawnCard = cardDeck.get(randomInt).cardName;
                cardDeck.get(randomInt).setCardIsdrawn(true);
                succes=true;
            }

        }

        return drawnCard;
    }



}