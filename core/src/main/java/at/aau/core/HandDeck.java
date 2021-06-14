package at.aau.core;

import java.util.ArrayList;

public class HandDeck {

    private ArrayList<String> handDeck = new ArrayList<>();
    private ArrayList<String> selectionForExchange = new ArrayList<>();

    public HandDeck() {
        // this.handDeck = handDeck;
        // this.selectionForExchange = selectionForExchange;
    }

    public HandDeck(String[] cardNames) {
        for (String s : cardNames) {
            this.handDeck.add(s);
        }
        // this.selectionForExchange = selectionForExchange;
    }


    // Get String[] for passing from MapActivity to CardActivity:
    public String[] getCardNames() {
        return handDeck.toArray(new String[0]);
    }


    public String getCardFromHandDeck(int i) {
        return this.handDeck.get(i);
    }

    public void addCardToHandDeck(String s) {
        this.handDeck.add(s);

    }


    public void deleteCardFromHandDeck(String s) {
        for (int i = 0; i < handDeck.size(); i++) {
            if (s.equals(handDeck.get(i))) {
                handDeck.remove(i);
            }
        }

        //for debugging---------------------------------

        System.out.println("--------------------------------");
        System.out.println(s + " was deleted from Handdeck");
        writeCardListInConsole();
        //----------------------------------------------

    }


    public int size() {
        return this.handDeck.size();
    }

    public void addCardToSelection(String s) {
        this.selectionForExchange.add(s);
    }

    public String getCardFromSelection(int i) {
        return this.selectionForExchange.get(i);
    }

    public int sizeOfSelection() {
        return this.selectionForExchange.size();
    }

    public void deleteCardFromSelection(String s) {
        for (int i = 0; i < selectionForExchange.size(); i++) {
            if (s.equals(selectionForExchange.get(i))) {
                selectionForExchange.remove(i);
            }
        }

    }

    public void deleteAllCardsFromSelection() {
        selectionForExchange.clear();


    }

    public boolean displaySelectionForExchange() {

        System.out.println("---------display Selection----------");

        for (int i = 0; i < selectionForExchange.size(); i++) {
            System.out.println(selectionForExchange.get(i));


        }
        return true;

    }


    //for debugging
    public void writeCardListInConsole() {
        System.out.println("---------actual handdeck----------");
        for (int i = 0; i < handDeck.size(); i++) {

            System.out.println("CardName: " + handDeck.get(i));
        }


    }


}
