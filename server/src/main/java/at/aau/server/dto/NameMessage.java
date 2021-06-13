package at.aau.server.dto;

public class NameMessage extends BaseMessage {

    public String name;

    public NameMessage() {

    }

    public NameMessage(String playerName) {
        this.name = playerName;
    }

}
