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

data = [["2000-06-05",116],["2000-06-06",129],["2000-06-07",135],["2000-06-08",86],["2000-06-09",73],["2000-06-10",85],["2000-06-11",73],["2000-06-12",68],["2000-06-13",92],["2000-06-14",130],["2000-06-15",245],["2000-06-16",139],["2000-06-17",115],["2000-06-18",111],["2000-06-19",309],["2000-06-20",206],["2000-06-21",137],["2000-06-22",128],["2000-06-23",85],["2000-06-24",94],["2000-06-25",71],["2000-06-26",106],["2000-06-27",84],["2000-06-28",93],["2000-06-29",85],["2000-06-30",73],["2000-07-01",83],["2000-07-02",125],["2000-07-03",107],["2000-07-04",82],["2000-07-05",44],["2000-07-06",72],["2000-07-07",106],["2000-07-08",107],["2000-07-09",66],["2000-07-10",91],["2000-07-11",92],["2000-07-12",113],["2000-07-13",107],["2000-07-14",131],["2000-07-15",111],["2000-07-16",64],["2000-07-17",69],["2000-07-18",88],["2000-07-19",77],["2000-07-20",83],["2000-07-21",111],["2000-07-22",57],["2000-07-23",55],["2000-07-24",60]];

var dateList = data.map(function (item) {
    return item[0];
});
var valueList = data.map(function (item) {
    return item[1];
});

option = {

    // Make gradient line here
    visualMap: [{
        show: false,
        type: 'continuous',
        seriesIndex: 0,
        min: 0,
        max: 400
    }, {
        show: false,
        type: 'continuous',
        seriesIndex: 1,
        dimension: 0,
        min: 0,
        max: dateList.length - 1
    }],


    title: [{
        left: 'center',
        text: 'weblogics'
    }, {
        top: '55%',
        left: 'center',
        text: 'tomcate'
    }],
    tooltip: {
        trigger: 'axis'
    },
    xAxis: [{
        data: dateList
    }, {
        data: dateList,
        gridIndex: 1
    }],
    yAxis: [{
        splitLine: {show: false}
    }, {
        splitLine: {show: false},
        gridIndex: 1
    }],
    grid: [{
        bottom: '60%'
    }, {
        top: '60%'
    }],
    series: [{
        type: 'line',
        showSymbol: false,
        data: valueList
    }, {
        type: 'line',
        showSymbol: false,
        data: valueList,
        xAxisIndex: 1,
        yAxisIndex: 1
    }]
};
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