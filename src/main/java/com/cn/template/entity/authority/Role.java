package com.cn.template.entity.authority;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;
import com.cn.template.xutil.enums.RoleType;
import com.cn.template.xutil.enums.Whether;

/**
 * 角色信息.
 * 
 * @author Libra
 * 
 */
@Entity
@Table(name = "tb_role")
public class Role extends IdEntity {
	
	/** 名称 */
	private String name;
	
	/** 代码 */
	private String code;
	
	/** 是否默认角色 */
	private Whether defaultRole;
	
	/** 角色类型 */
	private RoleType roleType;
	
	/** 角色对应的菜单列表 */
	private List<Sidebar> sidebars;
	
	/** 角色对应的用户列表 */
	private List<User> users;

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
	
	@Enumerated(EnumType.ORDINAL)
	public Whether getDefaultRole() {
		return defaultRole;
	}

	public void setDefaultRole(Whether defaultRole) {
		this.defaultRole = defaultRole;
	}

	@Enumerated(EnumType.ORDINAL)
	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	@ManyToMany
	@JoinTable(
			name="tb_role_sidebar",
			joinColumns=@JoinColumn(name="role_id", referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name="sidebar_id", referencedColumnName="id")
			)
	public List<Sidebar> getSidebars() {
		return sidebars;
	}

	public void setSidebars(List<Sidebar> sidebars) {
		this.sidebars = sidebars;
	}

	@ManyToMany(mappedBy="roles")
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
