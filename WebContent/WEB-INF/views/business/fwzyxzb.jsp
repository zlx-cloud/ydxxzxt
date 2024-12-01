<%@page
	import="java.util.Date,java.text.DateFormat,org.apache.shiro.SecurityUtils,net.sinodata.security.vo.ShiroUser"%>
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
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var url;
	function searchVarSystem() {
		$('#dg').datagrid('options').url = '${ctx}/fwzyxzb/list';
		$('#dg').datagrid('load', {
			yymc : $("#yymc_search").val(),
			fwbs : $("#xzSearch").val(),
			fwmc : $("#fwmc_search").val()
		});
	}

	function closeRoleSaveDialog() {
		$("#dlg").dialog("close");
		$("#fm").form('clear');
	}

	function closeAuthDialog() {
		$("#dlg2").dialog("close");
	}

	
	function openFwzyxzbModifyDialog() {
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length != 1) {
			$.messager.alert('系统提示', '请选择一条要编辑的数据！');
			return;
		}
		var row = selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle", "修改服务信息");
		$("#fm").form("load", row);
		
		$("#fwbs").attr("readonly", "readonly");
		url = "${ctx}/fwzyxzb/add";
	}
	function openFwzyxzbAddDialog() {
		$("#fwbs").removeAttr("readonly");
		$("#dlg").dialog("open").dialog("setTitle", "添加服务信息");
		$('#fwztdm').combobox("setValue", "1")
		url = "${ctx}/fwzyxzb/add?flag=1";
	}
	
	function saveUpdateFwzcxz() {
		$("#fm").form(
				"submit",
				{
					url : url,
					onSubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
						var result = eval('(' + result + ')');
						if (result.errorMsg) {
							$.messager.alert('系统提示', "<font color=red>"
									+ result.errorMsg + "</font>");
							return;
						} else {
							$.messager.alert('系统提示', '保存成功');
							closeRoleSaveDialog();
							$("#dg").datagrid("reload");
						}
					}
				});
	}
	
	
	function deleteFwzyxzb() {
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length == 0) {
			$.messager.alert('系统提示', '请选择要删除的数据！');
			return;
		}
		var fwbs = selectedRows[0].fwbs;

		$.messager.confirm("系统提示", "您确认要删除这条数据吗？", function(r) {
			if (r) {
				$.post("${ctx}/fwzyxzb/delete", {
					fwbs : fwbs
				}, function(result) {
					if (result.success) {
						$.messager.alert('系统提示', "您已成功删除此条数据！");
						$("#dg").datagrid("reload");
					} else {
						$.messager.alert('系统提示', result.errorMsg);
					}
				}, "json");
			}
		});
	}
	

	function staformat(value, row, index) {
		return row.fwztdm == 1 ? "启用" : "禁用";
	}
	
	function yjtb() {
		$.ajax({
			type : "POST",
			dataType : "text",
			url : "${ctx}/fwzyxzb/yjtb",
			success : function(data) {
				$("#dg").datagrid("reload");
				$.messager.alert('系统提示', '一键同步成功！');
			}
		});
	}
</script>
<title>请求响应日志</title>
</head>
<body style="margin: 1px;">
	<table id="dg" class="easyui-datagrid" singleSelect="true"  fitColumns="true" pagination="true" rownumbers="true" url="${ctx}/fwzyxzb/list"
		fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="yymc" width="20" align="center">应用名称</th>
				<th field="fwbs" width="20" align="center">服务标识</th>
				<th field="fwmc" width="20" align="center">服务名称</th>
				<th field="fwxzzt" width="10" align="center" formatter="staformat">服务限制状态</th>
<!-- 				<th field="fwzt" width="50" align="center">服务状态</th> -->
				<th field="dayCount" width="10" align="center">按天使用上限</th>
				<th field="hourCount" width="10" align="center">按小时使用上限</th>
				<!--     		<th field="minuteCount" width="20" align="center">按分钟使用上限</th> -->
			</tr>
		</thead>
	</table>
	<div id="tb">
	
	<div>
			<a href="javascript:yjtb()" class="easyui-linkbutton" iconCls="icon-add" plain="true">一键同步</a> 
<!-- 				<a href="javascript:openFwzyxzbAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">一键同步</a>  -->
			<a href="javascript:openFwzyxzbModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a> 
			<a href="javascript:deleteFwzyxzb()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
	</div>
			
		<div>
			&nbsp;应用名称：&nbsp;<input type="text" name="yymc_search" id="yymc_search" size="20"/>
			&nbsp;服务标识：&nbsp;<input type="text" name="xzSearch" id="xzSearch" size="20"/>
			&nbsp;服务名称：&nbsp;<input type="text" name="fwmc_search" id="fwmc_search" size="20"/>
			<a href="javascript:searchVarSystem()" class="easyui-linkbutton"
				iconCls="icon-search" plain="true">查询</a>
		</div>
	</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 500px; height: 250px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="5px;">
				<tr>
					<td>服务标识：</td>
					<td><input type="text" id="fwbs" name="fwbs" class="easyui-validatebox" required="true" /></td>
				</tr>
				<tr>
					<td>服务限制状态：</td>
					<td><select class="easyui-combobox" name="fwxzzt" id="fwxzzt" style="width: 100%;">
							<option value="0">不启用</option>
							<option value="1">启用</option>
					</select></td>
				</tr>
				<tr>
				<td>按天使用上限：</td>
					<td><input type="text" id="dayCount" name="dayCount" class="easyui-validatebox" required="true" /></td>
				</tr>
				<tr>
<!-- 					<td>服务状态：</td> -->
<!-- 					<td><select class="easyui-combobox" name="fwzt" id="fwzt" style="width: 100%;"> -->
<!-- 							<option value="0">启用</option> -->
<!-- 							<option value="1">禁用</option> -->
<!-- 					</select></td> -->
					
						<td>按小时使用上限：</td>
					<td><input type="text" id="hourCount" name="hourCount" class="easyui-validatebox" required="true" /></td>
				</tr>
<!-- 				<tr> -->
				
<!-- 				</tr> -->
				
			</table>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href='javascript:saveUpdateFwzcxz()' class='easyui-linkbutton' iconCls='icon-ok'>保存</a> 
		<a href="javascript:closeRoleSaveDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>

	<div id="dlg2" class="easyui-dialog"
		style="width: 300px; height: 450px; padding: 10px 20px" closed="true"
		buttons="#dlg2-buttons">
		<ul id="tree" class="easyui-tree"></ul>
	</div>

</body>
</html>