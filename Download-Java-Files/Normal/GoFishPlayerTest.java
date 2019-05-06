package io.zipcoder.casino;

import io.zipcoder.casino.CardGames.CardUtils.Card;
import io.zipcoder.casino.CardGames.CardUtils.Face;
import io.zipcoder.casino.CardGames.CardUtils.Suit;
import io.zipcoder.casino.CardGames.GoFish.GoFishPlayer;
import io.zipcoder.casino.Utilities.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GoFishPlayerTest {


    Card hA = new Card(Face.ACE, Suit.HEARTS);
    Card sA = new Card(Face.ACE, Suit.SPADES);
    Card cA = new Card(Face.ACE, Suit.CLUBS);
    Card dA = new Card(Face.ACE, Suit.DIAMONDS);
    Card h2 = new Card(Face.TWO, Suit.HEARTS);
    Card s2 = new Card(Face.TWO, Suit.SPADES);
    Card c2 = new Card(Face.TWO, Suit.CLUBS);
    Card d2 = new Card(Face.TWO, Suit.DIAMONDS);
    Card h3 = new Card(Face.THREE, Suit.HEARTS);
    Card s3 = new Card(Face.THREE, Suit.SPADES);
    Card c3 = new Card(Face.THREE, Suit.CLUBS);
    Card d3 = new Card(Face.THREE, Suit.DIAMONDS);
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
    public void goFishPlayerConstructorTest() {

        // Given
        String expectedName = "Cara";
        Player expectedPlayer = new Player(expectedName, 10000);

        // When
        GoFishPlayer goFishPlayer = new GoFishPlayer(expectedPlayer);
        Player actualPlayer = goFishPlayer.getPlayer();
        String actualName = goFishPlayer.getName();

        // Then
        Assert.assertEquals(expectedPlayer, actualPlayer);
        Assert.assertEquals(expectedName, actualName);
    }


    @Test
    public void addCardsToHandsTest() {
        // Given
        Player player = new Player("Cara", 1000);
        GoFishPlayer goFishPlayer = new GoFishPlayer(player);

        goFishPlayer.getHand().showMyCards().add(h3);
        goFishPlayer.getHand().showMyCards().add(d9);
        goFishPlayer.getHand().showMyCards().add(cK);

        ArrayList<Card> cardsToAdd = new ArrayList<>();
        cardsToAdd.add(sK);
        cardsToAdd.add(hK);
        cardsToAdd.add(dK);

        ArrayList<Card> expected = new ArrayList<>();
        expected.add(h3);
        expected.add(d9);
        expected.add(cK);
        expected.add(sK);
        expected.add(hK);
        expected.add(dK);

        // When
        goFishPlayer.getHand().addCardsToHand(cardsToAdd);
        ArrayList<Card> actual = goFishPlayer.getHand().showMyCards();

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void getCardCountInHandTest() {
        // Given
        Player player = new Player("Cara", 1000);
        GoFishPlayer goFishPlayer = new GoFishPlayer(player);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(dA);
        cards.add(hA);
        cards.add(s3);
        cards.add(s4);
        cards.add(d4);
        cards.add(c4);
        cards.add(h4);
        cards.add(s6);
        cards.add(h8);
        cards.add(c8);
        cards.add(sQ);
        cards.add(hQ);

        goFishPlayer.getHand().addCardsToHand(cards);

        Integer[] expected = {2, 0, 1, 4, 0, 1, 0, 2, 0, 0, 0, 2, 0};

        // When
        Integer[] actual = goFishPlayer.getCardCountInHand();

        // Then
        Assert.assertArrayEquals(expected, actual);
    }


    @Test
    public void layDown4OfAKindTest() {
        // Given
        Player player = new Player("Cara", 1000);
        GoFishPlayer goFishPlayer = new GoFishPlayer(player);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(dA);
        cards.add(hA);
        cards.add(s3);
        cards.add(s4);
        cards.add(d4);
        cards.add(c4);
        cards.add(h4);
        cards.add(s6);
        cards.add(h8);
        cards.add(c8);
        cards.add(sQ);
        cards.add(hQ);

        goFishPlayer.getHand().addCardsToHand(cards);

        int expectedCounter4 = 1;
        ArrayList<Card> expectedCards = new ArrayList<>();
        expectedCards.add(dA);
        expectedCards.add(hA);
        expectedCards.add(s3);
        expectedCards.add(s6);
        expectedCards.add(h8);
        expectedCards.add(c8);
        expectedCards.add(sQ);
        expectedCards.add(hQ);

        // When
        goFishPlayer.layDown4OfAKind(Face.FOUR);
        ArrayList<Card> actualCards = goFishPlayer.getHand().showMyCards();
        int actualCounter4 = goFishPlayer.getCounter4();

        // Then
        Assert.assertEquals(expectedCards, actualCards);
        Assert.assertEquals(expectedCounter4, actualCounter4);
    }


    @Test
    public void fourOfAKindFinderTest() {
        // Given
        Player player = new Player("Cara", 1000);
        GoFishPlayer goFishPlayer = new GoFishPlayer(player);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(dA);
        cards.add(hA);
        cards.add(s3);
        cards.add(s4);
        cards.add(d4);
        cards.add(c4);
        cards.add(h4);
        cards.add(s6);
        cards.add(h8);
        cards.add(c8);
        cards.add(sQ);
        cards.add(hQ);
        cards.add(sK);
        cards.add(cK);
        cards.add(dK);
        cards.add(hK);

        goFishPlayer.getHand().addCardsToHand(cards);

        int expectedCounter4 = 2;
        ArrayList<Card> expectedCards = new ArrayList<>();
        expectedCards.add(dA);
        expectedCards.add(hA);
        expectedCards.add(s3);
        expectedCards.add(s6);
        expectedCards.add(h8);
        expectedCards.add(c8);
        expectedCards.add(sQ);
        expectedCards.add(hQ);

        // When
        boolean actual = goFishPlayer.fourOfAKindFinder();
        int actualCounter4 = goFishPlayer.getCounter4();
        ArrayList<Card> actualCards = goFishPlayer.getHand().showMyCards();

        // Then
        Assert.assertEquals(expectedCounter4, actualCounter4);
        Assert.assertEquals(expectedCards, actualCards);
        Assert.assertTrue(actual);
    }


    @Test
    public void requestCardTest() {
        // Given
        Player player = new Player("Cara", 1000);
        Player otherPlayer = new Player("Neela", 1000);
        GoFishPlayer goFishPlayer = new GoFishPlayer(player);
        GoFishPlayer otherGoFishPlayer = new GoFishPlayer(otherPlayer);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(d4);
        cards.add(hQ);
        cards.add(s2);
        cards.add(sJ);
        cards.add(d10);
        cards.add(c3);
        cards.add(h5);
        cards.add(s7);
        cards.add(h9);

        goFishPlayer.getHand().addCardsToHand(cards);

        ArrayList<Card> expectedCards = new ArrayList<>();
        expectedCards.addAll(cards);
        expectedCards.add(s5);
        expectedCards.add(c5);
        expectedCards.add(d5);

        ArrayList<Card> otherPlayersCards = new ArrayList<>();
        otherPlayersCards.add(s5);
        otherPlayersCards.add(c5);
        otherPlayersCards.add(c7);
        otherPlayersCards.add(d7);
        otherPlayersCards.add(s8);
        otherPlayersCards.add(d6);
        otherPlayersCards.add(c6);
        otherPlayersCards.add(d5);

        otherGoFishPlayer.getHand().addCardsToHand(otherPlayersCards);

        ArrayList<Card> expectedOtherPlayersCards = new ArrayList<>();
        expectedOtherPlayersCards.add(c7);
        expectedOtherPlayersCards.add(d7);
        expectedOtherPlayersCards.add(s8);
        expectedOtherPlayersCards.add(d6);
        expectedOtherPlayersCards.add(c6);

        // When
        goFishPlayer.requestCard(otherGoFishPlayer, Face.FIVE);
        ArrayList<Card> actualCards = goFishPlayer.getHand().showMyCards();
        ArrayList<Card> actualOtherPlayersCards = otherGoFishPlayer.getHand().showMyCards();

        // Then
        Assert.assertEquals(expectedCards, actualCards);
        Assert.assertEquals(expectedOtherPlayersCards, actualOtherPlayersCards);

    }


    @Test
    public void hasRequestedCardTest() {
        // Given
        Player player = new Player("Cara", 1000);
        GoFishPlayer goFishPlayer = new GoFishPlayer(player);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(cJ);
        cards.add(dJ);
        cards.add(sA);
        cards.add(c2);
        cards.add(s10);
        cards.add(c10);
        cards.add(d8);
        cards.add(dQ);
        cards.add(h6);

        goFishPlayer.getHand().addCardsToHand(cards);

        // When
        boolean expectedTrue = goFishPlayer.hasRequestedCard(Face.TEN);
        boolean expectedTrue2 = goFishPlayer.hasRequestedCard(Face.SIX);
        boolean expectedFalse = goFishPlayer.hasRequestedCard(Face.THREE);

        // Then
        Assert.assertTrue(expectedTrue);
        Assert.assertTrue(expectedTrue2);
        Assert.assertFalse(expectedFalse);
    }


    @Test
    public void playerHasCardsTest(){
        // Given
        Player player = new Player("Cara", 1000);
        GoFishPlayer goFishPlayer = new GoFishPlayer(player);
        List<Card> cards = goFishPlayer.getHand().showMyCards();

        // When
        boolean expectedFalse = goFishPlayer.playerHasCards();

        cards.add(h9);
        cards.add(sA);

        boolean expectedTrue = goFishPlayer.playerHasCards();

        // Then
        Assert.assertFalse(expectedFalse);
        Assert.assertTrue(expectedTrue);
    }


    @Test
    public void setPlayerTest(){
        // Given
        Player player = new Player("Cara", 1000);
        GoFishPlayer goFishPlayer = new GoFishPlayer(player);
        Player expected = new Player("Expected", 1000);

        // When
        goFishPlayer.setPlayer(expected);
        Player actual = goFishPlayer.getPlayer();

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void setCounter4Test(){
        // Given
        Player player = new Player("Cara", 1000);
        GoFishPlayer goFishPlayer = new GoFishPlayer(player);
        int expected = 5;

        // When
        goFishPlayer.setCounter4(expected);
        int actual = goFishPlayer.getCounter4();

        // Then
        Assert.assertEquals(expected, actual);
    }

}
