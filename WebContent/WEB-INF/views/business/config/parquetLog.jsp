<%@page import="java.util.Date,java.text.DateFormat,org.apache.shiro.SecurityUtils,net.sinodata.security.vo.ShiroUser"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<head lang="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
<style>
.combo-panel.panel-body.panel-body-noheader {
	height: 300px;
}
.es-detail-width {
	width: 250px;
}
</style>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/echarts.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	function searchParquetLog() {
		var startTimeVal = $("#startTimeSearch").val();
		var endTimeVal =$("#endTimeSearch").val();
		if(startTimeVal == '' || startTimeVal == null){
			$.messager.alert('系统提示','请选择开始日期');
			return;
		}
		if(endTimeVal == '' || endTimeVal == null){
			$.messager.alert('系统提示','请选择结束日期');
			return;
		}
		$('#dg').datagrid('options').url = '${ctx}/parquetLog/list';
		$('#dg').datagrid('load', {
			fwbsSearch : $("#fwbsSearch").val(),
			ffbsSearch : $('#ffbsSearch').combobox('getValue'),
			startTimeSearch : startTimeVal,
			endTimeSearch : endTimeVal,
			statusSearch : $("#statusSearch").val()
		});
	}

	function closeRoleSaveDialog() {
		$("#dlg").dialog("close");
		$("#fm").form('clear');
	}

	function detail(value, row, index) {
		return '<a class="editcls" onclick="look(\'' + index
				+ '\')" href="javascript:void(0)">' + value + '</a>';
	}

	function look(varId) {
		document.getElementById('fm').reset();
		var row = $("#dg").datagrid("getRows")[varId];
		$("#dlg").dialog("open").dialog("setTitle", "日志信息");
		$("#fm").form("load", row);
	}

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
	
	function timeConsuming(value, row, index) {
		if(row.status == '00'){
			return '<a class="editcls" onclick="hsBar(\'' + index + '\')" href="javascript:void(0)">查看</a>';
		} else {
			return '';
		}
	}
	
	function hsBar(varId) {
		var row = $("#dg").datagrid("getRows")[varId];
		$("#dlgBar").dialog("open").dialog("setTitle", "耗时情况(毫秒)");

		var diff3Time = row.diff3Time === undefined ? '' : row.diff3Time;
		var diffWallTime = row.diffWallTime === undefined ? ''
				: row.diffWallTime;
		var diffTime = row.diffTime === undefined ? ''
				: row.diffTime;

		var time1, time2, time3, time4, time5;
		$.ajax({
			type : "post",
			url : "${ctx}/parquetLog/jshs?diff3Time=" + diff3Time
					+ "&diffWallTime=" + diffWallTime + "&diffTime="
					+ diffTime,
			dataType : "json",
			async : false,
			success : function(data) {
				time1 = data.time1;
				time2 = data.time2;
				time3 = data.time3;
				time4 = data.time4;
				time5 = data.time5;
			}
		})

		var myChart = echarts.init(document.getElementById('hsBarDiv'));

		option = {
			grid : {
				top : '6%',
				left : '3%',
				right : '4%',
				bottom : '2%',
				containLabel : true
			},
			yAxis : {
				type : 'category',
				splitLine : {
					show : false
				},
				data : [ '总线响应结果','响应结果自边界返回',
					'服务请求->服务响应','请求通过边界','总线处理及请求参数采集']
			},
			xAxis : {
				type : 'value'
			},
			series : [
					{
						name : 'Placeholder',
						type : 'bar',
						stack : 'Total',
						itemStyle : {
							normal : {
								borderColor : 'rgba(0,0,0,0)',
								color : 'rgba(0,0,0,0)'
							}
						},
						emphasis : {
							itemStyle : {
								normal : {
									borderColor : 'rgba(0,0,0,0)'
								}
							}
						},
						data : [ time1 + time2 + time3 + time4,time1 + time2 + time3,
								time1 + time2,time1,0]
					}, {
						name : '时长',
						type : 'bar',
						stack : 'Total',
						label : {
							normal : {
								show : true,
								position : 'right'
							}
						},
						data : [ time5, time4, time3, time2, time1 ]
					} ]
		};
		myChart.setOption(option);
	}

	function closeBar() {
		$("#dlgBar").dialog("close");
	}
