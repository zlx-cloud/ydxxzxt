$(function() {
	initCurrentActiveServiceCharts();
	initServiceUseByOrgCharts();
	initServiceUseTopTenCharts();
	initServiceUseByDayCharts();
	initServiceUseByOrgAndDayCharts();
	initServiceUseByWeekCharts();
});
function initCurrentActiveServiceCharts() {
	$.ajax({
		type : "POST",
		dataType : "json",
		url : "/ydxxzxt/monitoring/getCurrentActiveServiceCharts",
		success : function(data) {
			var x = [];
			var y = [];
			for (var i = 0; i < data.length; i++) {
				x.push(data[i].Z);
				y.push(data[i].COUNT);
			}
			// 当前活跃服务
			var myChart = echarts.init(document.getElementById('currentActiveServiceCharts'));
			option = {
				color : [ '#3398DB' ],
				title : {
					text : '当前活跃服务',
					subtext : '5分钟内服务参与方及服务调用个数统计',
					x : 'center'
				},
				toolbox : {
					show : true,
					feature : {
						restore : {},
						saveAsImage : {}
					}
				},
				tooltip : {
					trigger : 'axis',
					axisPointer : { // 坐标轴指示器，坐标轴触发有效
						type : 'line' // 默认为直线，可选为：'line' | 'shadow'
					}
				},
				grid : {
					left : '3%',
					right : '4%',
					bottom : '3%',
					containLabel : true
				},
				xAxis : [ {
					type : 'category',
					data : x,
					axisTick : {
						alignWithLabel : true
					}
				} ],
				yAxis : [ {
					type : 'value'
				} ],
				series : [ {
					name : '个数',
					type : 'bar',
					barWidth : '60%',
					data : y
				} ]
			};
			myChart.setOption(option);
		}
	});
}
// 各分局服务调用量统计
function initServiceUseByOrgCharts() {
	$.ajax({
		type : "POST",
		dataType : "json",
		url : "/ydxxzxt/monitoring/getServiceUseByOrgCharts",
		success : function(data) {

			var x = [];
			var datas = [];
			for (var i = 0; i < data.length; i++) {
				x.push(data[i].Z);
				var map = {};
				if (data[i].COUNT == null) {
					map['value'] = 0;
				} else {
					map['value'] = data[i].COUNT;
				}
				map['name'] = data[i].Z;
				datas.push(map);
			}
			var myChart = echarts.init(document.getElementById('serviceUseByOrgCharts'));
			option = {
				title : {
					text : '各分局服务调用量占比统计',
					subtext : '当天各分局服务调用量占比统计',
					x : 'center'
				},
				grid : {
					left : '3%',
					right : '4%',
					bottom : '3%',
					containLabel : true
				},
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b} : {c} ({d}%)"
				},
				toolbox : {
					show : true,
					feature : {
						restore : {},
						saveAsImage : {}
					}
				},
				legend : {
					orient : 'vertical',
					left : 'left',
					data : x
				},
				series : [ {
					name : '机构及服务调用量:',
					type : 'pie',
					radius : '55%',
					center : [ '60%', '60%' ],
					data : datas,
					itemStyle : {
						emphasis : {
							shadowBlur : 10,
							shadowOffsetX : 0,
							shadowColor : 'rgba(0, 0, 0, 0.5)'
						}
					}
				} ]
			};
			myChart.setOption(option);
		}
	});
}

