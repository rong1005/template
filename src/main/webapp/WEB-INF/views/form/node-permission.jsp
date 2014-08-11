<%@page import="com.cn.template.xutil.enums.PermissionType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>字段授权管理</title>
	<!-- UNIFORM -->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/js/uniform/css/uniform.default.min.css" />
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
										<a href="${ctx}/node?formId=${formId}">表单节点列表</a>
									</li>
									<li>
										字段授权列表
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
									<h4><i class="fa fa-table"></i>字段授权列表</h4>
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
									<form id="inputForm" action="${ctx}/node/permission" method="post">
									<input type="hidden" name="applyStatus" value="${applyStatus}" />
									<input type="hidden" name="formId" value="${formId}" />
									<table  class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th>字段</th>
												<th>名称</th>
												<th>授权</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${fields}" var="field" varStatus="status">
											<tr>
												<td>${field.name}</td>
												<td>${field.chViewName}(${field.enViewName})</td>
												<td>
													<input type="hidden" name="nodes[${status.index }].field.id" value="${field.id}"/>
													<c:if test="${not empty nodeMap[field.id]}">
														<input type="hidden" name="nodes[${status.index }].id" value="${nodeMap[field.id].id}"/>
													</c:if>
													<c:forEach items="<%=PermissionType.values() %>" var="permissionType">
														<label class="radio-inline"> 
															<input type="radio" name="nodes[${status.index }].permissionType" class="uniform" <c:if test="${nodeMap[field.id].permissionType eq permissionType}">checked="checked"</c:if> value="${permissionType }">${permissionType.value }
														</label>
													</c:forEach> 
												</td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
										
									<div class="row">
										<div class="col-sm-12">
											<div class="pull-left">
												<input id="submit_btn" class="btn btn-info" type="submit" value="提交"/>&nbsp;
												<input id="cancel_btn" class="btn btn-default" type="button" value="返回" onclick="history.back()"/>
											</div>
										</div>
									</div>	
									</form>
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
	
	<!-- UNIFORM -->
	<script type="text/javascript" src="${ctx}/static/js/uniform/jquery.uniform.min.js"></script>
	
	<!-- 自定义JS脚本 -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {
			//如果页面无需设置效果，可以不设置 App.setPage ，如设置 App.setPage 而页面缺少对应的元素，会导致JS错误.
			App.setPage("node-permission");  //设置当前启动的页面
			
			App.setHasSub("forms-manager");//设置一级菜单目录ID
			App.setSubMenu("forms-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置项目路径
			App.init(); //初始化元素以及插件
		});
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>