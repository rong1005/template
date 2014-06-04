package com.cn.template.xutil.weixin;

/**
 * 微信接口相关的常量.
 * 调用：<code>WeixinConstants.常量</code>
 * @author Libra
 *
 */
public class WeixinConstants {
	
	public static final String TOKEN = "libra_live";
	
	public static final String APPID="wx43bf59c700c031d4";
	
	public static final String SECRET="4f3b94e36419c4d566c1f9ff5678c782";
	
	
	/** 获取微信tooken的接口 */
	public static final String GET_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	
	/** 获取菜单信息的接口 */
	public static final String GET_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/get?access_token=%s";
	
	/** 创建菜单信息的接口 */
	public static final String CREATE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";
	
	/** 获取用户基本信息的接口 */
	public static final String GET_USER_URL="https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
	
	/** 发送客服消息的接口 */
	public static final String SENT_CUSTOM_MESSAGE_URL="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";
	
	/** 生成二维码 */
	public static final String CREATE_QRCODE_URL="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s";
	
	/** 通过code换取网页授权access_token */
	public static final String GET_OAUTH2_URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+APPID+"&secret="+SECRET+"&code=%s&grant_type=authorization_code";
	

}
