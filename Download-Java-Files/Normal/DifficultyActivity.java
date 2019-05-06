package edu.gatech.cs2340.spacetraders.views;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
//import android.widget.Spinner;
//import edu.gatech.cs2340.spacetraders.R;

/**
 * Difficulty Activity class
 */
public class DifficultyActivity extends Activity implements AdapterView.OnItemSelectedListener {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        parent.getItemAtPosition(pos);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
