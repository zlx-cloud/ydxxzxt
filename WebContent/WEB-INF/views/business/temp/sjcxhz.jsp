<%@page import="java.util.Date,java.text.DateFormat,org.apache.shiro.SecurityUtils,net.sinodata.security.vo.ShiroUser"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="zh-cn">
<title>风险事件查询</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<style>

</style>
<script type="text/javascript">
	// 查询
	function searchConfig() {
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
		
		$('#dg').datagrid('options').url='${ctx}/temporary/sjcxhz';
		$('#dg').datagrid('load', {
			yymc : $("#yymcIndex").val(),
			fwmc : $("#fwmcIndex").val(),
			ffmc : $("#ffmcIndex").val(),
			warnType : $('#warnTypeIndex').combobox("getValue"),
			startTime : $("#startTime").val(),
			endTime : $("#endTime").val()
		});
	}
	
	function showWarnValue(value, row, index) {
		return '<a class="editcls" onclick="showList(\'' + row.FWBS + '\',\'' + row.FFBS
				+ '\')" href="javascript:void(0)">' + value + '</a>';
	}
	
	function showList(FWBS,FFBS) {
		$("#dlg").dialog("open").dialog("setTitle", "日志信息");
		var options = $('#dglog').datagrid('options');
		options.url = "${ctx}/temporary/warnEsLog";
		options.queryParams = { FWBS:FWBS,FFBS:FFBS,startTime:$("#startTime").val(),endTime:$("#endTime").val()};
		$('#dglog').datagrid(options);
	}
	
	function closeRoleSaveDialog() {
		$("#dlg").dialog("close");
	}
	
	function detail(value, row, index) {
		return '<a class="editcls" onclick="look(\'' + index
				+ '\')" href="javascript:void(0)">' + value + '</a>';
	}
	
	function look(varId) {
		document.getElementById('fm').reset();
		var row = $("#dglog").datagrid("getRows")[varId];
		$("#dlgdetail").dialog("open").dialog("setTitle", "日志信息");
		$("#fm").form("load", row);
	}
	
	function closeDetailDialog() {
		$("#dlgdetail").dialog("close");
		$("#fm").form('clear');
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<table id="dg" class="easyui-datagrid" fitColumns="true" singleSelect="true"
			pagination="true" rownumbers="true" url="${ctx}/temporary/sjcxhz?startTime=${time}&endTime=${time}" fit="true" toolbar="#tb">
			<thead>
				<tr>
					<th field="YYBS" align="center">应用标识</th>
					<th field="YYMC" align="center">应用名称</th>
					<th field="FWBS" align="center">服务标识</th>
					<th field="FWMC" align="center">服务名称</th>
					<th field="FFBS" align="center">方法标识</th>
					<th field="FFMC" align="center">方法名称</th>
					<th field="FFMS" align="center">方法描述</th>
					<th field="WARN_NAME" align="center">风险类型</th>
					<th field="WARN_VALUE" align="center" formatter="showWarnValue">发生次数</th>
				</tr>
			</thead>
		</table>
		
		<div id="tb">
			<div>
				&nbsp; 应用名称：&nbsp;<input type="text" class="easyui-textbox" name="yymcIndex" id="yymcIndex" size="20" />
				&nbsp; 服务名称：&nbsp;<input type="text" class="easyui-textbox" name="fwmcIndex" id="fwmcIndex" size="20" />
				&nbsp; 方法名称：&nbsp;<input type="text" class="easyui-textbox" name="ffmcIndex" id="ffmcIndex" size="20" />
				</br>
				&nbsp; 请求开始日期：&nbsp; 
				<input type="text" id="startTime" class="easyui-datebox" size="20" value="${time}" />
				&nbsp; 请求结束日期：&nbsp; 
				<input type="text" id="endTime" class="easyui-datebox" size="20" value="${time}" />
				&nbsp; 控制类型：&nbsp;
				<select class="easyui-combobox" name="warnTypeIndex" id="warnTypeIndex"
					labelPosition="top" size="20" editable=false panelHeight='auto'>
					<option value="">请选择...</option>
					<option value="RISK-RT">请求时间变长</option>
					<option value="RISK-ERR">失败率</option>
					<option value="RISK-MIS">缺少包</option>
				</select>
				<a href="javascript:searchConfig()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
			</div>
		</div>

		<div id="dlg" class="easyui-dialog" style="width: 910px; height: 470px; padding: 10px 20px" closed="true"
			buttons="#dlg-buttons">
			<table id="dglog" class="easyui-datagrid" fitColumns="true" pagination="true"
				rownumbers="true" url="">
				<thead>
					<tr>
						<th field="uuid" width="60" align="center" formatter="detail">报文标识</th>
						<th field="notes" width="50" align="center">消息</th>
						<th field="requestTime" width="45" align="center">请求时间</th>
						<th field="responseTime" width="45" align="center">响应时间</th>
						<th field="statusStr" width="20" align="center">响应状态</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="dlg-buttons">
			<a href="javascript:closeRoleSaveDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
		</div>

		<div id="dlgdetail" class="easyui-dialog"
			style="width: 800px; height: 470px; padding: 10px 20px" closed="true"
			buttons="#dlgdetail-buttons">
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
						<td><input type="text" id="beforeWallTime"
							name="beforeWallTime" class="easyui-validatebox es-detail-width"
							readonly /></td>
					</tr>
					<tr>
						<td>离开防火墙时间：</td>
						<td><input type="text" id="finishWallTime"
							name="finishWallTime" class="easyui-validatebox es-detail-width"
							readonly /></td>
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
						<td colspan="3"><textarea rows="7" cols="80"
								name="requestDataJson" id="requestDataJson" readonly></textarea>
						</td>
					</tr>
					<tr>
						<td valign="top">响应内容：</td>
						<td colspan="3"><textarea rows="4" cols="80"
								name="responseData" id="responseData" readonly></textarea></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="dlgdetail-buttons">
			<a href="javascript:closeDetailDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
		</div>
	</div>
</body>
</html>