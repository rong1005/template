package com.cn.template.service.mail;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cn.template.entity.mail.EmailAttachment;
import com.cn.template.entity.mail.EmailContent;
import com.cn.template.entity.structure.Employee;
import com.cn.template.entity.weixin.WeixinUser;
import com.cn.template.repository.mail.EmailContentDao;
import com.cn.template.repository.structure.EmployeeDao;
import com.cn.template.repository.weixin.WeixinUserDao;
import com.cn.template.web.listener.jms.NotifyMessageProducer;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.Utils;
import com.cn.template.xutil.enums.AttachmentType;
import com.cn.template.xutil.enums.EventType;
import com.cn.template.xutil.enums.Operator;
import com.cn.template.xutil.enums.Status;
import com.cn.template.xutil.enums.Whether;
import com.cn.template.xutil.persistence.DynamicSpecifications;
import com.cn.template.xutil.persistence.SearchFilter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

import freemarker.ext.beans.BeansWrapper;

/**
 * 邮件内容管理的业务逻辑.
 * @author Libra
 *
 */
@Component  // Spring Bean的标识.
@Transactional // 类中所有public函数都纳入事务管理的标识.
public class EmailContentService {
	private static final Logger logger=LoggerFactory.getLogger(EmailContentService.class); 
	
	/** 邮件内容管理的数据访问接口 */
	private EmailContentDao emailContentDao;
	
	/** 员工信息的数据访问接口 */
	private EmployeeDao employeeDao;
	
	/** 微信订阅者信息的数据访问接口 */
	private WeixinUserDao weixinUserDao;
	
	/** JMS消息的生产者（将消息加入JMS消息列表） */
	@Autowired
	private NotifyMessageProducer notifyMessageProducer;
	
	@Autowired
	public void setEmailContentDao(EmailContentDao emailContentDao) {
		this.emailContentDao = emailContentDao;
	}
	
	@Autowired
	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	@Autowired
	public void setWeixinUserDao(WeixinUserDao weixinUserDao) {
		this.weixinUserDao = weixinUserDao;
	}

	/**
	 * 根据ID获得邮件内容记录.
	 * @param id
	 * @return
	 */
	public EmailContent getEmailContent(Long id) {
		return emailContentDao.findOne(id);
	}

	/**
	 * 保存邮件内容信息.
	 * @param entity
	 */
	public void saveEmailContent(EmailContent entity) {
		emailContentDao.save(entity);
	}

	/**
	 * 单个删除邮件内容记录.
	 * @param id
	 */
	public void deleteEmailContent(Long id) {
		emailContentDao.delete(id);
	}

	/**
	 * 获得所有的邮件内容记录.
	 * @return
	 */
	public List<EmailContent> getAllEmailContent() {
		return (List<EmailContent>) emailContentDao.findAll();
	}

	
	/**
	 * 获取邮件内容记录[分页、排序]
	 * @param openid
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<EmailContent> getUserEmailContent(String openid,int pageNumber, int pageSize,String sortType){
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Map<String, SearchFilter> filters = Maps.newHashMap();
		filters.put("openid", new SearchFilter("openid", Operator.EQ, openid));
		Specification<EmailContent> spec = DynamicSpecifications.bySearchFilter(filters.values(), EmailContent.class);
		return emailContentDao.findAll(spec, pageRequest);
	}
	
	/**
	 * 获取邮件内容记录[查询、分页、排序].
	 * @param openid
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<EmailContent> getUserEmailContent(String openid, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<EmailContent> spec = buildSpecification(openid, searchParams);

		return emailContentDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 * @param pageNumber
	 * @param pagzSize
	 * @param sortType
	 * @return
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "sentDate");
		} else if ("subject".equals(sortType)) {
			sort = new Sort(Direction.ASC, "subject");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 * @param openid
	 * @param searchParams
	 * @return
	 */
	private Specification<EmailContent> buildSpecification(String openid, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("openid", new SearchFilter("openid", Operator.EQ, openid));
		Specification<EmailContent> spec = DynamicSpecifications.bySearchFilter(filters.values(), EmailContent.class);
		return spec;
	}
	
