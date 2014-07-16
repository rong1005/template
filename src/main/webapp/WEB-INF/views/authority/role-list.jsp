<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>角色管理</title>
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
										角色列表
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
									<h4><i class="fa fa-table"></i>角色列表</h4>
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
									<form class="form-inline" action="#">
										<div class="form-group">
											<input type="text" name="search_LIKE_name" class="form-control" value="${param.search_LIKE_name}" placeholder="名称" />
										</div>
										<button type="submit" class="btn btn-inverse" id="search_btn"> 查 询 </button>
										<tags:sort/>
									</form>
									
									<br/>
									
									<table  class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th>名称</th>
												<th>代码</th>
												<th>是否默认</th>
												<th>类型</th>
												<th>管理</th>
											</tr>
										</thead>
										<tbody>
										<c:forEach items="${roles.content}" var="role">
											<tr>
												<td>${role.name}</td>
												<td>${role.code}</td>
												<td>${role.defaultRole.value}</td>
												<td>${role.roleType.value}</td>
												<td>
													<a href="${ctx}/role/update/${role.id}">修改</a> / 
													<a href="${ctx}/role/delete/${role.id}" onclick="return confirm('是否删除该角色？')">删除</a> / 
													<a href="${ctx}/role/sidebar/${role.id}">管理菜单</a>
												</td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
									<div class="row">
										<div class="col-sm-12">
											<div class="pull-right">
												<tags:pagination page="${roles}" paginationSize="5"/>
											</div>
											
											<div class="pull-left">
												<a class="btn btn-info in-loading" href="${ctx}/role/create">创建角色</a>
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
			
			App.setHasSub("other-page");//设置一级菜单目录ID
			App.setSubMenu("roles-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置项目路径
			App.init(); //初始化元素以及插件
		});
	</script>
	<!-- /JAVASCRIPTS -->	
</body>
</html>
