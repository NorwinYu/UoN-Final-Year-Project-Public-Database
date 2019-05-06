package io.zipcoder.casino.DiceGame.Craps;

import io.zipcoder.casino.DiceGame.DiceUtils.Dice;
import io.zipcoder.casino.Interfaces.GamblingPlayer;
import io.zipcoder.casino.Utilities.Player;
import io.zipcoder.casino.Utilities.Console;

import java.util.ArrayList;

public class CrapsPlayer implements GamblingPlayer {
    private String name;
    private double wallet;
    private Player player;
    Console crapPlayerConsole = Console.getInstance();


    public CrapsPlayer(Player player) {
        this.wallet = player.getWallet();
        this.name = player.getName();
    }

    public void bet(double amount) {
        while (this.wallet < amount || amount < 0){
            amount = crapPlayerConsole.getDoubleInput("\nPlease enter an amount of money less than or equal to your wallet\n" + "You have " + this.wallet + " available");
        }
        this.wallet = wallet - amount;
    }

    public ArrayList<Dice> rollDice(int numberOfDice) {

        ArrayList<Dice> rolledDice = new ArrayList<>();

        for (int i = 0; i < numberOfDice; i++) {
            Dice die = new Dice(1);
            int dieValue = die.rollDice();
            rolledDice.add(new Dice(1, dieValue));
        }
        return rolledDice;
    }

    public int sumOfRoll(ArrayList<Dice> diceRoll){
        return diceRoll.get(0).getValue() + diceRoll.get(1).getValue();
    }


    public void collect(double amount) {
        this.wallet += amount;
    }

    public String getName(){
        return name;
    }

    public void collectCraps(double amount, double winningsMultiplier){
        this.wallet += (amount*winningsMultiplier);
    }

    public double getWallet(){
        return this.wallet;
    }
}
