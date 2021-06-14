package at.aau.server.dto;

public class CardMessage extends BaseMessage {

    // Card that was drawn
    public String cardName;
    // Identifier of the player
    public Integer playerIndex;

    public CardMessage() {

    }

    public CardMessage(String cardName) {
        this.cardName = cardName;
    }

}
