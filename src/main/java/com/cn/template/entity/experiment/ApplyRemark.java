package com.cn.template.entity.experiment;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;
import com.cn.template.xutil.enums.ApplyStatus;
import com.cn.template.xutil.enums.Whether;

/**
 * 实验申请备注.
 * 
 * @author Libra
 *
 */
@Entity
@Table(name = "tb_apply_remark")
public class ApplyRemark extends IdEntity{

	/** 实验委托申请 */
	private Apply apply;
	
	/** 是否通过 */
	private Whether isPass;
	
	/** 实验状态 */
	private ApplyStatus applyStatus;
	
	/** 备注信息 */
	private String remark;

	@ManyToOne
	@JoinColumn(name="apply_id")
	public Apply getApply() {
		return apply;
	}

	public void setApply(Apply apply) {
		this.apply = apply;
	}

	public ApplyStatus getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(ApplyStatus applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Enumerated(EnumType.ORDINAL)
	public Whether getIsPass() {
		return isPass;
	}

	public void setIsPass(Whether isPass) {
		this.isPass = isPass;
	}
	
	
}
