<%@page import="java.util.Date,java.text.DateFormat,org.apache.shiro.SecurityUtils,net.sinodata.security.vo.ShiroUser"%>
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
<title>应用管理</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>


<script type="text/javascript">
	var url;
	
	$(function(){
		$("#dg2").datagrid({
			onDblClickRow:function(rowIndex,rowData){
				chooseRole();
			}
		});
	})
	
	function searchUser(){
		$('#dg').datagrid('load',{
			s_userName:$("#s_userName").val(),
			s_bm:$("#s_bm").val(),
			s_yybs:$("#s_yybs").val(),
			s_roleId:$('#s_roleId').combobox("getValue")
		});
	}
	
	function openUserAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加应用信息");
         $('#fwcyfYyxtmc').textbox('textbox').attr('readonly',false); 
		$("#fwcyfYyxtmc").textbox('textbox').css('background','') 
		url="${ctx}/userT/add?flag=1";
	}
	
	function openUserModifyDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert('系统提示','请选择一条要编辑的数据！');
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","修改应用信息");
		$("#fm").form("load",row);
		 $('#fwcyfYyxtmc').textbox('textbox').attr('readonly',true); 
		
		$("#fwcyfYyxtmc").textbox('textbox').css('background','#E0ECFF')
		url="${ctx}/userT/add";
	}
	
	function saveUser(){
		$("#fm").form("submit",{
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
					closeUserAddDialog();
					$("#dg").datagrid("reload");
				}
			}
		});
	}
	
	function closeUserAddDialog(){
		$("#dlg").dialog("close");
		$("#fm").form('clear');
	}
	
	
	function openRoleChooseDialog(){
		$("#dlg2").dialog("open").dialog("setTitle","选择角色");
	}
	
	function searchRole(){
		$('#dg2').datagrid('load',{
			s_roleName:$("#s_roleName").val()
		});
	}
	
	function closeRoleDialog(){
		$("#s_roleName").val("");
		$('#dg2').datagrid('load',{
			s_roleName:""
		});
		$("#dlg2").dialog("close");
	}
	
	function chooseRole(){
		var selectedRows=$("#dg2").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert('系统提示','请选择一个角色！');
			return;
		}
		var row=selectedRows[0];
		$("#roleid").val(row.roleid);
		$("#rolename").textbox("setValue",row.rolename);
		closeRoleDialog();
	}
	
	function deleteUser(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
			$.messager.alert('系统提示','请选择要删除的数据！');
			return;
		}
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].fwcyfYyxtbh);
		}
		var ids=strIds.join(",");
		$.messager.confirm("系统提示","您确认要删除这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
				$.post("${ctx}/userT/delete",{delIds:ids},function(result){
					if(result.success){
						$.messager.alert('系统提示',"您已成功删除<font color=red>"+result.delNums+"</font>条数据！");
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert('系统提示',result.errorMsg);
					}
				},"json");
			}
		});
	}
	  //格式化单元格提示信息  
    function formatCellTooltip(value){  
    	if(value==''||value==null){
			  return "";  
		  }
        return "<span title='" + value + "'>" + value + "</span>";  
    }
  //采用jquery easyui loading css效果 
    function ajaxLoading(){ 
    	//$('#dlg3').append();
        $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("#dlg3"); 
        $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("#dlg3").css({display:"block",left:"64px"}); 
     } 
    function ajaxLoadEnd(){ 
        $(".datagrid-mask").remove(); 
        $(".datagrid-mask-msg").remove();             
   } 
 function openjgChooseDialog(){
	 $("#dlg3").dialog("open").dialog("setTitle","单位");
	
	 ajaxLoading();
			$("#tree").tree({
				lines:true,
				animate:true,
				url:"${ctx}/userT/jgTree",
				onLoadSuccess:function(){
					
					//$("#tree").tree('expandAll');
					ajaxLoadEnd();
					//$("#treeGrid").treegrid('collapseAll');
				},
				onClick:function(node){
					/* $("#fwcyfSsfj").val(node.id);
					$("#jgmc").val(node.text);	
					$("#dlg3").dialog("close"); */
				},
				onDblClick: function(node) {  
					$("#fwcyfSsfj").val(node.id);
					$("#jgmc").textbox("setValue",node.text);	
					$("#dlg3").dialog("close");
					$('#tree').tree('options').url="${ctx}/userT/jgTree";
				},
				 onBeforeExpand:function(node){
					 $('#tree').tree('options').url ="${ctx}/userT/jgTree?sssjjg="+node.id;    
		               /*  $('#tree').tree('options').queryParams = {sssjjg:node.id}; */

		               
		            }
			});
	  }
 function selectJg(){
	 var node = $('#tree').tree('getSelected');
     if (node){
    	 $("#fwcyfSsfj").val(node.id);
		$("#jgmc").textbox("setValue",node.text);	
     }
 
	 $("#dlg3").dialog("close");
 }

 function closeAuthDialog(){
		$("#dlg3").dialog("close");
	}
 function myformatter(value){  
		
	    if(value!=null){
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
<body style="margin: 1px;">
<table id="dg"  class="easyui-datagrid" fitColumns="true" 
    pagination="true" rownumbers="true" url="${ctx}/userT/list" fit="true" toolbar="#tb">
    <thead>
    	<tr>
    		<th field="cb" checkbox="true" align="center"></th>
    		<th field="fwcyfYyxtbh" width="50" align="center" formatter="formatCellTooltip">标识</th>
    		<th field="fwcyfYyxtmc" width="50" align="center" formatter="formatCellTooltip">应用名称</th>
    		<th field="fwcyfDlmm" hidden="true">应用密码</th>
    		<th field="roleid" hidden="true">应用角色ID</th>
    		<th field="rolename" width="30" align="center" formatter="formatCellTooltip">应用角色</th>
    		 <th field="fwcyfSsfj" hidden="true">所属单位id</th>
    		<th field="jgmc" width="30" align="center" formatter="formatCellTooltip">所属单位</th>
<!--     		<th field="fwcyfMs" width="50" align="center" formatter="formatCellTooltip">描述</th> -->
    		<!-- <th field="fwcyfRqsj" width="50" align="center" formatter="formatCellTooltip" >注册时间</th> -->
    		<th	data-options="field:'fwcyfRqsj',width:50,align:'center',formatter: function(value,row,index){
					if(value){ return myformatter(value);}
				}">注册时间</th>
    	<!--     <th field="fwcyfLxfs" width="50" align="center" formatter="formatCellTooltip">联系方式</th>
    	     <th field="lxdh" width="50" align="center" formatter="formatCellTooltip">联系电话</th>
    		<th field="lxrSm" width="50" align="center" formatter="formatCellTooltip">联系人说明</th>
    		<th field="lxrXm" width="50" align="center" formatter="formatCellTooltip">联系人姓名</th>
    	    <th field="dzxx" width="50" align="center" formatter="formatCellTooltip">电子信箱</th>
    		<th field="txdz" width="50" align="center" formatter="formatCellTooltip">通信地址</th> -->
    		<th field="fwcyfBm" width="50" align="center" formatter="formatCellTooltip">包名</th>
    		<th field="appId" width="30" align="center" formatter="formatCellTooltip">应用编号</th>
    	</tr>
    </thead>
</table>
<div id="tb">
	<div>
		<a href="javascript:openUserAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="javascript:openUserModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<a href="javascript:deleteUser()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		<a href="${ctx}/userT/exportExcel" class="easyui-linkbutton" iconCls="icon-download-chart" plain="true">下载应用</a>
	</div>
	<div>
		&nbsp;应用名称：&nbsp;<input type="text" class="easyui-textbox"  name="s_userName" id="s_userName" size="20" onkeydown="if(event.keyCode==13) searchUser()"/>
		&nbsp;应用角色：&nbsp;<input class="easyui-combobox" id="s_roleId" name="s_roleId" size="20" data-options="editable:false,panelHeight:'auto',valueField:'roleid',textField:'rolename',url:'${ctx}/roleT/comBoList'"/>
		&nbsp;包名：&nbsp;<input type="text" class="easyui-textbox"  name="s_bm" id="s_bm" size="20" onkeydown="if(event.keyCode==13) searchUser()"/>
		&nbsp;应用标识：&nbsp;<input type="text" class="easyui-textbox"  name="s_yybs" id="s_yybs" size="20" onkeydown="if(event.keyCode==13) searchUser()"/>
		<a href="javascript:searchUser()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
	</div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 650px;height: 480px;padding: 10px 20px" closable="false"
  closed="true" buttons="#dlg-buttons">
  <form id="fm" method="post">
  	<table cellspacing="5px;">
  	<tr>
  			<!-- <td>标识：</td>
  			<td><input type="text" id="fwcyfYyxtbh" name="fwcyfYyxtbh" class="easyui-textbox" required="true"/></td> -->
  		<input type="hidden" id="fwcyfYyxtbh" name="fwcyfYyxtbh" />
  			<td>应用名称：</td>
  			<td><input type="text" id="fwcyfYyxtmc" name="fwcyfYyxtmc" class="easyui-textbox" required="true"/></td>
  			 <td>应用密码：</td>
  			<td><input type="text" id="fwcyfDlmm" name="fwcyfDlmm" class="easyui-validatebox" data-options="validType:'pwd'"/>
  				<script>
  				$(function(){
        			$.extend($.fn.validatebox.defaults.rules, {
        				pwd: {
                			validator: function(value){
                    				return /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$/.test(value);
                			},
                		message: '密码由8-16位字母和数字组成'
            			}
        			})
  				})
    			</script> 
  			</td>
  		</tr>
  		<tr>
  		    
  			<td>联系姓名：</td>
  			<td><input type="text" id="lxrXm" name="lxrXm" class="easyui-textbox" required="true"/></td>
  			<td>联系人说明：</td>
  			<td ><input type="text" id="lxrSm" name="lxrSm" class="easyui-textbox" required="true"/></td>
  			
  		</tr>
  		<tr>
  		     <td>联系电话：</td>
  			<td><input type="text" id="lxdh" name="lxdh" class="easyui-textbox" required="true"/></td>
  			<td>联系方式：</td>
  			<td><input type="text" id="fwcyfLxfs" name="fwcyfLxfs" class="easyui-textbox" required="true"/></td>
  		</tr>
  	
  		<tr>
  		     <td>电子信箱：</td>
  			<td><input type="text" id="dzxx" name="dzxx" class="easyui-textbox" required="true"/></td>
  			<td>通信地址：</td>
  			<td><input type="text" id="txdz" name="txdz" class="easyui-textbox" required="true"/></td>
  		
  			
  		</tr>
  			<tr>
  		     <td>包名：</td>
  			<td><input type="text" id="fwcyfBm" name="fwcyfBm" class="easyui-textbox" required="true"/></td>
  			<td>应用编号：</td>
  			<td><input type="text" id="appId" name="appId" class="easyui-textbox" required="true"/></td>
  		
  			
  		</tr>
  		<tr>
  			<td>所属单位：</td>
  			<td><input type="hidden" id="fwcyfSsfj" name="fwcyfSsfj" /><input type="text" id="jgmc" name="jgmc" readonly="readonly" class="easyui-textbox" required="true"/></td>
  			<td><a href="javascript:openjgChooseDialog()" class="easyui-linkbutton" >选择单位</a></td>
  			<td></td>
  		</tr>
  		<tr>
  			<td>角色名称：</td>
  			<td><input type="hidden" id="roleid" name="roleid" /><input type="text" id="rolename" name="rolename" readonly="readonly" class="easyui-textbox" required="true"/></td>
  			<td><a href="javascript:openRoleChooseDialog()" class="easyui-linkbutton" >选择角色</a></td>
  			<td></td>
  		</tr>
  		<tr>
  			<td valign="top">描述：</td>
  			<td colspan="4">
  				<textarea rows="7" cols="50" name="fwcyfMs" id="fwcyfMs"></textarea>
  			</td>
  		</tr>
  	</table>
  </form>
</div>

<div id="dlg-buttons">
	<a href="javascript:saveUser()" class="easyui-linkbutton" iconCls="icon-ok" >保存</a>
	<a href="javascript:closeUserAddDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>

<div id="dlg3" class="easyui-dialog" style="width: 300px;height: 450px;padding: 10px 20px"
  closed="true" buttons="#dlg3-buttons" closable="false">
	<ul id="tree" class="easyui-tree"></ul>
</div>

<div id="dlg3-buttons">
	<a href="javascript:selectJg()" class="easyui-linkbutton" iconCls="icon-ok" >确定</a>
	<a href="javascript:closeAuthDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>

<div id="dlg2" class="easyui-dialog" iconCls="icon-search" style="width: 500px;height: 400px"
  closed="true" buttons="#dlg2-buttons" closable="false">
  <div  align="center" style="padding-bottom: 4px; padding-top: 4px;">
  	角色名称：<input type="text" class="easyui-textbox" id="s_roleName" name="s_roleName" onkeydown="if(event.keyCode==13) searchRole()"/>
  	<a href="javascript:searchRole()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
  </div>
 
  	<table id="dg2"  class="easyui-datagrid" fitColumns="true" 
    pagination="true" rownumbers="true" url="${ctx}/roleT/list" singleSelect="true" fit="false" >
    <thead>
    	<tr>
    		<th field="roleid" width="50" align="center">编号</th>
    		<th field="rolename" width="100" align="center">角色名称</th>
    		<th field="roledescription" width="200" align="center">备注</th>
    	</tr>
    </thead>
</table>
  
</div>

<div id="dlg2-buttons">
	<a href="javascript:chooseRole()" class="easyui-linkbutton" iconCls="icon-ok" >确定</a>
	<a href="javascript:closeRoleDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
</div>
</body>
</html>