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
<title>警员基本信息</title>
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
			jh : $("#jh_search").val(),
			sfzh : $("#sfzh_search").val(),
			name : $("#name_search").val(),
			jz : $("#jz_search").val(),
			sex : $("#sex_search").val(),
			jg : $("#jg_search").val(),
			phone : $("#phone_search").val(),
		});
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<!-- 列表 -->
		<table id="dg" class="easyui-datagrid" fitColumns="true" singleSelect="true" pagination="true" rownumbers="true"
			url="${ctx}/policemember/data?randnum="+Math.floor(Math.random()*1000000) fit="true" toolbar="#tb">
			<thead>
				<tr>
					<th data-options="field:'POLICE_NO'" align="center" width="10">警号</th>
					<th data-options="field:'POLICE_NAME'" align="center" width="10">姓名</th>
					<th data-options="field:'POLICE_SEX'" align="center" width="10">性别</th>
					<th data-options="field:'ORG_CODE'" align="center" width="20">所属机构</th>
					<th data-options="field:'POLICE_TYPE'" align="center" width="20">警种</th>
					<th data-options="field:'POLICE_SFZH'" align="center" width="15">身份证号</th>
					<th data-options="field:'PHONE'" align="center" width="15">手机号</th>
				</tr>
			</thead>
		</table>
		<div id="tb">
			<div>
				&nbsp;警号：&nbsp;<input type="text" class="easyui-textbox" id="jh_search" size="20" />
				&nbsp;姓名：&nbsp;<input type="text" class="easyui-textbox" id="name_search" size="20" />
				&nbsp;性别：&nbsp;<input type="text" class="easyui-textbox" id="sex_search" size="20" />
				&nbsp;所属机构：&nbsp;<input type="text" class="easyui-textbox" id="jg_search" size="20" /></br>
				&nbsp;警种：&nbsp;<input type="text" class="easyui-textbox" id="jz_search" size="20" />
				&nbsp;身份证号：&nbsp;<input type="text" class="easyui-textbox" id="sfzh_search" size="20" />
				&nbsp;手机号：&nbsp;<input type="text" class="easyui-textbox" id="phone_search" size="20" />
				<a href="javascript:search()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
			</div>
		</div>
	</div>
</body>
</html>