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
		$('#dg').datagrid('load',{
			varName:$("#var_name").val()
		});
	}
	
	function deleteVarSystem(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
			$.messager.alert('系统提示','请选择要删除的数据！');
			return;
		}
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].varId);
		}
		var ids=strIds.join(",");
		$.messager.confirm("系统提示","您确认要删除这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
				$.post("${ctx}/varSystem/delete",{delIds:ids},function(result){
					if(result.success){
						$.messager.alert('系统提示',"您已成功删除<font color=red>"+result.delNums+"</font>条数据，选中已被使用的数据未删除！");
						$("#dg").datagrid("reload");
					}else{
						//$.messager.alert('系统提示','<font color=red>'+selectedRows[result.errorIndex].varName+'</font>'+result.errorMsg);
						$.messager.alert('系统提示','<font color=red>'+result.errorMsg+'</font>');
					}
				},"json");
			}
		});
	}
	
	var url;
	
	function openRoleAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加接口信息");
		var varName = document.getElementById("varName");
		varName.readOnly = false;
		url="${ctx}/varSystem/add";
	}
	
	function openRoleModifyDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert('系统提示','请选择一条要编辑的数据！');
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","修改接口信息");
		$("#fm").form("load",row);
		var varName = document.getElementById("varName");
		varName.readOnly = true;
		url="${ctx}/varSystem/add";
	}
	
	function saveVarSystem(isLoad){
		$("#fm").form("submit",{
			url:url+"?isLoad="+isLoad,
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
	
	function checkNode(node){
		if(!node){
			return;
		}else{
			checkNode($('#tree').tree('getParent',node.target));
			$('#tree').tree('check',node.target);
		}
	}
	
	function closeAuthDialog(){
		$("#dlg2").dialog("close");
	}
	
	function rowformat(value, row, index){
		return '<a class="editcls" onclick="look(\''+row.varId+'\')" href="javascript:void(0)">'+value+'</a>';
	}
	
	function look(varId){
		window.open("");
	}
</script>
<title>总线变量管理</title>
</head>
<body style="margin: 1px;">
<table id="dg"  class="easyui-datagrid" fitColumns="true" 
    pagination="true" rownumbers="true" url="${ctx}/varSystem/list" fit="true" toolbar="#tb">
    <thead>
    	<tr>
    		<th field="varId" checkbox="true" align="center"></th>
    		<th field="varName" width="50" align="center">接口名称</th>
    		<th field="varValue" width="100" align="center">接口值</th>
    		<th field="varDesc" width="200" align="center">说明</th>
    		<th field="streamNum" width="50" align="center">关联流程数</th>
    	</tr>
    </thead>
</table>
<div id="tb">
	<div>
		<a href="javascript:openRoleAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="javascript:openRoleModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<a href="javascript:deleteVarSystem()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
	</div>
	<div>
		&nbsp;接口名称：&nbsp;<input type="text" name="var_name" id="var_name" size="20" class="easyui-textbox" onkeydown="if(event.keyCode==13) searchRole()"/>
		<a href="javascript:searchVarSystem()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
	</div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 570px;height: 350px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
  <form id="fm" method="post">
  	<table cellspacing="5px;">
  		<tr>
  			<td>接口名称：</td>
  			<td width="80%"><input type="hidden" id="varId" name="varId" /><input type="text" id="varName" name="varName" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr>
  			<td>接口值：</td>
  			<td width="80%"><input type="text" id="varValue" name="varValue" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr>
  			<td valign="top">接口说明：</td>
  			<td colspan="2">
  				<textarea rows="7" cols="50" name="varDesc" id="varDesc"></textarea>
  			</td>
  		</tr>
  	</table>
  </form>
</div>

<div id="dlg-buttons">
	<a href="javascript:saveVarSystem(0)" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
	<a href="javascript:closeRoleSaveDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
	<a href="javascript:saveVarSystem(1)" class="easyui-linkbutton" iconCls="icon-ok" >保存并加载</a>
</div>

<div id="dlg2" class="easyui-dialog" style="width: 300px;height: 450px;padding: 10px 20px"
  closed="true" buttons="#dlg2-buttons">
	<ul id="tree" class="easyui-tree"></ul>
</div>

</body>
</html>