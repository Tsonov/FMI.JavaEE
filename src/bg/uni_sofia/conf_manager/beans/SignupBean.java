
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
import bg.uni_sofia.conf_manager.dao.UserDao;
import bg.uni_sofia.conf_manager.entity.LecturerModel;
import bg.uni_sofia.conf_manager.entity.UserModel;
import bg.uni_sofia.conf_manager.enums.UserType;
import bg.uni_sofia.conf_manager.utils.GeneralUtils;
import bg.uni_sofia.conf_manager.utils.MessageUtils;

@ManagedBean(name="signupBean")
@ViewScoped
public class SignupBean implements Serializable {

	@EJB
    private LecturerDao lecturerDao;
	
	@EJB
	private UserDao userDAO;
		
	private static final long serialVersionUID = -6735226323733749234L;
	
	private LecturerModel lecturer;
	private String reemail;
	private String repassword;
	private String operationType;
	
	@PostConstruct
    public void init(){
		if(lecturer == null) {
			lecturer = new LecturerModel();
			
			HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
				
			String email = (String) req.getSession().getAttribute("_identityEmail");
			
			lecturer.setEmail(email);
			setReemail(email);
		}

		if(StringUtils.isBlank(operationType)) {
			setOperationType("create");
		}
    }
	
	public String saveLecturer() {
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		
		if(!lecturer.getEmail().equals(reemail)) {
			MessageUtils.addErrorMessage("E-mails mismatch");
			return null;
		}
		
		if(!lecturer.getPassword().equals(repassword)) {
			MessageUtils.addErrorMessage("Passwords mismatch");
			return null;
		}

		
		boolean existing = lecturerDao.findUserByName(lecturer.getUsername());
		if(existing) {
			MessageUtils.addErrorMessage("There is already user with this username!");
			return null;
		}
		
		
		String plainPassword = lecturer.getPassword();
		String encryptedPassword = GeneralUtils.encodeSha256Password(plainPassword);
		lecturer.setPassword(encryptedPassword);
		lecturer.setEmail(lecturer.getEmail().toLowerCase());
		
		UserModel user = new UserModel();
		user.setType(UserType.LECTURER);
		user.setLecturer(lecturer);
		user.setUsername(lecturer.getUsername());
		user.setPassword(lecturer.getPassword());
		
		userDAO.addUser(user);
		
		req.getSession().setAttribute("_loggedUser", user);
		
		return "home?faces-redirect=true";
	}	

	public LecturerModel getUser() {
		return lecturer;
	}

	public void setUser(LecturerModel user) {
		this.lecturer = user;
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
