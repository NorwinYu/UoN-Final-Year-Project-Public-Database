package edu.gatech.cs2340.spacetraders.model;

//import java.util.ArrayList;
//import android.support.annotation.NonNull;

import java.util.HashSet;

/**
 * Solar System class
 */
public class SolarSystem {
    private final Coordinate coordinate;
    private final String name;
    private final TechLevel tech;
    private final Resources resource;
    private final GovType gov;
    private final PirateLevel pirate;
    private final PoliceLevel police;
    private final Condition condition;
    private final HashSet<Planet> planets;

    /**
     * solar system constructor
     * @param coordinate coordinate
     * @param nam string
     * @param tech tech
     * @param resource resources
     * @param g government type
     * @param pi pirate level
     * @param po police level
     * @param cond condition
     * @param planet planet
     */
    public SolarSystem(Coordinate coordinate, String nam, TechLevel tech,
                       Resources resource, GovType g,
                       PirateLevel pi, PoliceLevel po, Condition cond,
                       HashSet<Planet> planet) {
        this.coordinate = coordinate;
        name = nam;
        this.tech = tech;
        this.resource = resource;
        gov = g;
        pirate = pi;
        police = po;
        planets = planet;
        condition = cond;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SolarSystem)) {
            return false;
        }
        SolarSystem newP = (SolarSystem) other;
        return newP.getName().equals(name) || newP.getCoordinate().equals(coordinate);
    }

    /**
     * get random planets
     * @return planet
     */
    public Planet getRandomPlanet() {
        int i = 0;
        int rand = (int) (Math.random() * 3);
        //Planet ans = null;
        Planet ans = null;
        for (Planet p:planets) {
            ans = p;
            if (i == rand) {
                return p;
            }
            i++;
        }
        return ans;
    }

    /**
     * get coordinate
     * @return coordinate
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * get name
     * @return string
     */
    public String getName() {
        return name;
    }

    /**
     * get tech level
     * @return tech level
     */
    public TechLevel getTechLevel() {
        return tech;
    }

    /**
     * get resources
     * @return resources
     */
    public Resources getResource() {
        return resource;
    }

    /**
     * get government level
     * @return government level
     */
    public GovType getGovLevel() {return gov;}

    /**
     * get pirate level
     * @return pirate level
     */
    public PirateLevel getPirateLevel() {return pirate;}
    /**
     * get police level
     * @return police level
     */
    public PoliceLevel getPoliceLevel() {return police;}
    /**
     * get planet
     * @return planet
     */
  //  public HashSet<Planet> getPlanets() {
  //      return planets;
  //  }

// --Commented out by Inspection START (4/7/19, 11:27 PM):
//    /**
//     * has planet
//     * @param name string
//     * @return boolean
//     */
//    public boolean hasPlanet(String name) {
//        for(Planet a:planets) {
//            if (a.getName().equals(name)) {
//                return true;
//            }
//        }
//        return false;
//    }
// --Commented out by Inspection STOP (4/7/19, 11:27 PM)

// --Commented out by Inspection START (4/7/19, 11:27 PM):
//    /**
//     * get planet
//     * @param name string
//     * @return planet
//     */
//    public Planet getPlanetByName(String name) {
//        for(Planet p:planets) {
//            if (p.getName().equals(name)) {
//                return p;
//            }
//        }
//        throw new java.util.NoSuchElementException("No planet with name " + name +
//                " exist in the solar system " + name + ".");
//    }
// --Commented out by Inspection STOP (4/7/19, 11:27 PM)

// --Commented out by Inspection START (4/7/19, 11:27 PM):
//    /**
//     * get condition
//     * @return condition
//     */
//    public Condition getCondition() {
//        return condition;
//    }
// --Commented out by Inspection STOP (4/7/19, 11:27 PM)
    public String toString() {
        StringBuilder p = new StringBuilder();
        for(Planet a:planets) {
            p.append(a).append(" ");
        }
        return "Name of Solar System: " + name + "\n" + coordinate +
                "\nTechLevel: " + tech + "\nResources: " + resource +
                "\nGovernment Type: " + gov + "\nPirate Level: " +
                pirate + "\nPolice Level: " + police + "\nCondition: " + condition
                + "\nPlanets: " + p + "\n";
    }


}
