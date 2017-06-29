package com.frame.quartz;

import java.util.List;

import org.quartz.SchedulerFactory;

import com.frame.quartz.job.QuartzJob;
import com.google.common.collect.Lists;

public class QuartzKit {
	private static SchedulerFactory schedulerFactory;
	private static List<QuartzJob> quartzJobs = Lists.newArrayList();

	public static QuartzJob getJob(QuartzKey quartzKey) {
		for (QuartzJob quartzJob : quartzJobs) {
			if (quartzJob.getQuartzKey().equals(quartzKey)) {
				return quartzJob;
			}
		}
		return null;
	}

	public static void stopJob(QuartzKey quartzKey) {
		for (QuartzJob quartzJob : quartzJobs)
			if (quartzJob.getQuartzKey().equals(quartzKey))
				quartzJob.stop();
	}

	public static void pauseJob(QuartzKey quartzKey) {
		for (QuartzJob quartzJob : quartzJobs)
			if (quartzJob.getQuartzKey().equals(quartzKey))
				quartzJob.pause();
	}

	public static void resumeJob(QuartzKey quartzKey) {
		for (QuartzJob quartzJob : quartzJobs)
			if (quartzJob.getQuartzKey().equals(quartzKey))
				quartzJob.resume();
	}

	public static SchedulerFactory getSchedulerFactory() {
		return schedulerFactory;
	}

	public static void setSchedulerFactory(SchedulerFactory mSchedulerFactory) {
		schedulerFactory = mSchedulerFactory;
	}

	public static List<QuartzJob> getQuartzJobs() {
		return quartzJobs;
	}

	public static void setQuartzJobs(List<QuartzJob> quartzJobs) {
		quartzJobs = quartzJobs;
	}

	public static void addQuartzJob(QuartzJob startedJob) {
		quartzJobs.add(startedJob);
	}

	public static void removeQuartzJob(QuartzJob startedJob) {
		quartzJobs.remove(startedJob);
	}
}