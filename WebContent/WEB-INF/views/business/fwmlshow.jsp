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
<title>服务目录多维展示</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
<style>
.textbox{
	width: 187.5px!important;
}
</style>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	function wlflFormat(value){
		if (value == '1') {
			return "一类";
		} else if (value == '2') {
			return "二类";
		} else if (value == '3') {
			return "三类";
		}
	}
	function czflFormat(value){
		if (value == 'C') {
			return "增加";
		} else if (value == 'R') {
			return "读取";
		} else if (value == 'U') {
			return "更新";
		} else if (value == 'D') {
			return "删除";
		}
	}
	function search() {
		$('#dg').datagrid('load', {
			yymc : $("#yymc_search").val(),
			fwmc : $("#fwmc_search").val(),
			ffbs : $("#ffbs_search").val(),
			ffmc : $("#ffmc_search").val(),
			jgmc : $("#jgmc_search").val(),
			wlfl : $("#wlfl_search").val(),
			czfl : $("#czfl_search").val(),
			jzlbmc : $("#jzlbmc_search").val()
		});
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<!-- 列表 -->
		<table id="dg" class="easyui-datagrid" fitColumns="true" singleSelect="true" pagination="true" rownumbers="true"
			url="${ctx}/fwmlshow/data?randnum="+Math.floor(Math.random()*1000000) fit="true" toolbar="#tb">
			<thead>
				<tr>
					<th data-options="field:'YYMC'" align="center" width="30">应用名称</th>
					<th data-options="field:'FWMC'" align="center" width="30">服务名称</th>
					<th data-options="field:'FFBS'" align="center" width="20">方法标识</th>
					<th data-options="field:'FFMC'" align="center" width="20">方法名称</th>
					<th data-options="field:'JGMC'" align="center" width="30">分局</th>
					<th data-options="field:'WLFL'" align="center" width="20" formatter="wlflFormat">类别</th>
					<th data-options="field:'CZFL'" align="center" width="20" formatter="czflFormat">操作</th>
					<th data-options="field:'JZLBMC'" align="center" width="30">警种</th>
				</tr>
			</thead>
		</table>
		<div id="tb">
			<div>
				&nbsp;应用名称：&nbsp;<input type="text" class="easyui-textbox" id="yymc_search" size="20" />
				&nbsp;服务名称：&nbsp;<input type="text" class="easyui-textbox" id="fwmc_search" size="20" />
				&nbsp;方法标识：&nbsp;<input type="text" class="easyui-textbox" id="ffbs_search" size="20" />
				&nbsp;方法名称：&nbsp;<input type="text" class="easyui-textbox" id="ffmc_search" size="20" /></br>
				&nbsp;分局：&nbsp;<input type="text" class="easyui-textbox" id="jgmc_search" size="20" />
				&nbsp;类别：&nbsp;
				<select class="easyui-combobox" name="wlfl_search" id="wlfl_search" labelPosition="top" size="20" editable=false panelHeight='auto'>
			        <option value="">请选择...</option>
					<option value="1">一类</option>
				    <option value="2">二类</option>
				    <option value="3">三类</option>
				</select>
				&nbsp;操作：&nbsp;
				<select class="easyui-combobox" name="czfl_search" id="czfl_search" labelPosition="top" size="20" editable=false panelHeight='auto'>
			        <option value="">请选择...</option>
					<option value="C">增加</option>
				    <option value="R">读取</option>
				    <option value="U">更新</option>
				    <option value="D">删除</option>
				</select>
				&nbsp;警种：&nbsp;
				<select class="easyui-combobox" name="jzlbmc_search" id="jzlbmc_search" 
					labelPosition="top" size="20" editable=false>
					<option value="">请选择...</option>
					<c:forEach items="${jzInfo}" var="i" varStatus="index">
						<option value="${i.JZLBDM}">${i.JZLBMC}</option>
					</c:forEach>
				</select>
				<a href="javascript:search()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
			</div>
		</div>
	</div>
</body>
</html>