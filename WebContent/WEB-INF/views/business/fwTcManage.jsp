<%@page
	import="java.util.Date,java.text.DateFormat,org.apache.shiro.SecurityUtils,net.sinodata.security.vo.ShiroUser"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="zh-cn">
<title>服务探测</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
//url：窗口调用地址，title：窗口标题，width：宽度，height：高度，shadow：是否显示背景阴影罩层  ${ctx}/fwSqhisManage/show
//探测失败历史记录
function showMessageDialog(  shadow) {  
	var selectedRows=$("#dg").datagrid('getSelections');
	if(selectedRows.length!=1){
		$.messager.alert('系统提示','请选择一条要查看探测失败历史记录的服务！');
		return;
	}
	var row=selectedRows[0];
	
	var url="${ctx}/fwSqhisManage/showFailHis?fwbs="+row.fwbs;
    var content = '<iframe src="' + url + '" width="100%" height="99%" frameborder="0" scrolling="no"></iframe>';  
    var boarddiv = '<div id="msgwindow" title="探测失败历史"></div>'//style="overflow:hidden;"可以去掉滚动条  
    $(document.body).append(boarddiv);  
    var win = $('#msgwindow').dialog({  
        content: content,  
        width: "700px",  
        height: "430px",  
        modal: true,  
        title: "探测失败历史",  
        onClose: function () {  
            $(this).dialog('destroy');//后面可以关闭后的事件  
        }  
    });  
    win.dialog('open');  
}


function searchFwzyzcb() {
	$('#dg').datagrid('load', {
		fwmc : $("#fwmc").val(),
		fwbs : $("#fwbsindex").val(),
		fwtgz :$("#fwtgz").val(),
		fwztdm : $('#fwztdmindex').combobox("getValue")
	});
}

function rowformat(value, row, index) {
// 		return '<a class="editcls" onclick="probe()" href="javascript:void(0)">探测</a> <a class="editcls" onclick="updateTc()" href="javascript:void(0)">编辑</a>';
	return '<a class="editcls" onclick="probe()" href="javascript:void(0)">探测</a>';

}

function staformat(value, row, index) {
	return row.fwztdm == 1 ? "启用" : "禁用";
}

//格式化单元格提示信息  
function formatCellTooltip(value) {
	if (value == '' || value == null) {
		return "";
	}
	return "<span title='" + value + "'>" + value + "</span>";
}


function probe() {
	var selectedRows = $("#dg").datagrid('getSelections');
	if (selectedRows.length == 0) {
		$.messager.alert('系统提示', '请选择要探测的接口！');
		return;
	}
	
	var fwbs = selectedRows[0].fwbs;
	var fwzxdz = selectedRows[0].fwzxdz;
	var fwrkdz = selectedRows[0].fwrkdz;
	$.ajax({
		type : "POST",
		dataType : "text",
		url : "${ctx}/monitoring/probeInterface?fwbs="+fwbs+"&fwzxdz="+fwzxdz+"&fwrkdz="+fwrkdz,
		success : function(data) {
			//console.log(data);
			if(data=='fail') {
				$.messager.alert('系统提示', '探测失败！');
			}else{
				$.messager.alert('系统提示', '探测成功！');
			}
		}
	});
}

var url;
function addFwzyzcb() {
	$("#addUpdate").dialog("open").dialog("setTitle", "添加服务探测信息");
	url = "${ctx}/serveManage/addUpdateTc?flag=1";
}

function closeFwtcAddUpdateDialog(){
	$("#addUpdate").dialog("close");
	$("#addUpdatefm").form('clear');
	$("#addUpdatefm").form('reset');
}


function updateTc() {
	var selectedRows = $("#dg").datagrid('getSelections');
	if (selectedRows.length != 1) {
		$.messager.alert('系统提示', '请选择一条要编辑的数据！');
		return;
	}
	var row = selectedRows[0];
	$("#addUpdate").dialog("open").dialog("setTitle", "修改探测信息");
	var 
// 	$("#addUpdatefm").form("load", row);
	url = "${ctx}/serveManage/addUpdateTc";
}


function saveUpdateFwtc(){
	var fwbs=$('#fw').combobox('getValue');
	var fwmc=$('#fw').combobox('getText');
	var arr=fwbs.split("-");
	var brr=fwmc.split("-");
	$('#fwbs').val(arr[0]);
	$('#ffbs').val(arr[1]);
	$('#fwmc').val(brr[0]);
	$('#ffmc').val(brr[1]);
	var fwqqcs = $("#fwqqcs").val();
	if(fwqqcs==null||$.trim(fwqqcs)==""){
		$.messager.alert('系统提示',"<font color=red>"+"请求参数不能为空"+"</font>");
		return;
	}
	//console.log(url);
	$("#addUpdatefm").form("submit",{
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
				closeFwtcAddUpdateDialog();
				$("#dg").datagrid("reload");
			}
		}
	});
}

