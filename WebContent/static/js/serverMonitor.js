$(function() {
	initServer();
	initServer1();
	initServer2();
	initServer3();
	initServer4();
});

function initServer() {
	$.ajax({
		type : "POST",
		dataType : "json",
		async :true,
		url : "/ydxxzxt/monitoring/getServer",
		success : function(data) {
			var cpuRate=data[0].sysCpuUse.substr(0,data[0].sysCpuUse.length-3);
			
			var memFree	= parseInt(data[1].memFree);
			var memTotal= parseInt(data[1].memTotal);
			var memUsed	= parseInt(data[1].memUsed);
			var memRate = memUsed/memTotal*100;
			memRate=Number(memRate.toString().match(/^\d+(?:\.\d{0,2})?/));
			
			var swapFree	= parseInt(data[2].swapFree);
			var swapTotal= parseInt(data[2].swapTotal);
			var swapUsed	= parseInt(data[2].swapUsed);
			var swapRate = swapUsed/swapTotal*100;
			swapRate=Number(swapRate.toString().match(/^\d+(?:\.\d{0,2})?/));
			
			var netUse=data[3].netUse;
			
			var myChart = echarts.init(document.getElementById('server'));
			var datas = {
				title : '仪表盘',
				color : {
					pieMini : '#ffca1c', // 小圆形颜色
					pieMiniMini : '#fff', // 小小圆形颜色
					piePlus : '#5DD1FA', // 大圆形颜色
					value : '#687284', // 底部数值颜色
				},
			}
			var option = {
				title : [ {
					text : 'CPU使用率',
					top : '58%',
					left : '6%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				}, {
					text : '内存使用率',
					top : '58%',
					left : '31%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				}, {
					text : '缓存使用率',
					top : '58%',
					left : '56%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				}, {
					text : 'IO占用率',
					top : '58%',
					left : '82%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				} ],
				backgroundColor : "#7FDBFF",
				series : [
						{
							name : '刻度1',
							type : 'gauge',
							radius : '50%',
							center : [ '10%', '50%' ],
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘1",
							"type" : "gauge",
							radius : '42%',
							center : [ '10%', '50%' ],
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ cpuRate/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : cpuRate,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						},

						{
							name : '刻度2',
							type : 'gauge',
							center : [ '35%', '50%' ],
							radius : '50%',
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘2",
							"type" : "gauge",
							center : [ '35%', '50%' ],
							radius : '42%',
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ memRate/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : memRate,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						},

						{
							name : '刻度3',
							type : 'gauge',
							center : [ '60%', '50%' ],
							radius : '50%',
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘3",
							"type" : "gauge",
							center : [ '60%', '50%' ],
							radius : '42%',
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ swapRate/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : swapRate,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						},
						{
							name : '刻度4',
							type : 'gauge',
							center : [ '85%', '50%' ],
							radius : '50%',
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘4",
							"type" : "gauge",
							center : [ '85%', '50%' ],
							radius : '42%',
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ netUse/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : netUse,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						}

				]
			};
			myChart.setOption(option);
		}
	});
}



function initServer1() {
	$.ajax({
		type : "POST",
		dataType : "json",
		async :true,
		url : "/ydxxzxt/monitoring/getServer1",
		success : function(data) {
			var cpuRate=data[0].sysCpuUse.substr(0,data[0].sysCpuUse.length-3);
			
			var memFree	= parseInt(data[1].memFree);
			var memTotal= parseInt(data[1].memTotal);
			var memUsed	= parseInt(data[1].memUsed);
			var memRate = memUsed/memTotal*100;
			memRate=Number(memRate.toString().match(/^\d+(?:\.\d{0,2})?/));
			
			var swapFree	= parseInt(data[2].swapFree);
			var swapTotal= parseInt(data[2].swapTotal);
			var swapUsed	= parseInt(data[2].swapUsed);
			var swapRate = swapUsed/swapTotal*100;
			swapRate=Number(swapRate.toString().match(/^\d+(?:\.\d{0,2})?/));
			
			var netUse=data[3].netUse;
			
			var myChart = echarts.init(document.getElementById('server1'));
			var datas = {
				title : '仪表盘',
				color : {
					pieMini : '#ffca1c', // 小圆形颜色
					pieMiniMini : '#fff', // 小小圆形颜色
					piePlus : '#5DD1FA', // 大圆形颜色
					value : '#687284', // 底部数值颜色
				},
			}
			var option = {
				title : [ {
					text : 'CPU使用率',
					top : '58%',
					left : '6%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				}, {
					text : '内存使用率',
					top : '58%',
					left : '31%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				}, {
					text : '缓存使用率',
					top : '58%',
					left : '56%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				}, {
					text : 'IO占用率',
					top : '58%',
					left : '82%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				} ],
				backgroundColor : "#7FDBFF",
				series : [
						{
							name : '刻度1',
							type : 'gauge',
							radius : '50%',
							center : [ '10%', '50%' ],
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘1",
							"type" : "gauge",
							radius : '42%',
							center : [ '10%', '50%' ],
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ cpuRate/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : cpuRate,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						},

						{
							name : '刻度2',
							type : 'gauge',
							center : [ '35%', '50%' ],
							radius : '50%',
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘2",
							"type" : "gauge",
							center : [ '35%', '50%' ],
							radius : '42%',
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ memRate/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : memRate,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						},

						{
							name : '刻度3',
							type : 'gauge',
							center : [ '60%', '50%' ],
							radius : '50%',
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘3",
							"type" : "gauge",
							center : [ '60%', '50%' ],
							radius : '42%',
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ swapRate/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : swapRate,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						},
						{
							name : '刻度4',
							type : 'gauge',
							center : [ '85%', '50%' ],
							radius : '50%',
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘4",
							"type" : "gauge",
							center : [ '85%', '50%' ],
							radius : '42%',
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ netUse/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : netUse,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						}

				]
			};
			myChart.setOption(option);
		}
	});
}


function initServer2() {
	$.ajax({
		type : "POST",
		dataType : "json",
		async :true,
		url : "/ydxxzxt/monitoring/getServer2",
		success : function(data) {
			var cpuRate=data[0].sysCpuUse.substr(0,data[0].sysCpuUse.length-3);
			
			var memFree	= parseInt(data[1].memFree);
			var memTotal= parseInt(data[1].memTotal);
			var memUsed	= parseInt(data[1].memUsed);
			var memRate = memUsed/memTotal*100;
			memRate=Number(memRate.toString().match(/^\d+(?:\.\d{0,2})?/));
			
			var swapFree	= parseInt(data[2].swapFree);
			var swapTotal= parseInt(data[2].swapTotal);
			var swapUsed	= parseInt(data[2].swapUsed);
			var swapRate = swapUsed/swapTotal*100;
			swapRate=Number(swapRate.toString().match(/^\d+(?:\.\d{0,2})?/));
			
			var netUse=data[3].netUse;
			
			var myChart = echarts.init(document.getElementById('server2'));
			var datas = {
				title : '仪表盘',
				color : {
					pieMini : '#ffca1c', // 小圆形颜色
					pieMiniMini : '#fff', // 小小圆形颜色
					piePlus : '#5DD1FA', // 大圆形颜色
					value : '#687284', // 底部数值颜色
				},
			}
			var option = {
				title : [ {
					text : 'CPU使用率',
					top : '58%',
					left : '6%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				}, {
					text : '内存使用率',
					top : '58%',
					left : '31%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				}, {
					text : '缓存使用率',
					top : '58%',
					left : '56%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				}, {
					text : 'IO占用率',
					top : '58%',
					left : '82%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				} ],
				backgroundColor : "#7FDBFF",
				series : [
						{
							name : '刻度1',
							type : 'gauge',
							radius : '50%',
							center : [ '10%', '50%' ],
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘1",
							"type" : "gauge",
							radius : '42%',
							center : [ '10%', '50%' ],
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ cpuRate/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : cpuRate,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						},

						{
							name : '刻度2',
							type : 'gauge',
							center : [ '35%', '50%' ],
							radius : '50%',
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘2",
							"type" : "gauge",
							center : [ '35%', '50%' ],
							radius : '42%',
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ memRate/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : memRate,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						},

						{
							name : '刻度3',
							type : 'gauge',
							center : [ '60%', '50%' ],
							radius : '50%',
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘3",
							"type" : "gauge",
							center : [ '60%', '50%' ],
							radius : '42%',
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ swapRate/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : swapRate,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						},
						{
							name : '刻度4',
							type : 'gauge',
							center : [ '85%', '50%' ],
							radius : '50%',
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘4",
							"type" : "gauge",
							center : [ '85%', '50%' ],
							radius : '42%',
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ netUse/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : netUse,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						}

				]
			};
			myChart.setOption(option);
		}
	});
}



function initServer3() {
	$.ajax({
		type : "POST",
		dataType : "json",
		async :true,
		url : "/ydxxzxt/monitoring/getServer3",
		success : function(data) {
			var cpuRate=data[0].sysCpuUse.substr(0,data[0].sysCpuUse.length-3);
			
			var memFree	= parseInt(data[1].memFree);
			var memTotal= parseInt(data[1].memTotal);
			var memUsed	= parseInt(data[1].memUsed);
			var memRate = memUsed/memTotal*100;
			memRate=Number(memRate.toString().match(/^\d+(?:\.\d{0,2})?/));
			
			var swapFree	= parseInt(data[2].swapFree);
			var swapTotal= parseInt(data[2].swapTotal);
			var swapUsed	= parseInt(data[2].swapUsed);
			var swapRate = swapUsed/swapTotal*100;
			swapRate=Number(swapRate.toString().match(/^\d+(?:\.\d{0,2})?/));
			
			var netUse=data[3].netUse;
			
			var myChart = echarts.init(document.getElementById('server3'));
			var datas = {
				title : '仪表盘',
				color : {
					pieMini : '#ffca1c', // 小圆形颜色
					pieMiniMini : '#fff', // 小小圆形颜色
					piePlus : '#5DD1FA', // 大圆形颜色
					value : '#687284', // 底部数值颜色
				},
			}
			var option = {
				title : [ {
					text : 'CPU使用率',
					top : '58%',
					left : '6%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				}, {
					text : '内存使用率',
					top : '58%',
					left : '31%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				}, {
					text : '缓存使用率',
					top : '58%',
					left : '56%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				}, {
					text : 'IO占用率',
					top : '58%',
					left : '82%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				} ],
				backgroundColor : "#7FDBFF",
				series : [
						{
							name : '刻度1',
							type : 'gauge',
							radius : '50%',
							center : [ '10%', '50%' ],
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘1",
							"type" : "gauge",
							radius : '42%',
							center : [ '10%', '50%' ],
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ cpuRate/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : cpuRate,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						},

						{
							name : '刻度2',
							type : 'gauge',
							center : [ '35%', '50%' ],
							radius : '50%',
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘2",
							"type" : "gauge",
							center : [ '35%', '50%' ],
							radius : '42%',
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ memRate/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : memRate,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						},

						{
							name : '刻度3',
							type : 'gauge',
							center : [ '60%', '50%' ],
							radius : '50%',
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘3",
							"type" : "gauge",
							center : [ '60%', '50%' ],
							radius : '42%',
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ swapRate/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : swapRate,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						},
						{
							name : '刻度4',
							type : 'gauge',
							center : [ '85%', '50%' ],
							radius : '50%',
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘4",
							"type" : "gauge",
							center : [ '85%', '50%' ],
							radius : '42%',
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ netUse/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : netUse,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						}

				]
			};
			myChart.setOption(option);
		}
	});
}

function initServer4() {
	$.ajax({
		type : "POST",
		dataType : "json",
		async :true,
		url : "/ydxxzxt/monitoring/getServer4",
		success : function(data) {
			var cpuRate=data[0].sysCpuUse.substr(0,data[0].sysCpuUse.length-3);
			
			var memFree	= parseInt(data[1].memFree);
			var memTotal= parseInt(data[1].memTotal);
			var memUsed	= parseInt(data[1].memUsed);
			var memRate = memUsed/memTotal*100;
			memRate=Number(memRate.toString().match(/^\d+(?:\.\d{0,2})?/));
			
			var swapFree	= parseInt(data[2].swapFree);
			var swapTotal= parseInt(data[2].swapTotal);
			var swapUsed	= parseInt(data[2].swapUsed);
			var swapRate = swapUsed/swapTotal*100;
			swapRate=Number(swapRate.toString().match(/^\d+(?:\.\d{0,2})?/));
			
			var netUse=data[3].netUse;
			
			var myChart = echarts.init(document.getElementById('server4'));
			var datas = {
				title : '仪表盘',
				color : {
					pieMini : '#ffca1c', // 小圆形颜色
					pieMiniMini : '#fff', // 小小圆形颜色
					piePlus : '#5DD1FA', // 大圆形颜色
					value : '#687284', // 底部数值颜色
				},
			}
			var option = {
				title : [ {
					text : 'CPU使用率',
					top : '58%',
					left : '6%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				}, {
					text : '内存使用率',
					top : '58%',
					left : '31%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				}, {
					text : '缓存使用率',
					top : '58%',
					left : '56%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				}, {
					text : 'IO占用率',
					top : '58%',
					left : '82%',
					padding : [ -30, 0 ],
					textStyle : {
						color : '#fff',
						fontSize : 18,
						align : 'center'
					}
				} ],
				backgroundColor : "#7FDBFF",
				series : [
						{
							name : '刻度1',
							type : 'gauge',
							radius : '50%',
							center : [ '10%', '50%' ],
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘1",
							"type" : "gauge",
							radius : '42%',
							center : [ '10%', '50%' ],
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ cpuRate/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : cpuRate,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						},

						{
							name : '刻度2',
							type : 'gauge',
							center : [ '35%', '50%' ],
							radius : '50%',
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘2",
							"type" : "gauge",
							center : [ '35%', '50%' ],
							radius : '42%',
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ memRate/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : memRate,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						},

						{
							name : '刻度3',
							type : 'gauge',
							center : [ '60%', '50%' ],
							radius : '50%',
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘3",
							"type" : "gauge",
							center : [ '60%', '50%' ],
							radius : '42%',
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ swapRate/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : swapRate,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						},
						{
							name : '刻度4',
							type : 'gauge',
							center : [ '85%', '50%' ],
							radius : '50%',
							min : 0,// 最小刻度
							max : 100,// 最大刻度
							splitNumber : 10, // 刻度数量
							startAngle : 225,
							endAngle : -45,
							axisLine : {
								show : true,
								lineStyle : {
									width : 1,
									color : [ [ 1, 'rgba(0,0,0,0)' ] ]
								}
							},// 仪表盘轴线
							axisLabel : {
								show : true,
								color : '#4d5bd1',
								distance : 25,
								formatter : function(v) {
									switch (v + '') {
									case '0':
										return '0';
									case '10':
										return '10';
									case '20':
										return '20';
									case '30':
										return '30';
									case '40':
										return '40';
									case '50':
										return '50';
									case '60':
										return '60';
									case '70':
										return '70';
									case '80':
										return '80';
									case '90':
										return '90';
									case '100':
										return '100';
									}
								}
							},// 刻度标签。
							axisTick : {
								show : true,
								splitNumber : 7,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
									width : 1,
								},
								length : -8
							},// 刻度样式
							splitLine : {
								show : true,
								length : -20,
								lineStyle : {
									color : '#5c53de', // 用颜色渐变函数不起作用
								}
							},// 分隔线样式
							detail : {
								show : false
							},
							pointer : {
								show : false
							}
						},
						{
							"name" : "仪表盘4",
							"type" : "gauge",
							center : [ '85%', '50%' ],
							radius : '42%',
							"splitNumber" : 10,
							"axisLine" : {
								"lineStyle" : {
									"color" : [
											[ netUse/ 100, "#BF18FE" ],
											[ 1, "#111F42" ] ],
									"width" : 8
								}
							},
							axisLabel : {
								show : false,
							},
							"axisTick" : {
								show : false,

							},
							"splitLine" : {
								"show" : false,
							},
							"itemStyle" : {
								show : false,
							},
							"detail" : {
								"formatter" : function(value) {
									if (value !== 0) {
										return value+ "%";
									} else {
										return 0;
									}
								},
								"offsetCenter" : [ 0, "70%" ],
								"textStyle" : {
									padding : [ 0, 0, 80, 0 ],
									"fontSize" : 18,
									fontWeight : '700',
									"color" : datas.color.value || '#83af98'
								}
							},
							"title" : {
								color : '#fff',
								"fontSize" : 10,
								"offsetCenter" : [ 0, "-25%" ]
							},
							"data" : [ {
								"value" : netUse,
							} ],
							pointer : {
								show : false,
								length : '75%',
								width : 20, // 指针粗细
							},
						}

				]
			};
			myChart.setOption(option);
		}
	});
}