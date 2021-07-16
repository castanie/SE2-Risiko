package at.aau.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class DiceTest {
    private Dice diceAtt = null;
    private Dice diceDef = null;
    @BeforeEach
    public void setup() {
        diceAtt = new Dice("attacker");
        diceDef = new Dice("defender");
    }
    @Test
    public void testConstructor() {
        Dice dice = new Dice("attacker");
        assertEquals("attacker", dice.getType());
    }
    @Test
    public void testSetType() {
        diceAtt.setType("defender");
        assertEquals("defender", diceAtt.getType());
    }
    @Test
    public void testGetType() {
        assertEquals("defender", diceDef.getType());
    }
    @Test
    public void testSetEyenumber() {
        diceAtt.setEyeNumber(6);
        assertEquals(6, diceAtt.getEyeNumber());
    }
    @Test
    public void testGetEyenumber() {
        diceDef.setEyeNumber(4);
        assertEquals(4, diceDef.getEyeNumber());
    }
    @Test
    public void testDiceRoll() {
        int eyeNum;
        int lowestEyeNum = 7;
        int hightestEyeNum = 0;

        boolean isCheckOk = false;
        for(int i = 0; i < 100000; i++) {

            eyeNum = diceDef.diceRoll();
            if(eyeNum < 1 || eyeNum > 6) {
                isCheckOk = false;
                fail("incorrect value of dice");
            }
            if(lowestEyeNum > eyeNum) {
                lowestEyeNum = eyeNum;
                isCheckOk = true;
            }
            if(hightestEyeNum < eyeNum) {
                hightestEyeNum = eyeNum;
                isCheckOk = true;
            }

        }
        if(lowestEyeNum == hightestEyeNum){
            isCheckOk = false;
            fail("lowest and highest eyenumber can't be the same");
        }
        if(lowestEyeNum != 1) {
            isCheckOk = false;
            fail("lowest should be 1 after 100000 calls: " + lowestEyeNum);

        }
        if(hightestEyeNum != 6) {
            isCheckOk = false;
            fail("highest should be 6 after 100000 calls: "+ hightestEyeNum);

        }

        assertTrue(isCheckOk);

    }


    @AfterEach
    public void tearDown() {
        diceAtt = null;
        diceDef = null;
    }
}
