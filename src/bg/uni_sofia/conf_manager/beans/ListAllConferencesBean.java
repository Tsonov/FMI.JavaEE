package bg.uni_sofia.conf_manager.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import bg.uni_sofia.conf_manager.dao.ConferenceDao;
import bg.uni_sofia.conf_manager.entity.ConferenceModel;
import bg.uni_sofia.conf_manager.entity.UserModel;
import bg.uni_sofia.conf_manager.utils.GeneralUtils;
import bg.uni_sofia.conf_manager.utils.MessageUtils;

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
		for (ConferenceModel em : conferences) {
			allConferences.add(em);
		}
		
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		UserModel currentuser = GeneralUtils.getLoggedUser(req);
		if(currentuser.getLecturer() != null) {
			int conferencesNotParticipatedIn = conferenceDao.countConferencesNotAppliedFor(currentuser.getLecturer().getId());
			MessageUtils.addMessage("There are " + conferencesNotParticipatedIn + " conferences that are closing in but you haven't applied for...consider applying for them!");
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
