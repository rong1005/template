<%@page import="com.cn.template.xutil.enums.SampleStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<c:set var="SEND_BACK" value="<%=SampleStatus.SEND_BACK %>"/>
<c:set var="USELESS" value="<%=SampleStatus.USELESS %>"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>样品管理</title>
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
									<li>样品处理</li>
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
							<h4><i class="fa fa-table"></i>样品处理</h4>
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
							<form id="inputForm" class="form-horizontal" action="${ctx}/sample/handle/${applyId}" method="post">
								<div class="row">
									<div class="col-sm-12">
										<textarea rows="5" cols="10" class="form-control" name="samples" placeholder="样品编号（格式：1403-0001-01;1403-0001-02;1403-0002-01;）"></textarea>
									</div>
								</div>
								<hr>
								<div class="row">
									<div class="col-sm-12">
										<label class="radio-inline">
											<input type="radio" class="uniform" id="apply_checkType" name="status" value="${SEND_BACK}"> ${SEND_BACK.value} 
										</label>
										<label class="radio-inline">
											<input type="radio" class="uniform" id="apply_checkType" name="status" value="${USELESS }"> ${USELESS.value}
										</label>
									</div>
								</div>	
								<hr>
								<input id="submit_btn" class="btn btn-info" type="submit" value="提交"/>&nbsp;
								<input id="cancel_btn" class="btn btn-default" type="button" value="返回" onclick="history.back()"/>				  
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
			App.setPage("sample_handle");  //设置当前启动的页面
			
			App.setHasSub("forms-manager");//设置一级菜单目录ID
			App.setSubMenu("applys-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置收费路径
			App.init(); //初始化元素以及插件
			
		});
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>
