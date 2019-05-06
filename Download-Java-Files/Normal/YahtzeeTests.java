package io.zipcoder.casino.YahtzeeTests;

import io.zipcoder.casino.DiceGame.DiceUtils.Dice;
import io.zipcoder.casino.DiceGame.Yahtzee.Yahtzee;
import io.zipcoder.casino.Utilities.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import static io.zipcoder.casino.DiceGame.Yahtzee.YahtzeeDisplay.*;

public class YahtzeeTests {

    Dice d1 = new Dice(1, 1);
    Dice d2 = new Dice(1, 2);
    Dice d3 = new Dice(1, 3);
    Dice d4 = new Dice(1, 4);
    Dice d5 = new Dice(1, 5);
    Dice d6 = new Dice(1, 6);

    @Test
    public void YahtzeeConstructorTest() {
        // Given
        String expectedYahtzeePlayerName = "Cara";
        Player player = new Player(expectedYahtzeePlayerName, 1000.00);
        ArrayList<Dice> expectedSavedDice = new ArrayList<>();
        ArrayList<Dice> expectedRolledDice = new ArrayList<>();
        int expectedScore = 0;

        // When
        Yahtzee yahtzee = new Yahtzee(player);
        String actualYahtzeePlayerName = yahtzee.getYahtzeePlayer().getName();
        List<Dice> actualSavedDice = yahtzee.getSavedDice();
        List<Dice> actualRolledDice = yahtzee.getRolledDice();
        int actualScore = yahtzee.getScore();

        // Then
        Assert.assertEquals(expectedYahtzeePlayerName, actualYahtzeePlayerName);
        Assert.assertEquals(expectedSavedDice, actualSavedDice);
        Assert.assertEquals(expectedRolledDice, actualRolledDice);
        Assert.assertEquals(expectedScore, actualScore);
    }


    @Test
    public void getAllDiceTest() {
        // Given
        Player player = new Player("Cara", 1000.0);
        Yahtzee yahtzee = new Yahtzee(player);

        yahtzee.getRolledDice().add(d1);
        yahtzee.getRolledDice().add(d2);
        yahtzee.getRolledDice().add(d3);

        yahtzee.getSavedDice().add(d4);
        yahtzee.getSavedDice().add(d5);

        ArrayList<Dice> expectedAllDice = new ArrayList<Dice>();
        expectedAllDice.add(d1);
        expectedAllDice.add(d2);
        expectedAllDice.add(d3);
        expectedAllDice.add(d4);
        expectedAllDice.add(d5);

        // When
        List<Dice> actualAllDice = yahtzee.getAllDice(yahtzee.getRolledDice(), yahtzee.getSavedDice());

        // Then
        Assert.assertEquals(expectedAllDice, actualAllDice);
    }


