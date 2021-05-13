package at.aau.risiko.cards;

/*Diese Klasse repräsentiert die Spielkarten. In der Variable cardName wird der Name des Landes gespeichert, zu dem die
jeweilige Karte zugeordnet ist. In der Variable cardType wird entweder "Artillerie", "Kavallerie", "Infanterie" oder
"Joker" gespeichert.
Dies dient dazu um die Karten eintauschen zu können. Im boolean cardIsDrawn wird hinterlegt, ob die jeweilige Karte
im Spiel bereits gezogen wurde.*/

public class Card {

    String cardName;
    String cardType;
    boolean cardIsdrawn;


    //Constructor für den Datentyp Card
    public Card(String cardName, String cardType, boolean cardIsdrawn) {
        this.cardName = cardName;
        this.cardType = cardType;
        this.cardIsdrawn = cardIsdrawn;
    }


    //Getter und Setter für die Klasse Card
    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public boolean isCardIsdrawn() {
        return cardIsdrawn;
    }

    public void setCardIsdrawn(boolean cardIsdrawn) {
        this.cardIsdrawn = cardIsdrawn;
    }
}