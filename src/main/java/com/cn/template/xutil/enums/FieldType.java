package com.cn.template.xutil.enums;

/**
 * 字段类型.
 * 
 * @author Libra
 *
 */
public enum FieldType {
	/** 字符串 */
	VARCHAR("字符串", "varchar", 0),
	/** 文本 */
	TEXT("文本", "text", 1),
	/** 整数 */
	INT("整数", "int", 2),
	/** 小数 */
	DOUBLE("小数", "double", 3),
	/** 下拉列表框 */
	SELECT("下拉列表框", "varchar", 4),
	/** 单选框 */
	RADIO("单选框", "varchar", 5),
	/** 多选框 */
	CHECKBOX("多选框", "varchar", 6);

	private String value;
	private String type;
	private int index;

	/**
	 * 构造赋值.
	 * 
	 * @param value
	 * @param index
	 */
	private FieldType(String value, String type, int index) {
		this.value = value;
		this.type = type;
		this.index = index;
	}

	/**
	 * 取得枚举量的名称.
	 * 
	 * @param index
	 * @return
	 */
	public static String getName(int index) {
		for (FieldType c : FieldType.values()) {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
