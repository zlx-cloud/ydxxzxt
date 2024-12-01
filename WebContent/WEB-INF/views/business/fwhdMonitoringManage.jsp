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
<title>总线监控</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <%-- <script src="${ctx}/static/common/js/jquery.min.js-v=2.1.4"></script> --%>
<script type="text/javascript" src="${ctx}/static/js/jquery-1.10.1.min.js"></script>
    <link href="${ctx}/static/common/css/bootstrap.min.css-v=3.3.5.css"  rel="stylesheet">
    <link href="${ctx}/static/common/css/font-awesome.min.css-v=4.4.0.css"  rel="stylesheet">
    <link href="${ctx}/static/common/css/animate.min.css"  rel="stylesheet">
    <link href="${ctx}/static/common/css/style.min.css-v=4.0.0.css"  rel="stylesheet">
     <script src="${ctx}/static/common/js/bootstrap.min.js-v=3.3.5" ></script>
     
<script type="text/javascript" src="${ctx}/static/js/echarts.js"></script>
<script type="text/javascript">
$(document).ready(function() {
var charts = echarts.init(document.getElementById('charts'));

function randomData() {
    now = new Date(+now + oneDay);
    value = value + Math.random() * 21 - 10;
    return {
        name: now.toString(),
        value: [
            [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
            Math.round(value)
        ]
    }
}

var data = [];
var now = +new Date(1997, 9, 3);
var oneDay = 24 * 3600 * 1000;
var value = Math.random() * 1000;
for (var i = 0; i < 1000; i++) {
    data.push(randomData());
}

option = {
    title: {
        text: '服务活动监测'
    },
    tooltip: {
        trigger: 'axis',
        formatter: function (params) {
            params = params[0];
            var date = new Date(params.name);
            return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
        },
        axisPointer: {
            animation: false
        }
    },
    xAxis: {
        type: 'time',
        splitLine: {
            show: false
        }
    },
    yAxis: {
        type: 'value',
        boundaryGap: [0, '100%'],
        splitLine: {
            show: false
        }
    },
    series: [{
        name: '模拟数据',
        type: 'line',
        showSymbol: false,
        hoverAnimation: false,
        data: data
    }]
};

setInterval(function () {

    for (var i = 0; i < 5; i++) {
        data.shift();
        data.push(randomData());
    }

    charts.setOption({
        series: [{
            data: data
        }]
    });
}, 1000);
 charts.setOption(option);
});
</script>
</head>
<body class="top-navigation">
 <div class="wrapper wrapper-content  animated fadeInRight"
		style="padding: 0px;">
<div class="row">
<div class="col-sm-12">
<div id="charts" style="height:460px; width:100%"></div>
</div>
</div>
</div>
</body>
</html>