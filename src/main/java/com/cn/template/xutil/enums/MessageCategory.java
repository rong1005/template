package com.cn.template.xutil.enums;

/**
 * 消息分类，主要是响应不同的微信事件.
 * 
 * @author Libra
 *
 */
public enum MessageCategory {
	/** 公司简介 */
	COMPANY_PROFILE("公司简介", 0),
	/** 技术专刊 */
	TECHNICAL_MONOGRAPH("技术专刊", 1),
	/** 国光电子报 */
	EPAPER("国光电子报",2),
	/** 主要产品 */
	MAJOR_PRODUCT("主要产品",3),
	/** 新闻资讯 */
	NEWS_INFORMATION("新闻资讯",4),
	/** 个人信息 */
	PERSONAL_INFORMATION("个人信息",5),
	/** 班车信息 */
	REGULAR_BUS("班车信息",6)
	;
	
	private String value;
	private int index;

	/**
	 * 构造赋值.
	 * 
	 * @param value
	 * @param index
	 */
	private MessageCategory(String value, int index) {
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
		for (MessageCategory c : MessageCategory.values()) {
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
