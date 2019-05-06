package main.java.ua.lviv.iot.kitchenDevice.model;

public class ElectricOven extends KitchenDevice {
	private int numberOfCookingModes;
	private TypeOfControl typeOfControl;

	public ElectricOven() {
	}

	public ElectricOven(DishesType dishesType, String name, int price, int power, int numberOfCookingModes) {
		super(dishesType, name, price, power);
		this.numberOfCookingModes = numberOfCookingModes;
	}

	public String toString() {
		return super.toString() + " numberOfCookingModes " + numberOfCookingModes + "}";
	}

	public int getNumberOfCookingModes() {
		return numberOfCookingModes;
	}

	public void setNumberOfCookingModes(int numberOfCookingModes) {
		this.numberOfCookingModes = numberOfCookingModes;
	}

	public TypeOfControl getTypeOfControl() {
		return typeOfControl;
	}

	public void setTypeOfControl(TypeOfControl typeOfControl) {
		this.typeOfControl = typeOfControl;
	}

}
