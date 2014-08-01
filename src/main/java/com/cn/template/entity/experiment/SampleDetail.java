package com.cn.template.entity.experiment;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;

/**
 * 实验样品明细.
 * 
 * @author Libra
 *
 */
@Entity
@Table(name = "tb_sample_detail")
public class SampleDetail extends IdEntity {

	/** 样品 */
	private Sample sample;
	
	/** 样品流水号 */
	private String serialNumber;

	@ManyToOne
	@JoinColumn(name="sample_id")
	public Sample getSample() {
		return sample;
	}

	public void setSample(Sample sample) {
		this.sample = sample;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

}
