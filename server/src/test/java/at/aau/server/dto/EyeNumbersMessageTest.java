package at.aau.server.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EyeNumbersMessageTest {

    EyeNumbersMessage eyeNumMsgAtt = null;
    EyeNumbersMessage eyeNumMsgDef = null;
    @BeforeEach
    public void setUp() {
        eyeNumMsgAtt = new EyeNumbersMessage(new int[]{5, 4, 3});
        eyeNumMsgDef = new EyeNumbersMessage(new int[]{6, 5, 4}, true);
    }

    @Test
    void attackerConstructorTest() {
        EyeNumbersMessage msg = new EyeNumbersMessage(new int[]{3, 3, 3});
        for(int i = 0; i < 3; i++) {
            assertEquals(3, msg.getMessage()[i]);
        }
        assertFalse(msg.getIsDefender());
    }
    @Test
    void defenderConstructorTest() {
        EyeNumbersMessage msg = new EyeNumbersMessage(new int[]{3, 3, 3}, true);
        for (int i = 0; i < 3; i++) {
            assertEquals(3, msg.getMessage()[i]);
        }
        assertTrue(msg.getIsDefender());
    }
    @Test
    void testGetMessage() {
        for (int i = 0; i < 3; i++) {
            assertEquals(5-i, eyeNumMsgAtt.getMessage()[i]);
        }
    }
    @Test
    void testGetIsDefender() {
        assertTrue(eyeNumMsgDef.getIsDefender());
    }


    @AfterEach
    public void tearDown() {
        eyeNumMsgAtt = null;
        eyeNumMsgDef = null;
    }

}