<%@page import="java.util.Date,java.text.DateFormat"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册应用</title>
<link href="${ctx}/static/common/css/bootstrap.min.css-v=3.3.5.css"  rel="stylesheet">
<link href="${ctx}/static/common/css/font-awesome.min.css-v=4.4.0.css"  rel="stylesheet">
<link href="${ctx}/static/common/css/animate.min.css"  rel="stylesheet">
<link href="${ctx}/static/common/css/style.min.css-v=4.0.0.css"  rel="stylesheet">
<link href="${ctx}/static/common/css/plugins/iCheck/custom.css"rel="stylesheet">

<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/js/layer.js"></script>
<script type="text/javascript" src="${ctx}/static/js/layerUtil.js"></script>
<style>
.wrapper-content {
     padding: 0px; 
}
.wrapper {
     padding: 0px; 
}
.ibox {
    
    margin-bottom: 0px;
  
}



</style>
<script type="text/javascript">


	function saveUser(){
		 var fwcyfYyxtmc =$("input[name='fwcyfYyxtmc']").val();
		 if(fwcyfYyxtmc==""||fwcyfYyxtmc==null){
				layAlert("服务参与方名称不能为空！", "fail");
				return;
		 }
		 var fwcyfDlmm =$("input[name='fwcyfDlmm']").val();
		 if(fwcyfDlmm==""||fwcyfDlmm==null){
				layAlert("服务参与方密码不能为空！", "fail");
				return; 
		 }
		 var checkCode = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$/.test(fwcyfDlmm);
		 if(!checkCode) {
			 layAlert("服务参与方密码由8-16位字母和数字组成！", "fail");
				return; 
		}
		 var fwcyfSsfj =$("input[name='fwcyfSsfj']").val();
		 if(fwcyfSsfj==""||fwcyfSsfj==null){
				layAlert("服务参与方所属单位不能为空！", "fail");
				return;
		 }
		 var fwcyfLxfs =$("input[name='fwcyfLxfs']").val();
		 if(fwcyfLxfs==""||fwcyfLxfs==null){
				layAlert("服务参与方联系方式不能为空！", "fail");
				return;
		 }
		 var lxrSm =$("input[name='lxrSm']").val();
		 if(lxrSm==""||lxrSm==null){
				layAlert("联系人说明不能为空！", "fail");
				return;
		 }
		 var lxrXm =$("input[name='lxrXm']").val();
		 if(lxrXm==""||lxrXm==null){
				layAlert("联系人姓名不能为空！", "fail");
				return;
		 }
		 var lxdh =$("input[name='lxdh']").val();
		 if(lxdh==""||lxdh==null){
				layAlert("联系电话不能为空！", "fail");
				return;
		 }
		 var dzxx =$("input[name='dzxx']").val();
		 if(dzxx==""||dzxx==null){
				layAlert("电子信箱不能为空！", "fail");
				return;
		 }
		 var txdz =$("input[name='txdz']").val();
		 if(txdz==""||txdz==null){
				layAlert("通信地址不能为空！", "fail");
				return;
		 }
		 var fwcyfBm =$("input[name='fwcyfBm']").val();
		 if(fwcyfBm==""||fwcyfBm==null){
				layAlert("包名不能为空！", "fail");
				return;
		 }
		 
		 var fwcyfMs =$("input[name='fwcyfMs']").val();
		 if(fwcyfMs==""||fwcyfMs==null){
				layAlert("服务参与方描述不能为空！", "fail");
				return;
		 }
		 
		$("#fm").form("submit",{
			url:"${ctx}/register/add",
			onSubmit:function(){
				return $(this).form("validate");
			},
			success:function(result){
				var result=eval('('+result+')');
				if(result.errorMsg){
					$.messager.alert('系统提示',"<font color=red>"+result.errorMsg+"</font>");
					return;
				}else{
					

					  $.messager.alert('系统提示',"保存成功,参与方ID为:"+result.success+",正在审核中,请等待!",'info',function(){closeWin()}); 
					  //
				}
			}
		});
	}
	
	
	 function openjgChooseDialog(){
		 $("#dlg3").dialog("open").dialog("setTitle","单位");
		
		
				$("#tree").tree({
					lines:true,
					animate:true,
					url:"${ctx}/register/jgTree",
					onLoadSuccess:function(){
						
						//$("#tree").tree('expandAll');
					
						//$("#treeGrid").treegrid('collapseAll');
					},
					onClick:function(node){
						 $("#fwcyfSsfj").val(node.id);
						$("#jgmc").val(node.text);	
						$("#dlg3").dialog("close"); 
						
					},
					onDblClick: function(node) {  
						$("#fwcyfSsfj").val(node.id);
						$("#jgmc").textbox("setValue",node.text);	
						$("#dlg3").dialog("close");
						$('#tree').tree('options').url="${ctx}/register/jgTree";
					},
					 onBeforeExpand:function(node){
						 $('#tree').tree('options').url ="${ctx}/register/jgTree?sssjjg="+node.id;    
			              

			               
			            }
				});
		  }
	
	
	
	
