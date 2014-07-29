package com.cn.template.mybatis;

import java.util.Map;

/**
 * 委托申请的数据访问接口.
 * @author Libra
 *
 */
@MyBatisRepository
public interface ApplyMybatisDao {
	
	/**
	 * 取得一个委托申请信息(对应自定义表单的内容).
	 * @param parameters
	 * @return
	 */
	Map<String,Object> findOne(Map<String, Object> parameters);
	
	/**
	 * 删除其中一个委托申请信息.
	 * @param parameters
	 */
	void deleteOne(Map<String, Object> parameters);
}
