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

import at.aau.server.dto.CardMessage;
import at.aau.server.dto.DiceMessage;
import at.aau.server.dto.ExchangeMessage;
import at.aau.server.dto.EyeNumbersMessage;
import at.aau.server.dto.LogMessage;
import at.aau.server.dto.NameMessage;
import at.aau.server.dto.ReadyMessage;
import at.aau.server.dto.StartMessage;
import at.aau.server.dto.TurnMessage;
import at.aau.server.dto.UpdateMessage;
import at.aau.server.kryonet.GameClient;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GameClient client = GameClient.getInstance();

        client.registerClass(String[].class);
        client.registerClass(Integer[].class);
        client.registerClass(LogMessage.class);
        client.registerClass(NameMessage.class);
        client.registerClass(StartMessage.class);
        client.registerClass(ReadyMessage.class);
        client.registerClass(TurnMessage.class);
        client.registerClass(UpdateMessage.class);
        client.registerClass(DiceMessage.class);
        client.registerClass(EyeNumbersMessage.class);
        // client.registerClass(CheatedMessage.class);
        client.registerClass(CardMessage.class);
        client.registerClass(ExchangeMessage.class);

        Thread clientThread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    client.connect("10.0.2.2");
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
        Log.i("BUTTON", "");
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}