package edu.gatech.cs2340.spacetraders.model;

/**
 * police level class
 */
public enum PoliceLevel {
    FEW (0),
    SEVERAL(1),
    MANY (2),
    ABUNDANT (3);

    private final int value;

    PoliceLevel(int v) {this.value = v;}
    //was public
    private int getValue() {return value;}

    /**
     * getters
     * @param a integer
     * @return police level
     */
    public static PoliceLevel getLevelByNumber(int a) {
        for (PoliceLevel level: values()) {
            if (level.getValue() == a) {
                return level;
            }
        }
        return FEW;
    }
}
