package at.aau.risiko;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import java.util.HashMap;

import at.aau.risiko.core.Country;
import at.aau.risiko.core.Game;
import at.aau.risiko.core.Player;

public class MapActivity extends AppCompatActivity {

    Game game;

    HashMap<Integer, Country> buttonMapping;
    HashMap<Integer, int[]> neighborMapping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Find all buttons in view and link to countries:

        buttonMapping = new HashMap<Integer, Country>();
        int[] buttons = ((Group) findViewById(R.id.group)).getReferencedIds();
        for (int button : buttons) {
            buttonMapping.put(button, new Country(((Button) findViewById(button)).getContentDescription().toString()));
            // Log.i("COUNTRY LISTED", buttonMapping.get(button).getName());
        }

        // Log.i("RES ID TEST", this.getResources().getIdentifier("buttonAlaska", "id", this.getPackageName()) + " should equal " + R.id.buttonAlaska);
        neighborMapping = new HashMap<Integer, int[]>();

        // Put all countries bordering a country into a hashtable:

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
                Log.i("REGISTERED NEIGHBOR", buttonMapping.get(i).getName() + " added neighbor " + buttonMapping.get(j).getName());
            }
        }

        // Start game:

        game = new Game(new Player[3], buttonMapping);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void onClick(View view) {
        game.handleInput(view);
    }

}