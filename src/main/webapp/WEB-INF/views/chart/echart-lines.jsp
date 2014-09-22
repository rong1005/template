<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>EChart 2.0 折线图</title>
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
										EChart 2.0 折线图
									</li>
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
									<h4><i class="fa fa-table"></i>折线图一</h4>
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
			App.setHasSub("charts-manager");//设置一级菜单目录ID
			App.setSubMenu("echart-bars");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置项目路径
			App.init(); //初始化元素以及插件
		});
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>