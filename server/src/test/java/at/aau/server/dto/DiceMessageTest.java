package at.aau.server.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceMessageTest {


    @Test
    public void constructorTest() {
        DiceMessage diceMsg = new DiceMessage(1, 2, "Scandinavia", "Greenland", 3,3);
        assertEquals(1, diceMsg.playerIndex);
        assertEquals(2, diceMsg.defendingIndex);
        assertEquals("Scandinavia", diceMsg.attackingCountryName);
        assertEquals("Greenland", diceMsg.defendingCountryName);
        assertEquals(3, diceMsg.numAttackers);
        assertEquals(3, diceMsg.numDefenders);
    }



}