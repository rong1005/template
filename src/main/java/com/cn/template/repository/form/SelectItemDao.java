package com.cn.template.repository.form;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.form.SelectItem;

/**
 * 字段选择项的数据访问接口.
 * @author Libra
 *
 */
public interface SelectItemDao extends PagingAndSortingRepository<SelectItem, Long>, JpaSpecificationExecutor<SelectItem> {

	/**
	 * 查询指定字段中的选择项;
	 * @param fieldId
	 * @return
	 */
	public List<SelectItem> findByField_Id(Long fieldId);
}
