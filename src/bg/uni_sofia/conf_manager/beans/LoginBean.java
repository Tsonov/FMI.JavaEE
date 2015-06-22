package bg.uni_sofia.conf_manager.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import bg.uni_sofia.conf_manager.dao.LecturerDao;
import bg.uni_sofia.conf_manager.dao.UserDao;
import bg.uni_sofia.conf_manager.entity.LecturerModel;
import bg.uni_sofia.conf_manager.entity.UserModel;
import bg.uni_sofia.conf_manager.enums.UserType;
//import org.apache.log4j.Logger;
import bg.uni_sofia.conf_manager.utils.GeneralUtils;

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

	//private Logger log = Logger.getLogger(LoginBean.class);

	@PostConstruct
	public void init() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		req.getContentType();
	}

	public String login() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		if (StringUtils.isBlank(mUsername) || StringUtils.isBlank(mPassword)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Username and password are required"));
			return null;
		}
		
		String cryptedPassword = GeneralUtils.encodeSha256Password(mPassword);
		UserModel user = userDAO.loginUser(mUsername, cryptedPassword);

		if(user == null || user.getId() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Wrong username or password"));
			return null;
		}
		
		req.getSession().setAttribute("_loggedUser", user);
		
		String returnUrl = null;
		if(user.getType().equals(UserType.ADMIN)){
			returnUrl = "adminPage?faces-redirect=true";
		} else if(user.getType().equals(UserType.EMPLOYEE)) {
			returnUrl = "listAllConferences?faces-redirect=true";
		} else if(user.getType().equals(UserType.LECTURER)) {
			returnUrl = "listAllConferences?faces-redirect=true";
		}
		
		return returnUrl;
	}

	/** log out the current user */
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("_topMenu");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("_loggedUser");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		return null;
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
