<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
 <!-- 以UTF-8 的格式解析内容（否则直接打开会是乱码） -->
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>JQuery Mobile 样例</title>
  <!-- 页面宽度将被设置到设备屏幕的像素宽度 -->
  <!-- <meta name="viewport" content="width=device-width, initial-scale=1"> -->
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
  <!-- JQuery Mobile 的资源引入 -->
  <link rel="stylesheet" href="${ctx}/static/js/jquery.mobile-1.4.2/jquery.mobile-1.4.2.min.css" />
  <script src="${ctx}/static/js/jquery/jquery-2.1.1.min.js"></script>
  <script src="${ctx}/static/js/jquery.mobile-1.4.2/jquery.mobile-1.4.2.min.js"></script>
</head>

<body>
  <!-- 第一个视图页面 -->
  <div data-role="page" id="first">
   <div data-role="header" data-position="fixed"> 
    <a href="#" data-role="button" data-icon="back">返回</a>
    <h1>收件箱</h1> 
	<a href="#" onclick="window.location.reload()" data-role="button" data-icon="refresh">刷新</a>
   </div>
   <div role="main" class="ui-content">
     <ul id="list" class="ui-listview" data-role="listview" data-icon="false" data-split-icon="delete">
            <li class="ui-li-has-alt ui-first-child ui-btn" onclick="window.location.href=www.baidu.com">
            <div class="ui-grid-a">
            <div class="ui-block-a"><h2>黄晓茵</h2></div>
			<div class="ui-block-b" style="padding-top:5px; text-align: right; padding-right: 10px;"><span style="font-size:.75em;">2014/06/06 7:58</span></div>
			</div>
            <p class="topic"><strong>“国光杯”足球赛球讯</strong></p>
            <p>昨天（6月5日）赛果：（男子A组）质管&国专联队以5：4胜国际音响事业部创业队（男子B组）J13E&J14W联队以4：1胜J23E车间</p>
             
            </li>
			<li id="getMore" style="text-align: center;" onclick="refresh(2);">
			点击 加载更多
			</li>
        </ul>

    </div>

  </div>
	
  <script>
  var page=1;
  jQuery(document).ready(function() {
	  refresh(page);
  });
  
  function refresh(page){
	  jQuery.ajax({
			url: "${ctx}/wxmail/jsonValue/${openid}/"+page,
			type: "post",
			dataType: "json",
			success: function(msg) {
				$(msg.emails).each(function(i,email){
					var html='';
					html=html+'<li class="ui-li-has-alt ui-first-child">';
					html=html+'<a href="${ctx}/html/email/'+email.url+'" class="ui-btn">';
					html=html+'<div class="ui-grid-a">';
					html=html+'<div class="ui-block-a"><h3>'+email.fromName+'</h3></div>';
					html=html+'<div class="ui-block-b" style="padding-top:5px;"><span style="font-size:.75em;">'+email.sentDate+'</span></div>';
					html=html+'</div>';
					html=html+'<p class="topic"><strong>'+email.subject+'</strong></p>';
					html=html+'<p class="topic">'+email.bodyText+'</p>';
					html=html+'</a>';
					html=html+'<a href="#" class="delete ui-btn ui-btn-icon-notext ui-icon-carat-r ui-shadow ui-btn-icon-left"></a>';
					html=html+'</li>';
					$("#getMore").before(html);
				});
				msg.page=page;
			},
			 error: function(XMLHttpRequest, textStatus, errorThrown) {
                 alert(XMLHttpRequest.status);
                 alert(XMLHttpRequest.readyState);
                 alert(textStatus);
             }
		});
  }

  </script>
</body>

</html>