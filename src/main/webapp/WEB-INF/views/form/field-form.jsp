<%@page import="com.cn.template.xutil.enums.Whether"%>
<%@page import="com.cn.template.xutil.enums.FieldType"%>
<%@page import="com.cn.template.xutil.enums.FieldInputType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="INPUT" value="<%=FieldInputType.INPUT %>" />
<c:set var="SELECT" value="<%=FieldType.SELECT %>" />
<c:set var="CHECKBOX" value="<%=FieldType.CHECKBOX %>" />
<c:set var="RADIO" value="<%=FieldType.RADIO %>" />
<c:set var="DOUBLE" value="<%=FieldType.DOUBLE %>" />
<c:set var="TEXT" value="<%=FieldType.TEXT %>" />
<c:set var="YES" value="<%=Whether.YES%>" />
<c:set var="NOT" value="<%=Whether.NOT%>" />
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>字段管理</title>
	<!-- UNIFORM -->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/js/uniform/css/uniform.default.min.css" />
	
	<!-- 富文本编辑器 -->
	<link href="${ctx}/static/js/ueditor/themes/default/css/ueditor.min.css" type="text/css" rel="stylesheet">
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
									<c:if test="${action eq 'create'}">
										<li>创建字段</li>
									</c:if>
									<c:if test="${action eq 'update'}">
										<li>修改字段</li>
									</c:if>
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
											<label class="radio-inline"> <input type="radio" name="fieldInputType" class="uniform" <c:if test="${(empty field.fieldInputType and fieldInputType eq INPUT) or field.fieldInputType eq fieldInputType}">checked="checked"</c:if> value="${fieldInputType }">${fieldInputType.value }</label>
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
														<c:forEach items="${field.selectItems}" var="selectItem">
														<tr>
															<td style="text-align: center;">
																<input type="checkbox" name="itemId" value="${selectItem.id}"/> 
																<input type="hidden" name="selectItems[${selectItem.showOrder}].showOrder" value="${selectItem.showOrder}" />
															</td>
															<td style="text-align: center;">
																<input type="text"name="selectItems[${selectItem.showOrder}].chItemName" value="${selectItem.chItemName}" placeholder="中文" style="width: 98%;" />
															</td>
															<td style="text-align: center;">
																<input type="text" name="selectItems[${selectItem.showOrder}].enItemName" value="${selectItem.enItemName}" placeholder="英文" style="width: 98%;" />
															</td>
															<td style="text-align: center;" name="td_isdefault">
																<input type="radio" name="isDefault" <c:if test="${YES eq selectItem.isdefault}">checked="checked"</c:if> onchange="isdefault(this)" >
																<input type="hidden" name="selectItems[${selectItem.showOrder}].isdefault" value="${selectItem.isdefault}" />
															</td>
														</tr>
														</c:forEach>
													</tbody>
												</table>

												<input id="addSelectItem" class="btn btn-grey" type="button"
													value="添加" />&emsp; <input id="deleteSelectItem"
													class="btn btn-danger" type="button" value="删除" />
											</div>
										</div>
									</div>
								</div>
								<div class="form-group" id="div_fieldLength" <c:if test="${TEXT eq field.fieldType or SELECT eq field.fieldType or CHECKBOX eq field.fieldType or RADIO eq field.fieldType}">style="display: none;"</c:if>>
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
								<div id="div_testPattern" class="form-group" <c:if test="${TEXT ne field.fieldType}">style="display: none;"</c:if>>
									<label class="col-sm-2 control-label">文本格式</label>
									<div class="col-sm-10">
										<textarea style="width: 100%;height: 260px;" id="test_pattern" name="testPattern" >${field.testPattern}</textarea>
									</div>
								</div>
								<div class="form-group" id="div_chDefaultValue" <c:if test="${SELECT eq field.fieldType or CHECKBOX eq field.fieldType or RADIO eq field.fieldType }">
									style="display: none;"
									</c:if>>
									<label class="col-sm-2 control-label">默认值(中文)</label>
									<div class="col-sm-10">
										<input type="text" id="field_chDefaultValue" name="chDefaultValue" value="${field.chDefaultValue}" class="form-control" placeholder="默认值(中文)"/>
									</div>
								</div>
								<div class="form-group" id="div_enDefaultValue" <c:if test="${SELECT eq field.fieldType or CHECKBOX eq field.fieldType or RADIO eq field.fieldType }">
									style="display: none;"
									</c:if>>
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
	
	<!-- 富文本编辑器 -->
	<script type="text/javascript" src="${ctx}/static/js/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/customize/addCustomizeDialog.js"></script>
	
	<!-- 自定义JS脚本 -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		var count=0;
		var itemLength=parseInt("${fn:length(field.selectItems)}");
	
		jQuery(document).ready(function() {
			App.setPage("field_forms");  //设置当前启动的页面
			App.setHasSub("forms-manager");//设置一级菜单目录ID
			App.setSubMenu("forms-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置字段路径
			App.init(); //初始化元素以及插件
			
			UE.getEditor("test_pattern");
			
			$("#field_name").focus();
			
			$("#field_fieldType").change( function() {
				var fieldType = $(this).val();
				if(fieldType=="${DOUBLE}"){
					$("#div_fieldPrecision").show();
					$("#selectItems").hide();
					$("#div_chDefaultValue").show();
					$("#div_enDefaultValue").show();
					$("#div_fieldLength").show();
				}else if(fieldType=="${SELECT}"||fieldType=="${CHECKBOX}"||fieldType=="${RADIO}"){
					$("#selectItems").show();
					$("#div_fieldPrecision").hide();
					$("#div_chDefaultValue").hide();
					$("#div_enDefaultValue").hide();
					$("#div_fieldLength").hide();
				}else{
					$("#div_fieldPrecision").hide();
					$("#selectItems").hide();
					$("#div_chDefaultValue").show();
					$("#div_enDefaultValue").show();
					$("#div_fieldLength").show();
				}
				
				if(fieldType=="${TEXT}"){
					$("#div_fieldLength").hide();
					$("#div_testPattern").show();
				}else{
					$("#div_testPattern").hide();
				}
				
			});
			
			$("#addSelectItem").click( function() {
				if(count==0&&itemLength>0){
					count=itemLength;
				}
				var str='<tr>';
				str=str+'<td style="text-align: center;">';
				str=str+'<input type="checkbox" name="itemId" value="0"/>';
				str=str+'<input type="hidden" name="selectItems['+count+'].showOrder" value="'+count+'"/>';
				str=str+'</td>';
				str=str+'<td style="text-align: center;">';
				str=str+'<input type="text" name="selectItems['+count+'].chItemName" placeholder="中文" style="width: 98%;"/>';
				str=str+'</td>';
				str=str+'<td style="text-align: center;">';
				str=str+'<input type="text" name="selectItems['+count+'].enItemName" placeholder="英文" style="width: 98%;"/>';
				str=str+'</td>';
				str=str+'<td style="text-align: center;" name="td_isdefault">';
				str=str+'<input type="radio" name="isDefault" onchange="isdefault(this)">';
				str=str+'<input type="hidden" name="selectItems['+count+'].isdefault" value="${NOT}" />';
				str=str+'</td></tr>';
				$("#selectItems table").append(str);
				
				count=count+1;
			});
			
			$("#deleteSelectItem").click(function(){
				if(confirm('是否删除该项内容？')){
					$("input[name='itemId']:checked").each(function(index, domEle){
						if($(domEle).val()!=0){
						jQuery.ajax({
			                    url: "${ctx}/select/delete/"+$(domEle).val(),
			                    type: "post",
			                    dataType: "json",
			                    success: function(msg) {
			                        if(msg.bool){
			                        	$(domEle).parents("tr").remove();
			                        }else{
			                        	alert("该项内容删除失败!");
			                        }
			                    }
						});
						}else{
							$(domEle).parents("tr").remove();
						}
					});
				}
			});
			
		});
		
		function isdefault(e){
			$("td[name='td_isdefault'] :hidden").each(function(index, domEle){
				$(domEle).val("${NOT}");
			});
			
			$(e).next(":hidden").val("${YES}")
		}
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>
