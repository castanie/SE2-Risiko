package at.aau.risiko;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import at.aau.risiko.networking.NetworkServer;
import at.aau.risiko.networking.dto.TextMessage;
import at.aau.risiko.networking.kryonet.NetworkClientKryo;
import at.aau.risiko.networking.kryonet.NetworkServerKryo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dice);




    }
}