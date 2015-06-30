package bg.uni_sofia.conf_manager.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import bg.uni_sofia.conf_manager.dao.ConferenceDao;
import bg.uni_sofia.conf_manager.dao.LectureDao;
import bg.uni_sofia.conf_manager.entity.ConferenceModel;
import bg.uni_sofia.conf_manager.entity.LectureModel;
import bg.uni_sofia.conf_manager.entity.LecturerModel;
import bg.uni_sofia.conf_manager.entity.UserModel;
import bg.uni_sofia.conf_manager.utils.GeneralUtils;

@ManagedBean
@ViewScoped
public class SuggestLectureBean {

	
	@EJB
	private ConferenceDao conferenceDao;
	
	@EJB
	private LectureDao lectureDao;
	
	private ConferenceModel conference;
	private LecturerModel currentLecturer;
	private LectureModel lecture;

	@PostConstruct
	public void init() {
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String id = req.getParameter("conferenceId");
		Long conferenceId;
		if (StringUtils.isEmpty(id)) {
			conferenceId = 0L;
		} else {
			conferenceId = Long.parseLong(id);
		}
		UserModel currentUser = GeneralUtils.getLoggedUser(req);
		currentLecturer = currentUser.getLecturer();
		conference = conferenceDao.findById(conferenceId);
		lecture = new LectureModel();
	}
	
	public String saveAction() {
		lecture.setApproved(false);
		lecture.setLecturer(currentLecturer);
		lecture.setConference(conference);
		lectureDao.addLecture(lecture);

		return goBackAction();
	}
	
	public String goBackAction() {
		return "/page/conferenceDetails?faces-redirect=true&conferenceId=" + conference.getId().toString();
	}
	
	public ConferenceModel getConference() {
		return this.conference;
	}
	
	public LecturerModel getLecturer() {
		return this.currentLecturer;
	}
	
	public LectureModel getLecture() {
		return this.lecture;
	}
	
}
