package io.zipcoder.casino.DiceGame.Craps;

import java.util.function.BiConsumer;

public enum CrapsActions {
    PASS_LINE_ODDS((crapsObj, doubleVal) -> crapsObj.passLineOddsBet(doubleVal)),
    FIELD((crapsObj, doubleVal) -> crapsObj.fieldBet(doubleVal)),
    HARDWAY((crapsObj, doubleVal) -> crapsObj.hardWayBet(doubleVal)),
    //PLACE((crapsObj, doubleVal) -> crapsObj.placeLineBet(doubleVal)),
    //COME_LINE((crapsObj, doubleVal) -> crapsObj.comeLineBet(doubleVal)),
    //    ROLL((crapsObj, doubleVal) -> crapsObj.roll),
    //COME_ODDS((crapsObj, doubleVal) -> crapsObj.comeLineOddsBet(doubleVal)),
    PASS_LINE((crapsObj, doubleVal) -> crapsObj.passLineBet(doubleVal));

    private final BiConsumer<Craps, Double> consumer;

    CrapsActions(BiConsumer<Craps, Double> consumer) {
        this.consumer = consumer;
    }


    public void perform(Craps crapsObject, Double doubleValue) {
        consumer.accept(crapsObject, doubleValue);
    }

}

