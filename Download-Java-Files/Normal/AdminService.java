package com.hotel.Service;

import java.util.List;

import com.hotel.model.Admin;
import com.hotel.model.User;

public interface AdminService {

	public void add(Admin admin);

	public void remove(String id);

	public void remove(Admin admin);

	public Admin view(String id);

	public List<User> viewAll();

	public void update(String id);
}
