package com.hotel.Service;

import java.util.List;

import com.hotel.model.Booking;

public interface BookingHistoryService {
	public void add(Booking booking);

	public void view(Booking booking);

	public List<Booking> viewAll();

	public void search(String id);
}
