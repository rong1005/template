package com.cn.template.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.cn.template.xutil.enums.Whether;

/**
 * 员工信息.
 * @author Libra
 *
 */
@Entity
@Table(name="tb_employee")
public class Employee extends IdEntity {

	/** 工号 */
	private String code;
	
	/** 姓名 */
	private String name;
	
	/** 邮箱 */
	private String email;
	
	/** 邮箱密码 */
	private String emailPassword;
	
	/** 是否认证 */
	private Whether whether;
	
	/** 用户的标识，对当前公众号唯一 */
	private String openid;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Enumerated(EnumType.ORDINAL)
	public Whether getWhether() {
		return whether;
	}

	public void setWhether(Whether whether) {
		this.whether = whether;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

}
