<%@page import="com.cn.template.xutil.enums.RecordType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>实验设备管理</title>
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
										<a href="${ctx}/equipment/type">类型列表</a>
									</li>
									<li>
										<a href="${ctx}/equipment/${equipment.equipmentType.id}">实验设备列表</a>
									</li>
									<li>
									<c:if test="${action eq 'create' }">
									  创建实验设备
									</c:if>
									<c:if test="${action eq 'update' }">
									  修改实验设备
									</c:if>
									</li>
								</ul>
								<!-- /BREADCRUMBS -->
								
							</div>
						</div>
					</div>
					<!-- /PAGE HEADER -->
					
					<div class="box border primary">
						<div class="box-title">
							<h4><i class="fa fa-table"></i>创建实验设备</h4>
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
							<form id="inputForm" class="form-horizontal" action="${ctx}/equipment/${action}" method="post" enctype="multipart/form-data">
								<input type="hidden" name="id" value="${equipment.id}"/>
								<input type="hidden" name="equipmentType.id" value="${equipment.equipmentType.id}"/>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">实验设备名称</label>
									<div class="col-sm-10">
										<input type="text" id="equipment_name" name="name" value="${equipment.name}" class="form-control" placeholder="实验设备名称"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">设备型号</label>
									<div class="col-sm-10">
										<input type="text" id="equipment_modelNumber" name="modelNumber" value="${equipment.modelNumber}" class="form-control" placeholder="设备型号"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">巡检记录类型</label>
									<div class="col-sm-10">
										<c:forEach items="<%=RecordType.values() %>" var="recordType">
										<label class="radio-inline">
											<input type="radio" class="uniform" id="apply_recordType" name="recordType" <c:if test="${equipment.recordType eq recordType}">checked="checked"</c:if> value="${recordType}"> 
											${recordType.value}
										</label>
										</c:forEach>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">设备编号</label>
									<div class="col-sm-10">
										<input type="text" id="equipment_serialNumber" name="serialNumber" value="${equipment.serialNumber}" class="form-control" placeholder="设备编号"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">设备有效期</label>
									<div class="col-sm-4">
										<input type="text" id="equipment_dateStart" name="dateStart" value="${equipment.dateStart}" class="form-control" onclick="WdatePicker();" />
									</div>
									<label class="col-sm-2 control-label" style="text-align: center;">到</label>
									<div class="col-sm-4">
										<input type="text" id="equipment_dateEnd" name="dateEnd" value="${equipment.dateEnd}" class="form-control" onclick="WdatePicker();"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">图片上传</label>
									<div class="col-sm-10">
										<input type="file" id="equipment_picture" name="picture" />
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label"></label>
									<div class="col-sm-10">
										<img src="${ctx}${equipment.pictureUrl}" onerror="javascript:this.src='${ctx}/static/img/logo/ggec_logo_80.gif';">
									</div>	
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">设备描述</label> 
									<div class="col-sm-10">
										<textarea rows="3" cols="5" id="equipment_description" name="description" class="autosize countable form-control" placeholder="设备描述" data-limit="100">${equipment.description}</textarea>
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
	
	<!-- 日期插件 -->
	<script type="text/javascript" src="${ctx}/static/js/datepicker97/WdatePicker.js"></script>
	
	<!-- UNIFORM -->
	<script type="text/javascript" src="${ctx}/static/js/uniform/jquery.uniform.min.js"></script>

	<!-- 自定义JS脚本 -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {
			//如果页面无需设置效果，可以不设置 App.setPage ，如设置 App.setPage 而页面缺少对应的元素，会导致JS错误.
			App.setPage("equipment_forms");  //设置当前启动的页面
			
			App.setHasSub("forms-manager");//设置一级菜单目录ID
			App.setSubMenu("equipments-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置实验设备路径
			App.init(); //初始化元素以及插件
			
			$("#equipment_name").focus();
			
		});
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>
