package com.hotel.Service.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotel.DAO.AddressDao;
import com.hotel.Service.AddressService;
import com.hotel.model.Address;
import com.hotel.model.Hotel;

@Service
@Transactional
public class AddressServiceImpl implements AddressService{

	
	@Autowired
	private AddressDao addressDao;
	
	
	
	@Override
	public void add(Address address) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Address address) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Address search(Address address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address view(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Address> viewAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public List<Hotel> searchByCity(String city) {
		// TODO Auto-generated method stub
		return addressDao.searchByCity(city);
	}

}
