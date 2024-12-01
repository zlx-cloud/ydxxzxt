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
<title>服务方法注册</title>
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
<!-- <style type="text/css">
 .radioSpan {
      position: relative;
      border: 1px solid #95B8E7;
      background-color: #fff;
      vertical-align: middle;
      display: inline-block;
      overflow: hidden;
      white-space: nowrap;
      margin: 0;
      padding: 0;
      -moz-border-radius: 5px 5px 5px 5px;
      -webkit-border-radius: 5px 5px 5px 5px;
      border-radius: 5px 5px 5px 5px;
      display:block;
    }

</style> -->
	<script type="text/javascript">

	function searchFwzyzcb(){
		
		$('#dg').datagrid('load',{
			fwbs:$("#fwbsindex").val(),
			fwmc:$("#fwmcindex").val(),
			ffbs:$("#ffbsindex").val(),
			ffmc:$("#ffmcindex").val(),
			jzfl:$("#jzflindex").val(),
			jzfl:$("#jzflindex").val(),
			fwtgzYyxtbh:$("#fwcyffwtgzYyxtbh").val(),
			sfhcsj:$('#sfhcsjindex').combobox('getValue')
			
		});
	}
	

	
	
	
   function openUploadDialog(){
 		$("#dlg").dialog("open").dialog("setTitle","服务方法注册信息");
 		
 	}
     function closeUploadDialog(){
 		$("#dlg").dialog("close");
 	}
     function uploadFiles() {
    	     $('#fm').form('submit', {                
    	                 url: '${ctx}/uploadServe/impExcelFwZcSJ',
    	                 success: function (result) {
    	                      //var result = eval('(' + result + ')');
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
		$('#fwmc').textbox('textbox').attr('readonly',true); 
	
		$("#fwmc").textbox('textbox').css('background','#E0ECFF');
		$("#fwmc").textbox('disable')
		/* $("#fwmc").combobox({disabled: true});  */
		var row=selectedRows[0];
		$("#addUpdate").dialog("open").dialog("setTitle","修改服务方法信息");
		 
		$("#addUpdatefm").form("load",row);
		
		url="${ctx}/fwFFManage/add";
	}
	function openFwzcAddDialog(){
		var savehtml="";
		$("#addUpdate-buttons").html('');
		savehtml+=" <a href='javascript:addxx()' class='easyui-linkbutton' iconCls='icon-add' plain='true'>保存并继续添加</a> ";
		savehtml+=" <a href='javascript:saveUpdateFwzc()' class='easyui-linkbutton' iconCls='icon-ok'>保存</a> ";
		savehtml+=" <a href='javascript:closeFwzcAddUpdateDialog()' class='easyui-linkbutton' iconCls='icon-cancel'>关闭</a> ";
		$("#addUpdate-buttons").append(savehtml);
		$.parser.parse();		

		$("#fwmc").textbox('enable');
		$('#fwmc').textbox('textbox').attr('readonly',true); 
		$('#jzfl').textbox('textbox').attr('readonly',true); 
		$('#czfl').textbox('textbox').attr('readonly',true); 
		$("#fwmc").textbox('textbox').css('background','')
		$("#addUpdate").dialog("open").dialog("setTitle","添加服务方法信息");
		
		url="${ctx}/fwFFManage/add?flag=1";
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
		$.messager.confirm("系统提示","您确认要删除这条数据吗？",function(r){
			if(r){
				$.post("${ctx}/fwFFManage/delete",{fwbs:fwbs,ffbs:ffbs},function(result){
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
	

	function openFwtgzDialog(){
		
		$("#fwzcTree").dialog("open").dialog("setTitle","服务信息");
		$("#tree").tree({
			lines:true,
		    checkbox:false,
			animate:true,
			url:'${ctx}/serveManage/tree?flag=fwzc',
			onLoadSuccess:function(){
				$("#tree").tree('collapseAll');
				
			},
			onClick:function(node){
				
				 var father = $(this).tree("getParent",node.target);
				 if(father==null){
					 $.messager.alert('系统提示','请选择服务！');
				 }else{
					 var twofather = $(this).tree("getParent",father.target);
					 if(twofather==null){
						 $.messager.alert('系统提示','请选择服务！');
					 }else{
						 var threefather = $(this).tree("getParent",twofather.target);
						 if(threefather==null){
							 $("#fwmc").textbox("setValue", node.text); 
							 $("#fwbs").val(node.id); 
							$("#fwzcTree").dialog("close");
						 }else{
							 
							
							 $.messager.alert('系统提示','请选择服务！');
						 }
						
					 }
					 
					 
				 }

              
				 
			}
		});
	}
	 $(function(){
		 $('#sjyxsj').textbox('textbox').attr('readonly',true); 
		  $(":radio").click(function(){
			  if($(this).val()=='1'){
				  $("#sjyxsj").textbox("setValue","");
				  $('#sjyxsj').textbox('textbox').attr('readonly',false); 
			  }else{
				  $("#sjyxsj").textbox("setValue","0");
				  $('#sjyxsj').textbox('textbox').attr('readonly',true); 
				 
			  }
		  });
		 });
	 
	 function staformat(value, row, index){
		 if(row.czfl=='R'){
			 return "查询";
		 }if(row.czfl=='C'){
			 return "新增";
		 }if(row.czfl=='U'){
			 return "更新";
		 }if(row.czfl=='D'){
			 return "删除";
		 }
		 
		
		}
	 function format(value, row, index){
		 if(row.sfhcsj=='1'){
			 return "是";
		 }if(row.sfhcsj=='0'){
			 return "否";
		 }
			
		}
	 
	 function wlflformat(value, row, index){
		 //console.log(row);
		 if(row.wlfl=='1'){
			 return "一类网";
		 }else if(row.wlfl=='2'){
			 return "二类网";
		 }else if(row.wlfl=='3'){
			 return "三类网";
		 }
			
	 }
	 
	 function fflbformat(value, row, index){
		 if(row.fflb=='1'){
			 return "公开";
		 }else if(row.fflb=='2'){
			 return "受控";
		 }else if(row.fflb=='3'){
			 return "私有";
		 }
			
		}
	  //格式化单元格提示信息  
     function formatCellTooltip(value){  
		  if(value==''||value==null){
			  return "";  
		  }
         return "<span title='" + value + "'>" + value + "</span>";  
     } 
	  
 	//url：窗口调用地址，title：窗口标题，width：宽度，height：高度，shadow：是否显示背景阴影罩层  ${ctx}/fwSqhisManage/show
 	function showMessageDialog(shadow) {  
 		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert('系统提示','请选择一条要查看历史授权的服务！');
			return;
		}
		var row=selectedRows[0];
 		
 		var url="${ctx}/fwFFManage/showHis?fwbs="+row.fwbs;
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
          <table id="dg"  class="easyui-datagrid" fitColumns="true"  singleSelect="true" pagination="true" rownumbers="true" url="${ctx}/fwFFManage/list" fit="true" toolbar="#tb">
                <thead>
                    <tr>
                   
    		      <th field="fwbs"  align="center">服务标识</th>
    		       <th field="fwmc"  align="center" >服务名称</th>
    		     <th field="ffbs"  align="center">方法标识</th>
    		      <th field="ffmc"  align="center" >方法名称</th>
    		      <th field="ffl"  align="center" formatter="formatCellTooltip" >方法类</th>
    		      <th field="jzlbmc"  align="center" >警种分类</th>
    		      <th field="czfl" formatter="staformat" align="center">操作分类</th>
    		      <th field="sfhcsj" formatter="format" align="center" >是否缓存</th>
    		      <th field="wlfl" formatter="wlflformat" align="center" >网络分类</th>
    		      <th field="fflb" formatter="fflbformat" align="center" >方法类别</th>
    		      <th field="sjyxsj"  align="center" >缓存时间(秒)</th>
    		      <th field="ffms"  align="center" formatter="formatCellTooltip">方法描述</th>
    		      <th field="url"  align="center" >url</th>
    		      <th field="fflx"  align="center" >接口方法类型</th>
                    </tr>
                </thead>
            </table>
       <div id="tb">
	<div>
		<a href="javascript:openFwzcAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="javascript:openFwzcModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<a href="javascript:deleteFwff()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		<a href="javascript:openUploadDialog()" class="easyui-linkbutton" iconCls="icon-upload-chart" plain="true">上传服务</a>
		<a href="${ctx}/fwFFManage/exportExcel" class="easyui-linkbutton" iconCls="icon-download-chart" plain="true">下载服务</a>
		<a href="${ctx}/fwFFManage/yyFwFfExport" class="easyui-linkbutton" iconCls="icon-download-chart" plain="true">应用服务方法</a>
		<a href="javascript:showMessageDialog()" class="easyui-linkbutton" iconCls="icon-history-chart" plain="true">历史记录</a>
	</div>
	<div>
	
		&nbsp; 服务标识：&nbsp;<input type="text" class="easyui-textbox"  name="fwbsindex" id="fwbsindex" size="15" />
		&nbsp; 方法标识：&nbsp;<input type="text" class="easyui-textbox"  name="ffbsindex" id="ffbsindex" size="15" />
       	&nbsp; 警种分类：&nbsp;<input class="easyui-combobox" id="jzflindex"   name="jzflindex" url="${ctx}/fwFFManage/jzfl" valueField="jzlbdm"   textField="jzlbmc"  panelHeight="200" size="15"/>
       	&nbsp; 是否缓存数据：&nbsp;<select class="easyui-combobox" name="sfhcsjindex" id="sfhcsjindex"  panelHeight="auto" style="width:8%">
  			  <option value="">请选择...</option>
              <option value="0">否</option><option value="1">是</option>
               </select></br>
       	&nbsp; 服务名称：&nbsp;<input type="text" class="easyui-textbox"  name="fwmcindex" id="fwmcindex" size="15" />
       	&nbsp; 方法名称：&nbsp;<input type="text" class="easyui-textbox"  name="ffmcindex" id="ffmcindex" size="15" />
       	
       	<c:if test="${users == 2}">

   			&nbsp;应用名称：&nbsp;
			<select name="fwcyffwtgzYyxtbh" id="fwcyffwtgzYyxtbh" class="easyui-combobox">
				<option value="">请选择</option>
				<c:forEach items="${fwcyf}" var="zcxx" varStatus="index">
					<option value="${zcxx.fwcyfYyxtbh}">${zcxx.fwcyfYyxtmc}</option>
				</c:forEach>
			</select>


		</c:if>
											
    
		<a href="javascript:searchFwzyzcb()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		
	</div>
</div>


<div id="dlg" class="easyui-dialog" style="width:100%;max-width:400px;padding:10px 60px;"
  closed="true" buttons="#dlg-buttons" closable="false">
  <form id="fm" method="post" enctype="multipart/form-data">
		<div style="margin-bottom:20px">
			<input class="easyui-filebox" id="fileFwff" name="fileFwff" label="服务方法注册:" labelPosition="top" data-options="buttonText:'选择文件',prompt:'选择文件...'" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" style="width:100%"><a href="${ctx}/uploadServe/downLoadExcelModel?flag=fwff">模板下载</a>
		</div>
	
			<input type="file" style="display:none" id="fileFwzc" name="fileFwzc" />
		
		
			<!-- <input class="easyui-filebox" type="hidden" id="fileFwff" name="fileFwff" /> -->
		
		
			<input type="file" style="display:none" id="fileFwqqcs" name="fileFwqqcs" />
		
	   
			<input type="file" style="display:none" id="fileFwfhcs" name="fileFwfhcs"/>
		
		
		</form>
  </div>
  <div id="dlg-buttons">
	<a href="javascript:uploadFiles()"  class="easyui-linkbutton" data-options="iconCls:'icon-ok'" >上传</a>
	<a href="javascript:closeUploadDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>



<div id="addUpdate" class="easyui-dialog" style="width: 700px;height: 400px;padding: 10px 20px" closable="false"
  closed="true" buttons="#addUpdate-buttons">
  <form id="addUpdatefm" method="post">
  	<table cellspacing="5px;">
  	<tr>
  	       <td>方法名称：</td>
  			<td><input type="text" id="ffmc" name="ffmc" class="easyui-textbox" required="true"/></td>
  			<input type="hidden" id="ffbs" name="ffbs"/>
  			<td>服务名称：</td>
  			<td>
  				<input class="easyui-textbox" id="fwmc" name="fwmc"  data-options="buttonText:'选择',onClickButton:openFwtgzDialog" required="true">
  			<input id="fwbs" name="fwbs"  type="hidden">
  			</td>
  		    
  		</tr>
  		<tr>
  		   <td>方法类：</td>
  			<td><input type="text" id="ffl" name="ffl" class="easyui-textbox" required="true"/></td>
  			 <td>警种分类：</td>
  			<td>
  			
  			 <input class="easyui-combobox" id="jzfl"   name="jzfl" url="${ctx}/fwFFManage/jzfl" valueField="jzlbdm"   textField="jzlbmc"  panelHeight="200" required="true" />
  			
  			</td>
  		</tr>
  		<tr>
  		   <td>网络分类：</td>
  			<td>  <select class="easyui-combobox" name="wlfl" id="wlfl" style="width:100%;" panelHeight="auto" required="true">
  			 <option></option><option value="1">一类网</option><option value="2">二类网</option><option value="3">三类网</option>
  			 </select></td>
  			 <td>方法类别：</td>
  			<td><select class="easyui-combobox" name="fflb" id="fflb" style="width:100%;" panelHeight="auto" required="true">
  			 <option></option><option value="1">公开</option><option value="2">受控</option><option value="3">私有</option>
  			 </select></td>
  			</td>
  		</tr>
  		<tr>
  		    <td>URL：</td>
  			<td><input type="text" id="url" name="url" class="easyui-textbox" required="true"/></td>
  			 <td>接口方法类型：</td>
  			<td><select class="easyui-combobox" name="fflx" id="fflx" style="width:100%;" panelHeight="auto" required="true">
  			 <option value="POST">POST</option><option value="GET">GET</option><option value="FORM-DATA">FORM-DATA</option>
  			 </select></td>
  			</td>
  		</tr>
  		<tr>
  		   <td>操作分类：</td>
  			<td>  <select class="easyui-combobox" name="czfl" id="czfl" style="width:100%;" panelHeight="auto" required="true">
  			 <option></option>
              <option value="R">查询</option><option value="C">增加</option><option value="U">更新</option><option value="D">删除</option>
               </select></td>
  			 <td>是否缓存数据：</td>
  			<td>
  			<span class="radioSpan">
                <input type="radio" name="sfhcsj" value="0" checked>否</input>
                <input type="radio" name="sfhcsj" value="1">是</input>	
            </span>
  			
  			
  		</tr>
  		
  		<tr>
  		   <td>缓存数据有效时间（秒）：</td>
  			<td><input type="text" id="sjyxsj" name="sjyxsj" class="easyui-numberbox" required="true" value="0" /></td>
  			
  		</tr>
  		
  		<tr>
  			<td valign="top">方法描述：</td>
  			<td colspan="4">
  				<textarea rows="7" cols="57" name="ffms" id="ffms"></textarea>
  			</td>
  		</tr>
  	</table>
  </form>
</div>

<div id="addUpdate-buttons">
<!-- 	<a href="javascript:saveUpdateFwzc()" class="easyui-linkbutton" iconCls="icon-ok" >保存</a> -->
<!-- 	<a href="javascript:closeFwzcAddUpdateDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a> -->
</div>



 <div id="fwzcTree" class="easyui-dialog" style="width: 300px;height: 450px;padding: 10px 20px"
  closed="true" buttons="fwtgzTree-buttons" closable="true">
	<ul id="tree" class="easyui-tree"></ul>
</div>


    </div>
 
</body>
</html>