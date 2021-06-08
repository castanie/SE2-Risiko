package at.aau.server.dto;

public class StartMessage extends BaseMessage {

    // Should contain a Player[].
    public String[] names;
    public Integer[] colors;
    public boolean setup;

    public StartMessage() {

    }

}
