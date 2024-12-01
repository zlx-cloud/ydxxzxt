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
<title>job</title>
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
<div id="charts" style="height:350px; width:99%"></div>
<script>
    var myChart = echarts.init(document.getElementById('charts'));
    option = {
        color: ['#3398DB'],
        title: {
			text: 'FW_EVENT_LOG',
			subtext: 'FW_EVENT_LOG'
		},
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : ${names},
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'数值',
                type:'bar',
                barWidth: '60%',
                data:${values}
            }
        ]
    };
    myChart.setOption(option)
</script>
    
<table id="dg" title="总线列表" class="easyui-datagrid" fitColumns="true"
    pagination="true" rownumbers="true" url="${ctx}/monitoring/queryFwEventLogGrid" fit="false" >
    <thead>
    	<tr>
    		<th field="SRC_SYS_NAME" width="100" align="center">SRC_SYS_NAME</th>
    		<th field="SRC_SYS_ID" width="100" align="center">SRC_SYS_ID</th>
    		<th field="SRC_BIZ_USER_ID" width="100" align="center">SRC_BIZ_USER_ID</th>
    		<th field="SRC_DEVICE_IMEI" width="100" align="center">SRC_DEVICE_IMEI</th>
    		<th field="EVENT_MESSAGE" width="100" align="center">EVENT_MESSAGE</th>
    		<th field="EVENT_START_TIME" width="100" align="center">EVENT_START_TIME</th>
    		<th field="EVENT_END_TIME" width="100" align="center">EVENT_END_TIME</th>
    	</tr>
    </thead>
</table>
</body>
</html>
