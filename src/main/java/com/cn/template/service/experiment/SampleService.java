package com.cn.template.service.experiment;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cn.template.entity.experiment.Sample;
import com.cn.template.repository.experiment.SampleDao;
import com.cn.template.xutil.enums.Operator;
import com.cn.template.xutil.persistence.DynamicSpecifications;
import com.cn.template.xutil.persistence.SearchFilter;

/**
 * 实验样品的业务逻辑.
 * @author Libra
 *
 */
@Component  // Spring Bean的标识.
@Transactional // 类中所有public函数都纳入事务管理的标识.
public class SampleService {
	
	/** 实验样品的数据访问接口 */
	private SampleDao sampleDao;
	
	@Autowired
	public void setSampleDao(SampleDao sampleDao) {
		this.sampleDao = sampleDao;
	}

	/**
	 * 根据ID获得实验样品记录.
	 * @param id
	 * @return
	 */
	public Sample getSample(Long id) {
		return sampleDao.findOne(id);
	}

	/**
	 * 保存实验样品信息.
	 * @param entity
	 */
	public void saveSample(Sample entity) {
		sampleDao.save(entity);
	}

	/**
	 * 单个删除实验样品记录.
	 * @param id
	 */
	public void deleteSample(Long id) {
		sampleDao.delete(id);
	}
	
	/**
	 * 单个删除实验样品记录.
	 * @param id
	 */
	public void deleteApplySample(Long applyId) {
		List<Sample> sampleList = getApplySample(applyId);
		if(!sampleList.isEmpty()){
			sampleDao.delete(sampleList);
		}
	}

	/**
	 * 获得所有的实验样品记录.
	 * @return
	 */
	public List<Sample> getAllSample() {
		return (List<Sample>) sampleDao.findAll();
	}
	
	/**
	 * 取得申请下的所有样品信息.
	 * @param applyId
	 * @return
	 */
	public List<Sample> getApplySample(Long applyId){
		return sampleDao.findByApply_Id(applyId);
	}

	/**
	 * 获取实验样品记录[查询、分页、排序].
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Sample> getApplySample(Long applyId,Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Sample> spec = buildSpecification(applyId,searchParams);
		return sampleDao.findAll(spec, pageRequest);
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
	 * @param searchParams
	 * @return
	 */
	private Specification<Sample> buildSpecification(Long applyId,Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("apply", new SearchFilter("apply", Operator.EQ, applyId));
		Specification<Sample> spec = DynamicSpecifications.bySearchFilter(filters.values(), Sample.class);
		return spec;
	}
	
}
