package bg.uni_sofia.conf_manager.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import bg.uni_sofia.conf_manager.dao.ConferenceDao;
import bg.uni_sofia.conf_manager.dao.LectureDao;
import bg.uni_sofia.conf_manager.entity.ConferenceModel;
import bg.uni_sofia.conf_manager.entity.LectureModel;
import bg.uni_sofia.conf_manager.entity.UserModel;
import bg.uni_sofia.conf_manager.utils.GeneralUtils;

@ManagedBean
@ViewScoped
public class ConferenceDetailsBean {

	
	@EJB
	private ConferenceDao conferenceDao;
	
	@EJB
	private LectureDao lectureDao;
	
	private ConferenceModel conference;
	private List<LectureModel> lectures;
	
	private Long conferenceId;

	@PostConstruct
	public void init() {
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String id = req.getParameter("conferenceId");
		
		if (StringUtils.isEmpty(id)) {
			conferenceId = (Long) req.getAttribute("conferenceId");
		} else {
			conferenceId = Long.parseLong(id);
		}
		conference = conferenceDao.findById(conferenceId);
		
		// Get the lectures for the current screen
		lectures = new ArrayList<LectureModel>();
		for(LectureModel lecture : lectureDao.findAllForConference(conference.getId())) {
			lectures.add(lecture);
		}
	}
	
	public ConferenceModel getConference() {
		return this.conference;
	}
	
	public String suggestLectureAction() {
		return "/page/suggestLecture?faces-redirect=true&conferenceId=" + conference.getId().toString();
	}
	
	public boolean isCurrentLecturer(String id) throws Exception {
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		UserModel currentUser = GeneralUtils.getLoggedUser(request);
		if (currentUser.getLecturer() != null) {
			return id.equals(currentUser.getLecturer().getId().toString());
		} else {
			throw new Exception("Invalid state, logged user should be a lecturer...");
		}
	}

	public List<LectureModel> getLectures() {
		return lectures;
	}
	
}
