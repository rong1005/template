package com.cn.template.entity.form;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;
import com.cn.template.xutil.enums.FormFormat;

/**
 * 表单信息
 * 
 * @author Libra
 *
 */
@Entity
@Table(name = "tb_form")
public class Form extends IdEntity {

	/** 表单名称 */
	private String name;

	/** 表单说明 */
	private String description;

	/** 表单格式 */
	private FormFormat formFormat;

	/** 中文表名 */
	private String tableName;
	
	/** 表单中包含的字段 */
	private List<Field> fields;

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

	public FormFormat getFormFormat() {
		return formFormat;
	}

	@Enumerated(EnumType.ORDINAL)
	public void setFormFormat(FormFormat formFormat) {
		this.formFormat = formFormat;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@OneToMany(cascade=CascadeType.ALL, mappedBy="form")
	@OrderBy(value = "showOrder ASC")
	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
}
