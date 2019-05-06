package io.zipcoder.casino.DiceGame.Yahtzee;

import io.zipcoder.casino.DiceGame.DiceUtils.Dice;
import io.zipcoder.casino.DiceGame.DiceUtils.DiceGame;
import io.zipcoder.casino.Utilities.Player;
import io.zipcoder.casino.Utilities.Console;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.zipcoder.casino.DiceGame.Yahtzee.YahtzeeDisplay.*;


public class Yahtzee extends DiceGame {
    private YahtzeePlayer yahtzeePlayer;
    private int score;
    public Scorecard scoreCard;
    private List<Dice> savedDice;
    private List<Dice> rolledDice;
    private boolean playing = true;
    Console console = Console.getInstance();
    String input = "";
    String input2 = "";


    public Yahtzee(Player player) {
        this.yahtzeePlayer = new YahtzeePlayer(player);
        this.score = 0;
        this.scoreCard = new Scorecard();
        this.savedDice = new ArrayList<>();
        this.rolledDice = new ArrayList<>();
    }

    @Override
    public void play() {
        console.println(welcomeToYahtzeeString());
        input = console.getStringInput("\nHello %s!  Welcome to Yahtzee!  Type 'roll' to begin!", yahtzeePlayer.getName());
        while (playing) {
            playGame();
        }
    }


    public void walkAway() {
        playing = false;
        System.out.println("Thank you for playing Yahtzee!");
    }


    public String exit(String input) {
        walkAway();
        return "Not a prompt";
    }


    public List<Dice> getAllDice(List<Dice> rolledDice, List<Dice> savedDice) {

        Stream<Dice> rolledDiceStream = rolledDice.stream();
        Stream<Dice> savedDiceStream = savedDice.stream();
        Stream<Dice> allDiceStream = Stream.concat(rolledDiceStream, savedDiceStream);

        return allDiceStream.collect(Collectors.toList());
    }

    public static Integer getScoreForCategory(String category, List<Dice> allDice) {
        String categoryToScore = category.toUpperCase().replaceAll("\\s+", "");

        Integer value = YahtzeeCategory.valueOf(categoryToScore).getValue();
        Integer score = YahtzeeCategory.valueOf(categoryToScore).perform(allDice, value);

        return score;
    }


    public static int scoreUpperSection(List<Dice> allDice, int value) {
        int score = 0;

        for (Dice die : allDice) {
            if (die.getValue() == value) {
                score += value;
            }
        }
        return score;
    }


    public static boolean hasThreeOfAKind(List<Dice> allDice) {
        Integer[] diceCount = countDice(allDice);
        for (Integer dieCount : diceCount) {
            if (dieCount >= 3) {
                return true;
            }
        }
        return false;
    }

    public static Integer scoreThreeOfAKind(List<Dice> allDice) {
        if (hasThreeOfAKind(allDice)) {
            return getSumOfDice(allDice);
        }
        return 0;
    }


    public static boolean hasFourOfAKind(List<Dice> allDice) {
        Integer[] diceCount = countDice(allDice);
        for (Integer dieCount : diceCount) {
            if (dieCount >= 4) {
                return true;
            }
        }
        return false;
    }


    public static int scoreFourOfAKind(List<Dice> allDice) {
        if (hasFourOfAKind(allDice)) {
            return getSumOfDice(allDice);
        }
        return 0;
    }


    public static boolean hasFullHouse(List<Dice> allDice) {
        Integer[] diceCount = countDice(allDice);
        boolean hasTwoCount = false;
        boolean hasThreeCount = false;

        for (Integer dieCount : diceCount) {
            if (dieCount == 2) {
                hasTwoCount = true;
            }
            if (dieCount == 3) {
                hasThreeCount = true;
            }
        }
        return (hasTwoCount && hasThreeCount);
    }


    public static int scoreFullHouse(List<Dice> allDice) {
        if (hasFullHouse(allDice)) {
            return 25;
        } else {
            return 0;
        }
    }


    public static boolean hasSmallStraight(List<Dice> allDice) {
        Integer[] diceCount = countDice(allDice);

        if (((diceCount[0] >= 1) && (diceCount[1] >= 1) && (diceCount[2] >= 1) && (diceCount[3] >= 1)) ||
                ((diceCount[1] >= 1) && (diceCount[2] >= 1) && (diceCount[3] >= 1) && (diceCount[4] >= 1)) ||
                ((diceCount[2] >= 1) && (diceCount[3] >= 1) && (diceCount[4] >= 1) && (diceCount[5] >= 1))) {
            return true;
        }
        return false;
    }


    public static int scoreSmallStraight(List<Dice> allDice) {
        if (hasSmallStraight(allDice)) {
            return 30;
        } else {
            return 0;
        }
    }


    public static boolean hasLargeStraight(List<Dice> allDice) {
        Integer[] diceCount = countDice(allDice);

        if (((diceCount[0] == 1) && (diceCount[1] == 1) && (diceCount[2] == 1) && (diceCount[3] == 1) && (diceCount[4] == 1)) ||
                ((diceCount[1] == 1) && (diceCount[2] == 1) && (diceCount[3] == 1) && (diceCount[4] == 1) && (diceCount[5] == 1))) {
            return true;
        }
        return false;
    }


    public static int scoreLargeStraight(List<Dice> allDice) {
        if (hasLargeStraight(allDice)) {
            return 40;
        } else {
            return 0;
        }
    }


    public static boolean hasYahtzee(List<Dice> allDice) {
        Integer[] diceCount = countDice(allDice);

        for (Integer dieCount : diceCount) {
            if (dieCount == 5) {
                return true;
            }
        }
        return false;
    }


