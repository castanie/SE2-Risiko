package at.aau.server.dto;

public class ExchangeMessage extends BaseMessage {

    // Array of three Cards that got exchanged
    public String cardOne;
    public String cardTwo;
    public String cardThree;
    // Identifier of the player
    public Integer playerIndex;
    // Armies received:
    public Integer bonusArmies;

    public ExchangeMessage() {

    }

    public ExchangeMessage(String cardOne, String cardTwo, String cardThree, Integer bonusArmies) {
        this.cardOne = cardOne;
        this.cardTwo = cardTwo;
        this.cardThree = cardThree;

        this.bonusArmies = bonusArmies;
    }

    //getter and setter methods
    public String getCardOne() {
        return cardOne;
    }

    public void setCardOne(String cardOne) {
        this.cardOne = cardOne;
    }

    public String getCardTwo() {
        return cardTwo;
    }

    public void setCardTwo(String cardTwo) {
        this.cardTwo = cardTwo;
    }

    public String getCardThree() {
        return cardThree;
    }

    public void setCardThree(String cardThree) {
        this.cardThree = cardThree;
    }

    public Integer getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(Integer playerIndex) {
        this.playerIndex = playerIndex;
    }

    public Integer getBonusArmies() {
        return bonusArmies;
    }

    public void setBonusArmies(Integer bonusArmies) {
        this.bonusArmies = bonusArmies;
    }

}
