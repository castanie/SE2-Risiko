package at.aau.server.dto;

public class UpdateMessage extends BaseMessage{

    public String playerName;
    public String countryName;
    public Integer armies;

    public UpdateMessage() {

    }

    public UpdateMessage(String playerName, String countryName, Integer armies) {
        this.playerName = playerName;
        this.countryName = countryName;
        this.armies = armies;
    }

}
