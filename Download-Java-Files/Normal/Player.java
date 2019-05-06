package com.musiccoder.clickergame2;

public class Player extends Entity{
    private int coins;
    private Sword sword;
    public static Player playerInstance;

    public Player(int maxHealth, int coins, Sword sword) {
            super(maxHealth);
            this.coins = coins;
            this.sword = sword;
    }

    static Player getInstance(int maxHealth, int coins, Sword sword) {
        if (playerInstance == null) {
            playerInstance = new Player(maxHealth, coins, sword);
        }
        return playerInstance;
    }

    static Player getInstance() {
        return playerInstance;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Sword getSword() {
        return sword;
    }

    public void setSword(Sword sword) {
        this.sword = sword;
    }
}
