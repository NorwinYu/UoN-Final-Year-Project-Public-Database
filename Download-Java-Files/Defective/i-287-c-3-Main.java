package main.java.ua.lviv.iot.kitchenDevice;

import java.util.ArrayList;

import java.util.List;

import main.java.ua.lviv.iot.kitchenDevice.manager.KitchenDeviceManager;
import main.java.ua.lviv.iot.kitchenDevice.model.Blender;
import main.java.ua.lviv.iot.kitchenDevice.model.DishesType;
import main.java.ua.lviv.iot.kitchenDevice.model.ElectricOven;
import main.java.ua.lviv.iot.kitchenDevice.model.Grill;
import main.java.ua.lviv.iot.kitchenDevice.model.KitchenDevice;
import main.java.ua.lviv.iot.kitchenDevice.model.Mixer;

public class Main {
	public static void main(String[] args) {

		KitchenDeviceManager manager = new KitchenDeviceManager();

		Blender blender = new Blender(DishesType.CAKE, "Blender", 200, 500, 100, "Stationary");
		Mixer mixer = new Mixer(DishesType.CAKE, "Mixer", 400, 300, 5);
		ElectricOven oven = new ElectricOven(DishesType.All, "ElecticOven", 1000, 900, 5);
		Grill grill = new Grill(DishesType.MEAT, "Grill", 350, 300, "Trisa");

		List<KitchenDevice> listDevice = new ArrayList();
		listDevice.add(blender);
		listDevice.add(mixer);
		listDevice.add(oven);
		listDevice.add(grill);

		System.out.println(manager.findForDishesType(listDevice, DishesType.CAKE));
		System.out.println("");
		System.out.println(manager.sortByPrice(listDevice, true).toString());
		System.out.println();
		System.out.println(manager.sortByPower(listDevice, true).toString());

	}

}
