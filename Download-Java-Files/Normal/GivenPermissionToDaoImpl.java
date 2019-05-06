package org.orcid.persistence.dao.impl;

import java.util.List;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.orcid.persistence.dao.GivenPermissionToDao;
import org.orcid.persistence.jpa.entities.GivenPermissionByEntity;
import org.orcid.persistence.jpa.entities.GivenPermissionToEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Will Simpson
 * 
 */
@PersistenceContext(unitName = "orcid")
public class GivenPermissionToDaoImpl extends GenericDaoImpl<GivenPermissionToEntity, Long> implements GivenPermissionToDao {

    public GivenPermissionToDaoImpl() {
        super(GivenPermissionToEntity.class);
    }

    @Override
    public GivenPermissionToEntity findByGiverAndReceiverOrcid(String giverOrcid, String receiverOrcid) {
        TypedQuery<GivenPermissionToEntity> query = entityManager.createQuery("from GivenPermissionToEntity where giver_orcid = :giverOrcid and receiver_orcid = :receiverOrcid",
                GivenPermissionToEntity.class);
        query.setParameter("giverOrcid", giverOrcid);
        query.setParameter("receiverOrcid", receiverOrcid);
        List<GivenPermissionToEntity> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    @Transactional
    public void remove(String giverOrcid, String receiverOrcid) {
        Query query = entityManager.createQuery("delete from GivenPermissionToEntity g where g.giver = :giverOrcid and g.receiver.id = :receiverOrcid");
        query.setParameter("giverOrcid", giverOrcid);
        query.setParameter("receiverOrcid", receiverOrcid);
        query.executeUpdate();
    }

    @Override
    public List<GivenPermissionToEntity> findByGiver(String giverOrcid) {
        TypedQuery<GivenPermissionToEntity> query = entityManager.createQuery("from GivenPermissionToEntity where giver_orcid = :giverOrcid",
                GivenPermissionToEntity.class);
        query.setParameter("giverOrcid", giverOrcid);
        return query.getResultList();
    }

    @Override
    public List<GivenPermissionByEntity> findByReceiver(String receiverOrcid) {
        TypedQuery<GivenPermissionByEntity> query = entityManager.createQuery("from GivenPermissionByEntity where receiver_orcid = :receiverOrcid",
                GivenPermissionByEntity.class);
        query.setParameter("receiverOrcid", receiverOrcid);
        return query.getResultList();
    }

}
