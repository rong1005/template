<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<title>模板示例 :: 登录页</title>
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
	
	<link href="${ctx}/static/font-awesome/css/font-awesome.min.css" rel="stylesheet">
	<!-- DATE RANGE PICKER -->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/js/bootstrap-daterangepicker/daterangepicker-bs3.css" />
	<!-- UNIFORM -->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/js/uniform/css/uniform.default.min.css" />
	<!-- ANIMATE -->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/animatecss/animate.min.css" />
	<!-- FONTS 
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700' rel='stylesheet' type='text/css'>
	-->
</head>
<body class="login">	
	<!-- PAGE -->
	<section id="page">
			<!-- HEADER -->
			<header>
				<!-- NAV-BAR -->
				<div class="container">
					<div class="row">
						<div class="col-md-4 col-md-offset-4">
							<div id="logo">
								<a href="index.html"><img src="${ctx}/static/img/logo/logo-alt.png" height="40" alt="logo name" /></a>
							</div>
						</div>
					</div>
				</div>
				<!--/NAV-BAR -->
			</header>
			<!--/HEADER -->
			<!-- LOGIN -->
			<section id="login" class="visible">
				<div class="container">
					<div class="row">
						<div class="col-md-4 col-md-offset-4">
							<div class="login-box-plain">
								<h2 class="bigintro">Sign In</h2>
								<div class="divide-40">
								<%
								String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
								if(error != null){
								%>
								<h5 class="text-danger" style="line-height: 40px;"><i class="fa fa-times"></i> 登录失败,帐号或密码错误</h5>
								<%
								}
								%>
								
								<c:if test="${not empty message}">
								<h5 class="text-success" style="line-height: 40px;"><i class="fa fa-check-square-o"></i> ${message}</h5>
								</c:if>
								</div>
								<form role="form" id="loginForm" action="${ctx}/login" method="post">
								  <div class="form-group">
									<label for="username">手机号 / 账号 / 邮箱</label>
									<i class="fa fa-envelope"></i>
									<input type="text" name="username" class="form-control" id="username" value="${username}" >
								  </div>
								  <div class="form-group"> 
									<label for="password">登录密码</label>
									<i class="fa fa-lock"></i>
									<input type="password" name="password" class="form-control" id="password" >
								  </div>
								  <div class="form-actions">
									<label class="checkbox"> <input type="checkbox" class="uniform" value=""> 十天内免登录</label>
									<button type="submit" class="btn btn-danger"> 登 录 </button>
								  </div>
								</form>
								<!-- SOCIAL LOGIN -->
								<div class="divide-20"></div>
								<div class="center">
									<strong>无需注册，直接使用社交账号登录</strong>
								</div>
								<div class="divide-20"></div>
								<div class="social-login center">
									<a class="btn btn-primary btn-lg">
										<i class="fa fa-weibo"></i>
									</a>
									<a class="btn btn-info btn-lg">
										<i class="fa fa-twitter"></i>
									</a>
									<a class="btn btn-danger btn-lg">
										<i class="fa fa-google-plus"></i>
									</a>
								</div>
								<!-- /SOCIAL LOGIN -->
								<div class="login-helpers">
									<a href="#" onclick="swapScreen('forgot');return false;">忘记密码?</a> 
									<br/>
									&emsp;&emsp;(管理员: <b>admin/admin</b>, 普通用户: <b>user/user</b>)
									<br/>
									没有账号? <a href="#" onclick="swapScreen('register');return false;">马上注册!</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<!--/LOGIN -->
			<!-- REGISTER -->
			<section id="register">
				<div class="container">
					<div class="row">
						<div class="col-md-4 col-md-offset-4">
							<div class="login-box-plain">
								<h2 class="bigintro"> 欢 迎 加 入 </h2>
								<div class="divide-40"></div>
								<form role="form" id="registerForm" action="${ctx}/register" method="post">
								  <div class="form-group">
									<label for="exampleInputName">登录账号</label>
									<i class="fa fa-font"></i>
									<input type="text" name="loginName" class="form-control" id="loginName" >
								  </div>
								  <div class="form-group">
									<label for="exampleInputUsername">姓名</label>
									<i class="fa fa-user"></i>
									<input type="text" name="name" class="form-control" id="name" >
								  </div>
								  <div class="form-group">
									<label for="exampleInputEmail1">邮箱地址</label>
									<i class="fa fa-envelope"></i>
									<input type="email" name="email" class="form-control" id="email" >
								  </div>
								  <div class="form-group"> 
									<label for="exampleInputPassword1">创建密码</label>
									<i class="fa fa-lock"></i>
									<input type="password" name="plainPassword" class="form-control" id="plainPassword" >
								  </div>
								  <div class="form-group"> 
									<label for="exampleInputPassword2">确认密码</label>
									<i class="fa fa-check-square-o"></i>
									<input type="password" name="confirmPassword" class="form-control" id="confirmPassword" >
								  </div>
								  <div>
									<label class="checkbox"> <input type="checkbox" class="uniform" value=""> 同意<a href="#">"服务条款"</a> 和 <a href="#">"用户须知"</a>、<a href="#">"隐私权相关政策"</a></label>
									<button type="submit" class="btn btn-success"> 马 上 注 册 </button>
								  </div>
								</form>
								<!-- SOCIAL REGISTER -->
								<div class="divide-20"></div>
								<div class="center">
									<strong>无需注册，直接使用社交账号登录</strong>
								</div>
								<div class="divide-20"></div>
								<div class="social-login center">
									<a class="btn btn-primary btn-lg">
										<i class="fa fa-weibo"></i>
									</a>
									<a class="btn btn-info btn-lg">
										<i class="fa fa-twitter"></i>
									</a>
									<a class="btn btn-danger btn-lg">
										<i class="fa fa-google-plus"></i>
									</a>
								</div>
								<!-- /SOCIAL REGISTER -->
								<div class="login-helpers">
									<a href="#" onclick="swapScreen('login');return false;"> 回到登录页 </a> <br>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<!--/REGISTER -->
			<!-- FORGOT PASSWORD -->
			<section id="forgot">
				<div class="container">
					<div class="row">
						<div class="col-md-4 col-md-offset-4">
							<div class="login-box-plain">
								<h2 class="bigintro">找回密码</h2>
								<div class="divide-40"></div>
								<form role="form">
								  <div class="form-group">
									<label for="exampleInputEmail1">输入您的邮箱地址</label>
									<i class="fa fa-envelope"></i>
									<input type="email" class="form-control" id="exampleInputEmail1" >
								  </div>
								  <div class="form-actions">
									<button type="submit" class="btn btn-info">Send Me Reset Instructions</button>
								  </div>
								</form>
								<div class="login-helpers">
									<a href="#" onclick="swapScreen('login');return false;"> 回到登录页 </a> <br>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<!-- FORGOT PASSWORD -->
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
	
	
	<!-- UNIFORM -->
	<script type="text/javascript" src="${ctx}/static/js/uniform/jquery.uniform.min.js"></script>
	
	<!-- JQUERY-VALIDATE -->
	<script type="text/javascript" src="${ctx}/static/js/jquery-validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/jquery-validate/additional-methods.min.js"></script>
	
	<!-- COOKIE -->
	<script type="text/javascript" src="${ctx}/static/js/jQuery-Cookie/jquery.cookie.min.js"></script>
	
	<script type="text/javascript">
	$(".uniform").uniform();
		jQuery(document).ready(function() {		
			$(".uniform").uniform();
			
			//登录校验.
			$("#loginForm").validate({
				errorPlacement: function(error, element) { 
				    $(element).attr("data-content",$(error).html());
				    $(element).popover({
				    	placement : 'left',
				    	trigger : 'focus'
				    });
				},
				rules: {
					username: "required",
					password: "required"
				},
				messages: {
					username: "请输入您的登录账号",
					password: "请输入您的登录密码"
				}
			});
		});

		function swapScreen(id) {
			jQuery('.visible').removeClass('visible animated fadeInUp');
			jQuery('#'+id).addClass('visible animated fadeInUp');
		}
		
		//清除侧边栏的历史样式Cookie.
		$.cookie('mini_sidebar',null); 
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>