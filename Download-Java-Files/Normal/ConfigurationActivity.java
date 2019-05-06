package edu.gatech.cs2340.spacetraders.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import edu.gatech.cs2340.spacetraders.model.DifficultyStanding;
//import edu.gatech.cs2340.spacetraders.model.Game;
//import edu.gatech.cs2340.spacetraders.model.Player;
//import edu.gatech.cs2340.spacetraders.viewmodels.GameViewModel;
import edu.gatech.cs2340.spacetraders.R;

/**
 * This class displays the opening dialog and gets the user inputs.
 */
public class ConfigurationActivity extends AppCompatActivity {
    //private GameViewModel viewModel;
    // --Commented out by Inspection (4/7/19, 11:27 PM):private Game game;
    //private boolean editing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_person);
        Spinner difficultySpinner;
        difficultySpinner = findViewById(R.id.difficultyLevel);

        ArrayAdapter<DifficultyStanding> difficultyAdapter =new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, DifficultyStanding.values());
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficultyAdapter);

// --Commented out by Inspection START (4/7/19, 11:27 PM):
//        difficultySpinner.setSelection(game.getDS().ordinal());
//    }
//
//    /**
//     * Actions when pressed
//     * @param view the view
//     */
//    public void onAddPressed(View view) {
//        game.setDS((DifficultyStanding) difficultySpinner.getSelectedItem());
// --Commented out by Inspection STOP (4/7/19, 11:27 PM)
        finish();
    }
}
