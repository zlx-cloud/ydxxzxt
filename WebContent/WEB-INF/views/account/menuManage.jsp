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
			url : '${ctx}/menuT/treeGridMenu?parentId=1',
			onLoadSuccess : function() {
				/* $("#treeGrid").treegrid('expandAll'); */
				$("#treeGrid").treegrid('collapseAll');
				
			}
		});
	});

	function formatIconCls(value, row, index) {
		/* return "<div class="+value+">&nbsp;</div>"; */

		return "<i class='"+value+"'></i>";
	}

	function openAuthAddDialog() {
		var id;
		var node = $('#treeGrid').treegrid('getSelected');
		if (node == null) {
			id="1";
			//$.messager.alert('系统提示', '请选择一个父菜单节点！');
			//return;
		}else{
			id=node.id;
		}
		
		$("#dlg").dialog("open").dialog("setTitle", "添加菜单");
		url = "${ctx}/menuT/add?parentId=" + id;
	}

	function openAuthModifyDialog() {
		var node = $('#treeGrid').treegrid('getSelected');
		if (node == null) {
			$.messager.alert('系统提示', '请选择一个要修改的菜单！');
			return;
		}
		$("#dlg").dialog("open").dialog("setTitle", "修改菜单");
		$("#fm").form("load", node);
		 $("#menuname").textbox("setValue",node.text); 
		/* $("#menuname").val(node.text); */
		url = "${ctx}/menuT/add?menuId=" + node.id;
	}

	function deleteMenu() {
		var node = $("#treeGrid").treegrid('getSelected');
		if (node == null) {
			$.messager.alert('系统提示', '请选择一个要删除的菜单节点！');
			return;
		}
		var parentNode = $("#treeGrid").treegrid('getParent', node.id);
		var parentId;
		if(parentNode==null){
			parentId="";
		}else{
			parentId=parentNode.id;
		}
		$.messager.confirm("系统提示", "您确认要删除这个菜单节点吗?", function(r) {
			if (r) {
				$.post("${ctx}/menuT/delete", {
					menuId : node.id,
					parentId : parentId
				}, function(result) {
					if (result.success) {
						$.messager.alert('系统提示', "您已成功删除这个菜单节点！");
						$("#treeGrid").treegrid("reload");
					} else {
						$.messager.alert('系统提示', result.errorMsg);
					}
				}, "json");
			}
		});
	}

	function saveAuth() {
		$("#fm").form(
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
							closeAuthDialog();
							$("#treeGrid").treegrid("reload");
						}
					}
				});
	}

	function closeAuthDialog() {
		$("#dlg").dialog("close");
		$("#fm").form('clear');
		$("#iconCls").val("icon-item");
	}
	function openYsChooseDialog() {
		$("#dlg2").dialog("open").dialog("setTitle", "选择样式");
	}
	function chooseYs(index){
	
	
		var i=index.firstChild;
		var box = $(i);
		
		 $("#iconcls").textbox("setValue",box.attr('class'));
		
		$("#dlg2").dialog("close"); 
	}
