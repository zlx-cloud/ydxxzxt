<%@page import="java.util.Date,java.text.DateFormat,org.apache.shiro.SecurityUtils,net.sinodata.security.vo.ShiroUser"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="zh-cn">
<title>授权申请</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
<style>
#addfm table tr {
	height: 40px;
}
.new_textbox{
	width: 240px;
}
</style>
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
			return "未审核";
		} else if (row.SQZT == '1') {
			return "<span style='color:green'>审核通过</span>";
		} else if (row.SQZT == '2') {
			return "<span style='color:red'>审核不通过</span>";
		}
	}
	function picFormate(value, row, index){
		if(row.FILENAME != '' && row.FILENAME !=null){
			return '<a style="text-decoration: none;" href="javascript:lookImage(&quot;'+row.ID+'&quot;,&quot;'+row.FILENAME+'&quot;)">'+row.FILENAME+'</a>';
			//return "<a style='text-decoration: none;' href='${ctx}/authapl/downPic?ID="+row.ID+"'>"+row.FILENAME+"</a>";
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
<script type="text/javascript">
	var url = "${ctx}/authapl/add";
	function openSqsqDialog() {
		var savehtml = "";
		$("#add-buttons").html('');
		savehtml += " <a href='javascript:saveAuthapl()' class='easyui-linkbutton' iconCls='icon-ok'>保存</a> ";
		savehtml += " <a href='javascript:closeSqsqDialog()' class='easyui-linkbutton' iconCls='icon-cancel'>关闭</a> ";
		$("#add-buttons").append(savehtml);
		$.parser.parse();

		$('#ffmc').textbox('textbox').attr('readonly', true);
		$("#add").dialog("open").dialog("setTitle", "授权申请");
	}
	function closeSqsqDialog() {
		$("#add").dialog("close");
		$("#addfm").form('clear');
		$("#addfm").form('reset');
	}

	function saveAuthapl() {
		$("#addfm").form(
				"submit",
				{
					url : url,
					onSubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
						var result = eval('(' + result + ')');
						if (result.errorMsg) {
							$.messager.alert('系统提示', "<font color=red>"
									+ result.errorMsg + "</font>");
							return;
						} else {
							$.messager.alert('系统提示', '保存成功');
							closeSqsqDialog();
							$("#dg").datagrid("reload");
						}
					}
				})
	}
</script>
<script type="text/javascript">
	function openSelectDialog() {
		$("#dlg2").dialog("open").dialog("setTitle", "选择服务方法");
	}
	function closeSelectDialog() {
		$("#dlg2").dialog("close");
	}
	function searchFwff(){
		$('#dg2').datagrid('load',{
			s_fwbs:$("#s_fwbs").val(),
			s_fwmc:$("#s_fwmc").val(),
			s_ffbs:$("#s_ffbs").val(),
			s_ffmc:$("#s_ffmc").val()
		});
	}
	function selectOption() {
		var selectedRows=$("#dg2").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert('系统提示','请选择一个服务方法！');
			return;
		}
		var row=selectedRows[0];
		$("#fwbs").val(row.FWBS);
		$("#ffbs").val(row.FFBS);
		$("#ffmc").textbox("setValue",row.FFMC);
		closeSelectDialog();
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
					<th data-options="field:'SQZT'" align="center" formatter="ztformat">申请状态</th>
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
				<a href="javascript:openSqsqDialog()" class="easyui-linkbutton" iconCls="icon-roleManage" plain="true">授权申请</a>
			</div>
		</div>

		<!-- 添加 -->
		<div id="add" class="easyui-dialog" style="width: 500px; height: 350px; padding: 10px 20px"
			closable="false" closed="true" buttons="#add-buttons">
			<form id="addfm" method="post" enctype="multipart/form-data">
				<table cellspacing="5px;" style="margin-left: 12%;">
					<input type="hidden" id="fwbs" name="fwbs" />
					<input type="hidden" id="ffbs" name="ffbs" />
					<tr>
						<td>方法名称：</td>
						<td><input class="easyui-textbox new_textbox" id="ffmc" name="ffmc" required="true"
							data-options="buttonText:'选择',onClickButton:openSelectDialog">
						</td>
					</tr>
					<tr>
						<td>申请原因：</td>
						<td><input type="text" id="sqyy" name="sqyy" class="easyui-textbox new_textbox" /></td>
					</tr>
					<tr>
						<td>使用时间：</td>
						<td><input type="text" id="sysj" name="sysj" class="easyui-textbox new_textbox" /></td>
					</tr>
					<tr>
						<td>图片：</td>
						<td><input class="easyui-filebox new_textbox" id="file" name="file" labelPosition="top"
							data-options="buttonText:'选择图片',accept:'image/jpeg,image/png'" style="width: 100%"></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="add-buttons"></div>

		<!-- 服务方法选择 -->
		<div id="dlg2" class="easyui-dialog" style="width: 700px; height: 400px; padding: 10px 20px" 
			closed="true" buttons="#dlg-buttons" closable="false">
			<div align="center" style="padding-bottom: 4px; padding-top: 4px;">
				服务标识：<input type="text" class="easyui-textbox" id="s_fwbs" name="s_fwbs" style="width:120px"/>
				服务名称：<input type="text" class="easyui-textbox" id="s_fwmc" name="s_fwmc" style="width:120px"/></br>
				方法标识：<input type="text" class="easyui-textbox" id="s_ffbs" name="s_ffbs" style="width:120px"/>
				方法名称：<input type="text" class="easyui-textbox" id="s_ffmc" name="s_ffmc" style="width:120px"/>
				<a href="javascript:searchFwff()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
			</div>
			<table id="dg2" class="easyui-datagrid" fitColumns="true" pagination="false" singleSelect="true" fit="false"
				rownumbers="true" url="${ctx}/authapl/fwfflist?randnum="+Math.floor(Math.random()*1000000) >
				<thead>
					<tr>
						<th field="FWBS" align="center">服务标识</th>
						<th field="FWMC" align="center">服务名称</th>
						<th field="FFBS" align="center">方法标识</th>
						<th field="FFMC" align="center">方法名称</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="dlg-buttons">
			<a href="javascript:selectOption()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确定</a>
			<a href="javascript:closeSelectDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
		</div>
		
		<div id="picture" class="easyui-dialog" style="width: 700px; height: 500px;overflow: hidden;" closed="true" closable="true">
			<iframe id="upload_image" src="" style="width: 100%;height: 100%;"/>
		</div>
		
	</div>
</body>
</html>