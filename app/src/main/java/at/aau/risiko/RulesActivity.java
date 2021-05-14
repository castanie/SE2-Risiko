package at.aau.risiko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class RulesActivity extends AppCompatActivity {

    private PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        pdfView = findViewById(R.id.pdfView);
        pdfView.fromAsset("Game_rules.pdf").defaultPage(0).load();




    }
}