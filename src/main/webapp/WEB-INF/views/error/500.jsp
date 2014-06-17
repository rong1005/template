<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>模板示例 :: 500系统内部错误</title>
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
									<li>500 Error</li>
								</ul>
							</div>
						</div>
					</div>
					<!-- /PAGE HEADER -->
					<div class="row">
						<div class="col-md-10 not-found text-center">
							<div style="color: #d9534f;font-size: 128px;font-weight: 300;letter-spacing: -10px;line-height: 128px;margin-top: 0;top: 35px;right: 50%;">500</div>
						</div>
						<div class="col-md-4 col-md-offset-4 not-found text-center">
							<div class="content">
								<h3>警告！警告！系统受损，发生内部错误！</h3>
								<p>
									 工程师集合！列队！出发...... <br />
						 			您可以先返回，或者查阅其他资料.
								</p>
								
								<div class="btn-group">
									<a href="javascript:history.back();" class="btn btn-danger"><i class="fa fa-chevron-left"></i> 返 回 </a> 
									<a href="index.html" class="btn btn-inverse"><i class="fa fa-home"></i> 主 页 </a>
					  			</div>
							</div>
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
			App.setPage("widgets_box");  //Set current page
			App.setPath("${ctx}/static");  //设置项目路径
			App.init(); //初始化元素以及插件
		});
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>