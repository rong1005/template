package com.cn.template.entity.experiment;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * 设备类型.
 * 
 * @author Libra
 *
 */
@Entity
@Table(name = "tb_equipment_type")
public class EquipmentType extends IdEntity{
	
	/** 设备名称 */
	private String name;

	/** 设备描述 */
	private String description;
	
	/** 对应拥有的设备 */
	private List<Equipment> equipments;

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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "equipmentType")
	@OrderBy(value = "id ASC")
	@JsonIgnore
	public List<Equipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
	}

}
