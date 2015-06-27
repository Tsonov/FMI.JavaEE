package bg.uni_sofia.conf_manager.dao;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import bg.uni_sofia.conf_manager.entity.EmployeeModel;

@Singleton
public class EmployeeDao {

	@PersistenceContext
	private EntityManager em;
	
	public void addEmployee(EmployeeModel employee) {
		em.persist(employee);
	}
	
	public void updateEmployee(EmployeeModel employee) {
		em.merge(employee);
		em.flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<EmployeeModel> findAll() {
		String txtQuery = "SELECT u FROM EmployeeModel u";
		Query q = em.createQuery(txtQuery);
		return q.getResultList();
	}
	
	public EmployeeModel findById(Long id) {
		String txtQuery = "SELECT u FROM EmployeeModel u WHERE u.id = :id";
		TypedQuery<EmployeeModel> query = em.createQuery(txtQuery, EmployeeModel.class);
		query.setParameter("id", id);
		return queryUser(query);
	}
	
	private EmployeeModel queryUser(TypedQuery<EmployeeModel> query) {
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
