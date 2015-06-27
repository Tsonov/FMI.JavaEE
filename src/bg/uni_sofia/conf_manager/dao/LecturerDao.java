package bg.uni_sofia.conf_manager.dao;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import bg.uni_sofia.conf_manager.entity.LecturerModel;


@Singleton
public class LecturerDao {

    @PersistenceContext
    private EntityManager em;

    public void addUser(LecturerModel user) {
        em.persist(user);
    }
    
    public LecturerModel findById(Long id) {
		String txtQuery = "SELECT u FROM LecturerModel u WHERE u.id = :id";
		TypedQuery<LecturerModel> query = em.createQuery(txtQuery, LecturerModel.class);
		query.setParameter("id", id);
		return queryUser(query);
	}
    
    public List<LecturerModel> findAll() {
    	String txtQuery = "SELECT u FROM LecturerModel u";
        TypedQuery<LecturerModel> query = em.createQuery(txtQuery, LecturerModel.class);
        return query.getResultList();
    }

    public boolean findUserByName(String userName) {
        String txtQuery = "SELECT u FROM LecturerModel u WHERE u.username = :userName";
        TypedQuery<LecturerModel> query = em.createQuery(txtQuery, LecturerModel.class);
        query.setParameter("userName", userName);
        return queryUser(query) != null;
    }
    
    public LecturerModel loginUser(String username, String password) {
    	 String txtQuery = "SELECT u FROM LecturerModel u WHERE u.username = :userName AND u.password=:password";
         TypedQuery<LecturerModel> query = em.createQuery(txtQuery, LecturerModel.class);
         query.setParameter("userName", username);
         query.setParameter("password", password);
         return queryUser(query);
    }

    private LecturerModel queryUser(TypedQuery<LecturerModel> query) {
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
