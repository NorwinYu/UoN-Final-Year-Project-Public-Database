package io.zipcoder.casino.CardGames.BlackJack;

import io.zipcoder.casino.CardGames.CardUtils.Deck;
import io.zipcoder.casino.Utilities.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BlackjackTest {

    private Player player;
    private Deck deck;

    @Before
    public void setUp(){
        player = new Player("name", 1000);
        deck = new Deck(10);
    }
    @Test
    public void constructorTest(){
        //Given
        Blackjack blackjack = new Blackjack(player);
        double expected = 0;

        //When
        double actual = blackjack.getPot();

        //Then
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void testValidateBet() {
        //Given
        Blackjack blackjack = new Blackjack(player);



        //When
        blackjack.setBet(100);
        boolean actual = blackjack.validateBet();

        //Then
        Assert.assertTrue(actual);
    }

    @Test
    public void testInvalidBet(){
        //Given
        Blackjack blackjack = new Blackjack(player);

        //When
        blackjack.setBet(2000);
        boolean actual = blackjack.validateBet();

        //Then
        Assert.assertFalse(actual);

    }

    @Test
    public void setBet() {
        //Given
        Blackjack blackjack = new Blackjack(player);
        double expected = 100;

        //When
       blackjack.setBet(100);
       double actual = blackjack.getBet();


        //Then
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void takeBet() {
        //Given
        Blackjack blackjack = new Blackjack(player);
        double expected = 100;

        //When
        blackjack.takeBet(100);
        double actual = blackjack.getPot();

        //Then
        Assert.assertEquals(expected, actual,0);
    }

    @Test
    public void payout() {
        //Given
        Blackjack blackjack = new Blackjack(player);
        double expected = 200;

        //When
        blackjack.takeBet(100);
        double actual = blackjack.payout();

        //Then
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void inputToEnum() {
        //Given
        Blackjack blackjack = new Blackjack(player);
        BlackjackActions expected = BlackjackActions.DOUBLE_DOWN;

        //When
        BlackjackActions actual = blackjack.inputToEnum(3);

        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void hit() {
        //GIVEN
        Blackjack blackjack = new Blackjack(this.player);
        blackjack.getBlackjackPlayer().setHand(deck.deal(2));
        int expected = 3;

        //WHEN
        blackjack.hit(deck);
        int actual = blackjack.getBlackjackPlayer().numberOfCardsInHand();

        //THEN
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void doublePot() {
        //Given
        Blackjack blackjack = new Blackjack(player);
        double expected = 200;
        blackjack.setBet(100);
        blackjack.takeBet(100);

        //When
        blackjack.doublePot();
        double actual = blackjack.getPot();

        //Then
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void canDoubleDown() {
        //Given
        Blackjack blackjack = new Blackjack(player);
        blackjack.setBet(2000);

        //When
        boolean actual = blackjack.canDoubleDown();

        //Then
        Assert.assertFalse(actual);
    }

    @Test
    public void testWalkAway() {
        //Given
        Blackjack blackjack = new Blackjack(player);
        blackjack.walkAway = false;

        //When
        blackjack.walkAway();

        //Then
        Assert.assertTrue(blackjack.walkAway);
    }

    @Test
    public void testMessage() {
        //Given
        Blackjack blackjack = new Blackjack(player);
        String expected = "\n**Enter 1 to Hit\n**Enter 2 to Stand \n**Enter 3 to Double Down\n**Enter 4 to Split\n**Enter 5 to Walk away\n\nMake a Move!";

        //When
        String actual = blackjack.menu();

        //Then
        Assert.assertEquals(expected, actual);
    }
}