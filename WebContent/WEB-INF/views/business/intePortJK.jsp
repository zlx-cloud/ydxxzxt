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
</head>
<body>
	<div id="charts01" style="height: 300px; width: 48%; float: left; margin-top: 10px;"></div>
	<div id="charts02" style="height: 300px; width: 48%; float: right; margin-top: 10px;"></div>
	<div id="charts03" style="height: 300px; width: 48%; float: left; margin-top: 10px;"></div>
	<div id="charts" style="height: 300px; width: 48%; float: right; margin-top: 10px;"></div>
</body>
<script type="text/javascript">
var myChart;
var xdata;
var ydata;
var statusdata;
function drewEcharts() {
	myChart = echarts.init(document.getElementById('charts'));
	option = {
		title: {
			text: '总线运行状态监控',
			left: 'center'
		},
		tooltip: {
			trigger: 'axis',
			axisPointer: {
				type: 'shadow'
			},
			formatter: function (params, ticket, callback) {
				var res = getDetailsData(params);
				return res;
			}
		},
		grid: {
			left: '3%',
			right: '3%',
			bottom: '1%',
			top: '15%',
			containLabel: true
		},
		yAxis: [{
				type: 'value',
				position: 'top',
				axisLabel: {
					formatter: '{value} 毫秒'
				}
			}
		],
		xAxis: [{
				type: 'category',
				data: xdata,
				axisLabel: {
					formatter: ''
				}
			}
		],
		series: [{
				name: '执行时间',
				type: 'bar',
				barWidth: '30%',
				data: ydata,
				itemStyle: {
					normal: {
						label: {
							show: true,
							position: 'top',
							formatter: '{b}'
						},
						color: function (params) {
							if (params.data.color != null
								 && params.data.color != undefined) {
								return params.data.color;
							} else {
								return params.data.color;
							}
						}
					}
				}
			}
		]
	}
	myChart.setOption(option);
	myChart.hideLoading();
}
$(document).ready(function () {
	drewEcharts();
	btn_reset();
});
function getDetailsData(param) {
	var result = "";
	var str = param[0].name;
	if (str != undefined) {
		for (var i = 0; i < statusdata.length; i++) {
			if (statusdata[i].split("-")[4] == str) {
				result += "IP：" + statusdata[i].split("-")[0] + "<br/>";
				result += "端口：" + statusdata[i].split("-")[3] + "<br/>";
				result += "状态：" + statusdata[i].split("-")[1] + "<br/>";
				result += "耗时:" + statusdata[i].split("-")[2] + "毫秒";
			}
		}
	}
	return result;
}
function btn_reset() {
	$.ajax({
		url: "${ctx}/ssjk/queryIntePortEchartsData",
		type: "get",
		async: true,
		dataType: "json",
		success: function (data) {
			statusdata = data.status;
			xdata = data.x;
			ydata = data.y;
			refreshData(xdata, ydata);
		}
	});
}
function refreshData(xdata, ydata) {
	if (!myChart) {
		return;
	}
	//更新数据
	var option = myChart.getOption();
	option.xAxis[0].data = xdata;
	option.series[0].data = ydata;
	myChart.setOption(option);
}
 </script>
 <script>
var myChart01 = echarts.init(document.getElementById('charts01'));
option01 = {
	title: {
		text: '请求访问频次监测',
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
	dataZoom: {
		show: false,
		start: 0,
		end: 100
	},
	grid: {
		left: '3%',
		right: '3%',
		bottom: '8%',
		containLabel: true
	},
	xAxis: [{
			type: 'category',
			boundaryGap: true,
			data: ${times},
			axisLabel: {
				interval: 0,
				rotate: 40
			}
		}
	],
	yAxis: [{
			type: 'value',
			scale: true,
			name: '访问频次',
			boundaryGap: [0.2, 0.2]
		}
	],
	series: [{
			name: '请求访问频次',
			type: 'line',
			step: 'start',
			xAxisIndex: 0,
			yAxisIndex: 0,
			data: ${counts1}
		}
	]
};
myChart01.setOption(option01);

var myChart02 = echarts.init(document.getElementById('charts02'));
option02 = {
	title: {
		text: '异常发生监测',
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
	dataZoom: {
		show: false,
		start: 0,
		end: 100
	},
	grid: {
		left: '3%',
		right: '3%',
		bottom: '8%',
		containLabel: true
	},
	xAxis: [{
			type: 'category',
			boundaryGap: true,
			data: ${times},
			axisLabel: {
				interval: 0,
				rotate: 40
			}
		}
	],
	yAxis: [{
			type: 'value',
			scale: true,
			name: '异常发生次数',
			boundaryGap: [0.2, 0.2]
		}
	],
	series: [{
			name: '异常发生次数',
			type: 'line',
			step: 'start',
			xAxisIndex: 0,
			yAxisIndex: 0,
			data: ${counts2}
		}
	]
};
myChart02.setOption(option02);

var myChart03 = echarts.init(document.getElementById('charts03'));
option03 = {
	title: {
		text: '执行时长监测',
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
	dataZoom: {
		show: false,
		start: 0,
		end: 100
	},
	grid: {
		left: '3%',
		right: '3%',
		bottom: '8%',
		containLabel: true
	},
	xAxis: [{
			type: 'category',
			boundaryGap: true,
			data: ${times},
			axisLabel: {
				interval: 0,
				rotate: 40
			}
		}
	],
	yAxis: [{
			type: 'value',
			scale: true,
			name: '执行时长',
			boundaryGap: [0.2, 0.2]
		}
	],
	series: [{
			name: '执行时长',
			type: 'line',
			step: 'start',
			xAxisIndex: 0,
			yAxisIndex: 0,
			data: ${counts3}
		}
	]
};
myChart03.setOption(option03);

setInterval(function () {
	var data0 = option01.series[0].data;
	data0.shift();
	option01.xAxis[0].data.shift();
	$.ajax({
		url: "${ctx}/ssjk/selectQqfwCount",
		type: "post",
		async: false,
		dataType: "json",
		success: function (data) {
			data0.push(data[0]);
			option01.xAxis[0].data.push(data[1]);
		}
	});
	myChart01.setOption(option01);
	
	var data2 = option02.series[0].data;
	data2.shift();
	option02.xAxis[0].data.shift();
	$.ajax({
		url: "${ctx}/ssjk/selectYcCount",
		type: "post",
		async: false,
		dataType: "json",
		success: function (data) {
			data2.push(data[0]);
			option02.xAxis[0].data.push(data[1]);
		}
	});
	myChart02.setOption(option02);
	
	var data3 = option03.series[0].data;
	data3.shift();
	option03.xAxis[0].data.shift();
	$.ajax({
		url: "${ctx}/ssjk/selectZxTime",
		type: "post",
		async: false,
		dataType: "json",
		success: function (data) {
			data3.push(data[0]);
			option03.xAxis[0].data.push(data[1]);
		}
	});
	myChart03.setOption(option03);
	
}, 5000);
</script>
</html>