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

 option = {
	    title: {
	        text: '总线活动监测',
	       
	    },
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['jvm堆内存','jvm线程']
	    },
	    toolbox: {
	        show: true,
	        feature: {
	            dataZoom: {
	                yAxisIndex: 'none'
	            },
	            dataView: {readOnly: false},
	            magicType: {type: ['line', 'bar']},
	            restore: {},
	            saveAsImage: {}
	        }
	    },
	    xAxis:  {
	        type: 'category',
	        boundaryGap: false,
	        data: ['15:03','15:04','15:05','15:06','15:07','15:08','15:09']
	    },
	    yAxis: {
	        type: 'value',
	        axisLabel: {
	            formatter: '{value} '
	        }
	    },
	    series: [
	        {
	            name:'jvm堆内存',
	            type:'line',
	            data:[50, 150, 200, 250, 300, 350, 200],
	            /*  markPoint: {
	                data: [
	                    {type: 'max', name: '最大值'},
	                    {type: 'min', name: '最小值'}
	                ]
	            },
	            markLine: {
	                data: [
	                    {type: 'average', name: '平均值'}
	                ]
	            } */
	        },
	        {
	            name:'jvm线程',
	            type:'line',
	            data:[20, 60, 80, 80, 120, 160, 150],
	            /* markPoint: {
	                data: [
	                    {name: '周最低', value: -2, xAxis: 1, yAxis: -1.5}
	                ]
	            }, 
	            markLine: {
	                data: [
	                    {type: 'average', name: '平均值'},
	                    [{
	                        symbol: 'none',
	                        x: '90%',
	                        yAxis: 'max'
	                    }, {
	                        symbol: 'circle',
	                        label: {
	                            normal: {
	                                position: 'start',
	                                formatter: '最大值'
	                            }
	                        },
	                        type: 'max',
	                        name: '最高点'
	                    }]
	                ]
	            }*/
	        }
	    ]
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