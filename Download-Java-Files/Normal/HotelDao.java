package com.hotel.DAO;

import java.util.List;

import com.hotel.model.Hotel;

public interface HotelDao {
	

	//public void edit(String id);

	//public void search(String city);
	
	//public void remove(String id);
	
	//public Hotel view(String id);
	
	public List<Hotel> search(String scity);
	
}
