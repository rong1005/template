package com.cn.template.entity.form;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;
import com.cn.template.xutil.enums.FieldInputType;
import com.cn.template.xutil.enums.FieldType;

/**
 * 字段信息.
 * 
 * @author Libra
 *
 */
@Entity
@Table(name = "tb_field")
public class Field extends IdEntity {

	/** 对应的表单 */
	private Form form;

	/** 字段名称 */
	private String name;

	/** 字段输入类型 */
	private FieldInputType fieldInputType;

	/** 字段类型 */
	private FieldType fieldType;

	/** 长度 */
	private Integer fieldLength;

	/** 精度 */
	private Integer fieldPrecision;

	/** 中文显示名称 */
	private String chViewName;

	/** 英文显示名称 */
	private String enViewName;

	/** 排序 */
	private Integer showOrder;
	
	/** 中文默认值 */
	private String chDefaultValue;
	
	/** 英文默认值 */
	private String enDefaultValue;
	
	/** 文本格式 */
	private String testPattern;
	
	/** 字段对应的选择项内容 */
	private List<SelectItem> selectItems; 
	

	@ManyToOne
	@JoinColumn(name = "form_id")
	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated(EnumType.ORDINAL)
	public FieldInputType getFieldInputType() {
		return fieldInputType;
	}

	public void setFieldInputType(FieldInputType fieldInputType) {
		this.fieldInputType = fieldInputType;
	}

	@Enumerated(EnumType.ORDINAL)
	public FieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}

	public Integer getFieldLength() {
		return fieldLength;
	}

	public void setFieldLength(Integer fieldLength) {
		this.fieldLength = fieldLength;
	}

	public Integer getFieldPrecision() {
		return fieldPrecision;
	}

	public void setFieldPrecision(Integer fieldPrecision) {
		this.fieldPrecision = fieldPrecision;
	}

	public String getChViewName() {
		return chViewName;
	}

	public void setChViewName(String chViewName) {
		this.chViewName = chViewName;
	}

	public String getEnViewName() {
		return enViewName;
	}

	public void setEnViewName(String enViewName) {
		this.enViewName = enViewName;
	}

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public String getChDefaultValue() {
		return chDefaultValue;
	}

	public void setChDefaultValue(String chDefaultValue) {
		this.chDefaultValue = chDefaultValue;
	}

	public String getEnDefaultValue() {
		return enDefaultValue;
	}

	public void setEnDefaultValue(String enDefaultValue) {
		this.enDefaultValue = enDefaultValue;
	}

	public String getTestPattern() {
		return testPattern;
	}

	public void setTestPattern(String testPattern) {
		this.testPattern = testPattern;
	}

	@OneToMany(cascade=CascadeType.ALL, mappedBy="field")
	@OrderBy(value = "showOrder ASC")
	public List<SelectItem> getSelectItems() {
		return selectItems;
	}

	public void setSelectItems(List<SelectItem> selectItems) {
		this.selectItems = selectItems;
	}

}
