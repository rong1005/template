package com.cn.template.entity.experiment;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;

/**
 * 实验样品信息.
 * 
 * @author Libra
 *
 */
@Entity
@Table(name = "tb_sample")
public class Sample extends IdEntity{

	/** 申请信息 */
	private Apply apply;
	
	/** 样品名称 */
	private String name;
	
	/** 样品流水号 */
	private String serialNumber;

	@ManyToOne
	@JoinColumn(name="apply_id")
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
	
}
