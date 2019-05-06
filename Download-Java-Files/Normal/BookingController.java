
package com.hotel.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotel.Service.BookingService;
import com.hotel.model.Booking;
import com.hotel.model.GuestDetails;

@Controller
@RequestMapping("/")

public class BookingController {

	@Autowired
	private BookingService bookingService;

	@ModelAttribute("Booking")
	public Booking setBookingForm() {
		return new Booking();
	}

	@RequestMapping("/hotelbook")
	public ModelAndView booking() {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("Book");
		mv.addObject("result");

		return mv;
	}

	@ModelAttribute("GuestDetails")
	public GuestDetails setDetailsForm() {
		return new GuestDetails();
	}

	@RequestMapping("/addBooking")
	public ModelAndView addBooking(@ModelAttribute("booking") Booking booking) {

		bookingService.add(booking);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Guest");
		mv.addObject("result");

		return mv;
	}
}