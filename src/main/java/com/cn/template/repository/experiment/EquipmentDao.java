package com.cn.template.repository.experiment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.experiment.Equipment;

/**
 * 实验设备信息的数据访问接口.
 * @author Libra
 *
 */
public interface EquipmentDao extends PagingAndSortingRepository<Equipment, Long>, JpaSpecificationExecutor<Equipment> {

	/**
	 * 获得类型下的所有设备.
	 * @param typeId
	 * @return
	 */
	public List<Equipment> findByEquipmentType_Id(Long typeId);
	
	/**
	 * 根据设备编号获得设备信息.
	 * @param serialNumber
	 * @return
	 */
	public Equipment findBySerialNumber(String serialNumber);
}
