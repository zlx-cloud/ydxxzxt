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
		$('#dg').datagrid('options').url='${ctx}/eventLog/qwlist';
		$('#dg').datagrid('load',{
			param:$("#param").val(),
			operator:$("#operator").val(),
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
		//console.log(row);
		$("#dlg").dialog("open").dialog("setTitle","查看全文日志信息");
		$("#fm").form("load",row);
	}
	
	$(document).ready(function() {
		$('#fwbsSearch').combobox({
			onChange : function() {
				var fwbs = $('#fwbsSearch').combobox('getValue');
				$.ajax({
					type : "post",
					url : "${ctx}/eventLog/getQw?id="+id,//请求后台数据
					dataType : "json",
					success : function(data) {
						//console.log(data);
						if(data==''||data==null){
							$("#ffmcSearch").combobox({//往下拉框塞值
								data : [{"FFBS":"0","FFMS":"此服务没有注册方法"}],
								valueField : "FFBS",//value值
								textField : "FFMS",//文本值
								panelHeight : "auto"
							})
						}else{
							$("#ffmcSearch").combobox({//往下拉框塞值
								data : data,
								valueField : "FFBS",//value值
								textField : "FFMS",//文本值
								panelHeight : "auto"
							})
						}
						
					}
				});
			}
		})
	});
</script>
<title>请求响应日志</title>
</head>
<body style="margin: 1px;">
<table id="dg"  class="easyui-datagrid" fitColumns="true" 
    pagination="true" rownumbers="true" url="${ctx}/eventLog/qwlist" fit="true" toolbar="#tb">
    <thead>
    	<tr>
    		<th field="qqbwbs" width="50" align="center" formatter="qqbwbsA">报文标识</th>
    		<th field="fwqqzZcxx" width="50" align="center">应用名称</th>
    		<th field="fwbs" width="50" align="center">服务名称</th>
    		<th field="ffbs" width="20" align="center">方法名称</th>
    		<th field="startTime" width="50" align="center">请求时间</th>
    		<th field="endTime" width="50" align="center">响应时间</th>
    		<th field="timeConsuming" width="20" align="center">响应时长</th>
    		<th field="fwtgztdm" width="20" align="center">响应状态</th>
    	</tr>
    </thead>
</table>
<div id="tb">
	<div>
		&nbsp;全文检索：&nbsp;<input type="text" name="param" id="param" size="20" onkeydown="if(event.keyCode==13) searchRole()"/>
		&nbsp;操作人员：&nbsp;<input type="text" name="operator" id="operator" size="20" onkeydown="if(event.keyCode==13) searchRole()"/>

		<!-- 		&nbsp;模糊搜索：&nbsp;<input type="text" name="fwmcfwcyfmcSearch" id="fwmcfwcyfmcSearch" size="20" /> -->
		<a href="javascript:searchVarSystem()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		<a href="${ctx}/eventLog/qwLogExport" class="easyui-linkbutton" iconCls="icon-download-chart" plain="true">导出</a>
	</div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 800px;height: 450px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
  <form id="fm" method="post">
  	<table cellspacing="5px;">
  		<tr>
  			<td>报文标识：</td>
  			<td><input type="text" id="qqbwbs" name="qqbwbs" class="easyui-validatebox" required="true" readonly/></td>
  			<td>服务名称：</td>
  			<td><input type="text" id="fwbs" name="fwbs" class="easyui-validatebox" required="true" readonly/></td>
  		</tr>
  		<tr>
  			<td>服务参与方名称：</td>
  			<td><input type="text" id="fwqqzZcxx" name="fwqqzZcxx" class="easyui-validatebox" required="true" readonly/></td>
  			<td>方法名称：</td>
  			<td><input type="text" id="ffbs" name="ffbs" class="easyui-validatebox" required="true" readonly/></td>
  		</tr>
  		<tr>
  			<td>接收请求时间：</td>
  			<td><input type="text" id="startTime" name="startTime" class="easyui-validatebox" required="true" readonly/></td>
  			<td>响应请求时间：</td>
  			<td><input type="text" id="endTime" name="endTime" class="easyui-validatebox" required="true" readonly/></td>
  		</tr>
  		<tr>
  			<td>请求时间：</td>
  			<td><input type="text" id="fwqqRqsj" name="fwqqRqsj" class="easyui-validatebox" required="true" readonly/></td>
  			<td>请求设备编号：</td>
  			<td><input type="text" id="fwqqsbBh" name="fwqqsbBh" class="easyui-validatebox" required="true" readonly/></td>
  		</tr>
  		<tr>
  			<td>操作人员姓名：</td>
  			<td><input type="text" id="xxczryXm" name="xxczryXm" class="easyui-validatebox" required="true" readonly/></td>
  			<td>操作人员证件号：</td>
  			<td><input type="text" id="xxczryGmsfhm" name="xxczryGmsfhm" class="easyui-validatebox" required="true" readonly/></td>
  		</tr>
  		<tr>
  			<td>操作人员机构：</td>
  			<td><input type="text" id="xxczryGajgjgdm" name="xxczryGajgjgdm" class="easyui-validatebox" required="true" readonly/></td>
  			<td>请求事件类型：</td>
  			<td><input type="text" id="fwqqSjsjlx" name="fwqqSjsjlx" class="easyui-validatebox" required="true" readonly/></td>
  		</tr>
  		<tr>
  			<td valign="top">请求内容：</td>
  			<td colspan="3">
  				<textarea rows="3" cols="65" name="fwqqNr" id="fwqqNr" readonly></textarea>
  			</td>
  		</tr>
  		<tr>
  			<td valign="top">响应内容：</td>
  			<td colspan="3">
  				<textarea rows="5" cols="65" name="fwtgNr" id="fwtgNr" readonly></textarea>
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