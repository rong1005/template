package com.cn.template.repository.form;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.form.Field;

/**
 * 字段信息的数据访问接口.
 * @author Libra
 *
 */
public interface FieldDao extends PagingAndSortingRepository<Field, Long>, JpaSpecificationExecutor<Field> {

}
