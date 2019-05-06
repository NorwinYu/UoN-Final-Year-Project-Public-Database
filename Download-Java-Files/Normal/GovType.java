package edu.gatech.cs2340.spacetraders.model;

/**
 * Government type
 */
public enum GovType {
    DEMOCRACY (0),
    DICTATORSHIP (1),
    MILITARY (2),
    MONARCHY (3),
    SOCIALIST (4);

    private final int value;

    GovType(int v) {this.value = v;}
    private int getValue() {return value;} //was public

    /**
     * Getter for type number
     * @param a integer
     * @return government type
     */
    public static GovType getTypeByNumber(int a) {
        if ((a < 0) || (a > 4)) {
            throw new IllegalArgumentException("Out of range");
        }
        for (GovType level: values()) {
            if (level.getValue() == a) {
                return level;
            }
        }
        return DEMOCRACY;
    }
}
