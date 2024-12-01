<%@page import="java.util.Date,java.text.DateFormat,org.apache.shiro.SecurityUtils,net.sinodata.security.vo.ShiroUser"%>
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
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	
	function formatException(value,row,index){
		if(row.state=="exception"){
			return "<a onclick='look(\""+index+"\")' href='javascript:void(0)'>查看</a>";
		}
	}
	
	function look(index){
	
		var row=$("#dg").datagrid("getRows")[index];
		
		//alert(row.id);
		$("#dlg").dialog("open").dialog("setTitle","查看异常信息");
		$("#fm").form("load",row);
	}
	
	function closeRoleSaveDialog(){
		$("#dlg").dialog("close");
		$("#fm").form('clear');
	}
	
</script>
<title>总线日志</title>
</head>
<body style="margin: 1px;">
	<table id="dg"  class="easyui-datagrid" fitColumns="true"  pagination="true" rownumbers="true" url="${ctx}/streamManage/streamDataList?streamId=${streamId}&state=${state}" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th data-options="field:'pid'" width="10">ID</th>
				<th data-options="field:'pname'" width="20" >名称</th>
				<th data-options="field:'startTime'" width="20" >开始时间</th>
				<th data-options="field:'endTime'" width="20" >结束时间</th>
				<th data-options="field:'runTime'" width="10" >运行时间(毫秒)</th>
				<th data-options="field:'state'" width="10" >状态</th>
				<th data-options="field:'xxx'" width="5" formatter="formatException">查看信息</th>
			</tr>
		</thead>
	</table>
	<div id="dlg" class="easyui-dialog" style="width: 680px;height: 450px;padding: 10px 20px"
		  closed="true" buttons="#dlg-buttons">
		  <form id="fm" method="post">
		  	<table cellspacing="5px;">
		  		<tr>
		  			<td valign="top">错误信息：</td>
		  			<td colspan="2">
		  				<textarea rows="22" cols="80" name="runtimeException" id="runtimeException"></textarea>
		  			</td>
		  		</tr>
			</table>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:closeRoleSaveDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
	</div>
	
	<div id="dlg2" class="easyui-dialog" style="width: 300px;height: 450px;padding: 10px 20px"
	  closed="true" buttons="#dlg2-buttons">
		<ul id="tree" class="easyui-tree"></ul>
	</div>

</body>
</html>