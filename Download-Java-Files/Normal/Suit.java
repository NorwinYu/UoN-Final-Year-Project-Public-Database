package io.zipcoder.casino.CardGames.CardUtils;

public enum Suit {

        CLUBS('\u2663'),
        SPADES('\u2660'),
        HEARTS('\u2665'),
        DIAMONDS('\u2666');

        private char suitIcon;

        private Suit(char s){
                this.suitIcon = s;
        }

        public char getSuitIcon(){
                return suitIcon;
        }
}


