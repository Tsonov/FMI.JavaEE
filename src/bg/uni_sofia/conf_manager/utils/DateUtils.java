package bg.uni_sofia.conf_manager.utils;


import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;

public class DateUtils {
		public static int getDaysBetweenTodayAnd(Date other) {
			LocalDate otherJoda = new LocalDate(other);
			LocalDate now = new DateTime().toLocalDate();
			return Days.daysBetween(now, otherJoda).getDays();
		}
		
		public static int getMonthsBetweenTodayAnd(Date other) {
			LocalDate otherJoda = new LocalDate(other);
			LocalDate now = new DateTime().toLocalDate();
			return Months.monthsBetween(now, otherJoda).getMonths();
		}
	
}