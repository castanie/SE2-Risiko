package at.aau.server.dto;

/**
 * this message type is used to send an array that contains the eyenumbers of the dices after they have been rolled.
 */
public class EyeNumbersMessage extends BaseMessage{
    int[] eyeNumbers;
    public boolean isDefender = false;

    public EyeNumbersMessage() {

    }

    //constructor for attacker
    public EyeNumbersMessage(int[] eyeNumbers) {
        this.eyeNumbers = eyeNumbers;
    }

    //constructor for defender
    public EyeNumbersMessage(int[] eyeNumbers, boolean isDefender) {
        this.eyeNumbers = eyeNumbers;
        this.isDefender = isDefender;
    }

    public int[] getMessage(){
        return eyeNumbers;
    }
}