// <!-- 服务调用Top10 -->
function initServiceUseTopTenCharts() {
	$.ajax({
		type : "POST",
		dataType : "json",
		url : "/ydxxzxt/monitoring/getServiceUseTopTenCharts",
		success : function(data) {

			var x = [];
			var datas = [];
			for (var i = 0; i < data.length; i++) {
				x.push(data[i].Z);
				var map = {};
				if (data[i].COUNT == null) {
					map['value'] = 0;
				} else {
					map['value'] = data[i].COUNT;
				}
				map['name'] = data[i].Z;
				datas.push(map);
			}
			var myChart = echarts.init(document.getElementById('serviceUseTopTenCharts'));
			option = {
				color : [ '#3398DB' ],
				title : {
					text : '各分局服务调用量Top10',
					subtext : '当天各分局服务调用量Top10',
					x : 'center'
				},
				toolbox : {
					show : true,
					feature : {
						restore : {},
						saveAsImage : {}
					}
				},
				tooltip : {
					trigger : 'axis',
					axisPointer : { // 坐标轴指示器，坐标轴触发有效
						type : 'line' // 默认为直线，可选为：'line' | 'shadow'
					}
				},
				grid : {
					left : '3%',
					right : '4%',
					bottom : '3%',
					containLabel : true
				},
				xAxis : [ {
					type : 'category',
					data : x,
					axisTick : {
						alignWithLabel : true
					}
				} ],
				yAxis : [ {
					type : 'value'
				} ],
				series : [ {
					name : '个数',
					type : 'bar',
					barWidth : '60%',
					data : datas
				} ]
			};
			myChart.setOption(option);
		}
	});
}
// <!-- 各分局服务日调用量统计 -->
function initServiceUseByDayCharts() {
	$.ajax({
		type : "POST",
		dataType : "json",
		url : "/ydxxzxt/monitoring/getServiceUseByDayCharts",
		success : function(data) {
			var x = [];
			var y = [];
			for (var i = 0; i < data.length; i++) {
				x.push(data[i].TIME);
				y.push(data[i].COUNT);
			}
			var myChart = echarts.init(document.getElementById('serviceUseByDayCharts'));
			option = {
				title : {
					text : '服务日调用量统计',
					subtext : '近七天服务日调用量统计',
					x : 'center'
				},
				tooltip : {
					trigger : 'axis'
				},
				toolbox : {
					show : true,
					feature : {
						restore : {},
						saveAsImage : {}
					}
				},
				grid : {
					left : '3%',
					right : '4%',
					bottom : '3%',
					containLabel : true
				},
				xAxis : {
					type : 'category',
					boundaryGap : true,
					data : x,
				},
				yAxis : {
					type : 'value',
					axisLabel : {
						formatter : '{value}'
					}
				},
				series : [ {
					name : '调用量',
					type : 'line',
					data : y,
					markPoint : {
						data : [ {
							type : 'max',
							name : '最大值'
						}, {
							type : 'min',
							name : '最小值'
						} ]
					},
					markLine : {
						data : [ {
							type : 'average',
							name : '平均值'
						} ]
					}
				}, ]
			};
			myChart.setOption(option);
		}
	});
}

// <!-- 各分局服务日调用量统计 -->
function initServiceUseByOrgAndDayCharts() {
	$.ajax({
		type : "POST",
		dataType : "json",
		url : "/ydxxzxt/monitoring/getServiceUseByOrgAndDayCharts",
		success : function(data) {
			var days =[];
			for (var i = 0; i < data.length; i++) {
				if (days.indexOf(data[i].Z) == -1) {
					days.push(data[i].Z);
				}
			}
			var jgs =[];
			for (var i = 0; i < data.length; i++) {
				if (jgs.indexOf(data[i].X) == -1) {
					jgs.push(data[i].X);
				}
			}
			
			var datas =[];
			for (var k = 0; k < jgs.length; k++) {
				var map={};
				map['name'] = jgs[k];
				map['type'] = 'line';
				var count=[];
				for (var j = 0; j < days.length; j++) {
					for (var i = 0; i < data.length; i++) {
						if(jgs[k]==data[i].X&&days[j]==data[i].Z){
								count.push(data[i].COUNT);
						}
					}
				}
				map['data'] =count;
				datas.push(map);
			}	
			
			
			//console.log(datas);

			var myChart = echarts.init(document.getElementById('serviceUseByOrgAndDayCharts'));
			option = {
				title : {
					text : '各分局服务日调用量统计',
					x : 'center'
				},
				toolbox : {
					show : true,
					feature : {
						restore : {},
						saveAsImage : {}
					}
				},
				tooltip : {
					trigger : 'axis'
				},
				grid : {
					top : '10%',
					left : '3%',
					right : '4%',
					bottom : '3%',
					containLabel : true
				},
				xAxis : {
					type : 'category',
					data : days
				},
				yAxis : {
					type : 'value'
				},
				series : datas
			};
			myChart.setOption(option);
		}
	});
}

// <!-- 服务周调用量统计 -->
function initServiceUseByWeekCharts() {
	$.ajax({
		type : "POST",
		dataType : "json",
		url : "/ydxxzxt/monitoring/getServiceUseByWeekCharts",
		success : function(data) {
			var myChart = echarts.init(document.getElementById('serviceUseByWeekCharts'));
			option = {
				title : {
					text : '服务周调用量统计',
					subtext : '近四个自然周服务周调用量统计',
					x : 'center'
				},
				tooltip : {
					trigger : 'axis'
				},
				toolbox : {
					show : true,
					feature : {
						restore : {},
						saveAsImage : {}
					}
				},
				grid : {
					left : '3%',
					right : '4%',
					bottom : '3%',
					containLabel : true
				},
				xAxis : {
					type : 'category',
					boundaryGap : true,
					data : data[0].X,
				},
				yAxis : {
					type : 'value',
					axisLabel : {
						formatter : '{value}'
					}
				},
				series : [ {
					name : '调用量',
					type : 'line',
					data : data[0].Y,
					markPoint : {
						data : [ {
							type : 'max',
							name : '最大值'
						}, {
							type : 'min',
							name : '最小值'
						} ]
					},
					markLine : {
						data : [ {
							type : 'average',
							name : '平均值'
						} ]
					}
				}, ]
			};
			myChart.setOption(option);
		}
	});
}