package com.hotel.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotel.Service.AddressService;
import com.hotel.Service.BookingService;
import com.hotel.model.Address;
import com.hotel.model.Booking;
import com.hotel.model.Hotel;

@Controller
@RequestMapping("/")
public class HotelController {

	@Autowired
	private AddressService addressService;

	@Autowired
	private BookingService bookingService;

	@RequestMapping("/")
	public ModelAndView HomePage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		mv.addObject("result");
		return mv;
	}

	@RequestMapping("index")
	public ModelAndView HomePage3() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		mv.addObject("result");
		return mv;
	}

	@RequestMapping("*/")
	public ModelAndView HomePage2() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		mv.addObject("result");
		return mv;
	}

	@RequestMapping("/hotelSearch")
	public ModelAndView search() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Search");
		mv.addObject("result");
		return mv;
	}

	@ModelAttribute("Address")
	public Address setSearchForm() {
		return new Address();
	}

	@RequestMapping("/search-hotel")
	public ModelAndView hotel(@RequestParam("city") String city, @RequestParam("checkin") String cind,
			@RequestParam("checkout") String cotd) {
		List<Hotel> hotelList = addressService.searchByCity(city);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("SearchDisplay");
		mv.addObject("checkin",cind);
		mv.addObject("checkout",cotd);
		mv.addObject("message", "hotel search successfully.");
		mv.addObject("list", hotelList);
		return mv;
	}

	@RequestMapping("/hotDisplay")
	@ResponseBody
	public ModelAndView hotel() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("hotDisplay");
		return mv;
	}

	@ModelAttribute("Booking")
	public Booking setBookingForm() {
		return new Booking();
	}

	@RequestMapping("/book")
	public ModelAndView book(@ModelAttribute("booking") Booking booking) {
		bookingService.add(booking);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		mv.addObject("message", "hotel booked successfully.");
		return mv;
	}

	@RequestMapping("/contactus")
	public ModelAndView contactus() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Contactus");
		// mv.setViewName("Partnerwithus");
		return mv;
	}

	@RequestMapping("/aboutus")
	public ModelAndView aboutus() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Aboutus");

		return mv;
	}

	@RequestMapping("/partnerwithus")
	public ModelAndView partnerwithus() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Partnerwithus");

		return mv;
	}

	@RequestMapping("/redirct")
	public ModelAndView redirct() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("display");
		mv.addObject("message", "We will reach you soon");

		return mv;
	}
}