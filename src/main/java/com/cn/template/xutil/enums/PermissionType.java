package com.cn.template.xutil.enums;

/**
 * 授权权限类型.
 * 
 * @author Libra
 *
 */
public enum PermissionType {
	/** 拒绝 */
	REJECT("拒绝", 0),
	/** 只读 */
	READ_ONLY("只读", 1),
	/** 读写 */
	READ_WRITE("读写",2);

	private String value;
	private int index;

	/**
	 * 构造赋值.
	 * 
	 * @param value
	 * @param index
	 */
	private PermissionType(String value, int index) {
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
		for (PermissionType c : PermissionType.values()) {
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
