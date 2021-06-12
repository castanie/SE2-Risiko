package at.aau.server.dto;

public class LogMessage extends BaseMessage {

    public String text;

    public LogMessage() {
    }

    public LogMessage(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format("TextMessage: %s", text);
    }
}
