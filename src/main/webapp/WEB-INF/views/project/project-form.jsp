<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>项目管理</title>
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
										<a href="${ctx}/project">项目列表</a>
									</li>
									<li>创建项目</li>
								</ul>
								<!-- /BREADCRUMBS -->
								
							</div>
						</div>
					</div>
					<!-- /PAGE HEADER -->
					
					<div class="box border primary">
						<div class="box-title">
							<h4><i class="fa fa-table"></i>创建项目</h4>
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
							<form id="inputForm" class="form-horizontal" action="${ctx}/project/${action}" method="post">
								<input type="hidden" name="id" value="${project.id}"/>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">项目名称</label>
									<div class="col-sm-10">
										<input type="text" id="project_name" name="name" value="${project.name}" class="form-control" placeholder="项目名称"/>
									</div>
								</div>
	
								<div class="form-group">
									<label class="col-sm-2 control-label">项目描述</label> 
									<div class="col-sm-10">
										<textarea rows="3" cols="5" id="description" name="description" class="autosize countable form-control" placeholder="项目描述" data-limit="100">${project.description}</textarea>
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
	
	<!-- TextArea 自动调整SIZE -->
	<script type="text/javascript" src="${ctx}/static/js/autosize/jquery.autosize.min.js"></script>
	<!-- TextArea 计算剩余字数 -->
	<script type="text/javascript" src="${ctx}/static/js/countable/jquery.simplyCountable.min.js"></script>
	
	<!-- 自定义JS脚本 -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {
			//如果页面无需设置效果，可以不设置 App.setPage ，如设置 App.setPage 而页面缺少对应的元素，会导致JS错误.
			App.setPage("project_forms");  //设置当前启动的页面
			
			App.setHasSub("projects-manager");//设置一级菜单目录ID
			App.setSubMenu("projects-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置项目路径
			App.init(); //初始化元素以及插件
			
			$("#project_name").focus();
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
						required: "请输入项目名称",
						minlength: jQuery.format("名称长度不能小于{0}个字符")
						},
					description: {
						required: "请描述您的项目",
						minlength: jQuery.format("描述不能小于{0}个字 符")
						}
				}
			});
			
		});
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>
