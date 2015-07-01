package bg.uni_sofia.conf_manager.mail;

import java.util.List;

import javax.ejb.Stateless;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import bg.uni_sofia.conf_manager.dao.LectureDao;
import bg.uni_sofia.conf_manager.dao.LecturerDao;
import bg.uni_sofia.conf_manager.entity.LectureModel;
import bg.uni_sofia.conf_manager.entity.LecturerModel;
import bg.uni_sofia.conf_manager.utils.JndiHelper;
import bg.uni_sofia.conf_manager.utils.MailUtil;

@Stateless
public class OnTimeMail implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		LecturerDao lecturerDao = (LecturerDao) JndiHelper.lookupLecturerDao();

		List<LecturerModel> lecturers = lecturerDao.findAll();
		if (lecturers.size() != 0) {
			for (LecturerModel lecturer : lecturers) {
				StringBuilder msg = new StringBuilder();
				msg.append("\n");
				msg.append("Dear \n" + lecturer.getFullName() + ", \n");
				msg.append(makeMessageForLecturer(lecturer.getId()));
				msg.append("\n Best regards, \n Pandicorn Team!");

				MailUtil.sendMail(lecturer.getEmail(),
						"Information: List with approved lectures",
						msg.toString());
			}
		}

	}

	private StringBuilder makeMessageForLecturer(Long lecturerId) {
		LectureDao lectureDao = (LectureDao) JndiHelper.lookupLectureDao();

		StringBuilder msgList = new StringBuilder();
		List<LectureModel> approvedLectures = lectureDao
				.findAllApprovedLecturesForLastDay(lecturerId);
		msgList.append("This is the list with lectures for last day:\n");
		if (approvedLectures.size() != 0) {
			for (LectureModel lec : approvedLectures) {
				msgList.append("Lecture " + lec.getTitle() + ",\n synopsis: "
						+ lec.getSynopsis() + ",\n date:"
						+ lec.getConference().getStartDate() + "\n");
				msgList.append("\n");
			}
		} else {
			msgList.append("There are no approved lectures for this day!");
		}
		return msgList;
	}

}
