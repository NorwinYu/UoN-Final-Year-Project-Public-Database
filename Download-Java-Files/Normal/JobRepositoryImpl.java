package com.example.codeclan.recruitment.repositories;

import com.example.codeclan.recruitment.models.Job;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class JobRepositoryImpl implements JobRepositoryCustom {

    @Autowired
    EntityManager entityManager;

    @Transactional
    public List<Job> getAllJobs(){
        List<Job> jobs = null;
        Session session = entityManager.unwrap(Session.class);

        try {
            Criteria cr = session.createCriteria(Job.class);
            jobs = cr.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return jobs;


    }
}