	/** 接收员工的邮件信息 */
	public void receiveEmployeeMail(){
		logger.info("进入接收邮件的方法!");
		//取得所有已经认证的员工.
		for(Employee employee : employeeDao.findByWhether(Whether.YES)){
			if(employee.getOpenid()!=null&&!employee.equals("")){
				WeixinUser weixinUser = weixinUserDao.findByOpenid(employee.getOpenid());
				/** 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号*/
				if(weixinUser!=null&&weixinUser.getSubscribe()!=0){
					logger.info("--开始--员工：{} 的邮件信息.邮箱账号:{} , 密码：{}",employee.getName(),employee.getEmail(),employee.getEmailPassword());
					try{
						readMail(employee.getEmail(), employee.getEmailPassword(), weixinUser.getOpenid());
					}catch(Exception e){
						e.printStackTrace();
						logger.info(e.getMessage());
						logger.info("--结束--员工：{} 的邮件信息 -- 抓取失败.",employee.getName());
					}
				}
			}
		}
	}
	
	/**
	 * 连接邮件存储空间
	 * @param email
	 * @param password
	 * @return
	 */
	private IMAPStore connectStore(String email,String password,String imapHost){
		Properties props = System.getProperties();
		props.put("mail.imap.host", imapHost);
		props.put("mail.store.protocol", "imap");
		Session session = Session.getDefaultInstance(props, null);
		IMAPStore store=null;
		try {
			store = (IMAPStore) session.getStore("imap");
			try {
				//链接
				store.connect(email,password);
			} catch (MessagingException e) {
				logger.info(e.getMessage());
				return null;
			}
		} catch (NoSuchProviderException e) {
			logger.info(e.getMessage());
			return null;
		}
		return store;
	}
	
