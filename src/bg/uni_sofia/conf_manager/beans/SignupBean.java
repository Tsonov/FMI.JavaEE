
package bg.uni_sofia.conf_manager.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import bg.uni_sofia.conf_manager.dao.LecturerDao;
import bg.uni_sofia.conf_manager.entity.LecturerModel;
import bg.uni_sofia.conf_manager.utils.GeneralUtils;

@ManagedBean(name="signupBean")
@ViewScoped
public class SignupBean implements Serializable {

	@EJB
    private LecturerDao lecturerDao;
		
	private static final long serialVersionUID = -6735226323733749234L;
	
	private LecturerModel user;
	private String reemail;
	private String repassword;
	private String operationType;
	
	@PostConstruct
    public void init(){
		if(user == null) {
			user = new LecturerModel();
			
			HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
				
			String email = (String) req.getSession().getAttribute("_identityEmail");
			
			user.setEmail(email);
			setReemail(email);
		}

		if(StringUtils.isBlank(operationType)) {
			setOperationType("create");
		}
    }
	
	public String saveUser() {
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		
		if(!user.getEmail().equals(reemail)) {
//			MessageUtils.addErrorMessage("error.signup.email.mismatch");
			return null;
		}
		
		if(!user.getPassword().equals(repassword)) {
//			MessageUtils.addErrorMessage("error.signup.password.mismatch");
			return null;
		}

		
		boolean existing = lecturerDao.findUserByName(user.getUsername());
		if(existing) {
//			MessageUtils.addErrorMessage("signupForm:email", "error.signup.email.exists");
			return null;
		}
		
		
		String plainPassword = user.getPassword();
		String encryptedPassword = GeneralUtils.encodeSha256Password(plainPassword);
		user.setPassword(encryptedPassword);
		user.setEmail(user.getEmail().toLowerCase());
		
		req.getSession().setAttribute("_loggedUser", user);
		
		lecturerDao.addUser(user);
		
//		EmailUtils.sendWelcomeEmail(user.getEmail(), user.getUserNames(), getLoginUrl());
		
		return "home?faces-redirect=true";
	}	

	public LecturerModel getUser() {
		return user;
	}

	public void setUser(LecturerModel user) {
		this.user = user;
	}

	public String getReemail() {
		return reemail;
	}

	public void setReemail(String reemail) {
		this.reemail = reemail;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
}
