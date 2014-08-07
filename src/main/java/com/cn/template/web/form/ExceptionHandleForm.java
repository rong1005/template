package com.cn.template.web.form;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import com.cn.template.entity.experiment.Equipment;
import com.cn.template.xutil.enums.ExceptionHandleType;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 异常处理信息（页面数据传递使用）.
 * @author Libra
 *
 */
public class ExceptionHandleForm {

	/** 处理类型 */
	private ExceptionHandleType exceptionHandleType;
	
	/** 实际结束时间 */
	private Date endTimeNow;
	
	/** 暂停时间 */
	private Date stopTime;
	
	/** 重新回复时间 */
	private Date restratTime;
	
	/** 设备 */
	private Equipment equipment;
	
	/** 描述 */
	private String description;

	public ExceptionHandleType getExceptionHandleType() {
		return exceptionHandleType;
	}

	public void setExceptionHandleType(ExceptionHandleType exceptionHandleType) {
		this.exceptionHandleType = exceptionHandleType;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
	public Date getEndTimeNow() {
		return endTimeNow;
	}

	public void setEndTimeNow(Date endTimeNow) {
		this.endTimeNow = endTimeNow;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
	public Date getRestratTime() {
		return restratTime;
	}

	public void setRestratTime(Date restratTime) {
		this.restratTime = restratTime;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 覆盖toString方法，目的显示所有JavaBean的属性值，省略写很多的getXxx的方法
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
