package com.cn.template.web.listener.jms;

import java.util.Map;

import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;

import com.google.common.collect.Maps;


/**
 * JMS用户变更消息生产者.
 * 
 * 使用jmsTemplate将用户变更消息分别发送到queue与topic.
 * 
 */
public class NotifyMessageProducer {

	private JmsTemplate jmsTemplate;
	private Destination notifyQueue;
	private Destination notifyTopic;

	/**
	 * 加入队列.
	 * @param event 事件
	 * @param openid 公众号的用户唯一标识
	 */
	public void sendQueue(String event,String openid) {
		sendMessage(event, openid, notifyQueue);
	}
	
	/**
	 * 加入队列.
	 * @param event 事件
	 * @param openid 公众号的用户唯一标识
	 * @param content 消息内容
	 */
	public void sendQueue(String event,String openid,String content) {
		sendMessage(event, openid, content, notifyQueue);
	}
	

	/**
	 * 加入队列.
	 * @param event 事件
	 * @param content 内容/用户输入的消息
	 * @param path 项目路径 
	 * @param openid 公众号的用户唯一标识
	 */
	public void sendQueue(String event,String content,String path,String openid) {
		sendMessage(event, content, path, openid, notifyQueue);
	}
	
	/**
	 * 加入队列.
	 * @param event 事件
	 * @param eventKey 事件键值
	 * @param createTime 发生时间
	 * @param ticket 票据
	 * @param path 项目路径
	 * @param openid 公众号的用户唯一标识
	 */
	public void sendQueue(String event,String eventKey,Long createTime,String ticket,String path,String openid) {
		sendMessage(event, eventKey, createTime,ticket,path, openid, notifyQueue);
	}

	/**
	 * 以一般规则加入JMS消息列表(一般使用队列).
	 * @param event 事件
	 * @param openid 公众号的用户唯一标识
	 */
	public void sendTopic(String event,String openid) {
		sendMessage(event, openid, notifyTopic);
	}

	/**
	 * 使用jmsTemplate最简便的封装convertAndSend()发送Map类型的消息.
	 * @param event
	 * @param openid
	 * @param destination
	 */
	private void sendMessage(String event,String openid, Destination destination) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("event", event);
		map.put("openid", openid);
		jmsTemplate.convertAndSend(destination, map);
	}
	
	/**
	 * 使用jmsTemplate最简便的封装convertAndSend()发送Map类型的消息.
	 * @param event
	 * @param openid
	 * @param destination
	 */
	private void sendMessage(String event, String openid, String content, Destination destination) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("event", event);
		map.put("openid", openid);
		map.put("content", content);
		jmsTemplate.convertAndSend(destination, map);
	}
	
	/**
	 * 使用jmsTemplate最简便的封装convertAndSend()发送Map类型的消息.
	 * @param event
	 * @param content
	 * @param contextPath
	 * @param openid
	 * @param destination
	 */
	private void sendMessage(String event,String content,String contextPath,String openid, Destination destination) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("event", event);
		map.put("content", content);
		map.put("contextPath", contextPath);
		map.put("openid", openid);
		jmsTemplate.convertAndSend(destination, map);
	}
	
	/**
	 * 使用jmsTemplate最简便的封装convertAndSend()发送Map类型的消息
	 * @param event
	 * @param eventKey
	 * @param createTime
	 * @param ticket
	 * @param contextPath
	 * @param openid
	 * @param destination
	 */
	private void sendMessage(String event,String eventKey,Long createTime,String ticket,String contextPath,String openid, Destination destination) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("event", event);
		map.put("eventKey", eventKey);
		map.put("createTime", createTime);
		map.put("ticket", ticket);
		map.put("contextPath", contextPath);
		map.put("openid", openid);
		jmsTemplate.convertAndSend(destination, map);
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setNotifyQueue(Destination notifyQueue) {
		this.notifyQueue = notifyQueue;
	}

	public void setNotifyTopic(Destination nodifyTopic) {
		this.notifyTopic = nodifyTopic;
	}
}
