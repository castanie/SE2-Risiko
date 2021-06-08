package at.aau.risiko;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import at.aau.server.dto.BaseMessage;
import at.aau.server.dto.NameMessage;
import at.aau.server.dto.ReadyMessage;
import at.aau.server.dto.StartMessage;
import at.aau.server.dto.TextMessage;
import at.aau.server.dto.TurnMessage;
import at.aau.server.dto.UpdateMessage;
import at.aau.server.kryonet.Callback;
import at.aau.server.kryonet.GameClient;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
        GameServer server = GameServer.getInstance();
        TextMessage response = new TextMessage("This is a response.");
        server.registerClass(TextMessage.class);
        server.registerCallback(new Callback<BaseMessage>() {
            @Override
            public void callback(BaseMessage argument) {
                Log.i("SERVER CALLBACK", ((TextMessage) argument).text);
                server.broadcastMessage(confirm);
            }
        });
        Thread serverThread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    server.start();
                } catch (IOException e) {
                    // e.printStackTrace();
                }
            }
        };
        serverThread.start();
        */

        GameClient client = GameClient.getInstance();

        TextMessage request = new TextMessage("This is a request.");
        client.registerClass(TextMessage.class);
        client.registerClass(NameMessage.class);
        client.registerClass(String[].class);
        client.registerClass(Integer[].class);
        client.registerClass(StartMessage.class);
        client.registerClass(ReadyMessage.class);
        client.registerClass(TurnMessage.class);
        client.registerClass(UpdateMessage.class);

        client.registerCallback(new Callback<BaseMessage>() {
            @Override
            public void callback(BaseMessage argument) {
                if (argument instanceof TextMessage) {
                    Log.i("SERVER MESSAGE", ((TextMessage) argument).text);
                } else if (argument instanceof StartMessage) {
                    Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                    intent.putExtra("names", ((StartMessage) argument).names);
                    intent.putExtra("colors",((StartMessage) argument).colors);
                    intent.putExtra("setup",((StartMessage) argument).setup);
                    startActivity(intent);
                }
            }
        });

        Thread clientThread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    client.connect("10.0.2.2");
                    client.sendMessage(request);
                } catch (IOException e) {
                    // e.printStackTrace();
                }
            }
        };

        clientThread.start();
    }

    @Override
    protected void onStart() {
        super.onStart();

        EditText txtNickname = findViewById(R.id.txtNickname);
        Button btnConfirm = findViewById(R.id.btnConfirm);

        Log.i("loginActivity", "LoginActivity launched");

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to send on server
                String enteredNickname = txtNickname.getText().toString();

                if (txtNickname.getText().toString().isEmpty()) {
                    showToast("Player's name has not been entered!");
                } else {
                    showToast("Player's name: " + enteredNickname);
                    // TODO: send nickname to server
                    GameClient.getInstance().sendMessage(new NameMessage(enteredNickname));

                    startActivity(new Intent(getApplicationContext(), LobbyActivity.class));
                }
            }
        });

    }


    public void showToast(String message) {
        Log.i("BUTTON", "Showing toast!!");
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}