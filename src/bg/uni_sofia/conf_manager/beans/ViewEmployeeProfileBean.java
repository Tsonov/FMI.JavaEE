package bg.uni_sofia.conf_manager.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import bg.uni_sofia.conf_manager.dao.EmployeeDao;
import bg.uni_sofia.conf_manager.entity.EmployeeModel;
import bg.uni_sofia.conf_manager.entity.UserModel;
import bg.uni_sofia.conf_manager.utils.GeneralUtils;

@ManagedBean(name = "viewEmployeeProfileBean")
@ViewScoped
public class ViewEmployeeProfileBean {

	@EJB
	private EmployeeDao employeeDao;

	private EmployeeModel employee;

	@PostConstruct
	public void init() {
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		UserModel loggedUser = GeneralUtils.getLoggedUser(req);
		if (loggedUser != null) {
			employee = loggedUser.getEmployee();
		}
	}

	public EmployeeModel getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeModel employee) {
		this.employee = employee;
	}
}

