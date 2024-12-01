<%@page import="java.util.Date,java.text.DateFormat,org.apache.shiro.SecurityUtils,net.sinodata.security.vo.ShiroUser"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="zh-cn">
<title>数据推送服务</title>
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
	function typeFormat(value) {
		if (value == 'FILE') {
			return "文件";
		} else if (value == 'DB') {
			return "数据库";
		} else if (value == 'NODB') {
			return "非关系型数据库";
		}
	}
	function search() {
		$('#dg').datagrid('load', {
			type : $("#type_search").val(),
			name : $("#name_search").val()
		});
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<table id="dg" class="easyui-datagrid" fitColumns="true" singleSelect="true" pagination="true" rownumbers="true"
			url="${ctx}/sjtsfw/data?randnum=" +Math.floor(Math.random()*1000000) fit="true" toolbar="#tb">
			<thead>
				<tr>
					<th data-options="field:'FILE_NAME'" align="center" width="10">文件/表名称</th>
					<th data-options="field:'FILE_TYPE'" align="center" width="10"
						formatter="typeFormat">类型</th>
					<th data-options="field:'FILE_PATH'" align="center" width="20">文件路径/所示库</th>
					<th data-options="field:'FILE_SIZE'" align="center" width="10">文件大小/数据量</th>
					<th data-options="field:'START_TIME'" align="center" width="15">开始时间</th>
					<th data-options="field:'END_TIME'" align="center" width="15">结束时间</th>
				</tr>
			</thead>
		</table>
		<div id="tb">
			<div>
				&nbsp;文件/表名称：&nbsp;<input type="text" class="easyui-textbox" id="name_search" size="20" />
				&nbsp;类型：&nbsp;
				<select class="easyui-combobox" name="type_search" id="type_search"
					labelPosition="top" size="20" editable=false panelHeight='auto'>
					<option value="">请选择...</option>
					<option value="FILE">文件</option>
					<option value="DB">数据库</option>
					<option value="NODB">非关系型数据库</option>
				</select>
				<a href="javascript:search()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
			</div>
		</div>
	</div>
</body>
</html>