    public static int scoreYahtzee(List<Dice> allDice) {
        if (hasYahtzee(allDice)) {
            return 50;
        }
        return 0;
    }

    public static int scoreChance(List<Dice> allDice) {
        return getSumOfDice(allDice);
    }

    public static Integer[] countDice(List<Dice> dice) {
        Integer[] diceCounter = {0, 0, 0, 0, 0, 0};
        for (Dice die : dice) {
            diceCounter[die.getValue() - 1]++;
        }
        return diceCounter;
    }


    public static int getSumOfDice(List<Dice> dice) {
        int sum = 0;

        for (Dice die : dice) {
            sum += die.getValue();
        }
        return sum;
    }


    public String roll(String s) {
        try {
            rolledDice = yahtzeePlayer.playerRollDice(5 - savedDice.size());
            console.println("\nRoll #%d", yahtzeePlayer.getRollNumber());
            console.println(getCurrentDiceString(rolledDice, savedDice));
            return allOptions();

        } catch (YahtzeePlayer.TooManyRollsException tooManyRollsException) {
            return "\nYou have already rolled 3 times.  Type 'mark' to mark your scorecard.";
        }
    }

    public String saveDice(String diceToSave) {
        try {
            for (Dice die : yahtzeePlayer.saveDice(rolledDice, diceToSave)) {
                savedDice.add(die);
            }
            console.println("Dice saved.\n\nRoll #%d\n" + getCurrentDiceString(rolledDice, savedDice), yahtzeePlayer.getRollNumber());
            return allOptions();
        } catch (IndexOutOfBoundsException i) {
            return "Invalid input.  " + allOptions();
        }
    }


    public String returnDice(String diceToReturn) {
        try {
            for (Dice die : yahtzeePlayer.returnDice(savedDice, diceToReturn)) {
                rolledDice.add(die);
            }
            console.println("Dice returned.\n\nRoll #%d\n" + getCurrentDiceString(rolledDice, savedDice), yahtzeePlayer.getRollNumber());
            return allOptions();
        } catch (IndexOutOfBoundsException ioobEx) {
            return "Invalid input.  " + allOptions();
        }
    }

    public String showScorecard(String input) {
        console.println(scoreCard.getScoreCardString());
        return "Type 'back' to go back.  Type 'mark' to mark scorecard";
    }


    public String back(String input) {
        console.println("\nRoll #%d", yahtzeePlayer.getRollNumber());
        console.println(getCurrentDiceString(rolledDice, savedDice));
        return allOptions();
    }


    public String markScore() {
        if (scoreCard.isValidCategory(input2)) {
            if (scoreCard.getScorecard().get(input2.toLowerCase()) != null) {
                console.println("You already have a score for %s", input2);
                return "Not a prompt";
            } else {
                return markAndReset();
            }
        } else {
            return "Invalid category. Enter 'mark' to try again.";
        }
    }

    public String markAndReset() {
        scoreCard.markScoreCard(input2.toLowerCase(), getAllDice(rolledDice, savedDice));
        scoreCard.getScorecard().put("total score", scoreCard.getTotalScore());
        resetDice();
        console.println(scoreCard.getScoreCardString());
        return checkScorecardComplete();
    }


    public void resetDice() {
        rolledDice.removeAll(rolledDice);
        savedDice.removeAll(savedDice);
        yahtzeePlayer.setRollNumber(0);
    }


    public String checkScorecardComplete() {
        if (scoreCard.scorecardComplete()) {
            console.println("Thank you for playing Yahtzee!  Your final score is %d.", scoreCard.getTotalScore());
            playing = false;
            return "Not a prompt";
        }
        return "Type 'roll' to start your next turn.";
    }


    public String invalidInputCheck() {
        if (!(input.toLowerCase().equals("roll") || input.toLowerCase().equals("save") || input.toLowerCase().equals("return") ||
                input.toLowerCase().equals("scorecard") || input.toLowerCase().equals("mark") || input.toLowerCase().equals("back")
                || input.toLowerCase().equals("exit"))) {
        }
        return "Invalid input.  " + allOptions();
    }


    public String checkForBack(String back) {
        if (back.toLowerCase().equals("back")) {
            return back(back);

        } else {
            return markScore();
        }
    }

    public void getSaveDicePrompt() {
        if ("save".equals(input.toLowerCase())) {
            input2 = console.getStringInput("Type the locations of the dice you want to save.\n" +
                    "(Ex: '123' to save first three dice)");
        }
    }

    public void getReturnDicePrompt() {
        if ("return".equals(input.toLowerCase())) {
            input2 = console.getStringInput("Type the locations of the dice you want to return.\n" +
                    "(Ex: '123' to save first three dice)");
        }
    }

    public void getMarkPrompt() {
        if ("mark".equals(input.toLowerCase())) {
            input2 = console.getStringInput(categoryString());
        }
    }

    public void getAllPrompts(){
        getSaveDicePrompt();
        getReturnDicePrompt();
        getMarkPrompt();
    }

    public void playGame() {
        try {
            getAllPrompts();

            String prompt = YahtzeeAction.valueOf(input.toUpperCase()).perform(this, input2);
            if (!prompt.equals("Not a prompt")) {
                input = console.getStringInput(prompt);
            }
        } catch (IllegalArgumentException iae) {
            input = console.getStringInput(invalidInputCheck());
        }
    }


    public boolean getPlaying() {
        return playing;
    }

    public List<Dice> getSavedDice() {
        return this.savedDice;
    }

    public int getScore() {
        return score;
    }

    public List<Dice> getRolledDice() {
        return rolledDice;
    }

    public YahtzeePlayer getYahtzeePlayer() {
        return this.yahtzeePlayer;
    }

    public void setInput2(String input) {
        this.input2 = input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
}
