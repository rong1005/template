package com.cn.template.web.shiro;

import java.io.Serializable;
import com.google.common.base.Objects;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
 * @author Libra
 *
 */
public class ShiroUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 员工编号 */
	public Long id;
	
	/** 登录名 */
	public String loginName;
	
	/** 员工姓名 */
	public String name;
	
	/** 员工照片 */
	public String photo;

	public ShiroUser(Long id, String loginName, String name,String photo) {
		this.id = id;
		this.loginName = loginName;
		this.name = name;
		this.photo = photo;
	}

	public String getName() {
		return name;
	}

	public String getPhoto() {
		return photo;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return loginName;
	}

	/**
	 * 重载hashCode,只计算loginName;
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(loginName);
	}

	/**
	 * 重载equals,只计算loginName;
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ShiroUser other = (ShiroUser) obj;
		if (loginName == null) {
			if (other.loginName != null) {
				return false;
			}
		} else if (!loginName.equals(other.loginName)) {
			return false;
		}
		return true;
	}
}
