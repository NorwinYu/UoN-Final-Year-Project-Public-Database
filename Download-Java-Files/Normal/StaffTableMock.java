package edu.mum.library.mock;

import java.util.Arrays;
import java.util.List;

import edu.mum.library.model.AuthorizationLevel;
import edu.mum.library.model.Staff;

public class StaffTableMock {
	private StaffTableMock() {
	};

	public static List<Staff> getStaffList() {
		Staff staff0 = new Staff("123", "123", AuthorizationLevel.ADMIN);
		Staff staff1 = new Staff("admin", "admin", AuthorizationLevel.ADMIN);
		Staff staff2 = new Staff("lib", "lib", AuthorizationLevel.LIBRARIAN);
		Staff staff3 = new Staff("both", "both", AuthorizationLevel.BOTH);

		return Arrays.asList(staff0, staff1, staff2, staff3);
	}

}
