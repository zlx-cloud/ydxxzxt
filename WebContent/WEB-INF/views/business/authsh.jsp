<%@page import="java.util.Date,java.text.DateFormat,org.apache.shiro.SecurityUtils,net.sinodata.security.vo.ShiroUser"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="zh-cn">
<title>授权审核</title>
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
	function formatCellTooltip(value) {
		if (value == '' || value == null) {
			return "";
		}
		return "<span title='" + value + "'>" + value + "</span>";
	}
	function ztformat(value, row, index) {
		if (row.SQZT == 0) {
			return '&nbsp;<a style="text-decoration: none;" href="javascript:shtg(1,&quot;'+
					row.ID+'&quot;,&quot;'+row.YYBS+'&quot;,&quot;'+row.FWBS+
					'&quot;,&quot;'+row.FFBS+'&quot;)">通过</a>&nbsp;'+
					'<a style="text-decoration: none;" href="javascript:shbtg(2,&quot;'+
					row.ID+'&quot;)">不通过</a>&nbsp;';
		} else if (row.SQZT == '1') {
			return "<span style='color:green'>审核通过</span>";
		} else if (row.SQZT == '2') {
			return "<span style='color:red'>审核不通过</span>";
		}
	}
	function shbtg(zt,id){
		$.ajax({
            url: "${ctx}/authapl/shbtg",
            type: "post",
            data: {
                'ZT': zt,
                'ID': id
            },
            async: false,
            dataType: "text",
            success: function (result) {
            	if(result=='success'){
            		$.messager.alert('系统提示', '审核成功');
            	}else{
            		$.messager.alert('系统提示', '审核失败');
            	}
            	$("#dg").datagrid("reload");
            }
        });
	}
	function shtg(zt,id,yybs,fwbs,ffbs){
		$.ajax({
            url: "${ctx}/authapl/shtg",
            type: "post",
            data: {
                'ZT': zt,
                'ID': id,
                'YYBS': yybs,
                'FWBS': fwbs,
                'FFBS': ffbs
            },
            async: false,
            dataType: "text",
            success: function (result) {
            	if(result=='success'){
            		$.messager.alert('系统提示', '审核成功');
            		$("#dg").datagrid("reload");
            	}else{
            		$.messager.alert('系统提示', '审核失败');
            		$("#dg").datagrid("reload");
            	}
            }
        });
	}
	function picFormate(value, row, index){
		if(row.FILENAME != '' && row.FILENAME !=null){
			return '<a style="text-decoration: none;" href="javascript:lookImage(&quot;'+row.ID+'&quot;,&quot;'+row.FILENAME+'&quot;)">'+row.FILENAME+'</a>';
		}else {
			return "";
		}
	}
	function search() {
		$('#dg').datagrid('load', {
			fwbs : $("#fwbs_search").val(),
			fwmc : $("#fwmc_search").val(),
			ffbs : $("#ffbs_search").val(),
			ffmc : $("#ffmc_search").val()
		});
	}
	function lookImage(ID,FILENAME){
		$("#upload_image").attr("src","");
		$("#picture").dialog("open").dialog("setTitle", FILENAME);
		$("#upload_image").attr("src","${ctx}/authapl/lookImage?ID="+ID);
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<!-- 列表 -->
		<table id="dg" class="easyui-datagrid" fitColumns="true"
			singleSelect="true" pagination="true" rownumbers="true"
			url="${ctx}/authapl/data?randnum="+Math.floor(Math.random()*1000000) fit="true" toolbar="#tb">
			<thead>
				<tr>
					<th data-options="field:'ID'" hidden="true">ID</th>
					<th data-options="field:'YYBS'" hidden="true">应用标识</th>
					<th data-options="field:'YYMC'" align="center" formatter="formatCellTooltip">申请方</th>
					<th data-options="field:'FWBS'" align="center" formatter="formatCellTooltip">服务标识</th>
					<th data-options="field:'FWMC'" align="center" formatter="formatCellTooltip">服务名称</th>
					<th data-options="field:'FFBS'" align="center" formatter="formatCellTooltip">方法标识</th>
					<th data-options="field:'FFMC'" align="center" formatter="formatCellTooltip">方法名称</th>
					<th data-options="field:'SQYY'" align="center" formatter="formatCellTooltip">申请原因</th>
					<th data-options="field:'SYSJ'" align="center" formatter="formatCellTooltip">使用时间</th>
					<th data-options="field:'FILENAME'" align="center" formatter="picFormate">图片</th>
					<th data-options="field:'SQSJ'" align="center" formatter="formatCellTooltip">申请时间</th>
					<th data-options="field:'SQZT'" align="center" formatter="ztformat">审核状态</th>
				</tr>
			</thead>
		</table>
		<div id="tb">
			<div>
				&nbsp;服务标识：&nbsp;<input type="text" class="easyui-textbox" id="fwbs_search" size="20" />
				&nbsp;服务名称：&nbsp;<input type="text" class="easyui-textbox" id="fwmc_search" size="20" />
				&nbsp;方法标识：&nbsp;<input type="text" class="easyui-textbox" id="ffbs_search" size="20" />
				&nbsp;方法名称：&nbsp;<input type="text" class="easyui-textbox" id="ffmc_search" size="20" />
				<a href="javascript:search()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
			</div>
		</div>

		<div id="picture" class="easyui-dialog" style="width: 700px; height: 500px;overflow: hidden;" closed="true" closable="true">
			<image id="upload_image" src="" style="width: 100%;height: 100%;"/>
		</div>
		
	</div>
</body>
</html>