<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>实验管理</title>
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
									<li>实验开始</li>
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
					
					<div class="box border primary">
						<div class="box-title">
							<h4><i class="fa fa-table"></i>实验开始</h4>
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
								<div class="col-sm-5">
									<input type="text" id="equipment_serialNumber" class="form-control"  placeholder="设备编号"/>
								</div>
								<div class="col-sm-7">
									<span class="form-control" id="equipment_text" ></span>
									<input type="hidden" id="equipment_id" />
								</div>
							</div>
							<br/>
							<div class="row">
								<div class="col-sm-12">
									<textarea rows="5" cols="10" class="form-control" id="sample_serialNumber" placeholder="样品编号（格式：1403-0001-01;1403-0001-02;1403-0002-01;）"></textarea>
								</div>
							</div>
							<br/>
							<input id="add_btn" class="btn btn-info" type="button" onclick="addExperimentDetail()" value="添加"/>&nbsp;
							
							<hr/>
							<form id="inputForm" class="form-horizontal" action="${ctx}/experiment/start" method="post">
								<table id="experimentTable" class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th>#</th>
											<th>设备</th>
											<th>样品</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
								
								<input id="submit_btn" class="btn btn-info" type="submit" value="提交"/>&nbsp;
								<input id="cancel_btn" class="btn btn-default" type="button" value="返回" onclick="history.back()"/>				  
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
	
	<!-- 自定义JS脚本 -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {
			//如果页面无需设置效果，可以不设置 App.setPage ，如设置 App.setPage 而页面缺少对应的元素，会导致JS错误.
			//App.setPage("price_forms");  //设置当前启动的页面
			
			App.setHasSub("forms-manager");//设置一级菜单目录ID
			App.setSubMenu("experiment-start");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置收费路径
			App.init(); //初始化元素以及插件
			
			$("#equipment_serialNumber").on("change",function(){
				$("#equipment_text").html("");
				$("#equipment_id").val("");
				jQuery.ajax({
	                 url: "${ctx}/equipment/show/"+$(this).val(),
	                 type: "post",
	                 dataType: "json",
	                 success: function(msg) {
	                	 $("#equipment_text").html(msg.equipmentType.name+" -- "+msg.name);
	                	 $("#equipment_id").val(msg.id);
	                 }
				 });
			});
		
		});
		
		function addExperimentDetail(){
			var str='<tr>';
			var str=str+'<td><span style="color: red; cursor: pointer;" onclick="deleteExperimentDetail(this)">删除</span>';
			var str=str+'<input type="hidden" name="equipmentId" value="'+$("#equipment_id").val()+'" />';
			var str=str+'<input type="hidden" name="sampleSerialNumber" value="'+$("#sample_serialNumber").val()+'" />';
			var str=str+'</td>';
			var str=str+'<td>'+$("#equipment_text").html()+'</td>';
			var str=str+'<td>'+$("#sample_serialNumber").val()+'</td>';
			var str=str+'</tr>';
			
			$("#experimentTable tbody").append(str);
		}

		
		function deleteExperimentDetail(e){
			$(e).parent().parent().remove();
		}
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>
