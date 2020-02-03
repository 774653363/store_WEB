<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
	
	
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员注册</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>

	<script type="text/javascript">
		$(function(){
			var flag1 = false;
			var flag2 = false;
			var flag3 = false;
			var flag4 = false;
			$("#username").blur(function(){
				var url = "${pageContext.request.contextPath}/UserServlet";
				var username = $("#username").val();
				var data = {"method":"checkUsername","username":username};
				$.post(url,data,function(result){
					if(1==result){
					$("#usernameMsg").html("<font style='color: green;'>账户可用!</font>");
					flag1 = true;
					}
					else{
					$("#usernameMsg").html("<font style='color: red;'>账户已存在! </font>");
					$("#registButton").attr("disabled",true);
					flag1 = false;
					}
					
				});
			});
			$("#confirmpwd").blur(function(){
				var password = $("#inputPassword3").val();
				var confirmpwd = $("#confirmpwd").val();
				if(password==confirmpwd){
					$("#passwordConfirmMsg").html("<font style='color: green;'>密码一致!</font>");
					flag2 = true;
					
				}else{
					$("#passwordConfirmMsg").html("<font style='color: red;'>密码不一致! </font>");
					$("#registButton").attr("disabled",true);
					flag2 = false;
				}
			});
			$("#inputEmail3").blur(function(){
				var email = $(this).val();
				var myreg = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
				if(myreg.test(email)){
					flag3 = true;
					$("#emailMsg").html("<font style='color: green;'>邮箱格式正确! </font>");
				}else{
					$("#emailMsg").html("<font style='color: red;'>邮箱格式不正确! </font>");
					$("#registButton").attr("disabled",true);
					flag3 = false;
				}
			});
			$("#inputPassword3").blur(function(){
				var password = $(this).val();
				if(password.length<=5){
					$("#passwordMsg").html("<font style='color: red;'>密码长度需大于5! </font>");
					$("#registButton").attr("disabled",true);
					flag4 = false;
				}else{
					$("#passwordMsg").html("<font style='color: green;'>密码可用! </font>");
					flag4 = true;
				}
			});
			
			$("input").blur(function(){
				if(flag1==true&&flag2==true&&flag3==true){
					$("#registButton").attr("disabled",false);
				}
			});
			
		});
	</script>
<style>
  body{
   margin-top:20px;
   margin:0 auto;
 }
 .carousel-inner .item img{
	 width:100%;
	 height:300px;
 }
 .container .row div{ 
	 /* position:relative;
	 float:left; */
 }
 
font {
    color: #3164af;
    font-size: 18px;
    font-weight: normal;
    padding: 0 10px;
}
 </style>
</head>
<body>



			<%--包含导航条 --%>
			<%@include file="/jsp/header.jsp" %>






<div class="container" style="width:100%;background:url('${pageContext.request.contextPath}/img/regist_bg.jpg');">
<div class="row"> 

	<div class="col-md-2"></div>
	
	


	<div class="col-md-8" style="background:#fff;padding:40px 80px;margin:30px;border:7px solid #ccc;">
		<font>会员注册</font>USER REGISTER
		<form class="form-horizontal" style="margin-top:5px;" action="${pageContext.request.contextPath}/UserServlet" method="post">
			<input type="hidden" name="method" value="register">
			 <div class="form-group">
			    <label for="username" class="col-sm-2 control-label">用户名</label>
			    <div class="col-sm-6">
			      <input type="text" class="form-control" name="username" id="username" placeholder="请输入用户名">
			    </div>
				    <span id="usernameMsg">
				    </span>
			  </div>
			   <div class="form-group">
			    <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
			    <div class="col-sm-6">
			      <input type="password" class="form-control" name="password" id="inputPassword3" placeholder="请输入密码">
			    </div>
			    <span id="passwordMsg">
			    </span>
			  </div>
			   <div class="form-group">
			    <label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
			    <div class="col-sm-6">
			      <input type="password" class="form-control" id="confirmpwd" placeholder="请输入确认密码">
			    </div>
			    <span id="passwordConfirmMsg">
			    </span>
			  </div>
			  <div class="form-group">
			    <label for="inputEmail3" class="col-sm-2 control-label">Email</label>
			    <div class="col-sm-6">
			      <input type="email" class="form-control" name="email" id="inputEmail3" placeholder="Email">
			    </div>
			    <span id="emailMsg">
			    </span>
			  </div>
			  
			  <div class="form-group">
			    <label for="inputTelephone3" class="col-sm-2 control-label">手机</label>
			    <div class="col-sm-6">
			      <input type="telephone" class="form-control" name="telephone" id="inputTelephone3" placeholder="手机号">
			    </div>
			  </div>
			  
			 <div class="form-group">
			    <label for="usercaption" class="col-sm-2 control-label">姓名</label>
			    <div class="col-sm-6">
			      <input type="text" class="form-control" name="name" id="usercaption" placeholder="请输入姓名">
			    </div>
			  </div>
			  <div class="form-group opt">  
			  <label for="inlineRadio1" class="col-sm-2 control-label">性别</label>  
			  <div class="col-sm-6">
			    <label class="radio-inline">
			  <input type="radio" name="sex" id="inlineRadio1" value="男"> 男
			</label>
			<label class="radio-inline">
			  <input type="radio" name="sex" id="inlineRadio2" value="女"> 女
			</label>
			</div>
			  </div>		
			  <div class="form-group">
			    <label for="date" class="col-sm-2 control-label" >出生日期</label>
			    <div class="col-sm-6">
			      <input type="date" class="form-control" name="birthday"  value="2000-01-01">		      
			    </div>
			  </div>
			  
			<!-- 
						 
			<div class="form-group">
			    <label for="date" class="col-sm-2 control-label">验证码</label>
			    <div class="col-sm-3">
			      <input type="text" class="form-control"  >
			      
			    </div>
			    <div class="col-sm-2">
			    <img src="${pageContext.request.contextPath}/img/captcha.jhtml"/>
			    </div>
			    
			  </div>
			  
			 -->
			 
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			      <input type="submit"  width="100" value="注册" disabled="disabled" name="submit" id="registButton" border="0"
				    style="background: url('${pageContext.request.contextPath}/img/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
				    height:35px;width:100px;color:white;">
			    </div>
			  </div>
			</form>
	</div>
	
	<div class="col-md-2"></div>
  
</div>
</div>

	  
	
		<%--页脚 --%>
		<%@include file="/jsp/footer.jsp" %>


</body></html>




