package com.cn.template.entity.structure;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;
import com.cn.template.xutil.enums.DepartmentType;
import com.cn.template.xutil.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 部门信息.
 * @author Libra
 *
 */
@Entity
@Table(name="tb_department")
public class Department extends IdEntity {
	
	/** 部门名称 */
	private String name;
	
	/** 集团类型 */
	private DepartmentType departmentType;
	
	/** 描述 */
	private String description;

	/** 上级部门 */
	private Department higherDepartment;
	
	/** 显示顺序 */
	private int showOrder;
	
	/** 状态 */
	private Status status; 
	
	/** 子部门集 */
	private List<Department> departments;

	/** 构造器 */
	public Department() {
		// TODO Auto-generated constructor stub
	}
	
	/** 构造器 带参数ID */
	public Department(Long id) {
		this.id=id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	@JoinColumn(name="higher_department_id")
	public Department getHigherDepartment() {
		return higherDepartment;
	}

	public void setHigherDepartment(Department higherDepartment) {
		this.higherDepartment = higherDepartment;
	}

	@Enumerated(EnumType.ORDINAL)
	public DepartmentType getDepartmentType() {
		return departmentType;
	}

	public void setDepartmentType(DepartmentType departmentType) {
		this.departmentType = departmentType;
	}

	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	@Enumerated(EnumType.ORDINAL)
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@JsonIgnore //不转化为json格式.
	@OneToMany(cascade=CascadeType.ALL, mappedBy="higherDepartment")
	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	
}
