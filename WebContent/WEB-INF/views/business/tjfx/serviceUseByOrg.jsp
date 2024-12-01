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
<title>各分局服务调用量占比统计</title>
<script type="text/javascript" src="${ctx}/static/js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/echarts.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<style>
.panel.datagrid.panel-htop{
	width: 96.5%;
}
.panel{
	overflow:auto;
}
</style>
</head>
<body >
<div id="charts" style="height:600px; width:99%"></div>
<script>
    var myChart = echarts.init(document.getElementById('charts'));
    option = {
    	    title : {
    	        text: '各分局服务调用量占比统计',
    	        subtext: '当天各分局服务调用量占比统计',
    	        x:'center'
    	    },
    	    tooltip : {
    	        trigger: 'item',
    	        formatter: "{a} <br/>{b} : {c} ({d}%)"
    	    },
    	    toolbox: {
    	        show: true,
    	        feature: {
    	            restore: {},
    	            saveAsImage: {}
    	        }
    	    },
    	    grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
    	    legend: {
    	        orient: 'vertical',
    	        left: 'left',
    	        data: ${names}
    	    },
    	    series : [
    	        {
    	            name: '机构及服务调用量:',
    	            type: 'pie',
    	            radius : '55%',
    	            center: ['50%', '60%'],
    	            data: ${data},
    	            itemStyle: {
    	                emphasis: {
    	                    shadowBlur: 10,
    	                    shadowOffsetX: 0,
    	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
    	                }
    	            }
    	        }
    	    ]
    	};
    myChart.setOption(option);
</script>
    
<%-- <table id="dg" title="各分局服务调用量占比统计" class="easyui-datagrid" fitColumns="true"
    pagination="true" rownumbers="true" url="${ctx}/monitoring/queryServiceUseByOrgGrid" fit="false" >
    <thead>
    	<tr>
    		<th field="QQBWBS" width="200" align="center">请求报文标识</th>
    		<th field="FWQQ_IP" width="200" align="center">服务请求IP</th>
    		<th field="FWQQ_DK" width="200" align="center">服务请求端口</th>
    		<th field="JGMC" width="250" align="center">机构名称</th>
    		<th field="FWBS" width="200" align="center">服务标识</th>
    		<th field="FFBS" width="200" align="center">方法标识</th>
    		<th field="FWQQ_RQSJ" width="300" align="center">服务请求时间</th>
    		<th field="FWQQSB_BH" width="250" align="center">服务请求设备编号</th>
    		<th field="FWQQ_SJSJLX" width="300" align="center">服务请求审计事件类型</th>
    		<th field="START_TIME" width="300" align="center">总线收到请求时间</th>
    	</tr>
    </thead>
</table> --%>
</body>
</html>