    @Test
    public void getScoreForCategoryTest() {
        // Given
        Player player = new Player("Cara", 1000.0);
        Yahtzee yahtzee = new Yahtzee(player);

        // Aces
        ArrayList<Dice> acesDice = new ArrayList<>();
        acesDice.add(d1);
        acesDice.add(d2);
        acesDice.add(d3);
        acesDice.add(d1);
        acesDice.add(d1);
        int expectedAcesScore = 3;

        // Twos
        ArrayList<Dice> twosDice = new ArrayList<>();
        twosDice.add(d2);
        twosDice.add(d6);
        twosDice.add(d5);
        twosDice.add(d2);
        twosDice.add(d1);
        int expectedTwosScore = 4;

        // Threes
        ArrayList<Dice> threesDice = new ArrayList<>();
        threesDice.add(d3);
        threesDice.add(d3);
        threesDice.add(d4);
        threesDice.add(d3);
        threesDice.add(d3);
        int expectedThreesScore = 12;

        // Fours
        ArrayList<Dice> foursDice = new ArrayList<>();
        foursDice.add(d4);
        foursDice.add(d1);
        foursDice.add(d4);
        foursDice.add(d5);
        foursDice.add(d2);
        int expectedFoursScore = 8;

        // Fives
        ArrayList<Dice> fivesDice = new ArrayList<>();
        fivesDice.add(d5);
        fivesDice.add(d5);
        fivesDice.add(d5);
        fivesDice.add(d5);
        fivesDice.add(d5);
        int expectedFivesScore = 25;

        // Sixes
        ArrayList<Dice> sixesDice = new ArrayList<>();
        sixesDice.add(d1);
        sixesDice.add(d6);
        sixesDice.add(d6);
        sixesDice.add(d6);
        sixesDice.add(d1);
        int expectedSixesScore = 18;

        // Three of a Kind
        ArrayList<Dice> threeOfAKindDice = new ArrayList<>();
        threeOfAKindDice.add(d5);
        threeOfAKindDice.add(d4);
        threeOfAKindDice.add(d5);
        threeOfAKindDice.add(d5);
        threeOfAKindDice.add(d2);
        int expectedThreeOfAKindScore = 21;
        int expectedNotThreeOfAKindScore = 0;

        // Four of a Kind
        ArrayList<Dice> fourOfAKindDice = new ArrayList<>();
        fourOfAKindDice.add(d1);
        fourOfAKindDice.add(d1);
        fourOfAKindDice.add(d1);
        fourOfAKindDice.add(d6);
        fourOfAKindDice.add(d1);
        int expectedFourOfAKindScore = 10;
        int expectedNotFourOfAKindScore = 0;

        // Small Straight
        ArrayList<Dice> smallStraightDice = new ArrayList<>();
        smallStraightDice.add(d3);
        smallStraightDice.add(d2);
        smallStraightDice.add(d1);
        smallStraightDice.add(d3);
        smallStraightDice.add(d4);
        int expectedSmallStraightScore = 30;
        int expectedNotSmallStraightScore = 0;

        // Large Straight
        ArrayList<Dice> largeStraightDice = new ArrayList<>();
        largeStraightDice.add(d2);
        largeStraightDice.add(d3);
        largeStraightDice.add(d4);
        largeStraightDice.add(d6);
        largeStraightDice.add(d5);
        int expectedLargeStraight = 40;
        int expectedNotLargeStraight = 0;

        // Yahtzee
        ArrayList<Dice> yahtzeeDice = new ArrayList<>();
        yahtzeeDice.add(d2);
        yahtzeeDice.add(d2);
        yahtzeeDice.add(d2);
        yahtzeeDice.add(d2);
        yahtzeeDice.add(d2);
        int expectedYahtzeeScore = 50;
        int expectedNotYahtzeeScore = 0;

        // Chance
        ArrayList<Dice> chanceDice = new ArrayList<>();
        chanceDice.add(d1);
        chanceDice.add(d2);
        chanceDice.add(d5);
        chanceDice.add(d6);
        chanceDice.add(d6);
        int expectedChanceScore = 20;


        // When
        int actualAcesScore = yahtzee.getScoreForCategory("Aces", acesDice);
        int actualTwosScore = yahtzee.getScoreForCategory("Twos", twosDice);
        int actualThreesScore = yahtzee.getScoreForCategory("Threes", threesDice);
        int actualFoursScore = yahtzee.getScoreForCategory("Fours", foursDice);
        int actualFivesScore = yahtzee.getScoreForCategory("Fives", fivesDice);
        int actualSixesScore = yahtzee.getScoreForCategory("Sixes", sixesDice);

        int actualThreeOfAKindScore = yahtzee.getScoreForCategory("Three of a kind", threeOfAKindDice);
        int actualNotThreeOfAKindScore = yahtzee.getScoreForCategory("Three of a kind", twosDice);

        int actualFourOfAKindScore = yahtzee.getScoreForCategory("Four of a kind", fourOfAKindDice);
        int actualNotFourOfAKindScore = yahtzee.getScoreForCategory("Four of a kind", foursDice);

        int actualSmallStraightScore = yahtzee.getScoreForCategory("Small straight", smallStraightDice);
        int actualNotSmallStraightScore = yahtzee.getScoreForCategory("Small straight", fourOfAKindDice);

        int actualLargeStraightScore = yahtzee.getScoreForCategory("Large straight", largeStraightDice);
        int actualNotLargeStraightScore = yahtzee.getScoreForCategory("Large straight", smallStraightDice);

        int actualYahtzeeScore = yahtzee.getScoreForCategory("Yahtzee", yahtzeeDice);
        int actualNotYahtzeeScore = yahtzee.getScoreForCategory("Yahtzee", fourOfAKindDice);

        int actualChanceScore = yahtzee.getScoreForCategory("Chance", chanceDice);


        // Then
        Assert.assertEquals(expectedAcesScore, actualAcesScore);
        Assert.assertEquals(expectedTwosScore, actualTwosScore);
        Assert.assertEquals(expectedThreesScore, actualThreesScore);
        Assert.assertEquals(expectedFoursScore, actualFoursScore);
        Assert.assertEquals(expectedFivesScore, actualFivesScore);
        Assert.assertEquals(expectedSixesScore, actualSixesScore);

        Assert.assertEquals(expectedThreeOfAKindScore, actualThreeOfAKindScore);
        Assert.assertEquals(expectedNotThreeOfAKindScore, actualNotThreeOfAKindScore);

        Assert.assertEquals(expectedFourOfAKindScore, actualFourOfAKindScore);
        Assert.assertEquals(expectedNotFourOfAKindScore, actualNotFourOfAKindScore);

        Assert.assertEquals(expectedSmallStraightScore, actualSmallStraightScore);
        Assert.assertEquals(expectedNotSmallStraightScore, actualNotSmallStraightScore);

        Assert.assertEquals(expectedLargeStraight, actualLargeStraightScore);
        Assert.assertEquals(expectedNotLargeStraight, actualNotLargeStraightScore);

        Assert.assertEquals(expectedYahtzeeScore, actualYahtzeeScore);
        Assert.assertEquals(expectedNotYahtzeeScore, actualNotYahtzeeScore);

        Assert.assertEquals(expectedChanceScore, actualChanceScore);
    }


    @Test
    public void scoreAcesTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceWith2Aces = new ArrayList<>();
        diceWith2Aces.add(d1);
        diceWith2Aces.add(d2);
        diceWith2Aces.add(d3);
        diceWith2Aces.add(d1);
        diceWith2Aces.add(d5);
        int expectedScore1 = 2;

