package com.cn.template.web.controller.weixin;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.template.entity.mail.EmailContent;
import com.cn.template.service.mail.EmailAttachmentService;
import com.cn.template.service.mail.EmailContentService;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.web.Servlets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 与微信接入业务处理的代理类.
 * @author Libra
 *
 */
@Controller
@RequestMapping(value = "/wxmail")
public class WeixinMailController {

	private static Logger logger=LoggerFactory.getLogger(WeixinMailController.class);
	
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("subject", "标题");
	}
	
	/** 邮件内容的业务处理 */
	@Autowired
	private EmailContentService emailContentService;
	
	/** 邮件附件的业务处理 */
	@Autowired
	private EmailAttachmentService emailAttachmentService;
	
	/**
	 * 邮件列表.
	 * @param openid 微信用户的标识
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{openid}",method = RequestMethod.GET)
	public String list(@PathVariable("openid") String openid,Model model,
			ServletRequest request) {
		
		model.addAttribute("openid", openid);
	

		return "wxmail/mail_list";
	}
	
	@RequestMapping(value="/jsonValue/{openid}/{page}")
	@ResponseBody
	public Map<String,Object> getValue(@PathVariable("openid") String openid,
			@PathVariable(value = "page") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = Constants.PAGE_SIZE_3) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType){
		Page<EmailContent> emailContents = emailContentService.getUserEmailContent(openid, pageNumber, pageSize, sortType);
		Map<String,Object> map=Maps.newHashMap();
		List<String> mailTitles=Lists.newArrayList();
		for(EmailContent emailContent:emailContents.getContent()){
			mailTitles.add("<li><a href=\"#\">"+emailContent.getSubject()+"</a><span>"+emailContent.getFromName()+" &emsp;&emsp; "+emailContent.getReceiveDate()+"</span></li>");
		}
		map.put("html", mailTitles);
		map.put("page", pageNumber+1);
		return map;
	}
}
