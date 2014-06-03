package com.cn.template.service.mail;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
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
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SentDateTerm;

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

import com.cn.template.entity.Employee;
import com.cn.template.entity.mail.EmailAttachment;
import com.cn.template.entity.mail.EmailContent;
import com.cn.template.entity.weixin.WeixinUser;
import com.cn.template.repository.EmployeeDao;
import com.cn.template.repository.mail.EmailContentDao;
import com.cn.template.repository.weixin.WeixinUserDao;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.Utils;
import com.cn.template.xutil.enums.AttachmentType;
import com.cn.template.xutil.enums.Operator;
import com.cn.template.xutil.enums.Status;
import com.cn.template.xutil.enums.Whether;
import com.cn.template.xutil.persistence.DynamicSpecifications;
import com.cn.template.xutil.persistence.SearchFilter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

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
				if(weixinUser.getSubscribe()!=0){
					logger.info("--开始--员工：{} 的邮件信息.邮箱账号:{} , 密码：{}！",employee.getName(),employee.getEmail(),employee.getEmailPassword());
					try{
						readMail(employee.getEmail(), employee.getEmailPassword(), weixinUser.getOpenid());
					}catch(Exception e){
						e.printStackTrace();
						logger.info("--结束--员工：{} 的邮件信息 -- 抓取失败.",employee.getName());
					}
				}
			}
		}
	}
	
	/**
	 * 连接员工邮箱，取得邮件信息并解析保存.
	 * @param email
	 * @param password
	 * @throws Exception
	 */
	private void readMail(String email,String password,String openid) throws Exception {
		Properties props = System.getProperties();
		props.put("mail.imap.host", "mail.ggec.gd");
		props.put("mail.store.protocol", "imap");
		Session session = Session.getDefaultInstance(props, null);
		IMAPStore store = (IMAPStore) session.getStore("imap");
		store.connect(email,password);
		IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
		
		int maxMsgnum = folder.getMessageCount();
		int minMsgnum =minMsgnum(maxMsgnum, email);
		logger.info("最大邮件ID：{}，当前邮件ID：{}",maxMsgnum,minMsgnum);
		if(maxMsgnum>0&&maxMsgnum>=minMsgnum){
		for(int i=minMsgnum(maxMsgnum, email);i<=maxMsgnum;i++){
			Message message=folder.getMessage(i);
			//设置EmailContent的内容，除了“邮件内容Text”、“邮件内容HTML”、“是否包含附件”
			EmailContent emailContent = initEmailContent(openid, email, message);
			getMailContent(emailContent, message);
			
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
		
	}
	
	/**
	 * 获得最新的邮件，如果从未获取，则获取前10封邮件.
	 * @param maxMsgnum
	 * @param email
	 * @return
	 */
	private int minMsgnum(int maxMsgnum,String email){
		Integer minMsgnum = emailContentDao.findMaxMessageIdByEmail(email);
		if(minMsgnum==null){
			minMsgnum=maxMsgnum-10; 
		}
		if(minMsgnum<=0){
			minMsgnum=0;
		}
		return minMsgnum+1;
	}
	
	/** 设置邮件的查询条件 :情况 邮件中ReceivedDateTerm 、 SentDateTerm的时间比较是按天进行比较的，不能进行时分秒比较*/
	private SearchTerm searchTerm(String email){
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
	}
	
	
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
        if (personal == null)   
            personal = "";   
        
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
		emailContent.setSubject(message.getSubject());
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
		
		emailContent.setReceiveDate(message.getReceivedDate());
		emailContent.setMessageId(message.getMessageNumber());
		
		emailContent.setStatus(Status.NORMAL);
		return emailContent;
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
    				
    				
    				String url = saveFile(bodyPart.getInputStream(),fileName.substring(fileName.lastIndexOf(".")),Constants.WEBROOT+"/html/email/");
    				
    				emailAttachment.setUrl(url);
    				
    				
    				if(bodyPart.getDisposition()!=null&&(bodyPart.getDisposition().equals(Part.ATTACHMENT)||bodyPart.getDisposition().equals(Part.INLINE))){
    					logger.info("{} : 这是附件！ 路径：{}",fileName,url);
    					emailAttachment.setAttachmentType(AttachmentType.ATTACHMEN);
    				}else{
    					logger.info("{} : 这是图片！ 路径：{}",fileName,url);
    					emailAttachment.setAttachmentType(AttachmentType.PICTURE);
    				}
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
	private String saveFile(InputStream inputStream, String suffix, String path) throws Exception {
		String folder = Utils.datef(new Date(), Constants.DATE_FORMAT_SHORT);
		path = path + folder;
		// 判断文件夹是否存在，如果不存在，则创建.
		File localFile = new File(path);
		if (!localFile.exists()) {
			localFile.mkdirs();
		}

		String fileName = new Date().getTime() + suffix;

		File file = new File(path + "/" + fileName);
		System.err.println("--------------------path fileName---------------------------"+path + "/" + fileName);
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
		
		System.err.println("--------------------folder fileName---------------------------"+folder + "/" + fileName);
		return folder + "/" + fileName;
	}
		
}