        ArrayList<Dice> diceWith0Aces = new ArrayList<>();
        diceWith0Aces.add(d5);
        diceWith0Aces.add(d2);
        diceWith0Aces.add(d3);
        diceWith0Aces.add(d4);
        diceWith0Aces.add(d6);
        int expectedScore2 = 0;

        // When
        int actualScore1 = yahtzee.scoreUpperSection(diceWith2Aces, 1);
        int actualScore2 = yahtzee.scoreUpperSection(diceWith0Aces, 1);

        // Then
        Assert.assertEquals(expectedScore1, actualScore1);
        Assert.assertEquals(expectedScore2, actualScore2);
    }


    @Test
    public void scoreTwosTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceWith4Twos = new ArrayList<>();
        diceWith4Twos.add(d2);
        diceWith4Twos.add(d3);
        diceWith4Twos.add(d2);
        diceWith4Twos.add(d2);
        diceWith4Twos.add(d2);
        int expectedScore1 = 8;

        ArrayList<Dice> diceWith0Twos = new ArrayList<>();
        diceWith0Twos.add(d5);
        diceWith0Twos.add(d6);
        diceWith0Twos.add(d1);
        diceWith0Twos.add(d3);
        diceWith0Twos.add(d4);
        int expectedScore2 = 0;

        // When
        int actualScore1 = yahtzee.scoreUpperSection(diceWith4Twos, 2);
        int actualScore2 = yahtzee.scoreUpperSection(diceWith0Twos, 2);

        // Then
        Assert.assertEquals(expectedScore1, actualScore1);
        Assert.assertEquals(expectedScore2, actualScore2);
    }


    @Test
    public void scoreThreesTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceWith3Threes = new ArrayList<>();
        diceWith3Threes.add(d3);
        diceWith3Threes.add(d2);
        diceWith3Threes.add(d2);
        diceWith3Threes.add(d3);
        diceWith3Threes.add(d3);
        int expectedScore1 = 9;

        ArrayList<Dice> diceWith0Threes = new ArrayList<>();
        diceWith0Threes.add(d1);
        diceWith0Threes.add(d2);
        diceWith0Threes.add(d4);
        diceWith0Threes.add(d5);
        diceWith0Threes.add(d6);
        int expectedScore2 = 0;

        // When
        int actualScore1 = yahtzee.scoreUpperSection(diceWith3Threes, 3);
        int actualScore2 = yahtzee.scoreUpperSection(diceWith0Threes, 3);

        // Then
        Assert.assertEquals(expectedScore1, actualScore1);
        Assert.assertEquals(expectedScore2, actualScore2);
    }


    @Test
    public void scoreFoursTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceWith4Fours = new ArrayList<>();
        diceWith4Fours.add(d4);
        diceWith4Fours.add(d2);
        diceWith4Fours.add(d4);
        diceWith4Fours.add(d4);
        diceWith4Fours.add(d4);
        int expectedScore1 = 16;

        ArrayList<Dice> diceWith0Fours = new ArrayList<Dice>();
        diceWith0Fours.add(d1);
        diceWith0Fours.add(d2);
        diceWith0Fours.add(d3);
        diceWith0Fours.add(d5);
        diceWith0Fours.add(d6);
        int expectedScore2 = 0;

        // When
        int actualScore1 = yahtzee.scoreUpperSection(diceWith4Fours, 4);
        int actualScore2 = yahtzee.scoreUpperSection(diceWith0Fours, 4);

        // Then
        Assert.assertEquals(expectedScore1, actualScore1);
        Assert.assertEquals(expectedScore2, actualScore2);
    }


    @Test
    public void scoreFivesTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceWith3Fives = new ArrayList<>();
        diceWith3Fives.add(d1);
        diceWith3Fives.add(d2);
        diceWith3Fives.add(d5);
        diceWith3Fives.add(d5);
        diceWith3Fives.add(d5);
        int expectedScore1 = 15;

        ArrayList<Dice> diceWith0Fives = new ArrayList<>();
        diceWith0Fives.add(d1);
        diceWith0Fives.add(d2);
        diceWith0Fives.add(d3);
        diceWith0Fives.add(d4);
        diceWith0Fives.add(d6);
        int expectedScore2 = 0;

        // When
        int actualScore1 = yahtzee.scoreUpperSection(diceWith3Fives, 5);
        int actualScore2 = yahtzee.scoreUpperSection(diceWith0Fives, 5);

        // Then
        Assert.assertEquals(expectedScore1, actualScore1);
        Assert.assertEquals(expectedScore2, actualScore2);
    }


    @Test
    public void scoreSixesTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceWith4Sixes = new ArrayList<>();
        diceWith4Sixes.add(d6);
        diceWith4Sixes.add(d6);
        diceWith4Sixes.add(d1);
        diceWith4Sixes.add(d6);
        diceWith4Sixes.add(d6);
        int expectedScore1 = 24;

        ArrayList<Dice> diceWith0Sixes = new ArrayList<>();
        diceWith0Sixes.add(d1);
        diceWith0Sixes.add(d2);
        diceWith0Sixes.add(d3);
        diceWith0Sixes.add(d4);
        diceWith0Sixes.add(d5);
        int expectedScore2 = 0;

        // When
        int actualScore1 = yahtzee.scoreUpperSection(diceWith4Sixes, 6);
        int actualScore2 = yahtzee.scoreUpperSection(diceWith0Sixes, 6);

        // Then
        Assert.assertEquals(expectedScore1, actualScore1);
        Assert.assertEquals(expectedScore2, actualScore2);
    }


    @Test
    public void hasThreeOfAKindTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceWithThreeOfAKind = new ArrayList<>();
        diceWithThreeOfAKind.add(d3);
        diceWithThreeOfAKind.add(d2);
        diceWithThreeOfAKind.add(d3);
        diceWithThreeOfAKind.add(d4);
        diceWithThreeOfAKind.add(d3);

        ArrayList<Dice> diceWithoutThreeOfAKind = new ArrayList<>();
        diceWithoutThreeOfAKind.add(d1);
        diceWithoutThreeOfAKind.add(d2);
        diceWithoutThreeOfAKind.add(d3);
        diceWithoutThreeOfAKind.add(d4);
        diceWithoutThreeOfAKind.add(d3);

        // When
        boolean actualThreeOfAKind = yahtzee.hasThreeOfAKind(diceWithThreeOfAKind);
        boolean actualNotThreeOfAKind = yahtzee.hasThreeOfAKind(diceWithoutThreeOfAKind);

        // Then
        Assert.assertTrue(actualThreeOfAKind);
        Assert.assertFalse(actualNotThreeOfAKind);
    }


    @Test
    public void hasFourOfAKindTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceWithFourOfAKind = new ArrayList<>();
        diceWithFourOfAKind.add(d2);
        diceWithFourOfAKind.add(d2);
        diceWithFourOfAKind.add(d6);
        diceWithFourOfAKind.add(d2);
        diceWithFourOfAKind.add(d2);

        ArrayList<Dice> diceWithoutFourOfAKind = new ArrayList<>();
        diceWithoutFourOfAKind.add(d2);
        diceWithoutFourOfAKind.add(d3);
        diceWithoutFourOfAKind.add(d4);
        diceWithoutFourOfAKind.add(d2);
        diceWithoutFourOfAKind.add(d2);

        // When
        boolean actualFourOfAKind = yahtzee.hasFourOfAKind(diceWithFourOfAKind);
        boolean actualNotFourOfAKind = yahtzee.hasFourOfAKind(diceWithoutFourOfAKind);

        // Then
        Assert.assertTrue(actualFourOfAKind);
        Assert.assertFalse(actualNotFourOfAKind);
    }


    @Test
    public void hasFullHouseTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceWithFullHouse = new ArrayList<>();
        diceWithFullHouse.add(d3);
        diceWithFullHouse.add(d6);
        diceWithFullHouse.add(d6);
        diceWithFullHouse.add(d3);
        diceWithFullHouse.add(d3);

        ArrayList<Dice> diceWithoutFullHouse = new ArrayList<>();
        diceWithoutFullHouse.add(d3);
        diceWithoutFullHouse.add(d6);
        diceWithoutFullHouse.add(d6);
        diceWithoutFullHouse.add(d3);
        diceWithoutFullHouse.add(d2);

        // When
        boolean actualFullHouse = yahtzee.hasFullHouse(diceWithFullHouse);
        boolean actualNotFullHouse = yahtzee.hasFullHouse(diceWithoutFullHouse);

        // Then
        Assert.assertTrue(actualFullHouse);
        Assert.assertFalse(actualNotFullHouse);
    }


    @Test
    public void hasSmallStraightTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceWithSmallStraight = new ArrayList<>();
        diceWithSmallStraight.add(d1);
        diceWithSmallStraight.add(d4);
        diceWithSmallStraight.add(d6);
        diceWithSmallStraight.add(d2);
        diceWithSmallStraight.add(d3);

        ArrayList<Dice> diceWithoutSmallStraight = new ArrayList<>();
        diceWithoutSmallStraight.add(d1);
        diceWithoutSmallStraight.add(d2);
        diceWithoutSmallStraight.add(d3);
        diceWithoutSmallStraight.add(d5);
        diceWithoutSmallStraight.add(d6);

        // When
        boolean actualSmallStraight = yahtzee.hasSmallStraight(diceWithSmallStraight);
        boolean actualNotSmallStraight = yahtzee.hasSmallStraight(diceWithoutSmallStraight);

        // Then
        Assert.assertTrue(actualSmallStraight);
        Assert.assertFalse(actualNotSmallStraight);
    }


    @Test
    public void hasLargeStraightTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceWithLargeStraight = new ArrayList<>();
        diceWithLargeStraight.add(d6);
        diceWithLargeStraight.add(d2);
        diceWithLargeStraight.add(d5);
        diceWithLargeStraight.add(d3);
        diceWithLargeStraight.add(d4);

        ArrayList<Dice> diceWithoutLargeStraight = new ArrayList<>();
        diceWithoutLargeStraight.add(d6);
        diceWithoutLargeStraight.add(d2);
        diceWithoutLargeStraight.add(d4);
        diceWithoutLargeStraight.add(d3);
        diceWithoutLargeStraight.add(d1);

        // When
        boolean actualLargeStraight = yahtzee.hasLargeStraight(diceWithLargeStraight);
        boolean actualNotLargeStraight = yahtzee.hasLargeStraight(diceWithoutLargeStraight);

        // Then
        Assert.assertTrue(actualLargeStraight);
        Assert.assertFalse(actualNotLargeStraight);
    }


    @Test
    public void hasYahtzeeTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceWithYahtzee = new ArrayList<>();
        diceWithYahtzee.add(d2);
        diceWithYahtzee.add(d2);
        diceWithYahtzee.add(d2);
        diceWithYahtzee.add(d2);
        diceWithYahtzee.add(d2);

        ArrayList<Dice> diceWithoutYahtzee = new ArrayList<>();
        diceWithoutYahtzee.add(d2);
        diceWithoutYahtzee.add(d2);
        diceWithoutYahtzee.add(d2);
        diceWithoutYahtzee.add(d2);
        diceWithoutYahtzee.add(d1);

        // When
        boolean actualYahtzee = yahtzee.hasYahtzee(diceWithYahtzee);
        boolean actualNotYahtzee = yahtzee.hasYahtzee(diceWithoutYahtzee);

        // Then
        Assert.assertTrue(actualYahtzee);
        Assert.assertFalse(actualNotYahtzee);
    }


    @Test
    public void scoreThreeOfAKindTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceWithThreeOfAKind = new ArrayList<>();
        diceWithThreeOfAKind.add(d5);
        diceWithThreeOfAKind.add(d5);
        diceWithThreeOfAKind.add(d3);
        diceWithThreeOfAKind.add(d4);
        diceWithThreeOfAKind.add(d5);
        int expectedScore1 = 22;

        ArrayList<Dice> diceWithoutThreeOfAKind = new ArrayList<>();
        diceWithoutThreeOfAKind.add(d1);
        diceWithoutThreeOfAKind.add(d2);
        diceWithoutThreeOfAKind.add(d3);
        diceWithoutThreeOfAKind.add(d4);
        diceWithoutThreeOfAKind.add(d3);
        int expectedScore2 = 0;

        // When
        int actualScore1 = yahtzee.scoreThreeOfAKind(diceWithThreeOfAKind);
        int actualScore2 = yahtzee.scoreThreeOfAKind(diceWithoutThreeOfAKind);

        // Then
        Assert.assertEquals(expectedScore1, actualScore1);
        Assert.assertEquals(expectedScore2, actualScore2);
    }


    @Test
    public void scoreFourOfAKindTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceWithFourOfAKind = new ArrayList<>();
        diceWithFourOfAKind.add(d5);
        diceWithFourOfAKind.add(d3);
        diceWithFourOfAKind.add(d5);
        diceWithFourOfAKind.add(d5);
        diceWithFourOfAKind.add(d5);
        int expectedScore1 = 23;

        ArrayList<Dice> diceWithoutFourOfAKind = new ArrayList<>();
        diceWithoutFourOfAKind.add(d5);
        diceWithoutFourOfAKind.add(d3);
        diceWithoutFourOfAKind.add(d3);
        diceWithoutFourOfAKind.add(d5);
        diceWithoutFourOfAKind.add(d5);
        int expectedScore2 = 0;

        // When
        int actualScore1 = yahtzee.scoreFourOfAKind(diceWithFourOfAKind);
        int actualScore2 = yahtzee.scoreFourOfAKind(diceWithoutFourOfAKind);

        // Then
        Assert.assertEquals(expectedScore1, actualScore1);
        Assert.assertEquals(expectedScore2, actualScore2);
    }


    @Test
    public void scoreFullHouseTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceWithFullHouse = new ArrayList<>();
        diceWithFullHouse.add(d5);
        diceWithFullHouse.add(d2);
        diceWithFullHouse.add(d5);
        diceWithFullHouse.add(d2);
        diceWithFullHouse.add(d5);
        int expectedScore1 = 25;

        ArrayList<Dice> diceWithoutFullHouse = new ArrayList<>();
        diceWithoutFullHouse.add(d5);
        diceWithoutFullHouse.add(d2);
        diceWithoutFullHouse.add(d5);
        diceWithoutFullHouse.add(d3);
        diceWithoutFullHouse.add(d5);
        int expectedScore2 = 0;

        // When
        int actualScore1 = yahtzee.scoreFullHouse(diceWithFullHouse);
        int actualScore2 = yahtzee.scoreFullHouse(diceWithoutFullHouse);

        // Then
        Assert.assertEquals(expectedScore1, actualScore1);
        Assert.assertEquals(expectedScore2, actualScore2);
    }


    @Test
    public void scoreSmallStraightTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceWithSmallStraight1 = new ArrayList<Dice>();
        diceWithSmallStraight1.add(d1);
        diceWithSmallStraight1.add(d4);
        diceWithSmallStraight1.add(d6);
        diceWithSmallStraight1.add(d2);
        diceWithSmallStraight1.add(d3);
        int expectedScore1 = 30;

        ArrayList<Dice> diceWithSmallStraight2 = new ArrayList<Dice>();
        diceWithSmallStraight2.add(d5);
        diceWithSmallStraight2.add(d4);
        diceWithSmallStraight2.add(d3);
        diceWithSmallStraight2.add(d2);
        diceWithSmallStraight2.add(d3);
        int expectedScore2 = 30;

        ArrayList<Dice> diceWithSmallStraight3 = new ArrayList<Dice>();
        diceWithSmallStraight3.add(d1);
        diceWithSmallStraight3.add(d4);
        diceWithSmallStraight3.add(d6);
        diceWithSmallStraight3.add(d5);
        diceWithSmallStraight3.add(d3);
        int expectedScore3 = 30;

        ArrayList<Dice> diceWithoutSmallStraight = new ArrayList<Dice>();
        diceWithoutSmallStraight.add(d1);
        diceWithoutSmallStraight.add(d2);
        diceWithoutSmallStraight.add(d3);
        diceWithoutSmallStraight.add(d5);
        diceWithoutSmallStraight.add(d6);
        int expectedScore4 = 0;

        // When
        int actualScore1 = yahtzee.scoreSmallStraight(diceWithSmallStraight1);
        int actualScore2 = yahtzee.scoreSmallStraight(diceWithSmallStraight2);
        int actualScore3 = yahtzee.scoreSmallStraight(diceWithSmallStraight3);
        int actualScore4 = yahtzee.scoreSmallStraight(diceWithoutSmallStraight);

        // Then
        Assert.assertEquals(expectedScore1, actualScore1);
        Assert.assertEquals(expectedScore2, actualScore2);
        Assert.assertEquals(expectedScore3, actualScore3);
        Assert.assertEquals(expectedScore4, actualScore4);
    }


    @Test
    public void scoreLargeStraightTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceWithLargeStraight1 = new ArrayList<Dice>();
        diceWithLargeStraight1.add(d6);
        diceWithLargeStraight1.add(d2);
        diceWithLargeStraight1.add(d5);
        diceWithLargeStraight1.add(d3);
        diceWithLargeStraight1.add(d4);
        int expectedScore1 = 40;

        ArrayList<Dice> diceWithLargeStraight2 = new ArrayList<Dice>();
        diceWithLargeStraight2.add(d1);
        diceWithLargeStraight2.add(d2);
        diceWithLargeStraight2.add(d5);
        diceWithLargeStraight2.add(d3);
        diceWithLargeStraight2.add(d4);
        int expectedScore2 = 40;

        ArrayList<Dice> diceWithoutLargeStraight = new ArrayList<Dice>();
        diceWithoutLargeStraight.add(d6);
        diceWithoutLargeStraight.add(d2);
        diceWithoutLargeStraight.add(d4);
        diceWithoutLargeStraight.add(d3);
        diceWithoutLargeStraight.add(d1);
        int expectedScore3 = 0;

        // When
        int actualScore1 = yahtzee.scoreLargeStraight(diceWithLargeStraight1);
        int actualScore2 = yahtzee.scoreLargeStraight(diceWithLargeStraight2);
        int actualScore3 = yahtzee.scoreLargeStraight(diceWithoutLargeStraight);

        // Then
        Assert.assertEquals(expectedScore1, actualScore1);
        Assert.assertEquals(expectedScore2, actualScore2);
        Assert.assertEquals(expectedScore3, actualScore3);
    }


    @Test
    public void scoreYahtzeeTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceWithYahtzee = new ArrayList<Dice>();
        diceWithYahtzee.add(d4);
        diceWithYahtzee.add(d4);
        diceWithYahtzee.add(d4);
        diceWithYahtzee.add(d4);
        diceWithYahtzee.add(d4);
        int expectedScore1 = 50;

        ArrayList<Dice> diceWithoutYahtzee = new ArrayList<Dice>();
        diceWithoutYahtzee.add(d4);
        diceWithoutYahtzee.add(d4);
        diceWithoutYahtzee.add(d4);
        diceWithoutYahtzee.add(d4);
        diceWithoutYahtzee.add(d6);
        int expectedScore2 = 0;

        // When
        int actualScore1 = yahtzee.scoreYahtzee(diceWithYahtzee);
        int actualScore2 = yahtzee.scoreYahtzee(diceWithoutYahtzee);

        // Then
        Assert.assertEquals(expectedScore1, actualScore1);
        Assert.assertEquals(expectedScore2, actualScore2);
    }


    @Test
    public void diceCounterTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceToCount1 = new ArrayList<Dice>();
        diceToCount1.add(d1);
        diceToCount1.add(d2);
        diceToCount1.add(d3);
        diceToCount1.add(d4);
        diceToCount1.add(d5);
        Integer[] expected1 = {1, 1, 1, 1, 1, 0};

        ArrayList<Dice> diceToCount2 = new ArrayList<Dice>();
        diceToCount2.add(d6);
        diceToCount2.add(d6);
        diceToCount2.add(d6);
        diceToCount2.add(d6);
        diceToCount2.add(d6);
        Integer[] expected2 = {0, 0, 0, 0, 0, 5};

        ArrayList<Dice> diceToCount3 = new ArrayList<Dice>();
        diceToCount3.add(d3);
        diceToCount3.add(d2);
        diceToCount3.add(d3);
        diceToCount3.add(d2);
        diceToCount3.add(d3);
        Integer[] expected3 = {0, 2, 3, 0, 0, 0};

        // When
        Integer[] actual1 = yahtzee.countDice(diceToCount1);
        Integer[] actual2 = yahtzee.countDice(diceToCount2);
        Integer[] actual3 = yahtzee.countDice(diceToCount3);

        // Then
        Assert.assertArrayEquals(expected1, actual1);
        Assert.assertArrayEquals(expected2, actual2);
        Assert.assertArrayEquals(expected3, actual3);
    }


    @Test
    public void getSumOfDiceTest() {
        // Given
        Player player = new Player("Cara", 1000.00);
        Yahtzee yahtzee = new Yahtzee(player);

        ArrayList<Dice> diceToSum1 = new ArrayList<>();
        diceToSum1.add(d1);
        diceToSum1.add(d4);
        diceToSum1.add(d2);
        diceToSum1.add(d6);
        diceToSum1.add(d1);
        int expectedSum1 = 14;

        ArrayList<Dice> diceToSum2 = new ArrayList<>();
        diceToSum2.add(d6);
        diceToSum2.add(d3);
        diceToSum2.add(d6);
        diceToSum2.add(d4);
        diceToSum2.add(d6);
        int expectedSum2 = 25;

        // When
        int actualSum1 = yahtzee.getSumOfDice(diceToSum1);
        int actualSum2 = yahtzee.getSumOfDice(diceToSum2);

        // Then
        Assert.assertEquals(expectedSum1, actualSum1);
        Assert.assertEquals(expectedSum2, actualSum2);
    }


    @Test
    public void walkAwayTest() {
        // Given
        Player player = new Player("Cara", 1000.0);
        Yahtzee yahtzee = new Yahtzee(player);
        yahtzee.setPlaying(true);

        // when
        yahtzee.walkAway();
        boolean actualFalse = yahtzee.getPlaying();

        // Then
        Assert.assertFalse(actualFalse);
    }

    @Test
    public void exitTest() {
        // Given
        Player player = new Player("Cara", 1000.0);
        Yahtzee yahtzee = new Yahtzee(player);
        yahtzee.setPlaying(true);
        String expected = "Not a prompt";

        // When
        String actual = yahtzee.exit("anything");
        boolean actualFalse = yahtzee.getPlaying();

        // Then
        Assert.assertEquals(expected, actual);
        Assert.assertFalse(actualFalse);
    }


    @Test
    public void rollTest() {
        // Given
        Player player = new Player("Cara", 1000.0);
        Yahtzee yahtzee = new Yahtzee(player);
        int expectedRolledDiceSize = 5;
        String expected = allOptions();
        int expectedRollNumber = 1;

        // When
        String actual = yahtzee.roll("anything");
        int actualRolledDiceSize = yahtzee.getRolledDice().size();
        int actualRollNumber = yahtzee.getYahtzeePlayer().getRollNumber();

        // Then
        Assert.assertEquals(expectedRolledDiceSize, actualRolledDiceSize);
        Assert.assertEquals(expectedRollNumber, actualRollNumber);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void rollTest2() {
        // Given
        Player player = new Player("Cara", 1000.0);
        Yahtzee yahtzee = new Yahtzee(player);
        yahtzee.getYahtzeePlayer().setRollNumber(3);
        String expected = "\nYou have already rolled 3 times.  Type 'mark' to mark your scorecard.";

        // When
        String actual = yahtzee.roll("anything");

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void saveDiceTest() {
        // Given
        Player player = new Player("Cara", 1000.0);
        Yahtzee yahtzee = new Yahtzee(player);
        yahtzee.getRolledDice().add(d2);
        yahtzee.getRolledDice().add(d6);
        yahtzee.getRolledDice().add(d2);
        yahtzee.getRolledDice().add(d1);
        yahtzee.getRolledDice().add(d5);

        String expected = allOptions();
        List<Dice> expectedSavedDice = new ArrayList<>();
        expectedSavedDice.add(d2);
        expectedSavedDice.add(d5);

        // When
        String actual = yahtzee.saveDice("15");
        List<Dice> actualSavedDice = yahtzee.getSavedDice();

        // Then
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expectedSavedDice, actualSavedDice);
    }

    @Test
    public void saveDiceTest2() {
        // Given
        Player player = new Player("Cara", 1000.0);
        Yahtzee yahtzee = new Yahtzee(player);
        yahtzee.getRolledDice().add(d2);
        yahtzee.getRolledDice().add(d6);
        yahtzee.getRolledDice().add(d2);
        yahtzee.getRolledDice().add(d1);
        yahtzee.getRolledDice().add(d5);

        String expected = "Invalid input.  " + allOptions();

        // When
        String actual = yahtzee.saveDice("6");

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void returnDiceTest() {
        // Given
        Player player = new Player("Cara", 1000.0);
        Yahtzee yahtzee = new Yahtzee(player);
        yahtzee.getSavedDice().add(d2);
        yahtzee.getSavedDice().add(d6);
        yahtzee.getSavedDice().add(d2);
        yahtzee.getSavedDice().add(d1);
        yahtzee.getSavedDice().add(d5);

        List<Dice> expectedReturnedDice = new ArrayList<>();
        expectedReturnedDice.add(d2);
        expectedReturnedDice.add(d5);

        String expected = allOptions();

        // When
        String actual = yahtzee.returnDice("15");
        List<Dice> actualReturnedDice = yahtzee.getRolledDice();

        // Then
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expectedReturnedDice, actualReturnedDice);
    }

    @Test
    public void returnDiceTest2() {
        // Given
        Player player = new Player("Cara", 1000.0);
        Yahtzee yahtzee = new Yahtzee(player);
        yahtzee.getSavedDice().add(d2);
        yahtzee.getSavedDice().add(d6);
        yahtzee.getSavedDice().add(d2);
        yahtzee.getSavedDice().add(d1);
        yahtzee.getSavedDice().add(d5);

        String expected = "Invalid input.  " + allOptions();

        // When
        String actual = yahtzee.returnDice("6");

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void showScorecardTest() {
        // Given
        Player player = new Player("Cara", 1000.0);
        Yahtzee yahtzee = new Yahtzee(player);
        String expected = "Type 'back' to go back.  Type 'mark' to mark scorecard";

        // When
        String actual = yahtzee.showScorecard("anything");

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void backTest() {
        // Given
        Player player = new Player("Cara", 1000.0);
        Yahtzee yahtzee = new Yahtzee(player);
        String expected = allOptions();

        // When
        String actual = yahtzee.back("lalala");

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void markScoreTest() {
        // Given
        Player player = new Player("Cara", 1000.0);
        Yahtzee yahtzee = new Yahtzee(player);
        yahtzee.setInput2("full house");
        yahtzee.scoreCard.getScorecard().put("full house", 25);
        String expected = "Not a prompt";

        // When
        String actual = yahtzee.markScore();

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void markScoreTest2() {
        // Given
        Player player = new Player("Cara", 1000.0);
        Yahtzee yahtzee = new Yahtzee(player);
        yahtzee.setInput2("not a category");
        String expected = "Invalid category. Enter 'mark' to try again.";

        // When
        String actual = yahtzee.markScore();

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void markAndResetTest(){
        // Given
        Player player = new Player("Cara", 1000.0);
        Yahtzee yahtzee = new Yahtzee(player);
        yahtzee.getRolledDice().add(d2);
        yahtzee.getSavedDice().add(d2);
        yahtzee.setInput2("twos");
        String expected = "Type 'roll' to start your next turn.";
        int expectedTotalScore = 4;
        List<Dice> expectedRolledDice = new ArrayList<>();
        List<Dice> expectedSavedDice = new ArrayList<>();
        int expectedRollNumber = 0;

        // When
        String actual = yahtzee.markAndReset();
        int actualTotalScore = yahtzee.scoreCard.getTotalScore();
        List<Dice> actualRolledDice = yahtzee.getRolledDice();
        List<Dice> actualSavedDice = yahtzee.getSavedDice();
        int actualRollNumber = yahtzee.getYahtzeePlayer().getRollNumber();

        // Then
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expectedRollNumber, actualRollNumber);
        Assert.assertEquals(expectedTotalScore, actualTotalScore);
        Assert.assertEquals(expectedRolledDice,  actualRolledDice);
        Assert.assertEquals(expectedSavedDice, actualSavedDice);
    }


    @Test
    public void checkScorecardCompleteTest(){
        // Given
        Player player = new Player("Cara", 1000.0);
        Yahtzee yahtzee = new Yahtzee(player);
        yahtzee.scoreCard.getScorecard().put("aces", 1);
        yahtzee.scoreCard.getScorecard().put("twos", 1);
        yahtzee.scoreCard.getScorecard().put("threes", 1);
        yahtzee.scoreCard.getScorecard().put("fours", 1);
        yahtzee.scoreCard.getScorecard().put("fives", 1);
        yahtzee.scoreCard.getScorecard().put("sixes", 1);
        yahtzee.scoreCard.getScorecard().put("three of a kind", 1);
        yahtzee.scoreCard.getScorecard().put("four of a kind", 1);
        yahtzee.scoreCard.getScorecard().put("small straight", 1);
        yahtzee.scoreCard.getScorecard().put("large straight", 1);
        yahtzee.scoreCard.getScorecard().put("full house", 1);
        yahtzee.scoreCard.getScorecard().put("yahtzee", 1);
        yahtzee.scoreCard.getScorecard().put("chance", 1);
        yahtzee.scoreCard.getScorecard().put("total score", 1);
        yahtzee.scoreCard.getScorecard().put("upper bonus", 1);

        String expected = "Not a prompt";

        // When
        String actual = yahtzee.checkScorecardComplete();
        boolean actualFalse = yahtzee.getPlaying();

        //
        Assert.assertEquals(expected, actual);
        Assert.assertFalse(actualFalse);
    }

    @Test
    public void invalidInputCheckTest(){
        // Given
        Player player = new Player("Cara", 1000.0);
        Yahtzee yahtzee = new Yahtzee(player);

        yahtzee.setInput("rolll");
        String expected = "Invalid input.  " + allOptions();

        // When
        String actual = yahtzee.invalidInputCheck();

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void checkForBackTest(){
        // Given
        Player player = new Player("Cara", 1000.0);
        Yahtzee yahtzee = new Yahtzee(player);
        String back = "back";
        String expected = allOptions();

        // When
        String actual = yahtzee.checkForBack(back);

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkForBackTest2(){
        // Given
        Player player = new Player("Cara", 1000.0);
        Yahtzee yahtzee = new Yahtzee(player);
        String back = "not back";
        String expected = yahtzee.markScore();

        // When
        String actual = yahtzee.checkForBack(back);

        // Then
        Assert.assertEquals(expected, actual);
    }




}