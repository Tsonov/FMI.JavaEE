package bg.uni_sofia.conf_manager.dao;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
public class AdminDao {

	@PersistenceContext
	private EntityManager em;
}
