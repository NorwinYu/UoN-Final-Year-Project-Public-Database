package io.zipcoder.casino.CardGames.BlackJack;

import io.zipcoder.casino.CardGames.CardUtils.Deck;

import java.util.function.BiConsumer;

public enum BlackjackActions {

    HIT(Blackjack::hit, 1),
    STAND((blackjack, deck) -> blackjack.stand(),2),
    DOUBLE_DOWN((blackjack, deck) -> blackjack.doubleDown(),3),
    SPLIT((blackjack, deck) -> blackjack.split(),4);

    private final BiConsumer<Blackjack, Deck> consumer;
    private int menuOption;

    BlackjackActions (BiConsumer<Blackjack, Deck> consumer, int menuOption) {
        this.consumer = consumer;
        this.menuOption = menuOption;
    }

    public void perform(Blackjack blackjackObject, Deck deckObject) {
        consumer.accept(blackjackObject, deckObject);
    }

    public int getMenuOption(){
        return this.menuOption;
    }
}

