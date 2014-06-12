<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
	<title>国光电器股份有限公司</title>
	<!--demo展示所用css，不用关心 begin-->
    <link rel="stylesheet" type="text/css" href="${ctx}/static/js/gmu/assets/reset.css" />
    <!--demo展示所用css end-->
    <!--组件依赖css begin-->
    <link rel="stylesheet" type="text/css" href="${ctx}/static/js/gmu/assets/widget/toolbar/toolbar.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/js/gmu/assets/widget/toolbar/toolbar.default.css" /> <!--皮肤文件，若不使用该皮肤，可以不加载-->
        <link rel="stylesheet" type="text/css" href="${ctx}/static/js/gmu/assets/widget/refresh/refresh.default.css" />    <!--皮肤文件，若不使用该皮肤，可以不加载-->
        
    <link rel="stylesheet" type="text/css" href="${ctx}/static/js/gmu/assets/widget/button/button.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/js/gmu/assets/widget/button/button.default.css" />
    <!--组件依赖css end-->

    <!--组件依赖js begin-->
    <script type="text/javascript" src="${ctx}/static/js/gmu/dist/zepto.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/gmu/src/core/gmu.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/gmu/src/core/event.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/gmu/src/core/widget.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/gmu/src/widget/toolbar/toolbar.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/gmu/src/widget/refresh/refresh.js"></script>

    <script type="text/javascript" src="${ctx}/static/js/gmu/src/extend/highlight.js"></script>
    <script type="text/javascript"  id="test" src="${ctx}/static/js/gmu/src/widget/button/button.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/gmu/src/widget/refresh/$lite.js"></script>
    <!--组件依赖js end-->
    <style type="text/css">
    	.data-list {
            width: 100%;
            text-align: left;
        }

        .data-list li {
            border-bottom: 1px solid #e7e7e7;
            background-color: #fafafa;
            font-size: 16px;
            font-weight: bold;
            padding: 10px 10px 10px 20px;
            display: block;
            position: relative;
        }
        
        .data-list li a{
			text-decoration: none;
        	display:block;
        	word-break:keep-all;
        	white-space:nowrap;
        	overflow:hidden;
        	text-overflow:ellipsis;
        }
        
        .data-list li span {
            font-size: 12px;
            color: #969696;
            margin-top: 8px;
            margin-right: 10px;
        }
    </style>
</head>
<body>
<div id="J_toolbar1">
    <!-- <a href="">收件</a> -->
    <h2>收件箱</h2>
    <!-- <span class="btn_1"><span>百科</span></span>
    <span class="btn_1">知道</span> -->
</div>
<script>
        // 全setup方式调用
        $('#J_toolbar1').toolbar({
            fixed: true
        });
</script>


<div class="ui-refresh">
    <ul class="data-list">
    	  
    </ul>
    <div class="ui-refresh-down"></div>      <!--setup方式带有class为ui-refresh-down或ui-refresh-up的元素必须加上，用于放refresh按钮-->
</div>

<script type="text/javascript">
    (function () {
    	var page=1;
    	$.getJSON('${ctx}/wxmail/jsonValue/${openid}/'+page,function(data){
    		$.each(data.html,function(index,item){
    			$('.data-list').append(item);
    		});
    		page=data.page;
    	});
    	
        /*组件初始化js begin*/
        $('.ui-refresh').refresh({
            load: function (dir, type) {
                var me = this;
                $.getJSON('${ctx}/wxmail/jsonValue/${openid}/'+page, function (data) {
                    var $list = $('.data-list'),
                            html = (function (data) {
                                var liArr = [];
                                $.each(data.html, function () {
                                    liArr.push(this);
                                });
                                page=data.page;
                                return liArr.join('');
                            })(data);

                    $list[dir == 'up' ? 'prepend' : 'append'](html);
                    me.afterDataLoading();    //数据加载完成后改变状态
                });
            }
        });
        /*组件初始化js end*/
    })();
    

</script>

</body>
</html>