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
							<div class="form-group">
								<label class="col-sm-2 control-label">委托名称</label>
								<div class="col-sm-10">
									<span class="form-control" >${apply.chApplyName}(${apply.enApplyName})</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">委托单位</label>
								<div class="col-sm-4">
									<span class="form-control" >${apply.chConsigner}(${apply.enConsigner})</span>
								</div>
								<label class="col-sm-2 control-label">委托人</label>
								<div class="col-sm-4">
									<span class="form-control" >${apply.chClient}(${apply.enClient})</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">委托名称</label>
								<div class="col-sm-10">
									<span class="form-control" >${apply.chApplyName}(${apply.enApplyName})</span>
								</div>
							</div>
							
							<c:forEach var="field" items="${apply.form.fields }">
								<c:choose>
								<c:when test="${field.fieldType eq SELECT}">
								<div class="form-group">
									<label class="col-sm-2 control-label">${field.chViewName}</label>
									<div class="col-sm-10">
										<span class="form-control">customField[field.name]['ch'](customField[field.name]['en'])</span>
									</div>
								</div>
								<hr>							
								</c:when>
								
								<c:when test="${field.fieldType eq CHECKBOX}">
								<div class="form-group">
									<label class="col-sm-2 control-label">${field.chViewName}</label>
									<div class="col-sm-10">
										<span class="form-control">customField[field.name]['ch'](customField[field.name]['en'])</span>
									</div>
								</div>
								<hr>							
								</c:when>
								
								<c:when test="${field.fieldType eq RADIO}">
								<div class="form-group">
									<label class="col-sm-2 control-label">${field.chViewName}</label>
									<div class="col-sm-10">
										<span class="form-control">customField[field.name]['ch'](customField[field.name]['en'])</span>
									</div>
								</div>
								<hr>							
								</c:when>
								
								<c:when test="${field.fieldType eq DOUBLE or field.fieldType eq INT }">
								<div class="form-group">
									<label class="col-sm-2 control-label">${field.chViewName}</label>
									<div class="col-sm-10">
										<span class="form-control">${customField[field.name] }</span>
									</div>
								</div>
								<hr>	
								</c:when>
								
								<c:when test="${field.fieldType eq TEXT}">
								<div class="form-group">
									<label class="col-sm-2 control-label">${field.chViewName}</label>
									<div class="col-sm-5">
										<span class="form-control">${customField[field.name]['ch'] }</span>
									</div>
									<div class="col-sm-5">
										<span class="form-control">${customField[field.name]['en'] }</span>
									</div>
								</div>
								</c:when>
								
								<c:otherwise>
								<div class="form-group">
									<label class="col-sm-2 control-label">${field.chViewName}</label>
									<div class="col-sm-10">
										<span class="form-control">${customField[field.name]['ch'] }(${customField[field.name]['en'] })</span>
									</div>
								</div>
								<hr>
								</c:otherwise>
								</c:choose>
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
								<label class="col-sm-1 control-label">用时</label>
								<div class="col-sm-5">
									<input type="text" id="usedTime" class="form-control" />
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
										<th>用时</th>
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
												<input type="hidden" name="usedTime" value="${schedule.usedTime}"/>
												<input type="hidden" name="startTime" value="<fmt:formatDate value='${schedule.startTime }' pattern='yyyy-MM-dd HH:mm' />"/>
												<input type="hidden" name="endTime" value="<fmt:formatDate value='${schedule.endTime }' pattern='yyyy-MM-dd HH:mm' />"/>
												<input type="hidden" name="sampleId" value="${schedule.sample.id}"/>
											</td>
											<td>${schedule.sample.serialNumber }</td>
											<td>${schedule.equipment.equipmentType.name} -- ${schedule.equipment.name} </td>
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
			
			countTotalPrice();
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
			var usedTime = Number($("#usedTime").val());
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
				str=str+'<input type="hidden" name="usedTime" value="'+usedTime+'"/>';
				str=str+'<input type="hidden" name="startTime" value="'+startTime+'"/>';
				str=str+'<input type="hidden" name="endTime" value="'+endTime+'"/>';
				str=str+'<input type="hidden" name="sampleId" value="'+$(domEle).val()+'"/>';
				str=str+'</td>';
				str=str+'<td>'+$("#td_"+$(domEle).val()).html()+'</td>';
				str=str+'<td>'+$("#equipmentType option:selected").text()+' -- '+$("#equipment option:selected").text()+'</td>';
				str=str+'<td>'+usedTime+'</td>';
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
