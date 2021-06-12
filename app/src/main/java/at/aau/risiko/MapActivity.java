package at.aau.risiko;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import java.util.HashMap;
import java.util.LinkedList;

import at.aau.core.Country;
import at.aau.core.Player;
import at.aau.risiko.controller.Game;
import at.aau.risiko.controller.ObserveState;
import at.aau.server.dto.BaseMessage;
import at.aau.server.dto.ReadyMessage;
import at.aau.server.kryonet.Callback;
import at.aau.server.kryonet.GameClient;

public class MapActivity extends AppCompatActivity {

    Game game;

    HashMap<Integer, Country> buttonMapping;
    HashMap<Integer, Player> avatarMapping;
    HashMap<Integer, Integer[]> neighborMapping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        // Get player data from intent:
        String[] playerNames = getIntent().getStringArrayExtra("names");
        for (String p : playerNames) {
            Log.i("PLAYER NAME", p);
        }
        Integer[] playerColors = (Integer[]) getIntent().getSerializableExtra("colors");
        for (Integer c : playerColors) {
            Log.i("PLAYER COLOR", String.valueOf(c));
        }

        // Create players from that array
        Player[] players = new Player[playerNames.length];
        for (int i = 0; i < playerNames.length; ++i) {
            players[i] = new Player(playerNames[i], playerColors[i]);
        }


        // Find all buttons in view and link to countries:
        buttonMapping = new HashMap<Integer, Country>();
        int[] buttons = ((Group) findViewById(R.id.group)).getReferencedIds();
        for (int button : buttons) {
            buttonMapping.put(button, new Country(findViewById(button).getContentDescription().toString()));
        }

        // Put all countries bordering a country into a hashtable:
        neighborMapping = new HashMap<Integer, Integer[]>();
        neighborMapping.put(R.id.buttonAlaska, new Integer[]{R.id.buttonOntario, R.id.buttonYakutsk});
        neighborMapping.put(R.id.buttonArgentina, new Integer[]{R.id.buttonBrazil, R.id.buttonPeru});
        neighborMapping.put(R.id.buttonBrazil, new Integer[]{R.id.buttonArgentina, R.id.buttonPeru, R.id.buttonVenezuela});
        neighborMapping.put(R.id.buttonCentralAmerica, new Integer[]{R.id.buttonEastCoast, R.id.buttonVenezuela, R.id.buttonWestCoast});
        neighborMapping.put(R.id.buttonChina, new Integer[]{R.id.buttonIndia, R.id.buttonMongolia, R.id.buttonSiam, R.id.buttonSiberia, R.id.buttonUral, R.id.buttonYakutsk});
        neighborMapping.put(R.id.buttonCongo, new Integer[]{R.id.buttonEthiopia, R.id.buttonNorthAfrica, R.id.buttonSouthAfrica});
        neighborMapping.put(R.id.buttonEastAustralia, new Integer[]{R.id.buttonNewGuinea, R.id.buttonWestAustralia});
        neighborMapping.put(R.id.buttonEastCoast, new Integer[]{R.id.buttonCentralAmerica, R.id.buttonOntario, R.id.buttonQuebec, R.id.buttonWestCoast});
        neighborMapping.put(R.id.buttonEgypt, new Integer[]{R.id.buttonEthiopia, R.id.buttonMiddleEast, R.id.buttonNorthAfrica, R.id.buttonWestEurope});
        neighborMapping.put(R.id.buttonEthiopia, new Integer[]{R.id.buttonCongo, R.id.buttonEgypt, R.id.buttonMiddleEast, R.id.buttonNorthAfrica, R.id.buttonSouthAfrica});
        neighborMapping.put(R.id.buttonGreenland, new Integer[]{R.id.buttonOntario, R.id.buttonQuebec, R.id.buttonScandinavia, R.id.buttonWestEurope});
        neighborMapping.put(R.id.buttonIndia, new Integer[]{R.id.buttonChina, R.id.buttonMiddleEast, R.id.buttonSiam, R.id.buttonUral});
        neighborMapping.put(R.id.buttonIndonesia, new Integer[]{R.id.buttonNewGuinea, R.id.buttonSiam, R.id.buttonWestAustralia});
        neighborMapping.put(R.id.buttonMiddleEast, new Integer[]{R.id.buttonEgypt, R.id.buttonEthiopia, R.id.buttonIndia, R.id.buttonUkraine, R.id.buttonUral});
        neighborMapping.put(R.id.buttonMongolia, new Integer[]{R.id.buttonChina, R.id.buttonSiberia});
        neighborMapping.put(R.id.buttonNewGuinea, new Integer[]{R.id.buttonEastAustralia});
        neighborMapping.put(R.id.buttonNorthAfrica, new Integer[]{R.id.buttonCongo, R.id.buttonEgypt, R.id.buttonWestEurope});
        neighborMapping.put(R.id.buttonOntario, new Integer[]{R.id.buttonAlaska, R.id.buttonEastCoast, R.id.buttonGreenland, R.id.buttonQuebec, R.id.buttonWestCoast});
        neighborMapping.put(R.id.buttonPeru, new Integer[]{R.id.buttonArgentina, R.id.buttonBrazil, R.id.buttonVenezuela});
        neighborMapping.put(R.id.buttonQuebec, new Integer[]{R.id.buttonEastCoast, R.id.buttonGreenland, R.id.buttonOntario});
        neighborMapping.put(R.id.buttonScandinavia, new Integer[]{R.id.buttonGreenland, R.id.buttonUkraine, R.id.buttonUral, R.id.buttonWestEurope});
        neighborMapping.put(R.id.buttonSiam, new Integer[]{R.id.buttonChina, R.id.buttonIndia, R.id.buttonIndonesia});
        neighborMapping.put(R.id.buttonSiberia, new Integer[]{R.id.buttonChina, R.id.buttonMongolia, R.id.buttonUral, R.id.buttonYakutsk});
        neighborMapping.put(R.id.buttonSouthAfrica, new Integer[]{R.id.buttonCongo, R.id.buttonEthiopia});
        neighborMapping.put(R.id.buttonUkraine, new Integer[]{R.id.buttonMiddleEast, R.id.buttonScandinavia, R.id.buttonUral, R.id.buttonWestEurope});
        neighborMapping.put(R.id.buttonUral, new Integer[]{R.id.buttonChina, R.id.buttonIndia, R.id.buttonMiddleEast, R.id.buttonScandinavia, R.id.buttonSiberia});
        neighborMapping.put(R.id.buttonVenezuela, new Integer[]{R.id.buttonBrazil, R.id.buttonCentralAmerica, R.id.buttonPeru});
        neighborMapping.put(R.id.buttonWestAustralia, new Integer[]{R.id.buttonEastAustralia, R.id.buttonIndonesia});
        neighborMapping.put(R.id.buttonWestCoast, new Integer[]{R.id.buttonCentralAmerica, R.id.buttonEastCoast, R.id.buttonOntario});
        neighborMapping.put(R.id.buttonWestEurope, new Integer[]{R.id.buttonEgypt, R.id.buttonGreenland, R.id.buttonNorthAfrica, R.id.buttonScandinavia, R.id.buttonUkraine});
        neighborMapping.put(R.id.buttonYakutsk, new Integer[]{R.id.buttonAlaska, R.id.buttonChina, R.id.buttonSiberia});

