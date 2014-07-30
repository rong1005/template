package com.cn.template.entity.form;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;
import com.cn.template.xutil.enums.ApplyStatus;
import com.cn.template.xutil.enums.PermissionType;

/**
 * 节点权限信息
 * 
 * @author Libra
 *
 */
@Entity
@Table(name = "tb_node")
public class Node extends IdEntity {
	/** 字段 */
	private Field field;
	
	/** 申请状态（所属节点） */
	private ApplyStatus applyStatus;
	
	/** 访问权限 */
	private PermissionType permissionType;

	@ManyToOne
	@JoinColumn(name="field_id")
	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public ApplyStatus getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(ApplyStatus applyStatus) {
		this.applyStatus = applyStatus;
	}

	public PermissionType getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(PermissionType permissionType) {
		this.permissionType = permissionType;
	}
	
}
