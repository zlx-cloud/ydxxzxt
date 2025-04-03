<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>警员请求排名分析</title>
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
				&nbsp;&nbsp; 应用名称：
				<select name="fwqqzZcxxSearch" id="fwqqzZcxxSearch" class="easyui-combobox">
					<option value="">请选择</option>
					<c:forEach items="${fwcyf}" var="zcxx" varStatus="index">
						<option value="${zcxx.fwcyfYyxtbh}">${zcxx.fwcyfYyxtmc}</option>
					</c:forEach>
				</select>
				&nbsp;&nbsp; 服务名称：
				<select name="fwbsSearch" id="fwbsSearch" class="easyui-combobox">
					<option value="">请选择</option>
					<c:forEach items="${fwzy}" var="zyxx" varStatus="index">
						<option value="${zyxx.fwbs}">${zyxx.fwmc}</option>
					</c:forEach>
				</select>
				&nbsp;&nbsp; 方法名称：
				<select name="ffbsSearch" id="ffbsSearch" class="easyui-combobox" style="width: 230px;">
					<option value="">请先选择服务名称</option>
				</select>
				</br>
				&nbsp;&nbsp; 请求开始日期：
				<input type="text" id="startTime" class="easyui-datebox" size="20" value="${time}" />
				&nbsp;&nbsp; 请求结束日期：
				<input type="text" id="endTime" class="easyui-datebox" size="20" value="${time}" />
				<a href="javascript:searchData()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
			</div>
		</div>

		<div id="charts" style="height: 520px; width: 99%;"></div>

		<table id="dg" title="警员请求排名分析" class="easyui-datagrid"
			fitColumns="true" pagination="true" rownumbers="true" fit="false">
			<thead>
				<tr>
					<th field="TJRQ_DATE" width="10" align="center">请求日期</th>
					<th field="TJRQ_HOUR" width="10" align="center">请求小时</th>
					<th field="YYMC" width="10" align="center">应用名称</th>
					<th field="FWMC" width="10" align="center">服务名称</th>
					<th field="FFMC" width="10" align="center">方法名称</th>
					<th field="POLICE_NAME" width="10" align="center">警员姓名</th>
					<th field="ORG_CODE" width="10" align="center">警员所属机构</th>
					<th field="XXCZRY_GMSFHM" width="15" align="center">警员证件号</th>
					<th field="PHONE" width="10" align="center">警员手机号</th>
					<th field="NUM" width="10" align="center">请求次数</th>
				</tr>
			</thead>
		</table>
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
			});
			
			searchData();
		})

		var myChart = echarts.init(document.getElementById('charts'));
		option = {
			tooltip : {
				trigger : 'axis'
			},
			xAxis : {
				type : 'category',
				axisLabel : {
					interval : 0,
					rotate : 30
				},
				data : []
			},
			yAxis : {
				type : 'value'
			},
			series : [ {
				data : [],
				type : 'bar'
			} ]
		};
		myChart.setOption(option);

		function searchData() {
			var startTimeVal = $("#startTime").val();
			if (startTimeVal == '' || startTimeVal == null) {
				$.messager.alert('系统提示', '请选择请求开始日期');
				return;
			}
			var endTimeVal = $("#endTime").val();
			if (endTimeVal == '' || endTimeVal == null) {
				$.messager.alert('系统提示', '请选择请求结束日期');
				return;
			}

			$('#dg').datagrid('options').url = '${ctx}/temporary/jyqqpmfxList';
			$('#dg').datagrid('load', {
				yybs : $("#fwqqzZcxxSearch").val(),
				fwbs : $("#fwbsSearch").val(),
				ffbs : $('#ffbsSearch').combobox('getValue'),
				startTime : $("#startTime").val(),
				endTime : $("#endTime").val()
			});

			myChart.clear();
			$.ajax({
				type : "post",
				url : "${ctx}/temporary/jyqqpmfxSearch?yybs=" + $("#fwqqzZcxxSearch").val()
						+ "&fwbs=" + $("#fwbsSearch").val()
						+ "&ffbs=" + $('#ffbsSearch').combobox('getValue')
						+ "&startTime=" + $("#startTime").val()
						+ "&endTime=" + $("#endTime").val(),
				dataType : "json",
				async : false,
				success : function(data) {
					option.xAxis.data = data[0];
					option.series[0].data = data[1];
					myChart.setOption(option);
				}
			})
		}
	</script>
</body>
</html>