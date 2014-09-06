package com.cn.template.xutil;

/**
 * 系统常量.
 * 调用：<code>Constants.常量</code>
 * @author Libra
 *
 */
public class Constants {
	
	public static final String CONTEXT_PATH="/template";
	
	/** 编码格式UTF8 */
	public static final String UTF8 = "UTF-8";
	
	/** 项目的物理路径,通常使用在文件保存中. 
	 * System.getProperty("webroot.path") 通过配置监听获取项目的物理路径（解决Windows与Linux系统路径的问题）.
	 * */
	public static final String WEBROOT=System.getProperty("webroot.path");
	
	
	/** 散列算法 */
	public static final String HASH_ALGORITHM = "SHA-1";
	
	/** 散列精度 */
	public static final int HASH_INTERATIONS = 1024;
	
	/** 盐值长度 */
	public static final int SALT_SIZE = 8;
	
	
	
	/** 分页参数,每3列一页 */
	public static final String PAGE_SIZE_3 = "3";
	
	/** 分页参数,每5列一页 */
	public static final String PAGE_SIZE_5 = "5";
	
	/** 分页参数,每10列一页 */
	public static final String PAGE_SIZE_10 = "10";
	
	
	
	/** 日期格式 ：yyyy-MM-dd HH:mm:SS */
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:SS";
	
	/** 日期格式 ：yyyy-MM-dd HH:mm */
	public static final String DATETIME_MIN_FORMAT = "yyyy-MM-dd HH:mm";
	
	/** 日期格式 ：yyyy-MM-dd */
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	/** 日期格式 ：yyyyMMdd */
	public static final String DATE_FORMAT_SHORT = "yyyyMMdd";
	
	/** 日期格式 ：HH:mm:ss */
	public static final String TIME_FORMAT = "HH:mm:ss";
	
	/** 日期格式数组，包含所有定义的日期格式. */
	public static final String[] FORMAT_PATTERNS = { DATETIME_FORMAT, DATETIME_MIN_FORMAT, DATE_FORMAT, DATE_FORMAT_SHORT, TIME_FORMAT };
	
	
	/** 文件位置 */
	public static final String FILESITE = "static/attachments";
	
	
	/** UEditor配置文件位置 */
	public static final String UEDITOR_CONFIG_PATH = "/static/js/ueditor/jsp/config.json";

	


}
