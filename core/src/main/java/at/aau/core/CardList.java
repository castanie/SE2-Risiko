package at.aau.core;

import java.util.ArrayList;
import java.util.Random;

public class CardList {


    private ArrayList<Card> cardList = new ArrayList<>();
    Random rand = new Random();


    public CardList() {

    }

    public void fillUpCardlistForStart() {
        Card alaska = new Card("Alaska", "infantry", false, false);
        Card greenland = new Card("Greenland", "cavalry", false, false);
        Card canada = new Card("Canada", "artillery", false, false);
        Card centralAmerica = new Card("Central America", "infantry", false, false);
        Card venezuela = new Card("Venezuela", "cavalry", false, false);
        Card peru = new Card("Peru", "artillery", false, false);
        Card brazil = new Card("Brazil", "infantry", false, false);
        Card argentina = new Card("Argentina", "cavalry", false, false);
        Card northAfrica = new Card("North Africa", "artillery", false, false);
        Card eastAfrica = new Card("East Africa", "infantry", false, false);
        Card congo = new Card("Congo", "cavalry", false, false);
        Card southAfrica = new Card("South Africa", "artillery", false, false);
        Card scandinavia = new Card("Scandinavia", "infantry", false, false);
        Card ukraine = new Card("Ukraine", "cavalry", false, false);
        Card westernEurope = new Card("Western Europe", "infantry", false, false);
        Card indonesia = new Card("Indonesia", "cavalry", false, false);
        Card westernAustralia = new Card("Western Australia", "artillery", false, false);
        Card easternAustralia = new Card("Eastern Australia", "infantry", false, false);
        Card siam = new Card("Siam", "cavalry", false, false);
        Card india = new Card("India", "artillery", false, false);
        Card china = new Card("China", "infantry", false, false);
        Card mongolia = new Card("Mongolia", "cavalry", false, false);
        Card siberia = new Card("Siberia", "artillery", false, false);
        Card ural = new Card("Ural", "infantry", false, false);
        Card middleEast = new Card("Middle East", "cavalry", false, false);
        Card usa = new Card("USA", "artillery", false, false);


        Card joker1 = new Card("Joker1", "joker", false, false);
        Card joker2 = new Card("Joker2", "joker", false, false);


        this.cardList.add(alaska);
        this.cardList.add(greenland);
        this.cardList.add(canada);
        this.cardList.add(centralAmerica);
        this.cardList.add(venezuela);
        this.cardList.add(peru);
        this.cardList.add(brazil);
        this.cardList.add(argentina);
        this.cardList.add(northAfrica);
        this.cardList.add(eastAfrica);
        this.cardList.add(congo);
        this.cardList.add(southAfrica);
        this.cardList.add(scandinavia);
        this.cardList.add(ukraine);
        this.cardList.add(westernEurope);
        this.cardList.add(indonesia);
        this.cardList.add(westernAustralia);
        this.cardList.add(easternAustralia);
        this.cardList.add(siam);
        this.cardList.add(india);
        this.cardList.add(china);
        this.cardList.add(mongolia);
        this.cardList.add(siberia);
        this.cardList.add(ural);
        this.cardList.add(middleEast);
        this.cardList.add(usa);
        this.cardList.add(joker1);
        this.cardList.add(joker2);

        //for debugging---------------
        System.out.println("_____________Cardlist after filling up_______________");
        writeCardListInConsole();
        //----------------------------

    }

    public int size() {
        return this.cardList.size();
    }

    public Card get(int i) {
        return this.cardList.get(i);
    }

    public String drawCardFromCardList() {

        String drawnCard = "";
        boolean success = false;

        if (checkIfCardsAvailable()) {

            while (!success) {
                int bound = this.cardList.size();
                int randomInt = rand.nextInt(bound);
                if (!this.cardList.get(randomInt).isCardIsdrawn()) {
                    drawnCard = this.cardList.get(randomInt).getCardName();
                    this.cardList.get(randomInt).setCardIsdrawn(true);
                    success = true;
                }

            }
        } else {
            drawnCard = "No Cards available!";

        }

        //for debugging---------------
        System.out.println("_____________Cardlist after a card was drawn_______________");
        System.out.println(drawnCard + " was drawn");
        writeCardListInConsole();
        //-----------------------------

        return drawnCard;
    }

