package com.cn.template.entity.experiment;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cn.template.entity.IdEntity;

/**
 * 委托实验的费用明细.
 * @author Libra
 *
 */
@Entity
@Table(name = "tb_apply_price")
public class ApplyPrice extends IdEntity {
	
	/** 所属申请 */
	private Apply apply;
	
	/**费用 */
	private Price price;
	
	/** 小时/小时数 */
	private Double usedHour;
	
	/** 次数/数量 */
	private Double usedTimes;
	
	/** 总费用 */
	private Double totalPrice;

	
	@ManyToOne
	@JoinColumn(name="apply_id")
	public Apply getApply() {
		return apply;
	}

	public void setApply(Apply apply) {
		this.apply = apply;
	}

	@ManyToOne
	@JoinColumn(name="price_id")
	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public Double getUsedHour() {
		return usedHour;
	}

	public void setUsedHour(Double usedHour) {
		this.usedHour = usedHour;
	}

	public Double getUsedTimes() {
		return usedTimes;
	}

	public void setUsedTimes(Double usedTimes) {
		this.usedTimes = usedTimes;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
