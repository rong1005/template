package com.cn.template.repository.mail;

import java.util.Date;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cn.template.entity.mail.EmailContent;

/**
 * 邮件内容处理的数据访问接口.
 * @author Libra
 *
 */
public interface EmailContentDao extends PagingAndSortingRepository<EmailContent, Long>, JpaSpecificationExecutor<EmailContent>{

	/**
	 * 取得邮箱最近的一封邮件(接收时间).
	 * @param email
	 * @return
	 */
	@Query(value="select Max(receiveDate) from EmailContent where email=?1 ")
	public Date findMaxReceiveDateByEmail(String email);
	
	/**
	 * 取得邮箱最近的一封邮件（邮件ID）.
	 * @param email
	 * @return
	 */
	@Query(value="select Max(messageId) from EmailContent where email=?1 ")
	public Integer findMaxMessageIdByEmail(String email);
}
