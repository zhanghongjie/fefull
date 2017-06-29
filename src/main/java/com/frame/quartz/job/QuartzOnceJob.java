package com.frame.quartz.job;

import java.util.Date;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.frame.quartz.QuartzKey;
import com.frame.quartz.QuartzKit;

public class QuartzOnceJob extends QuartzJob {
	private Date startTime;

	public QuartzOnceJob(QuartzKey quartzKey, Date startTime,
			Class<? extends Job> jobClass) {
		this.quartzKey = quartzKey;
		this.startTime = startTime;
		this.jobClass = jobClass;
		this.state = JobState.INITED;
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

				Trigger trigger = TriggerBuilder
						.newTrigger()
						.withIdentity("trigger_" + name + "_" + id,
								"group_" + group + "_" + id)
						.startAt(this.startTime).build();

				this.scheduleTime = sched.scheduleJob(job, trigger);
				sched.start();
				this.state = JobState.STARTED;
				QuartzKit.addQuartzJob(this);
			}
		} catch (Exception e) {
			throw new RuntimeException("Can't start once job.", e);
		}
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
}
