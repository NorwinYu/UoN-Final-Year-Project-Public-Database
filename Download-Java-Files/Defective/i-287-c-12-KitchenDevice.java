package main.java.ua.lviv.iot.kitchenDevice.model;

public class KitchenDevice {
	
	private DishesType dishesType;
	private String name;
	private int price;
	private int power;

	public KitchenDevice() {
	}

	public KitchenDevice(DishesType dishesType, String name, int price, int power) {
		super();
		this.dishesType = dishesType;
		this.name = name;
		this.price = price;
		this.power = power;

	}

	public String toString() {
		return "KitchenDevice{" + " DeviceFor " + dishesType + "," + " Name " + name + "," + " price " + price + ","
				+ " power " + power + ",";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public DishesType getDishesType() {
		return dishesType;
	}

	public void setDishesType(DishesType dishesType) {
		this.dishesType = dishesType;
	}

}
