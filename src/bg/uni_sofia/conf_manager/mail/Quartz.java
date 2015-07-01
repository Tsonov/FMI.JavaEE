package bg.uni_sofia.conf_manager.mail;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class Quartz {

	public static void runScheduler() throws SchedulerException{
		SchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler = factory.getScheduler();
		
		JobDetail job = JobBuilder.newJob(OnTimeMail.class).withIdentity("Msg").build();
		
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("MSG").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(2, 32))
						.build();
		
		scheduler.start();
		scheduler.scheduleJob(job,trigger);
		
		
	}
}
