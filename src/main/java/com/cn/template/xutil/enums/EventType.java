package com.cn.template.xutil.enums;

/**
 * 微信消息事件类型.
 * 
 * @author Libra
 *
 */
public enum EventType {
	/** 取消订阅 */
	UNSUBSCRIBE("unsubscribe", 0),
	/** 订阅 */
	SUBSCRIBE("subscribe", 1),
	/** 文字 */
	TEXT("text", 2),
	/** 菜单点击 */
	CLICK("CLICK", 3),
	/** 二维码扫描 */
	SCAN("SCAN", 4),
	/** 客服文本消息 */
	CUSTOM_TEXT("CUSTOM_TEXT",5)
	;

	private String value;
	private int index;

	/**
	 * 构造赋值.
	 * 
	 * @param value
	 * @param index
	 */
	private EventType(String value, int index) {
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
		for (EventType c : EventType.values()) {
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
