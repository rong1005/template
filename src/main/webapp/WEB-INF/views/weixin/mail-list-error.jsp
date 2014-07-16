<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
 <!-- 以UTF-8 的格式解析内容（否则直接打开会是乱码） -->
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>内部邮箱-收件箱</title>
  <!-- 页面宽度将被设置到设备屏幕的像素宽度 -->
  <!-- <meta name="viewport" content="width=device-width, initial-scale=1"> -->
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
  <!-- JQuery Mobile 的资源引入 -->
  <link rel="stylesheet" href="${ctx}/static/js/jquery.mobile-1.4.2/jquery.mobile-1.4.2.min.css" />
  <script src="${ctx}/static/js/jquery/jquery-2.1.1.min.js"></script>
  <script src="${ctx}/static/js/jquery.mobile-1.4.2/jquery.mobile-1.4.2.min.js"></script>
</head>

<body>
	<div data-role="page">
		<div data-role="header" data-position="fixed"> 
			<a href="#" data-role="button" data-icon="home" data-iconpos="notext"></a>
			<h1>收件箱</h1> 
			<a href="#" onclick="window.location.reload()" data-role="button" data-icon="refresh"  data-iconpos="notext">刷新</a>
		</div>
		<div role="main" class="ui-content">
		${message}
		</div>
	</div>
</body>

</html>