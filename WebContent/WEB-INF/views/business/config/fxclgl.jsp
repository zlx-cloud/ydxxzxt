<%@page import="java.util.Date,java.text.DateFormat,org.apache.shiro.SecurityUtils,net.sinodata.security.vo.ShiroUser"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="zh-cn">
<title>风险策略管理</title>
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
.combo-panel {
	height: 180px !important;
}
.msg-center {
	text-align: center;
}
</style>
<script type="text/javascript">
	// 查询
	function searchConfig() {
		$('#dg').datagrid('load', {
			yybs : $("#yybsIndex").val(),
			yymc : $("#yymcIndex").val(),
			fwbs : $("#fwbsIndex").val(),
			fwmc : $("#fwmcIndex").val(),
			ffbs : $("#ffbsIndex").val(),
			ffmc : $("#ffmcIndex").val(),
			riskType : $('#riskTypeIndex').combobox("getValue")
		});
	}

	var url;
	// 新增页面
	function openAddView() {
		var savehtml = "";
		$("#addUpdate-buttons").html('');
		savehtml += " <a href='javascript:saveUpdateConfig()' class='easyui-linkbutton' iconCls='icon-ok'>保存</a> ";
		savehtml += " <a href='javascript:closeAddUpdateView()' class='easyui-linkbutton' iconCls='icon-cancel'>关闭</a> ";
		$("#addUpdate-buttons").append(savehtml);
		$.parser.parse();

		$("#addUpdate").dialog("open").dialog("setTitle", "添加风险策略管理");
		url = "${ctx}/configRiskStrategy/add?flag=1";
	}

	// 机构-应用-服务-方法 树
	function openFwffDialog() {
		$("#fwffTree").dialog("open").dialog("setTitle", "方法信息");
		$("#tree2").tree(
				{
					lines : true,
					checkbox : false,
					animate : true,
					url : '${ctx}/serveManage/tree?flag=fwzcffqqfh',
					onLoadSuccess : function() {
						$("#tree2").tree('expandAll');
					},
					onClick : function(node) {
						var father = $(this).tree("getParent", node.target);
						if (father == null) {
							$.messager.alert('系统提示', '请选择服务或方法！');
						} else {
							var twofather = $(this).tree("getParent",
									father.target);
							if (twofather == null) {
								$.messager.alert('系统提示', '请选择服务或方法！');
							} else {
								var threefather = $(this).tree("getParent",
										twofather.target);
								if (threefather == null) {
									$("#ffms").textbox("setValue", node.text);
									$("#ffbs").val("");
									$("#fwbs").val(node.id);
									$("#fwffTree").dialog("close");
								} else {
									var fourfather = $(this).tree("getParent",
											threefather.target);
									if (fourfather == null) {
										$("#ffms").textbox("setValue",
												node.text);
										$("#ffbs").val(node.id);
										$("#fwbs").val(father.id);
										$("#fwffTree").dialog("close");
									} else {
										$.messager.alert('系统提示', '请选择服务或方法！');
									}
								}
							}
						}
					}
				})
	}

	// 修改页面
	function openUpdateView() {
		var savehtml = "";
		$("#addUpdate-buttons").html('');
		savehtml += " <a href='javascript:saveUpdateConfig()' class='easyui-linkbutton' iconCls='icon-ok'>保存</a> ";
		savehtml += " <a href='javascript:closeAddUpdateView()' class='easyui-linkbutton' iconCls='icon-cancel'>关闭</a> ";
		$("#addUpdate-buttons").append(savehtml);
		$.parser.parse();

		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length != 1) {
			$.messager.alert('系统提示', '请选择一条要编辑的数据！');
			return;
		}
		var row = selectedRows[0];
		$("#addUpdate").dialog("open").dialog("setTitle", "修改风险策略管理");
		$("#addUpdatefm").form("load", row);
		if (row.ffms == '' || row.ffms == null) {
			$("#ffms").textbox("setValue", row.fwmc);
		}

		url = "${ctx}/configRiskStrategy/add";
	}

	// 保存
	function saveUpdateConfig() {
		var riskType = $('#riskTypeIndex').combobox("getValue");
		if(riskType == 'FAIL'){
			var f0 = $("#f0").val();
			var f1 = $("#f1").val();
			var f2 = $("#f2").val();
			if(f0 == null || f0 == '' || f1 == null || f1 == '' || f2 == null || f2 == ''){
				$.messager.alert('系统提示','请填写失败率');
			}
		}else if(riskType == 'RT'){
			var rt0 = $("#rt0").val();
			var rt1 = $("#rt1").val();
			var rt2 = $("#rt2").val();
			if(rt0 == null || rt0 == '' || rt1 == null || rt1 == '' || rt2 == null || rt2 == ''){
				$.messager.alert('系统提示','请填写请求时间');
			}
		}
		
		
		$("#addUpdatefm").form(
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
							closeAddUpdateView();
							$("#dg").datagrid("reload");
						}
					}
				})
	}

	// 关闭新增/修改页面
	function closeAddUpdateView() {
		$("#addUpdate").dialog("close");
		$("#addUpdatefm").form('clear');
		$("#addUpdatefm").form('reset');
	}

	// 删除
	function deleteConfig() {
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length == 0) {
			$.messager.alert('系统提示', '请选择要删除的数据！');
			return;
		}

		var id = selectedRows[0].id;
		$.messager.confirm("系统提示", "您确认要删除这条数据吗？", function(r) {
			if (r) {
				$.post("${ctx}/configRiskStrategy/delete", {
					id : id
				}, function(result) {
					if (result.success) {
						$.messager.alert('系统提示', "您已成功删除此条数据！");
						$("#dg").datagrid("reload");
					} else {
						$.messager.alert('系统提示', result.errorMsg);
					}
				}, "json")
			}
		})
	}

	function formatRiskType(value) {
		if (value == 'FAIL') {
			return "失败率";
		} else if (value == 'RT') {
			return "请求时间";
		} else {
			return "";
		}
	}

	function formatEnabled(value) {
		if (value == 'Y') {
			return "启用";
		} else if (value == 'N') {
			return "停用";
		} else {
			return "";
		}
	}

	function rowformat(value, row, index) {
		if (row.enabled == "Y") {
			return '<a class="editcls" onclick="updateStatus(\'' + row.id
			+ '\',\'' + 'N' + '\',\'' + row.yybs + '\',\'' + row.fwbs +'\')" href="javascript:void(0)">停用</a>';
		} else {
			return '<a class="editcls" onclick="updateStatus(\'' + row.id
			+ '\',\'' + 'Y' + '\',\'' + row.yybs + '\',\'' + row.fwbs +'\')" href="javascript:void(0)">启用</a>';
		}
	}

	function updateStatus(id, enabled, yybs, fwbs) {
		$.post("${ctx}/configRiskStrategy/updateStatus", {
			id : id,
			enabled : enabled,
			yybs : yybs,
			fwbs : fwbs
		}, function(result) {
			if (result.success) {
				$.messager.alert('系统提示', "操作成功！");
				$("#dg").datagrid("reload");

			} else {
				$.messager.alert('系统提示', '操作失败！');
			}
		}, "json");
	}

	function formatHl(value, row, index) {
		if (row.riskType == "FAIL") {
			return '失败率在' + row.f0 + '%以下';
		} else if (row.riskType == "RT") {
			return '90%请求在' + row.rt0 + '毫秒以下';
		} else {
			return '';
		}
	}
	
	function formatGj(value, row, index) {
		if (row.riskType == "FAIL") {
			return '失败率在' + row.f1 + '%以下';
		} else if (row.riskType == "RT") {
			return '90%请求在' + row.rt1 + '毫秒以下';
		} else {
			return '';
		}
	}
	
	function formatZd(value, row, index) {
		if (row.riskType == "FAIL") {
			return '失败率在' + row.f2 + '%以下';
		} else if (row.riskType == "RT") {
			return '90%请求在' + row.rt2 + '毫秒以下';
		} else {
			return '';
		}
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<table id="dg" class="easyui-datagrid" fitColumns="true" singleSelect="true"
			pagination="true" rownumbers="true"url="${ctx}/configRiskStrategy/list"
			fit="true" toolbar="#tb">
			<thead>
				<tr>
					<th field="id" hidden="true">ID</th>
					<th field="yybs" align="center">应用标识</th>
					<th field="yymc" align="center">应用名称</th>
					<th field="fwbs" align="center">服务标识</th>
					<th field="fwmc" align="center">服务名称</th>
					<th field="ffbs" align="center">方法标识</th>
					<th field="ffmc" align="center">方法名称</th>
					<th field="ffms" align="center">方法描述</th>
					<th field="executeStartTime" align="center">执行开始时间</th>
					<th field="executeEndTime" align="center">执行结束时间</th>
					<th field="riskType" align="center" formatter="formatRiskType">风险类型</th>
					<th field="hl" align="center" formatter="formatHl">忽略</th>
					<th field="gj" align="center" formatter="formatGj">告警</th>
					<th field="zd" align="center" formatter="formatZd">阻断</th>
					<th field="enabled" align="center" formatter="formatEnabled">状态</th>
					<th field="createTime" align="center">创建时间</th>
					<th field="updateTime" align="center">修改时间</th>
					<th field="x" align="center" formatter="rowformat">操作</th>
				</tr>
			</thead>
		</table>
		<div id="tb">
			<div>
				<a href="javascript:openAddView()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
				<a href="javascript:openUpdateView()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
				<a href="javascript:deleteConfig()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			</div>
			<div>
				&nbsp; 应用标识：&nbsp;
				<input type="text" class="easyui-textbox" name="yybsIndex" id="yybsIndex" size="20" />
				&nbsp; 应用名称：&nbsp;
				<input type="text" class="easyui-textbox" name="yymcIndex" id="yymcIndex" size="20" />
				&nbsp; 服务标识：&nbsp;
				<input type="text" class="easyui-textbox" name="fwbsIndex" id="fwbsIndex" size="20" />
				&nbsp; 服务名称：&nbsp;
				<input type="text" class="easyui-textbox" name="fwmcIndex" id="fwmcIndex" size="20" /></br>
				&nbsp; 方法标识：&nbsp;
				<input type="text" class="easyui-textbox" name="ffbsIndex" id="ffbsIndex" size="20" />
				&nbsp; 方法名称：&nbsp;
				<input type="text" class="easyui-textbox" name="ffmcIndex" id="ffmcIndex" size="20" />
				&nbsp; 风险类型：&nbsp;
				<select class="easyui-combobox" name="riskTypeIndex" id="riskTypeIndex"
					labelPosition="top" size="20" editable=false panelHeight='auto'>
					<option value="">请选择...</option>
					<option value="FAIL">失败率</option>
					<option value="RT">请求时间</option>
				</select>
				<a href="javascript:searchConfig()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
			</div>
		</div>

		<div id="addUpdate" class="easyui-dialog" style="width: 620px; height: 260px; padding: 10px 20px"
			closable="false" closed="true" buttons="#addUpdate-buttons">
			<form id="addUpdatefm" method="post">
				<table cellspacing="5px;">
					<input id="id" name="id" type="hidden">
					<input id="enabled" name="enabled" type="hidden">
					<tr>
						<td>服务方法：</td>
						<td><input type="hidden" id="fwbs" name="fwbs" />
							<input type="hidden" id="ffbs" name="ffbs" />
							<input class="easyui-textbox" id="ffms" name="ffms"
							data-options="buttonText:'选择',onClickButton:openFwffDialog">
						</td>
						<td>执行开始时间：</td>
						<td>
							<input id="executeStartTime" name="executeStartTime" class="easyui-datebox" />
						</td>
					</tr>
					<tr>
						<td>执行结束时间：</td>
						<td>
							<input id="executeEndTime" name="executeEndTime" class="easyui-datebox" />
						</td>
						<td>风险类型：</td>
						<td>
							<select class="easyui-combobox" name="riskType" id="riskType" style="width: 100%;">
								<option value="FAIL">失败率</option>
								<option value="RT">请求时间</option>
							</select>
						</td>
					</tr>
					<tr id="f0Tr">
						<td colspan="4" class="msg-center">
							1、忽略：失败率在&nbsp;<input id="f0" name="f0" class="easyui-textbox" />&nbsp;%以下
						</td>
					</tr>
					<tr id="f1Tr">
						<td colspan="4" class="msg-center">
							2、告警：失败率在&nbsp;<input id="f1" name="f1" class="easyui-textbox" />&nbsp;%以下
						</td>
					</tr>
					<tr id="f2Tr">
						<td colspan="4" class="msg-center">
							3、阻断：失败率在&nbsp;<input id="f2" name="f2" class="easyui-textbox" />&nbsp;%以下
						</td>
					</tr>
					<tr id="rt0Tr">
						<td colspan="4" class="msg-center">
							1、忽略：90%请求在&nbsp;<input id="rt0" name="rt0" class="easyui-textbox" />&nbsp;毫秒以下
						</td>
					</tr>
					<tr id="rt1Tr">
						<td colspan="4" class="msg-center">
							2、告警：90%请求在&nbsp;<input id="rt1" name="rt1" class="easyui-textbox" />&nbsp;毫秒以下
						</td>
					</tr>
					<tr id="rt2Tr">
						<td colspan="4" class="msg-center">
							3、阻断：90%请求在&nbsp;<input id="rt2" name="rt2" class="easyui-textbox" />&nbsp;毫秒以下
						</td>
					</tr>
				</table>
			</form>
		</div>

		<div id="addUpdate-buttons"></div>

		<div id="fwffTree" class="easyui-dialog"
			style="width: 300px; height: 450px; padding: 10px 20px" closed="true"
			buttons="fwffTree-buttons" closable="true">
			<ul id="tree2" class="easyui-tree"></ul>
		</div>
	</div>
	<script>
		$(function() {
			$('.easyui-datebox').datebox();
			
			$('#riskType').combobox({
				onSelect: function(newSelect,oldSelect){
					if('FAIL' == newSelect.value){
						$("tr[id=f0Tr]").show();
						$("tr[id=f1Tr]").show();
						$("tr[id=f2Tr]").show();
						$("tr[id=rt0Tr]").hide();
						$("tr[id=rt1Tr]").hide();
						$("tr[id=rt2Tr]").hide();
					} else if('RT' == newSelect.value){
						$("tr[id=f0Tr]").hide();
						$("tr[id=f1Tr]").hide();
						$("tr[id=f2Tr]").hide();
						$("tr[id=rt0Tr]").show();
						$("tr[id=rt1Tr]").show();
						$("tr[id=rt2Tr]").show();
					}
				}
			})
		})
	</script>
</body>
</html>