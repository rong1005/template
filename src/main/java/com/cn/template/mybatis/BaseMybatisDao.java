package com.cn.template.mybatis;

import java.util.Map;


/**
 * 基础信息处理的数据访问接口.主要用于DDL操作.
 * @author Libra
 *
 */
@MyBatisRepository
public interface BaseMybatisDao {
	
	/**
	 * 创建数据表.
	 * @param parameters
	 */
	void createTable(Map<String, Object> parameters);
	
	/**
	 * 删除数据表.
	 * @param parameters
	 */
	void dropTable(Map<String, Object> parameters);
	
	/**
	 * 给数据表添加字段.
	 * @param parameters
	 */
	void addColumn(Map<String, Object> parameters);

	/**
	 * 删除数据表字段.
	 * @param parameters
	 */
	void dropColumn(Map<String, Object> parameters);
	
	/**
	 * 自定义组合更新.
	 * @param parameters key:tableName、setString、whereString
	 * 
	 */
	void update(Map<String, Object> parameters);
	
	/**
	 * 自定义组合添加.
	 * @param parameters
	 */
	void insert(Map<String, Object> parameters);
	
}
