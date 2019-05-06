package main.java.ua.lviv.iot.kitchenDevice.manager;

import java.util.List;

import main.java.ua.lviv.iot.kitchenDevice.model.DishesType;
import main.java.ua.lviv.iot.kitchenDevice.model.KitchenDevice;


public interface IKitchenDeviceManager {
	 List<KitchenDevice> findForDishesType(List<KitchenDevice> device, DishesType dishesType);
	 List<KitchenDevice> sortByPrice(List<KitchenDevice> device, boolean reverse);
	 List<KitchenDevice> sortByPower(List<KitchenDevice> device, boolean reverse);
}
