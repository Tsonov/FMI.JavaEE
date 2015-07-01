package bg.uni_sofia.conf_manager.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import bg.uni_sofia.conf_manager.dao.LectureDao;
import bg.uni_sofia.conf_manager.entity.LectureModel;

@ManagedBean
@ViewScoped
public class EmployeeHomeBean {

	@EJB
	private LectureDao lectureDao;

	private List<LectureModel> unapprovedLectures;

	@PostConstruct
	public void init() {
		List<LectureModel> lec = lectureDao.findAllUnapproved();
		unapprovedLectures = new ArrayList<LectureModel>();
		for (LectureModel em : lec) {
			unapprovedLectures.add(em);
		}
	}

	public EmployeeHomeBean() {
	}

	public List<LectureModel> getUnapprovedLectures() {
		return unapprovedLectures;
	}
	
	public void approve(Long lectureId) {
		LectureModel lectureToModify = lectureDao.findById(lectureId);
		if(lectureToModify != null) {
			lectureToModify.setApproved(true);
			lectureDao.updateLecture(lectureToModify);
			this.unapprovedLectures.remove(lectureToModify);
		}
	}
	
	public void refuse(Long lectureId) {
		LectureModel lectureToModify = lectureDao.findById(lectureId);
		if(lectureToModify != null) {
			lectureToModify.setApproved(false);
			lectureDao.updateLecture(lectureToModify);
			this.unapprovedLectures.remove(lectureToModify);
		}
	}
}