    /**
     * This method sets a card in the list as drawn; it does not actually remove the card data.
     * @param cardName
     */
    public void removeCardFromCardsList(String cardName) {
        // this.cardList.get(cardIndex).setCardIsdrawn(true);
        for (Card c : cardList) {
            if (c.getCardName().equals(cardName)) {
                c.setCardIsdrawn(true);
                break;
            }
        }
    }

    public String returnCardTypeFoundByName(String s) {

        String c = "Card can't be found";

        for (int i = 0; i < this.cardList.size(); i++) {

            if (this.cardList.get(i).getCardName().equals(s)) {
                return this.cardList.get(i).getCardType();
            }
        }
        return c;
    }


    public boolean checkIfCardsAvailable() {

        boolean cardsAvailable = true;
        int availableCards = 0;

        for (int i = 0; i < this.cardList.size(); i++) {
            if (!this.cardList.get(i).isCardIsdrawn()) {
                availableCards++;
            }
        }

        if (availableCards == 0) {
            cardsAvailable = false;
        }

        return cardsAvailable;
    }


    public boolean checkIfCombinationOfCardsCanBeExchanged(String a, String b, String c) {

        boolean allowed = false;
        String typeA = returnCardTypeFoundByName(a);
        String typeB = returnCardTypeFoundByName(b);
        String typeC = returnCardTypeFoundByName(c);


        //for debugging---------------
        System.out.println("_____________following cards are being checked for exchange_______________");
        System.out.println(a + " " + typeA);
        System.out.println(b + " " + typeB);
        System.out.println(c + " " + typeC);

        //----------------------------


        //dreimal das gleiche Symbol
        if (typeA.equals(typeB) && typeA.equals(typeC) && typeB.equals(typeC)) {
            allowed = true;
        }
        //drei unterschiedliche Symbole, aber kein Joker
        if (!typeA.equals(typeB) && !typeA.equals(typeC) && !typeB.equals(typeC) && !typeA.equals("joker") && !typeB.equals("joker") && !typeC.equals("joker")) {
            allowed = true;
        }

        //Erste Karte Joker, die anderen beiden gleich
        if (typeA.equals("joker") && typeB.equals(typeC)) {
            allowed = true;
        }

        //Zweite Karte Joker, die anderen beiden gleich
        if (typeB.equals("joker") && typeA.equals(typeC)) {
            allowed = true;
        }

        //Dritte Karte Joker, die anderen beiden gleich
        if (typeC.equals("joker") && typeA.equals(typeB)) {
            allowed = true;
        }

        //gleiche Karte
        if (a.equals(b) || b.equals(c) || a.equals(c)) {
            allowed = false;
        }

        //for debugging---------------------------------------
        if (allowed) {

            System.out.println("_____________following cards has been accepted for exchange_______________");
            System.out.println(a + " " + typeA);
            System.out.println(b + " " + typeB);
            System.out.println(c + " " + typeC);
        } else {
            System.out.println("_____________following cards has been declined for exchange_______________");
            System.out.println(a + " " + typeA);
            System.out.println(b + " " + typeB);
            System.out.println(c + " " + typeC);

        }
        //------------------------------------------------------


        return allowed;

    }

    public void exchangeCards(String a, String b, String c) {

        String cardnameA = a;
        String cardnameB = b;
        String cardnameC = c;


        for (int i = 0; i < this.cardList.size(); i++) {

            if (cardnameA.equals(this.cardList.get(i).getCardName())) {

                this.cardList.get(i).setCardIsExchanged(true);
            }

            if (cardnameB.equals(this.cardList.get(i).getCardName())) {

                this.cardList.get(i).setCardIsExchanged(true);
            }

            if (cardnameC.equals(this.cardList.get(i).getCardName())) {

                this.cardList.get(i).setCardIsExchanged(true);
            }


        }


        //for debugging-----------------------------------------
        System.out.println("-------------Following cards were exchangend------------------");
        System.out.println(cardnameA);
        System.out.println(cardnameB);
        System.out.println(cardnameC);

        writeCardListInConsole();

        //------------------------------------------------------


    }

    //for debugging
    public void writeCardListInConsole() {
        System.out.println("---------show Cardnames and if they are drawn and if they were exchangend----------");
        for (int i = 0; i < cardList.size(); i++) {

            System.out.println("CardName: " + cardList.get(i).getCardName() + " ||isDrawn: " + cardList.get(i).cardIsdrawn + " ||isExchanged: " + cardList.get(i).cardIsExchanged);
        }


    }


}
