package com.cn.template.xutil.weixin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.gson.Gson;

/**
 * access_token的工具类，取得token，将token保存到缓存中.
 * @author Libra
 *
 */
public class AccessTokenUtil {
	private static Logger logger = LoggerFactory.getLogger(AccessTokenUtil.class);
	
	private static final Map<String,String> tokencache = Maps.newHashMap();
	
	/** 
	 * 获得缓存中的access_token.
	 * @return
	 */
	public static Map<String, String> getTokencache() {
		return tokencache;
	}


	/**
	 * 获取AccessToken
	 * @param reset 如果reset=true 强制重新获取access_token
	 * @return
	 * @throws IOException
	 */
	public synchronized static String getAccessToken(boolean reset) throws IOException {
		logger.info("判断条件 -- " +
				"!reset:{} , " +
				"!tokencache!=null:{} , " +
				"tokencache.get(access_token)!=null:{} , " +
				"tokencache.get(expires_in)!=null:{} , " +
				"tokencache.get(access_time)!=null:{} , " ,!reset,tokencache!=null,tokencache.get("access_token")!=null,tokencache.get("expires_in")!=null,tokencache.get("access_time")!=null);
		
		if(!reset&&tokencache!=null&&tokencache.get("access_token")!=null&&tokencache.get("expires_in")!=null&&tokencache.get("access_time")!=null){
			Long expiresIn=Long.parseLong(tokencache.get("expires_in"));
			Long accessTime=Long.parseLong(tokencache.get("access_time"));
			logger.info("时间判断 -- expiresIn*1000：{} ， new Date().getTime()-accessTime ：{}",expiresIn*1000 ,new Date().getTime()-accessTime );
			//access_token的有效时间为7200秒，时间的计算是毫秒，所有*1000
			if(expiresIn*1000 > new Date().getTime()-accessTime ){
				logger.info("读取缓存：{}",tokencache.get("access_token"));
				return tokencache.get("access_token");
			}
		}
		
		logger.info("--------------------重新读取------------------------");
		URL access_token_url = new URL(String.format(WeixinConstants.GET_TOKEN_URL, WeixinConstants.APPID, WeixinConstants.SECRET));  
		//解决提取tooken时出现的一个未知问题(暂未了解详细原因，待分析)
		System.setProperty ("jsse.enableSNIExtension", "false"); 
		Reader reader = new InputStreamReader(access_token_url.openStream());
	    Gson gson = new Gson();
	    //取得access_token ,有效期为7200秒；多处调用用到.
	    AccessToken accessToken = gson.fromJson(reader, AccessToken.class);
	    reader.close();
	    tokencache.put("access_token", accessToken.getAccess_token());
	    tokencache.put("expires_in", accessToken.getExpires_in());
	    tokencache.put("access_time", new Date().getTime()+"");
	    logger.info("重新读取：{}",tokencache.get("access_token"));
	    return accessToken.getAccess_token();
	}
}
