package edu.gatech.cs2340.spacetraders.model;

/**
 * tech level class
 */
public enum TechLevel {
    PRE_AGRICULTURE (0),
    AGRICULTURE (1),
    MEDIEVAL (2),
    RENAISSANCE (3),
    EARLY_INDUSTRIAL(4),
    INDUSTRIAL (5),
    POST_INDUSTRIAL (6),
    HI_TECH (7);

    private final int value;
    TechLevel(final int newValue) {
        value = newValue;
    }

    /**
     * get value
     * @return integer
     */
    public int getValue() {
        return value;
    }

    /**
     * get level
     * @param a integer
     * @return tech level
     */
    public static TechLevel getLevelByNumber(int a) {
        for (TechLevel level: values()) {
            if (level.getValue() == a) {
                return level;
            }
        }
        return PRE_AGRICULTURE;
    }
}
