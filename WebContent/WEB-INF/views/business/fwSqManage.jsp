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

	function searchFwzyzcb(){
		
		$('#dg').datagrid('load',{
			fwmc:$("#fwmc").val(),
			fwbs:$("#fwbs").val(),
			fwtgzYyxtbh:$("#fwtgzYyxtbh").val(),
			fwztdm:$('#fwztdm').combobox("getValue")
		});
	}
	
	
	function staformat(value, row, index){
		return  row.fwztdm==1?"启用":"禁用" ;
	}
	
	  //格式化单元格提示信息  
     function formatCellTooltip(value){  
         return "<span title='" + value + "'>" + value + "</span>";  
     } 
     var fwbs;
 	function openFwsqDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert('系统提示','请选择一条要授权的服务！');
			return;
		}
		var row=selectedRows[0];
		fwbs=row.fwbs;
		$("#dlg2").dialog("open").dialog("setTitle","服务授权");
		var url="${ctx}/fwSqManage/tree?fwbs="+fwbs;
		
		$("#tree").tree({
			lines:true,
			url:url,
			checkbox:true,
			animate:true,
			onLoadSuccess:function(){
				$("#tree").tree('expandAll');
			}
			/* onCheck:function(node,checked){
				if(checked){
					checkNode($('#tree').tree('getParent',node.target));
				}
			} */
		});
	}
	/* function checkNode(node){
		if(!node){
			return;
		}else{
			checkNode($('#tree').tree('getParent',node.target));
			$('#tree').tree('check',node.target);
		}
	} */
 	function closeFwsqDialog(){
		$("#dlg2").dialog("close");
	}
 	
	function saveFwsq(){
		var nodes=$('#tree').tree('getChecked');
		/* if(nodes.length==0){
			$.messager.alert('系统提示','请选择要授权的参与方！');
			return;
		} */
		var fwsqArrIds=[];
		for(var i=0;i<nodes.length;i++){
			if(nodes[i].id!=''){
			
			fwsqArrIds.push(nodes[i].id);
			}
		}
		
		var fwsqIds=fwsqArrIds.join(",");
		$.post("${ctx}/fwSqManage/fwSq",{fwsqIds:fwsqIds,fwbs:fwbs},function(result){
			if(result.success){
				$.messager.alert('系统提示','授权成功！');
				closeFwsqDialog();
			}else{
				$.messager.alert('系统提示',result.errorMsg);
			}
		},"json");
	}
	
	//url：窗口调用地址，title：窗口标题，width：宽度，height：高度，shadow：是否显示背景阴影罩层  ${ctx}/fwSqhisManage/show
	function showMessageDialog(  shadow) {  
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert('系统提示','请选择一条要查看历史授权的服务！');
			return;
		}
		var row=selectedRows[0];
		
		var url="${ctx}/fwSqhisManage/show?fwbs="+row.fwbs;
	    var content = '<iframe src="' + url + '" width="100%" height="99%" frameborder="0" scrolling="no"></iframe>';  
	    var boarddiv = '<div id="msgwindow" title="授权历史"></div>'//style="overflow:hidden;"可以去掉滚动条  
	    $(document.body).append(boarddiv);  
	    var win = $('#msgwindow').dialog({  
	        content: content,  
	        width: "700px",  
	        height: "430px",  
	        modal: true,  
	        title: "授权历史",  
	        onClose: function () {  
	            $(this).dialog('destroy');//后面可以关闭后的事件  
	        }  
	    });  
	    win.dialog('open');  
	}
	
	


	var urlwifi= 'http://20.1.11.52:5988/http/GA000Comm';
	var anr={"method":"queryFW","params":{}};
	var sip='010011033223';
	//today.getMilliseconds();

	var params = JSON.stringify({ "BWLYDKH":"8081"
	, "BWLYIPDZ":"10.11.33.223"
	, "FWBS":"S00101100000000023004"
	, "FFBS":"FUN001"
	, "FWQQSB_BH":"869661020828470"
	, "FWQQZ_ZCXX":"A00111000000000002302"
	, "FWQQ_BWBH":"SR020001026135201902211148067060001"
	, "FWQQ_NR":anr
	, "FWQQ_RQSJ":"20190221114926"
	, "FWQQ_SJSJLX":"service_request"
	, "XXCZRY_GAJGJGDM":"110102000000"
	, "XXCZRY_GMSFHM":"111111111111111110"
	, "XXCZRY_XM":"李晨"
	})
	
		function aa(){//点击按钮触发的方法
		$.ajax({
			url : urlwifi,//后台定义的方法和参数
			type : "post",
			async : false,
			dataType : "text",
			 data: params,
			success : function(data) {
				alert(data);
			}
		});	}
