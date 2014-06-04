package com.cn.template.xutil.weixin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.cn.template.entity.weixin.NewsMessage;
import com.cn.template.entity.weixin.WeixinMenu;
import com.cn.template.entity.weixin.WeixinUser;
import com.cn.template.modules.test.category.UnStable;
import com.cn.template.modules.test.spring.SpringContextTestCase;
import com.cn.template.repository.weixin.WeixinMenuDao;
import com.cn.template.service.weixin.NewsMessageService;
import com.cn.template.xutil.Utils;
import com.cn.template.xutil.enums.Whether;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 微信接口对接开发测试.
 * @author Libra
 *
 */
@Category(UnStable.class)
@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class WeixinTest extends SpringContextTestCase {

	private WeixinMenuDao weixinMenuDao; 
	
	private NewsMessageService newsMessageService;
	
	/** 自定义菜单查询. */
	@Test
	@Ignore
	public void findWeixinMenu() {
		try{	
			URL weixinMenu_url = new URL(String.format(WeixinConstants.GET_MENU_URL, AccessTokenUtil.getAccessToken(false)));
			BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(weixinMenu_url.openStream()));
			WeixinMenu weixinMenu=new WeixinMenu();
			weixinMenu.setCreateTime(new Date());
			weixinMenu.setUpdateTime(new Date());
			weixinMenu.setName("自定义菜单");
			weixinMenu.setIsUsing(Whether.YES);
			weixinMenu.setDescription(bufferedReader.readLine());
			weixinMenuDao.save(weixinMenu);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/** 生成二维码 */
	@Test
	@Ignore
	public void createCode(){
		try{
			String content="{\"expire_seconds\": 1800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123456}}}";
			String inString = Utils.contentPublish(String.format(WeixinConstants.CREATE_QRCODE_URL, AccessTokenUtil.getAccessToken(false)), content);
			System.out.println(inString);
			Gson gson =new Gson();
			Map map = gson.fromJson(inString, Map.class);
			System.err.println(map.get("ticket"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/** 图文消息的生成，FreeMark模板解析. */
	@Test
	@Ignore
	public void newsMessage(){
		try{
		List<NewsMessage> newsMessageList = newsMessageService.getAllNewsMessage();
		if(newsMessageList.size()>6){
			newsMessageList = newsMessageList.subList(0, 5);
		}
		Map<String,Object> map=Maps.newHashMap();
		map.put("openid", "222222");
		map.put("path", "http://localhost:8080/template");
		map.put("messages", newsMessageList);
		
		FreeMarkerConfigurationFactory freeMarkerConfigurationFactory=new FreeMarkerConfigurationFactory();
		freeMarkerConfigurationFactory.setTemplateLoaderPath("classpath:/freemarker");
		
		Configuration configuration=freeMarkerConfigurationFactory.createConfiguration();
		Template template=configuration.getTemplate("newsMessage.ftl", "utf-8");
		String messages = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
		System.out.println(messages);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/** 提取微信用户信息. */
	@Test
	public void getWeixinUser() {
		try{
		URL get_user_url = new URL(String.format(WeixinConstants.GET_USER_URL, AccessTokenUtil.getAccessToken(true), "o7Chyt0jbuYPa5AWGDQ-Ttbk2gGU"));
		Reader reader = new InputStreamReader(get_user_url.openStream(),"UTF-8");
		Gson gson = new Gson();
		WeixinUser weixinUser = gson.fromJson(reader, WeixinUser.class);
		reader.close();
		//如果取得的微信用户有误（AccessToken超时等），重置AccessToken，重新获取
		if (weixinUser.getErrcode() != null
				&&weixinUser.getOpenid()==null
				&& weixinUser.getErrcode() == 40001
				&& weixinUser.getErrcode() == 40014
				&& weixinUser.getErrcode() == 42001) {
			
			
		}
		System.out.println("-----------"+weixinUser.toString());
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	

	@Autowired
	public void setWeixinMenuDao(WeixinMenuDao weixinMenuDao) {
		this.weixinMenuDao = weixinMenuDao;
	}

	@Autowired
	public void setNewsMessageService(NewsMessageService newsMessageService) {
		this.newsMessageService = newsMessageService;
	}

}
