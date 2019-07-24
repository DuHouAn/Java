<%@ page import="java.util.HashMap" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>购物车</title>
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
		<div class="container">
			<a href="${pageContext.request.contextPath}/category_list.jsp">继续购物</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="${pageContext.request.contextPath}/clearCart">清空购物车</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<div class="row">
				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong style="font-size:16px;margin:5px 0;">订单详情</strong>
					<table class="table table-bordered">
						<tbody>
						<tr class="warning" align="center">
							<th>商品</th>
							<th>数量</th>
						</tr>
						<%
							HashMap<String,Integer> map = (HashMap<String,Integer>)request.getSession().getAttribute("cart");
							if(map==null){
								out.print("<tr><th colspan='2'>亲,购物车空空,先去逛逛~~</th></tr>");
							}else{
								for(String name : map.keySet()){
									out.print("<tr class='active'>");

									out.print("<td width='30%'>");
									out.print(name);
									out.print("</td>");
									out.print("<td width='20%'>");
									out.print(map.get(name));
									out.print("</td>");

									out.print("</tr>");
								}
							}
						%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</body>
</html>