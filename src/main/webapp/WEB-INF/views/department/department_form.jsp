<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>部门管理</title>
	<link rel="stylesheet" href="${ctx}/static/js/bootstrap-tree/tree.css" type="text/css" />
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
										<a href="#">部门管理</a>
									</li>
									<li>创建部门</li>
								</ul>
								<!-- /BREADCRUMBS -->
								
							</div>
						</div>
					</div>
					<!-- /PAGE HEADER -->
					
					<div class="box border green">
						<div class="box-title">
							<h4><i class="fa fa-table"></i>创建部门</h4>
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
							<form id="inputForm" class="form-horizontal" action="${ctx}/department/${action}" method="post">
								<input type="hidden" name="id" value="${department.id}"/>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">部门名称</label>
									<div class="col-sm-10">
										<input type="text" id="department_name" name="name" value="${department.name}" class="form-control" placeholder="部门名称"/>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">上级部门</label>
									<div class="col-sm-10">
										<input type="text" id="department_higher_name" value="${department.higherDepartment.name}" class="form-control" placeholder="上级部门" readonly="readonly" />
										<input type="hidden" id="department_higher_id" name="higherDepartment.id" value="${department.higherDepartment.id}" class="form-control"/>
										<div id="departmentTree" style="display:none;"></div>
										
										<%-- <select id="department_higher"  name="higherDepartment.id" class="form-control">
											<option value="0">没有上级</option>
											<c:forEach var="item" items="${departmentList}">
												<option value="${item.id }" <c:if test="${item.id eq department.higherDepartment.id }">selected="selected"</c:if>>${item.name }</option>
											</c:forEach>
										</select> --%>
										
									</div>
								</div>
	
								<div class="form-group">
									<label class="col-sm-2 control-label">部门描述</label> 
									<div class="col-sm-10">
										<textarea rows="3" cols="5" id="description" name="description" class="autosize countable form-control" data-limit="100">${department.description}</textarea>
										<p class="help-block">您还可以输入 <span id="counter"></span> 字.</p> 
									</div>
								</div>
												  
								<div class="form-group">
									<div class="col-sm-offset-2 col-sm-10">
										<input id="submit_btn" class="btn btn-success" type="submit" value="提交"/>&nbsp;
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
	
	<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
		<ul id="departmentTree" class="ztree" style="margin-top:0; width:160px;"></ul>
	</div>
	
	<!-- JAVASCRIPTS -->
	<!-- 引入公共JS脚本 -->
	<%@ include file="/WEB-INF/layouts/include_script.jsp"%>
	
	<!-- bootstrap-tree -->
	<script src="${ctx}/static/js/bootstrap-tree/bootstrap.tree.js"></script>
	
	<!-- TextArea 自动调整SIZE -->
	<script type="text/javascript" src="${ctx}/static/js/autosize/jquery.autosize.min.js"></script>
	<!-- TextArea 计算剩余字数 -->
	<script type="text/javascript" src="${ctx}/static/js/countable/jquery.simplyCountable.min.js"></script>
	
	<!-- 自定义JS脚本 -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {
			//如果页面无需设置效果，可以不设置 App.setPage ，如设置 App.setPage 而页面缺少对应的元素，会导致JS错误.
			App.setPage("department_forms");  //设置当前启动的页面
			
			App.setHasSub("struct-manager");//设置一级菜单目录ID
			App.setSubMenu("departments-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置项目路径
			App.init(); //初始化元素以及插件
			
			jQuery.ajax({
				url: "${ctx}/department/tree",
				type: "post",
				dataType: "json",
				success: function(msg) {
					Tree.setData(msg);
					Tree.init("#departmentTree");
					Tree.onClick(function(node){
						$("#department_higher_name").val(node.name);
						$("#department_higher_id").val(node.id);
						$("#departmentTree").hide();
					});
				}
			});
			
			$("#department_higher_name").click(function(){
				$("#departmentTree").width($(this).outerWidth());
				$("#departmentTree").show();
				$("#departmentTree").offset({top: $(this).offset().top+$(this).outerHeight(),left: $(this).offset().left});
				
			});

			
		});
		

	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>
