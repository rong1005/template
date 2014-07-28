package com.cn.template.xutil.enums;

/**
 * 实验委托申请的检验类别.
 * 
 * @author Libra
 *
 */
public enum ApplyCheckType {
	/** 内部委托 */
	Interior("内部委托","Interior Application", 0),
	/** 外部委托 */
	Exterior("外部委托","Exterior Application", 1);

	private String value;
	private String enValue;
	private int index;

	/**
	 * 构造赋值.
	 * 
	 * @param value
	 * @param index
	 */
	private ApplyCheckType(String value, String enValue, int index) {
		this.value = value;
		this.enValue = enValue;
		this.index = index;
	}

	/**
	 * 取得枚举量的名称.
	 * 
	 * @param index
	 * @return
	 */
	public static String getName(int index) {
		for (ApplyCheckType c : ApplyCheckType.values()) {
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

	public String getEnValue() {
		return enValue;
	}

	public void setEnValue(String enValue) {
		this.enValue = enValue;
	}
	
}
