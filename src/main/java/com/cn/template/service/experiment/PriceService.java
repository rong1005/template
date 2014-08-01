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

import com.cn.template.entity.experiment.Price;
import com.cn.template.repository.experiment.PriceDao;
import com.cn.template.xutil.persistence.DynamicSpecifications;
import com.cn.template.xutil.persistence.SearchFilter;

/**
 * 收费管理的业务逻辑.
 * @author Libra
 *
 */
@Component  // Spring Bean的标识.
@Transactional // 类中所有public函数都纳入事务管理的标识.
public class PriceService {
	
	/** 收费管理的数据访问接口 */
	private PriceDao priceDao;
	
	@Autowired
	public void setPriceDao(PriceDao priceDao) {
		this.priceDao = priceDao;
	}

	/**
	 * 根据ID获得收费记录.
	 * @param id
	 * @return
	 */
	public Price getPrice(Long id) {
		return priceDao.findOne(id);
	}

	/**
	 * 保存收费信息.
	 * @param entity
	 */
	public void savePrice(Price entity) {
		priceDao.save(entity);
	}

	/**
	 * 单个删除收费记录.
	 * @param id
	 */
	public void deletePrice(Long id) {
		priceDao.delete(id);
	}

	/**
	 * 获得所有的收费记录.
	 * @return
	 */
	public List<Price> getAllPrice() {
		return (List<Price>) priceDao.findAll();
	}
	
	/**
	 * 获取收费记录[查询、分页、排序].
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Price> getPrice( Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Price> spec = buildSpecification(searchParams);
		return priceDao.findAll(spec, pageRequest);
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
	private Specification<Price> buildSpecification( Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Price> spec = DynamicSpecifications.bySearchFilter(filters.values(), Price.class);
		return spec;
	}
	
}
