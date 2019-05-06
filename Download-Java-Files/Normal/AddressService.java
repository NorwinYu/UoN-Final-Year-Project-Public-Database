package com.hotel.Service;

import java.util.List;

import com.hotel.model.Address;
import com.hotel.model.Hotel;

public interface AddressService {
	public void add(Address address);

	public void remove(String id);

	public void remove(Address address);

	public Address search(Address address);

	public Address view(String id);

	public List<Address> viewAll();
	
	public List<Hotel> searchByCity(String city);
}
