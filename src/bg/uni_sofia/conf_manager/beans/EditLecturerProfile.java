package bg.uni_sofia.conf_manager.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import bg.uni_sofia.conf_manager.dao.LecturerDao;
import bg.uni_sofia.conf_manager.dao.UserDao;
import bg.uni_sofia.conf_manager.entity.LecturerModel;
import bg.uni_sofia.conf_manager.entity.UserModel;
import bg.uni_sofia.conf_manager.utils.GeneralUtils;
import bg.uni_sofia.conf_manager.utils.MessageUtils;

@ManagedBean(name = "editLecturerProfile")
@ViewScoped
public class EditLecturerProfile {

	@EJB
	private LecturerDao lecturerDao;
	
	@EJB
	private UserDao userDao;

	private LecturerModel lecturer;
	private UserModel loggedUser;
	private String newPassword;
	private String repassword;

	@PostConstruct
	public void init() {
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		loggedUser = GeneralUtils.getLoggedUser(req);

		if (loggedUser == null || loggedUser.getId() == null) {
			throw new FacesException("Unable to find logged user");
		}

		lecturer = loggedUser.getLecturer();
	}
	
	public String editLecturer() {
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		
		if(StringUtils.isNotBlank(newPassword)) {
			// changing user password
			
			if(!newPassword.equals(repassword)) {
				MessageUtils.addErrorMessage("Password mismatch");
				return null;
			}
			
			String newEncryptedPassword = GeneralUtils.encodeSha256Password(newPassword);
			lecturer.setPassword(newEncryptedPassword);
			
			loggedUser.setLecturer(lecturer);
			loggedUser.setPassword(newEncryptedPassword);
			userDao.updateUser(loggedUser);
			
		} else {
			userDao.updateUser(loggedUser);
		}
		
		req.getSession().setAttribute("_loggedUser", loggedUser);
		
		MessageUtils.addFlashMessage("edit_profile_success");
		
		return "/page/viewProfile?faces-redirect=true";
	}

	public LecturerModel getLecturer() {
		return lecturer;
	}

	public void setLecturer(LecturerModel lecturer) {
		this.lecturer = lecturer;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	
}
