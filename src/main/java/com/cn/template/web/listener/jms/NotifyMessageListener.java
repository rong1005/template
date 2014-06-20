package com.cn.template.web.listener.jms;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.cn.template.entity.Employee;
import com.cn.template.entity.weixin.NewsCategory;
import com.cn.template.entity.weixin.NewsMessage;
import com.cn.template.entity.weixin.WeixinAuthLog;
import com.cn.template.entity.weixin.WeixinUser;
import com.cn.template.service.employee.EmployeeService;
import com.cn.template.service.weixin.NewsCategoryService;
import com.cn.template.service.weixin.NewsMessageService;
import com.cn.template.service.weixin.WeixinAuthLogService;
import com.cn.template.service.weixin.WeixinUserService;
import com.cn.template.xutil.Utils;
import com.cn.template.xutil.enums.EventType;
import com.cn.template.xutil.enums.MessageCategory;
import com.cn.template.xutil.enums.Whether;
import com.cn.template.xutil.weixin.AccessTokenUtil;
import com.cn.template.xutil.weixin.WeixinConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;


/**
 * 消息的异步被动接收者.
 * 
 * 使用Spring的MessageListenerContainer侦听消息并调用本Listener进行处理.
 * 
 */
public class NotifyMessageListener implements MessageListener {

	private static Logger logger = LoggerFactory.getLogger(NotifyMessageListener.class);
	

	/** 微信用户的业务处理 */
	@Autowired
	private WeixinUserService weixinUserService;
	
	/** 图文消息的业务处理 */
	@Autowired
	private NewsMessageService newsMessageService;
	
	/** 图文消息类别的业务处理 */
	@Autowired
	private NewsCategoryService newsCategoryService;
	
	/** 员工信息的业务处理 */
	@Autowired
	private EmployeeService employeeService;
	
	/** 微信认证记录 */
	@Autowired
	private WeixinAuthLogService weixinAuthLogService;

