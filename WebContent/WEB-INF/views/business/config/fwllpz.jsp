<%@page import="java.util.Date,java.text.DateFormat,org.apache.shiro.SecurityUtils,net.sinodata.security.vo.ShiroUser"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="zh-cn">
<title>访问流量配置</title>
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
	height: 100px!important;
}
</style>
<script type="text/javascript">
	// 查询
	function searchConfig() {
		$('#dg').datagrid('load', {
			consumer : $("#consumerIndex").val(),
			consumerName : $("#consumerNameIndex").val(),
			yybs : $("#yybsIndex").val(),
			yymc : $("#yymcIndex").val(),
			fwbs : $("#fwbsIndex").val(),
			fwmc : $("#fwmcIndex").val(),
			ffbs : $("#ffbsIndex").val(),
			ffmc : $("#ffmcIndex").val(),
			controlType : $('#controlTypeIndex').combobox("getValue")
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
		
		$("#addUpdate").dialog("open").dialog("setTitle", "添加访问流量配置");
		url = "${ctx}/configAccessTraffic/add?flag=1";
	}
	
	// 消费方
	function openConsumerView() {
		$("#consumerTree").dialog("open").dialog("setTitle", "消费方");
		$("#tree1").tree({
			lines : true,
			url : "${ctx}/serveManage/fwzctree",
			checkbox : false,
			animate : true,
			onLoadSuccess : function() {
				$("#tree1").tree('collapseAll');
			},
			onClick : function(node) {
				var father = $(this).tree("getParent", node.target);
				if (father == null) {
					$.messager.alert('系统提示', '请选择消费方！');
				} else {
					var father = $(this).tree("getParent", node.target);
					$("#consumer").val(node.id);
					$("#consumerName").textbox("setValue", node.text);
					$("#consumerTree").dialog("close");
				}
			}
		})
	}
	
	// 机构-应用-服务-方法 树
	function openFwffDialog() {
		$("#fwffTree").dialog("open").dialog("setTitle", "方法信息");
		$("#tree2").tree({
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
					var twofather = $(this).tree("getParent",father.target);
					if (twofather == null) {
						$.messager.alert('系统提示', '请选择服务或方法！');
					} else {
						var threefather = $(this).tree("getParent",twofather.target);
						if (threefather == null) {
							$("#ffms").textbox("setValue",node.text);
							$("#ffbs").val("");
							$("#fwbs").val(node.id);
							$("#fwffTree").dialog("close");
						} else {
							var fourfather = $(this).tree("getParent",threefather.target);
							if (fourfather == null) {
								$("#ffms").textbox("setValue",node.text);
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
		$("#addUpdate").dialog("open").dialog("setTitle", "修改访问流量配置");
		$("#addUpdatefm").form("load", row);
		if(row.ffms == '' || row.ffms == null){
			$("#ffms").textbox("setValue", row.fwmc);
		}

		url = "${ctx}/configAccessTraffic/add";
	}

	// 保存
	function saveUpdateConfig() {
		$("#addUpdatefm").form("submit",{
			url : url,
			onSubmit : function() {
				return $(this).form("validate");
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.errorMsg) {
					$.messager.alert('系统提示', "<font color=red>" + result.errorMsg + "</font>");
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
				$.post("${ctx}/configAccessTraffic/delete", {
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
	
	//格式化  
	function formatControlType(value) {
		if (value == 'ZQKZ') {
			return "周期控制";
		} else if (value == 'DCQQKZ') {
			return "单次请求控制";
		} else {
			return "";
		}
	}
	
	function formatDealStrategy(value) {
		if (value == 'BLOCK') {
			return "阻断";
		} else if (value == 'WARN') {
			return "预警";
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
		$.post("${ctx}/configAccessTraffic/updateStatus", {
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
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<table id="dg" class="easyui-datagrid" fitColumns="true" singleSelect="true"
			pagination="true" rownumbers="true" url="${ctx}/configAccessTraffic/list" fit="true" toolbar="#tb">
			<thead>
				<tr>
					<th field="id" hidden="true">ID</th>
					<th field="consumer" align="center">消费方标识</th>
					<th field="consumerName" align="center">消费方名称</th>
					<th field="yybs" align="center">应用标识</th>
					<th field="yymc" align="center">应用名称</th>
					<th field="fwbs" align="center">服务标识</th>
					<th field="fwmc" align="center">服务名称</th>
					<th field="ffbs" align="center">方法标识</th>
					<th field="ffmc" align="center">方法名称</th>
					<th field="ffms" align="center">方法描述</th>
					<th field="controlType" align="center" formatter="formatControlType">控制类型</th>
					<th field="cycle" align="center">周期（秒）</th>
					<th field="trafficThreshold" align="center">流量阈值（kb）</th>
					<th field="dealStrategy" align="center" formatter="formatDealStrategy">处置策略</th>
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
				&nbsp; 消费方标识：&nbsp;<input type="text" class="easyui-textbox" name="consumerIndex" id="consumerIndex" size="20" />
				&nbsp; 应用标识：&nbsp;<input type="text" class="easyui-textbox" name="yybsIndex" id="yybsIndex" size="20" />
				&nbsp; 服务标识：&nbsp;<input type="text" class="easyui-textbox" name="fwbsIndex" id="fwbsIndex" size="20" />
				&nbsp; 方法标识：&nbsp;<input type="text" class="easyui-textbox" name="ffbsIndex" id="ffbsIndex" size="20" /></br>
				&nbsp; 消费方名称：&nbsp;<input type="text" class="easyui-textbox" name="consumerNameIndex" id="consumerNameIndex" size="20" />
				&nbsp; 应用名称：&nbsp;<input type="text" class="easyui-textbox" name="yymcIndex" id="yymcIndex" size="20" />
				&nbsp; 服务名称：&nbsp;<input type="text" class="easyui-textbox" name="fwmcIndex" id="fwmcIndex" size="20" />
				&nbsp; 方法名称：&nbsp;<input type="text" class="easyui-textbox" name="ffmcIndex" id="ffmcIndex" size="20" />
				&nbsp; 控制类型：&nbsp;
				<select class="easyui-combobox" name="controlTypeIndex" id="controlTypeIndex"
					labelPosition="top" size="20" editable=false panelHeight='auto'>
					<option value="">请选择...</option>
					<option value="ZQKZ">周期控制</option>
					<option value="DCQQKZ">单次请求控制</option>
				</select>
				<a href="javascript:searchConfig()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
			</div>
		</div>

		<div id="addUpdate" class="easyui-dialog" style="width: 620px; height: 220px;
			padding: 10px 20px" closable="false" closed="true" buttons="#addUpdate-buttons">
			<form id="addUpdatefm" method="post">
				<table cellspacing="5px;">
					<input id="id" name="id" type="hidden">
					<input id="enabled" name="enabled" type="hidden">
					<tr>	
						<td>消费方：</td>
						<td>
							<input id="consumer" name="consumer" type="hidden">
							<input class="easyui-textbox" id="consumerName" name="consumerName" 
								data-options="buttonText:'选择',onClickButton:openConsumerView">
						</td>
						<td>服务方法：</td>
						<td>
							<input type="hidden" id="fwbs" name="fwbs" />
							<input type="hidden" id="ffbs" name="ffbs" />
							<input class="easyui-textbox" id="ffms" name="ffms"
								data-options="buttonText:'选择',onClickButton:openFwffDialog">
						</td>
					</tr>
					<tr>
						<td>处置策略：</td>
						<td>
				  			<select class="easyui-combobox" name="dealStrategy" id="dealStrategy" style="width:100%;">
				  				<option value="">请选择...</option>
				  				<option value="BLOCK">阻断</option>
				  				<option value="WARN">预警</option>
				  			</select>
				  		</td>
						<td>控制类型：</td>
				  		<td>
				  			<select class="easyui-combobox" name="controlType" id="controlType" style="width:100%;">
				  				<option value="">请选择...</option>
				  				<option value="ZQKZ">周期控制</option>
				  				<option value="DCQQKZ">单次请求控制</option>
				  			</select>
				  		</td>
					</tr>
					<tr>
						<td>流量阈值（kb）：</td>
						<td>
							<input type="text" id="trafficThreshold" name="trafficThreshold" class="easyui-textbox"/>
						</td>
						<td id="trafficThresholdTd1">周期（秒）：</td>
						<td id="trafficThresholdTd2">
							<input type="text" id="cycle" name="cycle" class="easyui-textbox"/>
						</td>
				  		
					</tr>
				</table>
			</form>
		</div>

		<div id="addUpdate-buttons"></div>

		<div id="consumerTree" class="easyui-dialog" style="width: 300px; height: 450px;
			padding: 10px 20px" closed="true" buttons="consumerTree-buttons" closable="true">
			<ul id="tree1" class="easyui-tree"></ul>
		</div>

		<div id="fwffTree" class="easyui-dialog" style="width: 300px; height: 450px;
			padding: 10px 20px" closed="true" buttons="fwffTree-buttons" closable="true">
			<ul id="tree2" class="easyui-tree"></ul>
		</div>
	</div>
	<script>
		$(function(){
			$('#controlType').combobox({
				onSelect: function(newSelect,oldSelect){
					if('ZQKZ' == newSelect.value){
						$("td[id=trafficThresholdTd1]").show();
						$("td[id=trafficThresholdTd2]").show();
					} else if('DCQQKZ' == newSelect.value){
						$("td[id=trafficThresholdTd1]").hide();
						$("td[id=trafficThresholdTd2]").hide();
					}
				}
			})
		})
	</script>
</body>
</html>