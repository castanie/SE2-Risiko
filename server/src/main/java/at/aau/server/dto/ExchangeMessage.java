package at.aau.server.dto;

public class ExchangeMessage extends BaseMessage {

    // Array of three Cards that got exchanged
    public String cardOne;
    public String cardTwo;
    public String cardThree;
    // Identifier of the player
    public Integer playerIndex;

    public ExchangeMessage() {

    }

    public ExchangeMessage(String cardOne, String cardTwo, String cardThree) {
        this.cardOne = cardOne;
        this.cardTwo = cardTwo;
        this.cardThree = cardThree;
    }

}
