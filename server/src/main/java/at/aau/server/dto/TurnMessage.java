package at.aau.server.dto;

public class TurnMessage extends BaseMessage {

    public int playerIndex;

    public TurnMessage() {

    }

    public TurnMessage(int playerIndex) {
        this.playerIndex = playerIndex;
    }

}
