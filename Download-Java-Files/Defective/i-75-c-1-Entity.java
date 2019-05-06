package characters;

import navigation.Floor;

public class Entity {
	
	//Class Variables
	String name;
	int health, melee, defense;

	public Entity(String name, int health, int melee, int defense) {
		this.name = name;
		this.health = health;
		this.melee = melee;
		this.defense = defense;
		
	}
	
	//Getters and Setters
	public int[] getPosition() {
		int[] out = {0,1};
		return out;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMelee() {
		return melee;
	}

	public void setMelee(int melee) {
		this.melee = melee;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getMove() {
		return 1;
	}
		
	//Other Methods	
	public void attack(int attack) {
		
	}
}// End Class Entity
