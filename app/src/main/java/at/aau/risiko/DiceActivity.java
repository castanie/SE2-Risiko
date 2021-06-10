package at.aau.risiko;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

import androidx.appcompat.app.AppCompatActivity;

import at.aau.core.Dice;

public class DiceActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView diceNum;
    private TextView accel;

    private ImageView diceOneAttack;
    private ImageView diceTwoAttack;
    private ImageView diceThreeAttack;
    private ImageView diceOneDefense;
    private ImageView diceTwoDefense;

    int numAttackers = 3;
    int numDefenders = 2;

    boolean hasShakenDefender = false;
    boolean hasShakenAttacker = false;
    //dice should only be rolled if acceleration is > SHAKE_THRESHOLD
    final static int SHAKE_THRESHOLD = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        diceOneAttack = findViewById(R.id.diceOneAttack);
        diceTwoAttack = findViewById(R.id.diceTwoAttack);
        diceThreeAttack = findViewById(R.id.diceThreeAttack);
        diceOneDefense = findViewById(R.id.diceOneDefense);
        diceTwoDefense = findViewById(R.id.diceTwoDefense);

        diceNum = (TextView)findViewById(R.id.diceNumber);
        accel = (TextView)findViewById(R.id.acceleration);


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


        //check how many dices are needed on each side and set them to some images
     /*   switch(numAttackers) {
            case 1:
                diceOneAttack.setImageResource(R.drawable.diceredtwo);
                break;
            case 2:
                diceOneAttack.setImageResource(R.drawable.diceredthree);
                diceTwoAttack.setImageResource(R.drawable.diceredfive);
                break;
            case 3:
                diceOneAttack.setImageResource(R.drawable.diceredthree);
                diceTwoAttack.setImageResource(R.drawable.diceredfive);
                diceThreeAttack.setImageResource(R.drawable.diceredone);
                break;
        }
        switch(numDefenders) {
            case 1:
                diceOneDefense.setImageResource(R.drawable.dicebluefive);
                break;
            case 2:
                diceOneDefense.setImageResource(R.drawable.dicebluefive);
                diceTwoDefense.setImageResource(R.drawable.dicebluesix);
                break;
        }
*/


    }
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //variables for tracking the motion of the device on x-, y- and z-axis
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        //calculate the movement value
        double accelerationValueDefender = Math.sqrt((x*x + y*y + z*z)) - SensorManager.GRAVITY_EARTH;
        //if accelerationValueDefender > SHAKE_THRESHOLD call rollDice
        Dice dice = new Dice("attacker");
        if(!hasShakenDefender && !hasShakenAttacker) {
            if (accelerationValueDefender > SHAKE_THRESHOLD && accelerationValueDefender < 30) {


                for (int i = 0; i < numDefenders; i++) {
                    int num = dice.diceRoll();
                    //diceNum.setText("dice have been rolled:" + num);
                    //accel.setText("Acceleration: " + (int)accelerationValueDefender);
                    dice.setEyeNumber(num);

                    setImageViewDefender(num, i + 1);

                }
                Log.i("DiceActivity", "Device was shaken");
            }  else{
                return;
            }
            //cheat function
            if (accelerationValueDefender > 10) {
                dice.setEyeNumber(6);
                for (int index = 0; index < numDefenders; index++) {
                    setImageViewDefender(6, index + 1);
                }
            }

            hasShakenDefender = true;
            System.out.println("Defender rolled dice");
            return;
        }

        /**
         * wait 2 seconds before attacker rolls the dice.
         */
        /*try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


        double accelerationValueAttacker = Math.sqrt((x*x + y*y + z*z)) - SensorManager.GRAVITY_EARTH;

        if(hasShakenDefender && !hasShakenAttacker) {
            if (accelerationValueAttacker > SHAKE_THRESHOLD && accelerationValueAttacker < 30) {


                for (int i = 0; i < numAttackers; i++) {
                    int num = dice.diceRoll();
                    //diceNum.setText("dice have been rolled:" + num);
                    //accel.setText("Acceleration: " + (int)accelerationValueDefender);
                    dice.setEyeNumber(num);

                    setImageViewAttacker(num, i + 1);

                }
                Log.i("DiceActivity", "Device was shaken");
            }else {
                return;
            }
            //cheat function
            if (accelerationValueAttacker > 10) {
                dice.setEyeNumber(6);
                for (int index = 0; index < numAttackers; index++) {
                    setImageViewAttacker(6, index + 1);
                }
            }
            System.out.println("Attacker rolled dice");
            hasShakenAttacker = true;
            return;

        }else {
            System.out.println("Dice roll done");
            return;
        }





    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

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

            }
            //  rotateDice(index);
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

            }
        }
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

            }
            rotateDice(index);
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

            }
            rotateDice(index);
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
            }
            rotateDice(index);
        }
    }

    private void rotateDice(int index) {

        Animation rollAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        if(index == 1) {
            diceOneAttack.setAnimation(rollAnimation);
        }else if(index == 2) {
            diceTwoAttack.setAnimation(rollAnimation);
        }else if(index == 3) {
            diceThreeAttack.setAnimation(rollAnimation);
        }
    }
}
