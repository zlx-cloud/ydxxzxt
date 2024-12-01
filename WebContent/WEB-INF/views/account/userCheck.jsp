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
<title>应用审核</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>


<script type="text/javascript">
function searchUser(){
	$('#dg').datagrid('load',{
		s_userName:$("#s_userName").val(),
		s_status:$("#s_status").val(),
		
	});
}
 function myformatter(value){  
		
	    if(value!=null){
	        var datetime = new Date();
	        datetime.setTime(value);
	        var year = datetime.getFullYear();
	        var month = datetime.getMonth() + 1;
	        var date = datetime.getDate();
	        var hour = datetime.getHours();
	        var minute = datetime.getMinutes();
	        var second = datetime.getSeconds();
	        var mseconds = datetime.getMilliseconds();
	        return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second+"."+mseconds;
	    }  
	}
 function formatCellTooltip(value){  
 	if(value==''||value==null){
			  return "";  
		  }
     return "<span title='" + value + "'>" + value + "</span>";  
 }
 
 
 function formatCellStatus(value, row, index){
		return  row.status=='1'?"未审核":"其他" ;
	}
 function rowformat(value, row, index){
		if(row.status=="1"){
			return '<a class="editcls" onclick="updateStatus(\''+row.fwcyfYyxtbh+'\')" href="javascript:void(0)">通过</a>';
		}else{
			return '';
		}
		
	}
 function updateStatus(fwcyfYyxtbh){
	 
	  $.post("${ctx}/userT/updateStatus",{fwcyfYyxtbh:fwcyfYyxtbh},function(result){
		if(result.success){
			$.messager.alert('系统提示',"审核通过！");
			$("#dg").datagrid("reload");
			
		}else{
			$.messager.alert('系统提示',result.errorMsg);
		}
	},"json"); 
	 
}
</script>
</head>
<body style="margin: 1px;">
<table id="dg"  class="easyui-datagrid" fitColumns="true" 
    pagination="true" rownumbers="true" url="${ctx}/userT/checklist" fit="true" toolbar="#tb" >
    <thead>
    	<tr>
    		
    		<th field="fwcyfYyxtbh" width="50" align="center" formatter="formatCellTooltip">标识</th>
    		<th field="fwcyfYyxtmc" width="50" align="center" formatter="formatCellTooltip">应用名称</th>
    		<th field="fwcyfDlmm" width="50" align="center" formatter="formatCellTooltip">应用密码</th>
    		<th field="roleid" width="10" align="center" hidden="true" formatter="formatCellTooltip">应用角色ID</th>
    		<th field="rolename" width="50" align="center" formatter="formatCellTooltip">应用角色</th>
    		 <th field="fwcyfSsfj" width="50" align="center" hidden="true">所属单位id</th>
    		<th field="jgmc" width="50" align="center" formatter="formatCellTooltip">所属单位</th>
    		<th field="status" width="50" align="center" formatter="formatCellStatus">状态</th>
    		<th field="fwcyfMs" width="50" align="center" formatter="formatCellTooltip">描述</th>
    		<!-- <th field="fwcyfRqsj" width="50" align="center" formatter="formatCellTooltip" >注册时间</th> -->
    		<th	data-options="field:'fwcyfRqsj',width:80,align:'center',formatter: function(value,row,index){
					if(value){ return myformatter(value);}
				}">注册时间</th>
    	    <th field="fwcyfLxfs" width="50" align="center" formatter="formatCellTooltip">联系方式</th>
    	     <th field="lxdh" width="50" align="center" formatter="formatCellTooltip">联系电话</th>
    		<th field="lxrSm" width="50" align="center" formatter="formatCellTooltip">联系人说明</th>
    		<th field="lxrXm" width="50" align="center" formatter="formatCellTooltip">联系人姓名</th>
    	    <th field="dzxx" width="50" align="center" formatter="formatCellTooltip">电子信箱</th>
    		<th field="txdz" width="50" align="center" formatter="formatCellTooltip">通信地址</th>
    		<th field="fwcyfBm" width="50" align="center" formatter="formatCellTooltip">包名</th>
    		<th field="appId" width="50" align="center" formatter="formatCellTooltip">应用编号</th>
    		<th data-options="field:'x'"  formatter="rowformat" align="center" >编辑</th>
    	</tr>
    </thead>
</table>
<div id="tb">
	
	<div>
		&nbsp;应用名称：&nbsp;<input type="text" class="easyui-textbox"  name="s_userName" id="s_userName" size="20" />
		<!-- &nbsp;审核状态：&nbsp;
		<select class="easyui-combobox" name="s_status" id="s_status"  labelPosition="top"  size="20"  editable=false panelHeight='auto'>
			        <option value="">请选择...</option>
					<option value="1">未审核</option>
				    <option value="0">审核通过</option>
				     <option value="2">审核未通过</option>
				</select> -->
		<a href="javascript:searchUser()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
	</div>
</div>

</body>
</html>