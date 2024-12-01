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
  
	function searchFwzyFFffcshis(){
		
		$('#dg').datagrid('load',{
			
			fhcsm:$("#fhcsmindex").val(),
			optTime:$("#optTime").datetimebox('getValue'), 
			optType:$("#optType").combobox("getValue")
		});
	}
	
	function czformat(value, row, index){
		if(row.optType=='C'){
			return "新增" ;
		}
if(row.optType=='U'){
	return "更新" ;
		}
if(row.optType=='D'){
	return "删除" ;
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

       
      
         <table id="dg"  class="easyui-datagrid" fitColumns="true"  singleSelect="true" pagination="true" rownumbers="true" url="${ctx}/fwFFfhcsManage/listHis?fwbs=${fwbs}" fit="true" toolbar="#tb">
                <thead>
                    <tr>
                   <th data-options="field:'optTime'" align="center" formatter="dateformat"  >操作日期</th>
                   <th data-options="field:'optType'" align="center" formatter="czformat" >操作类型</th>
    		      <th field="fwbs"  align="center">服务标识</th>
    		     <th field="fwmc"  align="center">服务名称</th>
    		     <th field="ffbs"  align="center">方法标识</th>
    		     <th field="ffmc"  align="center">方法名</th>
    		      <th field="fhcsbs"  align="center">返回参数标识</th>
    		      <th field="fhcsm"  align="center">返回参数名</th>
    		      <th field="fhcslx"  align="center">返回参数类型</th>
    		       <th field="fhcscd"  align="center">返回参数长度</th>
    		       <th field="fhcszy"  align="center">返回参数值域</th>
                    </tr>
                </thead>
            </table>
       <div id="tb">
	
	<div>
		&nbsp;返回参数名称：&nbsp;<input type="text" class="easyui-textbox"  name="fhcsmindex" id="fhcsmindex" size="20" />
		操作日期:<input class="easyui-datebox"  id="optTime" ></input> 
        &nbsp; 操作类型：&nbsp;
		<select class="easyui-combobox" name="optType" id="optType"  labelPosition="top"  size="20"  editable=false panelHeight='auto'>
		        <option value="">请选择...</option>
				<option value="C">新增</option>
			    <option value="U">修改</option>
			    <option value="D">删除</option>
				
		</select>
		<a href="javascript:searchFwzyFFffcshis()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>

	</div>
</div>

    
   
</body>
</html>