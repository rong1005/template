<%@page import="com.cn.template.xutil.enums.Whether"%>
<%@page import="com.cn.template.xutil.enums.FieldType"%>
<%@page import="com.cn.template.xutil.enums.FieldInputType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="SELECT" value="<%=FieldType.SELECT %>" />
<c:set var="CHECKBOX" value="<%=FieldType.CHECKBOX %>" />
<c:set var="RADIO" value="<%=FieldType.RADIO %>" />

<c:set var="DOUBLE" value="<%=FieldType.DOUBLE %>" />
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
											<option value="${fieldType }" <c:if test="${fieldType eq field.fieldType}">selected="selected"</c:if>>${fieldType.value}</option>
										</c:forEach>
									</select>
									</div>
								</div>
								<div class="form-group" id="selectItems"
									<c:if test="${SELECT ne field.fieldType and CHECKBOX ne field.fieldType and RADIO ne field.fieldType }">
									style="display: none;"
									</c:if>>
									<label class="col-sm-2 control-label"></label>
									<div class="col-sm-10">
										<div class="box border inverse">
											<div class="box-title">
												<h4>
													<i class="fa fa-table"></i>选择项
												</h4>
											</div>
											<div class="box-body">
												<table class="table table-bordered">
													<thead>
														<tr>
															<th style="text-align: center;">#</th>
															<th style="text-align: center;">中文</th>
															<th style="text-align: center;">英文</th>
															<th style="text-align: center;">是否默认</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td style="text-align: center;"><input
																type="checkbox" name="itemId" value="0" /> <input
																type="hidden" name="selectItems[0].showOrder" value="0" />
															</td>
															<td style="text-align: center;"><input type="text"
																name="selectItems[0].chItemName" placeholder="中文"
																style="width: 98%;" /></td>
															<td style="text-align: center;"><input type="text"
																name="selectItems[0].enItemName" placeholder="英文"
																style="width: 98%;" /></td>
															<td style="text-align: center;"><input type="radio"
																name="selectItems[0].isdefault" value="<%=Whether.YES%>"
																onclick="isdefault(this)"></td>
														</tr>
													</tbody>
												</table>

												<input id="addSelectItem" class="btn btn-grey" type="button"
													value="添加" />&emsp; <input id="deleteSelectItem"
													class="btn btn-danger" type="button" value="删除" />
											</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">长度</label>
									<div class="col-sm-10">
										<input type="text" id="field_fieldLength" name="fieldLength" value="${field.fieldLength}" class="form-control" placeholder="长度"/>
									</div>
								</div>
								<div id="div_fieldPrecision" class="form-group" <c:if test="${DOUBLE ne field.fieldType}">style="display: none;"</c:if>>
									<label class="col-sm-2 control-label">精度</label>
									<div class="col-sm-10">
										<input type="text" id="field_fieldPrecision" name="fieldPrecision" value="${field.fieldPrecision}" class="form-control" placeholder="精度"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">默认值(中文)</label>
									<div class="col-sm-10">
										<input type="text" id="field_chDefaultValue" name="chDefaultValue" value="${field.chDefaultValue}" class="form-control" placeholder="默认值(中文)"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">默认值(英文)</label>
									<div class="col-sm-10">
										<input type="text" id="field_enDefaultValue" name="enDefaultValue" value="${field.enDefaultValue}" class="form-control" placeholder="默认值(英文)"/>
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
		var count=1;
	
		jQuery(document).ready(function() {
			App.setPage("field_forms");  //设置当前启动的页面
			App.setHasSub("forms-manager");//设置一级菜单目录ID
			App.setSubMenu("forms-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置字段路径
			App.init(); //初始化元素以及插件
			
			$("#field_name").focus();
			
			$("#field_fieldType").change( function() {
				var fieldType = $(this).val();
				if(fieldType=="${DOUBLE}"){
					$("#div_fieldPrecision").show();
				}else if(fieldType=="${SELECT}"||fieldType=="${CHECKBOX}"||fieldType=="${RADIO}"){
					$("#selectItems").show();
				}else{
					$("#div_fieldPrecision").hide();
					$("#selectItems").hide();
				}
			});
			
			$("#addSelectItem").click( function() {
				var str='<tr>';
				str=str+'<td style="text-align: center;">';
				str=str+'<input type="checkbox" name="itemId" value="'+count+'" />';
				str=str+'<input type="hidden" name="selectItems['+count+'].showOrder" value="'+count+'"/>';
				str=str+'</td>';
				str=str+'<td style="text-align: center;">';
				str=str+'<input type="text" name="selectItems['+count+'].chItemName" placeholder="中文" style="width: 98%;"/>';
				str=str+'</td>';
				str=str+'<td style="text-align: center;">';
				str=str+'<input type="text" name="selectItems['+count+'].enItemName" placeholder="英文" style="width: 98%;"/>';
				str=str+'</td>';
				str=str+'<td style="text-align: center;">';
				str=str+'<input type="radio" name="selectItems['+count+'].isdefault" value="<%=Whether.YES%>" onclick="isdefault(this)">';
				str=str+'</td></tr>';
				$("#selectItems table").append(str);
				count=count+1;
			});
			
			$("#deleteSelectItem").click(function(){
				if(confirm('是否删除该项内容？')){
					$("input[name='itemId']:checked").each(function(index, domEle){
						$(domEle).parents("tr").remove();
					});
				}
			});
			
		});
		
		function isdefault(e){
			$("#selectItems table :radio").each(function(index, domEle){
				if(domEle==e){
					$(domEle).attr("checked", true);
				}else{
					$(domEle).attr("checked", false);
				}
			});
		}
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>
