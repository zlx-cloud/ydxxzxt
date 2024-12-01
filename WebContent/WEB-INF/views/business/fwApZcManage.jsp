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
			fwmc:$("#fwmc1").val(),
			fwbs:$("#fwbsindex").val(),
			fwztdm:$('#fwztdmindex').combobox("getValue")
		});
	}
	
	
	function staformat(value, row, index){
		return  row.fwztdm==1?"启用":"禁用" ;
	}
	
	  //格式化单元格提示信息  
    function formatCellTooltip(value){  
		  if(value==''||value==null){
			  return "";  
		  }
        return "<span title='" + value + "'>" + value + "</span>";  
    } 
   function openUploadDialog(){
 		$("#dlg").dialog("open").dialog("setTitle","服务注册信息");
 		
 	}
     function closeUploadDialog(){
 		$("#dlg").dialog("close");
 	}
     function uploadFiles() {
	     $('#uploadfm').form('submit', {                
	                 url: '${ctx}/uploadServe/impExcelFwZcSJ',
	                 success: function (result) {
	                      var result = eval('(' + result + ')');
	                      if(result.errorMsg){
	      					$.messager.alert('系统提示',"<font color=red>"+result.errorMsg+"</font>");
	      					return;
	      				}else{
	      					$.messager.alert('系统提示','上传成功');
	      					$("#dlg").dialog("close");
	      					$("#tree").treegrid("reload");
	      					$("#dg").datagrid("reload");
	      					
	      				}
	                  }
	              });
	}
     
     function updateStatus(fwbs,fwztdm){
    	 
    	  $.post("${ctx}/serveManage/updateStatus",{fwbs:fwbs,fwztdm:fwztdm},function(result){
			if(result.success){
				$.messager.alert('系统提示',"操作成功！");
				$("#dg").datagrid("reload");
				
			}else{
				$.messager.alert('系统提示','操作失败！');
			}
		},"json"); 
    	 
     }
     
 	function rowformat(value, row, index){
 		if(row.fwztdm=="1"){
 			return '<a class="editcls" onclick="updateStatus(\''+row.fwbs+'\',2)" href="javascript:void(0)">禁用</a>';
 		}else{
 			return '<a class="editcls" onclick="updateStatus(\''+row.fwbs+'\',1)" href="javascript:void(0)">启用</a>';
 		}
		
	}
 	var url;
 	function openFwzcModifyDialog(){
 		
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert('系统提示','请选择一条要编辑的数据！');
			return;
		}
		var row=selectedRows[0];
		$('#fwcyfYyxtmc').textbox('textbox').attr('readonly',true); 
		$("#addUpdate").dialog("open").dialog("setTitle","修改服务信息");
		$("#addUpdatefm").form("load",row);
		
		url="${ctx}/serveManage/add";
	}
	function openFwzcAddDialog(){
		
		$('#fwcyfYyxtmc').textbox('textbox').attr('readonly',true); 
		$("#addUpdate").dialog("open").dialog("setTitle","添加服务信息");
		$('#fwztdm').combobox("setValue","1")
		
		url="${ctx}/serveManage/add?flag=1";
	}
	function closeFwzcAddUpdateDialog(){
		$("#addUpdate").dialog("close");
		$("#addUpdatefm").form('clear');
	}
	
	function saveUpdateFwzc(){
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
					closeFwzcAddUpdateDialog();
					$("#dg").datagrid("reload");
				}
			}
		});
	}
	
	function openFwtgzDialog(){
		
		$("#fwtgzTree").dialog("open").dialog("setTitle","应用名称");
		var url="${ctx}/serveManage/fwzctree";
		
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
</script>
</head>
<body class="easyui-layout" >

       
        <div data-options="region:'center'">
          <table id="dg"  class="easyui-datagrid" fitColumns="true"  singleSelect="true" pagination="true" rownumbers="true" url="${ctx}/serveManage/list?flag=1" fit="true" toolbar="#tb">
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
                        <th data-options="field:'fwbbh'" align="center" hidden="true">版本</th>
                        <th data-options="field:'fwcyfYyxtmc'" align="center" formatter="formatCellTooltip" >应用名称</th>
                        <th data-options="field:'fwzxdz'" align="center" width="100" formatter="formatCellTooltip">服务总线地址</th>
                           <th data-options="field:'fwzykfYylxdm'" align="center" hidden="true">语言类型</th>
                        <th data-options="field:'fwnrBz'" align="center" width="60" formatter="formatCellTooltip" >服务内容</th>
                        <th data-options="field:'fwMs'" align="center" width="60" formatter="formatCellTooltip">服务描述</th>
                     
                       <!--  <th data-options="field:'x'"  formatter="rowformat" align="center" >编辑</th> -->
                    </tr>
                </thead>
            </table>
       <div id="tb">
	<div>
		<!-- <a href="javascript:openFwzcAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> -->
		<a href="javascript:openFwzcModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<!-- <a href="javascript:openUploadDialog()" class="easyui-linkbutton" iconCls="icon-upload-chart" plain="true">上传服务</a> -->
		<a href="${ctx}/serveManage/exportExcel" class="easyui-linkbutton" iconCls="icon-download-chart" plain="true">下载服务</a>
	</div>
	<div>
		&nbsp; 服务标识：&nbsp;<input type="text" class="easyui-textbox"  name="fwbsindex" id="fwbsindex" size="20" />
		&nbsp;服务名称：&nbsp;<input type="text" class="easyui-textbox"  name="fwmc" id="fwmc1" size="20" />
        &nbsp; 服务状态：&nbsp;
		<select class="easyui-combobox" name="fwztdmindex" id="fwztdmindex"  labelPosition="top"  size="20"  editable=false panelHeight='auto'>
		        <option value="">请选择...</option>
				<option value="1">启用</option>
			    <option value="2">禁用</option>
				
		</select>
		<a href="javascript:searchFwzyzcb()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
	
	</div>
</div>


<div id="dlg" class="easyui-dialog" style="width:100%;max-width:400px;padding:10px 60px;"
  closed="true" buttons="#dlg-buttons" closable="false">
  <form id="uploadfm" method="post" enctype="multipart/form-data">
		<div style="margin-bottom:20px">
			<input class="easyui-filebox" id="fileFwzc" name="fileFwzc" label="服务注册:" labelPosition="top" data-options="buttonText:'选择文件',prompt:'选择文件...'" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" style="width:100%"><a href="${ctx}/uploadServe/downLoadExcelModel?flag=fwzc">模板下载</a>
		</div>
		
			<input  type="file" style="display:none" id="fileFwff" name="fileFwff" />
		
		
			<input  type="file" style="display:none" id="fileFwqqcs" name="fileFwqqcs" />
		
	   
			<input  type="file" style="display:none" id="fileFwfhcs" name="fileFwfhcs"/>
		
		
		</form>
  </div>
  <div id="dlg-buttons">
	<a href="javascript:uploadFiles()"  class="easyui-linkbutton" data-options="iconCls:'icon-ok'" >Upload</a>
	<a href="javascript:closeUploadDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
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
  			<input class="easyui-textbox" id="fwcyfYyxtmc" name="fwcyfYyxtmc"  disabled="disabled" data-options="buttonText:'选择',onClickButton:openFwtgzDialog" required="true">
  			<input id="fwtgzYyxtbh" name="fwtgzYyxtbh"  type="hidden">
  			</td>
  			
  		
  			<td>服务名称：</td>
  			<td><input type="text" id="fwmc" name="fwmc" disabled="disabled" class="easyui-textbox" required="true"/></td>
  		</tr>
  		<tr>
  		     <td>服务入口地址列表：</td>
  			<td><input type="text" id="fwRkdzlb" name="fwRkdzlb" class="easyui-textbox" disabled="disabled" required="true"/></td>
  			 <td>服务状态：</td>
  			<td>
  			
                <select disabled="disabled" class="easyui-combobox"  name="fwztdm" id="fwztdm" style="width:100%;">
                <option value="1">启用</option><option value="2">禁用</option>
               </select>
            
  			
  			
  			</td>
  			
  		</tr>
  		<tr>
  		     <td>版本：</td>
  			<td><input type="text" id="fwbbh" disabled="disabled" name="fwbbh" class="easyui-textbox" required="true"/></td>
  			<td>语言类型：</td>
  			<td><input type="text" id="fwzykfYylxdm" disabled="disabled" name="fwzykfYylxdm" class="easyui-textbox" required="true"/></td>
  		</tr>
  		<tr>
  		    
  			<td>服务总线地址：</td>
  			<td>
  			
  			 <input class="easyui-combobox" id="fwzxdz"  disabled="disabled" name="fwzxdz" url="${ctx}/serveManage/zxdz" valueField="ZXDZ" panelWidth="350"  textField="TEXT" required="true" />
  			
  			
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
	<a href="javascript:saveUpdateFwzc()" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
	<a href="javascript:closeFwzcAddUpdateDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>

 <div id="fwtgzTree" class="easyui-dialog" style="width: 300px;height: 450px;padding: 10px 20px"
  closed="true" buttons="fwtgzTree-buttons" closable="true">
	<ul id="tree" class="easyui-tree"></ul>
</div>


 </div>
 
</body>
</html>