<%@page
	import="java.util.Date,java.text.DateFormat,org.apache.shiro.SecurityUtils,net.sinodata.security.vo.ShiroUser"%>
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
<title>菜单管理</title>

<link href="${ctx}/static/common/css/font-awesome.min.css-v=4.4.0.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/easyui/themes/icon.css">
<script type="text/javascript"
	src="${ctx}/static/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var url;

	$(function() {
		$('#treeGrid').treegrid({
			url : '${ctx}/orgT/treeGridOrg',
			onLoadSuccess : function() {
				/* $("#treeGrid").treegrid('expandAll'); */
				//$("#treeGrid").treegrid('collapseAll');
				
			},
			   onBeforeExpand:function(node){

	                $('#treeGrid').treegrid('options').queryParams = {sssjjg:node.id};

	               
	            }
		});
	});

	function searchOrg(){
		$('#treeGrid').treegrid('load',{
			jgmc:$("#jgmc").val(),
			jgbs:$("#jgbs").val(),
		});
	}


	


</script>
</head>
<body style="margin: 1px;">
	<table id="treeGrid"  class="easyui-treegrid" toolbar="#tb"
		data-options="idField:'id',treeField:'text',fit:true,fitColumns:true,rownumbers:true">
		<thead>
			<tr>
				<th field="id" width="30" align="center">机构标识</th>
				<th field="text" width="80">机构名称</th>
				<th field="jgywmc" width="100" align="center">机构英文名称</th>
				
			</tr>
		</thead>
	</table>
	

	<div id="tb">
	
	<div>
		&nbsp;机构名称：&nbsp;<input type="text" class="easyui-textbox"  name="jgmc" id="jgmc" size="20" />
		&nbsp;机构标识：&nbsp;<input type="text"  class="easyui-textbox"  name="jgbs" id="jgbs" size="20" />
		<a href="javascript:searchOrg()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
	
	</div>
</div>


</body>
</html>