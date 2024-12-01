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

	function searchFwzyzcb(){
		
		$('#dg').datagrid('load',{
			fwmc:$("#fwmc").val(),
			fwbs:$("#fwbs").val(),
			fwztdm:$('#fwztdm').combobox("getValue"),
			sfhcsj:$('#sfhcsjindex').combobox('getValue')
		});
	
	}
	
	function rowformat(value, row, index){
		return '<a class="editcls" onclick="look(\''+row.fwbs+'\')" href="javascript:void(0)">查看</a>';
	}
	function staformat(value, row, index){
		return  row.fwztdm==1?"启用":"禁用" ;
	}
	function look(value){
		alert(value);
	}
	
	
	
	
	
	 $(function(){
         $('#dg').datagrid({
             view: detailview,
             detailFormatter:function(index,row){
                 return '<div style="padding:2px;position:relative;"><table class="ddv" id="ddv"></table></div>';
             },
             onExpandRow: function(index,row){
                 var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
                 ddv.datagrid({
                    /*  url:'datagrid22_getdetail.php?itemid='+row.itemid, */
                     fitColumns:true,
                     singleSelect:true,
                     rownumbers:true,
                     loadMsg:'',
                     height:'auto',
                     
                     
                     view: detailview,
                     detailFormatter:function(index,row){
                         return '<div style="padding:2px;position:relative;"><table class="ddvqq" id="ddvqq"></table><table class="ddvfh" id="ddvfh"></table></div>';
                     },
                     /* view: detailview,
                     detailFormatter:function(index,row){
                         return '<div style="padding:2px;position:relative;"><table class="ddvfh" id="ddvfh"></table></div>';
                     }, */
                     columns:[[
                         {field:'ffbs',title:'方法标识',align:'center',formatter:formatCellTooltip },
                         {field:'ffmc',title:'方法名',align:'center',formatter:formatCellTooltip},
                         {field:'ffl',title:'方法类',align:'center',formatter:formatCellTooltip},
                         {field:'ffms',title:'方法描述',width:20,align:'center',formatter:formatCellTooltip},
                         {field:'jzlbmc',title:'警种分类',align:'center',formatter:formatCellTooltip},
                         {field:'czfl',title:'操作分类',align:'center', formatter: function(value,row,index){  
                        	 if(row.czfl=='R'){
                    			 return "查询";
                    		 }if(row.czfl=='C'){
                    			 return "新增";
                    		 }if(row.czfl=='U'){
                    			 return "更新";
                    		 }if(row.czfl=='D'){
                    			 return "删除";
                    		 }
                         }  },
                         {field:'sfhcsj',title:'是否缓存',align:'center',formatter: function(value,row,index){  
                        	 if(row.sfhcsj=='1'){
                    			 return "是";
                    		 }if(row.sfhcsj=='0'){
                    			 return "否";
                    		 } 
                         }  },
                         {field:'sjyxsj',title:'缓存时间(秒)',align:'center'}
                     ]],
                     data:row.fwzyffzcbList,
                     onResize:function(){
                         $('#dg').datagrid('fixDetailRowHeight',index);
                     },
                     onLoadSuccess:function(){
                         setTimeout(function(){
                             $('#dg').datagrid('fixDetailRowHeight',index);
                         },0);
                     },
                     
                     
                     onExpandRow: function(index,row){
                         var ddvqq = $(this).datagrid('getRowDetail',index).find('table.ddvqq');
                         var ddvfh = $(this).datagrid('getRowDetail',index).find('table.ddvfh');
                         
                         ddvfh.datagrid({
                             fitColumns:true,
                             singleSelect:true,
                             rownumbers:true,
                             loadMsg:'',
                             height:'auto',
                              columns:[[
                                        {field:'fhcsbs',title:'返回参数标识',align:'center',formatter:formatCellTooltip},
                                        {field:'fhcsm',title:'返回参数名',align:'center',formatter:formatCellTooltip},
                                        {field:'fhcslx',title:'返回参数类型',align:'center',formatter:formatCellTooltip},
                                        {field:'fhcscd',title:'返回参数长度',align:'center',formatter:formatCellTooltip},
                                        {field:'fhcszy',title:'返回参数值域',align:'center',formatter:formatCellTooltip}
                             ]],
                             data:row.fwzyfffhcsbList,
                             onResize:function(){
                            	 ddv.datagrid('fixDetailRowHeight',index);
                             },
                             onLoadSuccess:function(){
                                 setTimeout(function(){
                                	 ddv.datagrid('fixDetailRowHeight',index);
                                 },0);
                             }
                         });
                         
                         ddvqq.datagrid({
                             fitColumns:true,
                             singleSelect:true,
                             rownumbers:true,
                             loadMsg:'',
                             height:'auto',
                              columns:[[
                                 {field:'qqcsbs',title:'请求参数标识',align:'center',formatter:formatCellTooltip},
                                 {field:'qqcsm',title:'请求参数名',align:'center',formatter:formatCellTooltip},
                                 {field:'qqcslx',title:'请求参数类型',align:'center',formatter:formatCellTooltip},
                                 {field:'qqcscd',title:'请求参数长度',align:'center',formatter:formatCellTooltip},
                                 {field:'qqcszy',title:'请求参数值域',align:'center',formatter:formatCellTooltip}
                             ]],
                             data:row.fwzyffqqcsbList,
                             onResize:function(){
                            	 ddv.datagrid('fixDetailRowHeight',index);
                             },
                             onLoadSuccess:function(){
                                 setTimeout(function(){
                                	 ddv.datagrid('fixDetailRowHeight',index);
                                 },0);
                             }
                         });
                         ddv.datagrid('fixDetailRowHeight',index);
                     }
                     
                      });
                 $('#dg').datagrid('fixDetailRowHeight',index);
             }
         });
         
         
     	$("#tree").tree({
			lines:true,
			animate:true,
			url:'${ctx}/serveManage/tree?flag=fwml',
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
  					 fwtgzYyxtbh:fwtgzYyxtbh,
  					fwbs:fwbs
  				});   
               }else{
            	  // $.messager.alert('系统提示','无此服务！');
            	 
            	   return;
               }
				 
			}
		});
         
     });
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
    	     $('#fm').form('submit', {                
    	                 url: '${ctx}/uploadServe/impExcelFwZcSJ',
    	                 success: function (result) {
    	                      var result = eval('(' + result + ')');
    	                      if(result.errorMsg){
    	      					$.messager.alert('系统提示',"<font color=red>"+result.errorMsg+"</font>");
    	      					return;
    	      				}else{
    	      					$.messager.alert('系统提示','上传成功');
    	      					$("#tree").treegrid("reload");
    	      					$("#dg").datagrid("reload");
    	      					
    	      				}
    	                  }
    	              });
    	}
  
