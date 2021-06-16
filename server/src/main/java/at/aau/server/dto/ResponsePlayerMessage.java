package at.aau.server.dto;

import java.util.ArrayList;

public class ResponsePlayerMessage extends BaseMessage {
    private ArrayList<String> playerNames;

    public ResponsePlayerMessage() {

    }

    public ResponsePlayerMessage(ArrayList<String>playerNames){
        this.playerNames = playerNames;
    }

    public ArrayList<String>getPlayerNames(){
        return this.playerNames;
    }

    public void setPlayerNames(ArrayList<String>playerNames){
        this.playerNames = playerNames;
    }
}
