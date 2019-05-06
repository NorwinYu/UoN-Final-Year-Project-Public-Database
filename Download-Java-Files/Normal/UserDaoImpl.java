package com.hotel.DAO.Implementation;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.CriteriaQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hotel.DAO.UserDao;
import com.hotel.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void add(User user) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(user);
	}

	public void remove(String id) {
		sessionFactory.getCurrentSession().remove(id);

	}

	public long count() {
		Session currentSession = sessionFactory.getCurrentSession();
		CriteriaBuilderImpl builder = (CriteriaBuilderImpl) currentSession.getCriteriaBuilder();
		CriteriaQueryImpl<Long> criteriaQuery = (CriteriaQueryImpl<Long>) builder.createQuery(Long.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(builder.count(root));
		Query<Long> query = currentSession.createQuery(criteriaQuery);
		long count = query.getSingleResult();
		System.out.println("Count = " + count);

		return count;
	}

	public User viewUser(String id) {
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}

	public List<User> viewAll() {

		return (List<User>) sessionFactory.getCurrentSession().createQuery("from User").list();
	}

	public void update(String id) {
		sessionFactory.getCurrentSession().update(id);
	}

	@Override
	public boolean verify(String email, String password) {
		try {
			Session currentSession = sessionFactory.getCurrentSession();
	
			 //Transaction tx = currentSession.beginTransaction();
			
			//tx.commit();
			Query<User>  query=currentSession.createQuery("from User user where user.email= '"+email+"' and user.password= '"+password+"'");
			try {
				User u=query.getSingleResult();
				if(u!=null)
			
					{
				return true;
					}
			}
			catch(NoResultException ex){
				return false;
			}
			
		} catch (HibernateException e) {
			if (sessionFactory.getCurrentSession().getTransaction() != null) {
				sessionFactory.getCurrentSession().getTransaction().rollback();
				System.out.println("User Not Registered");
			}
			return false;
		}
		
		
		return false;

	}

}
