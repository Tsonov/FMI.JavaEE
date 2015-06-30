package bg.uni_sofia.conf_manager.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import bg.uni_sofia.conf_manager.dao.LecturerDao;
import bg.uni_sofia.conf_manager.entity.LecturerModel;
import bg.uni_sofia.conf_manager.entity.UserModel;
import bg.uni_sofia.conf_manager.utils.GeneralUtils;

@ManagedBean(name = "viewLecturerProfileBean")
@ViewScoped
public class ViewLecturerProfileBean {

	@EJB
	private LecturerDao lecturerDao;

	private LecturerModel lecturer;
	private UserModel loggedUser;

	@PostConstruct
	public void init() {
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		loggedUser = GeneralUtils.getLoggedUser(req);
		if (loggedUser != null) {
			lecturer = loggedUser.getLecturer();
		}
	}

	public LecturerModel getLecturer() {
		return lecturer;
	}

	public void setLecturer(LecturerModel lecturer) {
		this.lecturer = lecturer;
	}

	public UserModel getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(UserModel loggedUser) {
		this.loggedUser = loggedUser;
	}

}
