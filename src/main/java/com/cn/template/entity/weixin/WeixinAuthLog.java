package com.cn.template.entity.weixin;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;

 /**
  * 微信与工号挂靠认证的记录
  * @author Libra
  *
  */
@Entity
@Table(name="tb_weixin_auth_log")
public class WeixinAuthLog extends IdEntity {
	/** 用户标识 */
	private String openid;
	
	/** 发送的内容 */
	private String content;
	
	/** 姓名 */
	private String name;
	
	/** 工号 */
	private String code;
	
	/** 邮箱 */
	private String email;
	
	/** 邮箱密码 */
	private String emailPassword;
	
	/** 获取的二维码ticket */
	private String ticket;
	
	/** 有效时间 */
	private Double expireSeconds;
	
	/** 场景值ID */
	private Long sceneId;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Double getExpireSeconds() {
		return expireSeconds;
	}

	public void setExpireSeconds(Double expireSeconds) {
		this.expireSeconds = expireSeconds;
	}

	public Long getSceneId() {
		return sceneId;
	}

	public void setSceneId(Long sceneId) {
		this.sceneId = sceneId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}
	
}
