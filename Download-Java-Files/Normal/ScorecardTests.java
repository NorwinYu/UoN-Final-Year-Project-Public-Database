package io.zipcoder.casino.YahtzeeTests;

import io.zipcoder.casino.DiceGame.DiceUtils.Dice;
import io.zipcoder.casino.DiceGame.Yahtzee.Scorecard;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ScorecardTests {

    @Test
    public void scorecardConstructorTest(){
        // Given
        Map<String, Integer> expected = new TreeMap<>();
        expected.put("aces", null);
        expected.put("twos", null);
        expected.put("threes", null);
        expected.put("fours", null);
        expected.put("fives", null);
        expected.put("sixes", null);
        expected.put("upper bonus", null);
        expected.put("three of a kind", null);
        expected.put("four of a kind", null);
        expected.put("full house", null);
        expected.put("small straight", null);
        expected.put("large straight", null);
        expected.put("yahtzee", null);
        expected.put("chance", null);
        expected.put("total score", null);

        // When
        Scorecard scorecard = new Scorecard();
        Map<String, Integer> actual = scorecard.getScorecard();

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void getAllCategoriesTest(){
        // Given
        Scorecard scorecard = new Scorecard();
        List<String> expected = new ArrayList<>();
        expected.add("aces");
        expected.add("twos");
        expected.add("threes");
        expected.add("fours");
        expected.add("fives");
        expected.add("sixes");
        expected.add("three of a kind");
        expected.add("four of a kind");
        expected.add("full house");
        expected.add("small straight");
        expected.add("large straight");
        expected.add("yahtzee");
        expected.add("chance");

        // When
        List<String> actual = scorecard.getAllCategories();

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void getAcesScoreStringTest(){
        //Given
        Scorecard scorecard = new Scorecard();
        String expected1 = "   Aces            |\n";
        String expected2 = "   Aces            |    3\n";

        // When
        String actual1 = scorecard.getScoreString("aces");

        scorecard.getScorecard().put("aces", 3);
        String actual2 = scorecard.getScoreString("aces");

        // Then
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void getTwosScoreStringTest(){
        // Given
        Scorecard scorecard = new Scorecard();
        String expected1 = "   Twos            |\n";
        String expected2 = "   Twos            |    8\n";

        // When
        String actual1 = scorecard.getScoreString("twos");

        scorecard.getScorecard().put("twos", 8);
        String actual2 = scorecard.getScoreString("twos");

        // Then
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void getThreesScoreStringTest() {
        // Given
        Scorecard scorecard = new Scorecard();
        String expected1 = "   Threes          |\n";
        String expected2 = "   Threes          |    12\n";

        // When
        String actual1 = scorecard.getScoreString("threes");

        scorecard.getScorecard().put("threes", 12);
        String actual2 = scorecard.getScoreString("threes");

        // Then
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void getFoursScoreStringTest() {
        // Given
        Scorecard scorecard = new Scorecard();
        String expected1 = "   Fours           |\n";
        String expected2 = "   Fours           |    8\n";

        // When
        String actual1 = scorecard.getScoreString("fours");

        scorecard.getScorecard().put("fours", 8);
        String actual2 = scorecard.getScoreString("fours");

        // Then
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void getFivesScoreStringTest() {
        // Given
        Scorecard scorecard = new Scorecard();
        String expected1 = "   Fives           |\n";
        String expected2 = "   Fives           |    0\n";

        // When
        String actual1 = scorecard.getScoreString("fives");

        scorecard.getScorecard().put("fives", 0);
        String actual2 = scorecard.getScoreString("fives");

        // Then
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void getSixesScoreStringTest() {
        // Given
        Scorecard scorecard = new Scorecard();
        String expected1 = "   Sixes           |\n";
        String expected2 = "   Sixes           |    24\n";

        // When
        String actual1 = scorecard.getScoreString("sixes");

        scorecard.getScorecard().put("sixes", 24);
        String actual2 = scorecard.getScoreString("sixes");

        // Then
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }


    @Test
    public void getUpperBonusScoreStringTest() {
        // Given
        Scorecard scorecard = new Scorecard();
        String expected1 = "   Upper Bonus     |\n";
        String expected2 = "   Upper Bonus     |    35\n";

        // When
        String actual1 = scorecard.getScoreString("upper bonus");

        scorecard.getScorecard().put("upper bonus", 35);
        String actual2 = scorecard.getScoreString("upper bonus");

        // Then
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }


    @Test
    public void getThreeOfAKindScoreStringTest() {
        // Given
        Scorecard scorecard = new Scorecard();
        String expected1 = "   Three Of A Kind |\n";
        String expected2 = "   Three Of A Kind |    22\n";

        // When
        String actual1 = scorecard.getScoreString("three of a kind");

        scorecard.getScorecard().put("three of a kind", 22);
        String actual2 = scorecard.getScoreString("three of a kind");

        // Then
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);

    }

    @Test
    public void getFourOfAKindScoreStringTest() {
        // Given
        Scorecard scorecard = new Scorecard();
        String expected1 = "   Four Of A Kind  |\n";
        String expected2 = "   Four Of A Kind  |    20\n";

        // When
        String actual1 = scorecard.getScoreString("four of a kind");

        scorecard.getScorecard().put("four of a kind", 20);
        String actual2 = scorecard.getScoreString("four of a kind");

        // Then
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void getFullHouseScoreStringTest() {
        // Given
        Scorecard scorecard = new Scorecard();
        String expected1 = "   Full House      |\n";
        String expected2 = "   Full House      |    25\n";

        // When
        String actual1 = scorecard.getScoreString("full house");

        scorecard.getScorecard().put("full house", 25);
        String actual2 = scorecard.getScoreString("full house");

        // Then
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void getSmallStraightScoreStringTest() {
        // Given
        Scorecard scorecard = new Scorecard();
        String expected1 = "   Small Straight  |\n";
        String expected2 = "   Small Straight  |    30\n";

        // When
        String actual1 = scorecard.getScoreString("small straight");

        scorecard.getScorecard().put("small straight", 30);
        String actual2 = scorecard.getScoreString("small straight");

        // Then
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void getLargeStraightScoreStringTest() {
        // Given
        Scorecard scorecard = new Scorecard();
        String expected1 = "   Large Straight  |\n";
        String expected2 = "   Large Straight  |    40\n";

        // When
        String actual1 = scorecard.getScoreString("large straight");

        scorecard.getScorecard().put("large straight", 40);
        String actual2 = scorecard.getScoreString("large straight");

        // Then
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void getYahtzeeScoreStringTest() {
        // Given
        Scorecard scorecard = new Scorecard();
        String expected1 = "   Yahtzee         |\n";
        String expected2 = "   Yahtzee         |    50\n";

        // When
        String actual1 = scorecard.getScoreString("yahtzee");

        scorecard.getScorecard().put("yahtzee", 50);
        String actual2 = scorecard.getScoreString("yahtzee");

        // Then
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }


    @Test
    public void getChanceScoreStringTest() {
        // Given
        Scorecard scorecard = new Scorecard();
        String expected1 = "   Chance          |\n";
        String expected2 = "   Chance          |    23\n";

        // When
        String actual1 = scorecard.getScoreString("chance");

        scorecard.getScorecard().put("chance", 23);
        String actual2 = scorecard.getScoreString("chance");

        // Then
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void getTotalScoreStringTest() {
        // Given
        Scorecard scorecard = new Scorecard();
        String expected1 = "   Total Score     |\n";
        String expected2 = "   Total Score     |    200\n";

        // When
        String actual1 = scorecard.getScoreString("total score");

        scorecard.getScorecard().put("total score", 200);
        String actual2 = scorecard.getScoreString("total score");

        // Then
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
    }

    @Test
    public void getUpperSectionCategoriesTest(){
        // Given
        Scorecard scorecard = new Scorecard();
        List<String> expected = scorecard.getAllCategories();
        expected.removeAll(scorecard.getLowerSectionCategories());

        // When
        List<String> actual = scorecard.getUpperSectionCategories();

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getLowerSectionCategoriesTest(){
        // Given
        Scorecard scorecard = new Scorecard();
        List<String> expected = scorecard.getAllCategories();
        expected.removeAll(scorecard.getUpperSectionCategories());

        // When
        List<String> actual = scorecard.getLowerSectionCategories();

        // Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void upperSectionScoresCompleteTest() {
        // Given
        Scorecard scorecard = new Scorecard();

        // When
        boolean expectedFalse1 = scorecard.upperSectionScoresComplete();

        scorecard.getScorecard().put("aces", 3);
        scorecard.getScorecard().put("twos", 2);
        scorecard.getScorecard().put("threes", 12);

        boolean expectedFalse2 = scorecard.upperSectionScoresComplete();

        scorecard.getScorecard().put("fours", 8);
        scorecard.getScorecard().put("fives", 20);
        scorecard.getScorecard().put("sixes", 18);

        boolean expectedTrue = scorecard.upperSectionScoresComplete();

        // Then
        Assert.assertFalse(expectedFalse1);
        Assert.assertFalse(expectedFalse2);
        Assert.assertTrue(expectedTrue);
    }


    @Test
    public void scorecardCompleteTest() {
        // Given
        Scorecard scorecard = new Scorecard();

        // When
        boolean expectedFalse = scorecard.scorecardComplete();

        scorecard.getScorecard().put("aces", 3);
        scorecard.getScorecard().put("twos", 2);
        scorecard.getScorecard().put("threes", 12);
        scorecard.getScorecard().put("fours", 8);
        scorecard.getScorecard().put("fives", 20);
        scorecard.getScorecard().put("sixes", 18);

        boolean expectedFalse2 = scorecard.scorecardComplete();

        scorecard.getScorecard().put("upper bonus", 35);
        scorecard.getScorecard().put("three of a kind", 20);
        scorecard.getScorecard().put("four of a kind", 22);
        scorecard.getScorecard().put("full house", 25);
        scorecard.getScorecard().put("small straight", 30);
        scorecard.getScorecard().put("large straight", 40);
        scorecard.getScorecard().put("yahtzee", 50);
        scorecard.getScorecard().put("chance", 23);
        scorecard.getScorecard().put("total score", 200);

        boolean expectedTrue = scorecard.scorecardComplete();

        // Then
        Assert.assertFalse(expectedFalse);
        Assert.assertFalse(expectedFalse2);
        Assert.assertTrue(expectedTrue);
    }

    @Test
    public void isValidCategoryTest() {
        // Given
        Scorecard scorecard = new Scorecard();

        String validCategory = "full house";
        String invalidCategory = "small straihgt";

        // When
        boolean actualTrue = scorecard.isValidCategory(validCategory);
        boolean actualFalse = scorecard.isValidCategory(invalidCategory);

        // Then
        Assert.assertTrue(actualTrue);
        Assert.assertFalse(actualFalse);
    }

    @Test
    public void getUpperSectionTotalTest() {
        // Given
        Scorecard scorecard1 = new Scorecard();
        Scorecard scorecard2 = new Scorecard();
        Scorecard scorecard3 = new Scorecard();
        int expected1 = 63;
        int expected2 = 62;
        int expected3 = 68;

        // When
        scorecard1.getScorecard().put("aces", 3);
        scorecard1.getScorecard().put("twos", 2);
        scorecard1.getScorecard().put("threes", 12);
        scorecard1.getScorecard().put("fours", 8);
        scorecard1.getScorecard().put("fives", 20);
        scorecard1.getScorecard().put("sixes", 18);
        int actual1 = scorecard1.getUpperSectionTotal();

        scorecard2.getScorecard().put("aces", 2);
        scorecard2.getScorecard().put("twos", 2);
        scorecard2.getScorecard().put("threes", 12);
        scorecard2.getScorecard().put("fours", 8);
        scorecard2.getScorecard().put("fives", 20);
        scorecard2.getScorecard().put("sixes", 18);
        int actual2 = scorecard2.getUpperSectionTotal();

        scorecard3.getScorecard().put("aces", 2);
        scorecard3.getScorecard().put("twos", 2);
        scorecard3.getScorecard().put("threes", 12);
        scorecard3.getScorecard().put("fours", 8);
        scorecard3.getScorecard().put("fives", 20);
        scorecard3.getScorecard().put("sixes", 24);
        int actual3 = scorecard3.getUpperSectionTotal();

        // Then
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
        Assert.assertEquals(expected3, actual3);
    }


    @Test
    public void upperSectionBonusTest() {
        // Given
        Scorecard scorecard1 = new Scorecard();
        Scorecard scorecard2 = new Scorecard();
        Scorecard scorecard3 = new Scorecard();
        int expected1 = 35;
        int expected2 = 0;
        int expected3 = 35;

        // When
        scorecard1.getScorecard().put("aces", 3);
        scorecard1.getScorecard().put("twos", 2);
        scorecard1.getScorecard().put("threes", 12);
        scorecard1.getScorecard().put("fours", 8);
        scorecard1.getScorecard().put("fives", 20);
        scorecard1.getScorecard().put("sixes", 18);
        int actual1 = scorecard1.upperSectionBonus();

        scorecard2.getScorecard().put("aces", 2);
        scorecard2.getScorecard().put("twos", 2);
        scorecard2.getScorecard().put("threes", 12);
        scorecard2.getScorecard().put("fours", 8);
        scorecard2.getScorecard().put("fives", 20);
        scorecard2.getScorecard().put("sixes", 18);
        int actual2 = scorecard2.upperSectionBonus();

        scorecard3.getScorecard().put("aces", 2);
        scorecard3.getScorecard().put("twos", 2);
        scorecard3.getScorecard().put("threes", 12);
        scorecard3.getScorecard().put("fours", 8);
        scorecard3.getScorecard().put("fives", 20);
        scorecard3.getScorecard().put("sixes", 24);
        int actual3 = scorecard3.upperSectionBonus();

        // Then
        Assert.assertEquals(expected1, actual1);
        Assert.assertEquals(expected2, actual2);
        Assert.assertEquals(expected3, actual3);
    }

    @Test
    public void getLowerSectionTotalTest() {
        // Given
        Scorecard scorecard = new Scorecard();
        int expected = 210;

        // When
        scorecard.getScorecard().put("three of a kind", 20);
        scorecard.getScorecard().put("four of a kind", 22);
        scorecard.getScorecard().put("full house", 25);
        scorecard.getScorecard().put("small straight", 30);
        scorecard.getScorecard().put("large straight", 40);
        scorecard.getScorecard().put("yahtzee", 50);
        scorecard.getScorecard().put("chance", 23);
        int actual = scorecard.getLowerSectionTotal();

        // Then
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void getTotalScoreTest() {
        // Given
        Scorecard scorecard = new Scorecard();

        scorecard.getScorecard().put("aces", 3);
        scorecard.getScorecard().put("twos", 2);
        scorecard.getScorecard().put("threes", 12);
        scorecard.getScorecard().put("fours", 8);
        scorecard.getScorecard().put("fives", 20);
        scorecard.getScorecard().put("sixes", 18);
        scorecard.getScorecard().put("upper bonus", 35);
        scorecard.getScorecard().put("three of a kind", 20);
        scorecard.getScorecard().put("four of a kind", 22);
        scorecard.getScorecard().put("full house", 25);
        scorecard.getScorecard().put("small straight", 30);
        scorecard.getScorecard().put("large straight", 40);
        scorecard.getScorecard().put("yahtzee", 50);
        scorecard.getScorecard().put("chance", 23);

        int expected = 308;

        // When
        int actual = scorecard.getTotalScore();

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void markScoreCardTest() {
        //Given
        Scorecard scorecard = new Scorecard();
        Dice d1 = new Dice(1, 1);
        Dice d2 = new Dice(1, 2);
        Dice d3 = new Dice(1, 3);
        Dice d4 = new Dice(1, 4);
        Dice d5 = new Dice(1, 5);
        Dice d6 = new Dice(1, 6);


        // Mark Aces
        List<Dice> rollAces = new ArrayList<>();
        rollAces.add(d1);
        rollAces.add(d2);
        rollAces.add(d3);
        rollAces.add(d1);
        rollAces.add(d1);
        int expectedAces = 3;

        // Mark Twos
        List<Dice> rollTwos = new ArrayList<>();
        rollTwos.add(d2);
        rollTwos.add(d2);
        rollTwos.add(d3);
        rollTwos.add(d2);
        rollTwos.add(d1);
        int expectedTwos = 6;

        // Mark Threes
        List<Dice> rollThrees = new ArrayList<>();
        rollThrees.add(d3);
        rollThrees.add(d2);
        rollThrees.add(d3);
        rollThrees.add(d3);
        rollThrees.add(d3);
        int expectedThrees = 12;

        // Mark Fours
        List<Dice> rollFours = new ArrayList<>();
        rollFours.add(d3);
        rollFours.add(d4);
        rollFours.add(d1);
        rollFours.add(d5);
        rollFours.add(d4);
        int expectedFours = 8;

        // Mark Fives
        List<Dice> rollFives = new ArrayList<>();
        rollFives.add(d1);
        rollFives.add(d5);
        rollFives.add(d5);
        rollFives.add(d5);
        rollFives.add(d5);
        int expectedFives = 20;

        // Mark Sixes
        List<Dice> rollSixes = new ArrayList<>();
        rollSixes.add(d6);
        rollSixes.add(d1);
        rollSixes.add(d4);
        rollSixes.add(d6);
        rollSixes.add(d6);
        int expectedSixes = 18;

        // Mark Three of A Kind
        List<Dice> rollThreeOfAKind = new ArrayList<>();
        rollThreeOfAKind.add(d4);
        rollThreeOfAKind.add(d4);
        rollThreeOfAKind.add(d3);
        rollThreeOfAKind.add(d6);
        rollThreeOfAKind.add(d4);
        int expectedThreeOfAKind = 21;

        // Mark Four of A Kind
        List<Dice> rollFourOfAKind = new ArrayList<>();
        rollFourOfAKind.add(d6);
        rollFourOfAKind.add(d6);
        rollFourOfAKind.add(d1);
        rollFourOfAKind.add(d6);
        rollFourOfAKind.add(d6);
        int expectedFourOfAKind = 25;

        // Mark Full House
        List<Dice> rollFullHouse = new ArrayList<>();
        rollFullHouse.add(d3);
        rollFullHouse.add(d3);
        rollFullHouse.add(d4);
        rollFullHouse.add(d3);
        rollFullHouse.add(d4);
        int expectedFullHouse = 25;

        // Mark Small Straight
        List<Dice> rollSmallStraight = new ArrayList<>();
        rollSmallStraight.add(d2);
        rollSmallStraight.add(d3);
        rollSmallStraight.add(d5);
        rollSmallStraight.add(d3);
        rollSmallStraight.add(d4);
        int expectedSmallStraight = 30;

        // Mark Large Straight
        List<Dice> rollLargeStraight = new ArrayList<>();
        rollLargeStraight.add(d6);
        rollLargeStraight.add(d5);
        rollLargeStraight.add(d4);
        rollLargeStraight.add(d2);
        rollLargeStraight.add(d3);
        int expectedLargeStraight = 40;

        // Mark Yahtzee
        List<Dice> rollYahtzee = new ArrayList<>();
        rollYahtzee.add(d1);
        rollYahtzee.add(d1);
        rollYahtzee.add(d1);
        rollYahtzee.add(d1);
        rollYahtzee.add(d1);
        int expectedYahtzee = 50;

        // Mark Chance
        List<Dice> rollChance = new ArrayList<>();
        rollChance.add(d5);
        rollChance.add(d6);
        rollChance.add(d5);
        rollChance.add(d4);
        rollChance.add(d4);
        int expectedChance = 24;


        // When
        scorecard.markScoreCard("Aces", rollAces);
        int actualAces = scorecard.getScorecard().get("aces");

        scorecard.markScoreCard("Twos", rollTwos);
        int actualTwos = scorecard.getScorecard().get("twos");

        scorecard.markScoreCard("Threes", rollThrees);
        int actualThrees = scorecard.getScorecard().get("threes");

        scorecard.markScoreCard("Fours", rollFours);
        int actualFours = scorecard.getScorecard().get("fours");

        scorecard.markScoreCard("Fives", rollFives);
        int actualFives = scorecard.getScorecard().get("fives");

        scorecard.markScoreCard("Sixes", rollSixes);
        int actualSixes = scorecard.getScorecard().get("sixes");

        scorecard.markScoreCard("three of a kind", rollThreeOfAKind);
        int actualThreeOfAKind = scorecard.getScorecard().get("three of a kind");

        scorecard.markScoreCard("four of a kind", rollFourOfAKind);
        int actualFourOfAKind = scorecard.getScorecard().get("four of a kind");

        scorecard.markScoreCard("Full House", rollFullHouse);
        int actualFullHouse = scorecard.getScorecard().get("full house");

        scorecard.markScoreCard("Small straight", rollSmallStraight);
        int actualSmallStraight = scorecard.getScorecard().get("small straight");

        scorecard.markScoreCard("Large straight", rollLargeStraight);
        int actualLargeStraight = scorecard.getScorecard().get("large straight");

        scorecard.markScoreCard("Yahtzee", rollYahtzee);
        int actualYahtzee = scorecard.getScorecard().get("yahtzee");

        scorecard.markScoreCard("Chance", rollChance);
        int actualChance = scorecard.getScorecard().get("chance");


        // Then
        Assert.assertEquals(expectedAces, actualAces);
        Assert.assertEquals(expectedTwos, actualTwos);
        Assert.assertEquals(expectedThrees, actualThrees);
        Assert.assertEquals(expectedFours, actualFours);
        Assert.assertEquals(expectedFives, actualFives);
        Assert.assertEquals(expectedSixes, actualSixes);
        Assert.assertEquals(expectedThreeOfAKind, actualThreeOfAKind);
        Assert.assertEquals(expectedFourOfAKind, actualFourOfAKind);
        Assert.assertEquals(expectedFullHouse, actualFullHouse);
        Assert.assertEquals(expectedSmallStraight, actualSmallStraight);
        Assert.assertEquals(expectedLargeStraight, actualLargeStraight);
        Assert.assertEquals(expectedYahtzee, actualYahtzee);
        Assert.assertEquals(expectedChance, actualChance);
    }


    @Test
    public void getScorecardStringTest(){
        // Given
        Scorecard scorecard = new Scorecard();

        String expected = "" +
                "|---------------------------------|\n" +
                "  Category         |  Score        \n" +
                "|---------------------------------|\n" +
                "   Aces            |\n" +
                "|---------------------------------|\n" +
                "   Twos            |\n" +
                "|---------------------------------|\n" +
                "   Threes          |\n" +
                "|---------------------------------|\n" +
                "   Fours           |\n" +
                "|---------------------------------|\n" +
                "   Fives           |\n" +
                "|---------------------------------|\n" +
                "   Sixes           |\n" +
                "|---------------------------------|\n" +
                "   Upper Bonus     |\n" +
                "|---------------------------------|\n" +
                "   Three Of A Kind |\n" +
                "|---------------------------------|\n" +
                "   Four Of A Kind  |\n" +
                "|---------------------------------|\n" +
                "   Full House      |\n" +
                "|---------------------------------|\n" +
                "   Small Straight  |\n" +
                "|---------------------------------|\n" +
                "   Large Straight  |\n" +
                "|---------------------------------|\n" +
                "   Yahtzee         |\n" +
                "|---------------------------------|\n" +
                "   Chance          |\n" +
                "|---------------------------------|\n" +
                "   Total Score     |\n" +
                "|---------------------------------|\n";

        System.out.println(expected);

        // When
        String actual = scorecard.getScoreCardString();

        // Then
        Assert.assertEquals(expected, actual);
    }
}
