package com.cn.template.service.form;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cn.template.entity.form.Node;
import com.cn.template.repository.form.NodeDao;
import com.cn.template.web.form.NodePermissionForm;
import com.cn.template.xutil.enums.ApplyStatus;
import com.google.common.collect.Maps;

/**
 * 节点权限信息的业务处理.
 * @author Libra
 *
 */
@Component  // Spring Bean的标识.
@Transactional // 类中所有public函数都纳入事务管理的标识.
public class NodeService {

	/** 节点权限信息的数据访问接口 */
	private NodeDao nodeDao;

	@Autowired
	public void setNodeDao(NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}
	
	/**
	 * 取得表单在指定节点下的字段访问权限.
	 * @param applyStatus
	 * @param formId
	 * @return
	 */
	public Map<Long,Node> nodeMap(ApplyStatus applyStatus, Long formId){
		Map<Long,Node> nodeMap=null;
		List<Node> nodeList = nodeDao.findByApplyStatusAndField_Form_Id(applyStatus, formId);
		if(!nodeList.isEmpty()){
			nodeMap = Maps.newHashMap();
			for(Node node : nodeList){
				nodeMap.put(node.getField().getId(), node);
			}
		}
		return nodeMap;
	}
	
	/**
	 * 保存节点授权记录.
	 * @param nodePermissionForm
	 */
	public void save(NodePermissionForm nodePermissionForm){
		for(Node node:nodePermissionForm.getNodes()){
			node.setApplyStatus(nodePermissionForm.getApplyStatus());
			if(node.getId()==null){
				node.setCreateTime(new Date());
			}
			node.setUpdateTime(new Date());
			nodeDao.save(node);
		}
	}
	
}
