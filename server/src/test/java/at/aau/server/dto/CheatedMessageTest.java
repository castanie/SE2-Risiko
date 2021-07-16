package at.aau.server.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheatedMessageTest {
    private CheatedMessage msg = null;

    @BeforeEach
    public void setUp() {
        msg = new CheatedMessage(true, false);
    }

    @Test
    public void constructorTest() {
        CheatedMessage message = new CheatedMessage(false, true);
        assertFalse(message.getMessage());
        assertTrue(message.getSenderIsDefender());
    }
    @Test
    public void getMessageTest() {
        assertTrue(msg.getMessage());
    }
    @Test
    public void getSenderIsDefenderTest() {
        assertFalse(msg.getSenderIsDefender());
    }


    @AfterEach
    public void tearDown() {
        msg = null;
    }


}