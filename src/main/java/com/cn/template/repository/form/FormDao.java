package com.cn.template.repository.form;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.form.Form;

/**
 * 表单信息的数据访问接口.
 * @author Libra
 *
 */
public interface FormDao extends PagingAndSortingRepository<Form, Long>, JpaSpecificationExecutor<Form> {

}
