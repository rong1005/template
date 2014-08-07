package com.cn.template.entity.experiment;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.cn.template.entity.IdEntity;
import com.cn.template.entity.authority.User;
import com.cn.template.xutil.enums.ExceptionHandleType;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 异常处理信息.
 * @author Libra
 *
 */
@Entity
@Table(name = "tb_exception_handle")
public class ExceptionHandle extends IdEntity{

	/** 巡检记录 */
	private InspectionRecord inspectionRecord;
	
	/** 实验排期 */
	private Schedule schedule;
	
	/** 处理人 */
	private User user;
	
	/** 处理类型 */
	private ExceptionHandleType exceptionHandleType;
	
	/** 描述 */
	private String description;
	
	/** 暂停时间 */
	private Date stopTime;
	
	/** 重新开始时间 */
	private Date restratTime;
	
	/** 原来计划结束时间 */
	private Date endTimeBefore; 
	
	/** 现计划结束时间 */
	private Date endTimeNow;
	
	/** 原设备 */
	private Equipment equipmentBefore;
	
	/** 现设备 */
	private Equipment equipmentNow;

	@ManyToOne
	@JoinColumn(name="inspection_record_id")
	public InspectionRecord getInspectionRecord() {
		return inspectionRecord;
	}

	public void setInspectionRecord(InspectionRecord inspectionRecord) {
		this.inspectionRecord = inspectionRecord;
	}

	@ManyToOne
	@JoinColumn(name="schedule_id")
	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
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
	public Date getRestratTime() {
		return restratTime;
	}

	public void setRestratTime(Date restratTime) {
		this.restratTime = restratTime;
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
	public Date getEndTimeBefore() {
		return endTimeBefore;
	}

	public void setEndTimeBefore(Date endTimeBefore) {
		this.endTimeBefore = endTimeBefore;
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
	public Date getEndTimeNow() {
		return endTimeNow;
	}

	public void setEndTimeNow(Date endTimeNow) {
		this.endTimeNow = endTimeNow;
	}

	@ManyToOne
	@JoinColumn(name="equipment_before_id")
	public Equipment getEquipmentBefore() {
		return equipmentBefore;
	}

	public void setEquipmentBefore(Equipment equipmentBefore) {
		this.equipmentBefore = equipmentBefore;
	}

	@ManyToOne
	@JoinColumn(name="equipment_now_id")
	public Equipment getEquipmentNow() {
		return equipmentNow;
	}

	public void setEquipmentNow(Equipment equipmentNow) {
		this.equipmentNow = equipmentNow;
	}

	@Enumerated(EnumType.ORDINAL)
	public ExceptionHandleType getExceptionHandleType() {
		return exceptionHandleType;
	}

	public void setExceptionHandleType(ExceptionHandleType exceptionHandleType) {
		this.exceptionHandleType = exceptionHandleType;
	}

	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
