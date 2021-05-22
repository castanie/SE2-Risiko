package at.aau.risiko.cards;

import java.util.ArrayList;

public class HandDeck {

    ArrayList<String> handDeck = new ArrayList<>();
    ArrayList<String> selectionForExchange = new ArrayList<>();

    public HandDeck() {
        this.handDeck = handDeck;
        this.selectionForExchange = selectionForExchange;
    }



    public String getCardFromHandDeck(int i){
        return this.handDeck.get(i);
    }

    public void addCardToHandDeck(String s){
        this.handDeck.add(s);

    }

    public void deleteCardFromHandDeck(String s){
        for(int i=0; i<handDeck.size(); i++){
            if(s.equals(handDeck.get(i))){
                handDeck.remove(i);
            }
        }

    }

    public int size() {
        return this.handDeck.size();
    }

    public void addCardToSelection(String s){
        this.selectionForExchange.add(s);
    }

    public void deleteCardFromSelection(String s){
        for(int i=0;i<selectionForExchange.size();i++){
            if(s.equals(selectionForExchange.get(i))){
                selectionForExchange.remove(i);
            }
        }

    }

    public boolean displaySelectionForExchange(){

        System.out.println("---------display Selection----------");

        for(int i=0;i<selectionForExchange.size();i++){
            System.out.println(selectionForExchange.get(i));


        }
        return true;

    }

}