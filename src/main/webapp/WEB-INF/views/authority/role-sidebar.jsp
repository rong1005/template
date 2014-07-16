<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>角色菜单管理</title>
	<!-- UNIFORM 优化表单样式 -->
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
										<a href="${ctx}/role">角色列表</a>
									</li>
									<li>角色菜单</li>
								</ul>
								<!-- /BREADCRUMBS -->								
							</div>
						</div>
					</div>
					<!-- /PAGE HEADER -->

					<!-- EXPORT TABLES -->
					<div class="row">
						<div class="col-md-12">
							<!-- BOX -->
							<div class="box border primary">
								<div class="box-title">
									<h4><i class="fa fa-table"></i>角色菜单</h4>
									<div class="tools hidden-xs">
										<a href="javascript:;" class="reload">
											<i class="fa fa-refresh"></i>
										</a>
										<a href="javascript:;" class="collapse">
											<i class="fa fa-chevron-up"></i>
										</a>
										<a href="javascript:;" class="remove">
											<i class="fa fa-times"></i>
										</a>
									</div>
								</div>
								<div class="box-body">
									<form id="inputForm" class="form-horizontal" action="${ctx}/role/sidebar" method="post">
										<input type="hidden" name="id" value="${role.id}"/>
										
										<c:forEach items="${topSidebar }" var="top">
										<div class="form-group">
											<div class="col-sm-2" style="text-align: right;">
												<label class="checkbox-inline"> <input type="checkbox" name="sidebars.id" class="uniform" value="${top.id}" <c:if test="${isExist[top.id] }">checked="checked"</c:if>>${top.name}</label>
											</div>
										</div>
										
										<c:if test="${not empty top.sidebars }">
										<div class="form-group">
											<div class="col-sm-offset-2 col-sm-10">
												<c:forEach items="${top.sidebars}" var="child">
												<label class="checkbox-inline"> <input type="checkbox" name="sidebars.id" class="uniform" value="${child.id}" <c:if test="${isExist[child.id] }">checked="checked"</c:if>>${child.name}</label>
												</c:forEach>
											</div>
										</div>
										</c:if>
										
										</c:forEach>

										<div class="form-group">
											<div class="col-sm-offset-2 col-sm-10">
												<input id="submit_btn" class="btn btn-success in-loading" type="submit" value="提交" />&nbsp; 
												<input id="cancel_btn" class="btn btn-default in-loading" type="button" value="返回" onclick="history.back()" />
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
	
	<!-- UNIFORM 优化表单样式 -->
	<script type="text/javascript" src="${ctx}/static/js/uniform/jquery.uniform.min.js"></script>
	
	<!-- 自定义JS脚本 -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {
			//如果页面无需设置效果，可以不设置 App.setPage ，如设置 App.setPage 而页面缺少对应的元素，会导致JS错误.
			App.setPage("role_sidebar");  //设置当前启动的页面
			
			App.setHasSub("other-page");//设置一级菜单目录ID
			App.setSubMenu("roles-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置项目路径
			App.init(); //初始化元素以及插件
		});
	</script>
	<!-- /JAVASCRIPTS -->	
</body>
</html>