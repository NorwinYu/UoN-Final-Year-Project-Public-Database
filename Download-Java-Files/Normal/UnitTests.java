package com.musiccoder.clickergame2;

import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTests {
    @Test
    public void testPlayerAttacksEnemy() throws Exception {
        Sword sword = new Sword(5);
        Player player = new Player(10,100,sword);
        Enemy enemy = new Enemy(10,5,5);

        Encounter encounter = new Encounter();
        encounter.playerAttacksEnemy(player,enemy);

        assertEquals(5, enemy.getCurrentHealth());
    }

    @Test
    public void testEnemyAttacksPlayer() throws Exception {
        Sword sword = new Sword(5);
        Player player = new Player(10,100,sword);
        Enemy enemy = new Enemy(10,4,5);

        Encounter encounter = new Encounter();
        encounter.enemyAttacksPlayer(enemy,player);

        assertEquals(6, player.getCurrentHealth());
    }
}