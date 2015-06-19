package bg.uni_sofia.conf_manager.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import bg.uni_sofia.conf_manager.dao.UserDao;
import bg.uni_sofia.conf_manager.entity.UserModel;
//import org.apache.log4j.Logger;


/**
 * @author gnovakov
 */
@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -404342799112068677L;

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
		req.getContentType();
		
		UserModel user = userDAO.findUserByName(mUsername);
		user.getUserName();

		return "";
	}

	/** log out the current user *//*
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(CommonConstants.SESS_PARAM_TOP_MENU);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(CommonConstants.SESS_PARAM_LOGGED_USER);
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

		// this will fix the issue with viewExpiredException and ajax comet pushing
		// if there is similar issue, try adding same logic to CustomExceptionHandler
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(req.getContextPath() + "/logout.jsp");
			FacesContext.getCurrentInstance().responseComplete();
		} catch (IOException e) {
	//		log.error(e);
			throw new FacesException(e);
		}
		return null;
	}*/

	/**
	 * for testing purposes only
	 */

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
