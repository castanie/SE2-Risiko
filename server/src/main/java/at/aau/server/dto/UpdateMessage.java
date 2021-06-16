package at.aau.server.dto;

public class UpdateMessage extends BaseMessage{

    public Integer playerIndex;
    public String countryName;
    public Integer countryArmies;

    private String wonString;



    public UpdateMessage() {

    }

    public UpdateMessage(String countryName, Integer countryArmies, Integer playerIndex) {
        this.countryName = countryName;
        this.countryArmies = countryArmies;
        this.playerIndex = playerIndex;
    }
    public UpdateMessage(String countryName, Integer countryArmies, Integer playerIndex, String wonString) {
        this.playerIndex = playerIndex;
        this.countryName = countryName;
        this.countryArmies = countryArmies;
        this.wonString = wonString;
    }

    public String getWonString() {
        return wonString;
    }

}
