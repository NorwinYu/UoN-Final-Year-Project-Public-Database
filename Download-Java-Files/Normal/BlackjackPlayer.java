package io.zipcoder.casino.CardGames.BlackJack;

import io.zipcoder.casino.CardGames.CardUtils.Card;
import io.zipcoder.casino.CardGames.CardUtils.Hand;
import io.zipcoder.casino.Interfaces.GamblingPlayer;
import io.zipcoder.casino.Utilities.Player;

import java.util.ArrayList;

public class BlackjackPlayer implements GamblingPlayer {
    private Hand hand;
    private Player player;
    private double wallet;

    public BlackjackPlayer(Player player) {
        this.player = player;
        this.wallet = player.getWallet();

    }

    public double getBlackjackPlayerWallet() {
        return this.wallet;

    }

    public void bet(double amount) {
        this.wallet -= amount;

    }

    public void collect(double amount) {
        this.wallet += amount;
    }

    public Integer sumOfHand() {
        int sum = 0;
        ArrayList<Card> cardsInHand = hand.showMyCards();
        for (Card c : cardsInHand) {
            sum += c.getFace().getValue();
        }

        return sum;
    }

    public Hand getHand() {
        return this.hand;
    }

    public void setHand(ArrayList<Card> cards) {
        this.hand = new Hand(cards);
    }

    public int numberOfCardsInHand() {
        return this.hand.getSize();
    }
}
