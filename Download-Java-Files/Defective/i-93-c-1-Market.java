package edu.gatech.cs2340.spacetraders.model;


import android.util.Log;

import java.util.HashMap;

import edu.gatech.cs2340.spacetraders.R;

public class Market {
    private HashMap<TradeGood, Integer> marketGoods = new HashMap<>();
    private Condition thisCondition;
    private Resources thisResource;
    private TechLevel techLevel;
    private  TradeGood[] goods = {
            new TradeGood("Water", 0, 0, 2, 3, 4, Condition.DROUGHT, Resources.LOTSOFWATER, Resources.DESERT, 30, 50, 30 ),
            new TradeGood("Furs", 0, 0, 0, 10, 10, Condition.COLD, Resources.RICHFAUNA, Resources.LIFELESS, 230, 280, 250),
            new TradeGood("Food", 1, 0, 1, 5, 5, Condition.CROPFAIL, Resources.RICHSOIL, Resources.POORSOIL, 90, 160, 100),
            new TradeGood("Ore", 2, 2, 3, 20, 10, Condition.WAR, Resources.MINERALRICH, Resources.MINERALPOOR, 350, 420, 350),
            new TradeGood("Games", 3, 1, 6, -10, 5, Condition.BOREDOM, Resources.ARTISTIC, null, 160, 270, 250),
            new TradeGood("Firearms", 3, 1, 5, -75, 100, Condition.WAR, Resources.WARLIKE, null, 600, 1100, 1250),
            new TradeGood("Medicine", 4, 1, 6, -20, 10, Condition.PLAGUE, Resources.LOTSOFHERBS, null, 400, 700, 650),
            new TradeGood("Machines", 4, 3, 5, -30, 5, Condition.LACKOOFWORKERS, null, null, 600, 800, 900),
            new TradeGood("Narcotics", 5, 0, 5, -125, 150, Condition.BOREDOM, Resources.WEIRDMUSHROOMS, null, 2000, 3000, 3500),
            new TradeGood("Robots", 6, 4, 7, -150, 100, Condition.LACKOOFWORKERS, null, null, 3500, 5000, 5000)
    };
    //setup the market's items
    public Market(Condition cond, Resources res, TechLevel tech) {
        thisCondition = cond;
        thisResource = res;
        techLevel = tech;
        for (int i = 0; i < goods.length; i++) {
            TradeGood good = goods[i];

            //planet is too weak to produce the product. Player cannot buy it, planet market doesn't produce.
            if (tech.getValue() < good.getMtlp()) {
                continue;
            }

            //generates amount of this product in the market - between 1 to 50
            int amountProduced = (int) (Math.random() * 50 + 1);
            if (good.getTtp() == tech.getValue()) {
                amountProduced += 20;
            }

            //economic model:
            int finalPrice = good.getBasePrice();
            finalPrice = finalPrice + good.getIpl() * (int) (tech.getValue() - good.getMtlp() + Math.random() * good.getVar() * 0.01);

            //if condition causes the price to increase astronomically
            if (good.getIe().getValue() == cond.getValue()) {
                finalPrice = finalPrice * 3;
            }

            //when CR is present, price is unusually low || when ER is present, price is unusually high
            if (good.getCr() != null && good.getCr().getValue() == res.getValue()) {
                finalPrice = finalPrice / 2;
            } else if (good.getEr() != null && good.getEr().getValue() == res.getValue()) {
                finalPrice = finalPrice * 2;
            }

            //sets the final price for the product and add it to market
            good.setFinalPrice(finalPrice);
            marketGoods.put(good, amountProduced);
        }
    }

    public void buyInPlanet(Player player, TradeGood good) {
        for (TradeGood g:marketGoods.keySet()) {
            if (g.getName().equals(good.getName())) {
                int price = g.getFinalPrice();
                if (player.getCredit() < price) {
                    Log.d("invalid action: ", "you're too poor to purchase this item.");
                    return;
                }
                if (marketGoods.get(g) <1) {
                    Log.d("invalid action: ", "This item is sold out");
                    return;
                }
                player.setCredit(player.getCredit() - price);
                marketGoods.put(g, marketGoods.get(g) - 1);
                player.moreGood(g);
                return;
            }
        }
    }
    public void sellInPlanet(Player player, TradeGood good) {
        if (techLevel.getValue() < good.getMtlu()) {
            Log.d("Invalid action: ", "The tech level of current planet is too low for you to sell the good.");
            return;
        }
        for (TradeGood g:marketGoods.keySet()) {
            if (g.getName().equals(good.getName())) {
                if (player.hasGood(good) !=null) {
                    int price = g.getFinalPrice();
                    player.setCredit(player.getCredit() + price);
                    marketGoods.put(g, marketGoods.get(g) + 1);
                    player.lessGood(good);
                    return;
                }
            }
        }

    }
    public String toString() {
        String str = "";
        for (TradeGood g:marketGoods.keySet()) {
            str += g.getName() + ": " + marketGoods.get(g) + "  ";
        }
        return str;
    }
    public HashMap<TradeGood, Integer> getMarketGoods() {
        return marketGoods;
    }
}