</script>
</head>
<body class="easyui-layout" >
	<div data-options="region:'center'">
		<table id="dg"  class="easyui-datagrid" fitColumns="true"  singleSelect="true" pagination="true" rownumbers="true" url="${ctx}/serveManage/list" fit="true" toolbar="#tb">
			<thead>
				<tr>
					<!--  <th field="cb" checkbox="true" align="center"></th> -->
					<th data-options="field:'fwbs'"  align="center"  >服务标识</th>
               		<th data-options="field:'fwmc'" align="center"  nytitle="true" formatter="formatCellTooltip">服务名称</th>
					<th data-options="field:'fwRkdzlb'" align="center"  formatter="formatCellTooltip">服务入口地址列表</th>
					<!-- <th data-options="field:'fwIpdz'" >服务入口地址(IP)</th>
					<th data-options="field:'fwZxdkhm'" >服务端口号</th> 
					<th data-options="field:'fwLj'" align="center" width="50" formatter="formatCellTooltip">服务路径</th> -->
					<th data-options="field:'fwztdm'"  formatter="staformat" align="center" >服务状态</th>
					<th data-options="field:'fwbbh'" align="center" >版本</th>
					<!--  <th data-options="field:'fwtgzYyxtbh'" align="center" width="80">服务提供者标识</th> -->
					<th data-options="field:'fwcyfYyxtmc'" align="center" >应用名称</th>
					<th data-options="field:'fwzxdz'" align="center"  formatter="formatCellTooltip">服务总线地址</th>
 				</tr>
			</thead>
		</table>
       	<div id="tb">
			<div>
				&nbsp; 服务标识：&nbsp;<input type="text" class="easyui-textbox"  name="fwbs" id="fwbs" size="15" />
				&nbsp;服务名称：&nbsp;<input type="text" class="easyui-textbox"  name="fwmc" id="fwmc" size="15" />
				&nbsp;应用名称 ：&nbsp;
				<select name="fwtgzYyxtbh" id="fwtgzYyxtbh" class="easyui-combobox" size="15">
					<option value="">请选择</option>
					<c:forEach items="${fwcyf}" var="zcxx" varStatus="index">
						<option value="${zcxx.fwcyfYyxtbh}">${zcxx.fwcyfYyxtmc}</option>
					</c:forEach>
				</select>
		        &nbsp; 服务状态：&nbsp;
				<select class="easyui-combobox" name="fwztdm" id="fwztdm"  labelPosition="top"  size="20"  editable=false panelHeight='auto'>
			        <option value="">请选择...</option>
					<option value="1">启用</option>
				    <option value="2">禁用</option>
				</select>
				<a href="javascript:searchFwzyzcb()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
	   			<a href="javascript:openFwsqDialog()" class="easyui-linkbutton" iconCls="icon-roleManage" plain="true">服务授权</a>
	    		<a href="javascript:showMessageDialog()" class="easyui-linkbutton" iconCls="icon-history-chart" plain="true">授权历史</a>
			</div>
		</div>
	</div>
    <div id="dlg2" class="easyui-dialog" style="width: 300px;height: 450px;padding: 10px 20px" closed="true" buttons="#dlg2-buttons" closable="false">
		<ul id="tree" class="easyui-tree"></ul>
	</div>
	
	<div id="dlg2-buttons">
		<a href="javascript:saveFwsq()" class="easyui-linkbutton" iconCls="icon-ok" >授权</a>
		<a href="javascript:closeFwsqDialog()" class="easyui-linkbutton" iconCls="icon-cancel" >关闭</a>
	</div>

</body>
</html>