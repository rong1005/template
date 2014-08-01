package com.cn.template.repository.experiment;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.experiment.Equipment;

/**
 * 实验设备信息的数据访问接口.
 * @author Libra
 *
 */
public interface EquipmentDao extends PagingAndSortingRepository<Equipment, Long>, JpaSpecificationExecutor<Equipment> {

}
