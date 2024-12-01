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
<!-- 表格css所有 -->
<script type="text/javascript" src="${ctx}/static/js/jquery-1.10.1.min.js"></script>
   
<%-- <script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script> --%>
<script type="text/javascript" src="${ctx}/static/js/echarts.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>

<!-- 自定義js -->
<script type="text/javascript">

var xdata;
var ydata;
var statusList;
var name;
var IP = '${IP}';
var myChart;

	function getDetailsData(param) {
		var result = "";
        var strid="";
        var strport="";
        var strstatus="";
		var str = param[0].name;
		if (str != undefined) {
			var time = param[0].data.value;
			result = "开始时间:" + str + " 耗时:" + time + "毫秒<br/>";
			for (var i = 0; i < statusList.length; i++) {
				
				if (statusList[i].split("-")[0] == str) {
				strid=statusList[i].split("-")[2];
				strstatus=statusList[i].split("-")[1];
				strport=statusList[i].split("-")[3];
					
				}

			}
			result += "ID：" + strid + " 状态："
							+ strstatus+" 端口："+strport
			return result;
		}
	}
	function drewEcharts() {
		//--- 折柱 ---

		myChart =echarts.init(document.getElementById('main'));
		/* myChart.showLoading({
			text : '正在努力的读取数据中...' //loading话术
		}); */
		
		option = {
			/* title : {
				text : name + '任务',

			}, */
			tooltip : {
				trigger : 'axis',
				formatter : function(params, ticket, callback) {
					var res = getDetailsData(params);
					return res;
				}
			},
			/** dataZoom: {
			 show: true,
			 start :40
			},**/
			legend : {
				data : [ IP ]
			},
			toolbox : {
				show : true,
				/* feature : {
					restore : {
						show : true
					}, */
					saveAsImage : {
						show : true
					}
				/* } */
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : xdata
			} ],
			yAxis : [ {
				type : 'value',
				axisLabel : {
					formatter : '{value} 毫秒'
				}
			} ],
			series : [

			{
				name : IP,
				type : 'line',
				stack : '总量',
				data : ydata
			} ]
		}

		myChart.setOption(option);
		myChart.hideLoading();
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
	
	$(document).ready(function() {
		drewEcharts();
		btn_reset();
	});
	function btn_reset() {
		$.ajax({
			url : "${ctx}/ssjk/queryAllIntePortEchartsData?IP=" + IP,
			type : "get",
			async : false,
			dataType : "json",
			success : function(data) {
			 name = data.name+"";
              statusList = data.status;
				xdata = data.x;
				ydata = data.y;
				refreshData(xdata, ydata);

			}
		});
	}
	clearInterval(timeTicket);
	var timeTicket = setInterval(function() {
		btn_reset();
		$("#dg").datagrid("reload");
	}, 12000);
	
	$(function(){
	$('#dg').datagrid('load',{
		IP:IP
		
	});			
	})					
</script>
</head>
<body>

<div id="main" style="height:300px; width:100%"></div>

<table id="dg" title="总线列表" class="easyui-datagrid" fitColumns="true"  
    pagination="true" rownumbers="true" url="${ctx}/ssjk/queryByIPGrid" fit="false"   pageSize="5"
              pageList="[5, 10, 15]">
    <thead>
    	<tr>
    		<th field="ID" width="50" align="center">ID</th>
    		<th field="IP" width="50" align="center">IP</th>
    		<th field="PORT" width="50" align="center">PORT</th>
    		<th field="EXESTATUS" width="50" align="center">状态</th>
    		<th field="INTERNAME" width="50" align="center">系统名称</th>
    		<th field="EXE_TIME" width="50" align="center">开始时间</th>
    		<th field="FINISH_TIME" width="50" align="center">完成时间</th>
    		<th field="EXETIME" width="50" align="center">耗时(毫秒)</th>
    		
    	</tr>
    </thead>
</table>
</body>
</html>