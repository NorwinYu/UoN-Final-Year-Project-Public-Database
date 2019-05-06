package main.java.ua.lviv.iot.kitchenDevice.model;

public class Blender extends KitchenDevice {
	private String type;
	private int capacityOfGlass;
	private Attachment attachment;

	public Blender() {
	}

	public Blender(DishesType dishesType, String name, int price, int power, int capacityOfGlass, String type) {
		super(dishesType, name, price, power);
		this.capacityOfGlass = capacityOfGlass;
		this.type = type;
	}

	public String toString() {
		return super.toString() + " type " + type + " capacityOfGlass " + capacityOfGlass + "}";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCapacityOfGlass() {
		return capacityOfGlass;
	}

	public void setCapacityOfGlass(int capacityOfGlass) {
		this.capacityOfGlass = capacityOfGlass;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

}
