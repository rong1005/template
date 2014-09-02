<%@page import="com.cn.template.xutil.enums.PermissionType"%>
<%@page import="com.cn.template.xutil.enums.Units"%>
<%@page import="com.cn.template.xutil.enums.ApplyCheckType"%>
<%@page import="com.cn.template.xutil.enums.FieldType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

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
										<a href="${ctx}/apply">申请纪录</a>
									</li>
									<li>实验排期</li>
								</ul>
								<!-- /BREADCRUMBS -->
								
							</div>
						</div>
					</div>
					<!-- /PAGE HEADER -->
					
					<form id="inputForm" class="form-horizontal" action="${ctx}/schedule/add/${apply.id}" method="post">
					<div class="box border primary">
						<div class="box-title">
							<h4><i class="fa fa-table"></i>实验资料</h4>
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
							<div class="row">
								<div class="col-md-8 box-container ui-sortable">
								<div class="box border blue">
  									<div class="box-title small"><h4>项目名称</h4></div>
									<div class="box-body">
    									<div class="input-group">
      										<div class="input-group-addon">中文</div>
      										<input type="text" disabled="disabled" id="apply_chApplyName" name="chApplyName" class="form-control" value="${apply.chApplyName}" placeholder="委托名称(中文)"/>
    									</div>
    									<br>
    									<div class="input-group">
      										<div class="input-group-addon">英文</div>
      										<input type="text" disabled="disabled" id="apply_enApplyName" name="enApplyName" class="form-control" value="${apply.enApplyName}" placeholder="委托名称(英文)"/>
    									</div>
									</div>
								</div>
								</div>
								
								<div class="col-md-4 box-container ui-sortable">
								<div class="box border blue">
  									<div class="box-title small"><h4>检验类别</h4></div>
									<div class="box-body">
    									<c:forEach items="<%=ApplyCheckType.values() %>" var="applyCheckType">
										<label class="radio" style="font-size: 16px;font-weight: normal;">
											<input type="radio" disabled="disabled" class="uniform" id="apply_checkType" name="checkType" <c:if test="${apply.checkType eq applyCheckType}">checked="checked"</c:if> value="${applyCheckType}"> 
											${applyCheckType.value}(${applyCheckType.enValue}) 
										</label>
										</c:forEach>
									</div>
								</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-4 box-container ui-sortable">
								<div class="box border blue">
  									<div class="box-title small"><h4>委托单位</h4></div>
									<div class="box-body">
    									<div class="input-group">
      										<div class="input-group-addon">中文</div>
      										<input type="text" disabled="disabled" id="apply_chConsigner" name="chConsigner" class="form-control" value="${apply.chConsigner}" placeholder="委托单位(中文)"/>
    									</div>
    									<br>
    									<div class="input-group">
      										<div class="input-group-addon">英文</div>
      										<input type="text" disabled="disabled" id="apply_enConsigner" name="enConsigner" class="form-control" value="${apply.enConsigner}" placeholder="委托单位(英文)"/>
    									</div>
									</div>
								</div>
								</div>
								
								<div class="col-md-4 box-container ui-sortable">
								<div class="box border blue">
  									<div class="box-title small"><h4>委托人</h4></div>
									<div class="box-body">
    									<div class="input-group">
      										<div class="input-group-addon">中文</div>
      										<input type="text" disabled="disabled" id="apply_chClient" name="chClient" class="form-control" value="${apply.chClient}" placeholder="委托人(中文)"/>
    									</div>
    									<br>
    									<div class="input-group">
      										<div class="input-group-addon">英文</div>
      										<input type="text" disabled="disabled" id="apply_enClient" name="enClient" class="form-control" value="${apply.enClient}" placeholder="委托人(英文)"/>
    									</div>
									</div>
								</div>
								</div>
								
								<div class="col-md-4 box-container ui-sortable">
								<div class="box border blue">
  									<div class="box-title small"><h4>邮箱</h4></div>
									<div class="box-body">
    									<div class="input-group">
      										<div class="input-group-addon">委托人</div>
      										<input type="text" disabled="disabled" id="apply_clientMail" name="clientMail" class="form-control" value="${apply.clientMail}" placeholder="委托人邮箱"/>
    									</div>
    									<br>
    									<div class="input-group">
      										<div class="input-group-addon">跟踪人</div>
      										<input type="text" disabled="disabled" id="apply_followMail" name="followMail" class="form-control" value="${apply.followMail}" placeholder="跟踪人邮箱"/>
    									</div>
									</div>
								</div>
								</div>
							</div>
								
								
							<div class="row">
								<div class="col-md-4 box-container ui-sortable">
								<div class="box border blue">
  									<div class="box-title small"><h4>样品名称</h4></div>
									<div class="box-body">
    									<div class="input-group">
      										<div class="input-group-addon">中文</div>
      										<input type="text" disabled="disabled" id="apply_chSampleName" name="chSampleName" class="form-control" value="${apply.chSampleName}" placeholder="样品名称(中文)"/>
    									</div>
    									<br>
    									<div class="input-group">
      										<div class="input-group-addon">英文</div>
      										<input type="text" disabled="disabled" id="apply_enSampleName" name="enSampleName" class="form-control" value="${apply.enSampleName}" placeholder="样品名称(英文)"/>
    									</div>
									</div>
								</div>
								</div>
								
								<div class="col-md-4 box-container ui-sortable">
								<div class="box border blue">
  									<div class="box-title small"><h4>型号</h4></div>
									<div class="box-body">
    									<div class="input-group">
      										<div class="input-group-addon">样品型号</div>
      										<input type="text" disabled="disabled" id="apply_sampleModel" name="sampleModel" class="form-control" value="${apply.sampleModel}" placeholder="样品型号"/>
    									</div>
    									<br>
    									<div class="input-group">
      										<div class="input-group-addon">客户型号</div>
      										<input type="text" disabled="disabled" id="apply_clientModel" name="clientModel" class="form-control" value="${apply.clientModel}" placeholder="客户型号"/>
    									</div>
									</div>
								</div>
								</div>
								
								<div class="col-md-4 box-container ui-sortable">
								<div class="box border blue">
  									<div class="box-title small"><h4>数量</h4></div>
									<div class="box-body">
									<input type="text" disabled="disabled" id="apply_sampleNumber" name="sampleNumber" class="form-control" value="${apply.sampleNumber}" placeholder="样品数量"/>
									<br>
									<select id="apply_units" disabled="disabled" name="units" class="form-control">
										<c:forEach items="<%=Units.values() %>" var="unit">
											<option value="${unit }" <c:if test="${unit eq apply.units }">selected="selected"</c:if>>${unit.value}(${unit.enValue})</option>
										</c:forEach>
									</select>
									</div>
								</div>
								</div>
							</div>
								
								
							<div class="row">
								<div class="col-md-6 box-container ui-sortable">
								<div class="box border blue">
  									<div class="box-title small"><h4>检验项目</h4></div>
									<div class="box-body">
    									<div class="input-group">
      										<div class="input-group-addon">中文</div>
      										<input type="text" disabled="disabled" id="apply_chTestItems" name="chTestItems" class="form-control" value="${apply.chTestItems}" placeholder="检验项目(中文)"/>
    									</div>
    									<br>
    									<div class="input-group">
      										<div class="input-group-addon">英文</div>
      										<input type="text" disabled="disabled" id="apply_enTestItems" name="enTestItems" class="form-control" value="${apply.enTestItems}" placeholder="检验项目(英文)"/>
    									</div>
									</div>
								</div>
								</div>
								<div class="col-md-6 box-container ui-sortable">
								<div class="box border blue">
  									<div class="box-title small"><h4>检测依据</h4></div>
									<div class="box-body">
    									<div class="input-group">
      										<div class="input-group-addon">中文</div>
      										<input type="text" disabled="disabled" id="apply_chTestReference" name="chTestReference" class="form-control" value="${apply.chTestReference}" placeholder="检测依据(中文)"/>
    									</div>
    									<br>
    									<div class="input-group">
      										<div class="input-group-addon">英文</div>
      										<input type="text" disabled="disabled" id="apply_enTestReference" name="enTestReference" class="form-control" value="${apply.enTestReference}" placeholder="检测依据(英文)"/>
    									</div>
									</div>
								</div>
								</div>
							</div>
								
								
							<div class="row">
								<div class="col-md-6 box-container ui-sortable">
								<div class="box border blue">
  									<div class="box-title small"><h4>样品状态</h4></div>
									<div class="box-body">
    									<div class="input-group">
      										<div class="input-group-addon">状态</div>
      										<input type="text" disabled="disabled" id="apply_sampleStatus" name="sampleStatus" class="form-control" value="${apply.sampleStatus}" placeholder="样品状态"/>
    									</div>
    									<br>
    									<div class="input-group">
      										&emsp;1、样品正常；2、外观不良；3、纯音不良；4、其它不良   
    									</div>
									</div>
								</div>
								</div>
								<div class="col-md-6 box-container ui-sortable">
								<div class="box border blue">
  									<div class="box-title small"><h4>存放要求</h4></div>
									<div class="box-body">
    									<div class="input-group">
      										<div class="input-group-addon">存放</div>
      										<input type="text" disabled="disabled" id="apply_storeRequire" name="storeRequire" class="form-control" value="${apply.storeRequire}" placeholder="存放要求"/>
    									</div>
    									<br>
    									<div class="input-group">
      										&emsp;1、常温存放；2、保密存放；3、其它要求   
    									</div>
									</div>
								</div>
								</div>
							</div>
								
							<div class="row">
								<div class="col-md-12 box-container ui-sortable">
								<div class="box border blue">
  									<div class="box-title small"><h4>样品检查</h4></div>
									<div class="box-body">
    									<div class="input-group">
      										<div class="input-group-addon">检查</div>
      										<input type="text" disabled="disabled" id="apply_sampleCheck" name="sampleCheck" class="form-control" value="${apply.sampleCheck}" placeholder="样品检查"/>
      										<div class="input-group-addon">检听电压</div>
      										<input type="text" disabled="disabled" id="apply_chCause" name="checkVoltage" class="form-control" value="${apply.checkVoltage}" placeholder="检听电压"/>
      										<div class="input-group-addon">V； 频率范围</div>
      										<input type="text" disabled="disabled" id="apply_checkHz" name="checkHz" class="form-control" value="${apply.checkHz}" placeholder="频率范围"/>
      										<div class="input-group-addon">HZ</div>
    									</div>
    									<br>
    									<div class="input-group">
      										&emsp;1、例行试验；2、新产品开发；3、变更验证；4、其它   
    									</div>
									</div>
								</div>
								</div>
							</div>
								
							<div class="row">
								<div class="col-md-6 box-container ui-sortable">
								<div class="box border blue">
  									<div class="box-title small"><h4>试验原因</h4></div>
									<div class="box-body">
    									<div class="input-group">
      										<div class="input-group-addon">原因</div>
      										<input type="text" disabled="disabled" id="apply_chCause" name="chCause" class="form-control" value="${apply.chCause}" placeholder="试验原因"/>
    									</div>
    									<br>
    									<div class="input-group">
      										&emsp;1、例行试验；2、新产品开发；3、变更验证；4、其它   
    									</div>
									</div>
								</div>
								</div>
								<div class="col-md-6 box-container ui-sortable">
								<div class="box border blue">
  									<div class="box-title small"><h4>样品处理</h4></div>
									<div class="box-body">
    									<div class="input-group">
      										<div class="input-group-addon">处理</div>
      										<input type="text" disabled="disabled" id="apply_sampleDeal" name="sampleDeal" class="form-control" value="${apply.sampleDeal}" placeholder="样品处理"/>
    									</div>
    									<br>
    									<div class="input-group">
      										&emsp;1、销毁；2、退还；3、保存(期限   )；4、其它   
    									</div>
									</div>
								</div>
								</div>
							</div>
							
							<c:forEach var="field" items="${apply.form.fields }">
								<c:if test="${nodeMap[field.id].permissionType ne REJECT}">
								<c:choose>
								<c:when test="${field.fieldType eq SELECT}">
								<div class="row">
								  <div class="col-md-12 box-container ui-sortable">
								    <div class="box border blue">
								      <div class="box-title small"><h4>${field.chViewName}(${field.enViewName})</h4></div>
								      <div class="box-body">
    							       <select class="form-control" name="${field.name}" id="apply_${field.name}" <c:if test="${nodeMap[field.id].permissionType eq READ_ONLY}">disabled="disabled"</c:if>>
											<c:forEach items="${field.selectItems }" var="item">
												<option value="${item.id }" <c:if test="${item.id eq customField[field.name]['id'] }">selected="selected"</c:if>>${item.chItemName}(${item.enItemName})</option>
											</c:forEach>
										</select>
								      </div>
								    </div>
								  </div>
								</div>							
								</c:when>
								
								<c:when test="${field.fieldType eq CHECKBOX}">
								<div class="row">
								  <div class="col-md-12 box-container ui-sortable">
								    <div class="box border blue">
								      <div class="box-title small"><h4>${field.chViewName}(${field.enViewName})</h4></div>
								      <div class="box-body">
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
								  </div>
								</div>							
								</c:when>
								
								<c:when test="${field.fieldType eq RADIO}">
								<div class="row">
								  <div class="col-md-12 box-container ui-sortable">
								    <div class="box border blue">
								      <div class="box-title small"><h4>${field.chViewName}(${field.enViewName})</h4></div>
								      <div class="box-body">
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
								  </div>
								</div>							
								</c:when>
								
								<c:when test="${field.fieldType eq DOUBLE or field.fieldType eq INT }">
								<div class="row">
								  <div class="col-md-12 box-container ui-sortable">
								    <div class="box border blue">
								      <div class="box-title small"><h4>${field.chViewName}(${field.enViewName})</h4></div>
								      <div class="box-body">
    							        <div class="input-group">
      										<div class="input-group-addon">${field.fieldType.value }</div>
      										<input type="text" id="apply_${field.name}" name="${field.name}" class="form-control" value="${customField[field.name] }" placeholder="${field.chViewName}" <c:if test="${nodeMap[field.id].permissionType eq READ_ONLY}">disabled="disabled"</c:if>/>
    									</div>
								      </div>
								    </div>
								  </div>
								</div>	
								</c:when>
								
								<c:when test="${field.fieldType eq TEXT}">
								<div class="row">
								  <div class="col-md-6 box-container ui-sortable">
								    <div class="box border blue">
								      <div class="box-title small"><h4>${field.chViewName}</h4></div>
								      <div class="box-body">
    							        <textarea id="apply_ch_${field.name}" name="ch_${field.name}" <c:if test="${nodeMap[field.id].permissionType eq READ_ONLY}">disabled="disabled"</c:if>>${customField[field.name]['ch'] }</textarea>
								      </div>
								    </div>
								  </div>
								  <div class="col-md-6 box-container ui-sortable">
								    <div class="box border blue">
								      <div class="box-title small"><h4>${field.enViewName}</h4></div>
								      <div class="box-body">
    							        <textarea id="apply_en_${field.name}" name="en_${field.name}" <c:if test="${nodeMap[field.id].permissionType eq READ_ONLY}">disabled="disabled"</c:if>>${customField[field.name]['en'] }</textarea>
								      </div>
								    </div>
								  </div>
								</div>
								</c:when>
								
								<c:otherwise>
								<div class="row">
								  <div class="col-md-12 box-container ui-sortable">
								    <div class="box border blue">
								      <div class="box-title small"><h4>${field.chViewName}(${field.enViewName})</h4></div>
								      <div class="box-body">
    							        <div class="input-group">
      										<div class="input-group-addon">中文</div>
      										<input type="text" id="apply_ch_${field.name}" name="ch_${field.name}" value="${customField[field.name]['ch'] }" class="form-control"  placeholder="${field.chViewName}" <c:if test="${nodeMap[field.id].permissionType eq READ_ONLY}">disabled="disabled"</c:if>/>
    									</div>
    									<br>
    									<div class="input-group">
      										<div class="input-group-addon">英文</div>
      										<input type="text" id="apply_en_${field.name}" name="en_${field.name}" value="${customField[field.name]['en'] }" class="form-control"   placeholder="${field.enViewName}" <c:if test="${nodeMap[field.id].permissionType eq READ_ONLY}">disabled="disabled"</c:if>/>
    									</div>
								      </div>
								    </div>
								  </div>
								</div>
								</c:otherwise>
								</c:choose>
								</c:if>
							</c:forEach>
															
						</div>
					</div>
					
					<div class="box border primary">
						<div class="box-title">
							<h4><i class="fa fa-table"></i>费用清单</h4>
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
								<table id="priceDetailTable" class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
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
										<c:forEach items="${apply.applyPrices}" var="applyPrice">
											<tr>
												<td>${applyPrice.price.project}</td>
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
											<th colspan="7">总费用</th>
											<th style="color: red;">0</th>
										</tr>
									</tfoot>
								</table>
							</div>
						</div>
					
					<div class="box border primary">
						<div class="box-title">
							<h4><i class="fa fa-table"></i>实验排期</h4>
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
							<div class="form-group">
								<label class="col-sm-1 control-label">样品名称</label>
								<div class="col-sm-4">
									<span class="form-control" >${apply.chSampleName}(${apply.enSampleName})</span>
								</div>
								<label class="col-sm-1 control-label">样品型号</label>
								<div class="col-sm-3">
									<span class="form-control" >${apply.sampleModel}</span>
								</div>
								<label class="col-sm-1 control-label">样品数量</label>
								<div class="col-sm-2">
									<span class="form-control" >${apply.sampleNumber} ${apply.units.value}</span>
								</div>
							</div>
							<table id="sampleTable" class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th style="text-align: center;">#</th>
										<th>流水号</th>
										<th>状态</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${apply.samples }" var="sample">
									<tr>
										<td style="text-align: center;"><input type="checkbox" name="sample" value="${sample.id }" /> </td>
										<td id="td_${sample.id }">${sample.serialNumber }</td>
										<td>${sample.status.value }</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
							
							<div class="form-group">
								<label class="col-sm-1 control-label">设备</label>
								<div class="col-sm-5">
									<select class="form-control" onchange="findEquipment(this)" id="equipmentType">
										<option value="0">请选择</option>
										<c:forEach items="${equipmentTypes}" var="equipmentType">
											<option value="${equipmentType.id }">${equipmentType.name }</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-sm-6">
									<select class="form-control" id="equipment">
										
									</select>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-1 control-label">实验用时</label>
								<div class="col-sm-5">
									<div class="input-group">
										<input type="text" id="experiment-time" class="form-control" value="0"/>
										<div class="input-group-addon">小时</div>
									</div>
								</div>
								
								<label class="col-sm-1 control-label">过渡用时</label>
								<div class="col-sm-5">
									<div class="input-group">
										<input type="text" id="transition-time" class="form-control" value="0"/>
										<div class="input-group-addon">小时</div>
									</div>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-1 control-label">总用时</label>
								<div class="col-sm-5">
									<div class="input-group">
										<input type="text" id="usedTime" class="form-control" value="0" readonly="readonly" />
										<div class="input-group-addon">小时</div>
									</div>
								</div>
								<label class="col-sm-1 control-label">开始时间</label>
								<div class="col-sm-5">
									<input type="text" id="startTime" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'});" />
								</div>
							</div>
							<input id="add_btn" class="btn btn-info" type="button" onclick="addSampleSchedule()" value="添加"/>&nbsp;
						</div>
					</div>
					
					
					<div class="box border primary">
						<div class="box-title">
							<h4><i class="fa fa-table"></i>样品排期</h4>
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
							<table id="scheduleTable" class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>管理</th>
										<th>样品流水号</th>
										<th>设备</th>
										<th>实验用时</th>
										<th>过渡用时</th>
										<th>总用时</th>
										<th>预计开始时间</th>
										<th>预计结束时间</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${schedules}" var="schedule">
										<tr>
											<td>
												<span style="color: red; cursor: pointer;" onclick="deleteApplyPrice(this)">删除</span>
												<input type="hidden" name="equipmentId" value="${schedule.equipment.id}"/>
												<input type="hidden" name="experimentTime" value="${schedule.experimentTime}"/>
												<input type="hidden" name="transitionTime" value="${schedule.transitionTime}"/>
												<input type="hidden" name="usedTime" value="${schedule.usedTime}"/>
												<input type="hidden" name="startTime" value="<fmt:formatDate value='${schedule.startTime }' pattern='yyyy-MM-dd HH:mm' />"/>
												<input type="hidden" name="endTime" value="<fmt:formatDate value='${schedule.endTime }' pattern='yyyy-MM-dd HH:mm' />"/>
												<input type="hidden" name="sampleId" value="${schedule.sample.id}"/>
											</td>
											<td>${schedule.sample.serialNumber }</td>
											<td>${schedule.equipment.equipmentType.name} -- ${schedule.equipment.name} </td>
											<td>${schedule.experimentTime }</td>
											<td>${schedule.transitionTime }</td>
											<td>${schedule.usedTime }</td>
											<td>
												<fmt:formatDate value="${schedule.startTime }" pattern="yyyy-MM-dd HH:mm" />
											</td>
											<td>
												<fmt:formatDate value="${schedule.endTime }" pattern="yyyy-MM-dd HH:mm" />
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							
							<input id="submit_btn" class="btn btn-info" type="submit" value="提交"/>&nbsp;
							<input id="cancel_btn" class="btn btn-default" type="button" value="返回" onclick="history.back()"/>
						</div>
					</div>
					</form>
					
					
				</div>
			</div>
		</div>
	</div>	
	
	<!-- JAVASCRIPTS -->
	<!-- 引入公共JS脚本 -->
	<%@ include file="/WEB-INF/layouts/include_script.jsp"%>
	
	<!-- UNIFORM -->
	<script type="text/javascript" src="${ctx}/static/js/uniform/jquery.uniform.min.js"></script>
	
	<!-- 日期插件 -->
	<script type="text/javascript" src="${ctx}/static/js/datepicker97/WdatePicker.js"></script>
	
	<!-- 富文本编辑器 -->
	<script type="text/javascript" src="${ctx}/static/js/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/customize/addCustomizeDialog.js"></script>

	<!-- 自定义JS脚本 -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {
			//如果页面无需设置效果，可以不设置 App.setPage ，如设置 App.setPage 而页面缺少对应的元素，会导致JS错误.
			//App.setPage("apply_forms");  //设置当前启动的页面
			
			App.setHasSub("forms-manager");//设置一级菜单目录ID
			App.setSubMenu("forms-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置表单路径
			App.init(); //初始化元素以及插件
			
			$("textarea").each(function (index, domEle) { 
				if($(domEle).attr("disabled")=='disabled'){
					UE.getEditor($(domEle).attr("id"),{
						wordCount:false,
						elementPathEnabled:false,
						readonly:true,
						initialFrameHeight:200
					});
				}else{
					UE.getEditor($(domEle).attr("id"),{
						wordCount:false,
						elementPathEnabled:false,
						initialFrameHeight:200
					});
				}
			});
			
			countTotalPrice();
			
			
			$("#experiment-time").on("change",function(){
				var experimentTime = Number($("#experiment-time").val());
				var transitionTime = Number($("#transition-time").val());
				$("#usedTime").val(Number(experimentTime+transitionTime).toFixed(2));
			});
			
			$("#transition-time").on("change",function(){
				var experimentTime = Number($("#experiment-time").val());
				var transitionTime = Number($("#transition-time").val());
				$("#usedTime").val(Number(experimentTime+transitionTime).toFixed(2));
			});
		});
		
		function findEquipment(e){
			 jQuery.ajax({
                 url: "${ctx}/equipment/list/"+$(e).val(),
                 type: "post",
                 dataType: "json",
                 success: function(msg) {
                	 var option="";
                	 $.each( msg, function(i, n){
                		  option=option+'<option value="'+n.id+'">'+n.name+'</option>'
                	});
                	 $("#equipment").html(option);
                 }
			 });
		}
		
		
		function addSampleSchedule(){
			var experimentTime = Number($("#experiment-time").val());
			var transitionTime = Number($("#transition-time").val());
			var usedTime = experimentTime+transitionTime;
			var startTime =$("#startTime").val();
			var endTime = "";
			jQuery.ajax({
                url: "${ctx}/schedule/count/time",
                data: {"datetime":startTime,"hour":usedTime},
                async: false,
                type: "get",
                dataType: "json",
                success: function(msg) {
                	endTime=msg.endTime;
                }
			 });
			
			var str='';
			$("#sampleTable :checkbox:checked").each(function (index, domEle) { 
				str=str+'<tr>';
				str=str+'<td><span style="color: red; cursor: pointer;" onclick="deleteApplyPrice(this)">删除</span>';
				str=str+'<input type="hidden" name="equipmentId" value="'+$("#equipment option:selected").val()+'"/>';
				str=str+'<input type="hidden" name="experimentTime" value="'+experimentTime+'"/>';
				str=str+'<input type="hidden" name="transitionTime" value="'+transitionTime+'"/>';
				str=str+'<input type="hidden" name="usedTime" value="'+Number(usedTime).toFixed(2)+'"/>';
				str=str+'<input type="hidden" name="startTime" value="'+startTime+'"/>';
				str=str+'<input type="hidden" name="endTime" value="'+endTime+'"/>';
				str=str+'<input type="hidden" name="sampleId" value="'+$(domEle).val()+'"/>';
				str=str+'</td>';
				str=str+'<td>'+$("#td_"+$(domEle).val()).html()+'</td>';
				str=str+'<td>'+$("#equipmentType option:selected").text()+' -- '+$("#equipment option:selected").text()+'</td>';
				str=str+'<td>'+experimentTime+'</td>';
				str=str+'<td>'+transitionTime+'</td>';
				str=str+'<td>'+Number(usedTime).toFixed(2)+'</td>';
				str=str+'<td>'+startTime+'</td>';
				str=str+'<td>'+endTime+'</td>';
				str=str+'</tr>';
			});
			
			$("#scheduleTable tbody").append(str);
		}
		
		/** 删除排期信息 */
		function deleteApplyPrice(e){
			$(e).parent().parent().remove();
		}
		
		/** 计算总费用 */
		function countTotalPrice(){
			var total=0;
			 $("#priceDetailTable tbody tr td:last-child").each(function (index, domEle) { 
				 total=total+Number($(domEle).text());
			 });
			
			 $("#priceDetailTable tfoot tr th").last().text(total);
		}

	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>
