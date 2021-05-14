package at.aau.risiko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import services.BackgroundMusicService;

public class Settings extends AppCompatActivity {

    Button btnPlayMusic,btnStopMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnPlayMusic = findViewById(R.id.btnPlayMusic);
        btnStopMusic = findViewById(R.id.btnStopMusic);

        btnPlayMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMusic();
            }
        });

        btnStopMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
            }
        });

    }

    private void stopMusic() {
        stopService(new Intent(this, BackgroundMusicService.class));
    }

    private void startMusic() {
        startService(new Intent(this, BackgroundMusicService.class));
    }


    }
