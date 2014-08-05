package com.cn.template.xutil.enums;

/**
 * 样品状态.
 * 
 * @author Libra
 *
 */
public enum SampleStatus {
	/** 待接收 */
	WAIT_RECEIVE("待接收", 0),
	/** 已接收 */
	RECEIVED("已接收", 1),
	/** 已排期 */
	RESCHEDULING("已排期", 2),
	/** 实验中 */
	EXPERIMENT("实验中", 3),
	/** 实验结束 */
	EXPERIMENT_END("实验结束", 4),
	/** 归还 */
	SEND_BACK("归还", 5),
	/** 报废 */
	USELESS("报废", 6);

	private String value;
	private int index;

	/**
	 * 构造赋值.
	 * 
	 * @param value
	 * @param index
	 */
	private SampleStatus(String value, int index) {
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
		for (SampleStatus c : SampleStatus.values()) {
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
