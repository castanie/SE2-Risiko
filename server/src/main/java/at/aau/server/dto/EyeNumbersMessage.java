package at.aau.server.dto;

/**
 * this message type is used to send an array that contains the eyenumbers of the dices after they have been rolled.
 */
public class EyeNumbersMessage extends BaseMessage{
    int[] eyeNumbers;

    public EyeNumbersMessage(int[] eyeNumbers) {
        this.eyeNumbers = eyeNumbers;
    }

    public int[] getMessage(){
        return eyeNumbers;
    }
}
