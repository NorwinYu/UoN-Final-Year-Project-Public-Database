package edu.mum.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.library.mock.StaffTableMock;
import edu.mum.library.model.Staff;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private SessionManager sessionManager;

	@Override
	public boolean login(String requestId, String password) {
		for (Staff s : StaffTableMock.getStaffList()) {
			if (s.getUserId().equalsIgnoreCase(requestId) && s.getPassword().equals(password)) {
				sessionManager.setLoginUser(s);
				return true;
			}
		}
		return false;
	}

}
