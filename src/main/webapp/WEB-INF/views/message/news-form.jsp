<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>图文消息管理</title>
	 <link href="${ctx}/static/js/ueditor/themes/default/css/ueditor.min.css" type="text/css" rel="stylesheet">
	 <style type="text/css">
	 	#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	 </style>
	 
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
										<a href="${ctx}/news-category?messageCategory=${newsMessage.newsCategory.messageCategory}">${newsMessage.newsCategory.title}</a>
									</li>
									<li>
										<a href="${ctx}/news/${newsMessage.newsCategory.id}">${newsMessage.newsCategory.title}列表</a>
									</li>
									<li>创建图文消息</li>
								</ul>
								<!-- /BREADCRUMBS -->
								
							</div>
						</div>
					</div>
					<!-- /PAGE HEADER -->
					<form id="inputForm" class="form-horizontal" action="${ctx}/news/${action}" method="post" enctype="multipart/form-data">
						<div class="box border primary">
							<div class="box-title">
								<h4><i class="fa fa-table"></i>创建图文消息</h4>
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
								<input type="hidden" name="id" value="${newsMessage.id}"/>
								<input type="hidden" name="newsCategory.id" value="${newsMessage.newsCategory.id}"/>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">标题</label>
									<div class="col-sm-10">
										<input type="text" id="newsMessage_title" name="title" value="${newsMessage.title}" class="form-control" placeholder="图文消息名称"/>
									</div>
								</div>
	
								<div class="form-group">
									<label class="col-sm-2 control-label">描述</label> 
									<div class="col-sm-10">
										<textarea rows="3" cols="5" id="description" name="description" class="autosize countable form-control" data-limit="100">${newsMessage.description}</textarea>
										<p class="help-block">您还可以输入 <span id="counter"></span> 字.</p> 
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">图片(640*320)</label> 
									<div class="col-sm-2">
										<input type="file" id="bigPic" name="bigPic" onchange="previewImage(this)"/>
									</div>
									<div id="preview" class="col-sm-8"><img alt="" width="640" height="320" src="${ctx}${newsMessage.bigPicurl}" onerror="javascript:this.src='${ctx}/static/img/logo/ggec_life_better.jpg';"> </div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label">图片(80*80)</label> 
									<div class="col-sm-2">
										<input type="file" id="smallPic" name="smallPic" />
									</div>
									<div  class="col-sm-8"><img alt="" width="80" height="80"  src="${ctx}${newsMessage.smallPicurl}" onerror="javascript:this.src='${ctx}/static/img/logo/ggec_logo_80.gif';"></div>
								</div>
							</div>
						</div>
					
						<div class="box border primary">
							<div class="box-title">
								<h4><i class="fa fa-table"></i>内容</h4>
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
									<div class="col-sm-12">
										<textarea style="width: 100%;height: 260px;" id="news_content" name="content" >${newsMessage.content}</textarea>
									</div> 			
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-sm-12" align="center">
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
	
	<!-- TextArea 自动调整SIZE -->
	<script type="text/javascript" src="${ctx}/static/js/autosize/jquery.autosize.min.js"></script>
	<!-- TextArea 计算剩余字数 -->
	<script type="text/javascript" src="${ctx}/static/js/countable/jquery.simplyCountable.min.js"></script>
	
	<script type="text/javascript" src="${ctx}/static/js/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/ueditor/lang/zh-cn/zh-cn.js"></script>
	 
	<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/customize/addCustomizeDialog.js"></script>
	
	<!-- 自定义JS脚本 -->
	<script src="${ctx}/static/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {
			//如果页面无需设置效果，可以不设置 App.setPage ，如设置 App.setPage 而页面缺少对应的元素，会导致JS错误.
			App.setPage("newsMessage_forms");  //设置当前启动的页面
			
			App.setHasSub("weixin-manager");//设置一级菜单目录ID
			App.setSubMenu("${newsMessage.newsCategory.messageCategory}");//设置二级菜单目录ID
			App.setPath("${ctx}/static");  //设置项目路径
			App.init(); //初始化元素以及插件
			
			UE.getEditor("news_content");
			
			$("#bigPicurl").change(function (){
				alert(document.selection.createRange().text);
			});
			
		});
		
		
        //图片上传预览    IE是用了滤镜。
        function previewImage(file)
        {
          var MAXWIDTH  = 260; 
          var MAXHEIGHT = 180;
          var div = document.getElementById('preview');
          if (file.files && file.files[0])
          {
              div.innerHTML ='<img id=imghead>';
              var img = document.getElementById('imghead');
              img.onload = function(){
                var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
                img.width  =  rect.width;
                img.height =  rect.height;
//                 img.style.marginLeft = rect.left+'px';
                img.style.marginTop = rect.top+'px';
              }
              var reader = new FileReader();
              reader.onload = function(evt){img.src = evt.target.result;}
              reader.readAsDataURL(file.files[0]);
          }
          else //兼容IE
          {
            var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
            file.select();
            var src = document.selection.createRange().text;
            div.innerHTML = '<img id=imghead>';
            var img = document.getElementById('imghead');
            img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
            var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
            status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
            div.innerHTML = "<div id=divhead style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
          }
        }
        
        function clacImgZoomParam( maxWidth, maxHeight, width, height ){
            var param = {top:0, left:0, width:width, height:height};
            if( width>maxWidth || height>maxHeight )
            {
                rateWidth = width / maxWidth;
                rateHeight = height / maxHeight;
                
                if( rateWidth > rateHeight )
                {
                    param.width =  maxWidth;
                    param.height = Math.round(height / rateWidth);
                }else
                {
                    param.width = Math.round(width / rateHeight);
                    param.height = maxHeight;
                }
            }
            
            param.left = Math.round((maxWidth - param.width) / 2);
            param.top = Math.round((maxHeight - param.height) / 2);
            return param;
        }
		
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>
