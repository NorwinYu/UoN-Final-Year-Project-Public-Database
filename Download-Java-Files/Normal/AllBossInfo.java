package com.musiccoder.clickergame2;

public class AllBossInfo {

    public static Enemy boss1;
    public static Enemy boss2;
    public static Enemy boss3;
    public static Enemy boss4;
    public static Enemy boss5;
    public static Enemy boss6;
    public static Enemy boss7;
    public static Enemy boss8;
    public static Enemy boss9;
    public static Enemy boss10;

    public AllBossInfo() {
        if (boss1 == null) {
            boss1 = new Enemy(100, 10, 100);
            boss2 = new Enemy(300, 40, 200);
            boss3 = new Enemy(800, 80, 300);
            boss4 = new Enemy(5000, 190, 4000);
            boss5 = new Enemy(20000, 800, 10000);
            boss6 = new Enemy(35000, 2000, 20000);
            boss7 = new Enemy(50000, 5200,50000);
            boss8 = new Enemy(70000, 6500, 80000);
            boss9 = new Enemy(85000, 8000, 100000);
            boss10 = new Enemy(100000, 10000,200000);
        }
    }

}
