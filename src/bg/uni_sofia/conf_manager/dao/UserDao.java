package bg.uni_sofia.conf_manager.dao;

import java.security.MessageDigest;

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

    public boolean findUserByName(String userName) {
        String txtQuery = "SELECT u FROM UserModel u WHERE u.username = :userName";
        TypedQuery<UserModel> query = em.createQuery(txtQuery, UserModel.class);
        query.setParameter("userName", userName);
        return queryUser(query) != null;
    }
    
    public UserModel loginUser(String username, String password) {
    	 String txtQuery = "SELECT u FROM UserModel u WHERE u.username = :userName AND u.password=:password";
         TypedQuery<UserModel> query = em.createQuery(txtQuery, UserModel.class);
         query.setParameter("userName", username);
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

    private String getHashedPassword(String password) {
        try {
            MessageDigest mda = MessageDigest.getInstance("SHA-512");
            password = new String(mda.digest(password.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }
}
