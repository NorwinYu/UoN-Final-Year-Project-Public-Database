package edu.gatech.cs2340.spacetraders.model;

/**
 * Planet class
 */
public class Planet {
    //added final
    private final String name;
    private final Market thisMarket;

    /**
     * Constructor for planet
     * @param nam name
     * @param market market
     */
    public Planet(String nam, Market market) {
        name = nam;
        thisMarket = market;
    }

    /**
     * getters for name
     * @return string
     */
    private String getName() {
        return name;
    }

    /**
     * getters for market
     * @return market
     */
    public Market getMarket() {
        return thisMarket;
    }
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Planet)) {
            return false;
        }
        Planet oth = (Planet) (other);
        return oth.getName().equals(name);
    }
    public String toString() {
        return name;
    }
}
