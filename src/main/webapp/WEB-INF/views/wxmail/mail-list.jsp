<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
 <!-- 以UTF-8 的格式解析内容（否则直接打开会是乱码） -->
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>内部邮箱-收件箱</title>
  <!-- 页面宽度将被设置到设备屏幕的像素宽度 -->
  <!-- <meta name="viewport" content="width=device-width, initial-scale=1"> -->
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
  <!-- JQuery Mobile 的资源引入 -->
  <link rel="stylesheet" href="${ctx}/static/js/jquery.mobile-1.4.2/jquery.mobile-1.4.2.min.css" />
  <script src="${ctx}/static/js/jquery/jquery-2.1.1.min.js"></script>
  <script src="${ctx}/static/js/jquery.mobile-1.4.2/jquery.mobile-1.4.2.min.js"></script>
</head>

<body>
  <div data-role="page">
   <div data-role="header" data-position="fixed"> 
    <a href="#" data-role="button" data-icon="home" data-iconpos="notext"></a>
    <h1>收件箱</h1> 
	<a href="#" onclick="window.location.reload()" data-role="button" data-icon="refresh"  data-iconpos="notext">刷新</a>
   </div>
   <div role="main" class="ui-content">
     <ul id="list" class="ui-listview" data-role="listview" data-icon="false">
     <c:forEach items="${emailContents.content}" var="email">
     <li class="ui-li-has-alt ui-first-child">
     <a href="${ctx}/html/email/${email.url}" class="ui-btn" data-transition="flow">
     <div class="ui-grid-a">
     <div class="ui-block-a"><h3>${email.fromName}</h3></div>
     <div class="ui-block-b" style="padding-top:5px;"><span style="font-size:.75em;">${email.sentDate}</span></div>
     </div>
     <p class="topic"><strong>${email.subject}</strong></p>
     <p class="topic">${email.bodyText}</p>
     </a>
     <a href="#" class="ui-btn ui-btn-icon-notext ui-icon-carat-r ui-shadow ui-btn-icon-left"></a>
     </li>
     </c:forEach>
     </ul>
    </div>
    
	<div id="footer" data-role="footer" style="text-align: center; <c:if test="${emailContents.lastPage}">display: none;</c:if>">
		<a href="javascript:refresh(${emailContents.number+3 })" style="width: 85%;height: 100%; font-size: 14px;">点击加载更多</a>
	</div>
  </div>
	
  <script>
  function refresh(page){
	  jQuery.ajax({
			url: "${ctx}/wxmail/json-value",
			data: {'openid':'${openid}','page':page},
			type: "post",
			dataType: "json",
			success: function(msg) {
				$(msg.content).each(function(i,email){
					var html='';
					html=html+'<li class="ui-li-has-alt ui-first-child">';
					html=html+'<a href="${ctx}/html/email/'+email.url+'" class="ui-btn" data-transition="flow">';
					html=html+'<div class="ui-grid-a">';
					html=html+'<div class="ui-block-a"><h3>'+email.fromName+'</h3></div>';
					html=html+'<div class="ui-block-b" style="padding-top:5px;"><span style="font-size:.75em;">'+email.sentDate+'</span></div>';
					html=html+'</div>';
					html=html+'<p class="topic"><strong>'+email.subject+'</strong></p>';
					html=html+'<p class="topic">'+email.shortBodyText+'</p>';
					html=html+'</a>';
					html=html+'<a href="#" class="ui-btn ui-btn-icon-notext ui-icon-carat-r ui-shadow ui-btn-icon-left"></a>';
					html=html+'</li>';
					$("#list").append(html);
				});
				if(msg.lastPage){
					$("#footer").hide();
				}else{
					$("#footer a").attr("href","javascript:refresh("+(msg.number+2)+")");
				}
			},
			 error: function(XMLHttpRequest, textStatus, errorThrown) {
				 //Json出错处理.
                 alert(XMLHttpRequest.status);
                 alert(XMLHttpRequest.readyState);
                 alert(textStatus);
             }
		});
  }

  </script>
</body>

</html>