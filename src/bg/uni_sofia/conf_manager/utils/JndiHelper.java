package bg.uni_sofia.conf_manager.utils;


import javax.naming.InitialContext;
import javax.naming.NamingException;

import bg.uni_sofia.conf_manager.dao.LectureDao;
import bg.uni_sofia.conf_manager.dao.LecturerDao;

public class JndiHelper {
	
	public static final String LECTURE_DAO = "java:app/JavaEE/LectureDao!bg.uni_sofia.conf_manager.dao.LectureDao";
	public static final String LECTURER_DAO = "java:app/JavaEE/LecturerDao!bg.uni_sofia.conf_manager.dao.LecturerDao";
	
	public static Object lookupLocal(String jndiBinding) {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			Object obj = ctx.lookup(jndiBinding);
			return obj;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static LectureDao lookupLectureDao(){ 
		return (LectureDao) JndiHelper.lookupLocal(LECTURE_DAO);
	}
	
	public static LecturerDao lookupLecturerDao(){ 
		return (LecturerDao) JndiHelper.lookupLocal(LECTURER_DAO);
	}
}
