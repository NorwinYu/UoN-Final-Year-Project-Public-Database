package edu.gatech.cs2340.spacetraders.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;

public class Universe {
    private HashSet<SolarSystem> solarSystems;
    private ArrayList<String> solarNames;
    private final String[] names = {
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

    public Universe() {
        solarSystems = new HashSet<>();
        solarNames = new ArrayList<>();
        while (solarSystems.size() < 10) {
            Coordinate co = new Coordinate((int) (Math.random() * 150 + 1), (int) (Math.random() * 100 + 1));
            String name = names[(int) (Math.random() * names.length)];
            TechLevel level = TechLevel.getLevelByNumber((int) (Math.random() * 7));
            Resources re = Resources.getResourcesByNumber((int) (Math.random() * 15));
            GovType gov = GovType.getTypeByNumber((int) (Math.random() * 4));
            PirateLevel pirate = PirateLevel.getLevelByNumber((int) (Math.random() * 3));
            PoliceLevel police = PoliceLevel.getLevelByNumber((int) (Math.random() * 3));
            Condition cond = Condition.getConditionByNumber((int) (Math.random() * 20));
            HashSet<Planet> planets = new HashSet<>();
            while (planets.size() < 3) {
                Market mar = new Market(cond, re, level);
                planets.add(new Planet(names[(int) (Math.random() * names.length)], mar));
                //test code
                //Log.d("market: " , mar.toString());
            }
            solarSystems.add(new SolarSystem(co, name, level, re, gov, pirate, police, cond, planets));
            solarNames.add(name);
        }
    }

    public SolarSystem getSolarSystemByName(String name) {
        for (SolarSystem s:solarSystems) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        throw new java.util.NoSuchElementException("No solar system with name " + name + "exist in this universe.");
    }
    public HashSet<SolarSystem> getSolarSystems() {
        return solarSystems;
    }
    public String toString() {
        String ans = "\n";
        for (SolarSystem s: solarSystems) {
            ans += s + "\n";
        }
        return ans;
    }
}
