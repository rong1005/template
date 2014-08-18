<%@page import="com.cn.template.xutil.enums.ExceptionHandleType"%>
<%@page import="com.cn.template.xutil.enums.Whether"%>
<%@page import="com.cn.template.xutil.enums.RecordType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<c:set var="OTHER" value="<%=RecordType.OTHER %>"/>
<c:set var="TWO_POWER" value="<%=RecordType.TWO_POWER %>"/>
<c:set var="FOUR_POWER" value="<%=RecordType.FOUR_POWER %>"/>
<c:set var="ENVIRONMENT" value="<%=RecordType.ENVIRONMENT %>"/>
<c:set var="UV" value="<%=RecordType.UV %>"/>
<c:set var="SALT_MIST" value="<%=RecordType.SALT_MIST %>"/>

<c:set var="YES" value="<%=Whether.YES %>"/>
<c:set var="NOT" value="<%=Whether.NOT %>"/>

<c:set var="CONTINUAL" value="<%=ExceptionHandleType.CONTINUAL %>"/>
<c:set var="SUSPEND" value="<%=ExceptionHandleType.SUSPEND %>"/>
<c:set var="STOP" value="<%=ExceptionHandleType.STOP %>"/>
<c:set var="CHANGE" value="<%=ExceptionHandleType.CHANGE %>"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>实验样品管理</title>
</head>
<body>

	<div id="main-content">
		<div class="container">
			<div class="row">
				<!-- 页面内容-->
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
										实验样品明细列表
									</li>
								</ul>
								<!-- /BREADCRUMBS -->
								
							</div>
						</div>
					</div>
					<!-- /PAGE HEADER -->
					

					<!-- EXPORT TABLES -->
					<div class="row">
						<div class="col-md-12">
							<!-- BOX -->
							<div class="box border primary">
								<div class="box-title">
									<h4><i class="fa fa-table"></i>实验样品明细列表</h4>
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
									
									<table class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th>操作时间</th>
												<th>操作人</th>
												<th>内容</th>
											</tr>
										</thead>
										<tbody>
										<c:forEach items="${sampleDetails}" var="details">
											<tr>
												<td><fmt:formatDate value="${details.createTime }" pattern="yyyy-MM-dd HH:mm" /></td>
												<td>${details.user.name}</td>
												<td>${details.content}</td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
									<div class="row">
										<div class="col-sm-12">
											<div class="pull-left">
												<button class="btn btn-default" onclick="history.back()">返回</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- /BOX -->
						</div>
					</div>
					<!-- /EXPORT TABLES -->
					
					
					<div class="row">
						<div class="col-md-12">
							<!-- BOX -->
							<div class="box border primary">
								<div class="box-title">
									<h4><i class="fa fa-table"></i>巡检记录</h4>
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
									
									<table class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th>设备</th>
												<th>状态</th>
												<th>巡检时间</th>
												<th>巡检记录</th>
												<th>巡检人</th>
											</tr>
										</thead>
										<tbody>
										<c:forEach items="${sampleInspections}" var="inspections">
											<tr>
												<td>${inspections.schedule.equipment.equipmentType.name }--${inspections.schedule.equipment.name }</td>
												<td>
												<c:if test="${inspections.inspectionRecord.isError eq NOT}">
												<span style="color: red;">正常</span>
												</c:if>
												<c:if test="${inspections.inspectionRecord.isError eq YES}">
												<span style="color: green;">异常</span>
												</c:if>
												</td>
												<td><fmt:formatDate value="${inspections.inspectionRecord.createTime }" pattern="yyyy-MM-dd HH:mm" /></td>
												<td>
												<c:if test="${inspections.inspectionRecord.equipment.recordType eq TWO_POWER}">
												CH1:${inspections.inspectionRecord.ch1}V; CH2:${inspections.inspectionRecord.ch2}V;
												</c:if>
												<c:if test="${inspections.inspectionRecord.equipment.recordType eq FOUR_POWER}">
												CH1:${inspections.inspectionRecord.ch1}V; CH2:${inspections.inspectionRecord.ch2}V; 
												CH3:${inspections.inspectionRecord.ch3}V; CH4:${inspections.inspectionRecord.ch4}V;
												</c:if>
												<c:if test="${inspections.inspectionRecord.equipment.recordType eq ENVIRONMENT}">
												温度:${inspections.inspectionRecord.temperature}℃; 湿度:${inspections.inspectionRecord.humidity}%;
												</c:if>
												<c:if test="${inspections.inspectionRecord.equipment.recordType eq UV}">
												UV-1:${inspections.inspectionRecord.uv1}V/M2; UV-2:${inspections.inspectionRecord.uv2}V/M2; 
												UV-3:${inspections.inspectionRecord.uv3}V/M2; UV-4:${inspections.inspectionRecord.uv4}V/M2; 
												</c:if>
												<c:if test="${inspections.inspectionRecord.equipment.recordType eq SALT_MIST}">
												试验室温度:${inspections.inspectionRecord.LabTemp}℃; 饱和桶温度:${inspections.inspectionRecord.saturatedBarrelTemp}℃; 
												气压:${inspections.inspectionRecord.pressure}kg/cm2;
												</c:if>
												<c:if test="${inspections.inspectionRecord.equipment.recordType eq OTHER}">
												仪器:${inspections.inspectionRecord.equipmentStatus};样品:${inspections.inspectionRecord.sampleStatus};
												</c:if>
												</td>
												<td>${inspections.inspectionRecord.user.name}</td>
											</tr>
											<c:if test="${inspections.inspectionRecord.isError eq YES}">
											<tr>
											<td colspan="5" style="background-color: #FFDAC8;">
											异常处理：
											<c:if test="${exceptionHandleMap[inspections.inspectionRecord.id] eq null}">
											尚未处理
											</c:if>
											
											<c:if test="${exceptionHandleMap[inspections.inspectionRecord.id] ne null}">
												${exceptionHandleMap[inspections.inspectionRecord.id].exceptionHandleType.value}&emsp;&emsp;
											
												<c:if test="${exceptionHandleMap[inspections.inspectionRecord.id].exceptionHandleType eq STOP}">
													[
													终止时间：<fmt:formatDate value="${exceptionHandleMap[inspections.inspectionRecord.id].endTimeNow}" pattern="yyyy-MM-dd HH:mm"/>
													]
												</c:if>
												<c:if test="${exceptionHandleMap[inspections.inspectionRecord.id].exceptionHandleType eq SUSPEND}">
													[
													暂停时间：<fmt:formatDate value="${exceptionHandleMap[inspections.inspectionRecord.id].stopTime}" pattern="yyyy-MM-dd HH:mm"/> -- <fmt:formatDate value="${exceptionHandleMap[inspections.inspectionRecord.id].restratTime}" pattern="yyyy-MM-dd HH:mm"/>
													]
												</c:if>
												<c:if test="${exceptionHandleMap[inspections.inspectionRecord.id].exceptionHandleType eq CHANGE}">
													[
													从 
													${exceptionHandleMap[inspections.inspectionRecord.id].equipmentBefore.equipmentType.name}(${exceptionHandleMap[inspections.inspectionRecord.id].equipmentBefore.name}) 
													转移到 
													${exceptionHandleMap[inspections.inspectionRecord.id].equipmentNow.equipmentType.name}(${exceptionHandleMap[inspections.inspectionRecord.id].equipmentNow.name})
													] 
												</c:if>
											</c:if>
											</td>
											</tr>
											</c:if>
										</c:forEach>
										</tbody>
									</table>
									<div class="row">
										<div class="col-sm-12">
											<div class="pull-left">
												<button class="btn btn-default" onclick="history.back()">返回</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- /BOX -->
						</div>
					</div>
					
					
					<div class="footer-tools">
						<span class="go-top">
							<i class="fa fa-chevron-up"></i> Top
						</span>
					</div>
				
				</div>
				<!-- 页面内容-->
			</div>
		</div>
	</div>

	<!-- JAVASCRIPTS -->
	<!-- 引入公共JS脚本 -->
	<%@ include file="/WEB-INF/layouts/include_script.jsp"%>
	
	<!-- 自定义JS脚本 -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {
			//如果页面无需设置效果，可以不设置 App.setPage ，如设置 App.setPage 而页面缺少对应的元素，会导致JS错误.
			//App.setPage("widgets_box");  //设置当前启动的页面
			
			App.setHasSub("forms-manager");//设置一级菜单目录ID
			App.setSubMenu("applys-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置项目路径
			App.init(); //初始化元素以及插件
		});
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>