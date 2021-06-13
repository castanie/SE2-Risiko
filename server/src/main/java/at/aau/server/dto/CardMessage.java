package at.aau.server.dto;

public class CardMessage extends BaseMessage {

    // Card that was drawn
    String cardName;
    // Identifier of the player
    Integer playerIndex;

    public CardMessage() {

    }

}
