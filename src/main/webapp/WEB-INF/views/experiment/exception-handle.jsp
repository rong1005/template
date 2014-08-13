<%@page import="com.cn.template.xutil.enums.ExceptionHandleType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<c:set var="CONTINUAL" value="<%=ExceptionHandleType.CONTINUAL %>"/>
<c:set var="SUSPEND" value="<%=ExceptionHandleType.SUSPEND %>"/>
<c:set var="STOP" value="<%=ExceptionHandleType.STOP %>"/>
<c:set var="CHANGE" value="<%=ExceptionHandleType.CHANGE %>"/>

<!DOCTYPE html>
<html lang="zh">
<head>
	<title>异常信息管理</title>
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
									<li>异常处理</li>
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
					<form id="inputForm" class="form-horizontal" action="${ctx}/ehandle/${inspectionRecord.id}" method="post">
					
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
							<div class="form-group">
								<label class="col-sm-2 control-label">巡检员</label>
								<div class="col-sm-4">
									<span class="form-control">
										${inspectionRecord.user.name}
									</span>
								</div>
								<label class="col-sm-2 control-label">巡检时间</label>
								<div class="col-sm-4">
									<span class="form-control">
										<fmt:formatDate value="${inspectionRecord.createTime}" pattern="yyyy-MM-dd HH:mm"/>
									</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">设备</label>
								<div class="col-sm-10">
									<span class="form-control">
										${inspectionRecord.equipment.serialNumber} -- ${inspectionRecord.equipment.equipmentType.name} -- ${inspectionRecord.equipment.name}
									</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">描述</label> 
								<div class="col-sm-10">
									<textarea rows="3" cols="5"  disabled="disabled" id="inspectionRecord_description" name="description" class="autosize countable form-control" >${inspectionRecord.description}</textarea>
								</div>
							</div>		  
						</div>
					</div>
					
					<div class="box border primary">
					<div class="box-title">
							<h4><i class="fa fa-table"></i>实验样品</h4>
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
										<th>实验</th>
										<th>样品</th>
										<th>流水号</th>
										<th>状态</th>
										<th>实验用时</th>
										<th>实际开始时间</th>
										<th>计划结束时间</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${schedules}" var="schedule">
										<tr>
											<td>${schedule.sample.apply.chApplyName}(${schedule.sample.apply.enApplyName})</td>
											<td>${schedule.sample.apply.chSampleName}(${schedule.sample.apply.enSampleName})</td>
											<td>${schedule.sample.serialNumber}</td>
											<td>${schedule.sample.status.value}</td>
											<td>${schedule.usedTime}</td>
											<td>${schedule.realStartTime}</td>
											<td>${schedule.realEndTime}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<div class="form-group">
								<div class="col-sm-12">
									<input id="cancel_btn" class="btn btn-default" type="button" value="返回" onclick="history.back()"/>
								</div>
							</div>
						</div>
					</div>
					
					<div class="box border primary">
					<div class="box-title">
							<h4><i class="fa fa-table"></i>异常处理</h4>
							<div class="tools hidden-xs">
								<a href="javascript:;" class="collapse">
									<i class="fa fa-chevron-up"></i>
								</a>
								<a href="javascript:;" class="remove">
									<i class="fa fa-times"></i>
								</a>
							</div>
						</div>
						
						<c:if test="${exceptionHandles eq null }">
						<div class="box-body">
							<div class="form-group">
								<label class="col-sm-2 control-label">处理类型</label>
								<div class="col-sm-10">
									<c:forEach items="<%=ExceptionHandleType.values() %>" var="exceptionHandleType">
										<label class="radio-inline">
											<input type="radio" class="uniform" onchange="changeHandleType(this)" id="exception_handle_type" name="exceptionHandleType" value="${exceptionHandleType}"> 
											${exceptionHandleType.value}
										</label>
									</c:forEach>
								</div>
							</div>
							
							<div class="form-group" style="display: none;" id="STOP">
								<label class="col-sm-2 control-label">实际结束时间</label>
								<div class="col-sm-10">
									<input type="text" id="endTimeNow" name="endTimeNow" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'});" value="<fmt:formatDate value="${inspectionRecord.createTime}" pattern="yyyy-MM-dd HH:mm"/>" />
								</div>
							</div>
							
							<div class="form-group" style="display: none;" id="SUSPEND">
								<label class="col-sm-2 control-label">暂停时间</label>
								<div class="col-sm-5">
									<input type="text" id="stopTime" name="stopTime" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'});" value="<fmt:formatDate value="${inspectionRecord.createTime}" pattern="yyyy-MM-dd HH:mm"/>" />
								</div>
								<div class="col-sm-5">
									<input type="text" id="restratTime" name="restratTime" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'});" />
								</div>
							</div>
							
							<div class="form-group" style="display: none;" id="CHANGE">
								<label class="col-sm-2 control-label">转移设备</label>
								<div class="col-sm-5">
									<select class="form-control" onchange="findEquipment(this)" id="equipmentType">
										<option value="0">请选择</option>
										<c:forEach items="${equipmentTypes}" var="equipmentType">
											<option value="${equipmentType.id }">${equipmentType.name }</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-sm-5">
									<select class="form-control" id="equipment" name="equipment.id">
										
									</select>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label">说明</label>
								<div class="col-sm-10">
									<textarea rows="5" cols="10" class="form-control" name="description"></textarea>
								</div>
							</div>
							
							
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<input id="submit_btn" class="btn btn-info" type="submit" value="提交"/>&nbsp;
									<input id="cancel_btn" class="btn btn-default" type="button" value="返回" onclick="history.back()"/>
								</div>
							</div>
						</div>
						</c:if>
						<c:if test="${exceptionHandles ne null }">
						<div class="box-body">
							<table class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>样品</th>
										<th>处理时间</th>
										<th>处理人</th>
										<th>处理类型</th>
										<th>情况</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${exceptionHandles}" var="exceptionHandle">
										<tr>
											<td>
											${exceptionHandle.schedule.sample.serialNumber} -- 
											${exceptionHandle.schedule.sample.apply.chSampleName}(${exceptionHandle.schedule.sample.apply.enSampleName})
											</td>
											<td><fmt:formatDate value="${exceptionHandle.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
											<td>${exceptionHandle.user.name}</td>
											<td>${exceptionHandle.exceptionHandleType.value}</td>
											<td>
											<c:if test="${exceptionHandle.exceptionHandleType eq STOP}">
												终止时间：<fmt:formatDate value="${exceptionHandle.endTimeNow}" pattern="yyyy-MM-dd HH:mm"/>
											</c:if>
											<c:if test="${exceptionHandle.exceptionHandleType eq SUSPEND}">
												暂停时间：<fmt:formatDate value="${exceptionHandle.stopTime}" pattern="yyyy-MM-dd HH:mm"/> -- <fmt:formatDate value="${exceptionHandle.restratTime}" pattern="yyyy-MM-dd HH:mm"/>
											</c:if>
											<c:if test="${exceptionHandle.exceptionHandleType eq CHANGE}">
												从 
												${exceptionHandle.equipmentBefore.equipmentType.name}(${exceptionHandle.equipmentBefore.name}) 
												转移到 
												${exceptionHandle.equipmentNow.equipmentType.name}(${exceptionHandle.equipmentNow.name}) 
											</c:if>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							
							<div class="form-group">
								<div class="col-sm-12">
									<input id="cancel_btn" class="btn btn-default" type="button" value="返回" onclick="history.back()"/>
								</div>
							</div>
						</div>
						</c:if>
						
					</div>
					
							
					</form>
					
				</div>
			</div>
		</div>
	</div>	
	
	<!-- JAVASCRIPTS -->
	<!-- 引入公共JS脚本 -->
	<%@ include file="/WEB-INF/layouts/include_script.jsp"%>
	
	<!-- 日期插件 -->
	<script type="text/javascript" src="${ctx}/static/js/datepicker97/WdatePicker.js"></script>
	
	<!-- 自定义JS脚本 -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {
			//如果页面无需设置效果，可以不设置 App.setPage ，如设置 App.setPage 而页面缺少对应的元素，会导致JS错误.
			//App.setPage("price_forms");  //设置当前启动的页面
			
			App.setHasSub("forms-manager");//设置一级菜单目录ID
			App.setSubMenu("applys-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置收费路径
			App.init(); //初始化元素以及插件
			
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
		
		function changeHandleType(e){
			$("#STOP").hide();
			$("#SUSPEND").hide();
			$("#CHANGE").hide();
			if($(e).val()=="${STOP}"){
				$("#STOP").show();
			}else if($(e).val()=="${SUSPEND}"){
				$("#SUSPEND").show();
			}else if($(e).val()=="${CHANGE}"){
				$("#CHANGE").show();
			}
		}
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>
