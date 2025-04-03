<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>服务质量分析</title>
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
				&nbsp;&nbsp; 
				请求开始日期：<input type="text" id="startTime" class="easyui-datebox" size="20" value="${time}" />
				&nbsp;&nbsp; 
				请求结束日期：<input type="text" id="endTime" class="easyui-datebox" size="20" value="${time}" />
				&nbsp;&nbsp; 
				应用名称：
				<select name="fwqqzZcxxSearch" id="fwqqzZcxxSearch" class="easyui-combobox">
					<option value="">请选择</option>
					<c:forEach items="${fwcyf}" var="zcxx" varStatus="index">
						<option value="${zcxx.fwcyfYyxtbh}">${zcxx.fwcyfYyxtmc}
						</option>
					</c:forEach>
				</select>
				<a href="javascript:searchData()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
			</div>
		</div>
		
		<div id="charts" style="height: 520px; width: 99%; margin-top: 30px;"></div>
		
		<table id="dg" title="服务质量分析" class="easyui-datagrid" fitColumns="true"
    		pagination="true" rownumbers="true" fit="false">
    		<thead>
    			<tr>
    				<th field="TJRQ_DATE" width="200" align="center">请求日期</th>
    				<th field="TJRQ_HOUR" width="200" align="center">请求小时</th>
    				<th field="FFQQZ" width="200" align="center">应用标识</th>
    				<th field="YYMC" width="250" align="center">应用名称</th>
    				<th field="NUM" width="200" align="center">请求次数</th>
    			</tr>
    		</thead>
		</table>
	</div>

	<script>
		$(document).ready(function() {
			searchData();
		})
	
		var myChart = echarts.init(document.getElementById('charts'));
		option = {
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					type : 'cross',
					crossStyle : {
						color : '#999'
					}
				}
			},
			legend : {
				data : []
			},
			xAxis : [ {
				type : 'category',
				data : [],
				axisPointer : {
					type : 'shadow'
				},
				axisLabel: {
					interval: 0,
					rotate: 40
				}
			} ],
			yAxis : {
				type : 'value',
				name : '访问量'
			},
			series : []
		};
		myChart.setOption(option);

		function searchData() {
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
			
			$('#dg').datagrid('options').url = '${ctx}/temporary/fwzlfxList';
			$('#dg').datagrid('load', {
				yybs : $("#fwqqzZcxxSearch").val(),
				startTime : $("#startTime").val(),
				endTime : $("#endTime").val()
			});
			
			myChart.clear();
			$.ajax({
				type : "post",
				url : "${ctx}/temporary/fwzlfxSearch?yybs="
					+ $("#fwqqzZcxxSearch").val() + "&startTime="
					+ $("#startTime").val() + "&endTime="
					+ $("#endTime").val(),
				dataType : "json",
				async : false,
				success : function(data) {
					option.legend.data = data[0];
					option.xAxis[0].data = data[1];
					option.series = data[2];
					myChart.setOption(option);
				}
			})
		}
	</script>
</body>
</html>