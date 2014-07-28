package com.cn.template.service.experiment;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cn.template.entity.experiment.Apply;
import com.cn.template.entity.form.Field;
import com.cn.template.entity.form.Form;
import com.cn.template.entity.form.SelectItem;
import com.cn.template.mybatis.BaseMybatisDao;
import com.cn.template.repository.experiment.ApplyDao;
import com.cn.template.repository.form.SelectItemDao;
import com.cn.template.service.form.FormService;
import com.cn.template.xutil.enums.ApplyStatus;
import com.cn.template.xutil.enums.FieldType;
import com.google.common.collect.Maps;

/**
 * 实验委托申请管理的业务逻辑.
 * @author Libra
 *
 */
@Component
@Transactional
public class ApplyService {

	/** 日志信息 */
	private static final Logger logger = LoggerFactory.getLogger(ApplyService.class);
	
	/** 表单信息的业务处理 */
	private FormService formService;
	
	@Autowired
	public void setFormService(FormService formService) {
		this.formService = formService;
	}
	
	/** 实验委托请求信息的数据访问接口. */
	private ApplyDao applyDao;
	
	/** 字段选择项的数据访问接口 */
	private SelectItemDao selectItemDao;
	
	/** 基础信息处理的数据访问接口.主要用于DDL操作 */
	private BaseMybatisDao baseMybatisDao; 
	
	@Autowired
	public void setApplyDao(ApplyDao applyDao) {
		this.applyDao = applyDao;
	}

	@Autowired
	public void setSelectItemDao(SelectItemDao selectItemDao) {
		this.selectItemDao = selectItemDao;
	}

	@Autowired
	public void setBaseMybatisDao(BaseMybatisDao baseMybatisDao) {
		this.baseMybatisDao = baseMybatisDao;
	}


	/**
	 * 保存实验委托信息.
	 * @param request
	 */
	public void saveApply(Apply newApply,ServletRequest request){
		Map<String, String[]> paramMap=request.getParameterMap();
		
		if(newApply.getForm()!=null&&newApply.getForm().getId()!=null&&newApply.getForm().getId()>0){
			Form form = formService.getForm(newApply.getForm().getId());
			newApply.setChCheckType(newApply.getCheckType().getValue());
			newApply.setEnCheckType(newApply.getCheckType().getEnValue());
			newApply.setCreateTime(new Date());
			newApply.setUpdateTime(new Date());
			newApply.setApplyStatus(ApplyStatus.REQUEST);
			//保存委托申请信息.
			newApply=applyDao.save(newApply);
			
			StringBuffer fieldNames=new StringBuffer();
			StringBuffer fieldValues=new StringBuffer();
			
			fieldNames.append("apply_id,ch_apply_name,en_apply_name,ch_check_type,en_check_type,ch_consigner,en_consigner,ch_test_items,en_test_items");
			fieldValues.append(newApply.getId());
			fieldValues.append(",'"+newApply.getChApplyName()+"'");
			fieldValues.append(",'"+newApply.getEnApplyName()+"'");
			fieldValues.append(",'"+newApply.getChConsigner()+"'");
			fieldValues.append(",'"+newApply.getEnConsigner()+"'");
			fieldValues.append(",'"+newApply.getChTestItems()+"'");
			fieldValues.append(",'"+newApply.getEnTestItems()+"'");
			fieldValues.append(",'"+newApply.getChCheckType()+"'");
			fieldValues.append(",'"+newApply.getEnCheckType()+"'");
			
			for(Field field : form.getFields()){
				
				
				if(field.getFieldType().equals(FieldType.SELECT)||field.getFieldType().equals(FieldType.CHECKBOX)||field.getFieldType().equals(FieldType.RADIO)){
					//选择框类型的.（除中英文外，还有对应的ID）
					if(paramMap.containsKey(field.getName())){
						fieldNames.append(","+field.getName());
						fieldNames.append(", ch_"+field.getName());
						fieldNames.append(", en_"+field.getName());
						
						if(field.getFieldType().equals(FieldType.CHECKBOX)){
							String[] values = request.getParameterValues(field.getName());
							String selectItemId="";
							String selectItemChName="";
							String selectItemEnName="";
							for(String value : values){
								SelectItem selectItem = selectItemDao.findOne(Long.parseLong(value));
								selectItemId=selectItemId+","+selectItem.getId();
								selectItemChName=selectItemChName+","+selectItem.getChItemName();
								selectItemEnName=selectItemEnName+","+selectItem.getEnItemName();
							}
							fieldValues.append(",'"+selectItemId.substring(1)+"'");
							fieldValues.append(",'"+selectItemChName.substring(1)+"'");
							fieldValues.append(",'"+selectItemEnName.substring(1)+"'");
						}else{
							String value = request.getParameter(field.getName());
							SelectItem selectItem = selectItemDao.findOne(Long.parseLong(value));
							
							fieldValues.append(","+selectItem.getId());
							fieldValues.append(",'"+selectItem.getChItemName()+"'");
							fieldValues.append(",'"+selectItem.getEnItemName()+"'");
						}
					}
				}else if(field.getFieldType().equals(FieldType.DOUBLE)||field.getFieldType().equals(FieldType.INT)){
					//数字类型（只有中文类字段）
					if(paramMap.containsKey(field.getName())){
						String value = request.getParameter(field.getName());
						fieldNames.append(","+field.getName());
						fieldValues.append(","+value);
					}
				}else{
					//其他文本类型（中英文字段）
					if(paramMap.containsKey("ch_"+field.getName())&&paramMap.containsKey("en_"+field.getName())){
						String chValue = request.getParameter("ch_"+field.getName());
						String enValue = request.getParameter("en_"+field.getName());
						
						fieldNames.append(", ch_"+field.getName());
						fieldNames.append(", en_"+field.getName());
						fieldValues.append(", '"+chValue+"'");
						fieldValues.append(", '"+enValue+"'");
					}
				}
				
				
			}
			
			//将数据插入到指定的表单（table）中
			//insert into ${tableName} (${fieldNames}) values (${fieldValues})
			Map<String, Object> parameters = Maps.newHashMap();
			parameters.put("tableName", form.getTableName());
			parameters.put("fieldNames", fieldNames.toString());
			parameters.put("fieldValues", fieldValues.toString());
			baseMybatisDao.insert(parameters);
		}
		
		
	}
}
