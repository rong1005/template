<%@page import="com.cn.template.xutil.enums.ApplyStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<c:set var="APPLY_REQUEST" value="<%=ApplyStatus.REQUEST %>"/>
<c:set var="AUDITING" value="<%=ApplyStatus.AUDITING %>"/>
<c:set var="SCHEDULE" value="<%=ApplyStatus.SCHEDULE %>"/>
<c:set var="BE_IN_PROGRESS" value="<%=ApplyStatus.BE_IN_PROGRESS %>"/>
<c:set var="FINISH" value="<%=ApplyStatus.FINISH %>"/>
<c:set var="BROWSE" value="<%=ApplyStatus.BROWSE %>"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>表单节点管理</title>
</head>
<body>

	<div id="main-content">
		<div class="container">
			<div class="row">
				<!-- 页面内容-->
				<div id="content" class="col-lg-12">
			
					<!-- PAGE HEADER-->
					<div class="row">
						<div class="col-sm-12">
							<div class="page-header">
								<!-- BREADCRUMBS -->
								<ul class="breadcrumb">
									<li>
										<i class="fa fa-home"></i>
										<a href="${ctx}/workbench">主页</a>
									</li>
									<li>
										<a href="${ctx}/form">表单列表</a>
									</li>
									<li>
										表单节点列表
									</li>
								</ul>
								<!-- /BREADCRUMBS -->
								
							</div>
						</div>
					</div>
					<!-- /PAGE HEADER -->
					
					<!-- Alerts Message -->
					<c:if test="${not empty message}">
					<div class="alert alert-block alert-info fade in">
						<a class="close" data-dismiss="alert" href="#" aria-hidden="true">&times;</a>
						<h4 style="margin: 0;"><i class="fa fa-check-square-o"></i> ${message}</h4>
					</div>
					</c:if>
					<!-- /Alerts Message -->

					<!-- EXPORT TABLES -->
					<div class="row">
						<div class="col-md-12">
							<!-- BOX -->
							<div class="box border primary">
								<div class="box-title">
									<h4><i class="fa fa-table"></i>表单节点列表</h4>
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
									
									
									<br/>
									
									<table  class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th>节点</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>${APPLY_REQUEST.value }</td>
												<td><a href="${ctx}/node/permission?formId=${formId}&applyStatus=${APPLY_REQUEST}">授权设置</a></td>
											</tr>
											<tr>
												<td>${AUDITING.value }</td>
												<td><a href="${ctx}/node/permission?formId=${formId}&applyStatus=${AUDITING}">授权设置</a></td>
											</tr>
											<tr>
												<td>${SCHEDULE.value }</td>
												<td><a href="${ctx}/node/permission?formId=${formId}&applyStatus=${SCHEDULE}">授权设置</a></td>
											</tr>
											<tr>
												<td>${BE_IN_PROGRESS.value }</td>
												<td><a href="${ctx}/node/permission?formId=${formId}&applyStatus=${BE_IN_PROGRESS}">授权设置</a></td>
											</tr>
											<tr>
												<td>${FINISH.value }</td>
												<td><a href="${ctx}/node/permission?formId=${formId}&applyStatus=${FINISH}">授权设置</a></td>
											</tr>
											<tr>
												<td>${BROWSE.value }</td>
												<td><a href="${ctx}/node/permission?formId=${formId}&applyStatus=${BROWSE}">授权设置</a></td>
											</tr>
										</tbody>
									</table>
									
									<div class="row">
										<div class="col-sm-12">
											<div class="pull-left">
												<input id="cancel_btn" class="btn btn-default" type="button" value="返回" onclick="history.back()"/>
											</div>
										</div>
									</div>	
								</div>
							</div>
							<!-- /BOX -->
						</div>
					</div>
					<!-- /EXPORT TABLES -->
					
					<div class="footer-tools">
						<span class="go-top">
							<i class="fa fa-chevron-up"></i> Top
						</span>
					</div>
				
				</div>
				<!-- 页面内容-->
			</div>
		</div>
	</div>

	<!-- JAVASCRIPTS -->
	<!-- 引入公共JS脚本 -->
	<%@ include file="/WEB-INF/layouts/include_script.jsp"%>
	
	<!-- 自定义JS脚本 -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {
			//如果页面无需设置效果，可以不设置 App.setPage ，如设置 App.setPage 而页面缺少对应的元素，会导致JS错误.
			//App.setPage("widgets_box");  //设置当前启动的页面
			
			App.setHasSub("forms-manager");//设置一级菜单目录ID
			App.setSubMenu("forms-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置项目路径
			App.init(); //初始化元素以及插件
		});
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>