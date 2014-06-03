package com.cn.template.repository;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.cn.template.modules.test.category.UnStable;
import com.cn.template.modules.test.spring.SpringContextTestCase;
import com.cn.template.repository.mail.EmailContentDao;


/**
 * 邮件内容数据访问接口测试.
 * @author Libra
 *
 */
@Category(UnStable.class)
@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class EmailContentDaoTest extends SpringContextTestCase {
	
	
	private EmailContentDao emailContentDao;
	
	@Autowired
	public void setEmailContentDao(EmailContentDao emailContentDao) {
		this.emailContentDao = emailContentDao;
	}


	@Test
	public void findMaxReceiveDateByEmail() {
		System.out.println(emailContentDao.findMaxReceiveDateByEmail("lzr@ggec.gd"));
	}
}
