package edu.gatech.cs2340.spacetraders.model;

//import android.util.Log;

import java.util.ArrayList;
//import java.util.Collection;
import java.util.HashSet;


/**
 * Universe class
 */
public class Universe {
    //added final
    private final HashSet<SolarSystem> solarSystems;
    private SolarSystem currentSolarSystem;
    private Planet currentPlanet;

    /**
     * Universe constructor
     */
    public Universe() {
       // Collection<String> solarNames = new ArrayList<>();
         final String[] names = {
                "Acamar",
                "Adahn",		// The alternate personality for The Nameless One in "Planescape: Torment"
                "Aldea",
                "Andevian",
                "Antedi",
                "Balosnee",
                "Baratas",
                "Brax",			// One of the heroes in Master of Magic
                "Bretel",		// This is a Dutch device for keeping your pants up.
                "Calondia",
                "Campor",
                "Capelle",		// The city I lived in while programming this game
                "Carzon",
                "Castor",		// A Greek demi-god
                "Cestus",
                "Cheron",
                "Courteney",	// After Courteney Cox…
                "Daled",
                "Damast",
                "Davlos",
                "Deneb",
                "Deneva",
                "Devidia",
                "Draylon",
                "Drema",
                "Endor",
                "Esmee",		// One of the witches in Pratchett's Discworld
                "Exo",
                "Ferris",		// Iron
                "Festen",		// A great Scandinavian movie
                "Fourmi",		// An ant, in French
                "Frolix",		// A solar system in one of Philip K. Dick's novels
                "Gemulon",
                "Guinifer",		// One way of writing the name of king Arthur's wife
                "Hades",		// The underworld
                "Hamlet",		// From Shakespeare
                "Helena",		// Of Troy
                "Hulst",		// A Dutch plant
                "Iodine",		// An element
                "Iralius",
                "Janus",		// A seldom encountered Dutch boy's name
                "Japori",
                "Jarada",
                "Jason",		// A Greek hero
                "Kaylon",
                "Khefka",
                "Kira",			// My dog's name
                "Klaatu",		// From a classic SF movie
                "Klaestron",
                "Korma",		// An Indian sauce
                "Kravat",		// Interesting spelling of the French word for "tie"
                "Krios",
                "Laertes",		// A king in a Greek tragedy
                "Largo",
                "Lave",			// The starting system in Elite
                "Ligon",
                "Lowry",		// The name of the "hero" in Terry Gilliam's "Brazil"
                "Magrat",		// The second of the witches in Pratchett's Discworld
                "Malcoria",
                "Melina",
                "Mentar",		// The Psilon home system in Master of Orion
                "Merik",
                "Mintaka",
                "Montor",		// A city in Ultima III and Ultima VII part 2
                "Mordan",
                "Myrthe",		// The name of my daughter
                "Nelvana",
                "Nix",			// An interesting spelling of a word meaning "nothing" in Dutch
                "Nyle",			// An interesting spelling of the great river
                "Odet",
                "Og",			// The last of the witches in Pratchett's Discworld
                "Omega",		// The end of it all
                "Omphalos",		// Greek for navel
                "Orias",
                "Othello",		// From Shakespeare
                "Parade",		// This word means the same in Dutch and in English
                "Penthara",
                "Picard",		// The enigmatic captain from ST:TNG
                "Pollux",		// Brother of Castor
                "Quator",
                "Rakhar",
                "Ran",			// A film by Akira Kurosawa
                "Regulas",
                "Relva",
                "Rhymus",
                "Rochani",
                "Rubicum",		// The river Ceasar crossed to get into Rome
                "Rutia",
                "Sarpeidon",
                "Sefalla",
                "Seltrice",
                "Sigma",
                "Sol",			// That's our own solar system
                "Somari",
                "Stakoron",
                "Styris",
                "Talani",
                "Tamus",
                "Tantalos",		// A king from a Greek tragedy
                "Tanuga",
                "Tarchannen",
                "Terosa",
                "Thera",		// A seldom encountered Dutch girl's name
                "Titan",		// The largest moon of Jupiter
                "Torin",		// A hero from Master of Magic
                "Triacus",
                "Turkana",
                "Tyrus",
                "Umberlee",		// A god from AD&D, which has a prominent role in Baldur's Gate
                "Utopia",		// The ultimate goal
                "Vadera",
                "Vagra",
                "Vandor",
                "Ventax",
                "Xenon",
                "Xerxes",		// A Greek hero
                "Yew",			// A city which is in almost all of the Ultima games
                "Yojimbo",		// A film by Akira Kurosawa
                "Zalkon",
                "Zuul"};
        solarSystems = new HashSet<>();
        while (solarSystems.size() < 10) {
            int CO_SIZE = 150;
            Coordinate co = new Coordinate((int) (Math.random() * CO_SIZE + 1),
                    (int) (Math.random() * 100 + 1));
            String name = names[(int) (Math.random() * names.length)];
            TechLevel level = TechLevel.getLevelByNumber((int) (Math.random() * 7));
            int RES_SIZE = 15;
            Resources re = Resources.getResourcesByNumber((int) (Math.random() * RES_SIZE));
            GovType gov = GovType.getTypeByNumber((int) (Math.random() * 4));
            double PIRATE_LEVEL = 3.99;
            PirateLevel pirate = PirateLevel.getLevelByNumber((int) (Math.random() * PIRATE_LEVEL));
            double POLICE_LEVEL = 3.99;
            PoliceLevel police = PoliceLevel.getLevelByNumber((int) (Math.random() * POLICE_LEVEL));
            int COND_SIZE = 20;
            Condition cond = Condition.getConditionByNumber((int) (Math.random() * COND_SIZE));
            HashSet<Planet> planets = new HashSet<>();
            while (planets.size() < 3) {
                Market mar = new Market(cond, re, level);
                Planet pp = new Planet(names[(int) (Math.random() * names.length)], mar);
                if (currentPlanet == null) {
                     currentPlanet = pp;
                }
                planets.add(pp);
                //test code
                //Log.d("market: " , mar.toString());
            }
            SolarSystem ss = new SolarSystem(co, name, level, re, gov, pirate, police,
                    cond, planets);
            if (currentSolarSystem == null) {
                currentSolarSystem = ss;
            }
            solarSystems.add(ss);

          //  solarNames.add(name);
        }
    }

