<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>

			<%--包含导航条 --%>
			<%@include file="/jsp/header.jsp" %>


		</nav>

		<c:if test="${empty user }">
			<h1 align="center">请登录后再进行操作!</h1>
		</c:if>
		<c:if test="${empty orderPage.data}">
			<h1 align="center">还没下单哦,快去购物吧!</h1>
		</c:if>
		<c:if test="${not empty user && not empty orderPage.data}">
			<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong>我的订单</strong>
					<c:forEach items="${orderPage.data }" var="order">
					
					
					<table class="table table-bordered">
						<tbody>
							<tr class="success">
								<th colspan="3">订单编号:${order.oid } </th>
								<th colspan="2">
									<c:if test="${order.state == 1 }">
										前往
										<a href="${pageContext.request.contextPath }/OrderServlet?method=findByOid&oid=${order.oid}">付款</a>
									</c:if>
									<c:if test="${order.state == 2 }">
										等待发货
									</c:if>
									<c:if test="${order.state == 3 }">
										确认收货
									</c:if>
									<c:if test="${order.state == 4 }">
										订单完成
									</c:if>
								</th>
							</tr>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
							</tr>
							<c:forEach items="${order.orderItems }" var="item">
								<tr class="active">
									<td width="60" width="40%">
										<input type="hidden" name="id" value="22">
										<img src="${pageContext.request.contextPath}/${item.product.pimage }" width="70" height="60">
									</td>
									<td width="30%">
										<a target="_blank">${item.product.pname }</a>
									</td>
									<td width="20%">
										￥${item.product.shop_price }
									</td>
									<td width="10%">
										${item.count }
									</td>
									<td width="15%">
										<span class="subtotal">￥${item.subtotal }</span>
									</td>
								</tr>
							</c:forEach>
							<tr class="warning" >
								<th colspan="5" style="text-align: center;">总计:${order.total }</th>
							</tr>
						</tbody>
					</table>
					</c:forEach>
					
				</div>
			</div>
			
			
			<div style="width:380px;margin:0 auto;margin-top:50px;" align="center">
			<ul class="pagination" style="text-align:center; margin-top:10px;">
				<c:if test="${orderPage.pageNumber == 1 }">
					<li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				</c:if>
				<c:if test="${ !(orderPage.pageNumber == 1)}">
					<li ><a href="${pageContext.request.contextPath}/OrderServlet?method=findByUid&pageNumber=${orderPage.pageNumber-1}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				</c:if>
				<c:forEach begin="1" end="${orderPage.totalPage }" var="i">
					<c:if test="${orderPage.pageNumber == i}">
						<li class="active"><a href="#">${i }</a></li>
					</c:if>
					<c:if test="${! (orderPage.pageNumber == i)}">
						<li ><a href="${pageContext.request.contextPath}/OrderServlet?method=findByUid&pageNumber=${i}">${i }</a></li>
					</c:if>
				</c:forEach>
				
				<c:if test="${orderPage.totalPage == orderPage.pageNumber }">
					<li class="disabled"><a href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
				</c:if>
				<c:if test="${!  (orderPage.totalPage == orderPage.pageNumber) }">
					<li ><a  href="${pageContext.request.contextPath}/OrderServlet?method=findByUid&pageNumber=${orderPage.pageNumber+1}" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
				</c:if>
				
				
			</ul>
		</div>
		
			
		</div>
		</c:if>
		<%--页脚 --%>
		<%@include file="/jsp/footer.jsp" %>

	</body>

</html>