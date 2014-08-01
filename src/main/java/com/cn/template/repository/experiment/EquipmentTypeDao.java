package com.cn.template.repository.experiment;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.experiment.EquipmentType;

/**
 * 实验设备类型的数据访问接口.
 * @author Libra
 *
 */
public interface EquipmentTypeDao extends PagingAndSortingRepository<EquipmentType, Long>, JpaSpecificationExecutor<EquipmentType> {

}
