package edu.gatech.cs2340.spacetraders.views;

//import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import edu.gatech.cs2340.spacetraders.R;
import edu.gatech.cs2340.spacetraders.model.DifficultyStanding;
import edu.gatech.cs2340.spacetraders.model.Game;
import edu.gatech.cs2340.spacetraders.model.ModelFacade;
import edu.gatech.cs2340.spacetraders.model.Player;
import edu.gatech.cs2340.spacetraders.model.Universe;

/**
 * Class that adds new person
 */
public class addNewPerson extends AppCompatActivity {


    private final int ABILITY_POINTS = 16;
    private boolean isNameGiven;
    private int pilotPts;
    private int engineerPts;
    private int tradePts;
    private int fightPts;
    private int pointsGiven;
    private int totalPoints;
    private TextView pilotText;
    private TextView fighterText;
    private TextView traderText;
    private TextView engineerText;
    private TextView totalPointsText;
    private Button okButton;
    private Spinner difficultySpinner;
    private EditText playerNameBox;
    private Game myGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        totalPoints = 16;
        setContentView(R.layout.activity_add_new_person);
        pilotText = findViewById(R.id.pilotPoints);
        fighterText = findViewById(R.id.fighterPoints);
        traderText = findViewById(R.id.traderPoints);
        engineerText = findViewById(R.id.engineerPoints);
        totalPointsText = findViewById(R.id.totalPoints);
        playerNameBox = findViewById(R.id.playerName);
        okButton = findViewById(R.id.OkButton);
        difficultySpinner = findViewById(R.id.difficultyLevel);

        ArrayAdapter<DifficultyStanding> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, DifficultyStanding.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(adapter);
        //ConfigurationViewModel configVM;
        //configVM = ViewModelProviders.of(this).get(ConfigurationViewModel.class);

        playerNameBox.addTextChangedListener(nameFieldWatcher);
        okButton.setOnClickListener(v -> {
            DifficultyStanding difficulty =
                    (DifficultyStanding) difficultySpinner.getSelectedItem();
            String playerName = playerNameBox.getText().toString();
            Player myPlayer =
                    new Player(playerName, difficulty, pilotPts,
                            engineerPts, tradePts, fightPts);
            Universe myUniverse = new Universe();
            //configVM.createGame(difficulty, myPlayer, myUniverse);
            myGame = new Game(difficulty, myPlayer, myUniverse);
            ModelFacade.getInstance().setGame(myGame);
            Log.d("My Game's Information", ModelFacade.getInstance().getGame().toString());
            Log.d("My Universe Information",
                    ModelFacade.getInstance().getGame().getMyUniverse().toString());
            startActivity(new Intent(addNewPerson.this, MainGame.class));

        });
    }

    //added final
    private final TextWatcher nameFieldWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String playerName = playerNameBox.getText().toString();
            isNameGiven = !playerName.isEmpty();
            okButton.setEnabled((isNameGiven) && (totalPoints == 0));
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    /**
     * Add value when clicked
     * @param v view
     */
    public void pilotPlusButtonOnClick(View v) {
        if ((pilotPts < 10) && (pointsGiven < ABILITY_POINTS)) {
            pilotText.setText(Integer.toString(++pilotPts));
            pointsGiven++;
            plusAbilityPointsText(totalPointsText);
        }
    }

    /**
     * Delete value when clicked
     * @param v view
     */
    public void pilotMinusButtonOnClick(View v) {
        if (pilotPts > 0) {
            pilotText.setText(Integer.toString(--pilotPts));
            pointsGiven--;
            minusAbilityPointsText(totalPointsText);
        } else {
            pilotText.setText(Integer.toString(0));
        }
    }

    /**
     * Add value when clicked
     * @param v view
     */
    public void fighterPlusButtonOnClick(View v) {
        if ((fightPts < 10) && (pointsGiven < ABILITY_POINTS)) {
            fighterText.setText(Integer.toString(++fightPts));
            pointsGiven++;
            plusAbilityPointsText(totalPointsText);
        }
    }

    /**
     * Delete value when clicked
     * @param v view
     */
    public void fighterMinusButtonOnClick(View v) {
        if (fightPts > 0) {
            fighterText.setText(Integer.toString(--fightPts));
            pointsGiven--;
            minusAbilityPointsText(totalPointsText);
        } else {
            fighterText.setText(Integer.toString(0));
        }
    }

    /**
     * Add value when clicked
     * @param v view
     */
    public void traderPlusButtonOnClick(View v) {
        if ((tradePts < 10) && (pointsGiven < ABILITY_POINTS)) {
            traderText.setText(Integer.toString(++tradePts));
            pointsGiven++;
            plusAbilityPointsText(totalPointsText);
        }
    }

    /**
     * Delete value when clicked
     * @param v view
     */
    public void traderMinusButtonOnClick(View v) {
        if (tradePts > 0) {
            traderText.setText(Integer.toString(--tradePts));
            pointsGiven--;
            minusAbilityPointsText(totalPointsText);
        } else {
            traderText.setText(Integer.toString(0));
        }
    }

    /**
     * Add value when clicked
     * @param v view
     */
    public void engineerPlusButtonOnClick(View v) {
        if ((engineerPts < 10) && (pointsGiven < ABILITY_POINTS)) {
            engineerText.setText(Integer.toString(++engineerPts));
            pointsGiven++;
            plusAbilityPointsText(totalPointsText);
        }
    }

    /**
     * Delete value when clicked
     * @param v view
     */
    public void engineerMinusButtonOnClick(View v) {
        if (engineerPts > 0) {
            engineerText.setText(Integer.toString(--engineerPts));
            pointsGiven--;
            minusAbilityPointsText(totalPointsText);
        } else {
            engineerText.setText(Integer.toString(0));
        }
    }

    //was public

    /**
     * Delete value when clicked
     * @param totalPointsText points
     */
    private void minusAbilityPointsText(TextView totalPointsText ) {
        totalPointsText.setText(Integer.toString(++totalPoints));
        okButton.setEnabled(false);
    }

    //was public

    /**
     * Total point
     * @param totalPointsText points
     */
    private void plusAbilityPointsText(TextView totalPointsText ) {
            totalPointsText.setText(Integer.toString(--totalPoints));
            okButton.setEnabled((totalPoints == 0) && (isNameGiven));
        }

}
