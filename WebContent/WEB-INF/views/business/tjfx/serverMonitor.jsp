<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>监测图标展示</title>
<script type="text/javascript"
	src="${ctx}/static/js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/echarts.js"></script>
<script type="text/javascript" src="${ctx}/static/js/serverMonitor.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/easyui/themes/icon.css">
<script type="text/javascript"
	src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>

	<!-- 服务器1 -->
	<div>
		<span style="color:red;">ORACLE（orcl）服务器（${serverurl}）</span>
		<div id="server" style="height: 350px; margin-top: 10px;"></div>
	</div>
	<!-- 服务器2 -->
	<div>
		<span style="color:red;">总线服务器(主)（${serverurl1}）</span>
		<div id="server1" style="height: 350px; margin-top: 10px;"></div>
	</div>
	<!-- 服务器3 -->
	<div>
		<span style="color:red;">总线服务器（从）（${serverurl2}）</span>
		<div id="server2" style="height: 350px; margin-top: 10px;"></div>
	</div>
	<!-- 服务器4 -->
	<div>
		<span style="color:red;"> WEB应用服务器（${serverurl3}）</span>
		<div id="server3" style="height: 350px; margin-top: 10px;"></div>
	</div>
	<!-- 服务器5 -->
	<div>
		<span style="color:red;">内存库(redis)（${serverurl4}）</span>
		<div id="server4" style="height: 350px; margin-top: 10px;"></div>
	</div>

</body>
</html>
