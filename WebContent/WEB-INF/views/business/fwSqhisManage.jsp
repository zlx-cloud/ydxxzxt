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
  
	function searchFwzysqhis(){
		
		$('#dg').datagrid('load',{
			
			ffmc:$("#ffmc").val(),
			czsj:$("#czsj").datetimebox('getValue'), 
			czls:$("#czls").combobox("getValue")
		});
	}
	
	function staformat(value, row, index){
		if(row.czls=='INSERT'){
			return "新增" ;
		}
if(row.czls=='UPDATE'){
	return "更新" ;
		}
if(row.czls=='DELETE'){
	return "取消" ;
}
		
		
	}
	function dateformat(value, row, index){
		if(value){ 
			
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
</script>
</head>
<body class="easyui-layout" >

       
      
          <table id="dg"  class="easyui-datagrid" fitColumns="true"  singleSelect="true" pagination="true" rownumbers="true" url="${ctx}/fwSqhisManage/list?fwbs=${fwbs}" fit="true" toolbar="#tb">
                <thead>
                    <tr>
                        <th data-options="field:'fwbs'"  align="center" >服务标识</th>
                        <th data-options="field:'fwmc'" align="center"  >服务名称</th>
                        <th data-options="field:'ffmc'" align="center" >方法名称</th>
                        <th data-options="field:'fwcyfYyxtmc'" align="center" >服务提供者名称</th>
                        <th data-options="field:'czsj'" align="center" formatter="dateformat"  >授权日期</th>
                        <th data-options="field:'czls'" align="center" formatter="staformat" >操作类型</th>
                       
                         
                    </tr>
                </thead>
            </table>
       <div id="tb">
	
	<div>
		&nbsp;方法名称：&nbsp;<input type="text" class="easyui-textbox" name="ffmc" id="ffmc" size="20" />
		授权日期:<input class="easyui-datebox"  id="czsj" ></input> 
        &nbsp; 操作类型：&nbsp;
		<select class="easyui-combobox" name="czls" id="czls"  labelPosition="top"  size="20"  editable=false panelHeight='auto'>
		        <option value="">请选择...</option>
				<option value="INSERT">新增</option>
			   
			    <option value="DELETE">取消</option>
				
		</select>
		<a href="javascript:searchFwzysqhis()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>

	</div>
</div>

    
   
</body>
</html>