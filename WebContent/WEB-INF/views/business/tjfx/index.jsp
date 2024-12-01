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
<title>首页图表展示</title>
<script type="text/javascript" src="${ctx}/static/js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/echarts.js"></script>
<script type="text/javascript" src="${ctx}/static/js/tjfx.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body >

<!-- 当前活跃服务 -->
<div id="currentActiveServiceCharts" style="height:350px;width:48%;float:left;margin-top: 10px;"></div>

<!-- 各分局服务调用量统计 -->
<div id="serviceUseByOrgCharts" style="height:350px; width:48%;float:right;margin-top: 10px;"></div>

<!-- 服务调用Top10 -->
<div id="serviceUseTopTenCharts" style="height:350px; width:48%;float:left;margin-top: 20px;"></div>

<!-- 服务日调用量统计 -->
<div id="serviceUseByDayCharts" style="height:350px; width:48%;float:right;margin-top: 20px;"></div>

<!-- 各分局服务日调用量统计 -->
<div id="serviceUseByOrgAndDayCharts" style="height:350px; width:48%;float:left;margin-top: 20px;"></div>

<!-- 服务周调用量统计 -->
<div id="serviceUseByWeekCharts" style="height:350px; width:48%;float:right;margin-top: 20px;"></div>

</body>
</html>
