package at.aau.risiko;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class DiceActivityAttackerTest {
    //for testing an activity a rule is needed
    @Rule
    public ActivityTestRule<DiceActivityAttacker> rule = new ActivityTestRule<DiceActivityAttacker>(DiceActivityAttacker.class);

    private DiceActivityAttacker mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = rule.getActivity();
    }

    @Test
    public void testLaunch() {
        View dice1 = mActivity.findViewById(R.id.diceOneAttack);
        assertNotNull(dice1);
        View dice2 = mActivity.findViewById(R.id.diceTwoAttack);
        assertNotNull(dice2);
        View dice3 = mActivity.findViewById(R.id.diceThreeAttack);
        assertNotNull(dice3);
        View dice4 = mActivity.findViewById(R.id.diceOneDefense);
        assertNotNull(dice4);
        View dice5 = mActivity.findViewById(R.id.diceTwoDefense);
        assertNotNull(dice5);
        View dice6 = mActivity.findViewById(R.id.diceThreeDefense);
        assertNotNull(dice6);
        View attackerLbl = mActivity.findViewById(R.id.attackerLbl);
        assertNotNull(attackerLbl);
        View defenderLbl = mActivity.findViewById(R.id.defenderLbl);
        assertNotNull(defenderLbl);
    }

    @Test
    public void testCheatedButton() {
        onView(withId(R.id.cheatBtn)).perform(click());
        assertTrue(mActivity.isClicked);
    }

    @Test
    public void testIsClicked() {
        assertFalse(mActivity.getIsClicked());
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}