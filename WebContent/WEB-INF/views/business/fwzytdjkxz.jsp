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
		$('#dg').datagrid('options').url = '${ctx}/fwzytdjkxz/list';
		$('#dg').datagrid('load', {
			fwbs : $("#xzSearch").val(),
		});
	}

	function closeRoleSaveDialog() {
		$("#dlg").dialog("close");
		$("#fm").form('clear');
	}

	function closeAuthDialog() {
		$("#dlg2").dialog("close");
	}

	
	function openTdjkModifyDialog() {
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length != 1) {
			$.messager.alert('系统提示', '请选择一条要编辑的数据！');
			return;
		}
		var row = selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle", "修改服务信息");
		$("#fm").form("load", row);
		
		$("#fwbs").attr("readonly", "readonly");
		url = "${ctx}/fwzytdjkxz/add";
	}
	function openTdjkAddDialog() {
		$("#fwbs").removeAttr("readonly");
		$("#dlg").dialog("open").dialog("setTitle", "添加服务信息");
		$('#fwztdm').combobox("setValue", "1")
		url = "${ctx}/fwzytdjkxz/add?flag=1";
	}
	
	function saveUpdateTdjk() {
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
	
	
	function deleteTdjk() {
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
		return row.fwztdm == 0 ? "启用" : "禁用";
	}
	
	function xzlxformat(value, row, index) {
		if(row.xzlx =="yy"){
			return "应用";
		}else if(row.xzlx =="jg"){
			return "机构";
		}
	}
	
	function xzlbformat(value, row, index) {
		if(row.xzlb =='1'){
			return "天";
		}else if(row.xzlb =='2'){
			return "小时";
		}else if(row.xzlb =='3'){
			return "分钟";
		}
	}
</script>
<title>请求响应日志</title>
</head>
<body style="margin: 1px;">
	<table id="dg" class="easyui-datagrid" singleSelect="true"  fitColumns="true" pagination="true" rownumbers="true" url="${ctx}/fwzytdjkxz/list"
		fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="yybs" width="50" align="center">应用标识</th>
				<th field="fwbs" width="50" align="center">服务标识</th>
				<th field="ffbs" width="50" align="center">方法标识</th>
				<th field="qybz" width="50" align="center"  formatter="staformat">启用标志</th>
				<th field="xzlb" width="50" align="center"  formatter="xzlbformat">限制类别</th>
				<th field="xzlx" width="50" align="center"  formatter="xzlxformat">限制类型</th>
				<th field="maxCount" width="50" align="center">最大限制数</th>
			</tr>
		</thead>
	</table>
	<div id="tb">
	
	<div>
			<a href="javascript:openTdjkAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> 
			<a href="javascript:openTdjkModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a> 
			<a href="javascript:deleteTdjk()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
	</div>
			
		<div>
			&nbsp;服务标识：&nbsp;<input type="text" name="xzSearch" id="xzSearch"
				size="20" onkeydown="if(event.keyCode==13) search()" />
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
					<td>启用标志：</td>
					<td><select class="easyui-combobox" name="qybz" id="qybz" style="width: 100%;">
							<option value="0">启用</option>
							<option value="1">禁用</option>
					</select></td>
				</tr>
				
				<tr>
				<td>最大限制数：</td>
					<td><input type="text" id="maxCount" name="maxCount" class="easyui-validatebox" required="true" /></td>
				</tr>
				
			</table>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href='javascript:saveUpdateTdjk()' class='easyui-linkbutton' iconCls='icon-ok'>保存</a> 
		<a href="javascript:closeRoleSaveDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>

	<div id="dlg2" class="easyui-dialog"
		style="width: 300px; height: 450px; padding: 10px 20px" closed="true"
		buttons="#dlg2-buttons">
		<ul id="tree" class="easyui-tree"></ul>
	</div>

</body>
</html>