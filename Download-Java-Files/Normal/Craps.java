package io.zipcoder.casino.DiceGame.Craps;

import io.zipcoder.casino.DiceGame.DiceUtils.Dice;
import io.zipcoder.casino.DiceGame.DiceUtils.DiceGame;
import io.zipcoder.casino.Interfaces.GamblingGame;
import io.zipcoder.casino.Utilities.DisplayGraphics;
import io.zipcoder.casino.Utilities.Player;
import io.zipcoder.casino.Utilities.Console;

import java.util.ArrayList;
import java.util.Collections;

public class Craps extends DiceGame implements GamblingGame {

    Console crapsConsole = Console.getInstance();

    private CrapsPlayer crapsPlayer;
    private ArrayList<Dice> rolledDice;
    private Integer summedRoll;
    private Boolean pointOn;
    private Boolean playing;
    boolean startPromt;
    String input;
    boolean roll;

    private int pointNumber;
    private int hardwayNumber;

    private double passLinePot;
    private double passLineOddsPot;
    private double hardWaysPot;
    private double fieldPot;

    ArrayList<Integer> fieldNumbers = new ArrayList<Integer>();
    ArrayList<Integer> pointNumbers = new ArrayList<Integer>();
    ArrayList<Integer> winningPointLineNumbers = new ArrayList<Integer>();
    ArrayList<Integer> losingPointNumbers = new ArrayList<Integer>();
    ArrayList<Integer> hardwayNumberArray = new ArrayList<>();


    public Craps(Player player) {
        this.crapsPlayer = new CrapsPlayer(player);
        this.rolledDice = new ArrayList<>();
        this.summedRoll = 0;
        this.passLinePot = 0.0;
        this.fieldPot = 0.0;
        this.hardWaysPot = 0.0;
        playing = false;
        pointOn = false;
        startPromt = false;
        roll = false;
        input = "";

        Collections.addAll(fieldNumbers, 2, 3, 4, 9, 10, 11, 12);
        Collections.addAll(pointNumbers, 4, 5, 6, 8, 9, 10);
        Collections.addAll(winningPointLineNumbers, 7, 11);
        Collections.addAll(losingPointNumbers, 2, 3, 12);
        Collections.addAll(hardwayNumberArray, 4, 6, 8, 10);
    }


    public void play() {
        playing = startPrompt();

        while (playing) {
            pointOffRoll();
            pointOnRoll();
            walkAway();
        }
    }

    public void pointOffRoll() {
        while (!pointOn) {
            advanceRoll(DisplayGraphics.crapsOptions1());
            rolledDice = rollDice();
            summedRoll = rollDiceSum(rolledDice);

            pointOffCheck();
        }
    }

    public void pointOnRoll() {
        while (pointOn) {
            advanceRoll(DisplayGraphics.crapsOptions2());
            rolledDice = rollDice();
            summedRoll = rollDiceSum(rolledDice);

            pointOnCheck();
        }
    }

    public void advanceRoll(String displayOption) {
        while (!roll) {
            roll = askForRoll(roll, displayOption);
        }
    }

    public void resetRoll() {
        roll = false;
        summedRoll = 0;
        printWallet();
    }

    private void pointOffCheck() {
        checkFieldBetWinner(summedRoll, fieldPot);
        checkPassLineBetWinner(summedRoll, passLinePot);
        checkHardwayWinner(summedRoll, hardWaysPot, rolledDice);

        resetRoll();
    }

    private void pointOnCheck() {
        checkPassLineBetPhase2(summedRoll, passLinePot);
        checkFieldBetWinner(summedRoll, fieldPot);
        checkPassLineOddsWinner(summedRoll, passLineOddsPot);
        checkHardwayWinner(summedRoll, hardWaysPot, rolledDice);

        resetRoll();
    }

    private boolean askForRoll(boolean roll, String phase1Options) {
        String input;
        input = crapsConsole.getStringInput(phase1Options).toUpperCase().replaceAll(" ", "_");

        if ((!input.equals("PASS_LINE_ODDS")) && !input.equals("FIELD") && !input.equals("HARDWAY") && !input.equals("PASS_LINE") && !input.equals("ROLL")) {
            System.out.println("Sorry, invalid input\n");
        } else if (input.equals("ROLL")) {
            roll = true;
        } else {
            CrapsActions.valueOf(input).perform(this, askForBet());
        }

        return roll;
    }


    public void walkAway() {
        String input = crapsConsole.getStringInput("Enter 'E' to exit or press 'ENTER' to continue").toLowerCase();
        if (input.equals("e")) {
            playing = false;
        }

    }

    public Boolean startPrompt() {
        while (!startPromt) {
            input = crapsConsole.getStringInput("\nHello %s!  Welcome to Craps!  Type 'Start' to begin!", crapsPlayer.getName());

            if (input.toLowerCase().equals("start")) {
                startPromt = true;
                playing = true;
            }
        }
        return playing;
    }

    public double payout() {
        return 0;
    }


    public void passLineBet(Double amount) {
        crapsPlayer.bet(amount);
        passLinePot += amount;
    }

    public void passLineOddsBet(Double amount) {
        crapsPlayer.bet(amount);
        passLineOddsPot += amount;
    }

