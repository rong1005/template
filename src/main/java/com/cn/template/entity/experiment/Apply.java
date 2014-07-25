package com.cn.template.entity.experiment;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;
import com.cn.template.entity.form.Form;
import com.cn.template.xutil.enums.ApplyStatus;

/**
 * 实验委托申请信息.
 * 
 * @author Libra
 *
 */
@Entity
@Table(name = "tb_apply")
public class Apply extends IdEntity {

	/** 所属的表单类型 */
	private Form form;
	
	/** 委托名称 中文 */
	private String chApplyName;
	
	/** 委托名称 英语 */
	private String enApplyName;
	
	/** 委托单位 中文 */
	private String chConsigner;
	
	/** 委托单位 英语 */
	private String enConsigner;
	
	/** 检验项目 中文 */
	private String chTestItems;
	
	/** 检验项目 英文 */
	private String enTestItems;
	
	/** 检验类别 中文 */
	private String chCheckType;
	
	/** 检验类别 英文 */
	private String enCheckType;
	
	/** 申请的状态 */
	private ApplyStatus applyStatus;

	@ManyToOne
	@JoinColumn(name="form_id")
	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	@Enumerated(EnumType.ORDINAL)
	public ApplyStatus getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(ApplyStatus applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getChApplyName() {
		return chApplyName;
	}

	public void setChApplyName(String chApplyName) {
		this.chApplyName = chApplyName;
	}

	public String getEnApplyName() {
		return enApplyName;
	}

	public void setEnApplyName(String enApplyName) {
		this.enApplyName = enApplyName;
	}

	public String getChConsigner() {
		return chConsigner;
	}

	public void setChConsigner(String chConsigner) {
		this.chConsigner = chConsigner;
	}

	public String getEnConsigner() {
		return enConsigner;
	}

	public void setEnConsigner(String enConsigner) {
		this.enConsigner = enConsigner;
	}

	public String getChTestItems() {
		return chTestItems;
	}

	public void setChTestItems(String chTestItems) {
		this.chTestItems = chTestItems;
	}

	public String getEnTestItems() {
		return enTestItems;
	}

	public void setEnTestItems(String enTestItems) {
		this.enTestItems = enTestItems;
	}

	public String getChCheckType() {
		return chCheckType;
	}

	public void setChCheckType(String chCheckType) {
		this.chCheckType = chCheckType;
	}

	public String getEnCheckType() {
		return enCheckType;
	}

	public void setEnCheckType(String enCheckType) {
		this.enCheckType = enCheckType;
	}

}
