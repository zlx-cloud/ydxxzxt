<%@page
	import="java.util.Date,java.text.DateFormat,org.apache.shiro.SecurityUtils,net.sinodata.security.vo.ShiroUser"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="zh-cn">
<title>授权信息管理</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	function search() {
		$('#dg').datagrid('load', {
			yybs : $("#yybs_search").val(),
			yymc : $("#yymc_search").val(),
			fwbs : $("#fwbs_search").val(),
			fwmc : $("#fwmc_search").val(),
			ffbs : $("#ffbs_search").val(),
			ffmc : $("#ffmc_search").val()
		});
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<!-- 列表 -->
		<table id="dg" class="easyui-datagrid" fitColumns="true" singleSelect="true" pagination="true" rownumbers="true"
			url="${ctx}/sqxxshow/data?randnum="+Math.floor(Math.random()*1000000) fit="true" toolbar="#tb">
			<thead>
				<tr>
					<th data-options="field:'yybs'" align="center" width="80">授权方标识</th>
					<th data-options="field:'yymc'" align="center" width="80">授权方名称</th>
					<th data-options="field:'fwbs'" align="center" width="80">服务标识</th>
					<th data-options="field:'fwmc'" align="center" width="80">服务名称</th>
					<th data-options="field:'ffbs'" align="center" width="80">方法标识</th>
					<th data-options="field:'ffmc'" align="center" width="80">方法名称</th>
				</tr>
			</thead>
		</table>
		<div id="tb">
			<div>
				&nbsp;授权方标识：&nbsp;<input type="text" class="easyui-textbox" id="yybs_search" size="20" />
				&nbsp;授权方名称：&nbsp;<input type="text" class="easyui-textbox" id="yymc_search" size="20" />
				&nbsp;服务标识：&nbsp;<input type="text" class="easyui-textbox" id="fwbs_search" size="20" />
				&nbsp;服务名称：&nbsp;<input type="text" class="easyui-textbox" id="fwmc_search" size="20" /></br>
				&nbsp;方法标识：&nbsp;<input type="text" class="easyui-textbox" id="ffbs_search" size="20" />
				&nbsp;方法名称：&nbsp;<input type="text" class="easyui-textbox" id="ffmc_search" size="20" />
				<a href="javascript:search()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
				<a href="${ctx}/sqxxshow/exportExcel" class="easyui-linkbutton" iconCls="icon-download-chart" plain="true">授权信息下载</a>
			</div>
		</div>
	</div>
</body>
</html>