package bg.uni_sofia.conf_manager.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import bg.uni_sofia.conf_manager.dao.ConferenceDao;
import bg.uni_sofia.conf_manager.entity.ConferenceModel;

@ManagedBean
@ViewScoped
public class ConferenceDetailsBean {

	
	@EJB
	private ConferenceDao conferenceDao;
	
	private ConferenceModel conference;

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
		conference = conferenceDao.findById(conferenceId);
	}
	
	public ConferenceModel getConference() {
		return this.conference;
	}
	
	
}
