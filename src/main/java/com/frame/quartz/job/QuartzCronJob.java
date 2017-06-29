package com.frame.quartz.job;

import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.quartz.QuartzKey;
import com.frame.quartz.QuartzKit;

public class QuartzCronJob extends QuartzJob {
	public static final Logger logger = LoggerFactory.getLogger(QuartzCronJob.class);
	private String cron;

	public QuartzCronJob(QuartzKey quartzKey, String cron,
			Class<? extends Job> jobClass) {
		this.quartzKey = quartzKey;
		this.state = JobState.INITED;
		this.cron = cron;
		this.jobClass = jobClass;
	}

	public void start(boolean force) {
		QuartzJob quartzJob = QuartzKit.getJob(this.quartzKey);
		if (quartzJob != null) {
			if (force)
				quartzJob.stop();
			else {
				return;
			}
		}

		long id = this.quartzKey.getId();
		String name = this.quartzKey.getName();
		String group = this.quartzKey.getGroup();
		SchedulerFactory factory = QuartzKit.getSchedulerFactory();
		try {
			if (factory != null) {
				Scheduler sched = factory.getScheduler();

				JobDetail job = JobBuilder
						.newJob(this.jobClass)
						.withIdentity("job_" + name + "_" + id,
								"group_" + group + "_" + id).requestRecovery()
						.build();

				Map jobMap = job.getJobDataMap();
				jobMap.put(group + "_" + name, Long.valueOf(id));

				if ((this.params != null) && (this.params.size() > 0)) {
					jobMap.putAll(this.params);
				}

				CronTrigger trigger = (CronTrigger) TriggerBuilder
						.newTrigger()
						.withIdentity("trigger_" + name + "_" + id,
								"group_" + group + "_" + id)
						.withSchedule(
								CronScheduleBuilder.cronSchedule(this.cron))
						.build();

				this.scheduleTime = sched.scheduleJob(job, trigger);
				sched.start();
				this.state = JobState.STARTED;
				QuartzKit.addQuartzJob(this);
			}
		} catch (Exception e) {
			throw new RuntimeException("Can't start cron job.", e);
		}
	}

	public String getCron() {
		return this.cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}
}