    /**
     * get solar system travel
     * @param fuel integer
     * @return arraylist
     */
    public ArrayList<SolarSystem> getSolarSystemsToTravel(int fuel) {
        ArrayList<SolarSystem> travelables = new ArrayList<>();
        for (SolarSystem s:solarSystems) {
            int a = s.getCoordinate().getX() + s.getCoordinate().getY();
            int b = currentSolarSystem.getCoordinate().getX() +
                    currentSolarSystem.getCoordinate().getY();
            int distance = Math.abs(a - b);
            if (fuel >= distance && !s.getName().equals(currentSolarSystem.getName())) {
                travelables.add(s);
            }
        }
        return travelables;
    }

    /**
     * Travel
     * @param solarSysName solar system name
     * @param player player
     */
    public void travel(String solarSysName, Player player) {
        SolarSystem newS = getSolarSystemByName(solarSysName);
        int a = newS.getCoordinate().getX() + newS.getCoordinate().getY();
        int b = currentSolarSystem.getCoordinate().getX() +
                currentSolarSystem.getCoordinate().getY();
        int distance = Math.abs(a - b);

        //System.out.println("fuel before travel: " + player.getMyShip().getFuel());

        player.getMyShip().setFuel(player.getMyShip().getFuel() - distance);

        //System.out.println("new solar: " + newS.getName() + " distance: " + distance +
                //" new fuel:" + player.getMyShip().getFuel());

        currentSolarSystem = getSolarSystemByName(solarSysName);
        currentPlanet = currentSolarSystem.getRandomPlanet();
    }

    //was public

    /**
     * getters for solar system
     * @param name string
     * @return solar system
     */
    private SolarSystem getSolarSystemByName(String name) {
        for (SolarSystem s:solarSystems) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        throw new java.util.NoSuchElementException("No solar system with name " +
                name + "exist in this universe.");
    }

// --Commented out by Inspection START (4/7/19, 11:27 PM):
//    /**
//     * get planet
//     * @param name string
//     * @return planet
//     */
//    public Planet getPlanetByName(String name) {
//        for (SolarSystem s:solarSystems) {
//            for (Planet p: s.getPlanets()) {
//                if (name.equals(p.getName())) {
//                    return p;
//                }
//            }
//        }
// --Commented out by Inspection START (4/7/19, 11:27 PM):
////        throw new java.util.NoSuchElementException("No planet with name " + name
////                + " exist in this universe");
////    }
//// --Commented out by Inspection STOP (4/7/19, 11:27 PM)
//
//    /**
// --Commented out by Inspection STOP (4/7/19, 11:27 PM)
     // get solar system
     // @return hashset
     //

    /*
      get solar system
      @return a set of solar system
     */
  //  public HashSet<SolarSystem> getSolarSystems() {
    //    return solarSystems;
 //   }

    /**
     * getter for solar system
     * @return soalr system
     */
    public SolarSystem getCurrentSolarSystem() {
        return currentSolarSystem;
    }

    /**
     * get planet
     * @return planet
     */
    public Planet getCurrentPlanet() {
        return currentPlanet;
    }

    /*
      set current solar system
      @param s solar system
     */
  //  public void setCurrentSolarSystem(SolarSystem s) {
   //     currentSolarSystem = s;
  //  }

    /**
     * set current planet
   //* @param p planet
     */
 //   public void setCurrentPlanet(Planet p) {
  //      currentPlanet = p;
  //  }
    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder("\n");
        for (SolarSystem s: solarSystems) {
            ans.append(s).append("\n");
        }
        return ans.toString();
    }
}
