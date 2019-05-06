package io.zipcoder.casino;

import io.zipcoder.casino.DiceGame.Yahtzee.Yahtzee;
import io.zipcoder.casino.Interfaces.Game;
import io.zipcoder.casino.Utilities.Player;
import org.junit.Assert;
import org.junit.Test;


public class CasinoTest {

    @Test
    public void setPlayerTest() {
        // Given
        Player expected = new Player("Cara", 1000);

        // When
        Casino.setPlayer(expected);
        Player actual = Casino.getPlayer();

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void setGameTest(){
        // Given
        Player player = new Player("Cara", 1000);
        Game expected = new Yahtzee(player);
        Casino.setGame(expected);

        // When
        Game actual = Casino.getGame();

        // Then
        Assert.assertEquals(expected, actual);
    }


}
