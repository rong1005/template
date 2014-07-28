package com.cn.template.xutil.enums;

/**
 * 实验委托申请的状态.
 * 
 * @author Libra
 *
 */
public enum ApplyStatus {
	/** 申请 */
	REQUEST("申请", 0),
	/** 审核通过 */
	PASS("审核通过", 1),
	/** 否决 */
	REJECT("否决",2);

	private String value;
	private int index;

	/**
	 * 构造赋值.
	 * 
	 * @param value
	 * @param index
	 */
	private ApplyStatus(String value, int index) {
		this.value = value;
		this.index = index;
	}

	/**
	 * 取得枚举量的名称.
	 * 
	 * @param index
	 * @return
	 */
	public static String getName(int index) {
		for (ApplyStatus c : ApplyStatus.values()) {
			if (c.getIndex() == index) {
				return c.value;
			}
		}
		return null;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
