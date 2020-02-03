<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>商品列表</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.cookie.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
		<script type="text/javascript">
			$(function(){
				$("#clearButton").click(function(){
					var url = "${pageContext.request.contextPath}/ProductServlet";
					$.post(url,{"method":"clearHistory"},function(){
						$("#historyList").remove();
					});
				});
				
			});
		</script>
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
		
		<%--包含导航条 --%>
			<%@include file="/jsp/header.jsp" %>



		<div class="row" style="width:1210px;margin:0 auto;">
			<div class="col-md-12">
				<ol class="breadcrumb">
					<li><a href="#">首页</a></li>
				</ol>
			</div>
		<c:forEach items="${productPage.data }" var="p">
			<div class="col-md-2">
				<a href="${pageContext.request.contextPath}/ProductServlet?method=findById&pid=${p.pid}&cid=${cid}">
					<img src="${pageContext.request.contextPath}/${p.pimage}" width="170" height="170" style="display: inline-block;">
				</a>
				<p><a href="${pageContext.request.contextPath}/ProductServlet?method=findById&pid=${p.pid}&cid=${cid}" style='color:green'>${p.pname}</a></p>
				<p><font color="#FF0000">商城价：&yen;${p.shop_price }</font></p>
			</div>
		
		</c:forEach>

			

		</div>

		<!--分页 -->
		<div style="width:380px;margin:0 auto;margin-top:50px;" align="center">
			<ul class="pagination" style="text-align:center; margin-top:10px;">
				<c:if test="${productPage.pageNumber == 1 }">
					<li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				</c:if>
				<c:if test="${ !(productPage.pageNumber == 1)}">
					<li ><a href="${pageContext.request.contextPath}/ProductServlet?method=findByCid&cid=${param.cid}&pageNumber=${productPage.pageNumber-1}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
				</c:if>
				<c:forEach begin="1" end="${productPage.totalPage }" var="i">
					<c:if test="${productPage.pageNumber == i}">
						<li class="active"><a href="#">${i }</a></li>
					</c:if>
					<c:if test="${! (productPage.pageNumber == i)}">
						<li ><a href="${pageContext.request.contextPath}/ProductServlet?method=findByCid&cid=${param.cid}&pageNumber=${i}">${i }</a></li>
					</c:if>
				</c:forEach>
				
				<c:if test="${productPage.totalPage == productPage.pageNumber }">
					<li class="disabled"><a href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
				</c:if>
				<c:if test="${!  (productPage.totalPage == productPage.pageNumber) }">
					<li ><a  href="${pageContext.request.contextPath}/ProductServlet?method=findByCid&cid=${param.cid}&pageNumber=${productPage.pageNumber+1}" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
				</c:if>
				
				
			</ul>
		</div>
		<!-- 分页结束=======================        -->

		<!--
       		商品浏览记录:
        -->
		<div style="width:1210px;margin:0 auto; padding: 0 9px;border: 1px solid #ddd;border-top: 2px solid #999;height: 246px;">

			<h4 style="width: 50%;float: left;font: 14px/30px " 微软雅黑 ";">浏览记录</h4>
			
			<div style="width: 50%;float: right;text-align: right;">
				<a href="" id="clearButton">clear</a>
				<a href="">more</a>
			</div>
			<div style="clear: both;"></div>

			<div style="overflow: hidden;">

				<ul style="list-style: none;" id="historyList">
					<c:forEach items="${history }" var="p">
						<li style="width: 150px;height: 216;float: left;margin: 0 8px 0 0;padding: 0 18px 15px;text-align: center;">
							<a href="${pageContext.request.contextPath}/ProductServlet?method=findById&pid=${p.pid}&cid=${cid}">
								<img src="${pageContext.request.contextPath}/${p.pimage}" width="130px" height="130px" />
							</a>
							<p>
								<a href="${pageContext.request.contextPath}/ProductServlet?method=findById&pid=${p.pid}&cid=${cid}">
									${p.pname }
								</a>
							</p>
						</li>
					</c:forEach>
				</ul>

			</div>
		</div>
		
		<%--页脚 --%>
		<%@include file="/jsp/footer.jsp" %>


	</body>

</html>