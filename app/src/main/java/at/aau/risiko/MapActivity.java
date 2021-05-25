package at.aau.risiko;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import java.util.HashMap;

import at.aau.risiko.core.Country;
import at.aau.risiko.core.DraftState;
import at.aau.risiko.core.Game;
import at.aau.risiko.core.Player;
import at.aau.risiko.core.SetupState;
import at.aau.risiko.networking.Callback;
import at.aau.risiko.networking.dto.BaseMessage;
import at.aau.risiko.networking.dto.ReadyMessage;
import at.aau.risiko.networking.dto.StartMessage;
import at.aau.risiko.networking.dto.TextMessage;
import at.aau.risiko.networking.dto.TurnMessage;
import at.aau.risiko.networking.kryonet.GameClient;

public class MapActivity extends AppCompatActivity {

    Game game;

    HashMap<Integer, Country> buttonMapping;
    HashMap<Integer, Player> playerMapping;
    HashMap<Integer, int[]> neighborMapping;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        GameClient.getInstance().sendMessage(new ReadyMessage());


        // Find all buttons in view and link to countries:
        buttonMapping = new HashMap<Integer, Country>();
        int[] buttons = ((Group) findViewById(R.id.group)).getReferencedIds();
        for (int button : buttons) {
            buttonMapping.put(button, new Country(((Button) findViewById(button)).getContentDescription().toString()));
            // Log.i("COUNTRY LISTED", buttonMapping.get(button).getName());
        }


        // TODO:  PUT DATA INTO EXTERNAL JSON?
        // Put all countries bordering a country into a hashtable:
        neighborMapping = new HashMap<Integer, int[]>();
        neighborMapping.put(R.id.buttonAlaska, new int[]{R.id.buttonOntario, R.id.buttonYakutsk});
        neighborMapping.put(R.id.buttonArgentina, new int[]{R.id.buttonBrazil, R.id.buttonPeru});
        neighborMapping.put(R.id.buttonBrazil, new int[]{R.id.buttonArgentina, R.id.buttonPeru, R.id.buttonVenezuela});
        neighborMapping.put(R.id.buttonCentralAmerica, new int[]{R.id.buttonEastCoast, R.id.buttonVenezuela, R.id.buttonWestCoast});
        neighborMapping.put(R.id.buttonChina, new int[]{R.id.buttonIndia, R.id.buttonMongolia, R.id.buttonSiam, R.id.buttonSiberia, R.id.buttonUral, R.id.buttonYakutsk});
        neighborMapping.put(R.id.buttonCongo, new int[]{R.id.buttonEthiopia, R.id.buttonNorthAfrica, R.id.buttonSouthAfrica});
        neighborMapping.put(R.id.buttonEastAustralia, new int[]{R.id.buttonNewGuinea, R.id.buttonWestAustralia});
        neighborMapping.put(R.id.buttonEastCoast, new int[]{R.id.buttonCentralAmerica, R.id.buttonOntario, R.id.buttonQuebec, R.id.buttonWestCoast});
        neighborMapping.put(R.id.buttonEgypt, new int[]{R.id.buttonEthiopia, R.id.buttonMiddleEast, R.id.buttonNorthAfrica, R.id.buttonWestEurope});
        neighborMapping.put(R.id.buttonEthiopia, new int[]{R.id.buttonCongo, R.id.buttonEgypt, R.id.buttonMiddleEast, R.id.buttonNorthAfrica, R.id.buttonSouthAfrica});
        neighborMapping.put(R.id.buttonGreenland, new int[]{R.id.buttonOntario, R.id.buttonQuebec, R.id.buttonScandinavia, R.id.buttonWestEurope});
        neighborMapping.put(R.id.buttonIndia, new int[]{R.id.buttonChina, R.id.buttonMiddleEast, R.id.buttonSiam, R.id.buttonUral});
        neighborMapping.put(R.id.buttonIndonesia, new int[]{R.id.buttonNewGuinea, R.id.buttonSiam, R.id.buttonWestAustralia});
        neighborMapping.put(R.id.buttonMiddleEast, new int[]{R.id.buttonEgypt, R.id.buttonEthiopia, R.id.buttonIndia, R.id.buttonUkraine, R.id.buttonUral});
        neighborMapping.put(R.id.buttonMongolia, new int[]{R.id.buttonChina, R.id.buttonSiberia});
        neighborMapping.put(R.id.buttonNewGuinea, new int[]{R.id.buttonEastAustralia});
        neighborMapping.put(R.id.buttonNorthAfrica, new int[]{R.id.buttonCongo, R.id.buttonEgypt, R.id.buttonWestEurope});
        neighborMapping.put(R.id.buttonOntario, new int[]{R.id.buttonAlaska, R.id.buttonEastCoast, R.id.buttonGreenland, R.id.buttonQuebec, R.id.buttonWestCoast});
        neighborMapping.put(R.id.buttonPeru, new int[]{R.id.buttonArgentina, R.id.buttonBrazil, R.id.buttonVenezuela});
        neighborMapping.put(R.id.buttonQuebec, new int[]{R.id.buttonEastCoast, R.id.buttonGreenland, R.id.buttonOntario});
        neighborMapping.put(R.id.buttonScandinavia, new int[]{R.id.buttonGreenland, R.id.buttonUkraine, R.id.buttonUral, R.id.buttonWestEurope});
        neighborMapping.put(R.id.buttonSiam, new int[]{R.id.buttonChina, R.id.buttonIndia, R.id.buttonIndonesia});
        neighborMapping.put(R.id.buttonSiberia, new int[]{R.id.buttonChina, R.id.buttonMongolia, R.id.buttonUral, R.id.buttonYakutsk});
        neighborMapping.put(R.id.buttonSouthAfrica, new int[]{R.id.buttonCongo, R.id.buttonEthiopia});
        neighborMapping.put(R.id.buttonUkraine, new int[]{R.id.buttonMiddleEast, R.id.buttonScandinavia, R.id.buttonUral, R.id.buttonWestEurope});
        neighborMapping.put(R.id.buttonUral, new int[]{R.id.buttonChina, R.id.buttonIndia, R.id.buttonMiddleEast, R.id.buttonScandinavia, R.id.buttonSiberia});
        neighborMapping.put(R.id.buttonVenezuela, new int[]{R.id.buttonBrazil, R.id.buttonCentralAmerica, R.id.buttonPeru});
        neighborMapping.put(R.id.buttonWestAustralia, new int[]{R.id.buttonEastAustralia, R.id.buttonIndonesia});
        neighborMapping.put(R.id.buttonWestCoast, new int[]{R.id.buttonCentralAmerica, R.id.buttonEastCoast, R.id.buttonOntario});
        neighborMapping.put(R.id.buttonWestEurope, new int[]{R.id.buttonEgypt, R.id.buttonGreenland, R.id.buttonNorthAfrica, R.id.buttonScandinavia, R.id.buttonUkraine});
        neighborMapping.put(R.id.buttonYakutsk, new int[]{R.id.buttonAlaska, R.id.buttonChina, R.id.buttonSiberia});


