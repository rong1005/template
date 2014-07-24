package com.cn.template.service.form;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

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
import com.cn.template.entity.form.Form;
import com.cn.template.mybatis.BaseMybatisDao;
import com.cn.template.repository.form.FormDao;
import com.cn.template.xutil.persistence.DynamicSpecifications;
import com.cn.template.xutil.persistence.SearchFilter;
import com.google.common.collect.Maps;

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
	
	/** 基础信息处理的数据访问接口 */
	private BaseMybatisDao baseMybatisDao;
	
	/** 字段信息的数据访问接口 */
	private FieldService fieldService;
	 

	@Autowired
	public void setFormDao(FormDao formDao) {
		this.formDao = formDao;
	}
	
	@Autowired
	public void setBaseMybatisDao(BaseMybatisDao baseMybatisDao) {
		this.baseMybatisDao = baseMybatisDao;
	}

	@Autowired
	public void setFieldService(FieldService fieldService) {
		this.fieldService = fieldService;
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
			logger.info("新建表单");
			entity=formDao.save(entity);
			entity.setTableName("form_"+entity.getId());
			//创建表单对应的数据表Table
			Map<String, Object> parameters =Maps.newHashMap();
			parameters.put("tableName", entity.getTableName());
			baseMybatisDao.createTable(parameters);
		}
		logger.info("更新表单");
		formDao.save(entity);
	}

	/**
	 * 单个删除表单记录.
	 * @param id
	 */
	public void deleteForm(Long id) {
		Form form = getForm(id);
		
		//删除表单对应的数据表Table
		Map<String, Object> parameters =Maps.newHashMap();
		parameters.put("tableName", form.getTableName());
		baseMybatisDao.dropTable(parameters);
		formDao.delete(id);
	}
	
	/**
	 * 保存实验委托信息.
	 * @param request
	 */
	public void saveApply(ServletRequest request){
		Map<String, String[]> paramMap=request.getParameterMap();
		if(paramMap.containsKey("formId")&&paramMap.containsKey("name")){
			Long formId = Long.parseLong(request.getParameter("formId"));
			logger.info("formId：{}",formId);
			Form form = getForm(formId);
			List<Field> fieldList = fieldService.getAllField(formId);
			StringBuffer fieldNames=new StringBuffer();
			StringBuffer fieldValues=new StringBuffer();
			
			fieldNames.append("name");
			fieldValues.append("'"+request.getParameter("name")+"'");

			
			for(Field field : fieldList){
				if(paramMap.containsKey("ch_"+field.getName())&&paramMap.containsKey("en_"+field.getName())){
					String chValue = request.getParameter("ch_"+field.getName());
					logger.info("中文内容：{}",chValue);
					String enValue = request.getParameter("en_"+field.getName());
					logger.info("英文内容：{}",enValue);
					
					fieldNames.append(", ch_"+field.getName());
					fieldNames.append(", en_"+field.getName());
					fieldValues.append(", '"+chValue+"'");
					fieldValues.append(", '"+enValue+"'");
				}
			}
			
			//insert into ${tableName} (${fieldNames}) values (${fieldValues})
			Map<String, Object> parameters = Maps.newHashMap();
			parameters.put("tableName", form.getTableName());
			parameters.put("fieldNames", fieldNames.toString());
			parameters.put("fieldValues", fieldValues.toString());
			baseMybatisDao.insert(parameters);
		}
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
