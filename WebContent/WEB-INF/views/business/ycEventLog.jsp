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
	function searchVarSystem(){
		$('#dg').datagrid('options').url='${ctx}/eventLog/yclist';
		$('#dg').datagrid('load',{
			qqbwbsSearch:$("#qqbwbsSearch").val(),
			fwbsSearch:$("#fwbsSearch").val(),
			fwqqzZcxxSearch:$("#fwqqzZcxxSearch").val(),
			startTimeSearch:$("#startTimeSearch").val(),
			endTimeSearch:$("#endTimeSearch").val(),
		});
	}
	
	function closeRoleSaveDialog(){
		$("#dlg").dialog("close");
		$("#fm").form('clear');
	}
	
	function closeAuthDialog(){
		$("#dlg2").dialog("close");
	}
	
	function qqbwbsA(value, row, index){
		return '<a class="editcls" onclick="look(\''+index+'\')" href="javascript:void(0)">'+value+'</a>';
	}
	
	function look(varId){
		var row=$("#dg").datagrid("getRows")[varId];
		$("#dlg").dialog("open").dialog("setTitle","查看日志信息");
		$("#fm").form("load",row);
	}
</script>
<title>异常请求日志</title>
</head>
<body style="margin: 1px;">
<table id="dg"  class="easyui-datagrid" fitColumns="true" 
    pagination="true" rownumbers="true" url="${ctx}/eventLog/yclist?startTimeSearch=${startTime}&endTimeSearch=${endTime}" fit="true" toolbar="#tb">
    <thead>
    	<tr>
    		<th field="qqbwbs" width="50" align="center" formatter="qqbwbsA">报文标识</th>
    		<th field="fwbs" width="50" align="center">服务名称</th>
    		<th field="fwqqzZcxx" width="50" align="center">应用名称</th>
    		<th field="ffbs" width="20" align="center">方法名称</th>
    		<th field="startTime" width="50" align="center">请求时间</th>
    		<th field="exceptionCols" width="50" align="center">异常字段</th>
    	</tr>
    </thead>
</table>
<div id="tb">
	<div>
		&nbsp;报文标识：&nbsp;<input type="text" name="qqbwbsSearch" id="qqbwbsSearch" size="20" onkeydown="if(event.keyCode==13) searchRole()"/>
		&nbsp;服务名称：&nbsp;
		<select name="fwbsSearch" id="fwbsSearch" class="easyui-combobox">
			<option value="">请选择</option>
			<c:forEach items="${fwzy}" var="zyxx" varStatus="index">
				<option value="${zyxx.fwbs}">${zyxx.fwmc}</option>
			</c:forEach>
		</select>
		&nbsp;应用名称：&nbsp;
		<select name="fwqqzZcxxSearch" id="fwqqzZcxxSearch" class="easyui-combobox">
			<option value="">请选择</option>
			<c:forEach items="${fwcyf}" var="zcxx" varStatus="index">
				<option value="${zcxx.fwcyfYyxtbh}">${zcxx.fwcyfYyxtmc}</option>
			</c:forEach>
		</select>
		<br/>
		&nbsp;请求时间：&nbsp;<input type="text" name="startTimeSearch" id="startTimeSearch" class="easyui-datetimebox" size="20" value="${startTime}"/>
		&nbsp;至&nbsp;<input type="text" name="endTimeSearch" id="endTimeSearch" class="easyui-datetimebox" size="20" value="${endTime}"/>
		<a href="javascript:searchVarSystem()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		<a href="${ctx}/eventLog/ycLogExport" class="easyui-linkbutton" iconCls="icon-download-chart" plain="true">导出</a>
	</div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 800px;height: 450px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
  <form id="fm" method="post">
  	<table cellspacing="5px;">
  		<tr>
  			<td>报文标识：</td>
  			<td><input type="text" id="qqbwbs" name="qqbwbs" required="true" readonly/></td>
  			<td>服务名称：</td>
  			<td><input type="text" id="fwbs" name="fwbs" required="true" readonly/></td>
  		</tr>
  		<tr>
  			<td>应用名称：</td>
  			<td><input type="text" id="fwqqzZcxx" name="fwqqzZcxx" required="true" readonly/></td>
  			<td>方法名称：</td>
  			<td><input type="text" id="ffbs" name="ffbs" required="true" readonly/></td>
  		</tr>
  		<tr>
  			<td>接收请求时间：</td>
  			<td><input type="text" id="startTime" name="startTime" required="true" readonly/></td>
  			<td>请求事件类型：</td>
  			<td><input type="text" id="fwqqSjsjlx" name="fwqqSjsjlx" required="true" readonly/></td>
  		</tr>
  		<tr>
  			<td>请求时间：</td>
  			<td><input type="text" id="fwqqRqsj" name="fwqqRqsj" required="true" readonly/></td>
  			<td>请求设备编号：</td>
  			<td><input type="text" id="fwqqsbBh" name="fwqqsbBh" required="true" readonly/></td>
  		</tr>
  		<tr>
  			<td>操作人员姓名：</td>
  			<td><input type="text" id="xxczryXm" name="xxczryXm" required="true" readonly/></td>
  			<td>操作人员证件号：</td>
  			<td><input type="text" id="xxczryGmsfhm" name="xxczryGmsfhm" required="true" readonly/></td>
  		</tr>
  		<tr>
  			<td>操作人员机构：</td>
  			<td><input type="text" id="xxczryGajgjgdm" name="xxczryGajgjgdm" required="true" readonly/></td>
  			<td>异常字段描述：</td>
  			<td rowspan="2">
  				<textarea rows="3" cols="30" name="exceptionColnames" id="exceptionColnames" readonly></textarea>
  			</td>
  		</tr>
  		<tr>
  			<td>异常字段：</td>
  			<td><input type="text" id="exceptionCols" name="exceptionCols" required="true" readonly/></td>
  		</tr>
  		<tr>
  			<td valign="top">请求内容：</td>
  			<td colspan="3">
  				<textarea rows="6" cols="65" name="fwqqNr" id="fwqqNr" readonly></textarea>
  			</td>
  		</tr>
  		<tr>
  			<td valign="top">请求全文：</td>
  			<td colspan="3">
  				<textarea rows="6" cols="65" name="qqqw" id="qqqw" readonly></textarea>
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