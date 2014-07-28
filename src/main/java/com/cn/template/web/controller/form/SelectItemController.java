package com.cn.template.web.controller.form;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.template.service.form.SelectItemService;
import com.google.common.collect.Maps;

/**
 * 字段选择项的业务代理.
 * @author Libra
 *
 */
@Controller
@RequestMapping(value = "/select")
public class SelectItemController {
	
	/** 字段选择项的业务处理 */
	@Autowired
	private SelectItemService selectItemService;
	
	/**
	 * 删除字段选择项.
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public Map<String,Boolean> delete(@PathVariable("id") Long id) {
		Map<String,Boolean> map=Maps.newHashMap();
		selectItemService.deleteSelectItem(id);
		map.put("bool", true);
		return map;
	}
}
