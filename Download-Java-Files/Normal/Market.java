package edu.gatech.cs2340.spacetraders.model;


import android.util.Log;

//import java.util.HashMap;

//import edu.gatech.cs2340.spacetraders.R;

/**
 * Market class
 */
public class Market {
    //private HashMap<TradeGood, Integer> marketGoods = new HashMap<>();
    //added final
    private final int[] marketGoodCounts;
    private final TechLevel techLevel;
    private  final TradeGood[] goods = {
            new TradeGood("Water", 0, 0, 2, 3, 4, Condition.DROUGHT,
                    Resources.LOTSOFWATER, Resources.DESERT, 30 ),
            new TradeGood("Furs", 0, 0, 0, 10, 10, Condition.COLD,
                    Resources.RICHFAUNA, Resources.LIFELESS, 250),
            new TradeGood("Food", 1, 0, 1, 5, 5, Condition.CROPFAIL,
                    Resources.RICHSOIL, Resources.POORSOIL, 100),
            new TradeGood("Ore", 2, 2, 3, 20, 10, Condition.WAR,
                    Resources.MINERALRICH, Resources.MINERALPOOR, 350),
            new TradeGood("Games", 3, 1, 6, -10, 5, Condition.BOREDOM,
                    Resources.ARTISTIC, null, 250),
            new TradeGood("Firearms", 3, 1, 5, -75, 100, Condition.WAR,
                    Resources.WARLIKE, null, 1250),
            new TradeGood("Medicine", 4, 1, 6, -20, 10, Condition.PLAGUE,
                    Resources.LOTSOFHERBS, null, 650),
            new TradeGood("Machines", 4, 3, 5, -30, 5, Condition.LACKOOFWORKERS,
                    null, null, 900),
            new TradeGood("Narcotics", 5, 0, 5, -125, 150, Condition.BOREDOM,
                    Resources.WEIRDMUSHROOMS, null, 3500),
            new TradeGood("Robots", 6, 4, 7, -150, 100, Condition.LACKOOFWORKERS,
                    null, null, 5000)
    };
    //setup the market's items

    /**
     * Market method
     * @param cond condition
     * @param res resources
     * @param tech technology
     */
    public Market(Condition cond, Resources res, TechLevel tech) {
        //Condition thisCondition;
        //thisCondition = cond;
        //Resources thisResource;
        //thisResource = res;
        techLevel = tech;
        marketGoodCounts = new int[goods.length];
        for (int i = 0; i < goods.length; i++) {
            TradeGood good = goods[i];

            //planet is too weak to produce the product. Player cannot buy it,
            // planet market doesn't produce.
            if (tech.getValue() < good.getMtlp()) {
                continue;
            }

            //generates amount of this product in the market - between 1 to 50
            int AMOUNTPRODFACTOR = 50;
            int amountProduced = (int) (Math.random() * AMOUNTPRODFACTOR + 1);
            if (good.getTtp() == tech.getValue()) {
                int AMOUNTPRODFACTOR2 = 20;
                amountProduced += AMOUNTPRODFACTOR2;
            }

            //economic model:
            int finalPrice = good.getBasePrice();
            double FINALPRICEFACTOR = 0.01;
            finalPrice = finalPrice + good.getIpl() * (int) (tech.getValue() - good.getMtlp() +
                    Math.random() * good.getVar() * FINALPRICEFACTOR);

            //if condition causes the price to increase astronomically
            if (good.getIe().getValue() == cond.getValue()) {
                finalPrice = finalPrice * 3;
            }

            //when CR is present, price is unusually low || when ER is present,
            // price is unusually high
            if (good.getCr() != null && good.getCr().getValue() == res.getValue()) {
                finalPrice = finalPrice / 2;
            } else if (good.getEr() != null && good.getEr().getValue() == res.getValue()) {
                finalPrice = finalPrice * 2;
            }

            //sets the final price for the product and add it to market
            good.setFinalPrice(finalPrice);
            marketGoodCounts[i] = amountProduced;
        }
    }

    /**
     * Buying method
     * @param player player
     * @param goodName good name
     */
    public void buyInPlanet(Player player, String goodName) {
        if (goodName == null) {
            throw new IllegalArgumentException();
        }
        int index = getIndexByName(goodName);
        if (index < 0) {
            return;
        }
        //|| index >= goods.length
        TradeGood thisGood = goods[index];
        int price = thisGood.getFinalPrice();
        if (player.getCredit() < price) {
            //Log.d("invalid action: ", "you're too poor to purchase this item.");
            return;
        }
        if (marketGoodCounts[index] <1) {
            //Log.d("invalid action: ", "This item is sold out");
            return;
        }
        marketGoodCounts[index]--;
        //Log.d("entered buy in planet", player.getName() + " bought " + goodName + ".");
        player.moreGood(thisGood);
        //return;
    }

    /**
     * Selling in planet
     * @param player player
     * @param goodName good name
     */
    public void sellInPlanet(Player player, String goodName) {
        int index = getIndexByName(goodName);
        if (index < 0 || index >= goods.length) {
            return;
        }
        TradeGood thisGood = goods[index];
        if (techLevel.getValue() < thisGood.getMtlu()) {
            Log.d("Invalid action: ", "The tech level of current planet " +
                    "is too low for you to sell the good.");
            return;
        }
        if (player.hasGood(goodName)) {
            marketGoodCounts[index]++;
            player.lessGood(thisGood);
            //return;
        } else {
            Log.d("invalid action: ", "you have nothing to sell");
        }

    }
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < goods.length; i++) {
            str.append(goods[i].getName()).append(": ").append(marketGoodCounts[i]).append("  ");
        }
        return str.toString();
    }

    /**
     * get market good numbers
     * @return integer
     */
    public int[] getMarketGoodCounts() {
        return marketGoodCounts;
    }

    /**
     * getters to get goods
     * @return trade good
     */
    public TradeGood[] getGoods() {
        return goods;
    }

    //was public
    private int getIndexByName(String n) {
        for (int i = 0; i < goods.length; i++) {
            TradeGood g = goods[i];
            if (g.getName().equals(n)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * getter for price
     * @param n string name
     * @return integer
     */
    public int getPriceOf(String n) {
        int index = getIndexByName(n);
        return goods[index].getFinalPrice();
    }


}