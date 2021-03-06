<%@page import="com.cn.template.xutil.enums.RecordType"%>
<%@page import="com.cn.template.xutil.enums.Whether"%>
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
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>巡检记录管理</title>
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
										巡检记录列表
									</li>
								</ul>
								<!-- /BREADCRUMBS -->
								
							</div>
						</div>
					</div>
					<!-- /PAGE HEADER -->
					
					<!-- Alerts Message -->
					<c:if test="${not empty message}">
					<div class="alert alert-block alert-info fade in">
						<a class="close" data-dismiss="alert" href="#" aria-hidden="true">&times;</a>
						<h4 style="margin: 0;"><i class="fa fa-check-square-o"></i> ${message}</h4>
					</div>
					</c:if>
					<!-- /Alerts Message -->

					<!-- EXPORT TABLES -->
					<div class="row">
						<div class="col-md-12">
							<!-- BOX -->
							<div class="box border primary">
								<div class="box-title">
									<h4><i class="fa fa-table"></i>巡检记录列表</h4>
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
									<form class="form-inline" action="#">
										<div class="form-group">
											<input type="text" name="search_LIKE_name" class="form-control" value="${param.search_LIKE_name}" placeholder="巡检记录名称" />
										</div>
										<button type="submit" class="btn btn-inverse" id="search_btn"> 查 询 </button>
										<tags:sort/>
									</form>
									
									<br/>
									
									<table  class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th>设备</th>
												<th>是否异常</th>
												<th>巡检时间</th>
												<th>巡检人</th>
												<th>巡检记录</th>
												<th>管理</th>
											</tr>
										</thead>
										<tbody>
										<c:forEach items="${inspectionRecords.content}" var="inspectionRecord">
											<tr>
												<td>${inspectionRecord.equipment.equipmentType.name} -- ${inspectionRecord.equipment.name}</td>
												<td>
												${inspectionRecord.isError.value}
												<c:if test="${inspectionRecord.isError eq YES}">
													 -- 
													 <c:choose>
													 <c:when test="${inspectionRecord.isHandle eq YES}">
													 	<a href="${ctx}/ehandle/${inspectionRecord.id}" style="color: green;">已经处理</a>
													 </c:when>
													 <c:otherwise>
													 	<a href="${ctx}/ehandle/${inspectionRecord.id}" style="color: red;">异常处理</a>
													 </c:otherwise>
													 </c:choose>
													
												</c:if>
												</td>
												<td><fmt:formatDate value="${inspectionRecord.createTime }" pattern="yyyy-MM-dd HH:mm" /></td>
												<td>${inspectionRecord.user.name}</td>
												<td>
												<c:if test="${inspectionRecord.equipment.recordType eq TWO_POWER}">
												CH1:${inspectionRecord.ch1}V; CH2:${inspectionRecord.ch2}V;
												</c:if>
												<c:if test="${inspectionRecord.equipment.recordType eq FOUR_POWER}">
												CH1:${inspectionRecord.ch1}V; CH2:${inspectionRecord.ch2}V; 
												CH3:${inspectionRecord.ch3}V; CH4:${inspectionRecord.ch4}V;
												</c:if>
												<c:if test="${inspectionRecord.equipment.recordType eq ENVIRONMENT}">
												温度:${inspectionRecord.temperature}℃; 湿度:${inspectionRecord.humidity}%;
												</c:if>
												<c:if test="${inspectionRecord.equipment.recordType eq UV}">
												UV-1:${inspectionRecord.uv1}V/M2; UV-2:${inspectionRecord.uv2}V/M2; 
												UV-3:${inspectionRecord.uv3}V/M2; UV-4:${inspectionRecord.uv4}V/M2; 
												</c:if>
												<c:if test="${inspectionRecord.equipment.recordType eq SALT_MIST}">
												试验室温度:${inspectionRecord.labTemp}℃; 饱和桶温度:${inspectionRecord.saturatedBarrelTemp}℃; 
												气压:${inspectionRecord.pressure}kg/cm2;
												</c:if>
												<c:if test="${inspectionRecord.equipment.recordType eq OTHER}">
												仪器:${inspectionRecord.equipmentStatus};样品:${inspectionRecord.sampleStatus};
												</c:if>
												</td>
												<td>
													<a href="${ctx}/inspection/update/${inspectionRecord.id}">修改</a> / 
													<a href="${ctx}/inspection/delete/${inspectionRecord.id}" onclick="return confirm('是否删除该巡检记录？')" >删除</a>
												</td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
									<div class="row">
										<div class="col-sm-12">
											<div class="pull-right">
												<tags:pagination page="${inspectionRecords}" paginationSize="5"/>
											</div>
											
											<div class="pull-left">
												<a class="btn btn-info" href="${ctx}/inspection/create?applyId=${applyId}">添加巡检记录</a>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- /BOX -->
						</div>
					</div>
					<!-- /EXPORT TABLES -->
					
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
			App.setSubMenu("inspections-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置项目路径
			App.init(); //初始化元素以及插件
		});
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>