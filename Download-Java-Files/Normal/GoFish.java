package io.zipcoder.casino.CardGames.GoFish;

import io.zipcoder.casino.CardGames.CardUtils.Card;
import io.zipcoder.casino.CardGames.CardUtils.CardGame;
import io.zipcoder.casino.CardGames.CardUtils.Deck;
import io.zipcoder.casino.CardGames.CardUtils.Face;
import io.zipcoder.casino.Utilities.Player;
import io.zipcoder.casino.Utilities.Console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GoFish extends CardGame {
    private GoFishPlayer goFishPlayer;
    private GoFishPlayer dealer;
    private int playersScoreCounter;
    private int dealersScoreCounter;
    private Deck deck;
    Console console = Console.getInstance();
    private boolean playing = false;
    private String input;
    private boolean playerAskingForCard = false;
    boolean playerDrawing = false;
    boolean playersTurn = true;
    boolean newTurn = true;
    boolean dealersTurn = false;
    boolean dealerAskingForCard = false;
    boolean givingDealerCard = false;
    boolean dealerDrawing = false;
    Face requestedCard = null;

    boolean deckIsEmpty = false;


    public GoFish(Player player) {

        this.goFishPlayer = new GoFishPlayer(player);
        this.dealer = new GoFishPlayer(new Player("dealer", 0.0));
        this.deck = new Deck(1);
        this.deck.shuffle();
    }


    public void play() {

        console.println(goFishTimeString());
        input = console.getStringInput("Type 'deal' to play!");
        startGame();
        checkExit();

        while (playing) {

            // players turn
            while (playersTurn) {
                checkIfOutOfCards();
                checkAskInput();
                checkExit();

                // player asking dealer for card
                while (playerAskingForCard) {
                    checkIfOutOfCards();
                    askDealerForCard();
                    checkExit();
                }

                // if dealer has card requested by player
                if ((requestedCard != null) && (input.toLowerCase().equals("ask") || input.toLowerCase().equals("type draw"))) {
                    checkIfOutOfCards();
                    dealerResponse();
                    checkExit();

                    // if player has to draw
                    checkDrawInput();
                    checkIfDeckIsEmpty();
                    checkIfOutOfCards();
                    checkExit();
                    while (playerDrawing) {
                        playerDraw();
                        checkExit();
                    }
                }
            }

            // dealers turn
            while (dealersTurn) {

                checkIfOutOfCards();
                checkExit();
                // card dealer is asking player for
                Face dealersRequestedCard = getDealersRequestedCard();

                // dealer requests card
                while (dealerAskingForCard) {
                    checkIfOutOfCards();
                    checkExit();
                    console.println("\nThe dealer requested the card " + dealersRequestedCard + ".\n\n" + goFishPlayer.getHand().toString());
                    respondToDealer(dealersRequestedCard);
                }

                // player has card to give to dealer
                if (givingDealerCard) {
                    checkIfOutOfCards();
                    checkExit();
                    giveDealerCard(dealersRequestedCard);
                }

                // dealer drawing
                checkIfDeckIsEmpty();
                checkExit();
                if (dealerDrawing) {
                    checkIfOutOfCards();
                    checkExit();
                    dealerDraw(dealersRequestedCard);

                    // dealer did not draw the requested card -- switch to players turn
                    if (dealerDrawing) {
                        checkExit();
                        checkIfOutOfCards();
                        dealersTurn = false;
                        dealerDrawing = false;
                        playersTurn = true;
                    }
                }
            }
        }
    }


    public Card getLastCard(GoFishPlayer player) {
        ArrayList<Card> cards = player.getHand().showMyCards();
        Card lastCard = player.getHand().showMyCards().get(cards.size() - 1);
        return lastCard;
    }


    public String getCardOptions() {
        return "\n" + Arrays.toString(Face.values()) + "\n";
    }

    public void updatePlayerScore() {
        playersScoreCounter = goFishPlayer.getCounter4();
    }

    public void updateDealerScore() {
        dealersScoreCounter = dealer.getCounter4();
    }


    public GoFishPlayer getGoFishPlayer() {
        return goFishPlayer;
    }

    public GoFishPlayer getDealer() {
        return dealer;
    }

    public int getPlayersScoreCounter() {
        return playersScoreCounter;
    }

    public int getDealersScoreCounter() {
        return dealersScoreCounter;
    }


    // Starting the game asking for the initial deal of 7 cards for each of the players.
    // cHeck if the initial dealt cards already contains any 4 of a kind  if yes updates the player's scores.

    public void startGame() {
        if (input.equals("deal")) {
            goFishPlayer.getHand().addCardsToHand(deck.deal(7));
            dealer.getHand().addCardsToHand(deck.deal(7));
            playing = true;

            console.println(goFishPlayer.getHand().toString());

            if (goFishPlayer.fourOfAKindFinder()) {
                updatePlayerScore();
                console.println("Wow! You were dealt four of a kind!  Your new score is %d.", playersScoreCounter);
            }

            if (dealer.fourOfAKindFinder()) {
                updateDealerScore();
                console.println("The dealer was dealt four of a kind!  The dealers score is %d.", dealersScoreCounter);
            }
        }
    }


    public void checkAskInput() {
        if (newTurn) {
            input = console.getStringInput("\nType 'ASK' to ask for a card.");
        }
        if (input.toLowerCase().equals("ask")) {
            playerAskingForCard = true;
        }
    }


    public void askDealerForCard() {
        console.println("\n" + goFishPlayer.getHand().toString());
        String cardInput = console.getStringInput("Enter the card you want to ask the dealer for!\n" + getCardOptions());
        try {
            requestedCard = Face.valueOf(cardInput.toUpperCase());
            playerAskingForCard = false;
        } catch (IllegalArgumentException iae) {
            console.println("Invalid card requested.\n");

        }
    }


    public void dealerResponse() {
        if (dealer.hasRequestedCard(requestedCard)) {
            goFishPlayer.requestCard(dealer, requestedCard);
            console.println("\n" + goFishPlayer.getHand().toString());
            console.println("The dealer gave you all their " + requestedCard.name() + " cards!  Go again!\n");

            if (goFishPlayer.fourOfAKindFinder()) {
                updatePlayerScore();
                console.println("\nYou got four of a kind!  Your new score is %d.", playersScoreCounter);
                playerAskingForCard = true;
            }
        } else {
            input = console.getStringInput("\nThe dealer does not have any " + requestedCard.name() + " cards.  Enter 'DRAW' to Go Fish!");
        }
    }

    public void checkDrawInput() {
        if (input.toLowerCase().equals("draw")) {
            playerDrawing = true;
            newTurn = true;
        } else if (input.toLowerCase().equals("ask")) {
            newTurn = true;
        } else {
            console.println("Invalid input.");
            newTurn = false;
            input = "type draw";
        }
    }

    public void playerDraw() {
        goFishPlayer.getHand().drawCard(deck);
        printPlayersDraw();
        if (goFishPlayer.fourOfAKindFinder()) {
            updatePlayerScore();
            console.println("You got 4 of a kind!  Your new score is %d!\n", playersScoreCounter);
        }
    }

    public void printPlayersDraw() {
        if (getLastCard(goFishPlayer).getFace().equals(requestedCard)) {

            console.println("You drew a %s!  Go again!", requestedCard.name());
            playerDrawing = false;
            playerAskingForCard = true;
            checkIfDeckIsEmpty();
        } else {
            console.println("\n" + goFishPlayer.getHand().toString());
            console.println("You drew a %s.  It's the dealer's turn.", getLastCard(goFishPlayer).getFace().name());
            playerDrawing = false;
            playersTurn = false;
            dealersTurn = true;
            dealerAskingForCard = true;
        }
    }

    public Face getDealersRequestedCard() {
        int sizeOfDealersHand = dealer.getHand().getSize();
        Face dealersRequestedCard;
        Random random = new Random();
        if (sizeOfDealersHand > 0) {
            Integer indexOfCard = random.nextInt(sizeOfDealersHand);
            dealersRequestedCard = dealer.getHand().showMyCards().get(indexOfCard).getFace();
        } else {
            dealersRequestedCard = Face.ACE;
        }

        return dealersRequestedCard;
    }


    public void respondToDealer(Face dealersRequestedCard) {
        if (goFishPlayer.hasRequestedCard(dealersRequestedCard)) {
            input = console.getStringInput("You have the card the dealer requested. Type 'GIVE' to it to the dealer.");
            if (!input.toLowerCase().equals("give")) {
                console.println("Invalid input.");
            } else {
                dealerAskingForCard = false;
                givingDealerCard = true;
            }
        } else {
            input = console.getStringInput("You don't have the requested card!  Tell the dealer to 'GO FISH'.");
            if (input.toLowerCase().equals("go fish")) {
                dealerAskingForCard = false;
                dealerDrawing = true;
            } else {
                console.println("Invalid input.");
            }
        }
    }


    public void dealerDraw(Face dealersRequestedCard) {
        dealer.getHand().drawCard(deck);

        if (getLastCard(dealer).getFace().equals(dealersRequestedCard)) {
            console.println("The dealer drew the card they requested.  It's their turn again!");
            dealerDrawing = false;
            dealerAskingForCard = true;
        }
        if (dealer.fourOfAKindFinder()) {
            updateDealerScore();
            console.println("The dealer got 4 of a kind.  Their score is %d", dealersScoreCounter);
        }
    }


    public void giveDealerCard(Face dealersRequestedCard) {
        dealer.requestCard(goFishPlayer, dealersRequestedCard);

        if (dealer.fourOfAKindFinder()) {
            updateDealerScore();
            console.println("The dealer got 4 of a kind.  Their score is %d", dealersScoreCounter);
        }
        givingDealerCard = false;
        dealerAskingForCard = true;
    }


    public boolean deckIsEmpty(Deck deck) {

        if (deck.deckSize() <= 1) {
            this.deckIsEmpty = true;
            return true;
        }

        return false;
    }


    public boolean handHasCards(GoFishPlayer goFishPlayer) {
        return goFishPlayer.playerHasCards();
    }


    public void drawNewCardsIfHandIsEmpty(GoFishPlayer goFishPlayer) {
        if (!handHasCards(goFishPlayer)) {
            if (deck.deckSize() > 7) {
                goFishPlayer.getHand().addCardsToHand(deck.deal(7));
                console.println("Hand is empty.  Dealing 7 more cards.");
            } else if (!deckIsEmpty) {
                goFishPlayer.getHand().addCardsToHand(deck.deal(deck.deckSize() - 1));
                deckIsEmpty = true;
                console.println("Hand is empty.  Dealing the rest of the cards in the deck.");
            } else {
                playing = false;
                endOfGameMessage(goFishPlayer);
            }
        }
    }

    public void checkIfOutOfCards() {
        drawNewCardsIfHandIsEmpty(goFishPlayer);
        drawNewCardsIfHandIsEmpty(dealer);
    }

    public void checkIfDeckIsEmpty() {
        if (deckIsEmpty(deck)) {
            playerDrawing = false;
            dealerDrawing = false;
            console.println("The deck is empty so there are no cards to draw.");
        }
    }


    public void endOfGameMessage(GoFishPlayer goFishPlayer) {
        setAllBooleansFalse();
        if (goFishPlayer.equals(dealer)) {
            console.println("The deck is empty and the dealer is out of cards!");
        }
        if (goFishPlayer.equals(this.goFishPlayer)) {
            console.println("The deck is empty and you are out of cards!");
        }
        console.println("Your score is %d.  The dealer's score is %d.", playersScoreCounter, dealersScoreCounter);
        if (playersScoreCounter > dealersScoreCounter) {
            console.println("You win!!!!! You are a Go Fish wizard!");
        } else {
            console.println("You lose!  Better luck next time!");
        }
    }

    public void setAllBooleansFalse() {
        playing = false;
        playerAskingForCard = false;
        playerDrawing = false;
        playersTurn = false;
        newTurn = false;
        dealersTurn = false;
        dealerAskingForCard = false;
        givingDealerCard = false;
        dealerDrawing = false;
        requestedCard = null;
    }

    public void checkExit() {
        if ("exit".equals(input)) {
            walkAway();
        }
    }

    public void walkAway() {
        setAllBooleansFalse();
        console.println("Thanks for playing Go Fish!");
        console.println("Your score is %d.  The dealer's score is %d.", playersScoreCounter, dealersScoreCounter);
    }


    public String goFishTimeString(){
        return "\n" +
                "               O  o\n" +
                "          _\\_   o\n" +
                "       \\\\/  o\\ .\n" +
                "       //\\___=\n" +
                "          ''\n" +
                "____ ____    ____ _ ____ _  _    ___ _ _  _ ____   /\n" +
                "| __ |  |    |___ | [__  |__|     |  | |\\/| |___  / \n" +
                "|__] |__|    |    | ___] |  |     |  | |  | |___ .  \n" +
                "                                                    \n" +
                "          _____\n" +
                "         |A .  | _____\n" +
                "         | /.\\ ||A ^  | _____\n" +
                "         |(_._)|| / \\ ||A _  | _____\n" +
                "         |  |  || \\ / || ( ) ||A_ _ |\n" +
                "         |____V||  .  ||(_'_)||( v )|\n" +
                "                |____V||  |  || \\ / |\n" +
                "                       |____V||  .  |\n" +
                "                              |____V|";

    }


    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public boolean isPlayerAskingForCard() {
        return playerAskingForCard;
    }

    public boolean isPlayerDrawing() {
        return playerDrawing;
    }

    public void setPlayerDrawing(boolean playerDrawing) {
        this.playerDrawing = playerDrawing;
    }

    public boolean isPlayersTurn() {
        return playersTurn;
    }


    public boolean isNewTurn() {
        return newTurn;
    }

    public void setNewTurn(boolean newTurn) {
        this.newTurn = newTurn;
    }

    public boolean isDealersTurn() {
        return dealersTurn;
    }


    public boolean isDealerAskingForCard() {
        return dealerAskingForCard;
    }


    public boolean isGivingDealerCard() {
        return givingDealerCard;
    }


    public boolean isDealerDrawing() {
        return dealerDrawing;
    }


    public Face getRequestedCard() {
        return requestedCard;
    }


    public void setDealerAskingForCard(boolean dealerAskingForCard) {
        this.dealerAskingForCard = dealerAskingForCard;
    }
}
