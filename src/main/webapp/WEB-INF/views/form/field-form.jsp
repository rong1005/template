<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>字段管理</title>
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
										<a href="${ctx}/field">字段列表</a>
									</li>
									<li>创建字段</li>
								</ul>
								<!-- /BREADCRUMBS -->
								
							</div>
						</div>
					</div>
					<!-- /PAGE HEADER -->
					
					<div class="box border primary">
						<div class="box-title">
							<h4><i class="fa fa-table"></i>创建字段</h4>
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
							<form id="inputForm" class="form-horizontal" action="${ctx}/field/${action}" method="post">
								<input type="hidden" name="id" value="${field.id}"/>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">字段名称</label>
									<div class="col-sm-10">
										<input type="text" id="field_name" name="name" value="${field.name}" class="form-control" placeholder="字段名称"/>
									</div>
								</div>
	
								<div class="form-group">
									<label class="col-sm-2 control-label">字段描述</label> 
									<div class="col-sm-10">
										<textarea rows="3" cols="5" id="description" name="description" class="autosize countable form-control" placeholder="字段描述" data-limit="100"></textarea>
										<p class="help-block">您还可以输入 <span id="counter"></span> 字.</p> 
									</div>
								</div>
												  
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
	<!-- TextArea 自动调整SIZE -->
	<script type="text/javascript" src="${ctx}/static/js/autosize/jquery.autosize.min.js"></script>
	<!-- TextArea 计算剩余字数 -->
	<script type="text/javascript" src="${ctx}/static/js/countable/jquery.simplyCountable.min.js"></script>
	
	<!-- 自定义JS脚本 -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {
			
			App.setHasSub("forms-manager");//设置一级菜单目录ID
			App.setSubMenu("forms-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置字段路径
			App.init(); //初始化元素以及插件
			
			$("#field_name").focus();
			//表单校验.
			$("#inputForm").validate({
				errorPlacement: function(error, element) { 
				    $(element).attr("data-content",$(error).html());
				    $(element).popover({
				    	placement : 'left',
				    	trigger : 'focus'
				    });
				},
				rules: {
					name: {
						required: true,
						minlength: 3
						},
					description: {
						required: true,
						minlength: 10
						}
				},
				messages: {
					name: {
						required: "请输入字段名称",
						minlength: jQuery.format("名称长度不能小于{0}个字符")
						},
					description: {
						required: "请描述您的字段",
						minlength: jQuery.format("描述不能小于{0}个字 符")
						}
				}
			});
			
		});
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>
