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
<title>服务方法返回参数注册</title>
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
			fwbs:$("#fwbsindex").val(),
			ffbs:$("#ffbsindex").val(),
			fhcsbs:$("#fhcsbssindex").val(),
			fwmc:$("#fwmcindex").val(),
			ffmc:$("#ffmcindex").val(),
			fhcsm:$("#fhcsmindex").val()
		});
	}
	
	
	
	
	
   function openUploadDialog(){
 		$("#dlg").dialog("open").dialog("setTitle","服务方法返回参数注册信息");
 		
 	}
     function closeUploadDialog(){
 		$("#dlg").dialog("close");
 	}
     function uploadFiles() {
    	     $('#fm').form('submit', {                
    	                 url: '${ctx}/uploadServe/impExcelFwZcSJ',
    	                 success: function (result) {
    	                      var result = eval('(' + result + ')');
    	                      if(result.errorMsg){
    	      					$.messager.alert('系统提示',"<font color=red>"+result.errorMsg+"</font>");
    	      					return;
    	      				}else{
    	      					$.messager.alert('系统提示','上传成功');
    	      					$("#dlg").dialog("close");
    	      					$("#dg").datagrid("reload");
    	      					
    	      					
    	      				}
    	                  }
    	              });
    	}
     
 
 	
 	var url;
 	function openFwzcModifyDialog(){
 		var savehtml="";
		$("#addUpdate-buttons").html('');
		savehtml+=" <a href='javascript:saveUpdateFwzc()' class='easyui-linkbutton' iconCls='icon-ok'>保存</a> ";
		savehtml+=" <a href='javascript:closeFwzcAddUpdateDialog()' class='easyui-linkbutton' iconCls='icon-cancel'>关闭</a> ";
		$("#addUpdate-buttons").append(savehtml);
		$.parser.parse();		
		
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert('系统提示','请选择一条要编辑的数据！');
			return;
		}
		var row=selectedRows[0];
		$("#addUpdate").dialog("open").dialog("setTitle","修改服务方法返回参数信息");
		$('#fhcsbs').textbox('textbox').attr('readonly',true); 
		$("#fhcsbs").textbox('textbox').css('background','#E0ECFF');
		$("#fwmc").textbox('textbox').css('background','#E0ECFF');
		$('#ffmc').textbox('textbox').attr('readonly',true); 
		$("#ffmc").textbox('textbox').css('background','#E0ECFF');
		$("#ffmc").textbox('disable');
		$("#addUpdatefm").form("load",row);
		
		url="${ctx}/fwFFfhcsManage/add";
	}
	function openFwzcAddDialog(){
		var savehtml="";
		$("#addUpdate-buttons").html('');
		savehtml+=" <a href='javascript:addxx()' class='easyui-linkbutton' iconCls='icon-add' plain='true'>保存并继续添加</a> ";
		savehtml+=" <a href='javascript:saveUpdateFwzc()' class='easyui-linkbutton' iconCls='icon-ok'>保存</a> ";
		savehtml+=" <a href='javascript:closeFwzcAddUpdateDialog()' class='easyui-linkbutton' iconCls='icon-cancel'>关闭</a> ";
		$("#addUpdate-buttons").append(savehtml);
		$.parser.parse();		
		
		$("#ffmc").textbox('enable');
		$('#fhcsbs').textbox('textbox').attr('readonly',false); 
		$("#fhcsbs").textbox('textbox').css('background','');
		$('#ffmc').textbox('textbox').attr('readonly',true); 
		$("#ffmc").textbox('textbox').css('background','');
		$("#fwmc").textbox('textbox').css('background','#E0ECFF');
		$("#addUpdate").dialog("open").dialog("setTitle","添加服务方法返回参数信息");
		
		url="${ctx}/fwFFfhcsManage/add?flag=1";
	}
	function closeFwzcAddUpdateDialog(){
		$("#addUpdate").dialog("close");
		$("#addUpdatefm").form('clear');
		$("#addUpdatefm").form('reset');
	}
	function addxx(){
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
					$.messager.alert('系统提示', '保存成功,请继续添加');
					$("#dg").datagrid("reload");
				}
			}
		});
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
	function deleteFwff(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
			$.messager.alert('系统提示','请选择要删除的数据！');
			return;
		}
	
		
		var fwbs=selectedRows[0].fwbs;
		var ffbs=selectedRows[0].ffbs;
		var fhcsbs=selectedRows[0].fhcsbs;
		$.messager.confirm("系统提示","您确认要删除这条数据吗？",function(r){
			if(r){
				$.post("${ctx}/fwFFfhcsManage/delete",{fwbs:fwbs,ffbs:ffbs,fhcsbs:fhcsbs},function(result){
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
	
function openFwzcqqfhDialog(){
		
		$("#fwzcqqfhTree").dialog("open").dialog("setTitle","方法信息");
		$("#tree").tree({
			lines:true,
		    checkbox:false,
			animate:true,
			url:'${ctx}/serveManage/tree?flag=fwzcffqqfh',
			onLoadSuccess:function(){
				$("#tree").tree('expandAll');
				
			},
			onClick:function(node){
				
				 var father = $(this).tree("getParent",node.target);
				 if(father==null){
					 $.messager.alert('系统提示','请选择方法！');
				 }else{
					 var twofather = $(this).tree("getParent",father.target);
					 if(twofather==null){
						 $.messager.alert('系统提示','请选择方法！');
					 }else{
						 var threefather = $(this).tree("getParent",twofather.target);
						 if(threefather==null){
							 $.messager.alert('系统提示','请选择方法！');
							/*  $.messager.alert('系统提示','请选择方法！');
							 $("#fwmc").textbox("setValue", node.text); 
							 $("#fwbs").val(node.id); 
							$("#fwzcTree").dialog("close"); */
						 }else{
							 var fourfather = $(this).tree("getParent",threefather.target);
							 if(fourfather==null){
								 $("#ffmc").textbox("setValue", node.text); 
								 $("#ffbs").val(node.id); 
								 $("#fwmc").textbox("setValue", father.text); 
								 $("#fwbs").val(father.id);
								 $("#fwzcqqfhTree").dialog("close"); 
							 }else{
							
							 $.messager.alert('系统提示','请选择方法！');
							 }
						 }
						
					 }
					 
					 
				 }

              
				 
			}
		});
	}
	
//url：窗口调用地址，title：窗口标题，width：宽度，height：高度，shadow：是否显示背景阴影罩层  ${ctx}/fwSqhisManage/show
function showMessageDialog() {  
	var selectedRows=$("#dg").datagrid('getSelections');
	if(selectedRows.length!=1){
		$.messager.alert('系统提示','请选择一条要查看历史授权的服务！');
		return;
	}
	var row=selectedRows[0];
	
	var url="${ctx}/fwFFfhcsManage/showHis?fwbs="+row.fwbs;
    var content = '<iframe src="' + url + '" width="100%" height="99%" frameborder="0" scrolling="no"></iframe>';  
    var boarddiv = '<div id="msgwindow" title="历史记录"></div>'//style="overflow:hidden;"可以去掉滚动条  
    $(document.body).append(boarddiv);  
    var win = $('#msgwindow').dialog({  
        content: content,  
        width: "800px",  
        height: "430px",  
        modal: true,  
        title: "历史记录",  
        onClose: function () {  
            $(this).dialog('destroy');//后面可以关闭后的事件  
        }  
    });  
    win.dialog('open');  
}
</script>
</head>
<body class="easyui-layout" >

       
        <div data-options="region:'center'">
          <table id="dg"  class="easyui-datagrid" fitColumns="true"  singleSelect="true" pagination="true" rownumbers="true" url="${ctx}/fwFFfhcsManage/list" fit="true" toolbar="#tb">
                <thead>
                    <tr>
                   
    		      <th field="fwbs"  align="center">服务标识</th>
    		     <th field="fwmc"  align="center">服务名称</th>
    		     <th field="ffbs"  align="center">方法标识</th>
    		     <th field="ffmc"  align="center">方法名称</th>
    		      <th field="fhcsbs"  align="center">返回参数标识</th>
    		      <th field="fhcsm"  align="center">返回参数名称</th>
    		      <th field="fhcslx"  align="center">返回参数类型</th>
    		       <th field="fhcscd"  align="center">返回参数长度</th>
    		       <th field="fhcszy"  align="center">返回参数值域</th>
                    </tr>
                </thead>
            </table>
       <div id="tb">
	<div>
		<a href="javascript:openFwzcAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="javascript:openFwzcModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<a href="javascript:deleteFwff()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		<a href="javascript:openUploadDialog()" class="easyui-linkbutton" iconCls="icon-upload-chart" plain="true">上传服务</a>
		<a href="${ctx}/fwFFfhcsManage/exportExcel" class="easyui-linkbutton" iconCls="icon-download-chart" plain="true">下载服务</a>
		<a href="javascript:showMessageDialog()" class="easyui-linkbutton" iconCls="icon-history-chart" plain="true">历史记录</a>
	</div>
	<div>
	
		&nbsp; 服务标识：&nbsp;<input type="text" class="easyui-textbox"  name="fwbsindex" id="fwbsindex" size="20" />
		&nbsp; 方法标识：&nbsp;<input type="text" class="easyui-textbox"  name="ffbsindex" id="ffbsindex" size="20" />
		&nbsp; 返回参数标识：&nbsp;<input type="text" class="easyui-textbox"  name="fhcsbssindex" id="fhcsbssindex" size="20" /></br>
		&nbsp; 服务名称：&nbsp;<input type="text" class="easyui-textbox"  name="fwmcindex" id="fwmcindex" size="20" />
		&nbsp; 方法名称：&nbsp;<input type="text" class="easyui-textbox"  name="ffmcindex" id="ffmcindex" size="20" />
       	&nbsp; 返回参数名称：&nbsp;<input type="text" class="easyui-textbox"  name="fhcsmindex" id="fhcsmindex" size="20" />
		<a href="javascript:searchFwzyzcb()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
	
	</div>
</div>


<div id="dlg" class="easyui-dialog" style="width:100%;max-width:400px;padding:10px 60px;"
  closed="true" buttons="#dlg-buttons" closable="false">
  <form id="fm" method="post" enctype="multipart/form-data">
		<div style="margin-bottom:20px">
			<input class="easyui-filebox" id="fileFwfhcs" name="fileFwfhcs" label="服务方法返回参数注册:" labelPosition="top" data-options="buttonText:'选择文件',prompt:'选择文件...'" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" style="width:100%"><a href="${ctx}/uploadServe/downLoadExcelModel?flag=fwfhcs">模板下载</a>
		</div>
		<input type="file" style="display:none" id="fileFwzc" name="fileFwzc" />
		
		
			<input type="file" style="display:none" id="fileFwff" name="fileFwff" />
		
		
			<input type="file" style="display:none" id="fileFwqqcs" name="fileFwqqcs" />
		
	   
			<!-- <input class="easyui-filebox" type="hidden" id="fileFwfhcs" name="fileFwfhcs"/> -->
		</form>
  </div>
  <div id="dlg-buttons">
	<a href="javascript:uploadFiles()"  class="easyui-linkbutton" data-options="iconCls:'icon-ok'" >上传</a>
	<a href="javascript:closeUploadDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>



<div id="addUpdate" class="easyui-dialog" style="width: 620px;height: 250px;padding: 10px 20px" closable="false"
  closed="true" buttons="#addUpdate-buttons">
  <form id="addUpdatefm" method="post">
  	<table cellspacing="5px;">
  		<tr>
  	        <td>方法名称：</td>
  			<td>
  			<input type="hidden" id="ffbs" name="ffbs" />
  			<input class="easyui-textbox" id="ffmc" name="ffmc"  data-options="buttonText:'选择',onClickButton:openFwzcqqfhDialog" required="true">
  			
  			
  			</td>
  			<td>服务名称：</td>
  			<td>
  			
  			<input type="text" id="fwmc" name="fwmc"  readonly="readonly" class="easyui-textbox" required="true"/>
  			<input id="fwbs" name="fwbs"  type="hidden">
  			</td>
  		
  			
  		</tr>
  		<tr>
  		     <td>返回参数标识：</td>
  			<td><input type="text" id="fhcsbs" name="fhcsbs" class="easyui-textbox" required="true"/></td>
  			<td>返回参数名称：</td>
  			<td><input type="text" id="fhcsm" name="fhcsm" class="easyui-textbox" required="true"/></td>
  		</tr>
  		<tr>
  		   <td>返回参数类型：</td>
  			<td><input type="text" id="fhcslx" name="fhcslx" class="easyui-textbox" required="true" value="String"/></td>
  			<td>返回参数长度：</td>
  			<td><input type="text" id="fhcscd" name="fhcscd" class="easyui-textbox" required="true"/></td>
  		</tr>
  		<tr>
  		   <td>返回参数值域：</td>
  			<td><input type="text" id="fhcszy" name="fhcszy" class="easyui-textbox" /></td>
  			<td></td>
  			<td></td>
  		</tr>
  	</table>
  </form>
</div>

<div id="addUpdate-buttons">
<!-- 	<a href="javascript:addxx()" class="easyui-linkbutton" iconCls="icon-add" plain="true">保存并继续添加</a> -->
<!-- 	<a href="javascript:saveUpdateFwzc()" class="easyui-linkbutton" iconCls="icon-ok" >保存</a> -->
<!-- 	<a href="javascript:closeFwzcAddUpdateDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a> -->
</div>


<div id="fwzcqqfhTree" class="easyui-dialog" style="width: 300px;height: 450px;padding: 10px 20px"
  closed="true" buttons="fwtgzTree-buttons" closable="true">
	<ul id="tree" class="easyui-tree"></ul>
</div>



    </div>
 
</body>
</html>