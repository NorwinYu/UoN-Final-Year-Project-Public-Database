package a.b.project;

import android.app.Activity;
import android.view.KeyEvent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.Display;
import android.util.DisplayMetrics;
import android.graphics.Point;
import android.graphics.Paint;


public class GameActivity extends Activity  {

    // Our object to handle the View
    private TDView gameView;

    
    // This is where the "Play" button from HomeActivity sends us
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	// Get a Display object to access screen details
	Display display = getWindowManager().getDefaultDisplay();
	// Load the resolution into a Point object
	Point size = new Point();
	display.getSize(size);

	// Create an instance of our Tappy Defender View (TDView)
	// Also passing in "this" which is the Context of our app
	gameView = new TDView(this, size.x, size.y);

	// Make our gameView the view for the Activity
	setContentView(gameView);
	
    }


   // If the player hits the back button, quit the app
   public boolean onKeyDown(int keyCode, KeyEvent event) {
     if (keyCode == KeyEvent.KEYCODE_BACK) {
	 //finish();
          return true;
     }
     return false;
   }

    
    // If the Activity is paused make sure to pause our thread
    @Override
    protected void onPause() {
	super.onPause();
	gameView.pause();
    }

    // If the Activity is resumed make sure to resume our thread
    @Override
    protected void onResume() {
	super.onResume();
	gameView.resume();
    }


    
}
