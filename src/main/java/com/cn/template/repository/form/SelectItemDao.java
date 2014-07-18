package com.cn.template.repository.form;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.form.SelectItem;

/**
 * 字段选择项的数据访问接口.
 * @author Libra
 *
 */
public interface SelectItemDao extends PagingAndSortingRepository<SelectItem, Long>, JpaSpecificationExecutor<SelectItem> {

}
