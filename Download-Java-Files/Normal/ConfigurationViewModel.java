package edu.gatech.cs2340.spacetraders.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
//import android.util.Log;

//import edu.gatech.cs2340.spacetraders.model.DifficultyStanding;
//import edu.gatech.cs2340.spacetraders.model.Game;
//import edu.gatech.cs2340.spacetraders.model.ModelFacade;
//import edu.gatech.cs2340.spacetraders.model.Player;
//import edu.gatech.cs2340.spacetraders.model.Universe;

/**
 * This class handles the user input events and
 * sets up the data for the model to be initialized
 */
class ConfigurationViewModel extends AndroidViewModel {
    // --Commented out by Inspection (4/7/19, 11:27 PM):private Game myGame;
    //private Player myPlayer;

    /**
     * Constructor
     * @param application application
     */
    public ConfigurationViewModel(Application application) {
        super(application);
    }
//    public void createGame(DifficultyStanding ds, Player p, Universe u) {
//        Log.d("u know ur in here","yuh yuh");
//        myGame = new Game(ds, p, u);
//    }
//    public Player getPlayer() {
//        return myGame.getPlayer();
//    }
//    public Universe getUniverse() {
//        return myGame.getMyUniverse();
//    }
//    public Game getGame() {
//        return myGame;
//    }
}
