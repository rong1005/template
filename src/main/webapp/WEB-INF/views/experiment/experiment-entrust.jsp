<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>实验委托平台</title>
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
										<a href="${ctx}/apply/entrust">实验委托平台</a>
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
					
					<div class="box border primary">
						<div class="box-title">
							<h4><i class="fa fa-table"></i>实验委托申请</h4>
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
							<div class="row">
								<c:forEach items="${forms}" var="form" varStatus="status">
								<div class="col-lg-12">
									 <div class="dashbox panel panel-default">
										<div class="panel-body">
										   <div class="panel-left <c:if test="${status.index%2 eq 0 }">red</c:if>">
												<a href="${ctx}/apply/create/${form.id}"><i class="fa fa-pencil-square-o fa-3x"> 申请</i></a>
										   </div>
										   <div class="panel-right">
												<div class="number">${form.prefix } -- ${form.name }</div>
												<div class="title">格式：${form.formFormat.value }</div>
												<a href="#" class="label label-success">
													预览 <i class="fa fa-eye"></i>
												</a>
										   </div>
										</div>
									 </div>
								</div>
								</c:forEach>
							</div>
						</div>	
					</div>
					
					<div class="box border primary">
						<div class="box-title">
							<h4><i class="fa fa-table"></i>实验委托查询</h4>
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
							<form class="form-inline" action="${ctx}/apply/search">
								<div class="form-group">
									<input type="text" name="applyCode" class="form-control" value="${applyCode}" placeholder="申请编号" />
								</div>
								<div class="form-group">
									<input type="text" name="experimentCode" class="form-control" value="${experimentCode}" placeholder="实验编号" />
								</div>
								<button type="submit" class="btn btn-inverse" id="search_btn"> 查 询 </button>
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
	<!-- 自定义JS脚本 -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {		
			//App.setPage("widgets_box");
			App.setHasSub("forms-manager"); //设置一级菜单目录ID
			App.setSubMenu("apply-entrust");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置项目路径
			App.init(); //初始化元素以及插件
		});
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>