package at.aau.server.dto;

public class DiceMessage extends BaseMessage {

    public Integer playerIndex;

    public DiceMessage() {
        
    }

    public DiceMessage(int playerIndex) {
        this.playerIndex = playerIndex;
    }
    
}
