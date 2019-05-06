package com.hotel.DAO.Implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hotel.DAO.BookingDao;
import com.hotel.model.Booking;

@Repository
public class BookingDaoImpl implements BookingDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void add(Booking booking) {
		Session session= sessionFactory.getCurrentSession();
		session.persist(booking);
	}

}
