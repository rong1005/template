<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>EChart 2.0 柱状图</title>
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
										EChart 2.0 柱状图
									</li>
								</ul>
								<!-- /BREADCRUMBS -->
							</div>
						</div>
					</div>
					<!-- /PAGE HEADER -->
					
					
					<div class="row">
						<div class="col-md-12">
							<!-- BOX -->
							<div class="box border primary">
								<div class="box-title">
									<h4><i class="fa fa-table"></i>标准柱状图</h4>
									<div class="tools hidden-xs">
										<a href="javascript:;" class="collapse">
											<i class="fa fa-chevron-up"></i>
										</a>
										<a href="javascript:;" class="remove">
											<i class="fa fa-times"></i>
										</a>
									</div>
								</div>
								<div class="box-body" id="bar1" style="height:400px;width:100%">

								</div>
							</div>
							<!-- /BOX -->
						</div>
					</div>
					
					<div class="row">
						<div class="col-md-12">
							<!-- BOX -->
							<div class="box border primary">
								<div class="box-title">
									<h4><i class="fa fa-table"></i>多系列层叠 柱状图</h4>
									<div class="tools hidden-xs">
										<a href="javascript:;" class="collapse">
											<i class="fa fa-chevron-up"></i>
										</a>
										<a href="javascript:;" class="remove">
											<i class="fa fa-times"></i>
										</a>
									</div>
								</div>
								<div class="box-body" id="bar12" style="height:400px;width:100%">

								</div>
							</div>
							<!-- /BOX -->
						</div>
					</div>
					
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
	
	<script src="${ctx}/static/js/echarts/echarts-plain-original.js"></script>
	
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
			
			
			//生成标准柱状图
			jQuery.ajax({
				url: "${ctx}/echart/bar1",
				type: "post",
				dataType: "json",
				success: function(msg) {
					var myChart = echarts.init(document.getElementById('bar1'));
					myChart.setOption(msg);
				}
			});
			
			//生成多系列层叠 柱状图 
			//(返回的格式非正规的Json格式，如果指定datatype 为Json，则出现返回失败的情况)
			//在这里不指定datatype格式：jQuery 将自动根据 HTTP 包 MIME 信息返回 responseXML 或 responseText，并作为回调函数参数传递
			jQuery.ajax({
				url: "${ctx}/echart/bar12",
				type: "post",
				success: function(msg) {
					var myChart12 = echarts.init(document.getElementById('bar12'));
					myChart12.setOption(eval("("+msg+")"));
				}
			});
			
		});
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>