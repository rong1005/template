<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<title>模板示例 :: 404页面不存在</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=no">
	<meta name="description" content="">
	<meta name="author" content="Libra">
	<!-- STYLESHEETS -->
	<!--[if lt IE 9]>
		<script src="${ctx}/static/js/flot/excanvas.min.js"></script>
		<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
	<![endif]-->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/cloud-admin.css" >
	
	<link href="${ctx}/static/font-awesome/css/font-awesome.min.css" rel="stylesheet">
	<!-- DATE RANGE PICKER -->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/js/bootstrap-daterangepicker/daterangepicker-bs3.css" />
	<!-- FONTS Google字体 速度巨慢，先注释！！
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700' rel='stylesheet' type='text/css'>
	 -->
</head>
<body id="not-found-bg">
	<div class="overlay"></div>
	<!-- PAGE -->
	<section id="page">
			<div class="row">
				<div class="col-md-12">
					<div class="divide-100"></div>
				</div>
			</div>
			<div class="row">
				<div class="container">
				<div class="col-md-12 not-found">
				   <div class="error">
					  404
				   </div>
				</div>
				<div class="col-md-5 not-found">
				   <div class="content">
					  <h3>啊...页面被外星人带走了！<i class="fa fa-rocket"></i></h3>
					  <p>
						 不用担心,我们的星际舰队已经出发<i class="fa fa-fighter-jet"></i> ,很快可以帮您找回！<br />
						 您可以先返回，或者查阅其他资料.
					  </p>
					  <form action="#">
						 <div class="input-group">
							<input type="text" class="form-control" placeholder="search here...">
							<span class="input-group-btn">                   
								<button type="submit" class="btn btn-danger"><i class="fa fa-search"></i></button>
							</span>
						 </div>
					  </form>
					  <p />
					  <div class="btn-group">
						<a href="javascript:history.back();" class="btn btn-danger"><i class="fa fa-chevron-left"></i> 返 回 </a> 
						<a href="index.html" class="btn btn-inverse"><i class="fa fa-home"></i> 主 页 </a>
					  </div>
				   </div>
				</div>
				</div>
			</div>
	</section>
	<!--/PAGE -->
	<!-- JAVASCRIPTS -->
	<!-- Placed at the end of the document so the pages load faster -->
	<!-- JQUERY -->
	<script src="${ctx}/static/js/jquery/jquery-2.1.1.min.js"></script>
	<!-- JQUERY UI-->
	<script src="${ctx}/static/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<!-- BOOTSTRAP -->
	<script src="${ctx}/static/bootstrap-dist/js/bootstrap.min.js"></script>
	
		
	<!-- DATE RANGE PICKER -->
	<script src="${ctx}/static/js/bootstrap-daterangepicker/moment.min.js"></script>
	
	<script src="${ctx}/static/js/bootstrap-daterangepicker/daterangepicker.min.js"></script>
	<!-- SLIMSCROLL -->
	<script type="text/javascript" src="${ctx}/static/js/jQuery-slimScroll-1.3.0/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/jQuery-slimScroll-1.3.0/slimScrollHorizontal.min.js"></script>
	<!-- CUSTOM SCRIPT -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {		
			App.setPage("widgets_box");  //Set current page
			App.init(); //Initialise plugins and elements
		});
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>