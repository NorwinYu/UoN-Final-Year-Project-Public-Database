package item;

import java.util.Random;

import characters.Entity;

public class Potion extends Item {

	// Class Objects
	Random rand = new Random();
	// Potion Objects
	public static final Potion manaPotion = new Potion("Mana Potion", 5, 5, 10, 100, 5, 0, true);
	public static final Item healthPotion = new Potion("Health Potion", 5, 5, 10, 100, 5, 0, true);

	// Class Variables
	int quantity, healAmount;

	public Potion(String name, int dropChance, int sellValue, int buyValue, int healAmount,
			int equippedItemSlot, int quantity, boolean isConsumable) {
		super(name, dropChance, sellValue, buyValue, equippedItemSlot, quantity, isConsumable);
		this.healAmount = healAmount;
	}

	// Getters and Setters

	public int getHealAmount() {
		return healAmount;
	}
	
	@Override
	public int getQuantity() {
		return quantity;
	}
	
	@Override
	public void setQuantity(int q) {
		this.quantity = q;
	}

	public void setHealAmount(int healAmount) {
		this.healAmount = healAmount;
	}

	// Other Methods
	public int heal() {		
		return rand.nextInt(healAmount) + 1;
	}
	
	@Override
	public void consume(Item i, Entity e){
		
		if(i.isConsumable == true && i.getQuantity() > 0 && i.equals(healthPotion) && e.getHealth() < e.getMaxHealth()) {
			int healAmount = ((Potion)i).heal();
			i.setQuantity(i.getQuantity() - 1);
			if(healAmount > e.getMaxHealth() - e.getHealth()) {
				System.out.println("You used a health potion healing yourself for " + (e.getMaxHealth() - e.getHealth())
						+ " health points.");
				e.setHealth(e.getMaxHealth());
			}
			else {
				e.setHealth(e.getHealth() + healAmount);
				System.out.println("You used a health potion healing yourself for " + healAmount + " health points.");
			}
			System.out.println("You now have " + e.getHealth() + " health points.");
		}
		
		else if(i.isConsumable == true && i.getQuantity() > 0 && i.equals(manaPotion) && e.getMana() < e.getMaxMana()) {
			int healAmount = ((Potion)i).heal();
			i.setQuantity(i.getQuantity() - 1);
			if(healAmount > e.getMaxMana() - e.getMana()) {
				System.out.println("You used a mana potion healing yourself for " + (e.getMaxMana() - e.getMana())
						+ " mana points.");
				e.setMana(e.getMaxMana());
			}
			else {
				e.setMana(e.getMana() + healAmount);
				System.out.println("You used a mana potion healing yourself for " + healAmount + " mana points.");
			}
			System.out.println("You now have " + e.getHealth() + " mana points.");
		}
		
		else
			System.out.println("You can not use this item.");
	}
	
	public String toString() {
		return name + ": " + quantity;
	}

}// End Class Potion
