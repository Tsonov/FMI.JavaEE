package bg.uni_sofia.conf_manager.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import bg.uni_sofia.conf_manager.dao.LecturerDao;
import bg.uni_sofia.conf_manager.dao.UserDao;
import bg.uni_sofia.conf_manager.entity.UserModel;
import bg.uni_sofia.conf_manager.enums.UserType;
//import org.apache.log4j.Logger;
import bg.uni_sofia.conf_manager.utils.GeneralUtils;
import bg.uni_sofia.conf_manager.utils.MessageUtils;

@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -404342799112068677L;

	@EJB
	private LecturerDao lecturerDAO;

	@EJB
	private UserDao userDAO;

	private String mUsername;
	private String mPassword;
	private String mEmail;

	@PostConstruct
	public void init() {
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		req.getContentType();
	}

	public String login() {
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		if (StringUtils.isBlank(mUsername) || StringUtils.isBlank(mPassword)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Username and password are required"));
			return null;
		}

		String cryptedPassword = GeneralUtils.encodeSha256Password(mPassword);
		UserModel user = userDAO.loginUser(mUsername, cryptedPassword);

		if (user == null || user.getId() == null) {
			MessageUtils.addErrorMessage("Wrong username or password");
			return null;
		}

		Set<String> permissions = new HashSet<String>();
		req.getSession().setAttribute("_loggedUser", user);

		String returnUrl = null;
		if (user.getType().equals(UserType.ADMIN)) {
			permissions.add("PERMISSIONS_ADMIN");
			returnUrl = "adminPage?faces-redirect=true";
		} else if (user.getType().equals(UserType.EMPLOYEE)) {
			permissions.add("PERMISSIONS_EMPLOYEE");
			returnUrl = "employeeHome?faces-redirect=true";
		} else if (user.getType().equals(UserType.LECTURER)) {
			permissions.add("PERMISSIONS_LECTURER");
			returnUrl = "listAllConferences?faces-redirect=true";
		}

		user.setPermissions(permissions);

		return returnUrl;
	}

	/** log out the current user */
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.remove("_topMenu");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.remove("_loggedUser");
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();

		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(req.getContextPath() + "/logout.jsp");
			FacesContext.getCurrentInstance().responseComplete();
		} catch (IOException e) {
			throw new FacesException(e);
		}

		return null;
	}

	public boolean isAdmin() {
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		UserModel loggedUser = GeneralUtils.getLoggedUser(req);
		boolean type = false;
		if (loggedUser != null && loggedUser.getType().equals(UserType.ADMIN)) {
			type = true;
		}
		return type;
	}

	public boolean isLecturer() {
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		UserModel loggedUser = GeneralUtils.getLoggedUser(req);
		boolean type = false;
		if (loggedUser != null
				&& loggedUser.getType().equals(UserType.LECTURER)) {
			type = true;
		}
		return type;
	}

	public boolean isEmployee() {
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		UserModel loggedUser = GeneralUtils.getLoggedUser(req);
		boolean type = false;
		if (loggedUser != null
				&& loggedUser.getType().equals(UserType.EMPLOYEE)) {
			type = true;
		}
		return type;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String mPassword) {
		this.mPassword = mPassword;
	}

	public String getUsername() {
		return mUsername;
	}

	public void setUsername(String aUsername) {
		this.mUsername = aUsername;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String mEmail) {
		this.mEmail = mEmail;
	}
}