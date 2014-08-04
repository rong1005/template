<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>委托申请管理</title>
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
										委托申请列表
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
									<h4><i class="fa fa-table"></i>委托申请列表</h4>
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
									<form class="form-inline" action="#">
										<div class="form-group">
											<input type="text" name="search_LIKE_chApplyName" class="form-control" value="${param.search_LIKE_chApplyName}" placeholder="委托名称(中文)" />
										</div>
										<div class="form-group">
											<input type="text" name="search_LIKE_enApplyName" class="form-control" value="${param.search_LIKE_enApplyName}" placeholder="委托名称(英文)" />
										</div>
										<button type="submit" class="btn btn-inverse" id="search_btn"> 查 询 </button>
										<tags:sort/>
									</form>
									
									<br/>
									
									<table class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th>委托编号</th>
												<th>表单</th>
												<th>委托时间</th>
												<th>委托名称</th>
												<th>委托单位</th>
												<th>检验项目</th>
												<th>检验类别</th>
												<th>审核</th>
												<th>管理</th>
											</tr>
										</thead>
										<tbody>
										<c:forEach items="${applys.content}" var="apply">
											<tr>
												<td>${apply.id}</td>
												<td>${apply.form.name}<br/>${apply.form.tableName}</td>
												<td>${apply.createTime}</td>
												<td>${apply.chApplyName}<br/>${apply.enApplyName}</td>
												<td>${apply.chConsigner}<br/>${apply.enConsigner}</td>
												<td>${apply.chTestItems}<br/>${apply.enTestItems}</td>
												<td>${apply.chCheckType}<br/>${apply.enCheckType}</td>
												<td>
													<c:if test="${empty apply.isPass }">未审核</c:if>
													<c:if test="${not empty apply.isPass }">${apply.isPass.value}</c:if>
												</td>
												<td>
													<a href="${ctx}/apply/update/${apply.id}">修改</a> / 
													<a href="${ctx}/apply/delete/${apply.id}" onclick="return confirm('是否删除该委托申请记录？')" >删除</a> / 
													<a href="${ctx}/sample/${apply.id}">样品管理</a> / 
													<a href="${ctx}/apply/audit/${apply.id}">审核</a>
												</td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
									<div class="row">
										<div class="col-sm-12">
											<div class="pull-right">
												<tags:pagination page="${applys}" paginationSize="5"/>
											</div>
											
											<div class="pull-left">
												<a class="btn btn-info" href="${ctx}/form/create">委托申请</a>
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
			App.setSubMenu("applys-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置项目路径
			App.init(); //初始化元素以及插件
		});
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>