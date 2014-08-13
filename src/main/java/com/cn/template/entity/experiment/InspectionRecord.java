package com.cn.template.entity.experiment;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;
import com.cn.template.entity.authority.User;
import com.cn.template.xutil.enums.Whether;

/**
 * 巡检记录.
 * 
 * @author Libra
 *
 */
@Entity
@Table(name = "tb_inspection_record")
public class InspectionRecord extends IdEntity {

	/** 巡检人 */
	private User user;

	/** 设备 */
	private Equipment equipment;

	/** 是否存在异常 */
	private Whether isError;
	
	/** 异常是否处理 */
	private Whether isHandle;

	/** 描述 */
	private String description;

	/** 电压CH1 */
	private String ch1;

	/** 电压CH2 */
	private String ch2;

	/** 电压CH3 */
	private String ch3;

	/** 电压CH4 */
	private String ch4;

	/** 温度 */
	private String temperature;

	/** 湿度 */
	private String humidity;

	/** 光照强度CH1 */
	private String uv1;

	/** 光照强度CH2 */
	private String uv2;

	/** 光照强度CH3 */
	private String uv3;

	/** 光照强度CH4 */
	private String uv4;

	/** 仪器状态 */
	private String equipmentStatus;

	/** 样品状态 */
	private String sampleStatus;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name="equipment_id")
	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	@Enumerated(EnumType.ORDINAL)
	public Whether getIsError() {
		return isError;
	}

	public void setIsError(Whether isError) {
		this.isError = isError;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCh1() {
		return ch1;
	}

	public void setCh1(String ch1) {
		this.ch1 = ch1;
	}

	public String getCh2() {
		return ch2;
	}

	public void setCh2(String ch2) {
		this.ch2 = ch2;
	}

	public String getCh3() {
		return ch3;
	}

	public void setCh3(String ch3) {
		this.ch3 = ch3;
	}

	public String getCh4() {
		return ch4;
	}

	public void setCh4(String ch4) {
		this.ch4 = ch4;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getUv1() {
		return uv1;
	}

	public void setUv1(String uv1) {
		this.uv1 = uv1;
	}

	public String getUv2() {
		return uv2;
	}

	public void setUv2(String uv2) {
		this.uv2 = uv2;
	}

	public String getUv3() {
		return uv3;
	}

	public void setUv3(String uv3) {
		this.uv3 = uv3;
	}

	public String getUv4() {
		return uv4;
	}

	public void setUv4(String uv4) {
		this.uv4 = uv4;
	}

	public String getEquipmentStatus() {
		return equipmentStatus;
	}

	public void setEquipmentStatus(String equipmentStatus) {
		this.equipmentStatus = equipmentStatus;
	}

	public String getSampleStatus() {
		return sampleStatus;
	}

	public void setSampleStatus(String sampleStatus) {
		this.sampleStatus = sampleStatus;
	}

	@Enumerated(EnumType.ORDINAL)
	public Whether getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(Whether isHandle) {
		this.isHandle = isHandle;
	}

}
