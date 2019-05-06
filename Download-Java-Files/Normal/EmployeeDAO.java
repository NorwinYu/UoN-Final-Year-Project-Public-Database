package com.hex.leaverequest.dao;

import java.util.List;

import com.hex.leaverequest.model.Employee;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Employee> getAllEmployees() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Employee> employeeList = session.createQuery("from Employee").list();
		return employeeList;
	}

	public Employee getEmployee(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Employee employee = (Employee) session.load(Employee.class, new Integer(id));
		return employee;
	}

	public Employee addEmployee(Employee employee) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(employee);
		return employee;
	}

	public void updateEmployee(Employee employee) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(employee);
	}

	public void deleteEmployee(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Employee p = (Employee) session.load(Employee.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}
	
	public Employee getManager(int empId){
		Session session = this.sessionFactory.getCurrentSession();
		//Query query = session.createQuery("Select e.empId, e.empName, e.empPhone, e.empDept, e.empMail, e.empDOJ, e.leaveBalance, e.empManagerId"
			//	+ " From Employee e, Employee m "
				//+ " Where e.empId = m.empManagerId And m.empId = :emp_id");
		Query query = session.createQuery(" From Employee e "
					+ " Where e.empId = (select m.empManagerId from Employee m where m.empId = :emp_id)");
		query.setInteger("emp_id", empId);
		
		Employee employee = (Employee)(query.list().get(0));
		return employee;
	}
}
