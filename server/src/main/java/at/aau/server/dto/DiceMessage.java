package at.aau.server.dto;

public class DiceMessage extends BaseMessage {

    public Integer playerIndex;
    public Integer attackingIndex;
    public String attackingCountryName;
    public String defendingCountryName;
    public Integer numAttackers;
    public Integer numDefenders;

    public DiceMessage() {

    }
    public DiceMessage(Integer playerIndex, Integer attackingIndex, String attackingCountryName, String defendingCountryName, Integer numAttackers, Integer numDefenders) {
        this.playerIndex = playerIndex;
        this.attackingIndex = attackingIndex;
        this.attackingCountryName = attackingCountryName;
        this.defendingCountryName = defendingCountryName;
        this.numAttackers = numAttackers;
        this.numDefenders = numDefenders;
    }



    public DiceMessage(int playerIndex, int attackingIndex) {
        this.playerIndex = playerIndex;
        this.attackingIndex = attackingIndex;
    }

}