	/**
	 * 连接员工邮箱，取得邮件信息并解析保存.
	 * [邮件通过OutLook收取到本地，并删除服务器上的邮件信息]
	 * @param email
	 * @param password
	 * @throws Exception
	 */
	private void readMail(String email,String password,String openid) throws Exception {
		IMAPStore store = null;
		//适配内部邮件的两个服务器.
		store=connectStore(email, password,"mail.ggec.gd");
		if(store==null){
			logger.info("-----------------------mail10.ggec.gd------------------------");
			store=connectStore(email, password,"mail10.ggec.gd");
		}
		
		if(store!=null){
		IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
		//当前服务器中邮件的数量
		int maxMsgnum = folder.getMessageCount();
		if(maxMsgnum>0){
			//数据库中最近一份邮件的收取时间
			Date receiveDate=emailContentDao.findMaxReceiveDateByEmail(email);
			//新邮件数量
			int newMessage=0;
			boolean bool=true;
			//收取邮件的数量
			int num=0;
			while(bool&&num<15){
				//当前要读取的邮件编号.
				int thisNum=maxMsgnum-num;
				if(thisNum>0){
					Message message=folder.getMessage(thisNum);
					EmailContent emailContent = initEmailContent(openid, email, message);
					logger.info("抓取邮箱：{}，邮件编号：{}，邮件主题：{}",email,emailContent.getMessageId(),emailContent.getSubject());
					//判断接收时间，如果数据库中最新的接收邮件大于或等于邮件服务器的邮件，则该邮件已读取
					if(receiveDate!=null&&(emailContent.getReceiveDate().equals(receiveDate)||emailContent.getReceiveDate().before(receiveDate))){
						logger.info("{}：{}  的邮件已存在，不再往下收取！",emailContent.getMessageId(),emailContent.getSubject());
						bool=false;
					}else{
						getMailContent(emailContent, message);
						
						if(emailContent.getBodyHtml()==null){
							emailContent.setBodyHtml(emailContent.getBodyText());
						}else{
						//截取邮件中body的内容
						emailContent.setBodyHtml(cutHtmlBody(emailContent.getBodyHtml()));
						//替换邮件中的图片路径（由代号 cid 更换为路径）
						emailContent.setBodyHtml(replaceAttachPath(emailContent.getBodyHtml(), emailContent.getAttachments()));
						
						//如果text的值为空Html不为空，则将html内容去除HTML标签作为Text保存
						if(emailContent.getBodyText()==null){
							emailContent.setBodyText(Utils.delHTMLTag(emailContent.getBodyHtml()).replaceAll("\\s*|\t|\r|\n","").replaceAll("&.*?;", ""));
						}
						}
						//将邮件内容静态化，保存访问路径
						saveHtml(emailContent,Constants.WEBROOT+"/html/email/");
						emailContentDao.save(emailContent);
						num=num+1;
						if(emailContent.getHasRead().equals(Whether.NOT)){
							//在读取邮件进行解析时，邮件服务器会将该邮件标记为已读，不便于两个客户端之间的处理，
				        	//所以，要将原来是“未读”的邮件重新标记为未读
				        	logger.info("设置邮件 {} 为“未读”",message.getSubject());
				        	message.setFlag(Flags.Flag.SEEN, false);
							newMessage=newMessage+1;
						}
					}
				}else{
					bool=false;
				}
			}
			
			if(newMessage>0){
				String content="您好：我们刚为您收取了 "+newMessage+" 封未读邮件，请进入'个人中心-我的邮箱'查阅，以免错过重要信息！ ";
				notifyMessageProducer.sendQueue(EventType.CUSTOM_TEXT.getValue(),openid,content);
			}
		}
		
		//关闭收件箱
		if(folder!=null&&folder.isOpen()){
			folder.close(false);
		}
		//关闭邮件存储空间
		if(store!=null){
			store.close();
		}
		}else{
			logger.info("邮件服务store链接失败，未能获取邮件！");
		}
	}
	
	
	/**
	 * 连接员工邮箱，取得邮件信息并解析保存.
	 * [并非通过OutLook获取，所有的邮件会保留在邮件服务器，不适用与当前公司的情况  废弃]
	 * @param email
	 * @param password
	 * @throws Exception
	 */
	/*private void readMailSaveInService(String email,String password,String openid) throws Exception {
		Properties props = System.getProperties();
		props.put("mail.imap.host", "mail.ggec.gd");
		//props.put("mail.imap.host", "imap.163.com");
		props.put("mail.store.protocol", "imap");
		Session session = Session.getDefaultInstance(props, null);
		IMAPStore store = (IMAPStore) session.getStore("imap");
		store.connect(email,password);
		//store.connect("rong_1005@163.com","248858868");
		IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
		
		int maxMsgnum = folder.getMessageCount();
		int minMsgnum =minMsgnum(maxMsgnum, email);
		
//		int maxMsgnum = 3;
//		int minMsgnum =1;
		logger.info("最大邮件ID：{}，当前邮件ID：{}",maxMsgnum,minMsgnum);
		logger.info("-----------------{}:{}",maxMsgnum>0,maxMsgnum>=minMsgnum);
		if(maxMsgnum>0&&maxMsgnum>=minMsgnum){
		for(int i=minMsgnum;i<=maxMsgnum;i++){
			Message message=folder.getMessage(i);
			//设置EmailContent的内容，除了“邮件内容Text”、“邮件内容HTML”、“是否包含附件”
			EmailContent emailContent = initEmailContent(openid, email, message);
			
			getMailContent(emailContent, message);
			
			if(emailContent.getBodyHtml()==null){
				emailContent.setBodyHtml(emailContent.getBodyText());
			}else{
			//截取邮件中body的内容
			emailContent.setBodyHtml(cutHtmlBody(emailContent.getBodyHtml()));
			//替换邮件中的图片路径（由代号 cid 更换为路径）
			emailContent.setBodyHtml(replaceAttachPath(emailContent.getBodyHtml(), emailContent.getAttachments()));
			
			//如果text的值为空Html不为空，则将html内容去除HTML标签作为Text保存
			if(emailContent.getBodyText()==null){
				emailContent.setBodyText(Utils.delHTMLTag(emailContent.getBodyHtml()).replaceAll("\\s*|\t|\r|\n","").replaceAll("&.*?;", ""));
			}
			}
			//将邮件内容静态化，保存访问路径
			saveHtml(emailContent,Constants.WEBROOT+"/html/email/");
			
			
			logger.info("邮件内容信息：{}",emailContent.getSubject());
			emailContentDao.save(emailContent);
		}
		}
		if(folder!=null&&folder.isOpen()){
			folder.close(false);
		}
		if(store!=null){
			store.close();
		}
		
	}*/
	
