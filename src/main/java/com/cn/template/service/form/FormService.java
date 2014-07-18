package com.cn.template.service.form;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cn.template.entity.form.Form;
import com.cn.template.repository.form.FormDao;
import com.cn.template.xutil.enums.Operator;
import com.cn.template.xutil.persistence.DynamicSpecifications;
import com.cn.template.xutil.persistence.SearchFilter;

/**
 * 表单信息的业务处理.
 * 
 * @author Libra
 *
 */
@Component
// Spring Bean的标识.
@Transactional
// 类中所有public函数都纳入事务管理的标识.
public class FormService {

	/** 日志信息 */
	private static final Logger logger = LoggerFactory.getLogger(FormService.class);

	/** 表单信息的数据访问对象 */
	private FormDao formDao;

	@Autowired
	public void setFormDao(FormDao formDao) {
		this.formDao = formDao;
	}
	

	/**
	 * 根据ID获得表单记录.
	 * @param id
	 * @return
	 */
	public Form getForm(Long id) {
		return formDao.findOne(id);
	}

	/**
	 * 保存表单信息.
	 * @param entity
	 */
	public void saveForm(Form entity) {
		if(entity.getId()==null){
			entity=formDao.save(entity);
			entity.setChTableName("ch_form_"+entity.getId());
			entity.setEnTableName("en_form_"+entity.getId());
		}
		formDao.save(entity);
	}

	/**
	 * 单个删除表单记录.
	 * @param id
	 */
	public void deleteForm(Long id) {
		formDao.delete(id);
	}

	/**
	 * 获得所有的表单记录.
	 * @return
	 */
	public List<Form> getAllForm() {
		return (List<Form>) formDao.findAll();
	}

	/**
	 * 获取表单记录[查询、分页、排序].
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Form> getForm(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Form> spec = buildSpecification(searchParams);
		return formDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页\排序请求.
	 * @param pageNumber
	 * @param pagzSize
	 * @param sortType
	 * @return
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.ASC, "id");
		} else if ("name".equals(sortType)) {
			sort = new Sort(Direction.ASC, "name");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 * @param searchParams
	 * @return
	 */
	private Specification<Form> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Form> spec = DynamicSpecifications.bySearchFilter(filters.values(), Form.class);
		return spec;
	}
	

}
