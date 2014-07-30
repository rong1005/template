package com.cn.template.web.form;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.cn.template.entity.form.Node;
import com.cn.template.xutil.enums.ApplyStatus;
import com.cn.template.xutil.enums.PermissionType;

/**
 * 节点授权信息（页面数据传递使用）.
 * 
 * @author Libra
 *
 */
public class NodePermissionForm {

	/** 表单ID */
	private Long formId;

	/** 申请状态 (节点) */
	private ApplyStatus applyStatus;

	/** 节点 */
	private List<Node> nodes;

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public ApplyStatus getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(ApplyStatus applyStatus) {
		this.applyStatus = applyStatus;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * 覆盖toString方法，目的显示所有JavaBean的属性值，省略写很多的getXxx的方法
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
