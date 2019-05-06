package main.java.ua.lviv.iot.kitchenDevice.manager;

import java.util.Collections;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import main.java.ua.lviv.iot.kitchenDevice.model.DishesType;
import main.java.ua.lviv.iot.kitchenDevice.model.KitchenDevice;

public class KitchenDeviceManager implements IKitchenDeviceManager {
	private List<KitchenDevice> device;

	public KitchenDeviceManager(List<KitchenDevice> device) {
		this.device = device;
	}

	public KitchenDeviceManager() {
	}

	@Override
	public List<KitchenDevice> findForDishesType(List<KitchenDevice> device, DishesType dishesType) {
		List<KitchenDevice> deviceList = device;
		List<KitchenDevice> findForCakeDeviceList = deviceList.stream()
				.filter(devices -> devices.getDishesType() == DishesType.CAKE).collect(Collectors.toList());
		return findForCakeDeviceList;
	}

	@Override
	public List<KitchenDevice> sortByPrice(List<KitchenDevice> device, boolean reverse) {
		Comparator<KitchenDevice> comparator = ((KitchenDevice k1, KitchenDevice k2) -> k1.getPrice() - k2.getPrice());
		device.sort(comparator);
		if (reverse) {
			Collections.reverse(device);
		}
		return device;
	}

	@Override
	public List<KitchenDevice> sortByPower(List<KitchenDevice> device, boolean reverse) {
		Comparator<KitchenDevice> comparator = ((KitchenDevice k1, KitchenDevice k2) -> k1.getPower() - k2.getPower());
		device.sort(comparator);
		if (reverse) {
			Collections.reverse(device);
		}
		return device;
	}

	public List<KitchenDevice> getDevice() {
		return device;
	}

	public void setDevice(List<KitchenDevice> device) {
		this.device = device;
	}
}
