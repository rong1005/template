package com.cn.template.entity.authority;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;

/**
 * 侧边栏菜单.
 * @author Libra
 *
 */
@Entity
@Table(name="tb_sidebar")
public class Sidebar extends IdEntity{
	
	/** 名称 */
	private String name;
	
	/** 代码 */
	private String code;
	
	/** 访问地址 */
	private String href;
	
	/** 上级菜单 */
	private Sidebar preSidebar;
	
	/** 菜单对应的角色 */
	private List<Role> roles;
	
	/** 对应的子菜单信息 */
	private List<Sidebar> sidebars;

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

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@ManyToOne
	@JoinColumn(name="pre_sidebar_id")
	public Sidebar getPreSidebar() {
		return preSidebar;
	}

	public void setPreSidebar(Sidebar preSidebar) {
		this.preSidebar = preSidebar;
	}

	@ManyToMany(mappedBy="sidebars")
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@OneToMany(cascade=CascadeType.ALL, mappedBy="preSidebar")
	public List<Sidebar> getSidebars() {
		return sidebars;
	}

	public void setSidebars(List<Sidebar> sidebars) {
		this.sidebars = sidebars;
	}
	
}
