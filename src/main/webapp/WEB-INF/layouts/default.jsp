<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<title>模板示例 :: <sitemesh:title/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=no">
	<meta name="description" content="">
	<meta name="author" content="Libra">
	<!-- STYLESHEETS -->
	<!--[if lt IE 9]>
		<script src="${ctx}/static/js/flot/excanvas.min.js"></script>
		<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
	<![endif]-->
	<link type="image/x-icon" href="${ctx}/static/img/logo/favicon.ico" rel="shortcut icon">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/cloud-admin.css" >
	<link rel="stylesheet" type="text/css"  href="${ctx}/static/css/themes/default.css" id="skin-switcher" >
	<link rel="stylesheet" type="text/css"  href="${ctx}/static/css/responsive.css" >
	
	<link href="${ctx}/static/font-awesome/css/font-awesome.min.css" rel="stylesheet">
	
	<!-- FONTS Google字体 速度巨慢，先注释！！
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700' rel='stylesheet' type='text/css'>
	 -->
	 
	<!-- 弹出框 -->
	<link href="${ctx}/static/js/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" />
  	<link href="${ctx}/static/js/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" />
	
	<sitemesh:head/>
</head>

<body>

	<%@ include file="/WEB-INF/layouts/header.jsp"%>
	
	<section id="page">
	
	<%@ include file="/WEB-INF/layouts/sidebar.jsp"%>
	
	<sitemesh:body/>
	
	</section>
	
</body>
</html>