<%@page import="com.cn.template.xutil.enums.FieldType"%>
<%@page import="com.cn.template.xutil.enums.FieldInputType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="INPUT" value="<%=FieldInputType.INPUT %>" />
<c:set var="SHOW" value="<%=FieldInputType.SHOW %>" />
<c:set var="CREATE" value="<%=FieldInputType.CREATE %>" />
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
										<a href="${ctx}/form">${field.form.name}</a>
									</li>
									<li>
										<a href="${ctx}/field/${field.form.id}">字段列表</a>
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
								<input type="hidden" name="form.id" value="${field.form.id}"/>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">字段名称</label>
									<div class="col-sm-10">
										<input type="text" id="field_name" name="name" value="${field.name}" class="form-control" placeholder="字段名称"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">中文显示</label>
									<div class="col-sm-10">
										<input type="text" id="field_chViewName" name="chViewName" value="${field.chViewName}" class="form-control" placeholder="中文显示"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">英文显示</label>
									<div class="col-sm-10">
										<input type="text" id="field_enViewName" name="enViewName" value="${field.enViewName}" class="form-control" placeholder="英文显示"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">排序</label>
									<div class="col-sm-10">
										<input type="text" id="field_showOrder" name="showOrder" value="${field.showOrder}" class="form-control" placeholder="排序"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">输入类型</label> 
									<div class="col-sm-10">
										<c:forEach items="<%=FieldInputType.values() %>" var="fieldInputType">
											<label class="radio-inline"> <input type="radio" name="fieldInputType" class="uniform" <c:if test="${field.fieldInputType eq fieldInputType}">checked="checked"</c:if> value="${fieldInputType }">${fieldInputType.value }</label>
										</c:forEach> 
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">字段类型</label> 
									<div class="col-sm-10">
									<select class="form-control" id="field_fieldType" name="fieldType">
										<c:forEach items="<%=FieldType.values() %>" var="fieldType">
											<option value="${fieldType }">${fieldType.value}</option>
										</c:forEach>
									</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">长度</label>
									<div class="col-sm-10">
										<input type="text" id="field_fieldLength" name="fieldLength" value="${field.fieldLength}" class="form-control" placeholder="长度"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">精度</label>
									<div class="col-sm-10">
										<input type="text" id="field_fieldPrecision" name="fieldPrecision" value="${field.fieldPrecision}" class="form-control" placeholder="精度"/>
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
	
	<!-- 自定义JS脚本 -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {
			App.setPage("field_forms");  //设置当前启动的页面
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
