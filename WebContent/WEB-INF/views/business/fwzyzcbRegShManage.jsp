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
<title>服务注册</title>
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

	function searchFwzyzcb(){
		
		$('#dg').datagrid('load',{
			fwmc:$("#s_fwmc").val(),
			fwbs:$("#fwbsindex").val(),
			status:$('#statusindex').combobox("getValue")
		});
	}
	
	
	function staformat(value, row, index){
		return  row.fwztdm==1?"启用":"禁用" ;
	}

	function stashformat(value, row, index){
		if(row.status=='1'){
			
			return "<font color=red>审核中</font>" ;
			}
			if(row.status=='0'){
				return "通过" ;
			}
			if(row.status=='2'){
				return "未通过" ;
			}
	}
	  //格式化单元格提示信息  
    function formatCellTooltip(value){  
		  if(value==''||value==null){
			  return "";  
		  }
        return "<span title='" + value + "'>" + value + "</span>";  
    } 


	function rowformat(value, row, index){
 		if(row.status=="1"){
 			return '<a class="editcls" onclick="updateStatus(\''+row.fwbs+'\',0,\''+row.fwmc+'\')" href="javascript:void(0)">通过</a>&nbsp;&nbsp;<a class="editcls" onclick="updateStatus(\''+row.fwbs+'\',2)" href="javascript:void(0)">退回</a>';
 		}
		
	}
	
	   function updateStatus(fwbs,status,fwmc){
	    	 
	    	  $.post("${ctx}/fwzyzcbRegManage/updateStatus",{fwbs:fwbs,status:status,fwmc:fwmc},function(result){
				if(result.success){
					if(status=='0'){
						
					$.messager.alert('系统提示',"审核通过！");
					}else{
						
						$.messager.alert('系统提示',"审核未通过！");
					}
					$("#dg").datagrid("reload");
					
				}else{
					$.messager.alert('系统提示','操作失败！');
				}
			},"json"); 
	    	 
	     }
 
 	
 	

	
	

	
	function openFwtgzDialog(){
		
		$("#fwtgzTree").dialog("open").dialog("setTitle","应用名称");
		var url="${ctx}/serveManage/tree?flag=fwRegzc";
		
		$("#tree").tree({
			lines:true,
			url:url,
			checkbox:false,
			animate:true,
			onLoadSuccess:function(){
				$("#tree").tree('expandAll');
			},
		onClick: function(node){
			 var father = $(this).tree("getParent",node.target);
			 if(father==null){
				 $.messager.alert('系统提示','请选择应用名称！');
				
			 }else{
				 var father = $(this).tree("getParent",node.target);
				 $("#fwcyfYyxtmc").textbox("setValue", node.text); 
				 $("#fwtgzYyxtbh").val(node.id); 
		         $("#jgbs").val(father.id); 
			$("#fwtgzTree").dialog("close");
			 }
			
		}
			
		});
	}
	
	function deleteFwReg(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
			$.messager.alert('系统提示','请选择要删除的数据！');
			return;
		}
	
		
		var fwbs=selectedRows[0].fwbs;
		
		$.messager.confirm("系统提示","您确认要删除这条数据吗？",function(r){
			if(r){
				$.post("${ctx}/fwzyzcbRegManage/delete",{fwbs:fwbs},function(result){
					if(result.success){
						$.messager.alert('系统提示',"您已成功删除此条数据！");
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert('系统提示',result.errorMsg);
					}
				},"json");
			}
		});
	}
</script>
</head>
<body class="easyui-layout" >

       
        <div data-options="region:'center'">
          <table id="dg"  class="easyui-datagrid" fitColumns="true"  singleSelect="true" pagination="true" rownumbers="true" url="${ctx}/fwzyzcbRegManage/list" fit="true" toolbar="#tb">
                <thead>
                    <tr>
                     <!--  <th field="cb" checkbox="true" align="center"></th> -->
                        <th data-options="field:'fwbs'"  align="center" formatter="formatCellTooltip">服务标识</th>
                        <th data-options="field:'fwmc'" align="center"  formatter="formatCellTooltip" >服务名称</th>
                        <th data-options="field:'fwRkdzlb'" align="center" width="100"  formatter="formatCellTooltip">服务入口地址列表</th>
                      <!--    <th data-options="field:'fwIpdz'"  align="center" >服务入口地址(IP)</th>
                        <th data-options="field:'fwZxdkhm'"  align="center" >服务端口号</th> 
                        <th data-options="field:'fwLj'" align="center"  formatter="formatCellTooltip">服务路径</th> -->
                        <th data-options="field:'fwztdm'"  formatter="staformat" align="center" formatter="formatCellTooltip">服务状态</th>
                        <th data-options="field:'fwbbh'" align="center" formatter="formatCellTooltip" >版本</th>
                        <th data-options="field:'fwcyfYyxtmc'" align="center" formatter="formatCellTooltip" >应用名称</th>
                        <th data-options="field:'fwzxdz'" align="center" width="100" formatter="formatCellTooltip">服务总线地址</th>
                           <th data-options="field:'fwzykfYylxdm'" align="center" formatter="formatCellTooltip" >语言类型</th>
                        <th data-options="field:'fwnrBz'" align="center" width="60" formatter="formatCellTooltip" >服务内容</th>
                        <th data-options="field:'fwMs'" align="center" width="60" formatter="formatCellTooltip">服务描述</th>
                         <th data-options="field:'status'"  formatter="stashformat" align="center" >申请状态</th>
                     
                        <th data-options="field:'x'"  formatter="rowformat" align="center" >编辑</th>
                    </tr>
                </thead>
            </table>
       <div id="tb">
	<div>
		
		<a href="javascript:deleteFwReg()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		
	</div>
	<div>
		&nbsp; 服务标识：&nbsp;<input type="text" class="easyui-textbox"  name="fwbsindex" id="fwbsindex" size="20" />
		&nbsp;服务名称：&nbsp;<input type="text" class="easyui-textbox"  name="s_fwmc" id="s_fwmc" size="20" />
        &nbsp; 申请状态：&nbsp;
		<select class="easyui-combobox" name="statusindex" id="statusindex"  labelPosition="top"  size="20"  editable=false panelHeight='auto'>
		        <option value="">请选择...</option>
		         <option value="0">通过</option>
				<option value="1">审核中</option>
			    <option value="2">未通过</option>
				
		</select>
		<a href="javascript:searchFwzyzcb()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
	
	</div>
