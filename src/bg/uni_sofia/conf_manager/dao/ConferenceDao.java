package bg.uni_sofia.conf_manager.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
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
	
	public int countProblematicConferences() {
		String txtQuery = "SELECT DISTINCT(l.conference.id) FROM LectureModel l WHERE l.approved IS NULL AND l.conference.startDate BETWEEN :today AND :upperDate";
		TypedQuery<ConferenceModel> query = em.createQuery(txtQuery, ConferenceModel.class);
		Date today = new java.util.Date();
		Calendar c = Calendar.getInstance();
		c.setTime(today);
		c.add(Calendar.DATE, 20);
		Date upperRangeDate = c.getTime();
		query.setParameter("today", today, TemporalType.DATE);
		query.setParameter("upperDate", upperRangeDate, TemporalType.DATE);
		return query.getResultList().size();
	}
	
	public int countConferencesNotAppliedFor(Long lecturerId) {
		String txtQuery = "SELECT DISTINCT(c.id) FROM conferences c WHERE EXISTS (SELECT 42 FROM lectures l WHERE l.conference_id = c.id AND l.is_approved = true AND l.lecturer_id <> ?1) AND c.start_date BETWEEN ?2 AND ?3";
		Query query = em.createNativeQuery(txtQuery);
		Date today = new java.util.Date();
		Calendar c = Calendar.getInstance();
		c.setTime(today);
		c.add(Calendar.DATE, 45);
		Date upperRangeDate = c.getTime();
		query.setParameter(1, lecturerId);
		query.setParameter(2, today, TemporalType.DATE);
		query.setParameter(3, upperRangeDate, TemporalType.DATE);
		List results =  query.getResultList();
		return results.size();
	}
	
	private ConferenceModel queryConference(TypedQuery<ConferenceModel> query) {
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
