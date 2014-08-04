<%@page import="com.cn.template.xutil.enums.Units"%>
<%@page import="com.cn.template.xutil.enums.PermissionType"%>
<%@page import="com.cn.template.xutil.enums.Whether"%>
<%@page import="com.cn.template.xutil.enums.FieldType"%>
<%@page import="com.cn.template.xutil.enums.ApplyCheckType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="YES" value="<%=Whether.YES %>" />
<c:set var="NOT" value="<%=Whether.NOT %>" />
<c:set var="SELECT" value="<%=FieldType.SELECT %>" />
<c:set var="CHECKBOX" value="<%=FieldType.CHECKBOX %>" />
<c:set var="RADIO" value="<%=FieldType.RADIO %>" />
<c:set var="DOUBLE" value="<%=FieldType.DOUBLE %>" />
<c:set var="INT" value="<%=FieldType.INT %>" />
<c:set var="TEXT" value="<%=FieldType.TEXT %>" />

<c:set var="REJECT" value="<%=PermissionType.REJECT %>" />
<c:set var="READ_ONLY" value="<%=PermissionType.READ_ONLY %>" />
<c:set var="READ_WRITE" value="<%=PermissionType.READ_WRITE %>" />
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>委托申请</title>
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
										<a href="${ctx}/form">表单列表</a>
									</li>
									<li>修改委托申请</li>
								</ul>
								<!-- /BREADCRUMBS -->
								
							</div>
						</div>
					</div>
					<!-- /PAGE HEADER -->
					
					<div class="box border primary">
						<div class="box-title">
							<h4><i class="fa fa-table"></i>修改委托申请</h4>
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
							<form id="inputForm" class="form-horizontal" action="${ctx}/apply/${action}" method="post">
								<input type="hidden" name="id" value="${apply.id}"/>
								<input type="hidden" name="form.id" value="${apply.form.id}"/>
								<input type="hidden" id="apply_isPass" name="isPass" value="${NOT}"/>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">委托名称(中文)</label>
									<div class="col-sm-10">
										<input type="text" disabled="disabled" id="apply_chApplyName" name="chApplyName" class="form-control" value="${apply.chApplyName}" placeholder="委托名称(中文)"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">委托名称(英文)</label>
									<div class="col-sm-10">
										<input type="text" disabled="disabled" id="apply_enApplyName" name="enApplyName" class="form-control" value="${apply.enApplyName}" placeholder="委托名称(英文)"/>
									</div>
								</div>
								<hr>
								<div class="form-group">
									<label class="col-sm-2 control-label">委托单位(中文)</label>
									<div class="col-sm-10">
										<input type="text" disabled="disabled" id="apply_chConsigner" name="chConsigner" class="form-control" value="${apply.chConsigner}" placeholder="委托单位(中文)"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">委托单位(英文)</label>
									<div class="col-sm-10">
										<input type="text" disabled="disabled" id="apply_enConsigner" name="enConsigner" class="form-control" value="${apply.enConsigner}" placeholder="委托单位(英文)"/>
									</div>
								</div>
								<hr>
								<div class="form-group">
									<label class="col-sm-2 control-label">委托人(中文)</label>
									<div class="col-sm-10">
										<input type="text" disabled="disabled" id="apply_chClient" name="chClient" class="form-control" value="${apply.chClient}" placeholder="委托人(中文)"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">委托人(英文)</label>
									<div class="col-sm-10">
										<input type="text" disabled="disabled" id="apply_enClient" name="enClient" class="form-control" value="${apply.enClient}" placeholder="委托人(英文)"/>
									</div>
								</div>
								<hr>
								<div class="form-group">
									<label class="col-sm-2 control-label">检验项目(中文)</label>
									<div class="col-sm-10">
										<input type="text" disabled="disabled" id="apply_chTestItems" name="chTestItems" class="form-control" value="${apply.chTestItems}" placeholder="检验项目(中文)"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">检验项目(英文)</label>
									<div class="col-sm-10">
										<input type="text" disabled="disabled" id="apply_enTestItems" name="enTestItems" class="form-control" value="${apply.enTestItems}" placeholder="检验项目(英文)"/>
									</div>
								</div>
								<hr>
								<div class="form-group">
									<label class="col-sm-2 control-label">检验类别(中文)</label>
									<div class="col-sm-10">
										<c:forEach items="<%=ApplyCheckType.values() %>" var="applyCheckType">
										<label class="radio-inline">
											<input type="radio" disabled="disabled" class="uniform" id="apply_checkType" name="checkType" <c:if test="${apply.checkType eq applyCheckType}">checked="checked"</c:if> value="${applyCheckType}"> 
											${applyCheckType.value}(${applyCheckType.enValue}) 
										</label>
										</c:forEach>
									</div>
								</div>
								<hr>
								<div class="form-group">
									<label class="col-sm-2 control-label">样品名称(中文)</label>
									<div class="col-sm-10">
										<input type="text" disabled="disabled" id="apply_chSampleName" name="chSampleName" class="form-control" value="${apply.chSampleName}" placeholder="样品名称(中文)"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">样品名称(英文)</label>
									<div class="col-sm-10">
										<input type="text" disabled="disabled" id="apply_enSampleName" name="enSampleName" class="form-control" value="${apply.enSampleName}" placeholder="样品名称(英文)"/>
									</div>
								</div>
								<hr>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">样品型号</label>
									<div class="col-sm-10">
										<input type="text" disabled="disabled" id="apply_sampleModel" name="sampleModel" class="form-control" value="${apply.sampleModel}" placeholder="样品型号"/>
									</div>
								</div>
								<hr>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">样品数量</label>
									<div class="col-sm-8">
										<input type="text" disabled="disabled" id="apply_sampleNumber" name="sampleNumber" class="form-control" value="${apply.sampleNumber}" placeholder="样品数量"/>
									</div>
									<div class="col-sm-2">
										<select id="apply_units" disabled="disabled" name="units" class="form-control">
										<c:forEach items="<%=Units.values() %>" var="unit">
											<option value="${unit }" <c:if test="${unit eq apply.units }">selected="selected"</c:if>>${unit.value}(${unit.enValue})</option>
										</c:forEach>
										</select>
									</div>
								</div>
								<hr>
								
								<c:forEach var="field" items="${apply.form.fields }">
								<c:if test="${nodeMap[field.id].permissionType ne REJECT}">
								<c:choose>
								<c:when test="${field.fieldType eq SELECT}">
								<div class="form-group">
									<label class="col-sm-2 control-label">${field.chViewName}</label>
									<div class="col-sm-10">
										<select disabled="disabled" class="form-control" name="${field.name}" id="apply_${field.name}" <c:if test="${nodeMap[field.id].permissionType eq READ_ONLY}">disabled="disabled"</c:if>>
											<c:forEach items="${field.selectItems }" var="item">
												<option value="${item.id }" <c:if test="${item.id eq customField[field.name]['id'] }">selected="selected"</c:if>>${item.chItemName}(${item.enItemName})</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<hr>							
								</c:when>
								
								<c:when test="${field.fieldType eq CHECKBOX}">
								<div class="form-group">
									<label class="col-sm-2 control-label">${field.chViewName}</label>
									<div class="col-sm-10">
										<c:forEach items="${field.selectItems }" var="item">
										<label class="checkbox-inline">
											<input type="checkbox" class="uniform" name="${field.name}" id="apply_${field.name}" 
											<c:if test="${nodeMap[field.id].permissionType eq READ_ONLY}">disabled="disabled"</c:if> 
											<c:if test="${fn:contains(customField[field.name]['ch'],item.chItemName)}">checked="checked"</c:if> value="${item.id }"> 
											${item.chItemName}(${item.enItemName})
										</label>
										</c:forEach>
									</div>
								</div>
								<hr>							
								</c:when>
								
								<c:when test="${field.fieldType eq RADIO}">
								<div class="form-group">
									<label class="col-sm-2 control-label">${field.chViewName}</label>
									<div class="col-sm-10">
										<c:forEach items="${field.selectItems }" var="item">
										<label class="radio-inline">
											<input type="radio" class="uniform" name="${field.name}" id="apply_${field.name}" 
											<c:if test="${nodeMap[field.id].permissionType eq READ_ONLY}">disabled="disabled"</c:if> 
											<c:if test="${item.id eq customField[field.name]['id'] }">checked="checked"</c:if> value="${item.id }"> 
											${item.chItemName}(${item.enItemName})
										</label>
										</c:forEach>
									</div>
								</div>
								<hr>							
								</c:when>
								
								<c:when test="${field.fieldType eq DOUBLE or field.fieldType eq INT }">
								<div class="form-group">
									<label class="col-sm-2 control-label">${field.chViewName}</label>
									<div class="col-sm-10">
										<input type="text" id="apply_${field.name}" name="${field.name}" class="form-control" value="${customField[field.name] }" placeholder="${field.chViewName}" <c:if test="${nodeMap[field.id].permissionType eq READ_ONLY}">disabled="disabled"</c:if>/>
									</div>
								</div>
								<hr>	
								</c:when>
								
								<c:when test="${field.fieldType eq TEXT}">
								<div class="form-group">
									<label class="col-sm-2 control-label">${field.chViewName}</label>
									<div class="col-sm-10">
										<textarea rows="5" cols="10" class="form-control" id="apply_ch_${field.name}" name="ch_${field.name}" <c:if test="${nodeMap[field.id].permissionType eq READ_ONLY}">disabled="disabled"</c:if>>${customField[field.name]['ch'] }</textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">${field.enViewName}</label>
									<div class="col-sm-10">
										<textarea rows="5" cols="10" class="form-control" id="apply_en_${field.name}" name="en_${field.name}" <c:if test="${nodeMap[field.id].permissionType eq READ_ONLY}">disabled="disabled"</c:if>>${customField[field.name]['en'] }</textarea>
									</div>
								</div>
								</c:when>
								
								<c:otherwise>
								<div class="form-group">
									<label class="col-sm-2 control-label">${field.chViewName}</label>
									<div class="col-sm-10">
										<input type="text" id="apply_ch_${field.name}" name="ch_${field.name}" value="${customField[field.name]['ch'] }" class="form-control"  placeholder="${field.chViewName}" <c:if test="${nodeMap[field.id].permissionType eq READ_ONLY}">disabled="disabled"</c:if>/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">${field.enViewName}</label>
									<div class="col-sm-10">
										<input type="text" id="apply_en_${field.name}" name="en_${field.name}" value="${customField[field.name]['en'] }" class="form-control"   placeholder="${field.enViewName}" <c:if test="${nodeMap[field.id].permissionType eq READ_ONLY}">disabled="disabled"</c:if>/>
									</div>
								</div>
								<hr>
								</c:otherwise>
								</c:choose>
								</c:if>
								</c:forEach>
								
								
								<div class="form-group">
									<label class="col-sm-2 control-label">实验编号</label>
									<div class="col-sm-10">
										<input type="text" id="apply_serialNumber" name="serialNumber" class="form-control" value="${apply.serialNumber}" placeholder="实验编号"/>
									</div>
								</div>
								<hr>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">费用明细</label>
									<div class="col-sm-10">
										<table id="priceDetailTable" class="table table-striped table-bordered table-hover">
											<thead>
											<tr>
												<td colspan="9">
													<div class="row">
													<div class="col-sm-4">
													<select class="form-control" id="equipment">
														<c:forEach items="${prices}" var="price">
															<option value="${price.id}">${price.project}--${price.equipmentType.name}</option>
														</c:forEach>
													</select>
													</div>
													<div class="col-sm-2">
													<input type="text" class="form-control" id="usedHour" placeholder="小时"/>
													</div>
													<div class="col-sm-2">
													<input type="text" class="form-control" id="usedTimes" placeholder="次数"/>
													</div>
													<div class="col-sm-4">
													<input class="btn btn-inverse" type="button" value="添加" onclick="addPrice()"/>
													</div>
													</div>
												</td>
											</tr>
											<tr>
												<th>#</th>
												<th>试验项目</th>
												<th>设备名称</th>
												<th>开机费</th>
												<th>电费/小时</th>
												<th>设备折旧/小时</th>
												<th>小时</th>
												<th>次数/数量</th>
												<th>费用</th>
											</tr>
											</thead>
											<tbody>
												<c:forEach items="${apply.applyPrices}" var="applyPrice" >
												<tr>
													<td><span style="color: red; cursor: pointer;" onclick="deleteApplyPrice(this)">删除</span></td>
													<td>
													${applyPrice.price.project}
													<input type="hidden" name="priceId" value="${applyPrice.price.id}" />
													<input type="hidden" name="usedHour" value="${applyPrice.usedHour}" />
													<input type="hidden" name="usedTimes" value="${applyPrice.usedTimes}" />
													<input type="hidden" name="totalPrice" value="${applyPrice.totalPrice }" />
													</td>
													<td>${applyPrice.price.equipmentType.name}</td>
													<td>${applyPrice.price.openPrice}</td>
													<td>${applyPrice.price.electricPrice}</td>
													<td>${applyPrice.price.depreciation}</td>
													<td>${applyPrice.usedHour}</td>
													<td>${applyPrice.usedTimes}</td>
													<td>${applyPrice.totalPrice}</td>
												</tr>
												</c:forEach>
											</tbody>
											<tfoot>
											<tr>
												<th colspan="8">总费用</th>
												<th style="color: red;">0</th>
											</tr>
											</tfoot>
										</table>
									</div>
								</div>
								<hr>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">备注</label>
									<div class="col-sm-10">
										<textarea rows="5" cols="10" class="form-control" id="apply_remark" name="apply_remark"></textarea>
									</div>
								</div>
								<hr>
								
												  
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<input id="pass_btn" class="btn btn-info" type="submit" value="通过"/>&nbsp;
										<input id="reject_btn" class="btn btn-warning" type="submit" value="否决"/>&nbsp;
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
			//如果页面无需设置效果，可以不设置 App.setPage ，如设置 App.setPage 而页面缺少对应的元素，会导致JS错误.
			App.setPage("apply_forms");  //设置当前启动的页面
			
			App.setHasSub("forms-manager");//设置一级菜单目录ID
			App.setSubMenu("forms-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置表单路径
			App.init(); //初始化元素以及插件
			
			$("#pass_btn").on("click", function(){
				$("#apply_isPass").val("${YES}");
			});
			
			$("#reject_btn").on("click", function(){
				$("#apply_isPass").val("${NOT}");
			});
			
			countTotalPrice();

		});
		
		//添加费用明细.
		function addPrice(){
			var usedTimes = $("#usedTimes").val();
			var usedHour = $("#usedHour").val();
			
			 jQuery.ajax({
                 url: "${ctx}/price/show/"+$("#equipment").val(),
                 type: "post",
                 dataType: "json",
                 success: function(msg) {
                	 var formula=msg.formula;
                	 formula = formula.replace('开机费',msg.openPrice);
                	 formula = formula.replace('小时数',usedHour);
                	 formula = formula.replace('小时',usedHour);
			 		 formula = formula.replace('次数',usedTimes);
			 		 formula = formula.replace('数量',usedTimes);
                	 
                	 var str='<tr>';
                	 str=str+'<td><span style="color: red; cursor: pointer;" onclick="deleteApplyPrice(this)">删除</span></td>';
                	 str=str+'<td>'+msg.project;
                	 str=str+'<input type="hidden" name="priceId" value="'+msg.id+'" />';
                	 str=str+'<input type="hidden" name="usedHour" value="'+usedHour+'" />';
                	 str=str+'<input type="hidden" name="usedTimes" value="'+usedTimes+'" />';
                	 str=str+'<input type="hidden" name="totalPrice" value="'+eval(formula)+'" />';
                	 str=str+'</td>';
                	 str=str+'<td>'+msg.equipmentType.name+'</td>';
                	 str=str+'<td>'+msg.openPrice+'</td>';
                	 str=str+'<td>'+msg.electricPrice+'</td>';
                	 str=str+'<td>'+msg.depreciation+'</td>';
                	 str=str+'<td>'+usedHour+'</td>';
                	 str=str+'<td>'+usedTimes+'</td>';
                	 str=str+'<td>'+eval(formula)+'</td>';
                	 str=str+'</tr>';
					 $("#priceDetailTable tbody").append(str); 
					 countTotalPrice();
                 }
             }); 
		}
		
		function deleteApplyPrice(e){
			$(e).parent().parent().remove();
			countTotalPrice();
		}
		
		function countTotalPrice(){
			var total=0;
			 $("tbody tr td:last-child").each(function (index, domEle) { 
				 total=total+Number($(domEle).text());
			 });
			
			 $("tfoot tr th").last().text(total);
		}
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>
