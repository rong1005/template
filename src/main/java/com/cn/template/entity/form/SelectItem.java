package com.cn.template.entity.form;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;
import com.cn.template.xutil.enums.Whether;

/**
 * 字段选择项
 * 
 * @author Libra
 *
 */
@Entity
@Table(name = "tb_select_item")
public class SelectItem extends IdEntity {

	/** 对应的字段 */
	private Field field;

	/** 选择项中文名 */
	private String chItemName;

	/** 选择项英文名 */
	private String enItemName;

	/** 是否默认选项 */
	private Whether isdefault;

	/** 排序 */
	private Integer showOrder;

	@ManyToOne
	@JoinColumn(name = "field_id")
	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getChItemName() {
		return chItemName;
	}

	public void setChItemName(String chItemName) {
		this.chItemName = chItemName;
	}

	public String getEnItemName() {
		return enItemName;
	}

	public void setEnItemName(String enItemName) {
		this.enItemName = enItemName;
	}

	@Enumerated(EnumType.ORDINAL)
	public Whether getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(Whether isdefault) {
		this.isdefault = isdefault;
	}

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

}
