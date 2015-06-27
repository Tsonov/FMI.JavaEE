package bg.uni_sofia.conf_manager.entity;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

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
	
	private Set<String> permissions;

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
	
	@Transient
	public boolean hasPermissions(String aPermissionCodes) {
		/**
		 * 1. Gets an "AND" group of permissions from a list of "OR" groups.
		 * 2. If this user has all permissions in the group, returns true. Else
		 * goes to the next "AND" group. 3. If no "AND" group matches the
		 * requirements, returns false.
		 */
		if (StringUtils.isBlank(aPermissionCodes)) {
			return true;
		}

		String semicolumnDelimiter = ";";
		StringTokenizer orTokenizer = new StringTokenizer(aPermissionCodes,
				semicolumnDelimiter);
		while (orTokenizer.hasMoreTokens()) {
			String andPermissions = orTokenizer.nextToken();
			String commaDelimiter = ",";
			StringTokenizer andTokenizer = new StringTokenizer(andPermissions,
					commaDelimiter);
			boolean andConditionMet = true;
			while (andTokenizer.hasMoreTokens()) {
				// strip whitespaces
				String andPermission = andTokenizer.nextToken().trim();
				andConditionMet = hasPermission(andPermission);
				if (!andConditionMet) {
					// one of the conditions is not met, skipping the remaining
					// loop iterations
					break;
				}
			}
			if (andConditionMet) {
				return true;
			}
		}
		return false;
	}

	private boolean hasPermission(String aPermissionCode) {
		if (StringUtils.isBlank(aPermissionCode)) {
			return true;
		}
		Set<String> userRoles = getPermissions();

		/*
		 * Checks if this user has a permission that matches a specific code
		 */
		if (null != userRoles) {
			Iterator<String> rolesIt = userRoles.iterator();
			while (rolesIt.hasNext()) {
				String code = rolesIt.next();
				if (code.equals(aPermissionCode)) {
					return true;
				}
			}
		}
		return false;
	}

	@Transient
	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}
}
