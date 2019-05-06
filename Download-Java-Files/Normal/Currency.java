/*
 * **************************************************************************************************************
 * Currency.java | Author: Shane Mccarty | Date: 2019.02.25 | Rev: 2019.04.14
 * This file is a class of the item package for the "Dungeons of UNG" text-based game.
 * **************************************************************************************************************
 */

package item;

public class Currency extends Item{
	
	//Class Variables
	int quantity;
	
	//Class Objects
	public static final Currency goldCoin = new Currency("Gold Coin", 3, 0, 0, 6, 0, false);

	public Currency(String name, int dropChance, int sellValue, int buyValue, int equippedItemSlot, int quantity,
			boolean isConsumable) {
		super(name, dropChance, sellValue, buyValue, equippedItemSlot, quantity, isConsumable);
		this.quantity = quantity;
	}
	
	//Getters and Setters
	
	@Override
	public int getQuantity() {
		return quantity;
	}
	
	@Override
	public void setQuantity(int q) {
		this.quantity = q;
	}

	
	//Other Methods
	
	public String toString() {
		return name + "(s): " + quantity;
	}
	
	

}//End Class Currency
