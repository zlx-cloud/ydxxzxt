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
<title>总线服务调用情况</title>
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
    	    title: {
    	        text: '总线服务实时调用情况',
    	        x: 'center'
    	    },
    	    tooltip: {
    	        trigger: 'axis',
    	        axisPointer: {
    	            type: 'cross',
    	            label: {
    	                backgroundColor: '#283b56'
    	            }
    	        }
    	    },
    	    toolbox: {
    	        show: true,
    	        feature: {
    	            restore: {},
    	            saveAsImage: {}
    	        }
    	    },
    	    dataZoom: {
    	        show: false,
    	        start: 0,
    	        end: 100
    	    },
    	    grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
    	    xAxis: [
    	        {
    	            type: 'category',
    	            name: '时间',
    	            boundaryGap: true,
    	            data: ${times}
    	        }
    	    ],
    	    yAxis: [
    	        {
    	            type: 'value',
    	            scale: true,
    	            name: '调用量',
    	            boundaryGap: [0.2, 0.2]
    	        }
    	    ],
    	    series: [
    	        {
    	            name:'总线服务调用量',
    	            type:'line',
    	            step:'start',
    	            xAxisIndex: 0,
    	            yAxisIndex: 0,
    	            data: ${counts}
    	        }
    	    ]
    	};
    
    	myChart.showLoading();
        setInterval(function (){
    		var data0 = option.series[0].data;
    		data0.shift();
    		
    		option.xAxis[0].data.shift();
    		
    		$.ajax({
    			url : "${ctx}/monitoring/selectCurrentZXCount",
    			type : "post",
    			async : false,
    			dataType : "json",
    			success : function(data) {
    				data0.push(data[0]);
    				option.xAxis[0].data.push(data[1]);
    			}
    		});
    		
    		myChart.setOption(option);
    		myChart.hideLoading();
    		
    		$('#dg').datagrid('reload');
    		
    	}, 5000);
</script>
    
<%--  <table id="dg" title="总线服务调用情况" class="easyui-datagrid" fitColumns="true"
    pagination="true" rownumbers="true" url="${ctx}/monitoring/queryZXInfoGrid" fit="false" >
    <thead>
    	<tr>
    		<th field="P_NAME" width="200" align="center">名称</th>
    		<th field="START_TIME" width="200" align="center">开始时间</th>
    		<th field="END_TIME" width="200" align="center">结束时间</th>
    		<th field="STATE" width="200" align="center">状态</th>
    		<th field="EXCEPTION_NODE_ALIAS" width="200" align="center">异常节点别名</th>
    	</tr>
    </thead>
</table> --%>
</body>
</html>