</script>
<title>请求响应日志</title>
</head>
<body style="margin: 1px;">
	<table id="dg" class="easyui-datagrid" fitColumns="true" pagination="true" rownumbers="true" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="uuid" align="center" formatter="detail">报文标识</th>
				<th field="notes" align="center">消息</th>
				<th field="FWQQZ_ZCXX" align="center">应用标识</th>
				<th field="FWBS" align="center">服务标识</th>
				<th field="FFBS" align="center">方法标识</th>
				<th field="requestTime" align="center">请求时间</th>
				<th field="responseTime" align="center">响应时间</th>
				<th field="statusStr" align="center">响应状态</th>
				<th field="FWQQSB_BH" align="center">请求设备编号</th>
				<th field="XXCZRY_XM" align="center">操作人员姓名</th>
				<th field="XXCZRY_GMSFHM" align="center">操作人员证件号</th>
				<th field="x" align="center" formatter="timeConsuming">耗时情况</th>
			</tr>
		</thead>
	</table>
	<div id="tb">
		<div>
			&nbsp;服务名称：&nbsp;
			<select name="fwbsSearch" id="fwbsSearch" class="easyui-combobox">
				<option value="">请选择</option>
				<c:forEach items="${fwzy}" var="zyxx" varStatus="index">
					<option value="${zyxx.fwbs}">${zyxx.fwmc}</option>
				</c:forEach>
			</select>
			&nbsp;方法名称：&nbsp;
			<select name="ffbsSearch" id="ffbsSearch"
				class="easyui-combobox" style="width: 230px;">
				<option value="">请先选择服务名称</option>
			</select>
			</br>
			&nbsp;请求时间：&nbsp;
			<input type="text" name="startTimeSearch" id="startTimeSearch"
				class="easyui-datetimebox" size="20" value="${startTime}" />
			&nbsp;至&nbsp;
			<input type="text" name="endTimeSearch" id="endTimeSearch"
				class="easyui-datetimebox" size="20" value="${endTime}" />
			&nbsp;响应状态&nbsp;
			<select name="statusSearch" id="statusSearch"
				class="easyui-combobox" style="width: 100px;">
				<option value="">请选择</option>
				<option value="00">正常</option>
				<option value="01">异常</option>
				<option value="02">校验错误</option>
			</select>
			<a href="javascript:searchParquetLog()" class="easyui-linkbutton" 
				iconCls="icon-search" plain="true">查询</a>
			<a href="${ctx}/parquetLog/parquetLogExport" class="easyui-linkbutton"
				iconCls="icon-download-chart" plain="true">导出</a>
		</div>
	</div>

	<div id="dlg" class="easyui-dialog" style="width: 800px; height: 490px; padding: 10px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="5px;">
				<tr>
					<td>报文标识：</td>
					<td><input type="text" id="uuid" name="uuid"
						class="easyui-validatebox es-detail-width" readonly /></td>
					<td>消息：</td>
					<td><input type="text" id="notes" name="notes"
						class="easyui-validatebox es-detail-width" readonly /></td>
				</tr>
				<tr>
					<td>接收请求时间：</td>
					<td><input type="text" id="requestTime" name="requestTime"
						class="easyui-validatebox es-detail-width" readonly /></td>
					<td>响应请求时间：</td>
					<td><input type="text" id="responseTime" name="responseTime"
						class="easyui-validatebox es-detail-width" readonly /></td>
				</tr>
				<tr>
					<td>请求时间差：</td>
					<td><input type="text" id="diffTime" name="diffTime"
						class="easyui-validatebox es-detail-width" readonly /></td>
					<td>进入防火墙时间：</td>
					<td><input type="text" id="beforeWallTime" name="beforeWallTime"
						class="easyui-validatebox es-detail-width" readonly /></td>
				</tr>
				<tr>
					<td>离开防火墙时间：</td>
					<td><input type="text" id="finishWallTime" name="finishWallTime"
						class="easyui-validatebox es-detail-width" readonly /></td>
					<td>服务请求时间：</td>
					<td><input type="text" id="begin3Time" name="begin3Time"
						class="easyui-validatebox es-detail-width" readonly /></td>
				</tr>
				<tr>
					<td>服务响应时间：</td>
					<td><input type="text" id="finish3Time" name="finish3Time"
						class="easyui-validatebox es-detail-width" readonly /></td>
					<td>响应时间差：</td>
					<td><input type="text" id="diff3Time" name="diff3Time"
						class="easyui-validatebox es-detail-width" readonly /></td>
				</tr>
				<tr>
					<td>响应状态：</td>
					<td><input type="text" id="statusStr" name="statusStr"
						class="easyui-validatebox es-detail-width" readonly /></td>
					<td>请求设备编号：</td>
					<td><input type="text" id="FWQQSB_BH" name="FWQQSB_BH" class="easyui-validatebox es-detail-width" readonly /></td>
				</tr>
				<tr>
					<td>操作人员姓名：</td>
					<td><input type="text" id="XXCZRY_XM" name="XXCZRY_XM" class="easyui-validatebox es-detail-width" readonly /></td>
					<td>操作人员证件号：</td>
					<td><input type="text" id="XXCZRY_GMSFHM" name="XXCZRY_GMSFHM" class="easyui-validatebox es-detail-width" readonly /></td>
				</tr>
				<tr>
					<td valign="top">请求内容：</td>
					<td colspan="3">
						<textarea rows="7" cols="80" name="requestDataJson" id="requestDataJson" readonly></textarea>
					</td>
				</tr>
				<tr>
					<td valign="top">响应内容：</td>
					<td colspan="3">
						<textarea rows="4" cols="80" name="responseData" id="responseData" readonly></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlgBar" class="easyui-dialog" style="width: 600px; height: 400px; padding: 10px 10px"
		closed="true" buttons="#dlgBar-buttons">
		<div id="hsBarDiv" style="width: 550px;height: 280px;"></div>
   	</div>

	<div id="dlg-buttons">
		<a href="javascript:closeRoleSaveDialog()" class="easyui-linkbutton"
			iconCls="icon-cancel">关闭</a>
	</div>
	
	<div id="dlgBar-buttons">
		<a href="javascript:closeBar()" class="easyui-linkbutton"
			iconCls="icon-cancel">关闭</a>
	</div>

</body>
</html>