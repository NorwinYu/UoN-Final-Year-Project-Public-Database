package main.java.ua.lviv.iot.kitchenDevice.model;

public class Mixer extends KitchenDevice {
	private int numberOfSpeeds;
	private Completion completion;

	public Mixer() {
	}

	public Mixer(DishesType dishesType, String name, int price, int power, int numberOfSpeeds) {
		super(dishesType, name, price, power);
		this.numberOfSpeeds = numberOfSpeeds;

	}

	public String toString() {
		return super.toString() + " numberOfSpeeds " + numberOfSpeeds + "}";
	}

	public int getNumberOfSpeeds() {
		return numberOfSpeeds;
	}

	public void setNumberOfSpeeds(int numberOfSpeeds) {
		this.numberOfSpeeds = numberOfSpeeds;
	}

	public Completion getCompletion() {
		return completion;
	}

	public void setCompletion(Completion completion) {
		this.completion = completion;
	}

}
