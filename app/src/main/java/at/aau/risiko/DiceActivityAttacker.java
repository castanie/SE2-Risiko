package at.aau.risiko;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import at.aau.core.Dice;
import at.aau.server.dto.BaseMessage;
import at.aau.server.dto.CheatedMessage;
import at.aau.server.dto.CloseDiceActivitiesMessage;
import at.aau.server.dto.EyeNumbersMessage;
import at.aau.server.kryonet.Callback;
import at.aau.server.kryonet.GameClient;

public class DiceActivityAttacker extends AppCompatActivity implements SensorEventListener {
    /*these variables are needed to track the sensor event*/
    private SensorManager sensorManager;
    private Sensor accelerometer;

    /*UI Variables*/
    private ImageView diceOneAttack;
    private ImageView diceTwoAttack;
    private ImageView diceThreeAttack;
    /*these variables are only for updating the UI with the according to the server message*/
    private ImageView diceOneDefense;
    private ImageView diceTwoDefense;
    private ImageView diceThreeDefense;

    /*This variables are needed to set the exact amount of dices needed*/
    int numAttackers = 3;
    int numDefenders = 3;
    /*This boolean needs to be set to true after the dices have been rolled to send to the Defender so the UI can be updated*/
    boolean isShaken = false;
    /*if hasRolledDefender == true && isUpdatedGUI == true the Attacker is allowed to roll the dices*/
    boolean hasRolledDefender = false;
    boolean isUpdatedGUI = false;

    //dice should only be rolled if acceleration is > SHAKE_THRESHOLD
    final static int SHAKE_THRESHOLD = 1;

    /*this array will be send to DiceActivityDefender*/
    int[] eyeNumbersAttacker;
    int[] defendersDices;

    boolean opponentCheated = false;
    boolean opponentNotCheated = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        diceOneAttack = findViewById(R.id.diceOneAttack);
        diceTwoAttack = findViewById(R.id.diceTwoAttack);
        diceThreeAttack = findViewById(R.id.diceThreeAttack);

        diceOneDefense = findViewById(R.id.diceOneDefense);
        diceTwoDefense = findViewById(R.id.diceTwoDefense);
        diceThreeDefense = findViewById(R.id.diceThreeDefense);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        TextView attackerLbl = findViewById(R.id.attackerLbl);
        TextView defenderLbl = findViewById(R.id.defenderLbl);

        defenderLbl.setTextSize(20);

        //set dices to random start values
        for (int i = 0; i < numAttackers; i++) {
            setImageViewAttacker(i + 2, i + 1);
        }
        for (int j = 0; j < numDefenders; j++) {
            updateGUI(j + 1, j + 2);
        }