</script>
</head>
<body class="easyui-layout">


	<div data-options="region:'center'">
		<table id="dg" class="easyui-datagrid" fitColumns="true"
			singleSelect="true" pagination="true" rownumbers="true"
			url="${ctx}/serveManage/queryTcList" fit="true" toolbar="#tb">
			<thead>
				<tr>
					<th data-options="field:'fwbs'" align="center">服务标识</th>
					<th data-options="field:'fwmc'" align="center" width="150">服务名称</th>
<!-- 					<th data-options="field:'fwRkdzlb'" hidden="true"  align="center" width="150" formatter="formatCellTooltip">服务入口地址列表</th> -->
<!-- 					<th data-options="field:'fwztdm'" hidden="true" formatter="staformat" align="center">服务状态</th> -->
					<th data-options="field:'fwcyfYyxtmc'" align="center">服务提供者名称</th>
<!-- 					<th data-options="field:'fwzxdz'" hidden="true" align="center" width="100" formatter="formatCellTooltip">服务总线地址</th> -->
					<th data-options="field:'fwnrBz'" align="center" width="200" formatter="formatCellTooltip">服务内容</th>
<!-- 					<th data-options="field:'fwMs'" align="center" width="60" formatter="formatCellTooltip">服务描述</th> -->
					<th data-options="field:'x'" formatter="rowformat" align="center" width="150">操作</th>
				</tr>
			</thead>
		</table>
		<div id="tb">
			<div>
				&nbsp; 服务标识：&nbsp;<input type="text" class="easyui-textbox"
					name="fwbsindex" id="fwbsindex" size="16" /> 
				&nbsp;服务名称：&nbsp;<input type="text" class="easyui-textbox" 
					name="fwmc" id="fwmc" size="16" />
				&nbsp;服务提供者：&nbsp;<input type="text" class="easyui-textbox"
					name="fwtgz" id="fwtgz" size="16" />
				&nbsp; 服务状态：&nbsp; <select class="easyui-combobox"
					name="fwztdmindex" id="fwztdmindex" labelPosition="top" size="16"
					editable=false panelHeight='auto'>
					<option value="">请选择...</option>
					<option value="1">启用</option>
					<option value="2">禁用</option>

				</select> 
				<a href="javascript:showMessageDialog()" class="easyui-linkbutton" iconCls="icon-history-chart" plain="true">探测失败历史记录</a>
					<a href="javascript:addFwzyzcb()" class="easyui-linkbutton"
					iconCls="icon-add" plain="true">新增</a>
				<a href="javascript:searchFwzyzcb()" class="easyui-linkbutton"
					iconCls="icon-search" plain="true">查询</a>
			</div>
		</div>
	<div id="addUpdate" class="easyui-dialog"
			style="width: 680px; height: 480px; padding: 10px 20px"
			closable="false" closed="true" buttons="#addUpdate-buttons">
			<form id="addUpdatefm" method="post">
					<input  type="hidden" id="fwbs" name="fwbs" />
				  	<input  type="hidden" id="fwmc" name="fwmc" />
				  	<input  type="hidden" id="ffbs" name="ffbs" />
				  	<input  type="hidden" id="ffmc" name="ffmc" />
				  	<table cellspacing="5px;">
				  	
				  		<tr>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;服务：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<select name="fw" id="fw" class="easyui-combobox"  >
								<option value="">请选择</option>
								<c:forEach items="${fw}" var="fw" varStatus="index">
									<option value="${fw.FFBS}">${fw.FFMC}</option>
								</c:forEach>
							</select>
				  		</tr>
				  		 
				  		<tr>
				  		    <td>方法描述：</td>
				  			<td><input type="text" id="ffms" name="ffms" class="easyui-textbox" required="true" value="用于接口探测" readonly/></td>
				  			<td>服务开关：</td>
				  			<td>
							  <select class="easyui-combobox" name="fwkg" id="fwkg" style="width:100%;">
				                <option value="0">禁用</option><option value="1">启用</option>
				               </select>
							</td>
				  		</tr>
				  		<tr>
				  			<td valign="top">接口请求参数：</td>
				  			<td colspan="4">
				  				<textarea rows="7" cols="64" name="fwqqcs" id="fwqqcs" required > </textarea>
				  			</td>
				  		</tr>
				  	</table>
			</form>
		</div>
		<div id="addUpdate-buttons">
		<a href="javascript:saveUpdateFwtc()" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
		<a href="javascript:closeFwtcAddUpdateDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
		</div>



	</div>
</body>
</html>