package bg.uni_sofia.conf_manager.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LECTURES")
public class LectureModel implements Serializable {
	private static final long serialVersionUID = -7196507424390163030L;

	private Long id;
	
	private String title;
	private String synopsis;
	private ConferenceModel conference;
	

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

	@Column(name="synopsis")
	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "conference_id", nullable = false)
	public ConferenceModel getConference() {
		return conference;
	}

	public void setConference(ConferenceModel conference) {
		this.conference = conference;
		if (!this.conference.getLectures().contains(this)) {
			this.conference.getLectures().add(this);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof LectureModel)) {
			return false;
		}
		LectureModel other = (LectureModel) obj;
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