</div>





<div id="addUpdate" class="easyui-dialog" style="width: 680px;height: 480px;padding: 10px 20px" closable="false"
  closed="true" buttons="#addUpdate-buttons">
  <form id="addUpdatefm" method="post">
  	<table cellspacing="5px;">
  	<tr>
  			<!-- <td>服务标识：</td>
  			<td><input type="text" id="fwbs" name="fwbs" class="easyui-textbox" required="true"/>
  			</td> -->
  			<input  type="hidden" id="fwbs" name="fwbs" />
  			<input  type="hidden" id="jgbs" name="jgbs" />
  			<td>应用名称：</td>
  			<td><!-- input type="text" id="fwtgzYyxtbh" name="fwtgzYyxtbh"  size="20"  class="easyui-textbox" required="true"/> -->
  			<input class="easyui-textbox" id="fwcyfYyxtmc" name="fwcyfYyxtmc"   data-options="buttonText:'选择',onClickButton:openFwtgzDialog" required="true">
  			<input id="fwtgzYyxtbh" name="fwtgzYyxtbh"  type="hidden">
  			</td>
  			
  		
  			<td>服务名称：</td>
  			<td><input type="text" id="fwmc" name="fwmc" class="easyui-textbox" required="true"/></td>
  		</tr>
  		<tr>
  		     <td>服务入口地址列表：</td>
  			<td><input type="text" id="fwRkdzlb" name="fwRkdzlb" class="easyui-textbox"  required="true"/></td>
  			 <td>服务状态：</td>
  			<td>
  			
                <select class="easyui-combobox"  name="fwztdm" id="fwztdm" style="width:100%;">
                <option value="1">启用</option><option value="2">禁用</option>
               </select>
            
  			
  			
  			</td>
  			
  		</tr>
  		<tr>
  		     <td>版本：</td>
  			<td><input type="text" id="fwbbh"  name="fwbbh" class="easyui-textbox" value="V1.0" required="true"/></td>
  			<td>语言类型：</td>
  			<td><input type="text" id="fwzykfYylxdm"  name="fwzykfYylxdm" class="easyui-textbox" value="JAVA" required="true"/></td>
  		</tr>
  		<tr>
  		    
  			<td>服务总线地址：</td>
  			<td>
  			
  			 <input class="easyui-combobox" id="fwzxdz"  name="fwzxdz" url="${ctx}/fwzyzcbRegManage/zxdz" valueField="ZXDZ" panelWidth="350"  textField="TEXT" required="true" />
  			
  			
  			<!-- <input type="text" id="fwzxdz" name="fwzxdz"  class="easyui-textbox" required="true"/> --></td>
  			
  		</tr>
  	
  		
  		<tr>
  			<td valign="top">服务内容：</td>
  			<td colspan="4">
  				<textarea rows="7" cols="64" name="fwnrBz" id="fwnrBz"></textarea>
  			</td>
  		</tr>
  		<tr>
  			<td valign="top">服务描述：</td>
  			<td colspan="4">
  				<textarea rows="7" cols="64" name="fwMs" id="fwMs"></textarea>
  			</td>
  		</tr>
  	</table>
  </form>
</div>

<div id="addUpdate-buttons">
	<a href="javascript:saveUpdateFwzc()" class="easyui-linkbutton" iconCls="icon-ok" >提交</a>
	<a href="javascript:closeFwzcAddUpdateDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>

 <div id="fwtgzTree" class="easyui-dialog" style="width: 300px;height: 450px;padding: 10px 20px"
  closed="true" buttons="fwtgzTree-buttons" closable="true">
	<ul id="tree" class="easyui-tree"></ul>
</div>


 </div>
 
</body>
</html>