        GameClient.getInstance().registerCallback(new Callback<BaseMessage>() {
            @Override
            public void callback(BaseMessage argument) {
                if (argument instanceof EyeNumbersMessage) {
                    Log.i("DICE ATTACKER", "Received EyeNumbersMessage!");
                    setDefendersDices(((EyeNumbersMessage) argument).getMessage());

                    for (int i = 0; i < numDefenders; i++) {
                        Log.i("DICE ATTACKER", String.valueOf(defendersDices[i]));
                        updateGUI(i + 1, defendersDices[i]);
                    }
                    isUpdatedGUI = true;

                    hasRolledDefender = true;

                    if (defendersDices[numDefenders] == 1) {
                        opponentCheated = true;
                    } else {
                        opponentNotCheated = true;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            defenderLbl.setTextSize(14);
                            attackerLbl.setTextSize(20);
                        }
                    });

                } else if (argument instanceof CloseDiceActivitiesMessage) {
                    Log.i("DICE ATTACKER", "Received CloseDiceActivitiesMessage!");
                    finish();
                }
            }
        });


        Button cheatedBtn = findViewById(R.id.cheatBtn);
        cheatedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (opponentCheated) {
                    Log.i("DEFENDER CHEATED", "Right!");
                    Toast toast = Toast.makeText(getApplicationContext(), "You are right, you've won the duel Sherlock.", Toast.LENGTH_LONG);
                    toast.show();
                    GameClient.getInstance().sendMessage(new CheatedMessage(true, false));
                }
                if (opponentNotCheated) {
                    Log.i("DEFENDER CHEATED", "Wrong!");
                    Toast toast = Toast.makeText(getApplicationContext(), "You are wrong, you've lost the duel.", Toast.LENGTH_LONG);
                    toast.show();
                    GameClient.getInstance().sendMessage(new CheatedMessage(false, false));
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        /*only allow to roll dice if the defender has rolled his*/
        //variables for tracking the motion of the device on x-, y- and z-axis
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        //calculate the movement value
        double accelerationValue = Math.sqrt((x * x + y * y + z * z)) - SensorManager.GRAVITY_EARTH;
        //if accelerationValue > SHAKE_THRESHOLD call rollDice
        Dice dice = new Dice("attacker");
        //array gets size numAttackers+1 because the last entry will be kind of a reference bit if the player
        //has cheated that element is 1 else it's 0
        eyeNumbersAttacker = new int[numAttackers + 1];
        if (!isShaken && hasRolledDefender && isUpdatedGUI) {

            if (accelerationValue > SHAKE_THRESHOLD && accelerationValue <= 3) {

                for (int i = 0; i < numAttackers; i++) {
                    int num = dice.diceRoll();
                    eyeNumbersAttacker[i] = num;
                    dice.setEyeNumber(num);

                    setImageViewAttacker(num, i + 1);

                }
                //player has not cheated
                eyeNumbersAttacker[numAttackers] = 0;
                isShaken = true;
                Log.i("DiceActivity", "Device was shaken, legit.");

            }
            //cheat function
            else if (accelerationValue > 3) {
                dice.setEyeNumber(6);
                for (int index = 0; index < numAttackers; index++) {
                    setImageViewAttacker(6, index + 1);
                    eyeNumbersAttacker[index] = 6;
                }
                //player has cheated
                eyeNumbersAttacker[numAttackers] = 1;
                isShaken = true;
                Log.i("DiceActivityAttacker", "Device was shaken - cheated.");
            }

            if (isShaken) {
                GameClient.getInstance().sendMessage(new EyeNumbersMessage(eyeNumbersAttacker));
            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void setImageViewAttacker(int num, int index) {
        if (index == 1) {
            switch (num) {
                case 1:
                    diceOneAttack.setImageResource(R.drawable.diceredone);
                    break;
                case 2:
                    diceOneAttack.setImageResource(R.drawable.diceredtwo);
                    break;
                case 3:
                    diceOneAttack.setImageResource(R.drawable.diceredthree);
                    break;
                case 4:
                    diceOneAttack.setImageResource(R.drawable.diceredfour);
                    break;
                case 5:
                    diceOneAttack.setImageResource(R.drawable.diceredfive);
                    break;
                case 6:
                    diceOneAttack.setImageResource(R.drawable.diceredsix);
                    break;
                default:
                    break;

            }

        } else if (index == 2) {
            switch (num) {
                case 1:
                    diceTwoAttack.setImageResource(R.drawable.diceredone);
                    break;
                case 2:
                    diceTwoAttack.setImageResource(R.drawable.diceredtwo);
                    break;
                case 3:
                    diceTwoAttack.setImageResource(R.drawable.diceredthree);
                    break;
                case 4:
                    diceTwoAttack.setImageResource(R.drawable.diceredfour);
                    break;
                case 5:
                    diceTwoAttack.setImageResource(R.drawable.diceredfive);
                    break;
                case 6:
                    diceTwoAttack.setImageResource(R.drawable.diceredsix);
                    break;
                default:
                    break;

            }

        } else if (index == 3) {
            switch (num) {
                case 1:
                    diceThreeAttack.setImageResource(R.drawable.diceredone);
                    break;
                case 2:
                    diceThreeAttack.setImageResource(R.drawable.diceredtwo);
                    break;
                case 3:
                    diceThreeAttack.setImageResource(R.drawable.diceredthree);
                    break;
                case 4:
                    diceThreeAttack.setImageResource(R.drawable.diceredfour);
                    break;
                case 5:
                    diceThreeAttack.setImageResource(R.drawable.diceredfive);
                    break;
                case 6:
                    diceThreeAttack.setImageResource(R.drawable.diceredsix);
                    break;
                default:
                    break;
            }

        }
        rotateDice(index);
    }


    private void setDefendersDices(int[] arr) {
        defendersDices = arr;
    }

    private void updateGUI(int index, int num) {
        if (index == 1) {
            switch (num) {
                case 1:
                    diceOneDefense.setImageResource(R.drawable.diceblueone);
                    break;
                case 2:
                    diceOneDefense.setImageResource(R.drawable.dicebluetwo);
                    break;
                case 3:
                    diceOneDefense.setImageResource(R.drawable.dicebluethree);
                    break;
                case 4:
                    diceOneDefense.setImageResource(R.drawable.dicebluefour);
                    break;
                case 5:
                    diceOneDefense.setImageResource(R.drawable.dicebluefive);
                    break;
                case 6:
                    diceOneDefense.setImageResource(R.drawable.dicebluesix);
                    break;
                default:
                    break;

            }

        } else if (index == 2) {
            switch (num) {
                case 1:
                    diceTwoDefense.setImageResource(R.drawable.diceblueone);
                    break;
                case 2:
                    diceTwoDefense.setImageResource(R.drawable.dicebluetwo);
                    break;
                case 3:
                    diceTwoDefense.setImageResource(R.drawable.dicebluethree);
                    break;
                case 4:
                    diceTwoDefense.setImageResource(R.drawable.dicebluefour);
                    break;
                case 5:
                    diceTwoDefense.setImageResource(R.drawable.dicebluefive);
                    break;
                case 6:
                    diceTwoDefense.setImageResource(R.drawable.dicebluesix);
                    break;
                default:
                    break;

            }
        } else if (index == 3) {
            switch (num) {
                case 1:
                    diceThreeDefense.setImageResource(R.drawable.diceblueone);
                    break;
                case 2:
                    diceThreeDefense.setImageResource(R.drawable.dicebluetwo);
                    break;
                case 3:
                    diceThreeDefense.setImageResource(R.drawable.dicebluethree);
                    break;
                case 4:
                    diceThreeDefense.setImageResource(R.drawable.dicebluefour);
                    break;
                case 5:
                    diceThreeDefense.setImageResource(R.drawable.dicebluefive);
                    break;
                case 6:
                    diceThreeDefense.setImageResource(R.drawable.dicebluesix);
                    break;
                default:
                    break;

            }
        }
        rotateDiceDefender(index);
    }


    private void rotateDice(int index) {
        Animation rollAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        if (index == 1) {
            diceOneAttack.startAnimation(rollAnimation);
        } else if (index == 2) {
            diceTwoAttack.startAnimation(rollAnimation);
        } else if (index == 3) {
            diceThreeAttack.startAnimation(rollAnimation);
        }
    }

    private void rotateDiceDefender(int index) {
        Animation rollAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        if (index == 1) {
            diceOneDefense.startAnimation(rollAnimation);
        } else if (index == 2) {
            diceTwoDefense.startAnimation(rollAnimation);
        } else if (index == 3) {
            diceThreeDefense.startAnimation(rollAnimation);
        }
    }
}
