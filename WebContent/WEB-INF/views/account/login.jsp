<%@page import="java.util.Date,java.text.DateFormat,org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>

 <link rel="shortcut icon" href="${ctx}/static/images/favicon.ico">
<link href="${ctx}/static/style/alogin.css" rel="stylesheet" type="text/css" />
<title>北京市公安局移动信息资源服务子系统</title>
<script type="text/javascript">
	function loadimage(){
		document.getElementById("randImage").src="${ctx}/login/image?"+Math.random();
	}
</script>
</head>
<body>
<form id="form1" name="form1" action="${ctx}/login" method="post">

	<div class="MAIN">
		<%-- <ul>
			<li class="top"></li>
			<li class="top2"></li>
			<li class="topA"></li>
			 <li class="topB">
				<span> 
					<a href="#" target="_blank"><img src="${ctx}/static/images/login/logo.jpg" alt="" style="height: 152px;
          margin-left: 64px;
         width: 181px;
          margin-top: 1px" /></a>
				</span>
			</li> 
			<li class="topC"></li>
			<li class="topD">
			<ul class="login">
				<br>
				<br>
				<li><span class="left">用户名：</span> <span style=""> <input id="username" name="username" type="text" class="txt" value="${userName }" /> </span></li>
				<li><span class="left">密&nbsp;&nbsp;&nbsp;码：</span> <span style=""> <input id="password" name="password" type="password" class="txt" value="${password }" onkeydown= "if(event.keyCode==13)form1.submit()"/> </span></li>
				<li><span class="left">验证码：</span> <span style=""> <input type="text" value="${imageCode }" name="imageCode"  class="txtCode" id="imageCode" size="10" onkeydown= "if(event.keyCode==13)form1.submit()"/>&nbsp;<img onclick="javascript:loadimage();"  title="换一张试试" name="randImage" id="randImage" src="${ctx}/login/image" width="60" height="20" border="1" align="absmiddle"> </span></li>
			</ul>
			</li>
			
			<li class="topE"></li>
			<li class="middle_A"></li>
			<li class="middle_B"></li>
			<li class="middle_C"><span class="btn"> <img alt="" src="${ctx}/static/images/login/btnlogin.gif" onclick="javascript:document.getElementById('form1').submit()"/> </span>&nbsp;&nbsp;<span ><font color="red">${LOGIN_ERROR_MSG}</font></span></li>
			<li class="middle_D"></li>
			<li class="bottom_A"></li>
			<li class="bottom_B">北京市公安局移动信息资源服务子系统
			</li>
		</ul> --%>
		<div class="login">
			<div class="login-top">
				<h1>北京市公安局移动信息资源服务子系统</h1>
			</div>
			<div class="login-bottom">
				<div class="login-icon"></div>
				<div class="login-table">
					<div style="margin-top: 40px;">
						用户名：<input id="username" name="username" type="text" class="txt" value="${userName }" placeholder="请输入用户名" />
					</div>
					<div style="margin-top: 20px;">
						密 &nbsp;&nbsp;码：<input id="password" name="password" type="password" class="txt" value="${password }" onkeydown= "if(event.keyCode==13)form1.submit()" placeholder="请输入密码" />
					</div>
					<font style="margin-left: 59px;" color="red">${LOGIN_ERROR_MSG}</font>	
					<div>
					
						<span class="btn" onclick="javascript:document.getElementById('form1').submit()">登录</span>
						<span class="btn" style="margin-left:4px;" onclick="javascript:window.open('${ctx}/register/show')">注册</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>