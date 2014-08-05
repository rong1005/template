package com.cn.template.xutil.enums;

/**
 * 巡检记录类型.
 * 
 * @author Libra
 *
 */
public enum RecordType {
	/** 其它试验 */
	OTHER("其它试验", 0),
	/** 功率试验(双通道功放) */
	TWO_POWER("功率试验(双通道功放)", 1),
	/** 功率试验(四通道功放) */
	FOUR_POWER("功率试验(四通道功放)", 2),
	/** 功率试验(四通道功放) */
	ENVIRONMENT("环境试验", 3),
	/** UV试验 */
	UV("UV试验", 4);

	private String value;
	private int index;

	/**
	 * 构造赋值.
	 * 
	 * @param value
	 * @param index
	 */
	private RecordType(String value, int index) {
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
		for (RecordType c : RecordType.values()) {
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
