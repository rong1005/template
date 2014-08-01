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
import org.springframework.web.multipart.MultipartFile;

import com.cn.template.entity.experiment.Equipment;
import com.cn.template.repository.experiment.EquipmentDao;
import com.cn.template.xutil.Utils;
import com.cn.template.xutil.enums.Operator;
import com.cn.template.xutil.persistence.DynamicSpecifications;
import com.cn.template.xutil.persistence.SearchFilter;

/**
 * 实验设备的业务逻辑.
 * @author Libra
 *
 */
@Component  // Spring Bean的标识.
@Transactional // 类中所有public函数都纳入事务管理的标识.
public class EquipmentService {
	
	/** 实验设备的数据访问接口 */
	private EquipmentDao equipmentDao;
	
	@Autowired
	public void setEquipmentDao(EquipmentDao equipmentDao) {
		this.equipmentDao = equipmentDao;
	}

	/**
	 * 根据ID获得实验设备记录.
	 * @param id
	 * @return
	 */
	public Equipment getEquipment(Long id) {
		return equipmentDao.findOne(id);
	}

	/**
	 * 保存实验设备信息.
	 * @param entity
	 */
	public void saveEquipment(Equipment entity,MultipartFile pictureUrl) {
		if(pictureUrl!=null&&!pictureUrl.isEmpty()){
			Utils.copy(pictureUrl, "/static/uploads/equipment/");
			entity.setPictureUrl("/static/uploads/equipment/"+pictureUrl.getOriginalFilename());
		}
		equipmentDao.save(entity);
	}

	/**
	 * 单个删除实验设备记录.
	 * @param id
	 */
	public void deleteEquipment(Long id) {
		equipmentDao.delete(id);
	}

	/**
	 * 获得所有的实验设备记录.
	 * @return
	 */
	public List<Equipment> getAllEquipment() {
		return (List<Equipment>) equipmentDao.findAll();
	}

	/**
	 * 获取实验设备记录[查询、分页、排序].
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Equipment> getTypeEquipment(Long typeId,Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Equipment> spec = buildSpecification(typeId,searchParams);
		return equipmentDao.findAll(spec, pageRequest);
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
	private Specification<Equipment> buildSpecification(Long typeId,Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("equipmentType", new SearchFilter("equipmentType", Operator.EQ, typeId));
		Specification<Equipment> spec = DynamicSpecifications.bySearchFilter(filters.values(), Equipment.class);
		return spec;
	}
	
}
