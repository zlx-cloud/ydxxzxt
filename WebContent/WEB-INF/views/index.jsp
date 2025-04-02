<%@page import="java.util.Date,java.text.DateFormat"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="${ctx}/static/common/css/bootstrap.min.css-v=3.3.5.css" rel="stylesheet">
<link href="${ctx}/static/common/css/font-awesome.min.css-v=4.4.0.css" rel="stylesheet">
<link href="${ctx}/static/common/css/animate.min.css" rel="stylesheet">
<link href="${ctx}/static/common/css/style.min.css-v=4.0.0.css" rel="stylesheet">
<link href="${ctx}/static/easyui/themes/default/easyui.css" rel="stylesheet">
<link href="${ctx}/static/easyui/themes/icon.css" rel="stylesheet">
<title>北京市公安局移动信息资源服务子系统</title>
<link rel="shortcut icon" href="${ctx}/static/images/favicon.ico">
<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		queryCount();
		setInterval(queryCount, 60000);
	});

	function queryCount() {
		$.ajax({
			url : "${ctx}/temporary/queryWarnCount",
			type : 'GET',
			success : function(data) {
				document.getElementById("warnCount").innerHTML = data;
			}
		});
	}

	function showList() {
		$("#dlg").dialog("open").dialog("setTitle", "预警信息");
		
		var options = $('#dg').datagrid('options');
		options.url ="${ctx}/temporary/jkbjData";
		$('#dg').datagrid(options);
		
		document.getElementById("warnCount").innerHTML = '0';
	}
	
	function closeRoleSaveDialog() {
		$("#dlg").dialog("close");
	}
</script>
<style>
.LOGO {
	width: 32px;
	height: 32px;
	float: left;
	margin: 10px 10px 0 36px;
	background: url(/ydxxzxt/static/images/favicon.ico) no-repeat center;
}

a.dropdown-toggle.count-info.warn-bell {
	background-color: #2f4050;
}
</style>
</head>

