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
	/** 实验室审核 */
	AUDITING("实验室审核", 1),
	/** 客户确认 */
	CONFIRM("客户确认", 2),
	/** 实验排期 */
	SCHEDULE("实验排期", 3),
	/** 实验进行 */
	BE_IN_PROGRESS("实验进行",4),
	/** 实验完成 */
	FINISH("实验完成",5),
	/** 实验取消 */
	CANCEL("实验取消",6),
	/** 实验终止 */
	STOP("实验终止",7),
	/** 实验查看 */
	BROWSE("实验查看",8)
	;

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
