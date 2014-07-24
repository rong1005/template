package com.cn.template.web.controller.form;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.view.JasperViewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cn.template.service.form.ReportService;
import com.cn.template.xutil.Constants;
import com.google.common.collect.Maps;

/**
 * 报表处理的业务代理.
 * @author Libra
 *
 */
@Controller
@RequestMapping(value = "/report")
public class ReportController {
	/** 日志信息 */
	private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
	
	/** 报表的业务处理 */
	@Autowired
	private ReportService reportService;
	
	/**
	 * 生成报表
	 * @param formId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{formId}",method = RequestMethod.GET)
	private String build(@PathVariable(value = "formId") Long formId,Model model,HttpServletResponse response){
		logger.info("生成报表---------------");
		try{
			String url="jdbc:mysql://localhost/template_development";   
			Class.forName("com.mysql.jdbc.Driver"); 
			Connection conn = DriverManager.getConnection(url,"root", "root");
			Map<String,Object> map=Maps.newHashMap();
			map.put("appleId", 1l);
			
			JasperPrint jasperPrint=JasperFillManager.fillReport(Constants.WEBROOT+"/reports/report_form_1.jasper",map,conn);
			
			System.out.println(JasperRunManager.runReportToHtmlFile(Constants.WEBROOT+"/reports/report_form_1.jasper",map,conn));
			System.out.println(JasperRunManager.runReportToPdfFile(Constants.WEBROOT+"/reports/report_form_1.jasper",map,conn));
			
			JasperViewer.viewReport(jasperPrint);
			
			response.setContentType("application/msword;charset=utf-8");
		    String fileName = new String("未命名.doc".getBytes("GBK"), "ISO8859_1");
		    response.setHeader("Content-disposition", "attachment; filename="+ fileName);
			JRExporter exporter = new JRRtfExporter();
		    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		    exporter.exportReport();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
