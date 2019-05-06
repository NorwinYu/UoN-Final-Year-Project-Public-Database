package main.java.ua.lviv.iot.kitchenDevice.model;

public class Grill extends KitchenDevice {
	private String manufacturer;
	private Type type;

	public Grill() {
	}

	public Grill(DishesType dishesType, String name, int price, int power, String manufacturer) {
		super(dishesType, name, price, power);
		this.manufacturer = manufacturer;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
