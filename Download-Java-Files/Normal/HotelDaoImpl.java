

package com.hotel.DAO.Implementation;

import java.util.List;

import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hotel.DAO.HotelDao;
import com.hotel.model.Hotel;


	@Repository
	public class HotelDaoImpl implements HotelDao {
		@Autowired
		private SessionFactory sessionFactory;

		public List<Hotel> search(String scity)
		 {

			return (List<Hotel>) sessionFactory.getCurrentSession().createQuery("from hotel").list();
		}
			
			
		}


	




