package edu.gatech.cs2340.spacetraders.model;
/**
 * This is the interface to the domain/business classes from anywhere else in the application
 *
 *
 * This class provides the player for the application.
 *
 * It is a Singleton so that it can be accessed from anywhere in the application
 */
public final class ModelFacade {
    private Game myGame;
    /** Singleton Pattern Code
     *  this allows us to get access to this class
     *  anywhere, which will allow our View models to access
     *  the "back end"  more easily
     */
    private static final ModelFacade ourInstance = new ModelFacade();

    /**
     * getters for model facade
     * @return model facade
     */
    public static ModelFacade getInstance() {
        return ourInstance;
    }
    /**
     * Make a new Model instance (should have parameters of the data entered by the player)
     */
    private ModelFacade() {
        myGame = new Game();
    }

    /**
     * setter game
     * @param a game
     */
    public void setGame(Game a) {
        myGame = a;
    }

    /**
     * getters for game
     * @return game
     */
    public Game getGame() {
        return myGame;
    }
}