    public void hardWayBet(Double amount) {
        crapsPlayer.bet(amount);
        hardWaysPot += amount;
        Integer input = 0;
        while (!hardwayNumberArray.contains(input)) {
            input = crapsConsole.getIntegerInput("Please enter one of the following numbers (4), (6), (8), (10) to bet on");
            if (!hardwayNumberArray.contains(input)) {
                System.out.println("Please enter a valid number \n");
            }
        }
        hardwayNumber = input;
    }

    public void fieldBet(Double amount) {
        crapsPlayer.bet(amount);
        fieldPot += amount;
    }

    public Double askForBet() {
        Double inputBetAmount = crapsConsole.getDoubleInput("\nPlease enter your bet amount.");
        return inputBetAmount;
    }

    public void clearPassLinePot() {
        this.passLinePot = 0;
    }

    public void clearfieldPot() {
        this.fieldPot = 0;
    }

    public void clearHardwayPot() {
        this.hardWaysPot = 0;
    }

    private void clearPassLineOddsPot() {
        this.passLineOddsPot = 0;
    }


    public void printWallet() {
        System.out.println("Your current wallet is " + crapsPlayer.getWallet() + "\n");
    }

    public void checkFieldBetWinner(int sumofRoll, Double betAmount) {
        if (fieldNumbers.contains(sumofRoll) && betAmount > 0) {
            System.out.println("You won " + fieldPot + " on your Field bet");
            crapsPlayer.collectCraps(fieldPot, 2);
        } else if (!fieldNumbers.contains(sumofRoll) && betAmount > 0) {
            System.out.println("You lost " + fieldPot + " on your Field bet");
        }
        clearfieldPot();
    }

    public void checkPassLineBetWinner(int sumofRoll, Double betAmount) {
        if (winningPointLineNumbers.contains(sumofRoll) && betAmount > 0) {
            System.out.println("You won " + passLinePot + " on your Pass Line bet");
            crapsPlayer.collectCraps(passLinePot, 2);
            clearPassLinePot();
        } else if (pointNumbers.contains(sumofRoll)) {
            System.out.println("The point is now set at " + sumofRoll);
            setPointOn();
            setPointNumber(sumofRoll);
        } else {
            System.out.println("You lost " + passLinePot + " on you Pass Line bet");
            clearPassLinePot();
        }
    }

    public void checkPassLineBetPhase2(int sumofRoll, Double betAmount) {
        if (sumofRoll == pointNumber && betAmount > 0) {
            System.out.println("You won " + passLinePot + " on your Pass Line bet");
            crapsPlayer.collectCraps(passLinePot, 2.0);
            clearPassLinePot();
            setPointOff();
        } else if (sumofRoll == 7) {
            System.out.println("You lost " + passLinePot + " on your Pass Line bet");
            clearPassLinePot();
            setPointOff();
        }
    }

    public void checkPassLineOddsWinner(int sumofRoll, Double betAmount) {
        if (sumofRoll == pointNumber && betAmount > 0) {
            System.out.println("You won " + passLineOddsPot + " on your Pass Line Odds");
            crapsPlayer.collectCraps(passLineOddsPot, 2.0);
            clearPassLineOddsPot();
            setPointOff();
        } else if (sumofRoll == 7) {
            System.out.println("You lost " + passLineOddsPot + " on your Pass Line Odds");
            clearPassLineOddsPot();
            setPointOff();
        }
    }

    public void checkHardwayWinner(int sumofRoll, Double betAmount, ArrayList<Dice> rolledDice) {
        if (sumofRoll == hardwayNumber && betAmount > 0) {
            if (areDiceTheSame(rolledDice)) {
                System.out.println("You won " + hardWaysPot + " on your Hardway bet");
                crapsPlayer.collectCraps(hardWaysPot, 2.0);
                clearHardwayPot();
            } else {
                System.out.println("You lost " + hardWaysPot + " on your Hardway bet");
                clearHardwayPot();
            }
        }
    }

    public ArrayList<Dice> rollDice() {
        rolledDice = (crapsPlayer.rollDice(2));
        return rolledDice;
    }

    public Integer rollDiceSum(ArrayList<Dice> lastRoll) {
        summedRoll += crapsPlayer.sumOfRoll(lastRoll);
        System.out.println("You rolled " + summedRoll);
        return summedRoll;
    }


    public CrapsPlayer getCrapsPlayer() {
        return this.crapsPlayer;
    }

    public int getPointNumber() {
        return this.pointNumber;
    }

    public void setPointNumber(int numberRolled) {
        pointNumber = numberRolled;
    }

    public boolean getPointOn() {
        return pointOn;
    }

    public Double getPassLinePot() {
        return passLinePot;
    }

    public Double getFieldBet() {
        return fieldPot;
    }

    public Double getHardwayBet() {
        return hardWaysPot;
    }

    public void setPointOn() {
        this.pointOn = true;
    }

    public void setPointOff() {
        this.pointOn = false;
    }

    public void setHardWayPot(Double amount) {
        this.hardWaysPot = amount;
    }

    public void setHardwayNumber(Integer hardwayNumber) {
        this.hardwayNumber = hardwayNumber;
    }

    public boolean areDiceTheSame(ArrayList<Dice> diceRolled) {
        if (diceRolled.get(0).getValue() == diceRolled.get(1).getValue()) {
            return true;
        } else {
            return false;
        }
    }

}


