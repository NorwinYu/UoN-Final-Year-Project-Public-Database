package io.zipcoder.casino;

import io.zipcoder.casino.CardGame.Cards.Card;
import io.zipcoder.casino.CardGame.Cards.Face;
import io.zipcoder.casino.CardGame.Cards.Suit;
import io.zipcoder.casino.CardGame.GoFish;
import io.zipcoder.casino.CardGame.GoFishPlayer;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static io.zipcoder.casino.CardGame.Cards.Face.*;

public class GoFishTests {

    Card hA = new Card(Face.ACE, Suit.HEARTS);
    Card sA = new Card(Face.ACE, Suit.SPADES);
    Card cA = new Card(Face.ACE, Suit.CLUBS);
    Card dA = new Card(Face.ACE, Suit.DIAMONDS);
    Card h2 = new Card(TWO, Suit.HEARTS);
    Card s2 = new Card(TWO, Suit.SPADES);
    Card c2 = new Card(TWO, Suit.CLUBS);
    Card d2 = new Card(TWO, Suit.DIAMONDS);
    Card h3 = new Card(THREE, Suit.HEARTS);
    Card s3 = new Card(THREE, Suit.SPADES);
    Card c3 = new Card(THREE, Suit.CLUBS);
    Card d3 = new Card(THREE, Suit.DIAMONDS);
    Card h4 = new Card(Face.FOUR, Suit.HEARTS);
    Card s4 = new Card(Face.FOUR, Suit.SPADES);
    Card c4 = new Card(Face.FOUR, Suit.CLUBS);
    Card d4 = new Card(Face.FOUR, Suit.DIAMONDS);
    Card h5 = new Card(Face.FIVE, Suit.HEARTS);
    Card s5 = new Card(Face.FIVE, Suit.SPADES);
    Card c5 = new Card(Face.FIVE, Suit.CLUBS);
    Card d5 = new Card(Face.FIVE, Suit.DIAMONDS);
    Card h6 = new Card(Face.SIX, Suit.HEARTS);
    Card s6 = new Card(Face.SIX, Suit.SPADES);
    Card c6 = new Card(Face.SIX, Suit.CLUBS);
    Card d6 = new Card(Face.SIX, Suit.DIAMONDS);
    Card h7 = new Card(Face.SEVEN, Suit.HEARTS);
    Card s7 = new Card(Face.SEVEN, Suit.SPADES);
    Card c7 = new Card(Face.SEVEN, Suit.CLUBS);
    Card d7 = new Card(Face.SEVEN, Suit.DIAMONDS);
    Card h8 = new Card(Face.EIGHT, Suit.HEARTS);
    Card s8 = new Card(Face.EIGHT, Suit.SPADES);
    Card c8 = new Card(Face.EIGHT, Suit.CLUBS);
    Card d8 = new Card(Face.EIGHT, Suit.DIAMONDS);
    Card h9 = new Card(Face.NINE, Suit.HEARTS);
    Card s9 = new Card(Face.NINE, Suit.SPADES);
    Card c9 = new Card(Face.NINE, Suit.CLUBS);
    Card d9 = new Card(Face.NINE, Suit.DIAMONDS);
    Card h10 = new Card(Face.TEN, Suit.HEARTS);
    Card s10 = new Card(Face.TEN, Suit.SPADES);
    Card c10 = new Card(Face.TEN, Suit.CLUBS);
    Card d10 = new Card(Face.TEN, Suit.DIAMONDS);
    Card hJ = new Card(Face.JACK, Suit.HEARTS);
    Card sJ = new Card(Face.JACK, Suit.SPADES);
    Card cJ = new Card(Face.JACK, Suit.CLUBS);
    Card dJ = new Card(Face.JACK, Suit.DIAMONDS);
    Card hQ = new Card(Face.QUEEN, Suit.HEARTS);
    Card sQ = new Card(Face.QUEEN, Suit.SPADES);
    Card cQ = new Card(Face.QUEEN, Suit.CLUBS);
    Card dQ = new Card(Face.QUEEN, Suit.DIAMONDS);
    Card hK = new Card(Face.KING, Suit.HEARTS);
    Card sK = new Card(Face.KING, Suit.SPADES);
    Card cK = new Card(Face.KING, Suit.CLUBS);
    Card dK = new Card(Face.KING, Suit.DIAMONDS);

    @Test
    public void getLastCardTest() {
        // Given
        Player player = new Player("Cara", 1000);
        GoFish goFish = new GoFish(player);
        GoFishPlayer goFishPlayer = goFish.getGoFishPlayer();

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(dJ);
        cards.add(cK);
        cards.add(h9);

        goFishPlayer.getHand().addCardsToHand(cards);

        Card expected = h9;

        // When
        Card actual = goFish.getLastCard(goFishPlayer);

        // Then
        Assert.assertEquals(expected, actual);
    }