</script>
</head>
<body class="easyui-layout" >
	<div data-options="region:'west',split:true,hideCollapsedContent:false,collapsed:true" title="服务列表" style="width:170px;">
		<ul id="tree" class="easyui-tree"></ul>
	</div>
	<div data-options="region:'center'">
		<table id="dg"  class="easyui-datagrid" fitColumns="true" singleSelect="true"   pagination="true" rownumbers="true" url="${ctx}/serveManage/list" fit="true" toolbar="#tb">
	     <thead>
                    <tr>
                     <!--  <th field="cb" checkbox="true" align="center"></th> -->
                        <th data-options="field:'fwbs'"  align="center" >服务标识</th>
                        <th data-options="field:'fwmc'" align="center"  nytitle="true" formatter="formatCellTooltip">服务名称</th>
                       <!--  <th data-options="field:'fwRkdzlb'" align="center"  formatter="formatCellTooltip">服务入口地址列表</th> -->
                        <!-- <th data-options="field:'fwIpdz'" >服务入口地址(IP)</th>
                        <th data-options="field:'fwZxdkhm'" >服务端口号</th> 
                        <th data-options="field:'fwLj'" align="center" width="50" formatter="formatCellTooltip">服务路径</th> -->
                        <th data-options="field:'fwztdm'"  formatter="staformat" align="center" >服务状态</th>
                        <th data-options="field:'fwbbh'" align="center" >版本</th>
                         <th data-options="field:'fwcyfYyxtmc'" align="center" >应用名称</th>
                         <th data-options="field:'fwzxdz'" align="center"  formatter="formatCellTooltip">服务总线地址</th>
                          <th data-options="field:'fwzykfYylxdm'" align="center" formatter="formatCellTooltip" >语言类型</th>
                          <th data-options="field:'fwnrBz'" align="center" width="60" formatter="formatCellTooltip" >服务内容</th>
                        <th data-options="field:'fwMs'" align="center" width="60" formatter="formatCellTooltip">服务描述</th>
                    </tr>
                </thead>
		</table>
       	<div id="tb">
			
			<div>
				&nbsp;服务名称：&nbsp;<input type="text" class="easyui-textbox"  name="fwmc" id="fwmc" size="20" />
				&nbsp; 服务标识：&nbsp;<input type="text" class="easyui-textbox"  name="fwbs" id="fwbs" size="20" />
		        &nbsp; 服务状态：&nbsp;
				<select class="easyui-combobox" name="fwztdm" id="fwztdm"  labelPosition="top"  size="20"  editable=false panelHeight='auto'>
			        <option value="">请选择...</option>
					<option value="1">启用</option>
				    <option value="2">禁用</option>
				</select>
				&nbsp;是否缓存数据：&nbsp;<select class="easyui-combobox" name="sfhcsjindex" id="sfhcsjindex" editable=false panelHeight="auto" style="width:8%">
  			  <option value="">请选择...</option>
              <option value="0">否</option><option value="1">是</option>
               </select>
				<a href="javascript:searchFwzyzcb()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
				<!-- <a href="javascript:openUploadDialog()" class="easyui-linkbutton" size="20" >服务注册</a> -->
			</div>
		</div>
		<div id="dlg" class="easyui-dialog" style="width:100%;max-width:400px;padding:10px 60px;" closed="true" buttons="#dlg-buttons" closable="false">
			<form id="fm" method="post" enctype="multipart/form-data">
				<div style="margin-bottom:20px">
					<input class="easyui-filebox" id="fileFwzc" name="fileFwzc" label="服务注册:" labelPosition="top" data-options="buttonText:'选择文件',prompt:'选择文件...'" accept="application/vnd.ms-excel" style="width:100%"><a href="${ctx}/uploadServe/downLoadExcelModel?flag=fwzc">模板下载</a>
				</div>
				<div style="margin-bottom:20px">
					<input class="easyui-filebox" id="fileFwff" name="fileFwff" label="方法注册:" labelPosition="top" data-options="buttonText:'选择文件',prompt:'选择文件...'" accept="application/vnd.ms-excel" style="width:100%"><a href="${ctx}/uploadServe/downLoadExcelModel?flag=fwff">模板下载</a>
				</div>
				<div style="margin-bottom:20px">
					<input class="easyui-filebox" id="fileFwqqcs" name="fileFwqqcs" label="输入参数:" labelPosition="top" data-options="buttonText:'选择文件',prompt:'选择文件...'" accept="application/vnd.ms-excel" style="width:100%"><a href="${ctx}/uploadServe/downLoadExcelModel?flag=fwqqcs">模板下载</a>
				</div>
				<div style="margin-bottom:20px">
					<input class="easyui-filebox" id="fileFwfhcs" name="fileFwfhcs" label="输出参数:" labelPosition="top" data-options="buttonText:'选择文件',prompt:'选择文件...'" accept="application/vnd.ms-excel" style="width:100%"><a href="${ctx}/uploadServe/downLoadExcelModel?flag=fwfhcs">模板下载</a>
				</div>
			</form>
		</div>
		<div id="dlg-buttons">
			<a href="javascript:uploadFiles()"  class="easyui-linkbutton" data-options="iconCls:'icon-ok'" >Upload</a>
			<a href="javascript:closeUploadDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
		</div>
	</div>
</body>
</html>