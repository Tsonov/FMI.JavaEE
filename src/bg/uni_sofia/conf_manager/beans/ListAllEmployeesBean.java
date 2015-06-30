package bg.uni_sofia.conf_manager.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import bg.uni_sofia.conf_manager.dao.EmployeeDao;
import bg.uni_sofia.conf_manager.entity.EmployeeModel;

/**
 * @author nia
 */
@ManagedBean(name = "listAllEmployeesBean")
@ViewScoped
public class ListAllEmployeesBean {

	@EJB
	private EmployeeDao employeeDao;

	private List<EmployeeModel> allEmployeesList;
	private List<EmployeeModel> filtredEmployeesList;

	@PostConstruct
	public void init() {
		List<EmployeeModel> employees = employeeDao.findAll();
		allEmployeesList = new ArrayList<EmployeeModel>();
		for (EmployeeModel em : employees) {
			allEmployeesList.add(em);
		}
	}

	public ListAllEmployeesBean() {
		// TODO Auto-generated constructor stub
	}

	public List<EmployeeModel> getAllEmployeesList() {
		return allEmployeesList;
	}

	public void setAllEmployeesList(List<EmployeeModel> allEmployeesList) {
		this.allEmployeesList = allEmployeesList;
	}

	public List<EmployeeModel> getFiltredEmployeesList() {
		return filtredEmployeesList;
	}

	public void setFiltredEmployeesList(List<EmployeeModel> filtredEmployeesList) {
		this.filtredEmployeesList = filtredEmployeesList;
	}
}
