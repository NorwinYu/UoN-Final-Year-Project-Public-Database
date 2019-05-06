package edu.gatech.cs2340.spacetraders.model;

/**
 * trade good
 */
public class TradeGood {
    //added final
    private final String name;
    //minimum tech level to produce this resource. can't buy on planets below this level
    private final int mtlp;
    //Minimum Tech Level to Use this resource (You can't sell on planets below this level)
    private final int mtlu;
    //Tech Level which produces the most of this item
    private final int ttp;
    //Price increase per tech level
    private final int ipl;
    //variance is the maximum percentage that the price can vary above or below the base
    private final int var;
    //Radical price increase event, when this even happens on a planet, the price may
    // increase astronomically
    private final Condition ie;
    //When this condition is present, the price of this resource is unusually low
    private final Resources cr;
    //When this condition is present, the resource is expensive
    private final Resources er;

    //base price for the good
    private final int basePrice;
    private int finalPrice;

    /**
     * trade good constructor
     * @param n string
     * @param mtlp mtlp
     * @param mtlu mtlu
     * @param ttp ttp
     * @param ipl ipl
     * @param var var
     * @param ie ie
     * @param cr cr
     * @param er er
     * @param base base
     */
    public TradeGood(String n, int mtlp, int mtlu, int ttp, int ipl, int var, Condition ie,
                     Resources cr, Resources er, int base) {
        this.name = n;
        this.mtlp = mtlp;
        this.mtlu = mtlu;
        this.ttp = ttp;
        this.ipl = ipl;
        this.var = var;
        this.ie = ie;
        this.cr = cr;
        this.er = er;
        //Min price offered in space trade with random trader (not on a planet)
        //Max price offered in space trade with random trader (not on a planet)
        basePrice = base;
        finalPrice = base;
    }

    /**
     * get name
     * @return string
     */
    public String getName() {
        return name;
    }
    /**
     * get mtlp
     * @return int
     */
    public int getMtlp() {
        return mtlp;
    }
    /**
     * get mtlu
     * @return int
     */
    public int getMtlu() {
        return mtlu;
    }
    /**
     * get ttp
     * @return int
     */
    public int getTtp() {
        return ttp;
    }
    /**
     * get ipl
     * @return int
     */
    public int getIpl() {
        return ipl;
    }
    /**
     * get var
     * @return int
     */
    public int getVar() {
        return var;
    }
    /**
     * get ie
     * @return condition
     */
    public Condition getIe() {
        return ie;
    }
    /**
     * get cr
     * @return resources
     */
    public Resources getCr() {
        return cr;
    }
    /**
     * get er
     * @return resources
     */
    public Resources getEr() {
        return er;
    }
// --Commented out by Inspection START (4/7/19, 11:27 PM):
//    /**
//     * get mtl
//     * @return int
//     */
//    public int getMtl() {
//        return mtl;
//    }
// --Commented out by Inspection STOP (4/7/19, 11:27 PM)
// --Commented out by Inspection START (4/7/19, 11:27 PM):
//    /**
//     * get mth
//     * @return int
//     */
//    public int getMth() {
//        return mth;
//    }
// --Commented out by Inspection STOP (4/7/19, 11:27 PM)
    /**
     * get base price
     * @return int
     */
    public int getBasePrice() {
        return basePrice;
    }
    /**
     * get final price
     * @return int
     */
    public int getFinalPrice() {
        return finalPrice;
    }

    /**
     * set final price
     * @param p price
     */
    public void setFinalPrice(int p) {
        finalPrice = p;
    }

}
