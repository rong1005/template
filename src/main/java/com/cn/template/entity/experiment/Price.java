package com.cn.template.entity.experiment;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;

/**
 * 实验收费明细信息.
 * 
 * @author Libra
 *
 */
@Entity
@Table(name = "tb_price")
public class Price extends IdEntity {

	/** 使用的设备 */
	private EquipmentType equipmentType;

	/** 实验项目 */
	private String project;

	/** 开机费 */
	private Double openPrice;

	/** 电费 */
	private Double electricPrice;

	/** 折旧费 */
	private Double depreciation;
	
	/** 计算公式 */
	private String formula;

	/** 备注 */
	private String description;

	@ManyToOne
	@JoinColumn(name = "equipment_type_id")
	public EquipmentType getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(EquipmentType equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public Double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}

	public Double getElectricPrice() {
		return electricPrice;
	}

	public void setElectricPrice(Double electricPrice) {
		this.electricPrice = electricPrice;
	}

	public Double getDepreciation() {
		return depreciation;
	}

	public void setDepreciation(Double depreciation) {
		this.depreciation = depreciation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

}
