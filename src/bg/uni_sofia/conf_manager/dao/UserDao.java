package bg.uni_sofia.conf_manager.dao;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import bg.uni_sofia.conf_manager.entity.FileModel;
import bg.uni_sofia.conf_manager.entity.LecturerModel;
import bg.uni_sofia.conf_manager.entity.UserModel;

@Singleton
public class UserDao {

	@PersistenceContext
	private EntityManager em;

	public void addUser(UserModel user) {
		em.persist(user);
	}
	
	public void updateUser(UserModel user) {
		em.merge(user);
	}

	public List<UserModel> findAll() {
		String txtQuery = "SELECT u FROM UserModel u";
		TypedQuery<UserModel> query = em.createQuery(txtQuery, UserModel.class);
		return query.getResultList();
	}
	
	 public UserModel findById(Long id) {
			String txtQuery = "SELECT u FROM UserModel u WHERE u.id = :id";
			TypedQuery<UserModel> query = em.createQuery(txtQuery, UserModel.class);
			query.setParameter("id", id);
			return queryUser(query);
		}

	public UserModel loginUser(String username, String password) {
		String txtQuery = "SELECT u FROM UserModel u WHERE u.username = :username AND u.password=:password";
		TypedQuery<UserModel> query = em.createQuery(txtQuery, UserModel.class);
		query.setParameter("username", username);
		query.setParameter("password", password);
		return queryUser(query);
	}

	private UserModel queryUser(TypedQuery<UserModel> query) {
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public FileModel getFile(Long fileId) {
		Query q = em.createQuery("select model from FileModel model where model.id = :pid");
		q.setParameter("pid", fileId);
		
		try {
			return (FileModel) q.getSingleResult();
		} catch(NoResultException nre) {
			return null;
		}
	}
	
	public void saveFile(FileModel file) {
		em.persist(file);
	}
	
	public void updateFile(FileModel file) {
		em.merge(file);
	}
	
	public void removeFile(FileModel file) {
		em.remove(file);
	}

}
