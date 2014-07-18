package com.cn.template.xutil.enums;

/**
 * 字段输入类型.
 * 
 * @author Libra
 *
 */
public enum FieldInputType {
	/** 输入 */
	INPUT("输入", 0),
	/** 显示 */
	SHOW("显示", 1),
	/** 生成 */
	CREATE("生成",2);

	private String value;
	private int index;

	/**
	 * 构造赋值.
	 * 
	 * @param value
	 * @param index
	 */
	private FieldInputType(String value, int index) {
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
		for (FieldInputType c : FieldInputType.values()) {
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
