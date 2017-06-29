package com.biz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**   
* @Title: TestJob.java 
* @Package com.biz.job 
* @Description: 测试定时任务
* @author sos
* @date 2016年5月31日 下午4:20:04 
* @version V1.0   
*/
public class TestJob implements Job{
	public static final Logger logger = LoggerFactory.getLogger(TestJob.class);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("--------------测试定时任务1---------------------");
	}
}
