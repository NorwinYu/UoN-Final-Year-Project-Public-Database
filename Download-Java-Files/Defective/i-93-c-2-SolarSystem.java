package edu.gatech.cs2340.spacetraders.model;

import java.util.ArrayList;
import java.util.HashSet;

public class SolarSystem {
    private Coordinate coord;
    private String name;
    private TechLevel tech;
    private Resources resource;
    private GovType gov;
    private PirateLevel pirate;
    private PoliceLevel police;
    private Condition condition;
    private HashSet<Planet> planets;

    public SolarSystem(Coordinate coor, String nam, TechLevel tec, Resources recourc, GovType g, PirateLevel pi, PoliceLevel po, Condition cond, HashSet<Planet> planet) {
        coord = coor;
        name = nam;
        tech = tec;
        resource = recourc;
        gov = g;
        pirate = pi;
        police = po;
        planets = planet;
        condition = cond;
    }

    @Override
    public boolean equals(Object other) {
        SolarSystem newP = (SolarSystem) other;
        return newP.getName().equals(name) || newP.getCoordinate().equals(coord);
    }

    public Coordinate getCoordinate() {
        return coord;
    }
    public String getName() {
        return name;
    }
    public TechLevel getTechLevel() {
        return tech;
    }
    public Resources getResource() {
        return resource;
    }
    public GovType getGovLevel() {return gov;}
    public PirateLevel getPirateLevel() {return pirate;}
    public PoliceLevel getPoliceLevel() {return police;}
    public HashSet<Planet> getPlanets() {
        return planets;
    }
    public boolean hasPlanet(String name) {
        for(Planet a:planets) {
            if (a.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public Planet getPlanetByName(String name) {
        for(Planet p:planets) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        throw new java.util.NoSuchElementException("No planet with name " + name + " exist in the solar system " + name + ".");
    }
    public Condition getCondition() {
        return condition;
    }
    public String toString() {
        String p = "";
        for(Planet a:planets) {
            p = p + a + " ";
        }
        return "Name of Solar System: " + name + "\n" + coord +
                "\nTechLevel: " + tech + "\nResources: " + resource +
                "\nGovernment Type: " + gov + "\nPirate Level: " +
                pirate + "\nPolice Level: " + police + "\nCondition: " + condition
                + "\nPlanets: " + p + "\n";
    }


}
