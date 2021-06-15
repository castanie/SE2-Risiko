package at.aau.risiko;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import services.BackgroundMusicService;

public class SettingsActivity extends AppCompatActivity {

    Button btnResumeMusic, btnPauseMusic;
    static  BackgroundMusicService  service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        btnResumeMusic = findViewById(R.id.btnResumeMusic);
        btnPauseMusic = findViewById(R.id.btnPauseMusic);

        btnResumeMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundMusicService.getInstance().resumeMusic();
            }
        });

        btnPauseMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               BackgroundMusicService.getInstance().pauseMusic();
            }
        });

    }






}

