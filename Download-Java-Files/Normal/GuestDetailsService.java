package com.hotel.Service;

import java.util.List;

import com.hotel.model.Booking;
import com.hotel.model.GuestDetails;

public interface GuestDetailsService {
	public void add();

	public void remove();

	public List<GuestDetails> viewAll(Booking booking);

	public void edit(Booking booking, GuestDetails guestDetails);

}
