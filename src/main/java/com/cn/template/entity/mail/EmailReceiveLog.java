package com.cn.template.entity.mail;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;

/**
 * 邮件的抓取记录.
 * @author Libra
 *
 */
@Entity
@Table(name="tb_email_receive_log")
public class EmailReceiveLog extends IdEntity {
	
	/** 员工标识号 */
	private String openid;
	
	/** 员工邮箱 */
	private String email;
	
	/** 姓名 */
	private String name;
	
	//收取的情况：自动/手动
	//收取的状态：成功/失败
	//收取的邮件数

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
