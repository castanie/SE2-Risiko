package at.aau.risiko;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import services.BackgroundMusicService;

public class SettingsActivity extends AppCompatActivity {

    Button btnPlayMusic;
    Button btnStopMusic;
    static  BackgroundMusicService  service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        btnStopMusic = findViewById(R.id.btnStopMusic);
        btnPlayMusic = findViewById(R.id.btnPlayMusic);

        btnStopMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
            }
        });

        btnPlayMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMusic();
            }
        });


    }


    private void stopMusic() {
        //stopService(new Intent(this, BackgroundMusicService.class));
        BackgroundMusicService.pauseMusic();

    }

    private void startMusic() {
        //startService(new Intent(this, BackgroundMusicService.class));
        BackgroundMusicService.resumeMusic();
    }


}

