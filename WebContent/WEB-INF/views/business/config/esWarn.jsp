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
	function searchEsWarn() {
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
		
		$('#dg').datagrid('options').url = '${ctx}/esWarn/list';
		$('#dg').datagrid('load', {
			fwqqzZcxxSearch : $("#fwqqzZcxxSearch").val(),
			fwbsSearch : $("#fwbsSearch").val(),
			ffbsSearch : $('#ffbsSearch').combobox('getValue'),
			startTime : $("#startTime").val(),
			endTime : $("#endTime").val()
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
</script>
<title>请求响应日志</title>
</head>
<body style="margin: 1px;">
	<table id="dg" class="easyui-datagrid" fitColumns="true" pagination="true" rownumbers="true"
		url="${ctx}/esWarn/list?startTime=${time}&endTime=${time}" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="uuid" width="65" align="center" formatter="detail">报文标识</th>
				<th field="notes" width="30" align="center">消息</th>
				<th field="FWQQZ_ZCXX" width="45" align="center">应用标识</th>
				<th field="FWBS" width="45" align="center">服务标识</th>
				<th field="FFBS" width="20" align="center">方法标识</th>
				<th field="requestTime" width="40" align="center">请求时间</th>
				<th field="responseTime" width="40" align="center">响应时间</th>
				<th field="statusStr" width="20" align="center">响应状态</th>
			</tr>
		</thead>
	</table>
	<div id="tb">
		<div>
			&nbsp;应用名称：&nbsp; 
			<select name="fwqqzZcxxSearch" id="fwqqzZcxxSearch" class="easyui-combobox">
				<option value="">请选择</option>
				<c:forEach items="${fwcyf}" var="zcxx" varStatus="index">
					<option value="${zcxx.fwcyfYyxtbh}">${zcxx.fwcyfYyxtmc}</option>
				</c:forEach>
			</select> 
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
			&nbsp; 请求开始日期：&nbsp; 
			<input type="text" id="startTime" class="easyui-datebox" size="20" value="${time}" />
			&nbsp; 请求结束日期：&nbsp; 
			<input type="text" id="endTime" class="easyui-datebox" size="20" value="${time}" />
			<a href="javascript:searchEsWarn()" class="easyui-linkbutton" 
				iconCls="icon-search" plain="true">查询</a>
		</div>
	</div>

	<div id="dlg" class="easyui-dialog" style="width: 800px; height: 470px; padding: 10px 20px"
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
	
	<div id="dlg-buttons">
		<a href="javascript:closeRoleSaveDialog()" class="easyui-linkbutton"
			iconCls="icon-cancel">关闭</a>
	</div>
	
</body>
</html>