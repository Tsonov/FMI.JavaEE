package bg.uni_sofia.conf_manager.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import bg.uni_sofia.conf_manager.entity.ConferenceModel;

@Singleton
public class ConferenceDao {
	@PersistenceContext
	private EntityManager em;

	public void addConference(ConferenceModel conference) {
		em.persist(conference);
	}
	
	public void updateConference(ConferenceModel conference) {
		em.merge(conference);
	}
	
	public void removeConference(ConferenceModel conference) {
		em.remove(conference);
	}
	
	public List<ConferenceModel> findAll() {
		String txtQuery = "SELECT l FROM ConferenceModel l";
		TypedQuery<ConferenceModel> query = em.createQuery(txtQuery, ConferenceModel.class);
		return query.getResultList();
	}
	
	public ConferenceModel findById(Long id) {
		String txtQuery = "SELECT l FROM ConferenceModel l WHERE l.id = :id";
		TypedQuery<ConferenceModel> query = em.createQuery(txtQuery, ConferenceModel.class);
		query.setParameter("id", id);
		return queryConference(query);
	}
	
	public List<ConferenceModel> findAllBefore(Date date) {
		String txtQuery = "SELECT c from ConferenceModel c WHERE c.start_date <= :date";
		TypedQuery<ConferenceModel> query = em.createQuery(txtQuery, ConferenceModel.class);
		query.setParameter("date", date);
		return query.getResultList();
	}
	
	private ConferenceModel queryConference(TypedQuery<ConferenceModel> query) {
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
