package com.cn.template.service.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 报表的业务处理.
 * 
 * @author Libra
 *
 */
@Component
// Spring Bean的标识.
@Transactional
// 类中所有public函数都纳入事务管理的标识.
public class ReportService {
	/** 日志信息 */
	private static final Logger logger = LoggerFactory.getLogger(ReportService.class);
	
	/** 表单信息的业务处理 */
	private FormService formService;
	
	/** 表单字段的业务信息 */
	private FieldService fieldService;

	@Autowired
	public void setFormService(FormService formService) {
		this.formService = formService;
	}

	@Autowired
	public void setFieldService(FieldService fieldService) {
		this.fieldService = fieldService;
	}
	
	

}
