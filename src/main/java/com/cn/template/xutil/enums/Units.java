package com.cn.template.xutil.enums;

/**
 * 判断，用于是非的判断.
 * 
 * @author Libra
 *
 */
public enum Units {
	/** 只 */
	PCS("只", "PCS", 0),
	/** 箱 */
	BOX("箱", "BOX", 1),
	/** 套 */
	SYSTEMS("套", "Systems", 1);

	private String value;
	private String enValue;
	private int index;

	/**
	 * 构造赋值.
	 * 
	 * @param value
	 * @param index
	 */
	private Units(String value, String enValue, int index) {
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
		for (Units c : Units.values()) {
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
