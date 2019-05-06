package io.zipcoder.casino;

import io.zipcoder.casino.CardGames.CardUtils.Card;
import io.zipcoder.casino.CardGames.CardUtils.Deck;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class DeckTest {





    @Test
    public void testShuffle() {
        //GIVEN
        Deck deck = new Deck(1);
        String expected = deck.toString();

        //WHEN
        deck.shuffle();
        String actual = deck.toString();


        //THEN
        Assert.assertNotEquals(expected, actual);

    }

    @Test
    public void removeCardFromDeck() {
        //GIVEN
        Deck deck = new Deck(1);
        int expected = deck.deckSize();

        //WHEN
        deck.removeCardFromDeck(26);
        int actual = deck.deckSize();


        //THEN
       Assert.assertNotEquals(expected, actual);

    }

    @Test
    public void testDeal() {
        //GIVEN
        Deck deck = new Deck(10);
        ArrayList<Card> cards = new ArrayList<>();


        //WHEN
        cards = deck.deal(7);
        int expected = cards.size();

        //THEN
        Assert.assertTrue(expected == 7);
    }

    @Test
    public void testCardRemovedAfterDeal(){
        //GIVEN
        Deck deck = new Deck(1);
        ArrayList<Card> cards = new ArrayList<>();
        int expected = 42;

        //WHEN
        cards = deck.deal(10);
        int actual = deck.deckSize();

        //THEN
        Assert.assertEquals(expected, actual);


    }




}