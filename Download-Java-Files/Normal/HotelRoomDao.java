package com.hotel.Service;

import java.util.List;

import com.hotel.model.HotelRoom;

public interface HotelRoomDao {
	public void add(HotelRoom hotelRoom);

	public void edit(String id);

	public void remove(String id);

	public HotelRoom view(String id);

	public List<HotelRoom> viewAll();

}
