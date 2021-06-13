package at.aau.server.dto;

public class TurnMessage extends BaseMessage {

    public int playerIndex;
    public boolean isCurrentPlayer;

    public TurnMessage() {

    }

    public TurnMessage(int playerIndex, boolean isCurrentPlayer) {
        this.playerIndex = playerIndex;
        this.isCurrentPlayer = isCurrentPlayer;
    }

}
