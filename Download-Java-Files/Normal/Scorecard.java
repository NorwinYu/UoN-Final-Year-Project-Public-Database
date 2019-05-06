package io.zipcoder.casino.DiceGame.Yahtzee;

import io.zipcoder.casino.DiceGame.DiceUtils.Dice;
import java.util.*;

public class Scorecard {

    private Map<String, Integer> scorecard;

    public Scorecard(){
        scorecard = setUpScoreCard();
    }


    public TreeMap<String, Integer> setUpScoreCard() {
        TreeMap<String, Integer> scorecard = new TreeMap<>();
        for(String s : getAllCategories()){
            scorecard.put(s, null);
        }
        scorecard.put("upper bonus", null);
        scorecard.put("total score", null);

        return scorecard;
    }


    public List<String> getAllCategories() {
        List<String> allCategories = new ArrayList<>();
        allCategories.addAll(getUpperSectionCategories());
        allCategories.addAll(getLowerSectionCategories());

        return allCategories;
    }


    public String capitalizeFirstLetters(String string){
        String answer = "";
        string = string.toLowerCase();
        string = string.substring(0, 1).toUpperCase() + string.substring(1);
        String[] stringArray = string.split("");
        for(int i = 1; i < stringArray.length; i++){
            if(stringArray[i - 1].equals(" ")){
                stringArray[i] = stringArray[i].toUpperCase();
            }
            answer += stringArray[i];
        }
        answer = stringArray[0] + answer;
        return answer;
    }

    public String padString(String string) {
        String paddedString = "   " + string;
        int rightPadding = 16 - string.length();
        for (int i = 0; i < rightPadding; i++) {
            paddedString += " ";
        }
        return paddedString;
    }



    public String getScoreString(String category){
        if(scorecard.get(category) == null){
            return padString(capitalizeFirstLetters(category)) + "|\n";
        }
        else{
            return  padString(capitalizeFirstLetters(category)) + "|    "
                    + scorecard.get(category.toLowerCase()) + "\n";
        }
    }


    public  List<String> getUpperSectionCategories(){
        List<String> upperSectionCategories = new ArrayList<>();
        upperSectionCategories.add("aces");
        upperSectionCategories.add("twos");
        upperSectionCategories.add("threes");
        upperSectionCategories.add("fours");
        upperSectionCategories.add("fives");
        upperSectionCategories.add("sixes");

        return upperSectionCategories;
    }

    public List<String> getLowerSectionCategories(){
        List<String> lowerSectionCategories = new ArrayList<>();
        lowerSectionCategories.add("three of a kind");
        lowerSectionCategories.add("four of a kind");
        lowerSectionCategories.add("full house");
        lowerSectionCategories.add("small straight");
        lowerSectionCategories.add("large straight");
        lowerSectionCategories.add("yahtzee");
        lowerSectionCategories.add("chance");
        return lowerSectionCategories;
    }

    public List<String> getAllScorecardLines(){
        List<String> allScorecardLines = new ArrayList<>();
        allScorecardLines.addAll(getUpperSectionCategories());
        allScorecardLines.add("upper bonus");
        allScorecardLines.addAll(getLowerSectionCategories());
        allScorecardLines.add("total score");

        return allScorecardLines;
    }



    public boolean upperSectionScoresComplete() {

        for(String s : getUpperSectionCategories()){
            if (scorecard.get(s) == null){
                return false;
            }
        }
        return true;
    }


    public  boolean scorecardComplete() {
        Set<String> scorecardCategories = scorecard.keySet();
        for (String s : scorecardCategories){
            if (scorecard.get(s) == null){
                return false;
            }
        }
        return true;
    }


    public boolean isValidCategory(String categoryInput) {
        String input = categoryInput.toLowerCase();

        for(String s :getAllCategories()){
            if(input.equals(s)){
                return true;
            }
        }
        return false;
    }



    public Integer upperSectionBonus() {
        if (getUpperSectionTotal() >= 63) {
            return 35;
        } else {
            return 0;
        }
    }

    public Integer getUpperSectionTotal() {
        Integer upperTotal = 0;

        for(String s : getUpperSectionCategories()){
            if(scorecard.get(s) != null){
                upperTotal += scorecard.get(s);
            }
        }

        return upperTotal;
    }

    public int getLowerSectionTotal() {
        int lowerTotal = 0;

        for (String s : getLowerSectionCategories()){
            if (scorecard.get(s) != null){
                lowerTotal += scorecard.get(s);
            }
        }
        return lowerTotal;
    }

    public void markScoreCard(String category, List<Dice> dice) {
        int score = Yahtzee.getScoreForCategory(category, dice);
        scorecard.put(category.toLowerCase(), score);

        if (upperSectionScoresComplete()) {
            scorecard.put("upper bonus", upperSectionBonus());
        }
    }


    public String getScoreCardString() {
        String scoreCardString = "";
        String spacerString = "|---------------------------------|\n";
        String categoryString = "  Category         |  Score        \n";
        scoreCardString = spacerString +categoryString + spacerString;
        for(String s : getAllScorecardLines()){
            scoreCardString += getScoreString(s) + spacerString;
        }
        return scoreCardString;
    }

    public Map<String, Integer> getScorecard() {
        return scorecard;
    }

    public int getTotalScore() {
        return getLowerSectionTotal() + getUpperSectionTotal() + upperSectionBonus();
    }

}
