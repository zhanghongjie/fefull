package com.frame.quartz;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frame.exception.BusinessException;
import com.frame.quartz.job.QuartzCronJob;
import com.frame.quartz.job.QuartzJob;
import com.frame.utils.Fun;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.IPlugin;

public class QuartzPlugin implements IPlugin {
	private Logger logger = LoggerFactory.getLogger(getClass());
	public static String dsName = "main";
	public static boolean dsAlone = false;

	private String config = "quartz.properties";

	private String jobs = "jobs.properties";

	public QuartzPlugin() {
	}

	public QuartzPlugin(String dsName) {
		dsName = dsName;
	}

	public boolean start() {
		try {
			PropKit.use(config);
			/*Properties properties = PropertiesKit.me().loadPropertyFile(
					this.config);*/

			QuartzKit.setSchedulerFactory(new StdSchedulerFactory(PropKit.getProp().getProperties()));

			Scheduler sched = QuartzKit.getSchedulerFactory().getScheduler();

			sched.start();

			startPropertiesJobs();
			return true;
		} catch (Exception e) {
			throw new RuntimeException("Can't start quartz plugin.", e);
		}
	}

	public boolean stop() {
		try {
			QuartzKit.getSchedulerFactory().getScheduler().shutdown();
			QuartzKit.setSchedulerFactory(null);
			return true;
		} catch (Exception e) {
			throw new RuntimeException("Can't stop quartz plugin.", e);
		}
	}

	public void startPropertiesJobs() {
		Properties properties = PropKit.use(jobs).getProperties();
		if (!Fun.eqNull(properties)) {
			Iterator<Entry<Object, Object>> it = properties.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Object, Object> entry = it.next();
				if (entry.getKey().toString().startsWith("job")) {
					if(Fun.eqNull(entry.getValue())){
						throw new BusinessException("QuartzPlugin-->startPropertiesJobs:entry.getValue()为空！");
					}
					String[] valArr = entry.getValue().toString().split("\\.");
					String[] keyArr = entry.getKey().toString().split("_");
					String jobClassName = valArr[valArr.length-1];
					
					//String jobClassKey = jobClassName+"."+"class";
					//String idKey = jobClassName+"Id";
					//String groupKey = jobClassName+"Group";
					String cronKey = jobClassName+"Cron";
					String enable = jobClassName+"Enable";

					if (Boolean.valueOf(properties.getProperty(enable))
							.booleanValue()) {
						Integer id = Integer.valueOf(Integer
								.parseInt(keyArr[1]));
						//String group = properties.getProperty(groupKey);

						QuartzKey quartzKey = new QuartzKey(id.intValue(),jobClassName, jobClassName);

						QuartzJob quartzJob = QuartzKit.getJob(quartzKey);
						if (quartzJob != null) {
							this.logger.info("This  job  has started,"
									+ quartzKey);
						} else {
							String jobCron = properties.getProperty(cronKey);
							Class clazz;
							try {
								clazz = Class.forName(entry.getValue().toString());
							} catch (ClassNotFoundException e) {
								throw new RuntimeException(e);
							}

							new QuartzCronJob(quartzKey, jobCron, clazz)
									.start();
						}
					}
				}
			}
		}
	}

	public String getConfig() {
		return this.config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getJobs() {
		return this.jobs;
	}

	public void setJobs(String jobs) {
		this.jobs = jobs;
	}

	public static boolean isDsAlone() {
		return dsAlone;
	}

	public static void setDsAlone(boolean dsAlone) {
		dsAlone = dsAlone;
	}
}