package at.aau.risiko;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

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
    ArrayAdapter<String> playerNamesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        playerNamesAdapter  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userNames);
        playersInLobby = findViewById(R.id.listOfPlayers);
        playersInLobby.setAdapter(playerNamesAdapter);

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
                    setUserNames(userNames);
                }
            }

        });

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

    // called by the network client to update the list of users
    public void setUserNames(ArrayList<String> userNames) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                playerNamesAdapter.notifyDataSetChanged();
            }
        });
    }

}