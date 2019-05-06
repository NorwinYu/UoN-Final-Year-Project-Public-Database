package com.musiccoder.clickergame2;

public class AllWildInfo {

    public static Enemy wild1;
    public static Enemy wild2;
    public static Enemy wild3;
    public static Enemy wild4;
    public static Enemy wild5;
    public static Enemy wild6;
    public static Enemy wild7;
    public static Enemy wild8;
    public static Enemy wild9;
    public static Enemy wild10;

    public AllWildInfo() {
        if (wild1 == null) {
            wild1 = new Enemy(60, 3, 1);
            wild2 = new Enemy(200, 30, 120);
            wild3 = new Enemy(500, 60, 220);
            wild4 = new Enemy(2500, 130, 320);
            wild5 = new Enemy(10000, 500, 6000);
            wild6 = new Enemy(27000, 1400, 14000);
            wild7 = new Enemy(40000, 3000,30000);
            wild8 = new Enemy(60000, 5500,60000);
            wild9 = new Enemy(75000, 7000, 90000);
            wild10 = new Enemy(90000, 9000,150000);
        }
    }

    public Enemy getWildInfo(int level) {
        switch (level) {
            case 1: return wild1;
            case 2: return wild2;
            case 3: return wild3;
            case 4: return wild4;
            case 5: return wild5;
            case 6: return wild6;
            case 7: return wild7;
            case 8: return wild8;
            case 9: return wild9;
            case 10: return wild10;
            default: return null;
        }
    }

}

