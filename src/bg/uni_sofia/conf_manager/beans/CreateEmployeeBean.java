package bg.uni_sofia.conf_manager.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import bg.uni_sofia.conf_manager.dao.EmployeeDao;
import bg.uni_sofia.conf_manager.dao.UserDao;
import bg.uni_sofia.conf_manager.entity.EmployeeModel;
import bg.uni_sofia.conf_manager.entity.UserModel;
import bg.uni_sofia.conf_manager.enums.UserType;
import bg.uni_sofia.conf_manager.utils.GeneralUtils;
import bg.uni_sofia.conf_manager.utils.MessageUtils;

@ManagedBean(name="createEmployeeBean")
@ViewScoped
public class CreateEmployeeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5215410208249218990L;

	@EJB
	private EmployeeDao employeeDao;
	
	@EJB
	private UserDao userDao;
	
	private Long employeeId;
	private EmployeeModel employee;
	private String operationType;

	@PostConstruct
	public void init() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String opTypeFromRequest = req.getParameter("operationType");
		String personIdStr = req.getParameter("employeeId");

		if (StringUtils.isBlank(personIdStr)) {
			employeeId = (Long) req.getAttribute("employeeId");
		} else {
			employeeId = Long.valueOf(personIdStr);
		}

		if (StringUtils.isBlank(operationType) && StringUtils.isNotBlank(opTypeFromRequest)) {
			setOperationType(opTypeFromRequest);
		} else if (StringUtils.isBlank(operationType)) {
			setOperationType("CREATE");
		}

		if (employeeId != null) {
			employee = employeeDao.findById(employeeId);
			setOperationType("UPDATE");
		} else {
			employee = new EmployeeModel();
		}
	}

	public CreateEmployeeBean() {
		// TODO Auto-generated constructor stub
	}

	public String editAction(Long entityId) {
		setOperationType("UPDATE");

		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		req.setAttribute("employeeId", entityId);
		return "/page/createEmployee";
	}

	protected String getSuccessRedirect() {
		if (operationType.equals("UPDATE")) {
			MessageUtils.addFlashMessage("Employee is updated successfully!");
		} else if (operationType.equals("CREATE")) {
			MessageUtils.addFlashMessage("Employee is created successfully!");
		}

		return "/page/listAllEmployees";
	}
	
	private boolean validate() {
		// TODO
		return true;
	}
	
	public String saveAction() {
		if(!validate()) {
			return null;
		} else {
			String plainPassword = employee.getPassword();
			String encryptedPassword = GeneralUtils.encodeSha256Password(plainPassword);
			employee.setPassword(encryptedPassword);
			employee.setId(null);
			
			UserModel user = new UserModel();
			user.setType(UserType.EMPLOYEE);
			user.setEmployee(employee);
			user.setUsername(employee.getUsername());
			user.setPassword(employee.getPassword());
			
			userDao.addUser(user);
			return getSuccessRedirect();
		}
	}
	
	public String updateAction() {
		if(!validate()) {
			return null;
		} else {
			employeeDao.updateEmployee(employee);
			return getSuccessRedirect();
		}
	}

	public EmployeeModel getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeModel employee) {
		this.employee = employee;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
}
