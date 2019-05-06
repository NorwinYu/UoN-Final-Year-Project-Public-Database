package io.zipcoder.casino.DiceGame.Yahtzee;

import io.zipcoder.casino.DiceGame.DiceUtils.Dice;
import io.zipcoder.casino.Utilities.Player;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class YahtzeePlayer {
    private String name;
    private Player player;
    private int rollNumber = 0;


    public YahtzeePlayer(Player player) {
        this.name = player.getName();
        this.player = player;
    }

    public ArrayList<Dice> playerRollDice(int numberOfDice) throws TooManyRollsException{
        if (rollNumber >= 3){
            throw new TooManyRollsException();
        }
        else {
            ArrayList<Dice> rolledDice = new ArrayList<>();
            for (int i = 0; i < numberOfDice; i++) {
                Dice die = new Dice(1);
                int dieValue = die.rollDice();
                rolledDice.add(new Dice(1, dieValue));
            }
            rollNumber++;
            return rolledDice;
        }
    }


    public ArrayList<Dice> saveDice(List<Dice> rolledDice, String diceToSaveInput) {
        ArrayList<Dice> savedDice = new ArrayList<>();
        diceToSaveInput = removeDuplicateCharacters(diceToSaveInput);
        for (int i = 0; i < diceToSaveInput.length(); i++) {
            int indexOfDieToSave = Character.getNumericValue(diceToSaveInput.charAt(i)) - 1;
            savedDice.add(rolledDice.get(indexOfDieToSave));
        }
        removeSameDice(rolledDice, savedDice);

        return savedDice;
    }


    public ArrayList<Dice> returnDice(List<Dice> savedDice, String diceToReturnInput) {
        diceToReturnInput = removeDuplicateCharacters(diceToReturnInput);
        ArrayList<Dice> returnedDice = new ArrayList<>();
        for (int i = 0; i < diceToReturnInput.length(); i++){
            int indexOfDieToReturn = Character.getNumericValue(diceToReturnInput.charAt(i) - (6 - savedDice.size()));
            returnedDice.add(savedDice.get(indexOfDieToReturn));
        }
        removeSameDice(savedDice, returnedDice);
        return returnedDice;
    }


    public void removeSameDice(List<Dice> diceListToRemoveFrom, ArrayList<Dice> diceList){
       diceListToRemoveFrom.removeAll(diceList);
    }


    public String removeDuplicateCharacters(String input){

        String inputWithoutDuplicates = "";

        char[] charArray = input.toCharArray();
        Set<Character> charSet = new HashSet<>();
        for (char c : charArray) {
            charSet.add(c);
        }

        for(Character c : charSet){
            inputWithoutDuplicates = inputWithoutDuplicates + c;
        }

        return inputWithoutDuplicates;
    }

    public String getName() {
        return this.name;
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public class TooManyRollsException extends Throwable {}
}
