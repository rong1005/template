<!DOCTYPE html>
<html lang="zh">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${email.subject!""}</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
	<!-- JQuery Mobile 的资源引入 -->
	<link rel="stylesheet" href="${contextPath!""}/static/js/jquery.mobile-1.4.2/jquery.mobile-1.4.2.min.css" />
	<script src="${contextPath!""}/static/js/jquery/jquery-2.1.1.min.js"></script>
	<script src="${contextPath!""}/static/js/jquery.mobile-1.4.2/jquery.mobile-1.4.2.min.js"></script>

	<style type="text/css">
		article img{
			max-width: 100%!important;
		}
	</style>  
</head>

<body>	
	<div data-role="page">
		<div data-role="header" data-position="fixed">
			<a href="#" data-role="button" data-rel="back" data-direction="reverse">返回</a>
			<h1>${email.subject!""}</h1>
			<a href="${contextPath!""}/html/email/${email.originalUrl!""}" data-role="button" data-ajax="false">原文</a>
		</div>
		<div role="main" class="ui-content" style="background-color: white;">
			<article>
			${email.bodyHtml!""}
			</article>
			<#if email.attachments?exists>
			<div data-role="collapsible" data-collapsed="false">
				<h4>附件</h4>
				<ul data-role="listview">
					<#list email.attachments as attachment>
					<#-- 枚举量的获取以及比较：记得传入的 Map 中需要包含 enums-->
            		<#-- 1：${enums["com.cn.template.xutil.enums.AttachmentType"].ATTACHMEN} 2：${attachment.attachmentType}-->
					<li><a href="${contextPath!""}/html/email/${attachment.fullUrl}" data-ajax="false">附件${attachment_index+1}：${attachment.fileName}</a></li>
					</#list>
				</ul>
			</div>
			</#if>
		</div>
  </div>
</body>
</html>
