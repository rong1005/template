package com.cn.template.service.experiment;

import java.util.Date;
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

import com.cn.template.entity.experiment.Apply;
import com.cn.template.entity.experiment.ApplyPrice;
import com.cn.template.entity.experiment.ApplyRemark;
import com.cn.template.entity.experiment.Sample;
import com.cn.template.entity.form.Field;
import com.cn.template.entity.form.Form;
import com.cn.template.entity.form.SelectItem;
import com.cn.template.mybatis.ApplyMybatisDao;
import com.cn.template.mybatis.BaseMybatisDao;
import com.cn.template.repository.experiment.ApplyDao;
import com.cn.template.repository.experiment.ApplyPriceDao;
import com.cn.template.repository.experiment.ApplyRemarkDao;
import com.cn.template.repository.form.SelectItemDao;
import com.cn.template.service.form.FormService;
import com.cn.template.xutil.enums.ApplyStatus;
import com.cn.template.xutil.enums.FieldType;
import com.cn.template.xutil.enums.SampleStatus;
import com.cn.template.xutil.enums.Whether;
import com.cn.template.xutil.persistence.DynamicSpecifications;
import com.cn.template.xutil.persistence.SearchFilter;
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
	
	/** 委托申请信息的业务处理 */
	private FormService formService;
	
	/** 样品信息 */
	private SampleService sampleService; 
	
	/** 收费管理的业务逻辑. */
	private PriceService priceService;
	
	@Autowired
	public void setSampleService(SampleService sampleService) {
		this.sampleService = sampleService;
	}

	@Autowired
	public void setFormService(FormService formService) {
		this.formService = formService;
	}
	
	@Autowired
	public void setPriceService(PriceService priceService) {
		this.priceService = priceService;
	}



	/** 实验委托请求信息的数据访问接口. */
	private ApplyDao applyDao;
	
	/** 实验委托请求信息的数据访问接口 MyBatis调用 */
	private ApplyMybatisDao applyMybatisDao;
	
	/** 字段选择项的数据访问接口 */
	private SelectItemDao selectItemDao;
	
	/** 基础信息处理的数据访问接口.主要用于DDL操作 */
	private BaseMybatisDao baseMybatisDao; 
	
	/** 实验申请备注信息的数据访问接口 */
	private ApplyRemarkDao applyRemarkDao;
	
	/** 实验费用清单的数据访问接口. */
	private ApplyPriceDao applyPriceDao;
	
	@Autowired
	public void setApplyDao(ApplyDao applyDao) {
		this.applyDao = applyDao;
	}

	@Autowired
	public void setApplyMybatisDao(ApplyMybatisDao applyMybatisDao) {
		this.applyMybatisDao = applyMybatisDao;
	}

	@Autowired
	public void setSelectItemDao(SelectItemDao selectItemDao) {
		this.selectItemDao = selectItemDao;
	}

	@Autowired
	public void setBaseMybatisDao(BaseMybatisDao baseMybatisDao) {
		this.baseMybatisDao = baseMybatisDao;
	}
	
	@Autowired
	public void setApplyRemarkDao(ApplyRemarkDao applyRemarkDao) {
		this.applyRemarkDao = applyRemarkDao;
	}
	
	@Autowired
	public void setApplyPriceDao(ApplyPriceDao applyPriceDao) {
		this.applyPriceDao = applyPriceDao;
	}

	/**
	 * 根据ID获得委托申请记录.
	 * @param id
	 * @return
	 */
	public Apply getApply(Long id) {
		return applyDao.findOne(id);
	}
	
	/** 更新申请的样品数量 */
	public void updateSampleCount(Long applyId){
		Apply apply=getApply(applyId);
		apply.setSampleNumber(sampleService.getApplySample(applyId).size());
		applyDao.save(apply);
		Map<String, Object> parameters=Maps.newHashMap();
		parameters.put("tableName", apply.getForm().getTableName());
		parameters.put("setString", "sample_number = "+apply.getSampleNumber());
		parameters.put("whereString", "apply_id = "+applyId);
		baseMybatisDao.update(parameters);
	}
	
	
	/**
	 * 取得委托申请中对应的自定义字段值.
	 * @param apply
	 * @return
	 */
	public Map<String,Object> getApplyCustomField(Apply apply){
		if(apply!=null){
			Map<String, Object> parameters = Maps.newHashMap();
			parameters.put("applyId", apply.getId());
			parameters.put("tableName", apply.getForm().getTableName());
			StringBuffer customFields=new StringBuffer();
			for(Field field : apply.getForm().getFields()){
				if(field.getFieldType().equals(FieldType.DOUBLE)||field.getFieldType().equals(FieldType.INT)){
					customFields.append(field.getName()+", ");
				}else{
					customFields.append("ch_"+field.getName()+" as '"+field.getName()+".ch', ");
					customFields.append("en_"+field.getName()+" as '"+field.getName()+".en', ");
					if(field.getFieldType().equals(FieldType.SELECT)||field.getFieldType().equals(FieldType.CHECKBOX)||field.getFieldType().equals(FieldType.RADIO)){
						customFields.append(field.getName()+" as '"+field.getName()+".id', ");
					}
				}
			}
			parameters.put("customFields", customFields.toString());
			return applyMybatisDao.findOne(parameters);
		}else{
			return null;
		}
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
			newApply.setChUnits(newApply.getUnits().getValue());
			newApply.setEnUnits(newApply.getUnits().getEnValue());
			newApply.setCreateTime(new Date());
			newApply.setUpdateTime(new Date());
			newApply.setApplyStatus(ApplyStatus.REQUEST);
			//保存委托申请信息.
			newApply=applyDao.save(newApply);
			
			//保存备注信息
			ApplyRemark applyRemark = new ApplyRemark();
			applyRemark.setApply(newApply);
			applyRemark.setApplyStatus(ApplyStatus.REQUEST);
			applyRemark.setRemark(ApplyStatus.REQUEST.getValue());
			applyRemark.setCreateTime(new Date());
			applyRemark.setUpdateTime(new Date());
			applyRemarkDao.save(applyRemark);
			
			StringBuffer fieldNames=new StringBuffer();
			StringBuffer fieldValues=new StringBuffer();
			
			fieldNames.append("apply_id,ch_apply_name,en_apply_name,ch_check_type,en_check_type,ch_consigner,en_consigner,ch_client,en_client,ch_test_items,en_test_items,ch_sample_name,en_sample_name,sample_model,sample_number,ch_units,en_units");
			fieldValues.append(newApply.getId());
			fieldValues.append(",'"+newApply.getChApplyName()+"'");
			fieldValues.append(",'"+newApply.getEnApplyName()+"'");
			fieldValues.append(",'"+newApply.getChCheckType()+"'");
			fieldValues.append(",'"+newApply.getEnCheckType()+"'");
			fieldValues.append(",'"+newApply.getChConsigner()+"'");
			fieldValues.append(",'"+newApply.getEnConsigner()+"'");
			fieldValues.append(",'"+newApply.getChClient()+"'");
			fieldValues.append(",'"+newApply.getEnClient()+"'");
			fieldValues.append(",'"+newApply.getChTestItems()+"'");
			fieldValues.append(",'"+newApply.getEnTestItems()+"'");
			fieldValues.append(",'"+newApply.getChSampleName()+"'");
			fieldValues.append(",'"+newApply.getEnSampleName()+"'");
			fieldValues.append(",'"+newApply.getSampleModel()+"'");
			fieldValues.append(",'"+newApply.getSampleNumber()+"'");
			fieldValues.append(",'"+newApply.getChUnits()+"'");
			fieldValues.append(",'"+newApply.getEnUnits()+"'");
			
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
			
			//将数据插入到指定的委托申请（table）中
			//insert into ${tableName} (${fieldNames}) values (${fieldValues})
			Map<String, Object> parameters = Maps.newHashMap();
			parameters.put("tableName", form.getTableName());
			parameters.put("fieldNames", fieldNames.toString());
			parameters.put("fieldValues", fieldValues.toString());
			baseMybatisDao.insert(parameters);
		}

	}
	
	
	
	/**
	 * 更新实验委托信息.
	 * @param request
	 */
	public void updateApply(Apply apply,ServletRequest request){
		Map<String, String[]> paramMap=request.getParameterMap();
		
		if(apply.getForm()!=null&&apply.getForm().getId()!=null&&apply.getForm().getId()>0){
			Form form = formService.getForm(apply.getForm().getId());
			apply.setChCheckType(apply.getCheckType().getValue());
			apply.setEnCheckType(apply.getCheckType().getEnValue());

			apply.setUpdateTime(new Date());
			//保存委托申请信息.
			apply=applyDao.save(apply);
			if(apply.getApplyStatus().equals(ApplyStatus.AUDITING)){
				//添加审核备注信息
				ApplyRemark applyRemark = new ApplyRemark();
				applyRemark.setApply(apply);
				applyRemark.setCreateTime(new Date());
				applyRemark.setUpdateTime(new Date());
				applyRemark.setApplyStatus(apply.getApplyStatus());
				applyRemark.setIsPass(apply.getIsPass());
				applyRemark.setRemark(request.getParameter("apply_remark"));
				applyRemarkDao.save(applyRemark);
			
				//添加样品信息
				if(apply.getIsPass().equals(Whether.YES)){
					sampleService.deleteApplySample(apply.getId());
					for(int i=1;i<=apply.getSampleNumber();i++){
						Sample sample = new Sample();
						sample.setApply(apply);
						sample.setCreateTime(new Date());
						sample.setUpdateTime(new Date());
						sample.setSerialNumber(apply.getSerialNumber()+"-"+((100+i)+"").substring(1));
						sample.setStatus(SampleStatus.WAIT_RECEIVE);
						sampleService.saveSample(sample);
					}
					
					if(paramMap.containsKey("priceId")&&paramMap.containsKey("usedHour")&&paramMap.containsKey("usedTimes")&&paramMap.containsKey("totalPrice")){
						List<ApplyPrice> applyPriceList = applyPriceDao.findByApply_Id(apply.getId());
						if(applyPriceList!=null&&!applyPriceList.isEmpty()){
							applyPriceDao.delete(applyPriceList);
						}
						
						String[] priceId=paramMap.get("priceId");
						String[] usedHour=paramMap.get("usedHour");
						String[] usedTimes=paramMap.get("usedTimes");
						String[] totalPrice=paramMap.get("totalPrice");
						for(int i=0;i<priceId.length;i++){
							ApplyPrice applyPrice=new ApplyPrice();
							applyPrice.setApply(apply);
							applyPrice.setPrice(priceService.getPrice(Long.parseLong(priceId[i])));
							applyPrice.setCreateTime(new Date());
							applyPrice.setUpdateTime(new Date());
							applyPrice.setUsedHour(Double.parseDouble(usedHour[i]));
							applyPrice.setUsedTimes(Double.parseDouble(usedTimes[i]));
							applyPrice.setTotalPrice(Double.parseDouble(totalPrice[i]));
							applyPriceDao.save(applyPrice);
						}
					}
					
				}
			
			}
			
			
			StringBuffer setString=new StringBuffer();
			StringBuffer whereString=new StringBuffer();
			
			whereString.append("apply_id = "+apply.getId());
			
			setString.append(" serial_number='"+apply.getSerialNumber()+"', ");
			setString.append(" ch_apply_name='"+apply.getChApplyName()+"', ");
			setString.append(" en_apply_name='"+apply.getEnApplyName()+"', ");
			setString.append(" ch_check_type='"+apply.getChCheckType()+"', ");
			setString.append(" en_check_type='"+apply.getEnCheckType()+"', ");
			setString.append(" ch_consigner='"+apply.getChConsigner()+"', ");
			setString.append(" en_consigner='"+apply.getEnConsigner()+"', ");
			setString.append(" ch_test_items='"+apply.getChTestItems()+"', ");
			setString.append(" en_test_items='"+apply.getEnTestItems()+"', ");
			setString.append(" ch_client='"+apply.getChClient()+"', ");
			setString.append(" en_client='"+apply.getEnClient()+"', ");
			
			setString.append(" ch_sample_name='"+apply.getChSampleName()+"', ");
			setString.append(" en_sample_name='"+apply.getEnSampleName()+"', ");
			setString.append(" sample_model='"+apply.getSampleModel()+"', ");
			setString.append(" sample_number='"+apply.getSampleNumber()+"', ");
			setString.append(" ch_units='"+apply.getChUnits()+"', ");
			setString.append(" en_units='"+apply.getEnUnits()+"', ");

			for(Field field : form.getFields()){
				
				
				if(field.getFieldType().equals(FieldType.SELECT)||field.getFieldType().equals(FieldType.CHECKBOX)||field.getFieldType().equals(FieldType.RADIO)){
					//选择框类型的.（除中英文外，还有对应的ID）
					if(paramMap.containsKey(field.getName())){
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
							
							setString.append(field.getName()+"='"+selectItemId.substring(1)+"', ");
							setString.append("ch_"+field.getName()+"='"+selectItemChName.substring(1)+"', ");
							setString.append("en_"+field.getName()+"='"+selectItemEnName.substring(1)+"', ");
							
						}else{
							String value = request.getParameter(field.getName());
							SelectItem selectItem = selectItemDao.findOne(Long.parseLong(value));
							
							setString.append(field.getName()+"='"+selectItem.getId()+"', ");
							setString.append("ch_"+field.getName()+"='"+selectItem.getChItemName()+"', ");
							setString.append("en_"+field.getName()+"='"+selectItem.getEnItemName()+"', ");
							
						}
					}
				}else if(field.getFieldType().equals(FieldType.DOUBLE)||field.getFieldType().equals(FieldType.INT)){
					//数字类型（只有中文类字段）
					if(paramMap.containsKey(field.getName())){
						String value = request.getParameter(field.getName());
						setString.append(field.getName()+"="+value+", ");
					}
				}else{
					//其他文本类型（中英文字段）
					if(paramMap.containsKey("ch_"+field.getName())&&paramMap.containsKey("en_"+field.getName())){
						String chValue = request.getParameter("ch_"+field.getName());
						String enValue = request.getParameter("en_"+field.getName());

						setString.append("ch_"+field.getName()+"='"+chValue+"', ");
						setString.append("en_"+field.getName()+"='"+enValue+"', ");
					}
				}
			}
			setString.append(" apply_id="+apply.getId());
			//将数据插入到指定的委托申请（table）中
			//update ${tableName} set ${setString} where ${whereString}
			Map<String, Object> parameters = Maps.newHashMap();
			parameters.put("tableName", form.getTableName());
			parameters.put("setString", setString.toString());
			parameters.put("whereString", whereString.toString());
			baseMybatisDao.update(parameters);
		}

	}

	/**
	 * 单个删除委托申请记录.
	 * @param id
	 */
	public void deleteApply(Long id) {
		Apply apply = getApply(id);
		//delete from ${tableName} where apply_id=#{applyId}
		Map<String, Object> parameters=Maps.newHashMap();
		parameters.put("tableName", apply.getForm().getTableName());
		parameters.put("applyId", apply.getId());
		//自定义表单
		applyMybatisDao.deleteOne(parameters);
		//删除备注
		applyRemarkDao.delete(apply.getRemarks());
		//删除申请
		applyDao.delete(id);
	}

	/**
	 * 获得所有的委托申请记录.
	 * @return
	 */
	public List<Apply> getAllApply() {
		return (List<Apply>) applyDao.findAll();
	}

	/**
	 * 获取委托申请记录[查询、分页、排序].
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Apply> getApply(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Apply> spec = buildSpecification(searchParams);
		return applyDao.findAll(spec, pageRequest);
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
	private Specification<Apply> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Apply> spec = DynamicSpecifications.bySearchFilter(filters.values(), Apply.class);
		return spec;
	}
	
	
}
