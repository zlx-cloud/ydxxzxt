<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预警事件汇总</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/echarts.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<div id="tb">
			<div>
				&nbsp;&nbsp;应用名称：
				<select name="fwqqzZcxxSearch" id="fwqqzZcxxSearch" class="easyui-combobox">
					<option value="">请选择</option>
					<c:forEach items="${fwcyf}" var="zcxx" varStatus="index">
						<option value="${zcxx.fwcyfYyxtbh}">${zcxx.fwcyfYyxtmc}</option>
					</c:forEach>
				</select>
				&nbsp;&nbsp;服务名称：
				<select name="fwbsSearch" id="fwbsSearch" class="easyui-combobox">
					<option value="">请选择</option>
					<c:forEach items="${fwzy}" var="zyxx" varStatus="index">
						<option value="${zyxx.fwbs}">${zyxx.fwmc}</option>
					</c:forEach>
				</select>
				&nbsp;&nbsp;方法名称：
				<select name="ffbsSearch" id="ffbsSearch" class="easyui-combobox" style="width: 230px;">
					<option value="">请先选择服务名称</option>
				</select>
				</br>
				&nbsp;&nbsp;请求开始日期：
				<input type="text" id="startTime" class="easyui-datebox" size="20" value="${time1}" />
				&nbsp;&nbsp;请求结束日期：
				<input type="text" id="endTime" class="easyui-datebox" size="20" value="${time1}" />
				<a href="javascript:searchDbLog()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
			</div>
		</div>
		<div id="charts1" style="height: 280px; width: 99%;"></div>
		<div id="charts" style="height: 480px; width: 99%; margin-top: 20px;"></div>
	</div>

	<script>
	$(document).ready(function() {
		$('#fwbsSearch').combobox({
			onChange : function() {
				var fwbs = $('#fwbsSearch').combobox('getValue');
				$.ajax({
					type : "post",
					url : "${ctx}/eventLog/getFfList?fwbs=" + fwbs,//请求后台数据
					dataType : "json",
					success : function(data) {
						if (data == '' || data == null) {
							$("#ffbsSearch").combobox({//往下拉框塞值
								data : [ {
									"FFBS" : "0",
									"FFMS" : "此服务没有注册方法"
								} ],
								valueField : "FFBS",//value值
								textField : "FFMS",//文本值
								panelHeight : "auto"
							})
						} else {
							$("#ffbsSearch").combobox({//往下拉框塞值
								data : data,
								valueField : "FFBS",//value值
								textField : "FFMS",//文本值
								panelHeight : "auto"
							})
						}
					}
				})
			}
		})
	})
	
	var myChart = echarts.init(document.getElementById('charts'));
	option = {
		tooltip : {
			trigger : 'axis'
		},
		legend : {
			data : [ '频繁操作', '关键字' ]
		},
		grid : {
			left : '3%',
			right : '4%',
			bottom : '3%',
			containLabel : true
		},
		xAxis : {
			type : 'category',
			boundaryGap : false,
			data : ${times}
		},
		yAxis : {
			type : 'value'
		},
		series : [ {
			name : '频繁操作',
			type : 'line',
			smooth: true,
			data : ${freList}
		}, {
			name : '关键字',
			type : 'line',
			smooth: true,
			data : ${keyList}
		} ]
	};
	myChart.setOption(option);
	
	var myChart1 = echarts.init(document.getElementById('charts1'));
	option1 = {
			  tooltip: {
			    trigger: 'item'
			  },
			  legend: {
			    top: '5%',
			    left: 'center'
			  },
			  series: [
			    {
			      name: '预警事件',
			      type: 'pie',
			      radius: ['40%', '70%'],
			      avoidLabelOverlap: false,
			      label: {
			        show: false,
			        position: 'center'
			      },
			      emphasis: {
			        label: {
			          show: true,
			          fontSize: 40,
			          fontWeight: 'bold'
			        }
			      },
			      labelLine: {
			        show: false
			      },
			      data: [
			        { value: ${count1}, name: '频繁操作' },
			        { value: ${count2}, name: '关键字' }
			      ]
			    }
			  ]
			};
	myChart1.setOption(option1);
	
	function searchDbLog() {
		var startTimeVal =$("#startTime").val();
		if(startTimeVal == '' || startTimeVal == null){
			$.messager.alert('系统提示','请选择请求开始日期');
			return;
		}
		var endTimeVal =$("#endTime").val();
		if(endTimeVal == '' || endTimeVal == null){
			$.messager.alert('系统提示','请选择请求结束日期');
			return;
		}
		
		option.xAxis.data=[];
		option.series[0].data=[];
		option.series[1].data=[];
		option1.series[0].data[0].value='';
		option1.series[0].data[1].value='';
		$.ajax({
			type : "post",
			url : "${ctx}/temporary/yjsjhzChartSearch?yybs=" + $("#fwqqzZcxxSearch").val()
					+ "&fwbs=" + $("#fwbsSearch").val() + "&ffbs="
					+  $('#ffbsSearch').combobox('getValue') + "&startTime=" + $("#startTime").val()
					+ "&endTime=" + $("#endTime").val(),
			dataType : "json",
			async : false,
			success : function(data) {
				option.xAxis.data=data[0];
				option.series[0].data=data[1];
				option.series[1].data=data[2];
				option1.series[0].data[0].value=data[3];
				option1.series[0].data[1].value=data[4];
			}
		})
		myChart.setOption(option);
		myChart1.setOption(option1);
	}
	</script>
</body>
</html>