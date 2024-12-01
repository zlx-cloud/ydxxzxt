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
<script type="text/javascript" src="${ctx}/static/easyui/datagrid-detailview.js"></script>
	<script type="text/javascript">
	
	 $(function(){
         /**$('#dg').datagrid({
             view: detailview,
             detailFormatter:function(index,row){
                 return '<div style="padding:2px;position:relative;"><table class="ddv" id="ddv"></table></div>';
             }
         });*/
         
         
     	$("#tree").tree({
			lines:true,
			animate:true,
			url:'${ctx}/streamManage/tree',
			onLoadSuccess:function(){
				$("#tree").tree('collapseAll');
			},
			onClick:function(node){
				 var  fwtgzYyxtbh;
				 var fwbs;
				 var father = $(this).tree("getParent",node.target);
				 if(father==null){
					  fwtgzYyxtbh=node.id;
					  fwbs=null;
				 }else{
					 var twofather = $(this).tree("getParent",father.target);
					 if(twofather==null){
						 fwtgzYyxtbh=node.id;
						  fwbs=null;
					 }else{
						 var threefather = $(this).tree("getParent",twofather.target);
						 if(threefather==null){
							 fwtgzYyxtbh=null;
							 fwbs=node.id; 
						 }else{
							 fwtgzYyxtbh=null;
							 fwbs=null; 
						 }
						
					 }
					 
					 
				 }

               if(fwtgzYyxtbh!=null||fwbs!=null){
            	   $('#dg').datagrid('load',{
  					 orgid:fwtgzYyxtbh
  				});   
               }else{
            	  // $.messager.alert('系统提示','无此服务！');
            	 
            	   return;
               }
				 
			}
		});
         
     });
	  //格式化单元格提示信息  
     function formatCellTooltip(value, row, index){  
         return "<span title='" + value + "'><a href='javascript:void(0)' onclick='look(\""+row.id+"\")'>" + value + "</a></span>";  
     }
     
     function translateStartwithesb(value){
     	if(value=="false"){
     		return "否";
     	}
     	if(value=="true"){
     		return "是";
     	}
     }
     
     function translateStarted(value){
     	if(value=="true"){
     		return "启动";
     	}else{
     		return "停止";
     	}
     }
     
     function formatCountAll(value, row, index){
     	return "<a href='javascript:void(0)' onclick='showStreamDetail(\""+row.id+"\",\"\")'>"+value+"</a>";
     }
     
     function formatCountEnded(value, row, index){
     	return "<a href='javascript:void(0)' onclick='showStreamDetail(\""+row.id+"\",\"ended\")'>"+value+"</a>";
     }
     
     function formatCountException(value, row, index){
     	return "<a href='javascript:void(0)' onclick='showStreamDetail(\""+row.id+"\",\"exception\")'>"+value+"</a>";
     }
     
     function showStreamDetail(streamId,state){
     	window.open("${ctx}/streamManage/streamList?streamId="+streamId+"&state="+state);
     }
     
     function startorstop(value, row, index){
    	 if(row.id=="esb_opt"){
    		 return;
    	 }
    	 
     	if(row.started=="false"){
     		return "<a href='javascript:void(0)' onclick='startEsb(\""+row.id+"\")'>启动</a>";
     	}
     	if(row.started=="true"){
     		return "<a href='javascript:void(0)' onclick='stopEsb(\""+row.id+"\")'>关闭</a>";
     	}
     }
     
     function startEsb(streamId){
     	$.post("${ctx}/streamManage/startProcess",{streamId:streamId},function(result){
			if(result.success){
				$.messager.alert('系统提示',"您已成功启动<font color=red>"+streamId+"</font>流程！");
				$("#dg").datagrid("reload");
			}else{
				$.messager.alert('系统提示',result.errorMsg);
			}
		},"json");
     }
     
     function stopEsb(streamId){
     	$.post("${ctx}/streamManage/stopProcess",{streamId:streamId},function(result){
			if(result.success){
				$.messager.alert('系统提示',"您已成功停止<font color=red>"+streamId+"</font>流程！");
				$("#dg").datagrid("reload");
			}else{
				$.messager.alert('系统提示',result.errorMsg);
			}
		},"json");
     }
</script>
</head>
<body class="easyui-layout" >

       <div data-options="region:'west',split:true" title="组织目录" style="width:170px;">
           <ul id="tree" class="easyui-tree"></ul>
        </div>
        <div data-options="region:'center'">
          <table id="dg"  class="easyui-datagrid" fitColumns="true"  pagination="true" rownumbers="true" url="${ctx}/streamManage/list" fit="true" toolbar="#tb">
                <thead>
                    <tr>
                     <!--  <th field="cb" checkbox="true" align="center"></th> -->
                        <th data-options="field:'id'" align="center" width="30">ID</th>
                        <th data-options="field:'name'" align="center" width="30" nytitle="true">名称</th>
                        <th data-options="field:'startwithesb'" align="center" formatter="translateStartwithesb">随总线启动</th>
                        <th data-options="field:'inputtype'" align="center" >输入类型</th>
                        <th data-options="field:'started'" align="center" formatter="translateStarted" width="30">状态</th>
                        <th data-options="field:'allNum'" align="center" formatter="formatCountAll">调用总数</th>
                        <th data-options="field:'successNum'" align="center" formatter="formatCountEnded">成功数</th>
                        <th data-options="field:'failureNum'" align="center" formatter="formatCountException">失败数</th>
                        <th data-options="field:'xxx'" align="center" formatter="startorstop" width="30">操作</th>
                    </tr>
                </thead>
            </table>
      
		
		<div id="dlg" class="easyui-dialog" style="width: 570px;height: 350px;padding: 10px 20px"
		  closed="true" buttons="#dlg-buttons">
		  <form id="fm" method="post">
		  	<table cellspacing="5px;">
		  		<tr>
		  			<td>接口名称：</td>
		  			<td width="80%"><input type="text" id="id" name="id" class="easyui-validatebox" required="true"/></td>
		  		</tr>
		  		<tr>
		  			<td>接口值：</td>
		  			<td width="80%"><input type="text" id="varValue" name="varValue" class="easyui-validatebox" required="true"/></td>
		  		</tr>
		  		<tr>
		  			<td valign="top">接口说明：</td>
		  			<td colspan="2">
		  				<textarea rows="7" cols="50" name="varDesc" id="varDesc"></textarea>
		  			</td>
		  		</tr>
		  	</table>
		  </form>
		</div>
		
		<div id="dlg-buttons">
			<a href="javascript:closeRoleSaveDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
		</div>
		
		<div id="dlg2" class="easyui-dialog" style="width: 300px;height: 450px;padding: 10px 20px"
		  closed="true" buttons="#dlg2-buttons">
			<ul id="tree" class="easyui-tree"></ul>
		</div>
    </div>
</body>
</html>