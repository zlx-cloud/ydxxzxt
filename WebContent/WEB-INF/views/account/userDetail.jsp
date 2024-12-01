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
<title>详情</title>
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
.form-control {
    
    display: none;
  
}
.ibox-tools{
	margin-top: -9px;
}
#showbtn{
display: none;
}
</style>
<script type="text/javascript">

function editRecord(){
	$("#showbtn").css("display","block");
	$(".form-control").css("display","block");
	$(".col-sm-10 label").css("display","none");
	$("#fwcyfYyxtbh").css("display","block");
	//$("#fwcyfRqsj").css("display","block");
	$("#fwcyfYyxtmc").css("display","block");
	
}
function cal(){
	$("#showbtn").css("display","none");
	$(".form-control").css("display","none");
	$(".col-sm-10 label").css("display","block");

}
    
    
  
function saveUser(){
	$("#fm").form("submit",{
		url:"${ctx}/userT/add",
		onSubmit:function(){
			return $(this).form("validate");
		},
		success:function(result){
			var result=eval('('+result+')');
			if(result.errorMsg){
				$.messager.alert('系统提示',"<font color=red>"+result.errorMsg+"</font>");
				return;
			}else{
				
				 window.location.reload();
				/*  $.messager.alert('系统提示','保存成功'); */
			}
		}
	});
}

</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
	
	 <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5><shiro:principal property="name"></shiro:principal>详细信息</h5>
                       <div class="ibox-tools">
							<button class="btn btn-info " type="button" onclick="editRecord()"><i class="fa fa-paste"></i> 编辑</button>
						</div>
                    </div>
                    <div class="ibox-content">
                        <form method="post" id="fm" class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">服务参与方标识</label>

                                <div class="col-sm-10">
                                  <label class="col-sm-3 control-label" id="fwcyfYyxtbh">${fwcyfzcb.fwcyfYyxtbh }</label>
                                  <input type="text" style="display:none"  name="fwcyfYyxtbh" value="${fwcyfzcb.fwcyfYyxtbh }"> 
                                    
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-2 control-label">服务参与方名称</label>
                                <div class="col-sm-10">
                                 <label class="col-sm-3 control-label" id="fwcyfYyxtmc">${fwcyfzcb.fwcyfYyxtmc }</label>
                                    <input type="text" style="display:none"  name="fwcyfYyxtmc" value="${fwcyfzcb.fwcyfYyxtmc }"> 
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                           
                           
                            <div class="form-group">
                                <label class="col-sm-2 control-label">服务参与方描述</label>

                                <div class="col-sm-10">
                                        <label class="col-sm-3 control-label">${fwcyfzcb.fwcyfMs }</label>
                                         <input type="text" class="form-control" id="fwcyfMs" name="fwcyfMs" value="${fwcyfzcb.fwcyfMs }"> 
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">服务参与方所属单位</label>

                                <div class="col-sm-10">
                                        <label class="col-sm-3 control-label">${fwcyfzcb.fwcyfSsfj==null?"": fwcyfzcb.jgmc}</label>
                                        <input type="hidden" id="fwcyfSsfj" name="fwcyfSsfj" value="${fwcyfzcb.fwcyfSsfj }"/>
                                         <input type="text"   disabled="" class="form-control" id="jgmc" name="jgmc" value="${fwcyfzcb.fwcyfSsfj==null?'': fwcyfzcb.jgmc }"> 
                                </div>
                            </div>
                             
                            <div class="form-group">
                                <label class="col-sm-2 control-label">服务参与方注册日期</label>

                                <div class="col-sm-10">
                                 <label class="col-sm-3 control-label" ><fmt:formatDate value="${fwcyfzcb.fwcyfRqsj }" pattern="yyyy-MM-dd" /></label>
                                 <input type="text" disabled="" class="form-control" id="fwcyfRqsj" name="fwcyfRqsj" value="<fmt:formatDate value="${fwcyfzcb.fwcyfRqsj }" pattern="yyyy-MM-dd" />">  
                                </div>
                            </div> 
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">服务参与方联系方式</label>

                                <div class="col-sm-10">
                                  <label class="col-sm-3 control-label">${fwcyfzcb.fwcyfLxfs }</label>
                                    <input type="text" class="form-control" id="fwcyfLxfs" name="fwcyfLxfs" value="${fwcyfzcb.fwcyfLxfs }">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">联系人说明</label>

                                <div class="col-sm-10">
                                  <label class="col-sm-3 control-label">${fwcyfzcb.lxrSm }</label>
                                    <input type="text" class="form-control" id="lxrSm" name="lxrSm" value="${fwcyfzcb.lxrSm }">
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-2 control-label">联系人姓名</label>

                                <div class="col-sm-10">
                                  <label class="col-sm-3 control-label">${fwcyfzcb.lxrXm }</label>
                                    <input type="text" class="form-control" id="lxrXm" name="lxrXm" value="${fwcyfzcb.lxrXm }">
                                </div>
                            </div>
                           <div class="form-group">
                                <label class="col-sm-2 control-label">联系电话</label>

                                <div class="col-sm-10">
                                  <label class="col-sm-3 control-label">${fwcyfzcb.lxdh }</label>
                                    <input type="text" class="form-control" id="lxdh" name="lxdh"  value="${fwcyfzcb.lxdh }">
                                </div>
                            </div>
                          
                         
                            
                             <div class="form-group">
                                <label class="col-sm-2 control-label">电子信箱</label>

                                <div class="col-sm-10">
                                  <label class="col-sm-3 control-label">${fwcyfzcb.dzxx }</label>
                                    <input type="text" class="form-control" id="dzxx" name="dzxx"  value="${fwcyfzcb.dzxx }">
                                </div>
                            </div>
                          
                            <div class="form-group">
                                <label class="col-sm-2 control-label">通信地址</label>

                                <div class="col-sm-10">
                                  <label class="col-sm-3 control-label">${fwcyfzcb.txdz }</label>
                                    <input type="text" class="form-control" id="txdz" name="txdz"  value="${fwcyfzcb.txdz }">
                                </div>
                            </div>
                           <div class="form-group">
                                <label class="col-sm-2 control-label">包名</label>

                                <div class="col-sm-10">
                                  <label class="col-sm-3 control-label">${fwcyfzcb.fwcyfBm }</label>
                                    <input type="text" class="form-control" id="fwcyfBm" name="fwcyfBm"  value="${fwcyfzcb.fwcyfBm }">
                                </div>
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