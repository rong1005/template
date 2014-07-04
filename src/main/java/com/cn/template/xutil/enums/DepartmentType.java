package com.cn.template.xutil.enums;

/**
 * 公司类型
 * 
 * @author Libra
 *
 */
public enum DepartmentType {
	/** 集团 */
	GROUP("集团", 0),
	/** 公司 */
	COMPANY("公司", 1),
	/** 部门 */
	DEPARTMENT("部门", 2)
	;

	private String value;
	private int index;

	/**
	 * 构造赋值.
	 * 
	 * @param value
	 * @param index
	 */
	private DepartmentType(String value, int index) {
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
		for (DepartmentType c : DepartmentType.values()) {
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
