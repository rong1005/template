<%@page import="com.cn.template.xutil.enums.RecordType"%>
<%@page import="com.cn.template.xutil.enums.Whether"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<c:set var="OTHER" value="<%=RecordType.OTHER %>"/>
<c:set var="TWO_POWER" value="<%=RecordType.TWO_POWER %>"/>
<c:set var="FOUR_POWER" value="<%=RecordType.FOUR_POWER %>"/>
<c:set var="ENVIRONMENT" value="<%=RecordType.ENVIRONMENT %>"/>
<c:set var="UV" value="<%=RecordType.UV %>"/>
<c:set var="SALT_MIST" value="<%=RecordType.SALT_MIST %>"/>

<!DOCTYPE html>
<html lang="zh">
<head>
	<title>巡检记录管理</title>
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
										<a href="${ctx}/inspectionRecord">巡检记录列表</a>
									</li>
									<li>创建巡检记录</li>
								</ul>
								<!-- /BREADCRUMBS -->
								
							</div>
						</div>
					</div>
					<!-- /PAGE HEADER -->
					
					<div class="box border primary">
						<div class="box-title">
							<h4><i class="fa fa-table"></i>创建巡检记录</h4>
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
							<form id="inputForm" class="form-horizontal" action="${ctx}/inspection/${action}" method="post">
								<input type="hidden" name="id" value="${inspectionRecord.id}"/>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">设备编号</label>
									<div class="col-sm-4">
										<c:if test="${not empty inspectionRecord.id}">
											<input type="text" id="serial_number" readonly="readonly" value="${inspectionRecord.equipment.serialNumber}" class="form-control" placeholder="设备编号"/>
										</c:if>
										<c:if test="${empty inspectionRecord.id}">
											<input type="text" id="serial_number" class="form-control" placeholder="设备编号"/>
										</c:if>
									</div>
									<label class="col-sm-2 control-label">设备</label>
									<div class="col-sm-4">
										<span class="form-control" id="equipment_name">
										<c:if test="${not empty inspectionRecord.equipment }">
											${inspectionRecord.equipment.equipmentType.name} -- ${inspectionRecord.equipment.name}
											(${inspectionRecord.equipment.modelNumber} -- ${inspectionRecord.equipment.serialNumber})
										</c:if>
										</span>
										<input type="hidden" id="equipment_id" name="equipment.id" value="${inspectionRecord.equipment.id}" class="form-control"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">是否异常</label>
									<div class="col-sm-10">
										<c:forEach items="<%=Whether.values() %>" var="whether">
										<label class="radio-inline">
											<input type="radio" class="uniform" name="isError" <c:if test="${inspectionRecord.isError eq whether}">checked="checked"</c:if> value="${whether}"> 
											${whether.value}
										</label>
										</c:forEach>
									</div>
								</div>
								
								<div class="form-group" <c:if test="${inspectionRecord.equipment.recordType ne TWO_POWER and inspectionRecord.equipment.recordType ne FOUR_POWER}">style="display: none;"</c:if> id="TWO_POWER" >
									<label class="col-sm-2 control-label">电压(1-2)</label>
									<div class="col-sm-5">
										<input type="text" id="ch1" name="ch1" value="${inspectionRecord.ch1}" class="form-control" placeholder="CH1"/>
									</div>
									<div class="col-sm-5">
										<input type="text" id="ch2" name="ch2" value="${inspectionRecord.ch2}" class="form-control" placeholder="CH2"/>
									</div>
								</div>
								
								<div class="form-group" <c:if test="${inspectionRecord.equipment.recordType ne FOUR_POWER}">style="display: none;"</c:if> id="FOUR_POWER">
									<label class="col-sm-2 control-label">电压(3-4)</label>
									<div class="col-sm-5">
										<input type="text" id="ch3" name="ch3" value="${inspectionRecord.ch3}" class="form-control" placeholder="CH3"/>
									</div>
									<div class="col-sm-5">
										<input type="text" id="ch4" name="ch4" value="${inspectionRecord.ch4}" class="form-control" placeholder="CH4"/>
									</div>
								</div>
								
								<div class="form-group" <c:if test="${inspectionRecord.equipment.recordType ne ENVIRONMENT}">style="display: none;"</c:if> id="ENVIRONMENT">
									<label class="col-sm-2 control-label">温度℃</label>
									<div class="col-sm-4">
										<input type="text" id="temperature" name="temperature" value="${inspectionRecord.temperature}" class="form-control" placeholder="温度℃"/>
									</div>
									<label class="col-sm-2 control-label">湿度%</label>
									<div class="col-sm-4">
										<input type="text" id="humidity" name="humidity" value="${inspectionRecord.humidity}" class="form-control" placeholder="湿度%"/>
									</div>
								</div>
								
								<div class="form-group" <c:if test="${inspectionRecord.equipment.recordType ne UV}">style="display: none;"</c:if> id="UV">
									<label class="col-sm-2 control-label">光照强度</label>
									<div class="col-sm-2">
										<input type="text" id="uv1" name="uv1" value="${inspectionRecord.uv1}" class="form-control" placeholder="UV1"/>
									</div>
									<div class="col-sm-3">
										<input type="text" id="uv2" name="uv2" value="${inspectionRecord.uv2}" class="form-control" placeholder="UV2"/>
									</div>
									<div class="col-sm-2">
										<input type="text" id="uv3" name="uv3" value="${inspectionRecord.uv3}" class="form-control" placeholder="UV3"/>
									</div>
									<div class="col-sm-3">
										<input type="text" id="uv4" name="uv4" value="${inspectionRecord.uv4}" class="form-control" placeholder="UV4"/>
									</div>
								</div>
								
								<div class="form-group" <c:if test="${inspectionRecord.equipment.recordType ne SALT_MIST}">style="display: none;"</c:if> id="SALT_MIST">
									<label class="col-sm-2 control-label">试验室温度（°C）</label>
									<div class="col-sm-2">
										<input type="text" id="labTemp" name="labTemp" value="${inspectionRecord.labTemp}" class="form-control" placeholder="试验室温度（°C）"/>
									</div>
									<label class="col-sm-2 control-label">饱和桶温度（°C）</label>
									<div class="col-sm-2">
										<input type="text" id="saturatedBarrelTemp" name="saturatedBarrelTemp" value="${inspectionRecord.saturatedBarrelTemp}" class="form-control" placeholder="饱和桶温度（°C）"/>
									</div>
									<label class="col-sm-2 control-label">气压（kg/cm2）</label>
									<div class="col-sm-2">
										<input type="text" id="pressure" name="pressure" value="${inspectionRecord.pressure}" class="form-control" placeholder="气压（kg/cm2）"/>
									</div>
								</div>
								
								<div class="form-group" <c:if test="${inspectionRecord.equipment.recordType ne OTHER}">style="display: none;"</c:if> id="OTHER">
									<label class="col-sm-2 control-label">仪器状态</label>
									<div class="col-sm-4">
										<label class="radio-inline">
											<input type="radio" class="uniform" name="equipmentStatus" <c:if test="${inspectionRecord.equipmentStatus eq '正常'}">checked="checked"</c:if> value="正常"> 
											正常
										</label>
										<label class="radio-inline">
											<input type="radio" class="uniform" name="equipmentStatus" <c:if test="${inspectionRecord.equipmentStatus eq '异常'}">checked="checked"</c:if> value="异常"> 
											异常
										</label>
									</div>
									<label class="col-sm-2 control-label">样品状态</label>
									<div class="col-sm-4">
										<label class="radio-inline">
											<input type="radio" class="uniform" name="sampleStatus" <c:if test="${inspectionRecord.sampleStatus eq '正常'}">checked="checked"</c:if> value="正常"> 
											正常
										</label>
										<label class="radio-inline">
											<input type="radio" class="uniform" name="sampleStatus" <c:if test="${inspectionRecord.sampleStatus eq '异常'}">checked="checked"</c:if> value="异常"> 
											异常
										</label>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">描述</label> 
									<div class="col-sm-10">
										<textarea rows="3" cols="5" id="inspectionRecord_description" name="description" class="autosize countable form-control" placeholder="描述" data-limit="100">${inspectionRecord.description}</textarea>
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

	<!-- UNIFORM -->
	<script type="text/javascript" src="${ctx}/static/js/uniform/jquery.uniform.min.js"></script>
	
	<!-- 自定义JS脚本 -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {
			//如果页面无需设置效果，可以不设置 App.setPage ，如设置 App.setPage 而页面缺少对应的元素，会导致JS错误.
			App.setPage("inspectionRecord_forms");  //设置当前启动的页面
			
			App.setHasSub("forms-manager");//设置一级菜单目录ID
			App.setSubMenu("inspections-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置巡检记录路径
			App.init(); //初始化元素以及插件

			$("#serial_number").on("change",function(){
				$("#equipment_name").html("");
				$("#equipment_id").val("");
				
				$("#OTHER").hide();
				$("#TWO_POWER").hide();
				$("#FOUR_POWER").hide();
				$("#ENVIRONMENT").hide();
				$("#UV").hide();
				$("#SALT_MIST").hide();
				
				jQuery.ajax({
	                 url: "${ctx}/equipment/show/"+$(this).val(),
	                 type: "post",
	                 dataType: "json",
	                 success: function(msg) {
	                	 $("#equipment_name").html(msg.equipmentType.name+" -- "+msg.name+"("+msg.modelNumber+" -- "+msg.serialNumber+")");
	                	 $("#equipment_id").val(msg.id);
	                	 if(msg.recordType=='OTHER'){
	                		 $("#OTHER").show();
	                	 }else if(msg.recordType=='TWO_POWER'){
	                		 $("#TWO_POWER").show();
	                	 }else if(msg.recordType=='FOUR_POWER'){
	                		$("#TWO_POWER").show();
	         				$("#FOUR_POWER").show();
	                	 }else if(msg.recordType=='ENVIRONMENT'){
	                		 $("#ENVIRONMENT").show();
	                	 }else if(msg.recordType=='UV'){
	                		 $("#UV").show();
	                	 }else if(msg.recordType=='SALT_MIST'){
	                		 $("#SALT_MIST").show();
	                	 }
	                 }
				 });
			});
			
		});
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>