        // Convert name mapping to neighbour mapping:
        for (int i : neighborMapping.keySet()) {
            for (int j : neighborMapping.get(i)) {
                buttonMapping.get(i).addNeighbor(buttonMapping.get(j));
                // Log.i("REGISTERED NEIGHBOR", buttonMapping.get(i).getName() + " added neighbor " + buttonMapping.get(j).getName());
            }
        }


        // Start game:
        // TODO: CHANGE PLAYER ARRAY TO REFLECT PLAYERS CONNECTED TO SERVER
        game = new Game(new Player[]{
                new Player("Uno", Color.valueOf(0xFFFFCC00)),
                new Player("Due", Color.valueOf(0xFFFF00CC))},
                buttonMapping,this);

        GameClient.getInstance().registerCallback(new Callback<BaseMessage>() {
            @Override
            public void callback(BaseMessage argument) {
                if (argument instanceof TextMessage) {
                    Log.i("SERVER MESSAGE", ((TextMessage) argument).text);
                } else if (argument instanceof StartMessage) {
                    // Do nothing.
                } else if(argument instanceof TurnMessage) {
                    // TODO: CHANGE TO SETUPSTATE?
                    if (game.getAvailableCountries().size() > 0) {
                        game.setState(new SetupState(game));
                    } else {
                        game.setState(new DraftState(game));
                    }
                }
            }
        });

        // Add every country to the list of available countries
        for (Country country : buttonMapping.values()) {
            game.getAvailableCountries().add(country);
        }


        // Add players to side layout
        playerMapping = new HashMap<Integer, Player>();

        LinearLayout layout = findViewById(R.id.linearLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 8, 32, 8);
        for (Player p : game.getPlayers()) {
            ImageView avatar = new ImageView(this);
            avatar.setId(View.generateViewId());
            avatar.setImageResource(R.drawable.ic_army_counter);
            avatar.setLayoutParams(params);
            // TODO: GET RID OF API DEPENDENCY!
            avatar.setImageTintList(ColorStateList.valueOf(p.getColor().toArgb()));
            avatar.setImageTintMode(PorterDuff.Mode.MULTIPLY);
            layout.addView(avatar, LinearLayout.LayoutParams.WRAP_CONTENT);
            playerMapping.put(avatar.getId(), p);
        }



        //open CardActivity by clicking on Card-Button
        Button cardButton = findViewById(R.id.buttonCards);
        cardButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                openCardActivity();
            }

        });

    }


    //method for opening CardActivity
    public void openCardActivity(){
        Intent intent = new Intent(this, CardActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void onClick(View view) {
        game.handleInput(view);
    }

}