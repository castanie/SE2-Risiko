package at.aau.server.dto;

/**
 * This message will be send from one of the two DiceActivities if cheating was detected by the other player
 * sender is one of the two DiceActivities reciever is the AttackState. The AttackState should choose the player
 * that has not cheated as a winner
 */
public class CheatedMessage extends BaseMessage{
    /**
     * The content of this message is not of interest it is only of interest if this message was sent or not
     * so I'll set some random content
     */

    boolean goodGuess = false;
    boolean senderIsDefender = false;

    public CheatedMessage() {
    }
    public CheatedMessage (boolean goodGuess ,boolean senderIsDefender) {
        this.goodGuess = goodGuess;
        this.senderIsDefender = senderIsDefender;
    }

    public boolean getMessage () {
        return goodGuess;
    }

    public boolean getSenderIsDefender() {
        return senderIsDefender;
    }
}