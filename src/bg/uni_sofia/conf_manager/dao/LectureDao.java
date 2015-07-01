package bg.uni_sofia.conf_manager.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import bg.uni_sofia.conf_manager.entity.ConferenceModel;
import bg.uni_sofia.conf_manager.entity.LectureModel;

@Singleton
public class LectureDao {
	@PersistenceContext
	private EntityManager em;

	public void addLecture(LectureModel lecture) {
		em.persist(lecture);
	}
	
	public void updateLecture(LectureModel lecture) {
		em.merge(lecture);
	}
	
	public void removeLecture(LectureModel lecture) {
		em.remove(lecture);
	}
	
	public List<LectureModel> findAll() {
		String txtQuery = "SELECT l FROM LectureModel l";
		TypedQuery<LectureModel> query = em.createQuery(txtQuery, LectureModel.class);
		return query.getResultList();
	}
	
	public List<LectureModel> findAllWithApprovedStatusOf(boolean approvedStatus) {
		String txtQuery = "SELECT l FROM LectureModel l WHERE l.approved = :status";
		TypedQuery<LectureModel> query = em.createQuery(txtQuery, LectureModel.class);
		query.setParameter("status", approvedStatus);
		return query.getResultList();
	}
	
	public List<LectureModel> findAllUnapproved() {
		String txtQuery = "SELECT l FROM LectureModel l WHERE l.approved IS NULL";
		TypedQuery<LectureModel> query = em.createQuery(txtQuery, LectureModel.class);
		return query.getResultList();
	}
	
	public List<LectureModel> findAllForConference(Long conferenceId) {
		String txtQuery = "SELECT l FROM LectureModel l WHERE l.conference.id= :confId AND l.approved = true";
		TypedQuery<LectureModel> query = em.createQuery(txtQuery, LectureModel.class);
		query.setParameter("confId", conferenceId);
		return query.getResultList();
	}
	
	public LectureModel findById(Long id) {
		String txtQuery = "SELECT l FROM LectureModel l WHERE l.id = :id";
		TypedQuery<LectureModel> query = em.createQuery(txtQuery, LectureModel.class);
		query.setParameter("id", id);
		return queryLecture(query);
	}
	
	@SuppressWarnings("unchecked")
	public List<LectureModel> findAllApprovedLecturesForLastDay(Long lecturerId) {
		String currentDate = getCurrentDate();
		
		Query query = em.createNativeQuery("SELECT * FROM LECTURES AS l WHERE l.lecturer_id = ?1 and l.is_approved = true and l.approved_date = ?2");
		
		
//		String txtQuery = "SELECT l FROM LectureModel l WHERE l.lecturer.id= :lecturerId AND l.approved = true AND l.approvedDate = :date";
//		TypedQuery<LectureModel> query = em.createQuery(txtQuery, LectureModel.class);
		query.setParameter(1, lecturerId);
		query.setParameter(2, currentDate);
		
		List<LectureModel> lectures = new ArrayList<LectureModel>();
		
		List<Object[]> rows = query.getResultList();
		if(!rows.isEmpty()) {
			
			for(Object[] row : rows) {
				LectureModel lecture = new LectureModel();
				
				Long id = null;
				if (row[0] != null) {
					id = (Long) row[0];
				}
				
				String synopsis = null;
				if (row[1] != null) {
					synopsis = row[1].toString();
				}
				String title = null;
				if (row[2] != null) {
					title = row[2].toString();
				}
				Long confId = null;
				ConferenceModel conf = null;
				if (row[3] != null) {
					confId = Long.parseLong(row[3].toString());
					Query q = em.createQuery("select model from ConferenceModel model where model.id = :id");
					q.setParameter("id", confId);
					conf = (ConferenceModel) q.getSingleResult();
				}
				
				lecture.setId(id);
				lecture.setSynopsis(synopsis);
				lecture.setTitle(title);
				lecture.setConference(conf);
				
				lectures.add(lecture);
				
			}
		}
		return lectures;
	}
	
	private LectureModel queryLecture(TypedQuery<LectureModel> query) {
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public String getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date current = cal.getTime();
		String currentStr = formatter.format(current);
		return currentStr;
	}
}