	/**
	 * MessageListener回调函数.
	 */
	@Override
	public void onMessage(Message message) {
		String event=null; 
		String openid=null; 
		try {
			MapMessage mapMessage = (MapMessage) message;
			event = mapMessage.getString("event");
			openid = mapMessage.getString("openid");
			// 打印消息详情
			logger.info("打印JMS信息 --> 事件：{}，标识：{} ",event,openid);
			//根据微信的唯一标识，查询微信订阅者信息.
			WeixinUser weixinUser = weixinUserService.getWeixinUser(openid);
			if(StringUtils.equals(EventType.SUBSCRIBE.getValue(), event)){
				//触发订阅事件
				if(weixinUser!=null){
					logger.info("订阅者更新！");
					weixinUser.setSubscribe(Whether.YES.ordinal());
					weixinUser.setUpdateTime(new Date());
					weixinUserService.saveWeixinUser(weixinUser);
				}else{
					//取得微信下执行openid的用户信息.
					logger.info("订阅者添加！");
					weixinUser=getWeixinUser(openid, false, 0);
					if(weixinUser!=null){
						weixinUserService.saveWeixinUser(weixinUser);
					}else{
						throw new Exception("用户获取有误.")  ;
					}
				}
			}else if(StringUtils.equals(EventType.UNSUBSCRIBE.getValue(), event)){
				//触发取消订阅事件
				logger.info("{} ：取消订阅！",weixinUser.getNickname());
				weixinUser.setSubscribe(Whether.NOT.ordinal());
				weixinUser.setUpdateTime(new Date());
				weixinUserService.saveWeixinUser(weixinUser);
				
			}else if(StringUtils.equals(EventType.SCAN.getValue(), event)){
				String msg="";
				Long eventKey = Long.parseLong(mapMessage.getString("eventKey"));
				Long createTime = Long.parseLong(mapMessage.getString("createTime"));
				String ticket = mapMessage.getString("ticket");
				WeixinAuthLog weixinAuthLog = weixinAuthLogService.getWeixinAuthLog(ticket,eventKey,openid);
				if(weixinAuthLog!=null){
					logger.info("createTime:{},weixinAuthLog.getCreateTime().getTime():{},weixinAuthLog.getExpireSeconds():{}",createTime,weixinAuthLog.getCreateTime().getTime(),weixinAuthLog.getExpireSeconds());
					if((createTime-weixinAuthLog.getCreateTime().getTime())/1000>weixinAuthLog.getExpireSeconds()){
						msg="您的认证超时，请重新按步骤执行认证！";
					}else{
						Employee employee=employeeService.findByCode(weixinAuthLog.getCode());
						if(employee!=null){
							if(employee.getWhether()!=null&&employee.getWhether().equals(Whether.YES)){
								msg="您的账号已经认证过！";
							}else{
								employee.setWhether(Whether.YES);
								employee.setOpenid(openid);
								employee.setEmail(weixinAuthLog.getEmail());
								employee.setEmailPassword(weixinAuthLog.getEmailPassword());
								employeeService.saveEmployee(employee);
								msg="恭喜你，你已经通过了认证，可以使用属于国光员工的专属功能！\n\n注：您的邮件我们会在5分钟后为您收取！";
							}
							
						}else{
							msg="您的认证有误，未能找到对应员工信息，请重新按步骤执行认证！";
						}
					}
				}else{
					msg="您的认证有误，请重新按步骤执行认证！";
				}
				
				
    			Map<String,Object> map=Maps.newHashMap();
				map.put("openid", openid);
				map.put("content", msg);
				String messages = Utils.ftlAnalyze("textMessage.ftl", map);
				Utils.contentPublish(String.format(WeixinConstants.SENT_CUSTOM_MESSAGE_URL, AccessTokenUtil.getAccessToken(false)), messages);
    			
				
			}else if(StringUtils.equals(EventType.CLICK.getValue(), event)){
				String eventKey = mapMessage.getString("content");
				String contextPath=mapMessage.getString("contextPath");
				logger.info("菜单点击事件 {},{},{}",StringUtils.equals(MessageCategory.COMPANY_PROFILE.toString(),eventKey),MessageCategory.COMPANY_PROFILE.toString(),eventKey);
				List<NewsCategory> newsCategorys=Lists.newArrayList();
				if(StringUtils.equals(MessageCategory.COMPANY_PROFILE.toString(),eventKey)){
					newsCategorys = newsCategoryService.getNewsCategory(MessageCategory.COMPANY_PROFILE);
					
				}else if(StringUtils.equals(MessageCategory.TECHNICAL_MONOGRAPH.toString(),eventKey)){
					newsCategorys = newsCategoryService.getNewsCategory(MessageCategory.TECHNICAL_MONOGRAPH);
					
				}else if(StringUtils.equals(MessageCategory.EPAPER.toString(),eventKey)){
					newsCategorys = newsCategoryService.getNewsCategory(MessageCategory.EPAPER);
					
				}else if(StringUtils.equals(MessageCategory.MAJOR_PRODUCT.toString(),eventKey)){
					newsCategorys = newsCategoryService.getNewsCategory(MessageCategory.MAJOR_PRODUCT);
					
				}else if(StringUtils.equals(MessageCategory.NEWS_INFORMATION.toString(),eventKey)){
					newsCategorys = newsCategoryService.getNewsCategory(MessageCategory.NEWS_INFORMATION);
					
				}else if(StringUtils.equals(MessageCategory.REGULAR_BUS.toString(),eventKey)){
					newsCategorys = newsCategoryService.getNewsCategory(MessageCategory.REGULAR_BUS);
					
				}else if(StringUtils.equals(MessageCategory.PERSONAL_INFORMATION.toString(),eventKey)){
					StringBuffer sb=new StringBuffer();
					sb.append("您的个人信息：\n\n");
					if(weixinUser!=null){
						sb.append("昵称："+weixinUser.getNickname()+"\n");
						sb.append("所在地："+weixinUser.getCountry()+"  "+weixinUser.getProvince()+"  "+weixinUser.getCity()+"\n");
						Employee employee = employeeService.findByOpenid(openid);
						if(employee!=null){
							sb.append("认证：认证通过 \n");
							sb.append("姓名："+employee.getName()+" \n");
							sb.append("工号："+employee.getCode()+" \n");
							sb.append("邮箱："+employee.getEmail()+" \n");
						}else{
							sb.append("认证：尚未认证 \n");
						}
					
					}else{
						sb.append("暂未能获取您的个人信息!");
					}
					Map<String,Object> map=Maps.newHashMap();
					map.put("openid", openid);
					map.put("content", sb.toString());
					String messages = Utils.ftlAnalyze("textMessage.ftl", map);
					Utils.contentPublish(String.format(WeixinConstants.SENT_CUSTOM_MESSAGE_URL, AccessTokenUtil.getAccessToken(false)), messages);
					
				}else if(StringUtils.equals("AUTHENTIFICATION_OF_USER",eventKey)){
					String content="请输入[认证#姓名#工号#邮箱#邮箱密码]进行认证,输入时无需中括号[],信息用#号隔开!\n\n注意:邮箱只支持公司的内部邮箱";
					Map<String,Object> map=Maps.newHashMap();
					map.put("openid", openid);
					map.put("content", content);
					String messages = Utils.ftlAnalyze("textMessage.ftl", map);
					Utils.contentPublish(String.format(WeixinConstants.SENT_CUSTOM_MESSAGE_URL, AccessTokenUtil.getAccessToken(false)), messages);
					
				}else{
					String content="此功能我们正加紧开发，敬请期待!";
					Map<String,Object> map=Maps.newHashMap();
					map.put("openid", openid);
					map.put("content", content);
					String messages = Utils.ftlAnalyze("textMessage.ftl", map);
					Utils.contentPublish(String.format(WeixinConstants.SENT_CUSTOM_MESSAGE_URL, AccessTokenUtil.getAccessToken(false)), messages);
				}
				
				if(!newsCategorys.isEmpty()){
					List<NewsMessage> newsMessageList = newsMessageService.getNewsMessage(newsCategorys.get(0));
					Map<String,Object> map=Maps.newHashMap();
					map.put("openid", openid);
					map.put("contextPath", contextPath);
					map.put("messages", newsMessageList);
					
					String messages = Utils.ftlAnalyze("newsMessage.ftl", map);
					logger.info("messages : {}"+messages);
					Utils.contentPublish(String.format(WeixinConstants.SENT_CUSTOM_MESSAGE_URL, AccessTokenUtil.getAccessToken(false)), messages);
				}
				
			}else if(StringUtils.equals(EventType.TEXT.getValue(), event)){
				String content=mapMessage.getString("content");
				String contextPath=mapMessage.getString("contextPath");
				
				if(StringUtils.equals("新闻",content)){
					List<NewsMessage> newsMessageList = newsMessageService.getAllNewsMessage();
					if(newsMessageList.size()>6){
						newsMessageList = newsMessageList.subList(0, 5);
					}
					Map<String,Object> map=Maps.newHashMap();
					map.put("openid", openid);
					map.put("contextPath", contextPath);
					map.put("messages", newsMessageList);

					String messages = Utils.ftlAnalyze("newsMessage.ftl", map);
					logger.info("messages : {}"+messages);
					Utils.contentPublish(String.format(WeixinConstants.SENT_CUSTOM_MESSAGE_URL, AccessTokenUtil.getAccessToken(false)), messages);
					
	            }else if(content.indexOf("#")>0){
	            	String[] values=content.split("#");
	            	boolean bool=true;
	            	String msg="";
	            	if(StringUtils.equals("认证",values[0].trim())){
	            		if(employeeService.findByOpenid(openid)!=null){
	            			bool=false;
	            			msg="您的微信号已经认证过，请不要重复认证！如果认证有问题，请直接与我们联系。";
	            		}
	            		if(bool&&values.length>=5){
	            			logger.info("姓名：{}，工号：{}，邮箱:{}，邮箱密码:{} ",values[1],values[2],values[3],values[4]);
	            			String email=values[3].toString();
	            			if(!email.endsWith("@ggec.gd")){
	            				bool=false;
	            				msg="您输入的邮箱并非公司内部邮箱，请使用公司内部邮箱并重新输入！";
	            			}
	            			
	            			if(bool){
	            			Employee employee = employeeService.findByCode(values[2]);
	            			
	            			if(employee==null){
	            				bool=false;
	            				msg="您输入的工号不存在，请确认您的工号是否正确。";
	            			}else{
	            			if(employee.getWhether().equals(Whether.YES)){
	            				bool=false;
	            				msg="您输入的工号已经认证，请不要重复认证！如果认证有问题，请直接与我们联系。";
	            			}
	            			}
	            			
	            			if(bool){
	            			Long time=new Date().getTime()%100000;
	            			//生成30分钟过期的二维码
	            			Map<String,Object> codeMap=Maps.newHashMap();
	            			codeMap.put("time", time.toString());
	            			String jsonContent=Utils.ftlAnalyze("createQrcode.ftl", codeMap);
	            			logger.info("二维码生成：{}",jsonContent);
	            			String inString = Utils.contentPublish(String.format(WeixinConstants.CREATE_QRCODE_URL, AccessTokenUtil.getAccessToken(false)), jsonContent);
	            			logger.info("二维码生成完成：{}",inString);
	            			Gson gson =new Gson();
	            			Map map = gson.fromJson(inString, Map.class);
	            			
	            			WeixinAuthLog authLog=new WeixinAuthLog();
	            			authLog.setOpenid(openid);
	            			authLog.setContent(content);
	            			authLog.setName(values[1]);
	            			authLog.setCode(values[2]);
	            			authLog.setEmail(values[3]);
	            			authLog.setEmailPassword(values[4]);
	            			authLog.setTicket(map.get("ticket").toString());
	            			authLog.setExpireSeconds(Double.parseDouble(map.get("expire_seconds").toString()));
	            			authLog.setSceneId(time);
	            			authLog.setCreateTime(new Date());
	            			
	            			JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
	            			// 设定mail server
	            			senderImpl.setHost("mail.ggec.gd");

	            			// 建立邮件消息,发送简单邮件和html邮件的区别
	            			MimeMessage mailMessage = senderImpl.createMimeMessage();
	            			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,"UTF-8");

	            			// 设置收件人，寄件人 [自己给自己发送一份带验证消息的邮件]
	            			messageHelper.setTo(values[3]);
	            			messageHelper.setFrom(values[3]);
	            			messageHelper.setFrom(values[3], values[1]);
	            			messageHelper.setSubject("欢迎加入'国光在线'微信服务平台，请根据邮件提示完成员工认证！");
	            			
	            			Map<String,Object> templateMap=Maps.newHashMap();
	    					
	            			templateMap.put("ticket", map.get("ticket"));
	            			templateMap.put("contextPath", contextPath);
	            			templateMap.put("employee", values[1]);
	            			String messages = Utils.ftlAnalyze("mailTemplate.ftl", templateMap);
	            			
	            			// true 表示启动HTML格式的邮件
	            			messageHelper.setText(messages,true);

	            			senderImpl.setUsername(values[3]); // 根据自己的情况,设置username
	            			senderImpl.setPassword(values[4]); // 根据自己的情况, 设置password
	            			Properties prop = new Properties();
	            			prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
	            			prop.put("mail.smtp.timeout", "25000");
	            			senderImpl.setJavaMailProperties(prop);
	            			// 发送邮件
	            			try{
	            				senderImpl.send(mailMessage);
	            				weixinAuthLogService.saveWeixinAuthLog(authLog);
	            				msg="您的认证信息已经发送到您的邮箱，请注意查收。认证信息在30分钟后失效。";
	            			}catch(MailException e){
	            				msg="您的认证邮件发送失败，请检查您输入的邮箱与密码是否正确。如需帮助，请直接与我们联系。";
	            			}
	            			}
	            			}
	            		}else{
	            			msg="您输入的认证信息格式不正确，请重新输入。";
	            		}
	            		
	            		
            			Map<String,Object> map=Maps.newHashMap();
        				map.put("openid", openid);
        				map.put("content", msg);
        				String messages = Utils.ftlAnalyze("textMessage.ftl", map);
        				Utils.contentPublish(String.format(WeixinConstants.SENT_CUSTOM_MESSAGE_URL, AccessTokenUtil.getAccessToken(false)), messages);
            		
	            	}
	            }
			}else if(StringUtils.equals(EventType.CUSTOM_TEXT.getValue(), event)){
				String content=mapMessage.getString("content");
				Map<String,Object> map=Maps.newHashMap();
				map.put("openid", openid);
				map.put("content", content);
				String messages = Utils.ftlAnalyze("textMessage.ftl", map);
				logger.info(messages);
				Utils.contentPublish(String.format(WeixinConstants.SENT_CUSTOM_MESSAGE_URL, AccessTokenUtil.getAccessToken(false)), messages);
			}

		} catch (Exception e) {
			logger.error("处理消息时发生异常.异常信息：{}", e);
			if(openid!=null){
				Map<String,Object> map=Maps.newHashMap();
				map.put("openid", openid);
				map.put("content", "服务器在处理消息时发生异常，请检查您输入的内容是否正确。如需帮助，请直接与我们联系。");
				String messages = Utils.ftlAnalyze("textMessage.ftl", map);
				try {
					Utils.contentPublish(String.format(WeixinConstants.SENT_CUSTOM_MESSAGE_URL, AccessTokenUtil.getAccessToken(false)), messages);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
		
	/**
	 * 获得微信用户信息.
	 * @param openid
	 * @param reset 是否强制重置access_token
	 * @param times 循环开始次数，一般从0开始
	 * @return
	 * @throws IOException
	 */
	public WeixinUser getWeixinUser(String openid, boolean reset, Integer times)
			throws IOException {
		logger.info("提取微信用户信息");
		//循环超过三次，则强制结束，返回空值.
		logger.info("openid：{},reset :{},times:{}",openid,reset,times);
		if (times < 3) {
			URL get_user_url = new URL(String.format(WeixinConstants.GET_USER_URL, AccessTokenUtil.getAccessToken(reset), openid));
			Reader reader = new InputStreamReader(get_user_url.openStream(),"UTF-8");
			Gson gson = new Gson();
			WeixinUser weixinUser = gson.fromJson(reader, WeixinUser.class);
			reader.close();
			//如果取得的微信用户有误（AccessToken超时等），重置AccessToken，重新获取
			if (weixinUser.getErrcode() != null
					&&weixinUser.getOpenid()==null
					&& weixinUser.getErrcode() == 40001
					&& weixinUser.getErrcode() == 40014
					&& weixinUser.getErrcode() == 42001) {
				
				weixinUser=getWeixinUser(openid, true, times + 1);
			}
			logger.info("微信订阅者：{}",weixinUser.toString());
			return weixinUser;
		} else {
			return null;
		}
	}
	
}
