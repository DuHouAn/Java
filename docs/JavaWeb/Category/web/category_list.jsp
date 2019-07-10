<%@ page import="com.southeast.utils.CookieUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>浏览商品</title>
		<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
		<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="css/style.css" type="text/css" />

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
				width: 100%;
			}
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>
		<div style="width:1210px;margin:0 auto; padding: 0 9px;border: 1px solid #ddd;border-top: 2px solid #999;height: 246px;">
			<div class="col-md-2">
				<a href="${pageContext.request.contextPath}/category?id=1">
					<img src="category/00.jpg" width="170" height="170" style="display: inline-block;">
				</a>
				<p align="center"><a href="${pageContext.request.contextPath}/category?id=1" style='color:green'>手机</a></p>
				<p align="center"><font color="#FF0000">商城价：&yen;1000.00</font></p>
			</div>

			<div class="col-md-2">
				<a href="${pageContext.request.contextPath}/category?id=2">
					<img src="category/01.jpg" width="170" height="170" style="display: inline-block;">
				</a>
				<p align="center"><a href="${pageContext.request.contextPath}/category?id=2" style='color:green'>电视</a></p>
				<p align="center"><font color="#FF0000">商城价：&yen;1299.00</font></p>
			</div>

			<div class="col-md-2">
				<a href="${pageContext.request.contextPath}/category?id=3">
					<img src="category/02.jpg" width="170" height="170" style="display: inline-block;">
				</a>
				<p align="center"><a href="${pageContext.request.contextPath}/category?id=3" style='color:green'>笔记本电脑</a></p>
				<p align="center"><font color="#FF0000">商城价：&yen;2299.00</font></p>
			</div>

			<div class="col-md-2">
				<a href="${pageContext.request.contextPath}/category?id=4">
					<img src="category/03.jpg" width="170" height="170" style="display: inline-block;">
				</a>
				<p align="center"><a href="${pageContext.request.contextPath}/category?id=4" style='color:green'>洗衣机</a></p>
				<p align="center"><font color="#FF0000">商城价：&yen;1999.00</font></p>
			</div>

			<div class="col-md-2">
				<a href="${pageContext.request.contextPath}/category?id=5">
					<img src="category/04.jpg" width="170" height="170" style="display: inline-block;">
				</a>
				<p align="center"><a href="${pageContext.request.contextPath}/category?id=5" style='color:green'>火锅</a></p>
				<p align="center"><font color="#FF0000">商城价：&yen;1999.00</font></p>
			</div>

			<div class="col-md-2">
				<a href="${pageContext.request.contextPath}/category?id=6">
					<img src="category/05.jpg" width="170" height="170" style="display: inline-block;">
				</a>
				<p align="center"><a href="${pageContext.request.contextPath}/category?id=6" style='color:green'>微波炉</a></p>
				<p align="center"><font color="#FF0000">商城价：&yen;1999.00</font></p>
			</div>
		</div>
		<!--
       		商品浏览记录:
        -->
		<div style="width:1210px;margin:0 auto; padding: 0 9px;border: 1px solid #ddd;border-top: 2px solid #999;height: 246px;">

			<h4 style="width: 50%;float: left;font: 14px/30px " 微软雅黑 ";">浏览记录</h4>
			<h4 style="width: 50%;float: left;font: 14px/30px " 微软雅黑 ";"><a href="${pageContext.request.contextPath}/clear">清空浏览记录</a></h4>
			<div style="clear: both;"></div>

			<div style="overflow: hidden;">
				<ul style="list-style: none;">
					<%
						//获取指定名称的cookie ids
						Cookie c= CookieUtils.getCookieByName("ids", request.getCookies());

						//判断ids是否为空
						if(c==null){
					%>
					<h2>暂无浏览记录</h2>
					<%
					}else{//ids=3-2-1
						String[] arr=c.getValue().split("-");
						for(String id:arr){
					%>
					<li style="width: 150px;height: 216;float: left;margin: 0 8px 0 0;padding: 0 18px 15px;text-align: center;"><img src="category/0<%=(Integer.parseInt(id)-1) %>.jpg" width="130px" height="130px" /></li>
					<%
							}
						}
					%>
				</ul>
			</div>
		</div>
	</body>

</html>