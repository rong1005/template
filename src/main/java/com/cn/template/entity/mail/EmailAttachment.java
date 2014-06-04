package com.cn.template.entity.mail;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;
import com.cn.template.xutil.enums.AttachmentType;

/**
 * 邮件附件
 * @author Libra
 *
 */
@Entity
@Table(name="tb_email_attachment")
public class EmailAttachment extends IdEntity {

	/** 附件对应的邮件 */
	private EmailContent emailContent;
	
	/** ContentType */
	private String contentType;
	
	/** 附件类型 */
	private AttachmentType attachmentType;
	
	/** 文件名 */;
	private String fileName;
	
	/** 存放目录 */
	private String url;
	
	/** 完整存放目录 */
	private String fullUrl;

	@ManyToOne
	@JoinColumn(name="email_content_id")
	public EmailContent getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(EmailContent emailContent) {
		this.emailContent = emailContent;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Enumerated(EnumType.ORDINAL)
	public AttachmentType getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(AttachmentType attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFullUrl() {
		return fullUrl;
	}

	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}
	
}