  /*  @Test
    public void getCardOptionsTest(){
        // Given
        Player player = new Player("Cara", 1000);
        GoFish goFish = new GoFish(player);

        String expected = "\n'Ace' 'Two' 'Three' 'Four' 'Five' 'Six' 'Seven' 'Eight' 'Nine ' Ten' 'Jack' 'Queen' 'King'\n";

        // When
        String actual = goFish.getCardOptions();

        // Then
        Assert.assertEquals(expected, actual);
    }
*/

    @Test
    public void updatePlayerScoreTest() {
        // Given
        Player player = new Player("Cara", 1000);
        GoFish goFish = new GoFish(player);
        GoFishPlayer goFishPlayer = goFish.getGoFishPlayer();

        int expected = 5;
        goFishPlayer.setCounter4(expected);

        // When
        goFish.updatePlayerScore();
        int actual = goFish.getPlayersScoreCounter();

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateDealerScoreTest() {
        // Given
        Player player = new Player("Cara", 1000);
        GoFish goFish = new GoFish(player);
        GoFishPlayer dealer = goFish.getDealer();

        int expected = 5;
        dealer.setCounter4(expected);

        // When
        goFish.updateDealerScore();
        int actual = goFish.getDealersScoreCounter();

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void checkAskInputTest() {
        // Given
        Player player = new Player("Cara", 1000);
        GoFish goFish = new GoFish(player);
        goFish.setNewTurn(false);

        // When
        goFish.setInput("notAsk");
        goFish.checkAskInput();
        boolean actualFalse = goFish.isPlayerAskingForCard();

        goFish.setInput("ask");
        goFish.checkAskInput();
        boolean actualTrue = goFish.isPlayerAskingForCard();

        // Then
        Assert.assertFalse(actualFalse);
        Assert.assertTrue(actualTrue);
    }


    @Test
    public void checkDrawInputTest() {
        // Given
        Player player = new Player("Cara", 1000);
        GoFish goFish = new GoFish(player);
        goFish.setNewTurn(false);
        goFish.setPlayerDrawing(false);

        // When
        goFish.setInput("draw");
        goFish.checkDrawInput();
        boolean expectedNewTurnTrue = goFish.isNewTurn();
        boolean expectedPlayerDrawingTrue = goFish.isPlayerDrawing();

        goFish.setPlayerDrawing(false);
        goFish.setNewTurn(false);
        goFish.setInput("ask");
        goFish.checkDrawInput();
        boolean expectedNewTurnTrue2 = goFish.isNewTurn();
        boolean expectedPlayerDrawingFalse = goFish.isPlayerDrawing();

        goFish.setNewTurn(true);
        goFish.setInput("notDrawOrAsk");
        goFish.checkDrawInput();
        boolean expectedNewTurnFalse = goFish.isNewTurn();

        // Then
        Assert.assertTrue(expectedNewTurnTrue);
        Assert.assertTrue(expectedPlayerDrawingTrue);
        Assert.assertTrue(expectedNewTurnTrue2);
        Assert.assertFalse(expectedPlayerDrawingFalse);
        Assert.assertFalse(expectedNewTurnFalse);

    }


    @Test
    public void getDealersRequestedCardTest() {
        // Given
        Player player = new Player("Cara", 1000);
        GoFish goFish = new GoFish(player);
        GoFishPlayer dealer = goFish.getDealer();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(h3);
        cards.add(s9);
        cards.add(d2);
        dealer.getHand().addCardsToHand(cards);

        // When
        ArrayList<Face> expected = new ArrayList<>();
        expected.add(THREE);
        expected.add(NINE);
        expected.add(QUEEN);
        expected.add(TWO);
        expected.add(SEVEN);
        expected.add(JACK);

        ArrayList<Face> notExpected = new ArrayList<>();
        notExpected.add(ACE);
        notExpected.add(FOUR);
        notExpected.add(FIVE);
        notExpected.add(SIX);
        notExpected.add(EIGHT);
        notExpected.add(TEN);
        notExpected.add(KING);

        Face requestedCard = goFish.getDealersRequestedCard();
        boolean actualTrue = expected.contains(requestedCard);
        boolean actualFalse = notExpected.contains(requestedCard);

        // Then
        Assert.assertTrue(actualTrue);
        Assert.assertFalse(actualFalse);
    }
}

/*
    @Test
    public void respondToDealerTest() {
        // Given
        Player player = new Player("Cara", 1000);
        GoFish goFish = new GoFish(player);
        GoFishPlayer dealer = goFish.getDealer();
        Face requestedCard = KING;
    }
*/