        // Convert name mapping to neighbour mapping:
        for (int i : neighborMapping.keySet()) {
            for (int j : neighborMapping.get(i)) {
                buttonMapping.get(i).addNeighbor(buttonMapping.get(j));
            }
        }

        // Add players to side layout
        avatarMapping = new HashMap<Integer, Player>();

        LinearLayout layout = findViewById(R.id.linearLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 8, 32, 8);
        for (Player player : players) {
            ImageView avatar = new ImageView(this);
            avatar.setId(View.generateViewId());
            avatar.setImageResource(R.drawable.ic_army_counter);
            avatar.setLayoutParams(params);
            avatar.setImageTintList(ColorStateList.valueOf(player.getColor()));
            avatar.setImageTintMode(PorterDuff.Mode.MULTIPLY);
            layout.addView(avatar, LinearLayout.LayoutParams.WRAP_CONTENT);

            avatarMapping.put(avatar.getId(), player);
        }


        // Create countries:
        LinkedList<Country> countries = new LinkedList<Country>();
        for (Country c : buttonMapping.values()) {
            countries.add(c);
        }

        // Start game:
        game = Game.getInstance(players, countries, buttonMapping, avatarMapping, this);

        GameClient.getInstance().registerCallback(new Callback<BaseMessage>() {
            @Override
            public void callback(BaseMessage argument) {
                game.handleMessage(argument);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        GameClient.getInstance().sendMessage(new ReadyMessage());
    }

    @Override
    protected void onResume() {
        super.onResume();

        GameClient.getInstance().registerCallback(new Callback<BaseMessage>() {
            @Override
            public void callback(BaseMessage argument) {
                game.handleMessage(argument);
            }
        });
    }

    public void onClick(View view) {
        game.handleInput(view);
    }

    public void openCardActivity(View view) {
        startActivity(new Intent(this, CardActivity.class));
    }

}