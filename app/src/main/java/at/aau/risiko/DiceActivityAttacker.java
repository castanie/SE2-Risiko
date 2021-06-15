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
import at.aau.server.dto.CheatedMessage;
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

    /**
     *ToDo: replace this Variables with getNumAttackers() from Daniel's feature to set exactly the amount of dices needed
     */
    /*This variables are needed to set the exact amount of dices needed*/
    int numAttackers = 3;
    int numDefenders = 3;
    /*This boolean needs to be set to true after the dices have been rolled to send to the Defender so the UI can be updated*/
    boolean isShaken = false;
    /*if hasRolledDefender == true && isUpdatedGUI == true the Attacker is allowed to roll the dices*/
    boolean hasRolledDefender = false;
    boolean isUpdatedGUI = false;

    //dice should only be rolled if acceleration is > SHAKE_THRESHOLD
    final static int SHAKE_THRESHOLD = 3;

    /*count variable for insurance that dices can only be rolled once*/
    int count = 0;
    /*this array will be send to DiceActivityDefender*/
    int[] eyeNumbersAttacker;
    int[] defendersDices;



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

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //set dices to random start values
        for (int i = 0; i < numAttackers; i++) {
            setImageViewAttacker(i+2, i+1);
        }
        for (int j = 0; j < numDefenders; j++) {
            updateGUI(j+1, j+2);
        }

        Button closeBtn = (Button)findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close the activity
                finish();
            }
        });



        /**
         * ToDo: read server message from DiceActivityDefender and set hasShakenDefender to true and update the UI
         */
        GameClient.getInstance().registerCallback(new Callback<BaseMessage>() {
            @Override
            public void callback(BaseMessage argument) {
                if (argument instanceof EyeNumbersMessage) {
                    Log.i("DICE ATTACKER", "Received EyeNumbersMessage!");
                    setDefendersDices(((EyeNumbersMessage) argument).getMessage());

                    for(int i = 0; i < numDefenders; i++) {
                        updateGUI(i, defendersDices[i]);
                    }
                    isUpdatedGUI = true;

                    hasRolledDefender = true;

                }
            }
        });

        /*
        // iterate over the
        if(hasRolledDefender) {
            for(int i = 0; i < defendersDices.length; i++) {
                updateGUI(i, defendersDices[i]);
            }
            isUpdatedGUI = true;

        }
        */


        Button cheatedBtn = findViewById(R.id.cheatBtn);
        cheatedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (defendersDices[numDefenders] == 1) {

                    //ToDo: send to server that defender cheated all his dices are set to one or he automatically loses

                    GameClient.getInstance().sendMessage(new CheatedMessage());
                }
            }
        });







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
        eyeNumbersAttacker = new int[numAttackers+1];
        if(hasRolledDefender && isUpdatedGUI && count < 1) {
            if (accelerationValue > SHAKE_THRESHOLD && accelerationValue < 30) {


                for (int i = 0; i < numAttackers; i++) {
                    int num = dice.diceRoll();
                    eyeNumbersAttacker[i] = num;
                    //diceNum.setText("dice have been rolled:" + num);
                    //accel.setText("Acceleration: " + (int)accelerationValueDefender);
                    dice.setEyeNumber(num);

                    setImageViewAttacker(num, i + 1);

                }
                //player has not cheated
                eyeNumbersAttacker[numAttackers] = 0;
                count++;
                isShaken = true;
                Log.i("DiceActivity", "Device was shaken");

            }
            //cheat function
            else if (accelerationValue > 30) {
                dice.setEyeNumber(6);
                for (int index = 0; index < numAttackers; index++) {
                    setImageViewAttacker(6, index + 1);
                    eyeNumbersAttacker[index] = 6;
                }
                count++;
                isShaken = true;
            }
            //player has cheated
            eyeNumbersAttacker[numAttackers] = 1;
            System.out.println("Attacker rolled dice");
            /**
             * ToDo: send server message to DiceActivityDefender if isShaken is true so that the UI can be updated and change state
             */
            if(isShaken) {

                GameClient.getInstance().sendMessage(new EyeNumbersMessage(eyeNumbersAttacker));
                //ToDo: send results to MapActivity
                // GameClient.getInstance().sendMessage(new DiceResultMessage(eyeNumbersAttacker, defendersDices));
            }
            return;

        }else {
            return;
        }


    }




    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    private void setImageViewAttacker(int num, int index) {
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
        }else if (index == 3) {
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
        if(index == 1) {
            diceOneAttack.startAnimation(rollAnimation);
        }else if(index == 2) {
            diceTwoAttack.startAnimation(rollAnimation);
        }else if(index == 3) {
            diceThreeAttack.startAnimation(rollAnimation);
        }
    }
    private void rotateDiceDefender(int index) {
        Animation rollAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        if (index == 1) {
            diceOneDefense.startAnimation(rollAnimation);
        }else if (index == 2) {
            diceTwoDefense.startAnimation(rollAnimation);
        }else if (index == 3) {
            diceThreeDefense.startAnimation(rollAnimation);
        }
    }
}
