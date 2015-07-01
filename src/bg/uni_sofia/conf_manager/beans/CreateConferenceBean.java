package bg.uni_sofia.conf_manager.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import bg.uni_sofia.conf_manager.dao.ConferenceDao;
import bg.uni_sofia.conf_manager.entity.ConferenceModel;
import bg.uni_sofia.conf_manager.utils.MessageUtils;

@ManagedBean
@ViewScoped
public class CreateConferenceBean implements Serializable {

	private static final long serialVersionUID = -5211110208249218990L;

	@EJB
	private ConferenceDao conferenceDao;

	private Long conferenceId;
	private ConferenceModel conference;
	private String operationType;

	@PostConstruct
	public void init() {
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String opTypeFromRequest = req.getParameter("operationType");
		String conferenceIdParam = req.getParameter("conferenceId");

		if (StringUtils.isBlank(conferenceIdParam)) {
			conferenceId = (Long) req.getAttribute("conferenceId");
		} else {
			conferenceId = Long.valueOf(conferenceIdParam);
		}

		if (StringUtils.isBlank(operationType)
				&& StringUtils.isNotBlank(opTypeFromRequest)) {
			setOperationType(opTypeFromRequest);
		} else if (StringUtils.isBlank(operationType)) {
			setOperationType("CREATE");
		}

		if (conferenceId != null) {
			conference = conferenceDao.findById(conferenceId);
			setOperationType("UPDATE");
		} else {
			conference = new ConferenceModel();
		}
	}

	public CreateConferenceBean() {
	}

	public String editAction(Long entityId) {
		setOperationType("UPDATE");

		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		req.setAttribute("conferenceId", entityId);
		return "/page/createConference";
	}

	protected String getSuccessRedirect() {

		if (operationType.equals("UPDATE")) {
			MessageUtils.addFlashMessage("Conference is updated successfully!");
		} else if (operationType.equals("CREATE")) {
			MessageUtils.addFlashMessage("Conference is created successfully!");
		}

		return "/page/listAllConferences?faces-redirect=true";
	}

	private boolean validate() {
		if(conference.getEndDate().compareTo(conference.getStartDate()) < 0) {
			MessageUtils.addErrorMessage("Can't set end date before start date!");
			return false;
		}
		return true;
	}

	public String saveAction() {
		if (!validate()) {
			return null;
		} else {
			conferenceDao.addConference(conference);
			return getSuccessRedirect();
		}
	}

	public String updateAction() {
		if (!validate()) {
			return null;
		} else {
			conferenceDao.updateConference(conference);
			return getSuccessRedirect();
		}
	}

	public ConferenceModel getConference() {
		return conference;
	}

	public void setconference(ConferenceModel conference) {
		this.conference = conference;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
}
