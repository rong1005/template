package com.cn.template.entity.experiment;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.cn.template.entity.IdEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 实验排期
 * @author Libra
 *
 */
@Entity
@Table(name = "tb_schedule")
public class Schedule extends IdEntity {

	/** 设备 */
	private Equipment equipment;
	
	/** 样品 */
	private Sample sample;
	
	/** 开始时间 */
	private Date startTime;
	
	/** 实验用时 */
	private Integer usedTime;
	
	/** 结束时间 */
	private Date endTime;
	
	/** 实际开始时间 */
	private Date realStartTime;
	
	/** 实际结束时间 */
	private Date realEndTime;

	
	@ManyToOne
	@JoinColumn(name="equipment_zone_id")
	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	
	@ManyToOne
	@JoinColumn(name="sample_id")
	public Sample getSample() {
		return sample;
	}

	public void setSample(Sample sample) {
		this.sample = sample;
	}

	/**
	 * 'Temporal' 定义时间保存的格式
	 * 'DateTimeFormat' 普通输出格式
	 * 'JsonFormat' JSON输出的格式
	 * @return
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 'Temporal' 定义时间保存的格式
	 * 'DateTimeFormat' 普通输出格式
	 * 'JsonFormat' JSON输出的格式
	 * @return
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Integer usedTime) {
		this.usedTime = usedTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
	public Date getRealStartTime() {
		return realStartTime;
	}

	public void setRealStartTime(Date realStartTime) {
		this.realStartTime = realStartTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
	public Date getRealEndTime() {
		return realEndTime;
	}

	public void setRealEndTime(Date realEndTime) {
		this.realEndTime = realEndTime;
	}
	
	
}
