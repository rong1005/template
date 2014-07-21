<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>委托申请</title>
	<!-- UNIFORM -->
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
										<a href="${ctx}/form">表单列表</a>
									</li>
									<li>委托申请</li>
								</ul>
								<!-- /BREADCRUMBS -->
								
							</div>
						</div>
					</div>
					<!-- /PAGE HEADER -->
					
					<div class="box border primary">
						<div class="box-title">
							<h4><i class="fa fa-table"></i>委托申请</h4>
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
							<form id="inputForm" class="form-horizontal" action="${ctx}/form/${action}" method="post">
								<input type="hidden" name="formId" value="${formId}"/>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">表单名称</label>
									<div class="col-sm-10">
										<input type="text" id="apply_name" name="name" class="form-control" placeholder="测试名称"/>
									</div>
								</div>
								<hr>
								<c:forEach var="field" items="${fields }">
								
								<div class="form-group">
									<label class="col-sm-2 control-label">${field.chViewName}</label>
									<div class="col-sm-10">
										<input type="text" id="apply_ch_${field.name}" name="ch_${field.name}"  class="form-control" placeholder="${field.chViewName}"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">${field.enViewName}</label>
									<div class="col-sm-10">
										<input type="text" id="apply_en_${field.name}" name="en_${field.name}"  class="form-control" placeholder="${field.enViewName}"/>
									</div>
								</div>
								<hr>
								</c:forEach>
												  
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<input id="submit_btn" class="btn btn-info" type="submit" value="提交"/>&nbsp;
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
	
	<!-- UNIFORM -->
	<script type="text/javascript" src="${ctx}/static/js/uniform/jquery.uniform.min.js"></script>

	<!-- 自定义JS脚本 -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {
			//如果页面无需设置效果，可以不设置 App.setPage ，如设置 App.setPage 而页面缺少对应的元素，会导致JS错误.
			App.setPage("apply_forms");  //设置当前启动的页面
			
			App.setHasSub("forms-manager");//设置一级菜单目录ID
			App.setSubMenu("forms-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置表单路径
			App.init(); //初始化元素以及插件
			
		});
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>
