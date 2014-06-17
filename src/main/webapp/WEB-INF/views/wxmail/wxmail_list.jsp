<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>国光电器股份有限公司</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${ctx}/static/js/jquery.mobile-1.4.2/jquery.mobile-1.4.2.min.css" />
<script src="${ctx}/static/js/jquery/jquery-2.1.1.min.js"></script>
<script src="${ctx}/static/js/jquery.mobile-1.4.2/jquery.mobile-1.4.2.min.js"></script>
</head>

<body>
	<div data-role="page" id="first">
		<div data-role="header" data-position="fixed">
			<a href="#" data-role="button" data-icon="back">返回</a>
			<h1>收件箱</h1> 
			<a href="#" onclick="window.location.reload()" data-role="button" data-icon="refresh">刷新</a>
		</div>
		
		<div role="main" class="ui-content">
		
		<ul id="list" class="ui-listview" data-role="listview" data-icon="false" data-split-icon="delete">
            <li class="ui-li-has-alt ui-first-child">
                <a href="#demo-mail" class="ui-btn">
					<div class="ui-grid-a">
                     <div class="ui-block-a"><h3>黄晓茵</h3></div>
					 <div class="ui-block-b" style="padding-top:5px; text-align: right;"><span style="font-size:.75em;">2014/06/06 7:58</span></div>
					</div>
                    <p class="topic"><strong>“国光杯”足球赛球讯</strong></p>
                    <p>昨天（6月5日）赛果：（男子A组）质管&国专联队以5：4胜国际音响事业部创业队（男子B组）J13E&J14W联队以4：1胜J23E车间</p> 
                </a>
                <a href="#" class="delete ui-btn ui-btn-icon-notext ui-icon-carat-r ui-shadow ui-btn-icon-left"></a>
            </li>
            <li class="ui-li-has-alt">
                <a href="#demo-mail" class="ui-btn">
                    <h3>Amazon.com</h3>
                    <p class="topic"><strong>4-for-3 Books for Kids</strong></p>
                    <p>As someone who has purchased children's books from our 4-for-3 Store, you may be interested in these featured books.</p>
                    <p class="ui-li-aside"><strong>4:37</strong>PM</p>
                </a>
                <a href="#" class="delete ui-btn ui-btn-icon-notext ui-icon-carat-r ui-shadow ui-btn-icon-left"></a>
            </li>
            <li class="ui-li-has-alt">
                <a href="#demo-mail" class="ui-btn">
                    <h3>Mike Taylor</h3>
                    <p class="topic"><strong>Re: This weekend in Maine</strong></p>
                    <p>Hey little buddy, sorry but I can't make it up to vacationland this weekend. Maybe next weekend?</p>
                    <p class="ui-li-aside"><strong>3:24</strong>PM</p>
                </a>
                <a href="#" class="delete ui-btn ui-btn-icon-notext ui-icon-carat-r ui-shadow ui-btn-icon-left"></a>
            </li>
            <li class="ui-li-has-alt">
                <a href="#demo-mail" class="ui-btn">
                    <h3>Redfin</h3>
                    <p class="topic"><strong>Redfin listing updates for today</strong></p>
                    <p>There are 3 updates for the home on your watchlist: 1 updated MLS listing and 2 homes under contract.</p>
                    <p class="ui-li-aside"><strong>2:52</strong>PM</p>
                </a>
                <a href="#" class="delete ui-btn ui-btn-icon-notext ui-icon-carat-r ui-shadow ui-btn-icon-left"></a>
            </li>
            <li class="ui-li-has-alt">
                <a href="#demo-mail" class="ui-btn">
                    <h3>Angela Smith</h3>
                    <p class="topic"><strong>Link Request</strong></p>
                    <p>My name is Angela Smith, SEO Consultant. I've greatly enjoyed looking through your site and I was wondering if you'd be interested in providing a link</p>
                    <p class="ui-li-aside"><strong>1:24</strong>PM</p>
                </a>
                <a href="#" class="delete ui-btn ui-btn-icon-notext ui-icon-carat-r ui-shadow ui-btn-icon-left"></a>
            </li>
            <li class="ui-li-has-alt">
                <a href="#demo-mail" class="ui-btn">
                    <h3>Stephen Weber</h3>
                    <p class="topic"><strong>You've been invited to a meeting at Filament Group in Boston, MA</strong></p>
                    <p>Hey Stephen, if you're available at 10am tomorrow, we've got a meeting with the jQuery team.</p>
                    <p class="ui-li-aside"><strong>11:24</strong>AM</p>
                </a>
                <a href="#" class="delete ui-btn ui-btn-icon-notext ui-icon-carat-r ui-shadow ui-btn-icon-left"></a>
            </li>
            <li class="ui-li-has-alt">
                <a href="#demo-mail" class="ui-btn">
                    <h3>jQuery Team</h3>
                    <p class="topic"><strong>Boston Conference Planning</strong></p>
                    <p>In preparation for the upcoming conference in Boston, we need to start gathering a list of sponsors and speakers.</p>
                    <p class="ui-li-aside"><strong>9:18</strong>AM</p>
                </a>
                <a href="#" class="delete ui-btn ui-btn-icon-notext ui-icon-carat-r ui-shadow ui-btn-icon-left"></a>
            </li>
            <li class="ui-li-has-alt">
                <a href="#demo-mail" class="ui-btn">
                    <h3>Avery Walker</h3>
                    <p class="topic"><strong>Re: Dinner Tonight</strong></p>
                    <p>Sure, let's plan on meeting at Highland Kitchen at 8:00 tonight. Can't wait! </p>
                    <p class="ui-li-aside"><strong>4:48</strong>PM</p>
                </a>
                <a href="#" class="delete ui-btn ui-btn-icon-notext ui-icon-carat-r ui-shadow ui-btn-icon-left"></a>
            </li>
            <li class="ui-li-has-alt">
                <a href="#demo-mail" class="ui-btn">
                    <h3>Amazon.com</h3>
                    <p class="topic"><strong>4-for-3 Books for Kids</strong></p>
                    <p>As someone who has purchased children's books from our 4-for-3 Store, you may be interested in these featured books.</p>
                    <p class="ui-li-aside"><strong>4:37</strong>PM</p>
                </a>
                <a href="#" class="delete ui-btn ui-btn-icon-notext ui-icon-carat-r ui-shadow ui-btn-icon-left"></a>
            </li>
            <li class="ui-li-has-alt">
                <a href="#demo-mail" class="ui-btn">
                    <h3>Mike Taylor</h3>
                    <p class="topic"><strong>Re: This weekend in Maine</strong></p>
                    <p>Hey little buddy, sorry but I can't make it up to vacationland this weekend. Maybe next weekend?</p>
                    <p class="ui-li-aside"><strong>3:24</strong>PM</p>
                </a>
                <a href="#" class="delete ui-btn ui-btn-icon-notext ui-icon-carat-r ui-shadow ui-btn-icon-left"></a>
            </li>
            <li class="ui-li-has-alt">
                <a href="#demo-mail" class="ui-btn">
                    <h3>Redfin</h3>
                    <p class="topic"><strong>Redfin listing updates for today</strong></p>
                    <p>There are 3 updates for the home on your watchlist: 1 updated MLS listing and 2 homes under contract.</p>
                    <p class="ui-li-aside"><strong>2:52</strong>PM</p>
                </a>
                <a href="#" class="delete ui-btn ui-btn-icon-notext ui-icon-carat-r ui-shadow ui-btn-icon-left"></a>
            </li>
            <li class="ui-li-has-alt">
                <a href="#demo-mail" class="ui-btn">
                    <h3>Angela Smith</h3>
                    <p class="topic"><strong>Link Request</strong></p>
                    <p>My name is Angela Smith, SEO Consultant. I've greatly enjoyed looking through your site and I was wondering if you'd be interested in providing a link</p>
                    <p class="ui-li-aside"><strong>1:24</strong>PM</p>
                </a>
                <a href="#" class="delete ui-btn ui-btn-icon-notext ui-icon-carat-r ui-shadow ui-btn-icon-left"></a>
            </li>
            <li class="ui-li-has-alt">
                <a href="#demo-mail" class="ui-btn">
                    <h3>Stephen Weber</h3>
                    <p class="topic"><strong>You've been invited to a meeting at Filament Group in Boston, MA</strong></p>
                    <p>Hey Stephen, if you're available at 10am tomorrow, we've got a meeting with the jQuery team.</p>
                    <p class="ui-li-aside"><strong>11:24</strong>AM</p>
                </a>
                <a href="#" class="delete ui-btn ui-btn-icon-notext ui-icon-carat-r ui-shadow ui-btn-icon-left"></a>
            </li>
            <li class="ui-li-has-alt ui-last-child">
                <a href="#demo-mail" class="ui-btn">
                    <h3>jQuery Team</h3>
                    <p class="topic"><strong>Boston Conference Planning</strong></p>
                    <p>In preparation for the upcoming conference in Boston, we need to start gathering a list of sponsors and speakers.</p>
                    <p class="ui-li-aside"><strong>9:18</strong>AM</p>
                </a>
                <a href="#" class="delete ui-btn ui-btn-icon-notext ui-icon-carat-r ui-shadow ui-btn-icon-left"></a>
            </li>
			<li id="getMore" style="text-align: center;">
			点击 加载更多
			</li>
        </ul>
		
		</div>
	</div>
	
  <script>
   $(document).on("tap","#getMore",function(){
	   $(this).text("正在加载...");
	   var html='<li class="ui-li-has-alt ui-first-child">'+
	   '<li class="ui-li-has-alt ui-first-child">'+
	   '<a href="#demo-mail" class="ui-btn">'+
	   '<div class="ui-grid-a">'+
	   '<div class="ui-block-a"><h3>黄晓茵</h3></div>'+
	   '<div class="ui-block-b" style="padding-top:5px; text-align: right;"><span style="font-size:.75em;">2014/06/06 7:58</span></div>'+
	   '</div>'+
	   '<p class="topic"><strong>“国光杯”足球赛球讯</strong></p>'+
	   '<p>昨天（6月5日）赛果：（男子A组）质管&国专联队以5：4胜国际音响事业部创业队（男子B组）J13E&J14W联队以4：1胜J23E车间</p>'+
	   '<a href="#" class="delete ui-btn ui-btn-icon-notext ui-icon-carat-r ui-shadow ui-btn-icon-left"></a>'+
	   ''+
	   '</li>';
	   for(var i = 0;i<10;i++){
		   $(this).before(html);
	   }
	   
	   $(this).text("点击 加载更多");
   });
  </script>
  
 </body>
</html>