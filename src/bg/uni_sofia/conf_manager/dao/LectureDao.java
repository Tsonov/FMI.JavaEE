package bg.uni_sofia.conf_manager.dao;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
	
	public LectureModel findById(Long id) {
		String txtQuery = "SELECT l FROM LectureModel l WHERE l.id = :id";
		TypedQuery<LectureModel> query = em.createQuery(txtQuery, LectureModel.class);
		query.setParameter("id", id);
		return queryLecture(query);
	}
	
	private LectureModel queryLecture(TypedQuery<LectureModel> query) {
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
