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
import com.cn.template.xutil.enums.RecordType;
import com.cn.template.xutil.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 设备信息(逐个).
 * 
 * @author Libra
 *
 */
@Entity
@Table(name = "tb_equipment")
public class Equipment extends IdEntity {

	/** 设备类型 */
	private EquipmentType equipmentType;
	
	/** 设备名称 */
	private String name;

	/** 设备描述 */
	private String description;
	
	/** 型号 */
	private String modelNumber;
	
	/** 编号 */
	private String serialNumber;
	
	/** 有效期 开始 */
	private Date dateStart;
	
	/** 有效期 结束 */
	private Date dateEnd;
	
	/** 设备图片路径 */
	private String pictureUrl;
	
	/** 设备状态 */
	private Status status;
	
	/** 巡检记录类型 */
	private RecordType recordType;

	@ManyToOne
	@JoinColumn(name="equipment_type_id")
	public EquipmentType getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(EquipmentType equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Enumerated(EnumType.ORDINAL)
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * 'Temporal' 定义时间保存的格式
	 * 'DateTimeFormat' 普通输出格式
	 * 'JsonFormat' JSON输出的格式
	 * @return
	 */
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	/**
	 * 'Temporal' 定义时间保存的格式
	 * 'DateTimeFormat' 普通输出格式
	 * 'JsonFormat' JSON输出的格式
	 * @return
	 */
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	@Enumerated(EnumType.ORDINAL)
	public RecordType getRecordType() {
		return recordType;
	}

	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}
	
	
}
