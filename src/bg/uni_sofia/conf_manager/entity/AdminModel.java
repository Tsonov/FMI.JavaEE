package bg.uni_sofia.conf_manager.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "ADMINS")
public class AdminModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5677525099212508261L;

	
	private Long id;

	private String username;
	private String password;
	
	private UserModel user;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name="password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy="admin")
	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public AdminModel(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public AdminModel() {
	}
	
	@Override
    public String toString() {
        return "Admins[ id=" + id + " ]";
    }

}
