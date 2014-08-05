package com.cn.template.entity.experiment;

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
import com.cn.template.entity.form.Form;
import com.cn.template.xutil.enums.ApplyCheckType;
import com.cn.template.xutil.enums.ApplyStatus;
import com.cn.template.xutil.enums.Units;
import com.cn.template.xutil.enums.Whether;

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

	/** 申请流水号 */
	private String serialNumber;

	/** 委托名称 中文 */
	private String chApplyName;

	/** 委托名称 英语 */
	private String enApplyName;

	/** 委托单位 中文 */
	private String chConsigner;

	/** 委托单位 英语 */
	private String enConsigner;
	
	/** 委托人 中文 */
	private String chClient;
	
	/** 委托人 英文 */
	private String enClient;

	/** 检验项目 中文 */
	private String chTestItems;

	/** 检验项目 英文 */
	private String enTestItems;

	/** 检验类别 */
	private ApplyCheckType checkType;

	/** 检验类别 中文 */
	private String chCheckType;

	/** 检验类别 英文 */
	private String enCheckType;
	
	/** 样品名称 (中文) */
	private String chSampleName;
	
	/** 样品名称 (英文) */
	private String enSampleName;
	
	/** 样品型号 */
	private String sampleModel;
	
	/** 样品数量 */
	private Integer sampleNumber;
	
	/** 数量单位 */
	private Units units; 
	
	/** 数量单位 中文 */
	private String chUnits;
	
	/** 数量单位 英文 */
	private String enUnits;

	/** 申请的状态 */
	private ApplyStatus applyStatus;
	
	/** 是否通过 */
	private Whether isPass;

	/** 申请的备注记录信息 */
	private List<ApplyRemark> remarks;
	
	/** 费用清单 */
	private List<ApplyPrice> applyPrices;
	
	/** 样品信息. */
	private List<Sample> samples;

	@ManyToOne
	@JoinColumn(name = "form_id")
	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Enumerated(EnumType.ORDINAL)
	public ApplyStatus getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(ApplyStatus applyStatus) {
		this.applyStatus = applyStatus;
	}

	/**
	 * 将Mybatis取得的数字转换为枚举量.
	 * 
	 * @param applyStatus
	 */
	public void setApplyStatusEnum(Integer applyStatus) {
		if (applyStatus != null) {
			this.applyStatus = ApplyStatus.values()[applyStatus];
		}
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

	@Enumerated(EnumType.ORDINAL)
	public ApplyCheckType getCheckType() {
		return checkType;
	}

	public void setCheckType(ApplyCheckType checkType) {
		this.checkType = checkType;
	}

	/**
	 * 将Mybatis取得的数字转换为枚举量
	 * 
	 * @param checkType
	 */
	public void setCheckTypeEnum(Integer checkType) {
		if (checkType != null) {
			this.checkType = ApplyCheckType.values()[checkType];
		}
	}

	@OneToMany(cascade=CascadeType.ALL, mappedBy="apply")
	@OrderBy(value = "id ASC")
	public List<ApplyRemark> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<ApplyRemark> remarks) {
		this.remarks = remarks;
	}

	public String getChClient() {
		return chClient;
	}

	public void setChClient(String chClient) {
		this.chClient = chClient;
	}

	public String getEnClient() {
		return enClient;
	}

	public void setEnClient(String enClient) {
		this.enClient = enClient;
	}

	public String getChSampleName() {
		return chSampleName;
	}

	public void setChSampleName(String chSampleName) {
		this.chSampleName = chSampleName;
	}

	public String getEnSampleName() {
		return enSampleName;
	}

	public void setEnSampleName(String enSampleName) {
		this.enSampleName = enSampleName;
	}

	public String getSampleModel() {
		return sampleModel;
	}

	public void setSampleModel(String sampleModel) {
		this.sampleModel = sampleModel;
	}

	public Integer getSampleNumber() {
		return sampleNumber;
	}

	public void setSampleNumber(Integer sampleNumber) {
		this.sampleNumber = sampleNumber;
	}

	@Enumerated(EnumType.ORDINAL)
	public Units getUnits() {
		return units;
	}

	public void setUnits(Units units) {
		this.units = units;
	}

	public String getChUnits() {
		return chUnits;
	}

	public void setChUnits(String chUnits) {
		this.chUnits = chUnits;
	}

	public String getEnUnits() {
		return enUnits;
	}

	public void setEnUnits(String enUnits) {
		this.enUnits = enUnits;
	}

	@Enumerated(EnumType.ORDINAL)
	public Whether getIsPass() {
		return isPass;
	}

	public void setIsPass(Whether isPass) {
		this.isPass = isPass;
	}

	@OneToMany(cascade=CascadeType.ALL, mappedBy="apply")
	@OrderBy(value = "id ASC")
	public List<ApplyPrice> getApplyPrices() {
		return applyPrices;
	}

	public void setApplyPrices(List<ApplyPrice> applyPrices) {
		this.applyPrices = applyPrices;
	}

	@OneToMany(cascade=CascadeType.ALL, mappedBy="apply")
	@OrderBy(value = "id ASC")
	public List<Sample> getSamples() {
		return samples;
	}

	public void setSamples(List<Sample> samples) {
		this.samples = samples;
	}
	
	
	
}
