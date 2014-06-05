package com.cn.template.service.weixin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cn.template.entity.weixin.NewsCategory;
import com.cn.template.entity.weixin.NewsMessage;
import com.cn.template.repository.weixin.NewsMessageDao;
import com.cn.template.xutil.Constants;
import com.cn.template.xutil.Utils;
import com.cn.template.xutil.enums.Operator;
import com.cn.template.xutil.persistence.DynamicSpecifications;
import com.cn.template.xutil.persistence.SearchFilter;
import com.google.common.collect.Maps;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 图文消息的业务处理.
 * @author Libra
 *
 */
@Component
@Transactional
public class NewsMessageService {

	/** 图文消息的数据访问对象 */
	private NewsMessageDao newsMessageDao;

	@Autowired
	public void setNewsMessageDao(NewsMessageDao newsMessageDao) {
		this.newsMessageDao = newsMessageDao;
	}
	
	/**
	 * 根据ID获得图文消息记录.
	 * @param id
	 * @return
	 */
	public NewsMessage getNewsMessage(Long id) {
		return newsMessageDao.findOne(id);
	}

	/**
	 * 保存图文消息信息.
	 * @param entity
	 */
	public void saveNewsMessage(NewsMessage entity) {
		newsMessageDao.save(entity);
	}
	
	/**
	 * 保存图文消息信息,带上传文件.
	 * @param entity
	 */
	public void saveNewsMessage(NewsMessage entity,MultipartFile bigPic,MultipartFile smallPic) {
		if(!bigPic.isEmpty()){
			Utils.copy(bigPic, "/static/uploads/news-message/big/");
			entity.setBigPicurl("/static/uploads/news-message/big/"+bigPic.getOriginalFilename());
		}
		if(!smallPic.isEmpty()){
			Utils.copy(smallPic, "/static/uploads/news-message/small/");
			entity.setSmallPicurl("/static/uploads/news-message/small/"+smallPic.getOriginalFilename());
		}
		newsMessageDao.save(entity);
	}


	/**
	 * 单个删除图文消息记录.
	 * @param id
	 */
	public void deleteNewsMessage(Long id) {
		newsMessageDao.delete(id);
	}
	
	/**
	 * 发布图文信息.
	 * @param id
	 */
	public void publishNewsMessage(NewsMessage newsMessage){
		try{
		
		String fileUrl=newsMessage.getUrl();
		if(fileUrl==null||fileUrl.equals("")){
			fileUrl="/html/weixin/news-message/" +new Date().getTime()+".html";
		}
		File localFile = new File(Constants.WEBROOT +"/html/weixin/news-message");
		if (!localFile.exists()) {
			localFile.mkdirs();
		}
		
		Map<String,Object> map=Maps.newHashMap();
		map.put("contextPath", Constants.CONTEXT_PATH);
		map.put("message", newsMessage);
		/*FreeMarkerConfigurationFactory freeMarkerConfigurationFactory=new FreeMarkerConfigurationFactory();
		freeMarkerConfigurationFactory.setTemplateLoaderPath("classpath:/freemarker");
		
		Configuration configuration=freeMarkerConfigurationFactory.createConfiguration();
		Template template=configuration.getTemplate("newsMessageContent.ftl", "utf-8");*/
		
		File file = new File(Constants.WEBROOT+fileUrl);
		OutputStreamWriter out=new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file), 16 * 1024), "UTF-8");
		//将FreeMark解析的内容写入文件.
		out.write(Utils.ftlAnalyze("newsMessageContent.ftl", map));
		newsMessage.setUrl(fileUrl);
		saveNewsMessage(newsMessage);
		if (null != out) {
			out.close();
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 获得所有的图文消息记录.
	 * @return
	 */
	public List<NewsMessage> getAllNewsMessage() {
		return (List<NewsMessage>) newsMessageDao.findAll();
	}
	
	/**
	 * 获取类别下的图文消息.
	 * @param newsCategory
	 * @return
	 */
	public List<NewsMessage> getNewsMessage(NewsCategory newsCategory ){
		return newsMessageDao.findByNewsCategory(newsCategory);
	}

	/**
	 * 获取图文消息记录[查询、分页、排序].
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<NewsMessage> getNewsMessage(Long newsCategoryId,Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<NewsMessage> spec = buildSpecification(newsCategoryId,searchParams);

		return newsMessageDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 * @param pageNumber
	 * @param pagzSize
	 * @param sortType
	 * @return
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("name".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 * @param searchParams
	 * @return
	 */
	private Specification<NewsMessage> buildSpecification(Long newsCategoryId,Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("newsCategory.id", new SearchFilter("newsCategory.id", Operator.EQ, newsCategoryId));
		Specification<NewsMessage> spec = DynamicSpecifications.bySearchFilter(filters.values(), NewsMessage.class);
		return spec;
	}
}
