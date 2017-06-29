package com.frame.quartz.job;

import java.util.Date;
import java.util.Map;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import com.frame.quartz.QuartzKey;
import com.frame.quartz.QuartzKit;
import com.google.common.collect.Maps;

public abstract class QuartzJob {
	protected QuartzKey quartzKey;
	protected JobState state;
	protected Class<? extends Job> jobClass;
	protected Date scheduleTime;
	protected Map<String, Object> params = Maps.newHashMap();
	protected static final String TRIGGER_MARK = "trigger";
	protected static final String GROUP_MARK = "group";
	protected static final String JOB_MARK = "job";
	protected static final String SEPARATOR = "_";

	public void start() {
		start(false);
	}

	public abstract void start(boolean paramBoolean);

	public void stop() {
		long id = this.quartzKey.getId();
		String name = this.quartzKey.getName();
		String group = this.quartzKey.getGroup();
		SchedulerFactory factory = QuartzKit.getSchedulerFactory();
		try {
			if (factory != null) {
				Scheduler scheduler = factory.getScheduler();
				TriggerKey triggerKey = TriggerKey.triggerKey("trigger_" + name
						+ "_" + id, "group_" + group + "_" + id);
				Trigger trigger = scheduler.getTrigger(triggerKey);
				if (trigger != null) {
					scheduler.pauseTrigger(triggerKey);
					scheduler.unscheduleJob(triggerKey);
					scheduler.deleteJob(trigger.getJobKey());
					this.state = JobState.STOPED;
					QuartzKit.removeQuartzJob(this);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Can't stop job.", e);
		}
	}

	public void pause() {
		long id = this.quartzKey.getId();
		String name = this.quartzKey.getName();
		String group = this.quartzKey.getGroup();
		SchedulerFactory factory = QuartzKit.getSchedulerFactory();
		try {
			if (factory != null) {
				Scheduler scheduler = factory.getScheduler();
				TriggerKey triggerKey = TriggerKey.triggerKey("trigger_" + name
						+ "_" + id, "group_" + group + "_" + id);
				Trigger trigger = scheduler.getTrigger(triggerKey);
				if (trigger != null) {
					scheduler.pauseTrigger(triggerKey);
					this.state = JobState.PAUSED;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Can't pause job.", e);
		}
	}

	public void resume() {
		long id = this.quartzKey.getId();
		String name = this.quartzKey.getName();
		String group = this.quartzKey.getGroup();
		SchedulerFactory factory = QuartzKit.getSchedulerFactory();
		try {
			if (factory != null) {
				Scheduler scheduler = factory.getScheduler();
				TriggerKey triggerKey = TriggerKey.triggerKey("trigger_" + name
						+ "_" + id, "group_" + group + "_" + id);
				Trigger trigger = scheduler.getTrigger(triggerKey);
				if (trigger != null) {
					scheduler.resumeJob(trigger.getJobKey());
					this.state = JobState.RESUMED;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Can't resume job.", e);
		}
	}

	public QuartzKey getQuartzKey() {
		return this.quartzKey;
	}

	public void setQuartzKey(QuartzKey quartzKey) {
		this.quartzKey = quartzKey;
	}

	public JobState getState() {
		return this.state;
	}

	public void setState(JobState state) {
		this.state = state;
	}

	public Class<? extends Job> getJobClass() {
		return this.jobClass;
	}

	public void setJobClass(Class<? extends Job> jobClass) {
		this.jobClass = jobClass;
	}

	public Map<String, Object> getParams() {
		return this.params;
	}

	public Date getScheduleTime() {
		return this.scheduleTime;
	}

	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public QuartzJob addParam(String key, Object value) {
		this.params.put(key, value);
		return this;
	}

	public QuartzJob addParams(Map<String, Object> values) {
		this.params.putAll(values);
		return this;
	}
}
