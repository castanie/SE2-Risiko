package at.aau.server.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheatedMessageTest {
    private CheatedMessage msg = null;

    @BeforeEach
    void setUp() {
        msg = new CheatedMessage(true, false);
    }

    @Test
    void constructorTest() {
        CheatedMessage message = new CheatedMessage(false, true);
        assertFalse(message.getMessage());
        assertTrue(message.getSenderIsDefender());
    }
    @Test
    void getMessageTest() {
        assertTrue(msg.getMessage());
    }
    @Test
    void getSenderIsDefenderTest() {
        assertFalse(msg.getSenderIsDefender());
    }


    @AfterEach
    void tearDown() {
        msg = null;
    }


}