<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>国光电器股份有限公司</title>
	
	<link href="${contextPath!""}/static/js/slideby/styles/style.css" rel="stylesheet" type="text/css">
	<link href="${contextPath!""}/static/js/slideby/styles/framework.css" rel="stylesheet" type="text/css">

<style type="text/css">
	#content .container img{
		max-width: 100%!important;
	}
</style>  
</head>
<body>

<div class="all-elements">
    <div id="content" class="page-content">
        <div class="content">
        	<div class="decoration"></div>

            <div class="container no-bottom">
            	<div class="section-title">
                	<h1 style="font-size:20px;">${email.subject!""}</h1>
					<h2 style="font-size:10px;">${email.fromName!""} &emsp;&emsp; ${email.sentDate?string("yyyy年MM月dd日 HH:mm:ss")} </h2>
                </div>
            </div>
            
            <div class="decoration"></div>

            <div class="container no-bottom">
				${email.bodyHtml!""}
            </div>
            
            <div class="decoration"></div>
            
            <#if email.attachments?exists>
            附件下载:<br/>
            <#list email.attachments as attachment>
            <#-- 枚举量的获取以及比较：记得传入的 Map 中需要包含 enums-->
            <#-- 1：${enums["com.cn.template.xutil.enums.AttachmentType"].ATTACHMEN} 2：${attachment.attachmentType}-->
				附件${attachment_index+1}：${attachment.fileName} <a href="${contextPath!""}/html/email/${attachment.fullUrl}"> 下载 </a>
			</#list>
 
            <div class="decoration"></div>
            </#if>
                
            <div class="content-footer">
                <a href="#" class="go-up-footer"></a>
                <div class="clear"></div>
            </div>
              
        </div>                
    </div>  
</div>

</body>
</html>
