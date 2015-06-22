package bg.uni_sofia.conf_manager.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import bg.uni_sofia.conf_manager.enums.UserType;

@Entity
@XmlRootElement
@Table(name = "USERS")
public class UserModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5530386289513821703L;

	private Long id;

	private String username;
	private String password;
	private AdminModel admin;
	private LecturerModel lecturer;
	private EmployeeModel employee;

	private UserType type;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "admin_id")
	public AdminModel getAdmin() {
		return admin;
	}

	public void setAdmin(AdminModel admin) {
		this.admin = admin;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "lecturer_id")
	public LecturerModel getLecturer() {
		return lecturer;
	}

	public void setLecturer(LecturerModel lecturer) {
		this.lecturer = lecturer;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_id")
	public EmployeeModel getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeModel employee) {
		this.employee = employee;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

}
