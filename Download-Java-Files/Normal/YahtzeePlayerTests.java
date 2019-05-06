package io.zipcoder.casino.YahtzeeTests;

import io.zipcoder.casino.DiceGame.DiceUtils.Dice;
import io.zipcoder.casino.DiceGame.Yahtzee.YahtzeePlayer;
import io.zipcoder.casino.Utilities.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class YahtzeePlayerTests {

    Dice d1 = new Dice(1, 1);
    Dice d2 = new Dice(1, 2);
    Dice d3 = new Dice(1, 3);
    Dice d4 = new Dice(1, 4);
    Dice d5 = new Dice(1, 5);
    Dice d6 = new Dice(1, 6);

    @Test
    public void YahtzeePlayerConstructorTest(){
        // Given
        String expectedName = "Cara";
        Player expectedPlayer = new Player(expectedName, 1000.0);

        // When
        YahtzeePlayer yahtzeePlayer = new YahtzeePlayer(expectedPlayer);
        Player actualPlayer = yahtzeePlayer.getPlayer();
        String actualName = yahtzeePlayer.getName();

        // Then
        Assert.assertEquals(expectedName, actualName);
        Assert.assertEquals(expectedPlayer, actualPlayer);
    }


    @Test
    public void playerRollDiceTest() throws YahtzeePlayer.TooManyRollsException {
        // Given
        Player player = new Player("Cara", 1000.00);
        YahtzeePlayer yahtzeePlayer = new YahtzeePlayer(player);
        int expected = 5;

        // When
        ArrayList<Dice> rolledDice = yahtzeePlayer.playerRollDice(5);
        int actual = rolledDice.size();

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void saveDiceTest(){
        // Given
        Player player = new Player("Cara", 1000.00);
        YahtzeePlayer yahtzeePlayer = new YahtzeePlayer(player);

        ArrayList<Dice> rolledDice = new ArrayList<>();
        rolledDice.add(d3);
        rolledDice.add(d6);
        rolledDice.add(d1);
        rolledDice.add(d6);
        rolledDice.add(d2);

        String diceToSaveInput = "135";

        ArrayList<Dice> expectedSaved = new ArrayList<>();
        expectedSaved.add(d3);
        expectedSaved.add(d1);
        expectedSaved.add(d2);

        ArrayList<Dice> expectedRolled = new ArrayList<>();
        expectedRolled.add(d6);
        expectedRolled.add(d6);

        // When
        ArrayList<Dice> actualSaved = yahtzeePlayer.saveDice(rolledDice, diceToSaveInput);

        // Then
        Assert.assertEquals(expectedSaved, actualSaved);
        Assert.assertEquals(expectedRolled, rolledDice);
    }


    @Test
    public void returnDiceTest(){
        // Given
        Player player = new Player("Cara", 1000.00);
        YahtzeePlayer yahtzeePlayer = new YahtzeePlayer(player);

        ArrayList<Dice> savedDice = new ArrayList<>();
        savedDice.add(d1);
        savedDice.add(d3);
        savedDice.add(d2);
        savedDice.add(d3);
        savedDice.add(d6);

        String diceToReturnInput = "324";

        ArrayList<Dice> expectedReturned = new ArrayList<>();
        expectedReturned.add(d3);
        expectedReturned.add(d2);
        expectedReturned.add(d3);

        ArrayList<Dice> expectedSaved = new ArrayList<>();
        expectedSaved.add(d1);
        expectedSaved.add(d6);

        // When
        ArrayList<Dice> actualReturned = yahtzeePlayer.returnDice(savedDice, diceToReturnInput);

        // Then
        Assert.assertEquals(expectedReturned, actualReturned);
        Assert.assertEquals(expectedSaved, savedDice);
    }


    @Test
    public void removeSameDiceTest(){
        Player player = new Player("Cara", 1000.00);
        YahtzeePlayer yahtzeePlayer = new YahtzeePlayer(player);

        ArrayList<Dice> diceList = new ArrayList<>();
        diceList.add(d1);
        diceList.add(d2);
        diceList.add(d3);
        diceList.add(d4);

        ArrayList<Dice> diceListToRemoveFrom = new ArrayList<>();
        diceListToRemoveFrom.add(d3);
        diceListToRemoveFrom.add(d4);
        diceListToRemoveFrom.add(d5);
        diceListToRemoveFrom.add(d6);

        ArrayList<Dice> expectedRemovedFromDiceList = new ArrayList<>();
        expectedRemovedFromDiceList.add(d5);
        expectedRemovedFromDiceList.add(d6);

        // When
        yahtzeePlayer.removeSameDice(diceListToRemoveFrom, diceList);

        // Then
        Assert.assertEquals(expectedRemovedFromDiceList, diceListToRemoveFrom);
    }


    @Test
    public void removeDuplicateCharactersTest(){
        // Given
        Player player = new Player("Cara", 1000.00);
        YahtzeePlayer yahtzeePlayer = new YahtzeePlayer(player);

        String input = "1122334454435";
        String expected = "12345";

        // When
        String actual = yahtzeePlayer.removeDuplicateCharacters(input);

        // Then
        Assert.assertEquals(expected, actual);
    }


    @Test(expected = YahtzeePlayer.TooManyRollsException.class)
    public void tooManyRollsExceptionTest() throws YahtzeePlayer.TooManyRollsException{
        Player player = new Player("Cara", 1000);
        YahtzeePlayer yahtzeePlayer = new YahtzeePlayer(player);
        yahtzeePlayer.setRollNumber(3);
        yahtzeePlayer.playerRollDice(5);
    }

    @Test
    public void getRollNumberTest(){
        // Given
        Player player = new Player("Cara", 1000.00);
        YahtzeePlayer yahtzeePlayer = new YahtzeePlayer(player);

        int expected = 2;
        yahtzeePlayer.setRollNumber(expected);

        // When
        int actual = yahtzeePlayer.getRollNumber();

        // Then
        Assert.assertEquals(expected, actual);



    }


}