</script>
</head>
<body style="margin: 1px;">
	<table id="treeGrid"  class="easyui-treegrid" toolbar="#tb"
		data-options="idField:'id',treeField:'text',fit:true,fitColumns:true,rownumbers:true">
		<thead>
			<tr>
				<th field="id" width="30" align="center">菜单编号</th>
				<th field="text" width="80">菜单名称</th>
				<th field="iconcls" width="35" align="center"
					formatter="formatIconCls">图标</th>
				<th field="menupath" width="100" align="center">链接地址</th>
				<th field="menudescription" width="100" align="center">备注</th>
			</tr>
		</thead>
	</table>
	<div id="tb">
		<div>
			<a href="javascript:openAuthAddDialog()" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">添加</a> <a
				href="javascript:openAuthModifyDialog()" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">修改</a> <a
				href="javascript:deleteMenu()" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true">删除</a>
		</div>
	</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 680px; height: 350px; padding: 10px 10px" closed="true" closable="false"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="5px;">
				<tr>
					<td>菜单名称：</td>
					<td><input type="text" id="menuname" name="menuname"
						class="easyui-textbox" required="true" /></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>菜单样式：</td>
					<td><input type="text" id="iconcls" name="iconcls"
						value="icon-item" class="easyui-textbox" required="true" /> <a
						href="javascript:openYsChooseDialog()" class="easyui-linkbutton">选择样式</a>

					</td>
				</tr>
				<tr>
					<td>链接路径：</td>
					<td colspan="4"><input type="text" size="58" id="menupath"
						name="menupath" class="easyui-textbox" required="true" /></td>
				</tr>
				<tr>
					<td valign="top">备注：</td>
					<td colspan="4"><textarea rows="7" cols="50" 
							name="menudescription" id="menudescription"></textarea></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:saveAuth()" class="easyui-linkbutton"
			iconCls="icon-ok">保存</a> <a href="javascript:closeAuthDialog()"
			class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>

	<div id="dlg2" class="easyui-dialog" iconCls="icon-search"
		style="width: 530px; height: 500px; padding: 10px 10px" closed="true" closable="true">
			<table cellspacing="5px;">
  	       <tr>
  			<td>
		<div class="fa-hover col-md-3 col-sm-4">
			<a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-area-chart"></i> area-chart</a>
		</div>

		<div class="fa-hover col-md-3 col-sm-4">
			<a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-bar-chart"></i> bar-chart</a>
		</div>

		<div class="fa-hover col-md-3 col-sm-4">
			<a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-bar-chart-o"></i>
				bar-chart-o <span class="text-muted">(alias)</span></a>
		</div>

		<div class="fa-hover col-md-3 col-sm-4">
			<a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-line-chart"></i> line-chart</a>
		</div>

		<div class="fa-hover col-md-3 col-sm-4">
			<a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-pie-chart"></i> pie-chart</a>
		</div>
		<div class="fa-hover col-md-3 col-sm-4">
			<a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-file"></i> file</a>
		</div>
                              <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-battery-three-quarters"></i> battery-three-quarters</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-bed"></i> bed</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-beer"></i> beer</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-bell"></i> bell</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-bell-o"></i> bell-o</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-bell-slash"></i> bell-slash</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-bell-slash-o"></i> bell-slash-o</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-bicycle"></i> bicycle</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-binoculars"></i> binoculars</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-birthday-cake"></i> birthday-cake</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-bolt"></i> bolt</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-bomb"></i> bomb</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-book"></i> book</a></div>
	

		

		
		 <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-cart-plus"></i> cart-plus</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-cc"></i> cc</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-certificate"></i> certificate</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-check"></i> check</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-check-circle"></i> check-circle</a></div>
                                
                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-caret-square-o-down"></i> caret-square-o-down</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-caret-square-o-left"></i> caret-square-o-left</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-caret-square-o-right"></i> caret-square-o-right</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-caret-square-o-up"></i> caret-square-o-up</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-cart-arrow-down"></i> cart-arrow-down</a></div>
		</td><td>
		
		   <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-adjust"></i> adjust</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-anchor"></i> anchor</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-archive"></i> archive</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-area-chart"></i> area-chart</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-arrows"></i> arrows</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-arrows-h"></i> arrows-h</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-arrows-v"></i> arrows-v</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-asterisk"></i> asterisk</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-at"></i> at</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-automobile"></i> automobile <span class="text-muted">(alias)</span></a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-balance-scale"></i> balance-scale</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-ban"></i> ban</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-bank"></i> bank <span class="text-muted">(alias)</span></a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-bar-chart"></i> bar-chart</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-bar-chart-o"></i> bar-chart-o <span class="text-muted">(alias)</span></a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-barcode"></i> barcode</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-bars"></i> bars</a></div>

                             

                              

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-bookmark"></i> bookmark</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-bookmark-o"></i> bookmark-o</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-briefcase"></i> briefcase</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-bug"></i> bug</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-building"></i> building</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-building-o"></i> building-o</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-bullhorn"></i> bullhorn</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-bullseye"></i> bullseye</a></div>


                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-cab"></i> cab <span class="text-muted">(alias)</span></a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-calculator"></i> calculator</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-calendar"></i> calendar</a></div>
                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-database"></i> database</a></div>


                               


                               
                              </td>
                              <td>
                              
                              <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-chrome"></i> chrome</a></div>
                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-odnoklassniki"></i> odnoklassniki</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-odnoklassniki-square"></i> odnoklassniki-square</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-opencart"></i> opencart</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-opera"></i> opera</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-optin-monster"></i> optin-monster</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-registered"></i> registered</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-safari"></i> safari</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-sticky-note"></i> sticky-note</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-sticky-note-o"></i> sticky-note-o</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-television"></i> television</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-trademark"></i> trademark</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-tripadvisor"></i> tripadvisor</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-tv"></i> tv <span class="text-muted">(alias)</span></a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-vimeo"></i> vimeo</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-wikipedia-w"></i> wikipedia-w</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-y-combinator"></i> y-combinator</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-yc"></i> yc <span class="text-muted">(alias)</span></a></div>
                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-hourglass-3"></i> hourglass-3 <span class="text-muted">(alias)</span></a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-hourglass-end"></i> hourglass-end</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-hourglass-half"></i> hourglass-half</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-hourglass-o"></i> hourglass-o</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-hourglass-start"></i> hourglass-start</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-houzz"></i> houzz</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-i-cursor"></i> i-cursor</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-industry"></i> industry</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-internet-explorer"></i> internet-explorer</a></div>

                                <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-map"></i> map</a></div>
                               <div class="fa-hover col-md-3 col-sm-4"><a href="javascript:void(0)" onclick="chooseYs(this)" ><i class="fa fa-black-tie"></i> black-tie</a></div>
                             	
                              </td>
                              </tr>
		

	</div>

</body>
</html>