package com.cn.template.web.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cn.template.service.mail.EmailContentService;

/**
 * 被Spring各种Scheduler反射调用的Service POJO.
 * @author Libra
 *
 */
@Component
public class UserCountScanner {

	private static Logger logger = LoggerFactory.getLogger(UserCountScanner.class);
	
	@Autowired
	private EmailContentService emailContentService;
	
	private int times=0;

	public void executeBySpringCronByJava() {
		execute("spring cron job by java");
	}

	// 被Spring的Quartz MethodInvokingJobDetailFactoryBean反射执行
	public void executeByQuartzLocalJob() {
		execute("quartz local job");
	}

	// 被Spring的Scheduler namespace 反射构造成ScheduledMethodRunnable
	public void executeBySpringCronByXml() {
		execute("spring cron job by xml");
		
	}

	/** 被Spring的Scheduler namespace 反射构造成ScheduledMethodRunnable */
	public void executeBySpringTimerByXml() {
		times=times+1;
		//execute("抓取员工邮件",times);
	}

	/**
	 * 定时打印当前用户数到日志.
	 */
	private void execute(String by) {
		logger.info("There are {} user in database, printed by {}.", 12, by);
	}
	
	/**
	 * 调用员工邮件抓取的方法.
	 */
	private void execute(String by,int time) {
		try{
		logger.info("--开始--操作:{}，次数:{} ", by ,time);
		
		emailContentService.receiveEmployeeMail();
		
		logger.info("--结束--操作:{}，次数:{} ", by ,time);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
