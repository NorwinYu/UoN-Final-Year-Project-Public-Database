package io.zipcoder.casino.CardGames.CardUtils;

public class Card {
    private Face face;
    private Suit suit;


    public Card (Face face, Suit suit) {
        this.face = face;
        this.suit = suit;



    }

    public Face getFace() {
        return face;
    }


    public Suit getSuit() {
        return suit;
    }

    public String getCardFaceValue(){
        return face.getFaceValue();
    }

    public char getCardSuitValue(){
        return suit.getSuitIcon();
    }
}
