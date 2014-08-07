package com.cn.template.xutil.enums;

/**
 * 异常处理类型.
 * 
 * @author Libra
 *
 */
public enum ExceptionHandleType {
	/** 继续 */
	CONTINUAL("继续", 0),
	/** 暂停 */
	SUSPEND("暂停", 1),
	/** 终止 */
	STOP("终止", 2),
	/** 转移 */
	CHANGE("转移", 3);

	private String value;
	private int index;

	/**
	 * 构造赋值.
	 * 
	 * @param value
	 * @param index
	 */
	private ExceptionHandleType(String value, int index) {
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
		for (ExceptionHandleType c : ExceptionHandleType.values()) {
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
