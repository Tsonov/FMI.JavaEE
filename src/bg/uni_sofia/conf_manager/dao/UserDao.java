package bg.uni_sofia.conf_manager.dao;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import bg.uni_sofia.conf_manager.entity.UserModel;

@Singleton
public class UserDao {

	@PersistenceContext
	private EntityManager em;

	public void addUser(UserModel user) {
		em.persist(user);
	}

	public List<UserModel> findAll() {
		String txtQuery = "SELECT u FROM UserModel u";
		TypedQuery<UserModel> query = em.createQuery(txtQuery, UserModel.class);
		return query.getResultList();
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

}
