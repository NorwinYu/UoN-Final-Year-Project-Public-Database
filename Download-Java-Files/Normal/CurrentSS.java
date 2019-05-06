package edu.gatech.cs2340.spacetraders.views;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import edu.gatech.cs2340.spacetraders.R;
import edu.gatech.cs2340.spacetraders.model.ModelFacade;
import edu.gatech.cs2340.spacetraders.model.SolarSystem;

/**
 * CurrentSS class
 */
public class CurrentSS extends AppCompatActivity {

    private SolarSystem currentSS;

    private TextView planetText;
    private TextView resText;
    private TextView techText;
    private TextView govText;
    private TextView polText;
    private TextView pirText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_solarsystem);
        currentSS = ModelFacade.getInstance().getGame().getMyUniverse().getCurrentSolarSystem();
        setText();
        updateText();
    }

    //was public
    private void setText() {
        planetText = findViewById(R.id.planetText);
        resText = findViewById(R.id.resText);
        techText = findViewById(R.id.techText);
        govText = findViewById(R.id.govText);
        polText = findViewById(R.id.polText);
        pirText = findViewById(R.id.pirText);
    }

    //was public
    private void updateText() {
        planetText.setText(currentSS.getName());
        resText.setText(currentSS.getResource().toString());
        techText.setText(currentSS.getTechLevel().toString());
        govText.setText(currentSS.getGovLevel().toString());
        polText.setText(currentSS.getPoliceLevel().toString());
        pirText.setText(currentSS.getPirateLevel().toString());
    }
}