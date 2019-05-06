package io.zipcoder.casino.YahtzeeTests;

import io.zipcoder.casino.DiceGame.DiceUtils.Dice;
import org.junit.Assert;
import org.junit.Test;
import io.zipcoder.casino.DiceGame.Yahtzee.YahtzeeDisplay;

import java.util.ArrayList;
import java.util.List;

public class YahtzeeDisplayTests {

    Dice d1 = new Dice(1, 1);
    Dice d2 = new Dice(1, 2);
    Dice d3 = new Dice(1, 3);
    Dice d4 = new Dice(1, 4);
    Dice d5 = new Dice(1, 5);
    Dice d6 = new Dice(1, 6);

    @Test
    public void categoryStringTest() {
        // Given
        String expected = "Enter the category you want to mark on your scorecard.\n" +
                " 'aces', 'twos', 'threes', fours', 'fives', 'sixes'\n" +
                "     'three of a kind', 'four of a kind', 'full house',\n" +
                "'small straight', large straight', 'yahtzee', 'chance'\n" +
                "           Enter 'back' to go back.\n";

        // When
        String actual = YahtzeeDisplay.categoryString();

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void allOptionsTest(){
        // Given
        String expected = "Type 'save' to save rolled dice.\n" +
                "Type 'return' to return saved dice to be rolled again.\n" +
                "Type 'roll' to roll again.\n" +
                "Type 'scorecard' to see scorecard.\n" +
                "Type 'mark' to mark a score on you scorecard.\n" +
                "Type 'exit' to walk away.";

        // When
        String actual = YahtzeeDisplay.allOptions();

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void listOfDiceToStringTest(){
        // Given
        List<Dice> dice = new ArrayList<>();
        dice.add(d1);
        dice.add(d2);
        dice.add(d3);
        dice.add(d6);
        dice.add(d5);
        dice.add(d4);

        String expected = "  ⚀  |  ⚁  |  ⚂  |  ⚅  |  ⚄  |  ⚃  |";

        // When
        String actual = YahtzeeDisplay.listOfDiceToDiceString(dice);

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void getCurrentDiceStringTest(){
        List<Dice> rolledDice = new ArrayList<>();
        List<Dice> savedDice = new ArrayList<>();

        rolledDice.add(d1);
        rolledDice.add(d2);
        savedDice.add(d5);
        savedDice.add(d5);
        savedDice.add(d6);

        String expected ="\n|------------------------------------------|\n" +
                "|            |  1  |  2  |  3  |  4  |  5  |" +
                "\n|------------------------------------------|\n" +
                "|Rolled Dice |  ⚀  |  ⚁  |     |     |     |" +
                "\n|------------------------------------------|\n" +
                "| Saved Dice |  ⚄  |  ⚄  |  ⚅  |     |     |" +
                "\n|------------------------------------------|\n";

        // When
        String actual = YahtzeeDisplay.getCurrentDiceString(rolledDice, savedDice);
    }

    @Test
    public void welcomeToYahtzeeStringTest(){
        // Given
        String expected = "\n⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅\n" +
                "      ___       __   __         ___    ___  __                   ___ __  ___  ___   /\n" +
                "|  | |__  |    /  ` /  \\  |\\/| |__      |  /  \\    \\ /  /\\  |__|  |   / |__  |__   / \n" +
                "|/\\| |___ |___ \\__, \\__/  |  | |___     |  \\__/     |  /~~\\ |  |  |  /_ |___ |___ .  \n\n" +
                "⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅ ⚀ ⚁ ⚂ ⚃ ⚄ ⚅\n";

        // When
        String actual = YahtzeeDisplay.welcomeToYahtzeeString();

        // Then
        Assert.assertEquals(expected, actual);
    }

}
