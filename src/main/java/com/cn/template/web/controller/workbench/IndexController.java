package com.cn.template.web.controller.workbench;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 工作台内容管理的业务代理.
 * @author Libra
 *
 */
@Controller
@RequestMapping(value = "/workbench")
public class IndexController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		
		return "workbench/index";
	}
	
}
