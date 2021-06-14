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

    // getter and setter methods
    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Integer getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(Integer playerIndex) {
        this.playerIndex = playerIndex;
    }

}
