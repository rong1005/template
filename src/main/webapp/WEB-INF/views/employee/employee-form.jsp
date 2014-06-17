<%@page import="com.cn.template.xutil.enums.Whether"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="wyes" value="<%=Whether.YES %>" />
<c:set var="wnot" value="<%=Whether.NOT %>" />
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>员工管理</title>
	<!-- UNIFORM 优化表单样式 -->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/js/uniform/css/uniform.default.min.css" />
</head>
<body>

	<div id="main-content">
		<div class="container">
			<div class="row">
				<div id="content" class="col-lg-12">
					<!-- PAGE HEADER-->
					<div class="row">
						<div class="col-sm-12">
							<div class="page-header">
								<!-- STYLER -->
								
								<!-- /STYLER -->
								<!-- BREADCRUMBS -->
								<ul class="breadcrumb">
									<li>
										<i class="fa fa-home"></i>
										<a href="${ctx}/workbench">主页</a>
									</li>
									<li>
										<a href="${ctx}/employee">员工列表</a>
									</li>
									<li>创建员工</li>
								</ul>
								<!-- /BREADCRUMBS -->
								
							</div>
						</div>
					</div>
					<!-- /PAGE HEADER -->
					
					<div class="box border green">
						<div class="box-title">
							<h4><i class="fa fa-table"></i>创建员工</h4>
							<div class="tools hidden-xs">
								<a href="javascript:;" class="collapse">
									<i class="fa fa-chevron-up"></i>
								</a>
								<a href="javascript:;" class="remove">
									<i class="fa fa-times"></i>
								</a>
							</div>
						</div>
						
						<div class="box-body">
							<form id="inputForm" class="form-horizontal" action="${ctx}/employee/${action}" method="post">
								<input type="hidden" name="id" value="${employee.id}"/>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">姓名</label>
									<div class="col-sm-10">
										<input type="text" id="employee_name" name="name" value="${employee.name}" class="form-control" placeholder="员工姓名"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">工号</label>
									<div class="col-sm-10">
										<input type="text" id="employee_code" name="code" value="${employee.code}" class="form-control" placeholder="员工工号"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">邮箱</label>
									<div class="col-sm-10">
										<input type="text" id="employee_email" name="email" value="${employee.email}" class="form-control" placeholder="邮箱"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">微信标识</label>
									<div class="col-sm-10">
										<input type="text" id="employee_openid" name="openid" value="${employee.openid}" class="form-control" placeholder="微信标识"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">微信标识</label>
									<div class="col-sm-10">
										<label class="radio-inline"> <input type="radio" name="whether" class="uniform" value="${wyes }" <c:if test="${wyes eq employee.whether }">checked="checked"</c:if>>${wyes.value}</label>
										<label class="radio-inline"> <input type="radio" name="whether" class="uniform" value="${wnot }" <c:if test="${wnot eq employee.whether }">checked="checked"</c:if>>${wnot.value}</label>
									</div>
								</div>
	
												  
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<input id="submit_btn" class="btn btn-success" type="submit" value="提交"/>&nbsp;
										<input id="cancel_btn" class="btn btn-default" type="button" value="返回" onclick="history.back()"/>
									</div>
								</div>
												  
							</form>
						</div>
					</div>
					
				</div>
			</div>
		</div>
	</div>	
	
	<!-- JAVASCRIPTS -->
	<!-- 引入公共JS脚本 -->
	<%@ include file="/WEB-INF/layouts/include_script.jsp"%>
	
	<!-- UNIFORM 优化表单样式 -->
	<script type="text/javascript" src="${ctx}/static/js/uniform/jquery.uniform.min.js"></script>
	
	<!-- 自定义JS脚本 -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {
			//如果页面无需设置效果，可以不设置 App.setPage ，如设置 App.setPage 而页面缺少对应的元素，会导致JS错误.
			App.setPage("employee_forms");  //设置当前启动的页面
			
			App.setHasSub("struct-manager");//设置一级菜单目录ID
			App.setSubMenu("employees-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置项目路径
			App.init(); //初始化元素以及插件
		});
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>
