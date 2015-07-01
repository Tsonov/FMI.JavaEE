package bg.uni_sofia.conf_manager.beans;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.quartz.SchedulerException;

import bg.uni_sofia.conf_manager.mail.Quartz;

@Singleton
@Startup
public class TestDataInserter {

	@PersistenceContext
	private EntityManager em;


	@PostConstruct
	public void insertTestData() {
		try {
			Quartz.runScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
