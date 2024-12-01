<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>统计分析</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/main.css">
<style>
.fontSizeBig {
	margin-top: 20px !important;
}
</style>
<script type="text/javascript" src="${ctx}/static/js/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/echarts.js"></script>
<script type="text/javascript" src="${ctx}/static/js/tjfx_new.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div class="allBox">
		<div class="homeLeft01">
			<a href="javascript:void(0)" class="homeUpaBule01">
				<div class="aLeft">
					<img class="tjfx_img" src="${ctx}/static/images/tjfx/xtdlzl.png" />
				</div>
				<div class="aRight">
					<p class="fontSizeBig" id="xtdlzl"></p>
					<p class="fontSizeMin">系统登录总量</p>
				</div>
			</a>
			<a href="javascript:void(0)" class="homeUpaBule02">
				<div class="aLeft">
					<img class="tjfx_img" src="${ctx}/static/images/tjfx/dwsjxtdlzl.png" />
				</div>
				<div class="aRight">
					<p class="fontSizeBig" id="dwsjxtdlzl"></p>
					<p class="fontSizeMin">近一月用户登陆量</p>
				</div>
			</a>
		</div>
		<div class="homeLeft01">
			<a href="javascript:void(0)" class="homeUpaBox">
				<div class="aLeft">
					<img class="tjfx_img" src="${ctx}/static/images/tjfx/yhzczl.png" />
				</div>
				<div class="aRight">
					<p class="fontSizeBig" id="yhzczl"></p>
					<p class="fontSizeMin">用户注册总量</p>
				</div>
			</a>
			<a href="javascript:void(0)" class="homeUpaBule">
				<div class="aLeft">
					<img class="tjfx_img" src="${ctx}/static/images/tjfx/dwsjyhzczl.png" />
				</div>
				<div class="aRight">
					<p class="fontSizeBig" id="dwsjyhzczl"></p>
					<p class="fontSizeMin">近一月用户注册量</p>
				</div>
			</a>
		</div>
		<div class="homeLeft01">
			<a href="javascript:void(0)" class="homeUpaBule03">
				<div class="aLeft">
					<img class="tjfx_img" src="${ctx}/static/images/tjfx/fwzyzczl.png" />
				</div>
				<div class="aRight">
					<p class="fontSizeBig" id="fwzyzczl">wan</p>
					<p class="fontSizeMin">服务资源注册总量</p>
				</div>
			</a>
			<a href="javascript:void(0)" class="homeUpaBule04">
				<div class="aLeft">
					<img class="tjfx_img" src="${ctx}/static/images/tjfx/dwsjfwzyzczl.png" />
				</div>
				<div class="aRight">
					<p class="fontSizeBig" id="dwsjfwzyzczl"></p>
					<p class="fontSizeMin">近一月服务资源注册量</p>
				</div>
			</a>
		</div>
		<div class="homeLeft01">
			<a href="javascript:void(0)" class="homeUpaBule05">
				<div class="aLeft">
					<img class="tjfx_img" src="${ctx}/static/images/tjfx/fwzysyzl.png" />
				</div>
				<div class="aRight">
					<p class="fontSizeBig" id="fwzysyzl"></p>
					<p class="fontSizeMin">服务资源使用总量</p>
				</div>
			</a>
			<a href="javascript:void(0)" class="homeUpaBule06">
				<div class="aLeft">
					<img class="tjfx_img" src="${ctx}/static/images/tjfx/dwsjfwzysyzl.png" />
				</div>
				<div class="aRight">
					<p class="fontSizeBig" id="dwsjfwzysyzl"></p>
					<p class="fontSizeMin">近一月服务资源调用量</p>
				</div>
			</a>
		</div>
		<div class="homeLeft01">
			<a href="javascript:void(0)" class="homeUpaBule07">
				<div class="aLeft">
					<img class="tjfx_img" src="${ctx}/static/images/tjfx/bwcjzl.png" />
				</div>
				<div class="aRight">
					<p class="fontSizeBig" id="bwcjzl"></p>
					<p class="fontSizeMin">报文采集总量</p>
				</div>
			</a>
			<a href="javascript:void(0)" class="homeUpaBule08">
				<div class="aLeft">
					<img class="tjfx_img" src="${ctx}/static/images/tjfx/dwsjbwcjzl.png" />
				</div>
				<div class="aRight">
					<p class="fontSizeBig" id="dwsjbwcjzl"></p>
					<p class="fontSizeMin">近一月报文采集量</p>
				</div>
			</a>
		</div>
		<div class="clear"></div>
		<div class="homeLeft04 easyui-tabs">
   		 	<div title="近一月单位用户登录量Top5">
				<ul class="orderUl" id="yhdllRankByTimeAndJg" style="height: 168px;"></ul>
   		 	</div>
			<div title="近一月单位注册用户量Top5" style="display:none;">
				<ul class="orderUl" id="zcyhlRankByTimeAndJg" style="height: 168px;"></ul>
   		 	</div>
		</div>
		<div class="homeLeft04" style="border:1px solid #95B8E7;height:197px;width: 25%;">
			<h5 class="hStyle" style="background-color: #E0ECFF;border-bottom:1px solid #95B8E7;">
				<span>各单位服务注册量Top5</span>
			</h5>
			<ul class="orderUl" id="fwzclByJg" style="height: 168px;background-color: #ffffff;"></ul>
		</div>
		<div class="homeLeft03 easyui-tabs" style="margin-top: 5px">
			<div title="按操作分析服务资源">
				<div id="fwzyRankByCzlx" style="height: 168px;"></div>
   		 	</div>
   		 	<div title="按警种分析服务资源">
   		 		<div id="fwzyRankByJzfl" style="width:429px;height: 168px;"></div>
   		 	</div>
   		 	<div title="按应用分析服务资源">
   		 		<div id="fwzyRankByYy" style="width:429px;height: 168px;"></div>
   		 	</div>
		</div>
		<div class="clear"></div>
		<div class="homeLeft05 easyui-tabs" style="width: 70.2%;">
			<div title="近一月单位请求量Top10">
				<div id="qqlRankBySjAndJg" style="width: 770px;height: 168px;"></div>
   		 	</div>
   		 	<div title="近一月服务资源被调用量Top10">
				<div id="fwzydylRank" style="width: 770px;height: 168px;"></div>
   		 	</div>
   		 	<div title="近一月应用系统被调用量Top10">
				<div id="yyxtdylRank" style="width: 770px;height: 168px;"></div>
   		 	</div>
   		 	<div title="近一月终端应用请求量Top10">
				<div id="zdyyqqlRank" style="width: 770px;height: 168px;"></div>
   		 	</div>
   		 	<div title="近一月服务资源响应时间Top10">
				<div id="fwzyxysjRank" style="width: 770px;height: 168px;"></div>
   		 	</div>
   		 	<div title="近一月服务资源异常量Top10">
				<div id="fwzyyclRank" style="width: 770px;height: 168px;"></div>
   		 	</div>
   		 	<div title="近一月服务资源使用高峰时段Top10">
				<div id="fwzysygfsdRank" style="width: 770px;height: 168px;"></div>
   		 	</div>
		</div>
		<div class="homeLeft06" style="border:1px solid #95B8E7;height:197px;width: 25%;">
			<h5 class="hStyle" style="background-color: #E0ECFF;border-bottom:1px solid #95B8E7;">
				<span>近一月单位报文采集量Top5</span>
			</h5>
			<ul class="orderUl" id="bwcjlRankByJg" style="height: 168px;background-color: #ffffff;"></ul>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>
<body>