<body class="fixed-sidebar full-height-layout gray-bg"
	style="overflow: hidden">
	<!-- 顶部标题栏开始  -->
	<div class="row border-bottom">
		<nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0;background-color:#2f4050;">
		<div class="navbar-header">
			<div class="LOGO"></div>
			<h3 style="margin-top: 20px; color: #FFF;">北京市公安局移动信息资源服务子系统</h3>
		</div>
		<ul class="nav navbar-top-links navbar-right" style="margin-right: 20px;">
			<li>
				<a class="dropdown-toggle count-info warn-bell" style="color: #FFF;">
					<i class="fa fa-bell" onclick="showList()" href="javascript:void(0)"">&nbsp;
						<span style="color: red; font-weight: 600; font-size: 15px;" id="warnCount">0</span>
					</i>
				</a>
			</li>
			<li>
				<a data-toggle="dropdown" class="dropdown-toggle count-info warn-bell" href="#">
					<span class="text-muted text-xs block" style="color: #FFF;">
						<shiro:principal property="name"></shiro:principal>
						<b class="caret"></b>
					</span>
				</a>
				<ul class="dropdown-menu animated fadeInRight m-t-xs">
					<li><a class="J_menuItem" href="${ctx}/userT/userDetail">个人信息</a></li>
				</ul></li>
			<li class="dropdown">
				<a class="dropdown-toggle count-info warn-bell" href="${ctx}/logout" style="color: #FFF;">
					<i class="fa fa-power-off"></i>
				</a>
			</li>
		</ul>
		</nav>
	</div>
	<!-- 顶部标题栏结束  -->
	<div id="wrapper">
		<!--左侧导航开始-->
		<nav class="navbar-default navbar-static-side" role="navigation">
		<a id="toggleNav" class="navbar-minimalize minimalize-styl-2 btn btn-primary "
			style="position: absolute; top: -5px; right: 12px; z-index: 9999;">
			<i class="fa fa-bars"></i>
		</a>
		<div class="nav-close">
			<i class="fa fa-times-circle"></i>
		</div>
		<div class="sidebar-collapse">
			<ul class="nav" id="side-menu">
				<li>
					<a class="J_menuItem" href="shouye">
						<i class="fa fa-home"></i>
						<span class="nav-label">主页</span>
					</a>
				</li>
				<c:forEach items="${userMenuList }" var="menu" varStatus="status">
					<!-- 一级子菜单没有parentId,有url -->
					<c:if test="${empty menu.children}">
						<li>
							<a class="J_menuItem" href="<c:url value='${menu.menupath}'/>">
								<i class="${menu.iconcls}"></i>
								<span class="nav-label">${menu.menuname}</span>
							</a>
						</li>
					</c:if>
					<!-- 可展开的一级菜单，没有parentid,有url -->
					<c:if test="${not empty menu.children}">
						<li>
							<a href="#">
							<i class="${menu.iconcls}"></i>
							<span class="nav-label">${menu.menuname}</span>
							<span class="fa arrow"></span>
						</a>
							<ul class="nav nav-second-level">
								<!-- 没有url的是三级菜单，有url的直接输出到li中 -->
								<c:forEach items="${menu.children}" var="secondChild" varStatus="status">
									<c:if test="${ empty secondChild.children}">
										<li>
											<a class="J_menuItem" href="<c:url value='${secondChild.menupath}'/>">
												<i class="${secondChild.iconcls }"></i>${secondChild.menuname}
											</a>
										</li>
									</c:if>
									<!-- 二级菜单url为空，表示还有三级菜单 -->
									<c:if test="${not empty secondChild.children }">
										<li>
											<a href="#">
												<i class="${secondChild.iconcls }"></i>${secondChild.menuname }
												<span class="fa arrow"></span>
											</a>
											<ul class="nav nav-third-level">
												<c:forEach items="${secondChild.children}" var="thirdChild" varStatus="status">
													<li>
														<a class="J_menuItem" href="<c:url value='${thirdChild.menupath }'/>">
															<i class="${thirdChild.iconcls }"></i>${thirdChild.menuname }
														</a>
													</li>
												</c:forEach>
											</ul></li>
									</c:if>
								</c:forEach>
							</ul></li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		</nav>
		<!--左侧导航结束-->
		<!--右侧部分开始-->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<div class="row content-tabs">
				<button class="roll-nav roll-left J_tabLeft">
					<i class="fa fa-backward"></i>
				</button>
				<nav class="page-tabs J_menuTabs">
				<div class="page-tabs-content">
					<a href="javascript:;" class="active J_menuTab" data-id="shouye">首页</a>
				</div>
				</nav>
				<button class="roll-nav roll-right J_tabRight" style="right: 80px;">
					<i class="fa fa-forward"></i>
				</button>
				<div class="btn-group roll-nav roll-right" style="right: 0px;">
					<button class="dropdown J_tabClose" data-toggle="dropdown">
						关闭操作<span class="caret"></span>
					</button>
					<ul role="menu" class="dropdown-menu dropdown-menu-right">
						<li class="J_tabShowActive"><a>定位当前选项卡</a></li>
						<li class="divider"></li>
						<li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
						<li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
					</ul>
				</div>
			</div>
			<div class="row J_mainContent" id="content-main">
				<iframe class="J_iframe" name="iframe0" width="100%" height="100%"
					src="${ctx}/tjReport/tjfxNew" frameborder="0" data-id="shouye"
					seamless></iframe>
			</div>
			<div class="footer">
				<div class="pull-right">&copy; 2018-2019</div>
			</div>
		</div>
		<!--右侧部分结束-->
	</div>

	<div id="dlg" class="easyui-dialog" style="width: 910px;padding: 10px 20px" closed="true" buttons="#dlg-buttons">
		<table id="dg" class="easyui-datagrid" fitColumns="true" rownumbers="true" url="">
			<thead>
				<tr>
					<th field="YYBS" align="center">应用标识</th>
					<th field="YYMC" align="center">应用名称</th>
					<th field="FWBS" align="center">服务标识</th>
					<th field="FWMC" align="center">服务名称</th>
					<th field="FFBS" align="center">方法标识</th>
					<th field="FFMS" align="center">方法名称</th>
					<th field="WARN_TYPE" align="center" hidden="true">预警信息</th>
					<th field="WARN_NAME" align="center">预警信息</th>
					<th field="WARN_VALUE" align="center">预警值</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:closeRoleSaveDialog()" class="easyui-linkbutton"
			iconCls="icon-cancel">关闭</a>
	</div>
	<script src="${ctx}/static/common/js/bootstrap.min.js-v=3.3.5"></script>
	<script src="${ctx}/static/common/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="${ctx}/static/common/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<script src="${ctx}/static/common/js/plugins/layer/layer.min.js"></script>
	<script src="${ctx}/static/common/js/hplus.min.js-v=4.0.0"></script>
	<script type="text/javascript" src="${ctx}/static/common/js/contabs.min.js"></script>
	<script src="${ctx}/static/common/js/plugins/pace/pace.min.js"></script>
</body>

</html>