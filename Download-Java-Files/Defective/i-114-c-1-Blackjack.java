package io.zipcoder.casino.CardGame.BlackJack;

import io.zipcoder.casino.CardGame.CardGame;
import io.zipcoder.casino.CardGame.Cards.Deck;
import io.zipcoder.casino.GamblingGame;
import io.zipcoder.casino.Player;
import io.zipcoder.casino.utilities.Console;

public class Blackjack extends CardGame implements GamblingGame {
    private double pot;
    private BlackjackPlayer blackjackPlayer;
    private BlackjackPlayer dealer;
    private Player player;
    private Deck deck;
    private double bet;
    protected boolean walkAway;
    private Console console = Console.getInstance();


    public Blackjack(Player player) {
        this.blackjackPlayer = new BlackjackPlayer(player);
        this.pot = 0;
        this.player = new Player("Dealer", 0);
        this.dealer = new BlackjackPlayer(this.player);
        this.deck = new Deck(10);
        this.deck.shuffle();
        this.walkAway = false;
    }

    public void takeBet(double amount) {
        this.pot += amount;
    }

    public double payout() {
        return this.pot * 2;
    }

    public void play() {
        while(!walkAway) {
            setBet(console.getDoubleInput("\nHow much money do you want to lose? "));

            while (!validateBet()) {
                setBet(console.getDoubleInput("\nWhoa there, scammer! You can't bet what you don't have! "));

            }

            blackjackPlayer.bet(getBet());
            takeBet(getBet());

            blackjackPlayer.setHand(deck.deal(2));
            dealer.setHand(deck.deal(1));

            if (blackjackPlayer.sumOfHand() == 21){
                playerWin();
            }
            else {
                playHand();
            }
        }
    }

    public void playHand() {
        displayHands();

        if (blackjackPlayer.sumOfHand() > 21){
            playerLose();
        }
        else if (blackjackPlayer.sumOfHand() == 21){
            chooseWinner();
        }
        else {
            playerInteraction();
        }
    }

    public void displayHands() {
        console.println("\nPlayer Hand\n");
        console.println(blackjackPlayer.getHand().toString());
        console.println("\nDealer Hand\n" + dealer.getHand().toString());
        console.println("\nYour cards total " + blackjackPlayer.sumOfHand().toString());
        console.println("\nDealer cards total " + dealer.sumOfHand().toString());
    }

    public void playDealerHand(){
        displayHands();
        if (dealer.sumOfHand() > 21){
            playerWin();
        }

        else if (dealer.sumOfHand() >= 17){
            chooseWinner();
        }

        else {
            stand();
        }
    }

    private void chooseWinner() {
        if(blackjackPlayer.sumOfHand() == 21 && dealer.sumOfHand() == 21)
        {
            push();
        }
        else if(blackjackPlayer.sumOfHand() > dealer.sumOfHand()){
            playerWin();
        }
        else {
            playerLose();
        }
    }


    public void playerLose() {
        console.println("\nYou lost " + String.format("$%.2f", pot));
        this.pot = 0;
        play();
    }

    public void playerWin() {
        blackjackPlayer.collect(payout());
        console.print("\nCongrats, cheater! You won " + String.format("$%.2f",payout()));
        this.pot = 0;
        play();
    }

    public void push(){
        console.println("\nPush! You get your money back.");
        blackjackPlayer.collect(getPot());
        this.pot = 0;
        play();
    }

    public void playerInteraction() {
        int playerInput =console.getIntegerInput(menu());
        if(playerInput == 5){
            walkAway();
        }
        else {
            inputToEnum(playerInput).perform(this, this.deck);
            playHand();
        }
    }

    public boolean validateBet(){
        boolean result = false;
        if (this.bet <= this.blackjackPlayer.getBlackjackPlayerWallet()) {
            result = true;

        }

        return result;
    }

    public void walkAway() {
        walkAway = true;
    }

    public double getPot(){
        return pot;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public double getBet() {
        return this.bet;
    }

    public String menu() {
        return "\n**Enter 1 to Hit\n**Enter 2 to Stand \n**Enter 3 to Double Down\n**Enter 4 to Split\n**Enter 5 to Walk away\n\nMake a Move!";
    }

    public BlackjackActions inputToEnum(int input){
        BlackjackActions result = BlackjackActions.STAND;

        for (BlackjackActions actions: BlackjackActions.values()) {
            if(input == actions.getMenuOption())
            {
                result = actions;
            }
        }
        return result;
    }

    public void hit(Deck deck) {
        this.blackjackPlayer.getHand().drawCard(deck);
    }

    public void stand() {
        dealer.getHand().drawCard(deck);
        playDealerHand();
    }

    public void doubleDown() {
        if(canDoubleDown()) {
            doublePot();
            blackjackPlayer.getHand().drawCard(deck);
            displayHands();
            if (blackjackPlayer.sumOfHand() == 21) {
                playerWin();
            } else {
                stand();
            }
        }
        else{
            console.println("\nYou don't have enough money to double down!");
            playerInteraction();
        }
    }

    public void doublePot() {
        takeBet(getBet());
    }

    public void split() {
        stand();
    }

    public BlackjackPlayer getBlackjackPlayer(){
        return  this.blackjackPlayer;
    }

    public boolean canDoubleDown(){
        boolean result = true;

        if(getBet() > blackjackPlayer.getBlackjackPlayerWallet())
        {
            result = false;
        }

        return result;
    }
}
