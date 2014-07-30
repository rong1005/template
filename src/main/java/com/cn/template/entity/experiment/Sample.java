package com.cn.template.entity.experiment;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;
import com.cn.template.xutil.enums.ExperimentResult;
import com.cn.template.xutil.enums.SampleStatus;

/**
 * 实验样品信息.
 * 
 * @author Libra
 *
 */
@Entity
@Table(name = "tb_sample")
public class Sample extends IdEntity {

	/** 申请信息 */
	private Apply apply;

	/** 样品名称 */
	private String name;

	/** 样品流水号 */
	private String serialNumber;

	/** 样品状态 */
	private SampleStatus status;

	/** 实验结果 */
	private ExperimentResult result;

	/** 实验结果（中文） */
	private String chResult;

	/** 实验结果（英文） */
	private String enResult;

	@ManyToOne
	@JoinColumn(name = "apply_id")
	public Apply getApply() {
		return apply;
	}

	public void setApply(Apply apply) {
		this.apply = apply;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Enumerated(EnumType.ORDINAL)
	public SampleStatus getStatus() {
		return status;
	}

	public void setStatus(SampleStatus status) {
		this.status = status;
	}

	@Enumerated(EnumType.ORDINAL)
	public ExperimentResult getResult() {
		return result;
	}

	public void setResult(ExperimentResult result) {
		this.result = result;
	}

	public String getChResult() {
		return chResult;
	}

	public void setChResult(String chResult) {
		this.chResult = chResult;
	}

	public String getEnResult() {
		return enResult;
	}

	public void setEnResult(String enResult) {
		this.enResult = enResult;
	}

}
