package io.zipcoder.casino.DiceGame.Yahtzee;

import java.util.function.BiFunction;

public enum YahtzeeAction {

    ROLL((yahtzeeObject, input) -> yahtzeeObject.roll(input)),
    SAVE((yahtzeeObject, input) -> yahtzeeObject.saveDice(input)),
    RETURN((yahtzeeObject, input) -> yahtzeeObject.returnDice(input)),
    SCORECARD((yahtzeeObject, input) -> yahtzeeObject.showScorecard(input)),
    MARK((yahtzeeObject, input) -> yahtzeeObject.checkForBack(input)),
    BACK((yahtzeeObject, input) -> yahtzeeObject.back(input)),
    EXIT((yahtzeeObject, input) -> yahtzeeObject.exit(input));


    private final BiFunction<Yahtzee, String, String> bifunction;

    YahtzeeAction(BiFunction<Yahtzee, String, String> bifunction) {
        this.bifunction = bifunction;
    }

    public String perform(Yahtzee yahtzeeObject, String input) {
        return bifunction.apply(yahtzeeObject, input);

    }

}
