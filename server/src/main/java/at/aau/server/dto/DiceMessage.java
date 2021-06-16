package at.aau.server.dto;

public class DiceMessage extends BaseMessage {

    public Integer playerIndex;
    public Integer defendingIndex;
    public String attackingCountryName;
    public String defendingCountryName;
    public Integer numAttackers;
    public Integer numDefenders;

    public DiceMessage() {

    }

    public DiceMessage(Integer playerIndex, Integer defendingIndex, String attackingCountryName, String defendingCountryName, Integer numAttackers, Integer numDefenders) {
        this.playerIndex = playerIndex;
        this.defendingIndex = defendingIndex;
        this.attackingCountryName = attackingCountryName;
        this.defendingCountryName = defendingCountryName;
        this.numAttackers = numAttackers;
        this.numDefenders = numDefenders;
    }

}