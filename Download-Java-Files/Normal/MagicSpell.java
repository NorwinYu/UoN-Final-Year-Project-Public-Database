package characters;

public class MagicSpell {

	//Class Variables
	String name, description;
	int manaCost;
	
	public MagicSpell(String name, int manaCost, String description){
		this.name = name;
		this.manaCost = manaCost;
		this.description = description;
	}
	
	//Getters and Setters

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getManaCost() {
		return manaCost;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}
	
	//Other Methods
	
	public void useMagic(MagicSpell spell){
		
		System.out.println(spell);
	}//End useMagic
	
	
}//End Class MagicSpell
