package com.cn.template.repository.form;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.form.Node;
import com.cn.template.xutil.enums.ApplyStatus;

/**
 * 节点权限信息的数据访问接口.
 * @author Libra
 *
 */
public interface NodeDao extends PagingAndSortingRepository<Node, Long>, JpaSpecificationExecutor<Node> {

	/**
	 * 获得表单在指定字段下的节点访问权限.
	 * @param applyStatus
	 * @param formId
	 * @return
	 */
	public List<Node> findByApplyStatusAndField_Form_Id(ApplyStatus applyStatus,Long formId);
}
