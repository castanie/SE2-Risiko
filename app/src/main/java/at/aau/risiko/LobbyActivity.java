package at.aau.risiko;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import at.aau.server.dto.BaseMessage;
import at.aau.server.dto.NameMessage;
import at.aau.server.dto.RequestPlayerMessage;
import at.aau.server.dto.ResponsePlayerMessage;
import at.aau.server.dto.StartMessage;
import at.aau.server.kryonet.Callback;
import at.aau.server.kryonet.GameClient;

public class LobbyActivity extends AppCompatActivity {

    ListView playersInLobby;

    // TODO replace this list with data from server
    ArrayList<String> userNames = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);


        TextView playerOne = findViewById(R.id.textPlayerOne);
        TextView playerTwo = findViewById(R.id.textPlayerTwo);
        TextView playerThree = findViewById(R.id.textPlayerThree);
        TextView playerFour = findViewById(R.id.textPlayerFour);
        TextView playerFive = findViewById(R.id.textPlayerFive);

        TextView[] playerNames = {playerOne, playerTwo, playerThree, playerFour, playerFive};


        GameClient.getInstance().registerCallback(new Callback<BaseMessage>() {
            @Override
            public void callback(BaseMessage argument) {
                if (argument instanceof StartMessage) {
                    Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                    intent.putExtra("names", ((StartMessage) argument).names);
                    intent.putExtra("colors", ((StartMessage) argument).colors);
                    startActivity(intent);
                }

                else if(argument instanceof ResponsePlayerMessage){
                    userNames = ((ResponsePlayerMessage)argument).getPlayerNames();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < userNames.size(); i++) {
                                playerNames[i].setText(userNames.get(i));
                            }
                        }
                    });
                }
            }

        });

        GameClient.getInstance().sendMessage(new RequestPlayerMessage());

        Button btnExit = findViewById(R.id.btnExit);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameClient.getInstance().sendMessage(new StartMessage());
            }
        });

    }


}