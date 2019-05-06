package com.musiccoder.clickergame2;

public class Enemy extends Entity {
    private int attackValue;
    private int lootAmount;

    public Enemy(int maxHealth, int attackValue, int lootAmount) {
        super(maxHealth);
        this.attackValue = attackValue;
        this.lootAmount = lootAmount;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public int getAttackValue() {
        return this.attackValue;
    }

    public void setLootAmount(int lootAmount) {
        this.lootAmount = lootAmount;
    }

    public int getLootAmount() {
        return this.lootAmount;
    }
}
