package at.aau.server.dto;

public class LostMessage extends BaseMessage {

    public Integer playerIndex;

    public LostMessage() {

    }

    public LostMessage(Integer playerIndex) {
        this.playerIndex = playerIndex;
    }

}
