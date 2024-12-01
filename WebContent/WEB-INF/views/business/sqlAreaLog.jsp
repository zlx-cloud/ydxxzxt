<%@page import="java.util.Date,java.text.DateFormat,org.apache.shiro.SecurityUtils,net.sinodata.security.vo.ShiroUser"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="zh-cn">
<title>系统操作日志</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
<style>
.textbox {
	width: 187.5px !important;
}
</style>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	function lookSql(value, row, index) {
		return '<a style="text-decoration: none;" href="javascript:cxSql(&quot;'
				+ row.SQL_ID + '&quot;)">查看</a>';
	}

	function cxSql(value) {
		$.ajax({
			url : "${ctx}/eventLog/lookSql",
			type : "post",
			data : {
				'sqlId' : value
			},
			async : false,
			dataType : "text",
			success : function(result) {
				$("#sqlFullText").val(result);
			}
		});
		$("#dlg").dialog("open").dialog("setTitle","查看异常信息");
	}
	
	function closeRoleSaveDialog(){
		$("#dlg").dialog("close");
		$("#fm").form('clear');
	}
	
	function search() {
		$('#dg').datagrid('load', {
			dayTime : $("#dayTime").val()
		});
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<!-- 列表 -->
		<table id="dg" class="easyui-datagrid" fitColumns="true"
			singleSelect="true" pagination="true" rownumbers="true"
			url="${ctx}/eventLog/sqlAreaList?randnum="
			+Math.floor(Math.random()*1000000) fit="true" toolbar="#tb">
			<thead>
				<tr>
					<th data-options="field:'SQL_ID'" align="center" width="20">ID</th>
					<th data-options="field:'PARSING_SCHEMA_NAME'" align="center" width="20">用户</th>
					<th data-options="field:'MODULE'" align="center" width="20">程序</th>
					<th data-options="field:'LAST_ACTIVE_TIME'" align="center" width="20">执行时间</th>
					<th data-options="field:'SQL'" align="center" width="20" formatter="lookSql">SQL</th>
				</tr>
			</thead>
		</table>
		<div id="tb">
			<div>
				请求时间：&nbsp;<input type="text" name="dayTime" id="dayTime" class="easyui-datebox" size="10" value="${dayTime}"/>
				<a href="javascript:search()" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="search">查询</a>
			</div>
		</div>

		<div id="dlg" class="easyui-dialog" style="width: 570px; height: 350px; padding: 10px 20px" 
			closed="true" buttons="#dlg-buttons">
			<form id="fm" method="post">
				<table cellspacing="5px;">
					<tr>
						<td colspan="3"><textarea rows="15" cols="68" id="sqlFullText"></textarea></td>
					</tr>
				</table>
			</form>
		</div>

		<div id="dlg-buttons">
			<a href="javascript:closeRoleSaveDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
		</div>

	</div>
</body>
</html>