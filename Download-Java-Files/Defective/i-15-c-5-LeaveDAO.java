package com.leave.obj;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LeaveDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Leave> getAllLeaves() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Leave> leaveList = session.createQuery("from leave_history").list();
		return leaveList;
	}

	public Leave getLeave(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Leave leave = (Leave) session.load(Leave.class, new Integer(id));
		return leave;
	}

	public List<Leave> getEmployeeLeaves(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Leave> leaveList = session.createQuery("from leave_history where emp_id = "+id).list();
		return leaveList;
	}
	
	public Leave addLeave(Leave leave) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(leave);
		return leave;
	}

	public void updateLeave(Leave leave) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(leave);
	}

	public void deleteLeave(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Leave p = (Leave) session.load(Leave.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}
}
