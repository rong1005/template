package com.cn.template.service.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cn.template.repository.form.SelectItemDao;


/**
 * 字段选择项的业务处理.
 * @author Libra
 *
 */
@Component  // Spring Bean的标识.
@Transactional // 类中所有public函数都纳入事务管理的标识.
public class SelectItemService {

	/** 字段选择项的数据访问接口 */
	private SelectItemDao selectItemDao;

	@Autowired
	public void setSelectItemDao(SelectItemDao selectItemDao) {
		this.selectItemDao = selectItemDao;
	}
	
	/**
	 * 删除单个选择项.
	 * @param id
	 */
	public void deleteSelectItem(Long id) {
		selectItemDao.delete(id);
	}
	
	
}
