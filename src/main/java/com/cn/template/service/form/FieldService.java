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

import com.cn.template.entity.form.Field;
import com.cn.template.repository.form.FieldDao;
import com.cn.template.xutil.enums.Operator;
import com.cn.template.xutil.persistence.DynamicSpecifications;
import com.cn.template.xutil.persistence.SearchFilter;

/**
 * 字段信息的业务处理.
 * @author Libra
 *
 */
@Component  // Spring Bean的标识.
@Transactional // 类中所有public函数都纳入事务管理的标识.
public class FieldService {
	
	/** 日志信息 */
	private static final Logger logger = LoggerFactory.getLogger(FieldService.class);
	
	/** 字段信息的数据访问对象 */
	private FieldDao fieldDao;

	@Autowired
	public void setFieldDao(FieldDao fieldDao) {
		this.fieldDao = fieldDao;
	}
	

	/**
	 * 根据ID获得字段记录.
	 * @param id
	 * @return
	 */
	public Field getField(Long id) {
		return fieldDao.findOne(id);
	}

	/**
	 * 保存字段信息.
	 * @param entity
	 */
	public void saveField(Field entity) {
		fieldDao.save(entity);
	}

	/**
	 * 单个删除字段记录.
	 * @param id
	 */
	public void deleteField(Long id) {
		fieldDao.delete(id);
	}

	/**
	 * 获得所有的字段记录.
	 * @return
	 */
	public List<Field> getAllField() {
		return (List<Field>) fieldDao.findAll();
	}

	/**
	 * 获取字段记录[查询、分页、排序].
	 * @param formId
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Field> getFormField(Long formId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Field> spec = buildSpecification(formId, searchParams);
		return fieldDao.findAll(spec, pageRequest);
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
			sort = new Sort(Direction.ASC, "createTime");
		} else if ("name".equals(sortType)) {
			sort = new Sort(Direction.ASC, "name");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 * @param formId
	 * @param searchParams
	 * @return
	 */
	private Specification<Field> buildSpecification(Long formId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("form.id", new SearchFilter("form.id", Operator.EQ, formId));
		Specification<Field> spec = DynamicSpecifications.bySearchFilter(filters.values(), Field.class);
		return spec;
	}
	
	
}
