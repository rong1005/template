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

import com.cn.template.entity.experiment.EquipmentType;
import com.cn.template.repository.experiment.EquipmentTypeDao;
import com.cn.template.xutil.persistence.DynamicSpecifications;
import com.cn.template.xutil.persistence.SearchFilter;

/**
 * 实验设备类型的业务逻辑.
 * @author Libra
 *
 */
@Component  // Spring Bean的标识.
@Transactional // 类中所有public函数都纳入事务管理的标识.
public class EquipmentTypeService {
	
	/** 实验设备类型的数据访问接口 */
	private EquipmentTypeDao equipmentTypeDao;
	
	@Autowired
	public void setEquipmentTypeDao(EquipmentTypeDao equipmentTypeDao) {
		this.equipmentTypeDao = equipmentTypeDao;
	}

	/**
	 * 根据ID获得实验设备类型记录.
	 * @param id
	 * @return
	 */
	public EquipmentType getEquipmentType(Long id) {
		return equipmentTypeDao.findOne(id);
	}

	/**
	 * 保存实验设备类型信息.
	 * @param entity
	 */
	public void saveEquipmentType(EquipmentType entity) {
		equipmentTypeDao.save(entity);
	}

	/**
	 * 单个删除实验设备类型记录.
	 * @param id
	 */
	public void deleteEquipmentType(Long id) {
		equipmentTypeDao.delete(id);
	}

	/**
	 * 获得所有的实验设备类型记录.
	 * @return
	 */
	public List<EquipmentType> getAllEquipmentType() {
		return (List<EquipmentType>) equipmentTypeDao.findAll();
	}

	/**
	 * 获取实验设备类型记录[查询、分页、排序].
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<EquipmentType> getEquipmentType(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<EquipmentType> spec = buildSpecification(searchParams);
		return equipmentTypeDao.findAll(spec, pageRequest);
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
	private Specification<EquipmentType> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<EquipmentType> spec = DynamicSpecifications.bySearchFilter(filters.values(), EquipmentType.class);
		return spec;
	}
	
}
