package edu.gatech.cs2340.spacetraders.model;

/**
 * Pirate level enum
 */
public enum PirateLevel {
    FEW (0),
    SEVERAL(1),
    MANY (2),
    ABUNDANT (3);

    private final int value;

    PirateLevel(int v) {this.value = v;}
    //was public

    /**
     * getter for value
     * @return int
     */
    public int getValue() {return value;}

    /**
     * getters for pirate level
     * @param a integer
     * @return pirate level
     */
    public static PirateLevel getLevelByNumber(int a) {
        for (PirateLevel level: values()) {
            if (level.getValue() == a) {
                return level;
            }
        }
        return FEW;
    }
}
