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

import androidx.appcompat.app.AppCompatActivity;

import at.aau.core.Dice;
import at.aau.server.dto.BaseMessage;
import at.aau.server.dto.EyeNumbersMessage;
import at.aau.server.kryonet.Callback;
import at.aau.server.kryonet.GameClient;

public class DiceActivityDefender extends AppCompatActivity implements SensorEventListener {
    /*these variables are needed to track the sensor event*/
    private SensorManager sensorManager;
    private Sensor accelerometer;
    /*UI Variables*/
    private ImageView diceOneDefense;
    private ImageView diceTwoDefense;
    /*only needed for GUI update*/
    private ImageView diceOneAttack;
    private ImageView diceTwoAttack;
    private ImageView diceThreeAttack;
    /**
     *ToDo: replace this Variables with getNumAttackers() from Daniel's feature to set exactly the amount of dices needed
     */
    /*This variables are needed to set the exact amount of dices needed*/
    int numAttackers = 3;
    int numDefenders = 2;
    /*This boolean needs to be set to true after the dices have been rolled to send to the Defender so the UI can be updated*/
    boolean isShaken = false;
    /*if hasRolledAttacker == true the UI needs to be updated*/
    boolean hasRolledAttacker = false;
    //dice should only be rolled if acceleration is > SHAKE_THRESHOLD
    final static int SHAKE_THRESHOLD = 3;
    /*this array will be send to DiceActivityAttacker*/
    int[] eyeNumbersDefender;

    int[] attackersDices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        diceOneDefense = findViewById(R.id.diceOneDefense);
        diceTwoDefense = findViewById(R.id.diceTwoDefense);

        diceOneAttack = findViewById(R.id.diceOneAttack);
        diceTwoAttack = findViewById(R.id.diceTwoAttack);
        diceThreeAttack = findViewById(R.id.diceThreeAttack);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Button closeBtn = (Button)findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close the activity
                finish();
            }
        });

        if(isShaken) {
            /**
             * ToDo: send the eye numbers to DiceActivityAttacker for GUI update.
             */
            GameClient.getInstance().sendMessage(new EyeNumbersMessage(eyeNumbersDefender));
        }

        /**
         * ToDo: wait for server message from DiceActivityAttacker to update GUI and then switch state.
         */

        GameClient.getInstance().registerCallback(new Callback<BaseMessage>() {
            @Override
            public void callback(BaseMessage argument) {
                if (argument instanceof EyeNumbersMessage) {
                    setAttackersDices(((EyeNumbersMessage) argument).getMessage());
                    if (attackersDices != null) {
                        hasRolledAttacker = true;
                    }
                }
            }
        });

        //iterate over the
        if(hasRolledAttacker) {
            for(int i = 0; i < attackersDices.length; i++) {
                updateGUI(i, attackersDices[i]);
            }

        }

    }


    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        //calculate the movement value
        double accelerationValue = Math.sqrt((x*x + y*y + z*z)) - SensorManager.GRAVITY_EARTH;
        //if accelerationValue > SHAKE_THRESHOLD call rollDice
        Dice dice = new Dice("defender");
        //initialize eyeNumbers array
        eyeNumbersDefender = new int[numDefenders];
        if (accelerationValue > SHAKE_THRESHOLD && accelerationValue < 30) {


            for (int i = 0; i < numDefenders; i++) {
                int num = dice.diceRoll();
                eyeNumbersDefender[i] = num;
                //diceNum.setText("dice have been rolled:" + num);
                //accel.setText("Acceleration: " + (int)accelerationValue);
                dice.setEyeNumber(num);

                setImageViewDefender(num, i + 1);

            }
            isShaken = true;
            Log.i("DiceActivity", "Device was shaken");
        }  else{
            return;
        }
        //cheat function
        if (accelerationValue > 30) {
            dice.setEyeNumber(6);
            for (int index = 0; index < numDefenders; index++) {
                setImageViewDefender(6, index + 1);
                eyeNumbersDefender[index] = 6;
            }
            isShaken = true;
        }


        System.out.println("Defender rolled dice");
        return;



    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    private void setImageViewDefender(int num, int index) {
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
        }
        rotateDice(index);
    }

    private void rotateDice(int index) {
        Animation rollAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        if(index == 1) {
            diceOneDefense.setAnimation(rollAnimation);
        }else if(index == 2) {
            diceTwoDefense.setAnimation(rollAnimation);
        }
    }

    private void updateGUI(int index, int num) {
        if(index == 1) {
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

        }else if(index == 2) {
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
                    diceTwoAttack.setImageResource(R.drawable.diceredfour);;
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

        }else if(index == 3) {
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

    private void setAttackersDices(int[] arr) {
        attackersDices = arr;
    }



}
