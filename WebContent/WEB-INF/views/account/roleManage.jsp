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
	function searchRole(){
		$('#dg').datagrid('load',{
			s_roleName:$("#s_roleName").val()
		});
	}
	
	function deleteRole(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
			$.messager.alert('系统提示','请选择要删除的数据！');
			return;
		}
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].roleid);
		}
		var ids=strIds.join(",");
		$.messager.confirm("系统提示","您确认要删除这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
				$.post("${ctx}/roleT/delete",{delIds:ids},function(result){
					if(result.success){
						$.messager.alert('系统提示',"您已成功删除<font color=red>"+result.delNums+"</font>条数据！");
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert('系统提示','<font color=red>'+selectedRows[result.errorIndex].rolename+'</font>'+result.errorMsg);
					}
				},"json");
			}
		});
	}
	
	var url;
	
	function openRoleAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加角色信息");
		url="${ctx}/roleT/add";
	}
	
	function openRoleModifyDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert('系统提示','请选择一条要编辑的数据！');
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","修改角色信息");
		$("#fm").form("load",row);
		url="${ctx}/roleT/add";
	}
	
	function saveRole(){
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
	var roleid;
	function openAuthDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert('系统提示','请选择一条要授权的角色！');
			return;
		}
		var row=selectedRows[0];
		roleid=row.roleid;
		$("#dlg2").dialog("open").dialog("setTitle","角色授权");
		url="${ctx}/menuT/MenuTree?parentid=1&roleid="+roleid;
		
		$("#tree").tree({
			lines:true,
			url:url,
			checkbox:true,
			cascadeCheck:false,
			onLoadSuccess:function(){
				$("#tree").tree('expandAll');
			},
			onCheck:function(node,checked){
				if(checked){
					checkNode($('#tree').tree('getParent',node.target));
				}
			}
		});
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
	
	function saveMenu(){
		var nodes=$('#tree').tree('getChecked');
		var menuArrIds=[];
		for(var i=0;i<nodes.length;i++){
			menuArrIds.push(nodes[i].id);
		}
		var menuIds=menuArrIds.join(",");
		$.post("${ctx}/roleT/MenuIdsUpdate",{menuIds:menuIds,roleId:roleid},function(result){
			if(result.success){
				$.messager.alert('系统提示','授权成功！');
				closeAuthDialog();
			}else{
				$.messager.alert('系统提示',result.errorMsg);
			}
		},"json");
	}
</script>
<title>角色管理</title>
</head>
<body style="margin: 1px;">
<table id="dg"  class="easyui-datagrid" fitColumns="true" 
    pagination="true" rownumbers="true" url="${ctx}/roleT/list" fit="true" toolbar="#tb">
    <thead>
    	<tr>
    		<th field="cb" checkbox="true" align="center"></th>
    		<th field="roleid" width="50" align="center">编号</th>
    		<th field="rolename" width="100" align="center">角色名称</th>
    		<th field="roledescription" width="200" align="center">备注</th>
    	</tr>
    </thead>
</table>
<div id="tb">
	<div>
		<a href="javascript:openRoleAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="javascript:openRoleModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<a href="javascript:deleteRole()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		<a href="javascript:openAuthDialog()" class="easyui-linkbutton" iconCls="icon-roleManage" plain="true">角色授权</a>
	</div>
	<div>
		&nbsp;角色名称：&nbsp;<input type="text" class="easyui-textbox"  name="s_roleName" id="s_roleName" size="20" onkeydown="if(event.keyCode==13) searchRole()"/>
		<a href="javascript:searchRole()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
	</div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 570px;height: 350px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons" closable="false">
  <form id="fm" method="post">
  	<table cellspacing="5px;">
  		<tr>
  			<td>角色名称：</td>
  			<td width="80%"><input type="hidden" id="roleid" name="roleid" /><input type="text" id="rolename" name="rolename" class="easyui-textbox" required="true"/></td>
  		</tr>
  		<tr>
  			<td valign="top">备注：</td>
  			<td colspan="2">
  				<textarea rows="7" cols="50" name="roledescription" id="roledescription"></textarea>
  			</td>
  		</tr>
  	</table>
  </form>
</div>

<div id="dlg-buttons">
	<a href="javascript:saveRole()" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
	<a href="javascript:closeRoleSaveDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>

<div id="dlg2" class="easyui-dialog" style="width: 300px;height: 450px;padding: 10px 20px"
  closed="true" buttons="#dlg2-buttons" closable="false">
	<ul id="tree" class="easyui-tree"></ul>
</div>

<div id="dlg2-buttons">
	<a href="javascript:saveMenu()" class="easyui-linkbutton" iconCls="icon-ok" >授权</a>
	<a href="javascript:closeAuthDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>
</body>
</html>