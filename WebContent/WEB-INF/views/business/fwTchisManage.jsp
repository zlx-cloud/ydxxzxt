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
<title>服务管理</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<%-- <script type="text/javascript" src="${ctx}/static/easyui/datagrid-detailview.js"></script> --%>
	<script type="text/javascript">
 
	function dateformat(value, row, index){
		if(value){ 
		        var year = value.substring(0,4);
		        //console.log(year);
		        var month = value.substring(4,6);
		        var date = value.substring(6,8);
		        var hour = value.substring(8,10);
		        var minute = value.substring(10,12);
		        var second = value.substring(12,14);
		        return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
			
		}
	}
	
	
	$(function () {
	    $('#dg').datagrid({
	    	loadMsg:'正在加载中，请稍等...',
	    	emptyMsg: '<span>没有相关记录！</span>'
	    });
	});
</script>
</head>
<body class="easyui-layout" >
          <table id="dg"  class="easyui-datagrid" fitColumns="true"  singleSelect="true" pagination="true" rownumbers="true" url="${ctx}/fwSqhisManage/faillist?fwbs=${fwbs}" fit="true" toolbar="#tb">
                <thead>
                    <tr>
                        <th data-options="field:'fwbs'"  width="100" align="center" >服务标识</th>
                        <th data-options="field:'tcsj'" width="100" align="center" formatter="dateformat"  >探测日期</th>
                    </tr>
                </thead>
            </table>
</body>
</html>