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
										部门列表
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
						<div class="col-md-4">
							<div class="box border inverse">
								<div class="box-title">
									<h4><i class="fa fa-sitemap"></i>部门树</h4>
									<div class="tools">
										<a href="javascript:;" class="collapse">
											<i class="fa fa-chevron-up"></i>
										</a>
									</div>
								</div>
								<div id="departmentTree" class="box-body">
								</div>
							</div>
						</div>
						<input type="hidden" id="nodeid"/>
						
						<div class="col-md-8">
							<!-- BOX -->
							<div class="box border primary">
								<div class="box-title">
									<h4><i class="fa fa-table"></i>部门列表</h4>
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
									<div class="form-inline">
										<div class="form-group">
											<input type="text" name="name" id="name" class="form-control" placeholder="名称" />
										</div>
										<button type="button" class="btn btn-inverse" onclick="search()" id="search_btn"> 查 询 </button>
										<tags:sort_json/>
									</div>
									
									<br/>
									
									<!-- table-page 标记该表格使用JSON获取数据并分页. -->
									<table class="table table-striped table-bordered table-hover table-page">
										<thead></thead>
										<tbody></tbody>
									</table>
									
									<div class="row">
										<div class="col-sm-12">
											<div class="pull-right" id="pagination"></div>
											
											<div class="pull-left">
												<a class="btn btn-info" href="${ctx}/department/create">创建部门</a>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- /BOX -->
							
							<div class="footer-tools">
								<span class="go-top">
									<i class="fa fa-chevron-up"></i> Top
								</span>
							</div>
							
						</div>
					</div>
					<!-- /EXPORT TABLES -->
					
					
				
				</div>
				<!-- 页面内容-->
			</div>
		</div>
	</div>

	<!-- JAVASCRIPTS -->
	<!-- 引入公共JS脚本 -->
	<%@ include file="/WEB-INF/layouts/include_script.jsp"%>

	<!-- bootstrap-tree -->
	<script src="${ctx}/static/js/bootstrap-tree/bootstrap.asynchronous.tree.js"></script>
	<!-- bootstrap-table -->
	<script src="${ctx}/static/js/bootstrap-table/bootstrap.table.js"></script>
	
	<!-- 自定义JS脚本 -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		
		jQuery(document).ready(function() {
			//如果页面无需设置效果，可以不设置 App.setPage ，如设置 App.setPage 而页面缺少对应的元素，会导致JS错误.
			//App.setPage("treeview");  //设置当前启动的页面
			
			App.setHasSub("struct-manager");//设置一级菜单目录ID
			App.setSubMenu("departments-list");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置项目路径
			App.init(); //初始化元素以及插件
			
			Tree.setPath("${ctx}");  //设置项目路径
			jQuery.ajax({
				url: "${ctx}/department/asyn_tree",
				type: "post",
				dataType: "json",
				success: function(msg) {
					Tree.setData(msg);
					Tree.init("#departmentTree");
					Tree.onClick(function(node){
						$("#nodeid").val(node.id);
						Table.load("${ctx}/department?sortType="+$("#sortTypeKey").val()+searchParams());
					});
				}
			});

			var thead={idField:"id",columns:[{field:"id",title:"编号"},{field:"name",title:"部门"}]};
			Table.setHead(thead);
			Table.load("${ctx}/department");
			
		});
		
		
		
		//分页
		function page(page){
			Table.load("${ctx}/department?sortType="+$("#sortTypeKey").val()+"&page="+page+searchParams());
		};
		
		//查询
		function search(){
			Table.load("${ctx}/department?sortType="+$("#sortTypeKey").val()+searchParams());
		}
		
		//排序
		function sort(sortType,value){
			//调用时，产生loding的效果.
			App.blockUI($(".table-page").parents(".box"));
			
			$("#sortTypeValue").html(value);
			$("#sortTypeKey").val(sortType);
			Table.load("${ctx}/department?sortType="+sortType+searchParams());
			
			window.setTimeout(function () {
                App.unblockUI($(".table-page").parents(".box"));
            }, 1000);
		}
		
		//查询条件
		function searchParams(){
			var searchParams='';
			if($("#nodeid").val()!=""){
				searchParams=searchParams+'&nodeid='+$("#nodeid").val();
			}
			if($("#name").val()!=""){
				searchParams=searchParams+'&name='+$("#name").val();
			}
			return searchParams;
		}

	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>