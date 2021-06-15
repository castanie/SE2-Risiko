package at.aau.server.dto;

public class UpdateMessage extends BaseMessage{

    public Integer playerIndex;
    public String countryName;
    public Integer countryArmies;

    public UpdateMessage() {

    }

    public UpdateMessage(String countryName, Integer countryArmies, Integer playerIndex) {
        this.countryName = countryName;
        this.countryArmies = countryArmies;
        this.playerIndex = playerIndex;
    }

}
