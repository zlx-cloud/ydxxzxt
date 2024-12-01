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
	
	var url;
	
	function openRoleModifyDialog(index){
		//var selectedRows=$("#dg").datagrid("getRows")[index];
		
		var row=$("#dg").datagrid("getRows")[index];
		$("#dlg").dialog("open").dialog("setTitle","修改信息");
		$("#fm").form("load",row);
		
		url="${ctx}/qqjczd/update";
	}
	
	function saveVarSystem(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				return $(this).form("validate");
			},
			success:function(result){
				var result=eval('('+result+')');
				if(result.errorMsg){
					$.messager.alert('系统提示',"<font color=red>"+result.errorMsg+"</font>");
					return;
				}else{
					$.messager.alert('系统提示','保存成功');
					closeRoleSaveDialog();
					$("#dg").datagrid("reload");
				}
			}
		});
	}
	
	function closeRoleSaveDialog(){
		$("#dlg").dialog("close");
		$("#fm").form('clear');
	}
	
	function closeAuthDialog(){
		$("#dlg2").dialog("close");
	}
	
	function rowformat(value, row, index){
		var result = "否";
		if(value=='1'){
			result="是";
		}
		return result;
	}
	
	function formatZdm(value,row,index){
		return "<a href='javascript:void(0)' class='editcls' onclick='openRoleModifyDialog(\""+index+"\")'>"+value+"</a>";
	}
	
</script>
<title>总线变量管理</title>
</head>
<body style="margin: 1px;">

<table id="dg"  class="easyui-datagrid" fitColumns="true" rownumbers="true" url="${ctx}/qqjczd/list" fit="true" toolbar="#tb">
    <thead>
    	<tr>
    		<th field="zdm" width="50" align="center" formatter="formatZdm">字段名</th>
    		<th field="zdms" width="100" align="center">字段描述</th>
    		<th field="bz" width="200" align="center">说明</th>
    		<th field="isuse" width="50" align="center" formatter="rowformat">是否使用</th>
    	</tr>
    </thead>
</table>
<!-- <div id="tb">
	<div>
		<a href="javascript:openRoleModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
	</div>
</div> -->

<div id="dlg" class="easyui-dialog" style="width: 570px;height: 350px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
  <form id="fm" method="post">
  	<table cellspacing="5px;">
  		<tr>
  			<td>字段名：</td>
  			<td>
  				<input type="text" id="zdm" name="zdm" class="easyui-validatebox" required="true" readonly/>
  			</td>
  			<td>是否使用：</td>
  			<td>
  				<select id="isuse" name="isuse">
  					<option value="0">否</option>
  					<option value="1">是</option>
  				</select>
  			</td>
  		</tr>
  		<tr>
  			<td>字段描述：</td>
  			<td  colspan="3">
  				<input type="text" id="zdms" name="zdms" class="easyui-validatebox" required="true"/>
  			</td>
  		</tr>
  		<tr>
  			<td valign="top">说明：</td>
  			<td colspan="3">
  				<textarea rows="7" cols="50" name="bz" id="bz"></textarea>
  			</td>
  		</tr>
  	</table>
  </form>
</div>

<div id="dlg-buttons">
	<a href="javascript:saveVarSystem()" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
	<a href="javascript:closeRoleSaveDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>

<div id="dlg2" class="easyui-dialog" style="width: 300px;height: 450px;padding: 10px 20px"
  closed="true" buttons="#dlg2-buttons">
	<ul id="tree" class="easyui-tree"></ul>
</div>

</body>
</html>