	public static void main(String[] args) {
		try{
			Properties props = System.getProperties();
			props.put("mail.imap.host", "mail.ggec.gd");
			//props.put("mail.imap.host", "imap.163.com");
			
			props.put("mail.store.protocol", "imap");
			Session session = Session.getDefaultInstance(props, null);
			IMAPStore store = (IMAPStore) session.getStore("imap");
			store.connect("lzr@ggec.gd","pass");
			//store.connect("rong_1005@163.com","248858868");
			
//			for(Folder folder :store.getPersonalNamespaces()){
//				System.out.println("33"+folder.getNewMessageCount());
//			}
//			System.out.println("--"+store.getDefaultFolder().getMessageCount());
//			
			IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX");
			folder.open(Folder.READ_WRITE);
			
//			for(Folder folder2 :folder.list()){
//				System.out.println("33"+folder2.getName());
//			}
			
			System.out.println(folder.getMessageCount());
			Message message=folder.getMessage(255);
			System.out.println(message.getSubject());
			
			InternetAddress address[] = (InternetAddress[]) message.getFrom();   
			
			
			
	        String from = address[0].getAddress();   
	        if (from == null)   
	            from = "";   
	        String personal = address[0].getPersonal();   
	        if (personal == null)   
	            personal = "";   
	        
	        logger.info("发件人信息：{}",personal + "<" + from + ">");
	        
			System.out.println(message.isMimeType("text/plain"));
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 截取html中对应的body内容
	 * @param html
	 * @return
	 */
	private String cutHtmlBody(String html){
		logger.info("html stare2->{}，end2->{} ",html.indexOf("<body"),html.indexOf("</body"));
		if(html.indexOf("<body")<0||html.indexOf("</body>")<0){
			logger.info("html :{}",html);
			return html;
		}else{
		int stateSite=html.toLowerCase().indexOf(">", html.indexOf("<body"));
		int endSite=html.toLowerCase().indexOf("</body>");
		logger.info("html stare->{}，end->{} ",stateSite,endSite);
		
		return html.substring(stateSite+1,endSite);
		}
	}
	
	/**
	 * 替换邮件中的图片路径（由代号 cid 更换为路径）
	 * @param html
	 * @return
	 */
	private String replaceAttachPath(String html,List<EmailAttachment> attachments){
		if(attachments!=null&&!attachments.isEmpty()){
			for(EmailAttachment emailAttachment : attachments){
        		if(emailAttachment.getAttachmentType().equals(AttachmentType.PICTURE)){
        			if(html.indexOf("cid:"+emailAttachment.getFileName())>0){
        				html=replaceAttachPath(html, emailAttachment);
        			}
        		}
			}
		}
		return html;
	}
	
	/**
	 * 递归处理邮件内图片替换的问题.(同一图片使用多次的情况).
	 * @param html
	 * @param emailAttachment
	 * @return
	 */
	private String replaceAttachPath(String html,EmailAttachment emailAttachment){
		//递归处理同一图片多次使用的问题[使用正则表达式后，无需使用]
		/*int startSite = html.indexOf("cid:"+emailAttachment.getFileName());
		int endSite = html.indexOf(">", startSite);
		logger.info("startSite :{},endSite :{}",startSite,endSite);
		html=html.substring(0,startSite)+emailAttachment.getUrl()+"\""+html.substring(endSite);
		while (html.indexOf("cid:"+emailAttachment.getFileName())>0) {
			html=replaceAttachPath(html, emailAttachment);
		}*/
		
		html=html.replaceAll("\\bsrc=\"cid:"+emailAttachment.getFileName()+".*?\"", "src=\""+emailAttachment.getUrl()+"\"");
		return html;
	}
	
	
	/**
	 * 获得最新的邮件，如果从未获取，则获取前10封邮件.
	 * @param maxMsgnum
	 * @param email
	 * @return
	 */
	/*private int minMsgnum(int maxMsgnum,String email){
		Integer minMsgnum = emailContentDao.findMaxMessageIdByEmail(email);
		if(minMsgnum==null){
			minMsgnum=maxMsgnum-21; 
		}
		if(minMsgnum<=0){
			minMsgnum=0;
		}
		//为什么要在此处加1？当minMsgnum不为空且>0时，会出现当前值与最大值一致，所以要当前值大于最大值，则加1
		return minMsgnum+1;
	}*/
	
	/** 设置邮件的查询条件 :情况 邮件中ReceivedDateTerm 、 SentDateTerm的时间比较是按天进行比较的，不能进行时分秒比较*/
	/*private SearchTerm searchTerm(String email){
		//搜索邮件[初定以时间作为搜索条件，如果从未收取的，默认收取本周的所有邮件；如果收取的，从最近的一次收取时间开始]
		Date startDate = emailContentDao.findMaxReceiveDateByEmail(email);
		System.err.println("-------startDate-------------"+startDate);
		if(startDate==null){
			//取得本周的开始日期（星期天作为一周的开始）
			Calendar calendar = Calendar.getInstance(); 
			calendar.add(Calendar.DAY_OF_MONTH, 0-(calendar.get(Calendar.DAY_OF_WEEK)-1));
	        startDate=calendar.getTime();
		}
		System.err.println("-------startDate2-------------"+startDate);
//		//收信的开始时间
//		SearchTerm comparisonTermGe = new ReceivedDateTerm(ComparisonTerm.GE, new Date());
//		//收信的结束时间
//		SearchTerm comparisonTermLe = new ReceivedDateTerm(ComparisonTerm.LE, new Date());
		return new SentDateTerm(ComparisonTerm.EQ, new Date());
	}*/
	
	
	/**
	 * 判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false"
	 * @param message
	 * @return
	 * @throws MessagingException
	 */
    private boolean getReplySign(Message message) throws MessagingException {   
        boolean replysign = false;   
        String needreply[] = message.getHeader("Disposition-Notification-To");   
        if (needreply != null) {   
            replysign = true;   
        }   
        return replysign;   
    }
    
    /**
     * 获得发件人的地址和姓名.
     * @param message
     * @param emailContent
     * @return
     * @throws Exception
     */
    private void getFrom(Message message,EmailContent emailContent) throws Exception {   
        InternetAddress address[] = (InternetAddress[]) message.getFrom();   
        String from = address[0].getAddress();   
        if (from == null)   
            from = "";   
        String personal = address[0].getPersonal();   
        if (personal == null){
            personal = from;   
        }
        
        logger.info("发件人信息：{}",personal + "<" + from + ">");
        emailContent.setFromName(personal);
        emailContent.setFromAddress(from);
    }
    
    
    /**
     * 获得邮件的收件人，抄送，和密送的地址和姓名，
     * 根据所传递的参数的不同 
     * "to"----收件人 
     * "cc"---抄送人地址 
     * "bcc"---密送人地址
     * 
     * @param type
     * @return
     * @throws Exception
     */
    private String getMailAddress(String type,Message message) throws Exception {   
        String mailaddr = "";   
        String addtype = type.toUpperCase();   
        InternetAddress[] address = null;   
        if (addtype.equals("TO") || addtype.equals("CC")|| addtype.equals("BCC")) {   
            if (addtype.equals("TO")) {   
                address = (InternetAddress[]) message.getRecipients(Message.RecipientType.TO);   
            } else if (addtype.equals("CC")) {   
                address = (InternetAddress[]) message.getRecipients(Message.RecipientType.CC);   
            } else {   
                address = (InternetAddress[]) message.getRecipients(Message.RecipientType.BCC);   
            }   
            if (address != null) {   
                for (int i = 0; i < address.length; i++) {   
                    String email = address[i].getAddress();   
                    if (email == null)   
                        email = "";   
                    else {   
                        email = MimeUtility.decodeText(email);   
                    }   
                    String personal = address[i].getPersonal();   
                    if (personal == null)   
                        personal = "";   
                    else {   
                        personal = MimeUtility.decodeText(personal);   
                    }   
                    String compositeto = personal + "<" + email + ">";   
                    mailaddr += "," + compositeto;   
                }   
                mailaddr = mailaddr.substring(1);   
            }   
        } else {   
            throw new Exception("Error emailaddr type!");   
        }   
        return mailaddr;   
    }
    
    /**
     * 填充邮件的内容信息.
     * @param openid
     * @param email
     * @param message
     * @return
     * @throws Exception
     */
    private EmailContent initEmailContent(String openid,String email,Message message) throws Exception{
    	EmailContent emailContent = new EmailContent();
		emailContent.setCreateTime(new Date());
		emailContent.setUpdateTime(new Date());
		emailContent.setOpenid(openid);
		emailContent.setEmail(email);
		
		//对获取的邮件主题进行转码
		String subject = message.getSubject();
		if(subject!=null&&subject.toLowerCase().indexOf("=?gb")>0){
			subject=decodeText(subject);
		}
		emailContent.setSubject(subject);
		
		//邮件发送时间
		emailContent.setSentDate(message.getSentDate());
		if(getReplySign(message)){
			//需要回执
			emailContent.setReplySign(Whether.YES);
		}else{
			//不需要回执
			emailContent.setReplySign(Whether.NOT);
		}
		
		if(message.getFlags().contains(Flags.Flag.SEEN)){
			//邮件已读
			emailContent.setHasRead(Whether.YES);
    	}else{
    		//邮件未读
    		emailContent.setHasRead(Whether.NOT);
    	}
		
		//邮件是否包含附件[默认无，根据邮件解析情况调整]
		emailContent.setIsContainAttach(Whether.NOT);
		
		//获得发件人的地址和姓名
		getFrom(message, emailContent);
		
		//"to"----收件人
		emailContent.setMailAddressTo(getMailAddress("to", message));
		
		//"cc"----收件人
		emailContent.setMailAddressCc(getMailAddress("cc", message));
		
		//"bcc"----收件人
		emailContent.setMailAddressBcc(getMailAddress("bcc", message));
		
		//邮件接收时间
		emailContent.setReceiveDate(message.getReceivedDate());
		emailContent.setMessageId(message.getMessageNumber());
		
		emailContent.setStatus(Status.NORMAL);
		return emailContent;
    }
    
    /**
     * 递归解码（如果内容/主题中存在编码内容，则递归转码）.
     * @param subject
     * @return
     * @throws Exception
     */
    private String decodeText(String subject) throws Exception{
		String text = subject.substring(subject.toLowerCase().indexOf("=?gb"));
		text =MimeUtility.decodeText(text);
		subject = subject.toLowerCase().substring(0,subject.toLowerCase().indexOf("=?gb")) +text;
		while (subject.toLowerCase().indexOf("=?gb")>0) {
			subject=decodeText(subject);
		}
		return subject;
	}
    
    
	/**
	 * 解析邮件的主体内容.
	 * 如果邮件中有内嵌资源（附件、HTML、图片等等）,需要通过递归方式进行获取.
	 * 
	 * @param emailContent
	 * @param part
	 * @throws Exception
	 */
	private void getMailContent(EmailContent emailContent,Part part) throws Exception{
		//通过下面的方式，获得调用的详细路径，对比是否使用合适的包文件，如果不是，则出现包冲突的问题。
		//如该项在Tomcat下不能通过，报NullPointerException的问题，是项目中多了这个文件包geronimo-javamail_1.4_spec-1.6.jar
//		System.out.println(javax.mail.internet.MimeMessage.class.getResource(""));
//		logger.info("emailContent:{}",emailContent);
		if(part.isMimeType("text/plain")){
			//取得邮件的Text内容
			if(emailContent.getBodyText()!=null){
				emailContent.setBodyText(emailContent.getBodyText()+part.getContent());
			}else{
				emailContent.setBodyText(part.getContent().toString());
			}
    	}else if(part.isMimeType("text/html")){
    		//取得邮件的HTML内容
    		if(emailContent.getBodyHtml()!=null){
    			emailContent.setBodyHtml(emailContent.getBodyHtml()+part.getContent());
    		}else{
    			emailContent.setBodyHtml(part.getContent().toString());
    		}
    	}else if(part.isMimeType("multipart/*")){
    		//邮件有内嵌资源
    		Multipart multipart =(Multipart)part.getContent();
    		for(int i=0;i<multipart.getCount();i++){
    			BodyPart bodyPart = multipart.getBodyPart(i);
    			//递归读取邮件资源.
    			getMailContent(emailContent,bodyPart);
    			
    			String fileName=bodyPart.getFileName();
    			if(fileName!=null){
    				//此邮件有附件.
    				emailContent.setIsContainAttach(Whether.YES);
    				EmailAttachment emailAttachment=new EmailAttachment();
    				emailAttachment.setEmailContent(emailContent);
    				fileName=new String(fileName.getBytes("ISO-8859-1"),"gb2312");
    				if(fileName.toLowerCase().startsWith("=?gb")){
    					 fileName = MimeUtility.decodeText(fileName); 
    				}
    				
    				emailAttachment.setContentType(new String(bodyPart.getContentType().getBytes("ISO-8859-1"),"gb2312"));
    				emailAttachment.setFileName(fileName);
    				
    				if(bodyPart.getDisposition()!=null&&(bodyPart.getDisposition().equals(Part.ATTACHMENT)||bodyPart.getDisposition().equals(Part.INLINE))){
    					emailAttachment.setAttachmentType(AttachmentType.ATTACHMEN);
    				}else{
    					emailAttachment.setAttachmentType(AttachmentType.PICTURE);
    				}
    				
    				saveFile(emailAttachment,bodyPart.getInputStream(),fileName.substring(fileName.lastIndexOf(".")),Constants.WEBROOT+"/html/email/",emailAttachment.getAttachmentType());

    				if(emailContent.getAttachments()==null){
    					List<EmailAttachment> list=Lists.newArrayList();
    					list.add(emailAttachment);
    					emailContent.setAttachments(list);
    				}else{
    					emailContent.getAttachments().add(emailAttachment);
    				}
    			}
    		}
    	}
	}
	
	
	/**
	 * 保存邮件附件,返回保存路径.
	 * @param inputStream
	 * @return
	 */
	private void saveFile(EmailAttachment emailAttachment,InputStream inputStream, String suffix, String path, AttachmentType attachmentType) throws Exception {
		String folder = Utils.datef(new Date(), Constants.DATE_FORMAT_SHORT);
		String type=attachmentType.toString().toLowerCase();
		path = path + folder + "/" + type;
		// 判断文件夹是否存在，如果不存在，则创建.
		File localFile = new File(path);
		if (!localFile.exists()) {
			localFile.mkdirs();
		}

		String fileName = new Date().getTime() + suffix;

		File file = new File(path + "/" + fileName);
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bis = new BufferedInputStream(inputStream);
			int c;
			while ((c = bis.read()) != -1) {
				bos.write(c);
				bos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bos.close();
			bis.close();
		}
		
		emailAttachment.setUrl(type + "/" + fileName);
		emailAttachment.setFullUrl(folder + "/" + type + "/" + fileName);
	}
	
	
	/**
	 * 将邮件的html内容保存为静态的.html文件.
	 * @param html
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private void saveHtml(EmailContent emailContent,String path) throws Exception{
		String folder = Utils.datef(new Date(), Constants.DATE_FORMAT_SHORT);
		path = path + folder;
		// 判断文件夹是否存在，如果不存在，则创建.
		File localFile = new File(path);
		if (!localFile.exists()) {
			localFile.mkdirs();
		}
		String timeName=new Date().getTime()+"";
		String fileName = timeName + "_1.html";
		String originalFileName = timeName + "_2.html";
		
		emailContent.setUrl(folder + "/" + fileName);
		emailContent.setOriginalUrl(folder + "/" +originalFileName);
		
		File file = new File(path + "/" + fileName);
		File originalFile = new File(path + "/" + originalFileName);
		
		Map<String,Object> map=Maps.newHashMap();
		map.put("contextPath", Constants.CONTEXT_PATH);
		map.put("email", emailContent);
		map.put("enums", BeansWrapper.getDefaultInstance().getEnumModels());
		
		OutputStreamWriter out1=new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file), 16 * 1024), "UTF-8");
		//HTML内容写入文件.
		out1.write(Utils.ftlAnalyze("weixinMailContent.ftl", map));
		
		OutputStreamWriter out2=new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(originalFile), 16 * 1024), "UTF-8");
		out2.write(Utils.ftlAnalyze("weixinMailOrginal.ftl", map));
		
		out1.close();
		out2.close();
		
	}
		
}
