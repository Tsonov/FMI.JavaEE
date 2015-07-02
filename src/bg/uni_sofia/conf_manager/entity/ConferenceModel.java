package bg.uni_sofia.conf_manager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import bg.uni_sofia.conf_manager.utils.DateUtils;

@Entity
@Table(name = "CONFERENCES")
public class ConferenceModel implements Serializable {
	
	public ConferenceModel() {
		super();
	}

	private static final long serialVersionUID = -1950507424390163030L;

	private Long id;
	
	private String title;
	private String venueAddress;
	private Date startDate;
	private Date endDate;
	//private List<LectureModel> lectures = new ArrayList<LectureModel>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Column(name="title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="venue_address", length=500)
	public String getVenueAddress() {
		return venueAddress;
	}

	public void setVenueAddress(String venueAddress) {
		this.venueAddress = venueAddress;
	}

	@Column(name="start_date", nullable=false)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name="end_date", nullable=false)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public boolean canAddLectures() {
		int months = DateUtils.getMonthsBetweenTodayAnd(getStartDate());
		return months >= 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ConferenceModel)) {
			return false;
		}
		ConferenceModel other = (ConferenceModel) obj;
		if (getId() != null) {
			if (!getId().equals(other.getId())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}
}
