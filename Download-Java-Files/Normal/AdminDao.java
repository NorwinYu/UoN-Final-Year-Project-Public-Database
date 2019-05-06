package com.hotel.DAO;

import java.util.List;

import com.hotel.model.Admin;
import com.hotel.model.User;

public interface AdminDao {

	public void add(Admin admin);

	public void remove(String id);

	public void remove(Admin admin);

	public Admin view(String id);

	public List<User> viewAll();

	public void update(String id);
}
