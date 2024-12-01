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
<title>统计报告</title>
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
<script>
	function downReport(){
		window.location.href = "${ctx}/tjReport/downReport";
	}
</script>
</head>
<body>
<h3 style="text-align: center;">
截至目前，用户注册总量为：<span style="color:red">${yhzczl}，</span>
服务注册总量为：<span style="color:red">${fwzczl}，</span>
接口注册总量为：<span style="color:red">${jkzczl}。</span>
(点此生成<a href="javascript:downReport()" style="text-decoration: none;color:#f71468">统计报告</a>)
</h3>
<div id="chart1" style="height:500px; width:99%"></div>
<div id="chart2" style="height:500px; width:99%"></div>
<script>
    var myChart = echarts.init(document.getElementById('chart1'));
    option = {
    	    title: {
    	        text: '本月服务调用量统计Top10',
    	        x:'center'
    	    },
    	    tooltip: {
    	        trigger: 'axis'
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
                bottom: '15%',
                containLabel: true
            },
    	    xAxis:  {
    	        type: 'category',
    	        boundaryGap: true,
    	        data: ${names},
    	        axisLabel: {
    	        	interval:0,
    	        	rotate:20,
    	        	textStyle: {
    	                fontSize : 13
    	            }
    	        }  
    	    },
    	    yAxis: {
    	        type: 'value',
    	        axisLabel: {
    	            formatter: '{value}'
    	        }
    	    },
    	    series: [
    	        {
    	            name:'调用量',
    	            type:'line',
    	            itemStyle : {
    	            	normal: {
    	            		label : {
    	            			show: true
    	            		}
    	        		}
    	       		},
    	            data:${values}
    	        },
    	    ]
    	};
    myChart.setOption(option);
</script>
<script>
    var myChart2 = echarts.init(document.getElementById('chart2'));
    option2 = {
    	    title: {
    	        text: '本月各分局服务调用量统计Top10',
    	        x:'center'
    	    },
    	    tooltip: {
    	        trigger: 'axis'
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
                bottom: '5%',
                containLabel: true
            },
    	    xAxis:  {
    	        type: 'category',
    	        boundaryGap: true,
    	        data: ${jgNames},
    	        axisLabel: {
    	        	interval:0,
    	        	rotate:20,
    	        	textStyle: {
    	                fontSize : 13
    	            }
    	        }  
    	    },
    	    yAxis: {
    	        type: 'value',
    	        axisLabel: {
    	            formatter: '{value}',
    	            color : 'blue'
    	        }
    	    },
    	    series: [
    	        {
    	            name:'调用量',
    	            type:'line',
    	            itemStyle : {
    	            	normal: {
    	            		label : {
    	            			show: true
    	            		}
    	        		}
    	       		},
    	            data:${jgValues}
    	        },
    	    ]
    	};
    myChart2.setOption(option2);
</script>
</body>
</html>