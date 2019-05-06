package io.zipcoder.casino.DiceGame.Yahtzee;

import io.zipcoder.casino.DiceGame.DiceUtils.Dice;

import java.util.List;

public class YahtzeeDisplay {

    public static String categoryString() {
        return "Enter the category you want to mark on your scorecard.\n" +
                " 'aces', 'twos', 'threes', fours', 'fives', 'sixes'\n" +
                "     'three of a kind', 'four of a kind', 'full house',\n" +
                "'small straight', large straight', 'yahtzee', 'chance'\n" +
                "           Enter 'back' to go back.\n";
    }


    public static String allOptions() {
        return "Type 'save' to save rolled dice.\n" +
                "Type 'return' to return saved dice to be rolled again.\n" +
                "Type 'roll' to roll again.\n" +
                "Type 'scorecard' to see scorecard.\n" +
                "Type 'mark' to mark a score on you scorecard.\n" +
                "Type 'exit' to walk away.";
    }


    public static String listOfDiceToDiceString(List<Dice> diceList) {
        String diceString = "";
        for (Dice die : diceList) {
            if (die.getValue() == 1) {
                diceString = diceString + "  ⚀  |";
            } else if (die.getValue() == 2) {
                diceString = diceString + "  ⚁  |";
            } else if (die.getValue() == 3) {
                diceString = diceString + "  ⚂  |";
            } else if (die.getValue() == 4) {
                diceString = diceString + "  ⚃  |";
            } else if (die.getValue() == 5) {
                diceString = diceString + "  ⚄  |";
            } else if (die.getValue() == 6) {
                diceString = diceString + "  ⚅  |";
            }
        }
        return diceString;
    }

    public static String getCurrentDiceString(List<Dice> rolledDice, List<Dice> savedDice) {
        String currentDiceString = "";
        String spacerString = "\n|------------------------------------------|\n";
        String numberString = "|            |  1  |  2  |  3  |  4  |  5  |";


        String rolledDiceString = "|Rolled Dice |" + YahtzeeDisplay.listOfDiceToDiceString(rolledDice);
        for (int i = 0; i < 5 - rolledDice.size(); i++) {
            rolledDiceString = rolledDiceString + "     |";
        }

        String savedDiceString = "| Saved Dice |";

        for (int i = 0; i < rolledDice.size(); i++) {
            savedDiceString = savedDiceString + "     |";
        }

        savedDiceString = savedDiceString + YahtzeeDisplay.listOfDiceToDiceString(savedDice);
        currentDiceString = spacerString + numberString + spacerString + rolledDiceString + spacerString + savedDiceString + spacerString;

        return currentDiceString;
    }

    public static String welcomeToYahtzeeString() {
        return "\n⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅\n" +
                "      ___       __   __         ___    ___  __                   ___ __  ___  ___   /\n" +
                "|  | |__  |    /  ` /  \\  |\\/| |__      |  /  \\    \\ /  /\\  |__|  |   / |__  |__   / \n" +
                "|/\\| |___ |___ \\__, \\__/  |  | |___     |  \\__/     |  /~~\\ |  |  |  /_ |___ |___ .  \n\n" +
                "⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅\n";
    }
}
