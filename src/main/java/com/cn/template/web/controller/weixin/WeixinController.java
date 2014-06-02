package com.cn.template.web.controller.weixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cn.template.web.listener.jms.NotifyMessageProducer;
import com.cn.template.xutil.enums.EventType;
import com.cn.template.xutil.weixin.AccessTokenUtil;
import com.cn.template.xutil.weixin.ImageMessage;
import com.cn.template.xutil.weixin.NewsItem;
import com.cn.template.xutil.weixin.TextMessage;
import com.cn.template.xutil.weixin.WeixinConstants;

/**
 * 与微信接入业务处理的代理类.
 * @author Libra
 *
 */
@Controller
@RequestMapping(value = "/weixin")
public class WeixinController {
	
	private static Logger logger = LoggerFactory.getLogger(WeixinController.class);
	
	@Autowired
	private NotifyMessageProducer notifyMessageProducer;
	
	
	/**
     * 检查TOKEN，接入微信，验证称为微信开发者的方法.
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
	 * @throws IOException 
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public void checkToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	System.out.println("验证----------");
		// http://localhost:8080/gzdfbz/weinxin?signature=9ec3cd1a021d5ffe45324a9fd3aefdfaa77fb1b7&timestamp=2&nonce=3&echostr=4
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");

		String[] ArrTmp = { WeixinConstants.TOKEN, timestamp, nonce };
		Arrays.sort(ArrTmp);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ArrTmp.length; i++) {
			sb.append(ArrTmp[i]);
		}
		String pwd = EncryptUtil.Encrypt(sb.toString());
		String echostr = request.getParameter("echostr");
		if (pwd.equals(signature)) {
			if (!"".equals(echostr) && echostr != null) {
				response.getWriter().print(echostr);
			}
		}
    }

    /**
     * 接收微信消息.
     * @param request
     * @param response
     */
	@RequestMapping(method = RequestMethod.POST)
	public void reply(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("接收信息----------");
		
		String contextPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+""+request.getContextPath();
		 Document doc = null;
	     SAXReader reader = new SAXReader();
	     try {
	            PrintWriter out = response.getWriter();
	            InputStream in = request.getInputStream();
	            doc = reader.read(in);
	            Element root = doc.getRootElement();
	            logger.info("微信发送的XML内容：{}",root.asXML());
	            
	            String toUserName = root.element("ToUserName").getTextTrim();
	            String fromUserName = root.element("FromUserName").getTextTrim();
	            String msgType = root.element("MsgType").getTextTrim();
	            
	            //System.out.println("toUserName={"+toUserName+"}" );
	            System.out.println("fromUserName={"+fromUserName+"}" );
	            //System.out.println("CreateTime={"+root.element("CreateTime").getTextTrim()+"}" );
	            System.out.println("msgType={"+msgType+"}" );
	            //System.out.println("Content={"+root.element("Content").getTextTrim()+"}" );
	            //System.out.println("MsgId={"+root.element("MsgId").getTextTrim()+"}" );
	            
	            
	            if (StringUtils.equals(msgType, "text")) {
	            	String content = root.element("Content").getTextTrim();
	            	if (StringUtils.equals("文字", content )) {
	            		TextMessage message = new TextMessage();
	                    message.setToUserName(toUserName);
	                    message.setFromUserName(fromUserName);
	                    message.setCreateTime(System.currentTimeMillis());
	                    message.setContent("欢迎您加入‘国光在线’平台！");
	                    message.print(out);
	            	}else if (StringUtils.equals("图文信息",content)) {
	            		 ImageMessage message = new ImageMessage();
	                     message.setToUserName(toUserName);
	                     message.setFromUserName(fromUserName);
	                     message.setCreateTime(System.currentTimeMillis());

	                     NewsItem item = new NewsItem();
	                     item.setTitle("老罗，我们都准备好了，你呢？");
	                     item.setUrl("http://www.36kr.com/p/212096.html");
	                     item.setPicUrl("http://a.36krcnd.com/photo/2014/adf420abca15801e24d2a566134b998c.png");
	                     item.setDescription("大家准备好了吗？镇定一下。我们是一个科技界的聚会，不是曲艺活动是吧，所以希望大家镇定一下。我等这一天已经等了快 14 个月了，一直很希望冲出来跟大家聊一聊，因为对我个人来讲我一生做的所有的事情，都是为了攒到一个点上出来吹吹牛 X 的，就像很多人说的，他是一个用生命吹牛 X 的人。");
	                     message.getItems().add(item);

	                     item = new NewsItem();
	                     item.setTitle("“空气果”预约量破百万");
	                     item.setUrl("http://www.36kr.com/p/212106.html");
	                     item.setPicUrl("http://a.36krcnd.com/photo/2014/518f9fb1cc1a5c68cb6a563a50c35443.png");
	                     item.setDescription("PM2.5会成为未来人们在购房时考虑的第三因素");
	                     message.getItems().add(item);
	                     message.print(out);
		            }else{
		            	notifyMessageProducer.sendQueue(EventType.TEXT.getValue(),content,contextPath,fromUserName);
		            	out.print("");
		            }
	            }else if(StringUtils.equals(msgType, "event")){
	            	 String event = root.element("Event").getTextTrim();
	            	 if(StringUtils.equals(event, "subscribe")){
	            		 System.out.println("用户："+fromUserName+" 订阅");
	            		 TextMessage message = new TextMessage();
		                 message.setToUserName(toUserName);
		                 message.setFromUserName(fromUserName);
		                 message.setCreateTime(System.currentTimeMillis());
		                 message.setContent("“国光在线”是整合员工生活和工作信息的一体化查阅服务平台。包括：活动通知、行政消息、宿舍水电、饭堂菜色、班车、天气、通讯录等等。\n\n平台当前尚在开发中，我们会尽快为您提供更多、更好的功能。敬请期待！\n\n如果您有好的想法或建议，可以通过编辑 [建议#您要说的内容] 发送给我们。有您的支持，我们会做得更好！");
		                 notifyMessageProducer.sendQueue(event, fromUserName);
		                 message.print(out);
	            	 }else if(StringUtils.equals(event, "unsubscribe")){
	            		 System.out.println("用户："+fromUserName+" 退阅");
	            		 out.print("");
	            		 notifyMessageProducer.sendQueue(event, fromUserName);
	            	 }else if (StringUtils.equals(event, "CLICK")){
	            		 String eventKey = root.element("EventKey").getTextTrim();
	            		 notifyMessageProducer.sendQueue(event,eventKey,contextPath,fromUserName);
	            		 out.print("");
	            	 }else if (StringUtils.equals(event, "SCAN")){
	            		 logger.info("二维码的事件KEY值：{}",root.element("EventKey").getTextTrim());
	            		 String eventKey = root.element("EventKey").getTextTrim();
	            		 Long createTime = Long.parseLong(root.element("CreateTime").getTextTrim());
	            		 String ticket =root.element("Ticket").getTextTrim();
	            		 
	            		 notifyMessageProducer.sendQueue(event,eventKey,createTime,ticket,contextPath,fromUserName);
	            		 out.print("");
	            	 }

	            }
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	
	public static void main(String[] args) throws Exception {
	    //取得菜单信息.
	    URL menu_url = new URL(String.format(WeixinConstants.GET_MENU_URL, AccessTokenUtil.getAccessToken(false)));
	    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(menu_url.openStream()));
	    logger.info("菜单内容：{}",bufferedReader.readLine());
	    } 
}
