package com.cn.template.xutil.enums;

/**
 * 实验结果.
 * 
 * @author Libra
 *
 */
public enum ExperimentResult {
	/** 失败 */
	FAIL("失败", "fail", 0),
	/** 成功 */
	SUCCESS("成功", "success", 1);

	private String value;
	private String enValue;
	private int index;

	/**
	 * 构造赋值.
	 * 
	 * @param value
	 * @param index
	 */
	private ExperimentResult(String value, String enValue, int index) {
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
		for (ExperimentResult c : ExperimentResult.values()) {
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
