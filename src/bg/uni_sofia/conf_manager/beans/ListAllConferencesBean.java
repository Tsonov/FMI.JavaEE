package bg.uni_sofia.conf_manager.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import bg.uni_sofia.conf_manager.dao.ConferenceDao;
import bg.uni_sofia.conf_manager.entity.ConferenceModel;

@ManagedBean(name = "ListAllConferencesBean")
@ViewScoped
public class ListAllConferencesBean {

	@EJB
	private ConferenceDao conferenceDao;

	private List<ConferenceModel> allConferences;
	private List<ConferenceModel> filteredList;

	@PostConstruct
	public void init() {
		List<ConferenceModel> conferences = conferenceDao.findAll();
		allConferences = new ArrayList<ConferenceModel>();
		for(ConferenceModel em : conferences) {
			allConferences.add(em);
		}
	}

	public ListAllConferencesBean() {
	}

	public List<ConferenceModel> getAllConferences() {
		return allConferences;
	}

	public List<ConferenceModel> getFiltredEmployeesList() {
		return filteredList;
	}

	public void ConferenceModel(List<ConferenceModel> filteredList) {
		this.filteredList = filteredList;
	}
}
