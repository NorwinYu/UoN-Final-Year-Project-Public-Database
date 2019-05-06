package io.zipcoder.casino.CardGames.GoFish;

import io.zipcoder.casino.CardGames.CardUtils.Card;
import io.zipcoder.casino.CardGames.CardUtils.Face;
import io.zipcoder.casino.CardGames.CardUtils.Hand;
import io.zipcoder.casino.Utilities.Player;

import java.util.ArrayList;

public class GoFishPlayer {
    private Hand hand;
    private String name;
    private Player player;

    // Four of a kind counter
    private int counter4 = 0;



    // Constructor

    public GoFishPlayer(Player player) {

        this.name = player.getName();
        this.player = player;
        this.hand = new Hand(new ArrayList<>());


    }

    public int getCounter4() {
        return counter4;
    }

    public Hand getHand() {
        return hand;
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    // method return boolean to check whether the opponent has the requested face
    public boolean hasRequestedCard(Face cardRequested) {

        for (Card card : hand.showMyCards()) {

            if (card.getFace().equals(cardRequested)) {
                return true;
            }
        }
        return false;
    }


    //A player is requesting  a particular Face of Card from the opponent.
    //Once the particular face enum is requested, check opponents hand for that face if true return all the cards in opponents hands having the same face to the requesting player's hand.
    // Adds the cards to the player's deck and removes the cards from the other player.
    public void requestCard(GoFishPlayer otherPlayer, Face face) {

        ArrayList<Card> cardsInHand = otherPlayer.hand.showMyCards();

        ArrayList<Card> cardsToRemove = new ArrayList<>();


        for (Card card : cardsInHand) {

            if (card.getFace().equals(face)) {

                cardsToRemove.add(card);
            }

        }

        otherPlayer.getHand().removeCardsFromHand(cardsToRemove);
        hand.addCardsToHand(cardsToRemove);

    }


    // Provided that a player has a particular face 4 times the player can lay stack of 4 of a kind.
    public void layDown4OfAKind(Face face4) {

        ArrayList<Card> fourOfAKind = new ArrayList<>();
        for (Card c : hand.showMyCards()) {
            if (c.getFace().equals(face4)) {
                fourOfAKind.add(c);
            }
        }

        hand.removeCardsFromHand(fourOfAKind);
        counter4++;


    }


    public Integer[] getCardCountInHand() {
        Integer[] counter = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


        for (Card card : hand.showMyCards()) {

            Face face = card.getFace();

            switch (face) {

                case ACE:

                    counter[0]++;
                    break;

                case TWO:
                    counter[1]++;
                    break;

                case THREE:
                    counter[2]++;
                    break;

                case FOUR:
                    counter[3]++;
                    break;

                case FIVE:
                    counter[4]++;
                    break;

                case SIX:
                    counter[5]++;
                    break;

                case SEVEN:
                    counter[6]++;
                    break;

                case EIGHT:
                    counter[7]++;
                    break;

                case NINE:
                    counter[8]++;
                    break;

                case TEN:
                    counter[9]++;
                    break;

                case JACK:
                    counter[10]++;
                    break;

                case QUEEN:
                    counter[11]++;
                    break;

                case KING:
                    counter[12]++;
                    break;
            }
        }
        return counter;
    }

    public boolean fourOfAKindFinder() {

        Integer[] cardCounter = getCardCountInHand();
        Boolean hasFourOfAKind = false;

        for (int i = 0; i < 13; i++) {
            if (cardCounter[i] == 4) {
                if (i == 0) {
                    layDown4OfAKind(Face.ACE);
                    hasFourOfAKind = true;
                }
                if (i == 1) {
                    layDown4OfAKind(Face.TWO);
                    hasFourOfAKind = true;
                }
                if (i == 2) {
                    layDown4OfAKind(Face.THREE);
                    hasFourOfAKind = true;
                }
                if (i == 3) {
                    layDown4OfAKind(Face.FOUR);
                    hasFourOfAKind = true;
                }
                if (i == 4) {
                    layDown4OfAKind(Face.FIVE);
                    hasFourOfAKind = true;
                }
                if (i == 5) {
                    layDown4OfAKind(Face.SIX);
                    hasFourOfAKind = true;
                }
                if (i == 6) {
                    layDown4OfAKind(Face.SEVEN);
                    hasFourOfAKind = true;
                }
                if (i == 7) {
                    layDown4OfAKind(Face.EIGHT);
                    hasFourOfAKind = true;
                }
                if (i == 8) {
                    layDown4OfAKind(Face.NINE);
                    hasFourOfAKind = true;
                }
                if (i == 9) {
                    layDown4OfAKind(Face.TEN);
                    hasFourOfAKind = true;
                }
                if (i == 10) {
                    layDown4OfAKind(Face.JACK);
                    hasFourOfAKind = true;
                }
                if (i == 11) {
                    layDown4OfAKind(Face.QUEEN);
                    hasFourOfAKind = true;
                }
                if (i == 12) {
                    layDown4OfAKind(Face.KING);
                    hasFourOfAKind = true;
                }
            }
        }
        return hasFourOfAKind;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setCounter4(int counter4) {
        this.counter4 = counter4;
    }

    public boolean playerHasCards(){

        return getHand().getSize() > 0;

    }
}