function closeWin() {
	  window.opener=null;
	  window.open('','_self');
	  window.close();
	}
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
	
	<div id="dlg3" class="easyui-dialog" style="width: 450px;height: 450px;padding: 10px 20px"
  closed="true" buttons="#dlg3-buttons" closable="true">
	<ul id="tree" class="easyui-tree"></ul>
</div>
	 <div class="row" style="margin-left: 24%;">
            <div class="col-sm-8">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5 style="margin-left: 46%; font-size: 18px;">注册应用</h5>
                       
                    </div>
                    <div class="ibox-content">
                        <form method="post" id="fm" class="form-horizontal">
                           <div style="color: red; text-align: center; margin-bottom: 5px;">
								带有*号的为必填项</div>
                             <div class="form-group">
                                <label class="col-sm-3 control-label">服务参与方名称</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control"  name="fwcyfYyxtmc" placeholder="应用APP名称" value=""> 
                                </div>
                                <span style="color: red; margin-top: 10px; position: absolute;">*</span>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">服务参与方密码</label>
                                <div class="col-sm-8">
                                    <input type="password" class="form-control"  name="fwcyfDlmm" value=""> 
                                </div>
                                <span style="color: red; margin-top: 10px; position: absolute;">*</span>
                            </div>
                           
                            
                            <div class="form-group">
                                <label class="col-sm-3 control-label">服务参与方所属单位</label>

                                <div class="col-sm-8">
                                      <input type="text"  readonly class="form-control" id="jgmc" name="jgmc" value=""/>
                                      <button type="button" style="float: right; margin-top: -34px;margin-bottom:0px" onclick="openjgChooseDialog()" class="btn btn-primary">选择
                                        </button> 
                                        <input type="hidden"  class="form-control" id="fwcyfSsfj" name="fwcyfSsfj" value=""/>
                                </div>
                                <span style="color: red; margin-top: 10px; position: absolute;">*</span>
                            </div>
                             
                          
                            
                            <div class="form-group">
                                <label class="col-sm-3 control-label">服务参与方联系方式</label>

                                <div class="col-sm-8">
                                    <input type="text" class="form-control" id="fwcyfLxfs" name="fwcyfLxfs" value="">
                                </div>
                                <span style="color: red; margin-top: 10px; position: absolute;">*</span>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-3 control-label">联系人说明</label>

                                <div class="col-sm-8">
                                    <input type="text" class="form-control" id="lxrSm" name="lxrSm" value="">
                                </div>
                                <span style="color: red; margin-top: 10px; position: absolute;">*</span>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-3 control-label">联系人姓名</label>

                                <div class="col-sm-8">
                                    <input type="text" class="form-control" id="lxrXm" name="lxrXm" value="">
                                </div>
                                <span style="color: red; margin-top: 10px; position: absolute;">*</span>
                            </div>
                           <div class="form-group">
                                <label class="col-sm-3 control-label">联系电话</label>

                                <div class="col-sm-8">
                                    <input type="text" class="form-control" id="lxdh" name="lxdh"  value="">
                                </div>
                                <span style="color: red; margin-top: 10px; position: absolute;">*</span>
                            </div>
                          <div class="form-group">
                                <label class="col-sm-3 control-label">电子信箱</label>

                                <div class="col-sm-8">
                                    <input type="text" class="form-control" id="dzxx" name="dzxx"  value="">
                                </div>
                                <span style="color: red; margin-top: 10px; position: absolute;">*</span>
                            </div>
                          
                            <div class="form-group">
                                <label class="col-sm-3 control-label">通信地址</label>

                                <div class="col-sm-8">
                                    <input type="text" class="form-control" id="txdz" name="txdz"  value="">
                                </div>
                                <span style="color: red; margin-top: 10px; position: absolute;">*</span>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-3 control-label">应用编号</label>

                                <div class="col-sm-8">
                                    <input type="text" class="form-control" id="appId" name="appId"  placeholder="开发者社区申请的APPID" value="">
                                </div>
                            </div>
                            
                           <div class="form-group">
                                <label class="col-sm-3 control-label">包名</label>

                                <div class="col-sm-8">
                                    <input type="text" class="form-control" id="fwcyfBm" name="fwcyfBm" placeholder="应用APP的包名" value="">
                                </div>
                                <span style="color: red; margin-top: 10px; position: absolute;">*</span>
                            </div>
                            
                           <div class="form-group">
                                <label class="col-sm-3 control-label">服务参与方描述</label>

                                <div class="col-sm-8">
                                         <input type="text" class="form-control" id="fwcyfMs" name="fwcyfMs" value=""> 
                                </div>
                                <span style="color: red; margin-top: 10px; position: absolute;">*</span>
                            </div>
                          
                            
                            <div class="hr-line-dashed"></div>
                            <div class="form-group" id="showbtn">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <!-- <button class="btn btn-primary" type="submit">保存内容</button> -->
                                        <button class="btn btn-primary" type="button" onclick="saveUser()">保存内容</button>
                                    <button class="btn btn-white" type="button" onclick="cal()">取消</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
	
	</div>
</body>
</html>