package at.aau.risiko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import at.aau.risiko.core.Game;
import at.aau.risiko.core.Player;

public class MapActivity extends AppCompatActivity {

    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        game = new Game(new Player[3]);
    }

    public void onClick(View view) {
        game.handleInput(view);
    }
    
}