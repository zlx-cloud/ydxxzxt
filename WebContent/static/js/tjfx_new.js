$(function() {
	tjsz();
	zcyhlRankByTimeAndJg();
	yhdllRankByTimeAndJg();
	fwzclByJg();
	fwzyRankByCzlx();
	fwzyRankByYy();
	bwcjlRankByJg();
	fwzyRankByJzfl();
	qqlRankBySjAndJg();
	fwzydylRank();
	yyxtdylRank();
	zdyyqqlRank();
	fwzyyclRank();
	fwzysygfsdRank();
	fwzyxysjRank();
});
//统计数字
function tjsz() {
	$.ajax({
		type : "post",
		url : "/ydxxzxt/tjReport/tjsz",
		async : true,
		dataType : "json",
		success : function(data) {
			for (var i = 0; i < data.length; i++) {
				$("#" + data[i].NAME).html(data[i].COUNT);
			}
		}
	});
}
//单位时间各单位注册用户量统计及排名
function zcyhlRankByTimeAndJg(){
	$.ajax({
		type : "post",
		url : "/ydxxzxt/tjReport/zcyhlRankByTimeAndJg",
		async : true,
		dataType : "json",
		success : function(data) {
			var html = "";
			for (var i = 0; i < data.length; i++) {
				html += '<li>'+data[i].JGMC+'<span>'+data[i].COUNT+'个</span></li>';
			}
			$("#zcyhlRankByTimeAndJg").append(html);
		}
	});
}
//单位时间各单位用户登录量统计及排名
function yhdllRankByTimeAndJg(){
	$.ajax({
		type : "post",
		url : "/ydxxzxt/tjReport/yhdllRankByTimeAndJg",
		async : true,
		dataType : "json",
		success : function(data) {
			var html = "";
			for (var i = 0; i < data.length; i++) {
				html += '<li>'+data[i].JGMC+'<span>'+data[i].COUNT+'次</span></li>';
			}
			$("#yhdllRankByTimeAndJg").append(html);
		}
	});
}
// 各单位服务注册统计及排名
function fwzclByJg(){
	$.ajax({
		type : "post",
		url : "/ydxxzxt/tjReport/fwzclByJg",
		async : true,
		dataType : "json",
		success : function(data) {
			var html = "";
			for (var i = 0; i < data.length; i++) {
				html += '<li>'+data[i].JGMC+'<span>'+data[i].COUNT+'个</span></li>';
			}
			$("#fwzclByJg").append(html);
		}
	});
}
// 现有、新增服务资源分类统计与排名
function fwzyRankByCzlx() {
	$.ajax({
		type : "post",
		url : "/ydxxzxt/tjReport/fwzyRankByCzlx",
		async : true,
		dataType : "json",
		success : function(data) {
			var czfl = [];
			var all_count = [];
			var month_count = [];
			for (var i = 0; i < data.length; i++) {
				czfl.push(data[i].CZFL);
				all_count.push(data[i].ALL_COUNT);
				month_count.push(data[i].MONTH_COUNT);
			}
			var myChart = echarts.init(document.getElementById('fwzyRankByCzlx'));
			option = {
				tooltip: {
					trigger: 'axis',
					axisPointer: {
						type: 'shadow'
					}
				},
				legend : {
					data : [ '现有量', '新增量' ]
				},
				grid: {
	                left: '10%',
	                right: '10%',
	                top: '20%',
	                bottom: '15%'
	            },
				calculable : true,
				xAxis : [ {
					type : 'category',
					data : czfl
				} ],
				yAxis : [ {
					type : 'value',
					axisLabel: {
		                formatter: '{value}'
		            }
				} ],
				series : [
						{
							name : '现有量',
							type : 'bar',
							data : all_count,
							itemStyle: {
				                   normal: {
				                       label: {
				                           show: true,
				                           position: 'top',
				                           textStyle: {
				                               color: 'black',
				                               fontSize: 12
				                           }
				                       }
				                   }
				               }
						},
						{
							name : '新增量',
							type : 'bar',
							data : month_count,
							itemStyle: {
				                   normal: {
				                       label: {
				                           show: true,
				                           position: 'top',
				                           textStyle: {
				                               color: 'black',
				                               fontSize: 12
				                           }
				                       }
				                   }
				               }
						} ]
			};
			myChart.setOption(option);
		}
	});
}
function fwzyRankByYy() {
	$.ajax({
		type : "post",
		url : "/ydxxzxt/tjReport/fwzyRankByYy",
		async : true,
		dataType : "json",
		success : function(data) {
			var yymc = [];
			var all_count = [];
			var month_count = [];
			for (var i = 0; i < data.length; i++) {
				yymc.push(data[i].YYMC);
				all_count.push(data[i].ALL_COUNT);
				month_count.push(data[i].MONTH_COUNT);
			}
			var myChart = echarts.init(document.getElementById('fwzyRankByYy'));
			option = {
				tooltip: {
					trigger: 'axis',
					axisPointer: {
						type: 'shadow'
					}
				},
				legend : {
					data : [ '现有量', '新增量' ]
				},
				grid: {
	                left: '10%',
	                right: '10%',
	                top: '20%',
	                bottom: '15%'
	            },
				calculable : true,
				xAxis : [ {
					type : 'category',
					data : yymc,
					axisLabel: {
                        formatter: function(value) {
                           var res = value;
                           if(res.length > 4) {
                               res = res.substring(0, 3) + "..";
                           }
                           return res;
                       }
                   }
				} ],
				yAxis : [ {
					type : 'value',
					axisLabel: {
		                formatter: '{value}'
		            }
				} ],
				series : [
						{
							name : '现有量',
							type : 'bar',
							data : all_count,
							itemStyle: {
				                   normal: {
				                       label: {
				                           show: true,
				                           position: 'top',
				                           textStyle: {
				                               color: 'black',
				                               fontSize: 12
				                           }
				                       }
				                   }
				               }
						},
						{
							name : '新增量',
							type : 'bar',
							data : month_count,
							itemStyle: {
				                   normal: {
				                       label: {
				                           show: true,
				                           position: 'top',
				                           textStyle: {
				                               color: 'black',
				                               fontSize: 12
				                           }
				                       }
				                   }
				               }
						} ]
			};
			myChart.setOption(option);
		}
	});
}
function fwzyRankByJzfl() {
	$.ajax({
		type : "post",
		url : "/ydxxzxt/tjReport/fwzyRankByJzfl",
		async : true,
		dataType : "json",
		success : function(data) {
			var jzlbmc = [];
			var all_count = [];
			var month_count = [];
			for (var i = 0; i < data.length; i++) {
				jzlbmc.push(data[i].JZLBMC);
				all_count.push(data[i].ALL_COUNT);
				month_count.push(data[i].MONTH_COUNT);
			}
			var myChart = echarts.init(document.getElementById('fwzyRankByJzfl'));
			option = {
				tooltip: {
					trigger: 'axis',
					axisPointer: {
						type: 'shadow'
					}
				},
				legend : {
					data : [ '现有量', '新增量' ]
				},
				grid: {
	                left: '10%',
	                right: '10%',
	                top: '20%',
	                bottom: '15%'
	            },
				calculable : true,
				xAxis : [ {
					type : 'category',
					data : jzlbmc,
					axisLabel: {
                        formatter: function(value) {
                           var res = value;
                           if(res.length > 4) {
                               res = res.substring(0, 3) + "..";
                           }
                           return res;
                       }
                   }
				} ],
				yAxis : [ {
					type : 'value',
					axisLabel: {
		                formatter: '{value}'
		            }
				} ],
				series : [
						{
							name : '现有量',
							type : 'bar',
							data : all_count,
							itemStyle: {
				                   normal: {
				                       label: {
				                           show: true,
				                           position: 'top',
				                           textStyle: {
				                               color: 'black',
				                               fontSize: 12
				                           }
				                       }
				                   }
				               }
						},
						{
							name : '新增量',
							type : 'bar',
							data : month_count,
							itemStyle: {
				                   normal: {
				                       label: {
				                           show: true,
				                           position: 'top',
				                           textStyle: {
				                               color: 'black',
				                               fontSize: 12
				                           }
				                       }
				                   }
				               }
						} ]
			};
			myChart.setOption(option);
		}
	});
}
//单位时间，各单位报文采集量统计及排名
function bwcjlRankByJg(){
	$.ajax({
		type : "post",
		url : "/ydxxzxt/tjReport/bwcjlRankByJg",
		async : true,
		dataType : "json",
		success : function(data) {
			var html = "";
			for (var i = 0; i < data.length; i++) {
				html += '<li>'+data[i].JGMC+'<span>'+data[i].COUNT+'次</span></li>';
			}
			$("#bwcjlRankByJg").append(html);
		}
	});
}

function qqlRankBySjAndJg(){
	$.ajax({
		type : "post",
		url : "/ydxxzxt/tjReport/qqlRankBySjAndJg",
		async : true,
		dataType : "json",
		success : function(data) {
			var names = [];
			var values = [];
			for (var i = 0; i < data.length; i++) {
				names.push(data[i].JGMC);
				values.push(data[i].COUNT);
			}
			var myChart = echarts.init(document.getElementById('qqlRankBySjAndJg'));
			option = {
				    color: ['#0074D9'],
				    tooltip: {
				        trigger: 'axis',
				        axisPointer: {
				            type: 'shadow'
				        }
				    },
				    grid: {
				        left: '3%',
				        right: '3%',
				        bottom: '3%',
				        top: '10%',
				        containLabel: true
				    },
				    xAxis: [
				        {
				            type: 'category',
				            axisLabel: {
		                        formatter: function(value) {
		                           var res = value;
		                           if(res.length > 4) {
		                               res = res.substring(0, 3) + "..";
		                           }
		                           return res;
		                       }
		                   },
				           data: names
				        }
				    ],
				    yAxis: [
				        {
				            type: 'value'
				        }
				    ],
				    series: [
				        {
				            name: '近一月请求量',
				            type: 'bar',
				            barWidth: '50%',
				            data: values,
				            itemStyle: {
				            	normal: {
				            		label: {
				            			show: true,
				            			position: 'top',
				                        textStyle: {
				                        	color: 'black',
				                        	fontSize: 12
				                        }
				            		}
				            	}
				            }
				        }
				    ]
				};
			myChart.setOption(option);
		}
	})
}
function fwzydylRank(){
	$.ajax({
		type : "post",
		url : "/ydxxzxt/tjReport/fwzydylRank",
		async : true,
		dataType : "json",
		success : function(data) {
			var names = [];
			var values = [];
			for (var i = 0; i < data.length; i++) {
				names.push(data[i].FFMS);
				values.push(data[i].COUNT);
			}
			var myChart = echarts.init(document.getElementById('fwzydylRank'));
			option = {
				    color: ['#FF851B'],
				    tooltip: {
				        trigger: 'axis',
				        axisPointer: {
				            type: 'shadow'
				        }
				    },
				    grid: {
				        left: '3%',
				        right: '3%',
				        bottom: '3%',
				        top: '10%',
				        containLabel: true
				    },
				    xAxis: [
				        {
				            type: 'category',
				            axisLabel: {
		                        formatter: function(value) {
		                           var res = value;
		                           if(res.length > 4) {
		                               res = res.substring(0, 3) + "..";
		                           }
		                           return res;
		                       }
		                   },
				           data: names
				        }
				    ],
				    yAxis: [
				        {
				            type: 'value'
				        }
				    ],
				    series: [
				        {
				            name: '近一月被调用量',
				            type: 'bar',
				            barWidth: '50%',
				            data: values,
				            itemStyle: {
				            	normal: {
				            		label: {
				            			show: true,
				            			position: 'top',
				                        textStyle: {
				                        	color: 'black',
				                        	fontSize: 12
				                        }
				            		}
				            	}
				            }
				        }
				    ]
				};
			myChart.setOption(option);
		}
	})
}
function yyxtdylRank(){
	$.ajax({
		type : "post",
		url : "/ydxxzxt/tjReport/yyxtdylRank",
		async : true,
		dataType : "json",
		success : function(data) {
			var names = [];
			var values = [];
			for (var i = 0; i < data.length; i++) {
				names.push(data[i].FWCYF_YYXTMC);
				values.push(data[i].COUNT);
			}
			var myChart = echarts.init(document.getElementById('yyxtdylRank'));
			option = {
				    color: ['#85144b'],
				    tooltip: {
				        trigger: 'axis',
				        axisPointer: {
				            type: 'shadow'
				        }
				    },
				    grid: {
				        left: '3%',
				        right: '3%',
				        bottom: '3%',
				        top: '10%',
				        containLabel: true
				    },
				    xAxis: [
				        {
				            type: 'category',
				            axisLabel: {
		                        formatter: function(value) {
		                           var res = value;
		                           if(res.length > 4) {
		                               res = res.substring(0, 3) + "..";
		                           }
		                           return res;
		                       }
		                   },
				           data: names
				        }
				    ],
				    yAxis: [
				        {
				            type: 'value'
				        }
				    ],
				    series: [
				        {
				            name: '近一月被调用量',
				            type: 'bar',
				            barWidth: '50%',
				            data: values,
				            itemStyle: {
				            	normal: {
				            		label: {
				            			show: true,
				            			position: 'top',
				                        textStyle: {
				                        	color: 'black',
				                        	fontSize: 12
				                        }
				            		}
				            	}
				            }
				        }
				    ]
				};
			myChart.setOption(option);
		}
	})
}
function zdyyqqlRank(){
	$.ajax({
		type : "post",
		url : "/ydxxzxt/tjReport/zdyyqqlRank",
		async : true,
		dataType : "json",
		success : function(data) {
			var names = [];
			var values = [];
			for (var i = 0; i < data.length; i++) {
				names.push(data[i].FWCYF_YYXTMC);
				values.push(data[i].COUNT);
			}
			var myChart = echarts.init(document.getElementById('zdyyqqlRank'));
			option = {
				    color: ['#01FF70'],
				    tooltip: {
				        trigger: 'axis',
				        axisPointer: {
				            type: 'shadow'
				        }
				    },
				    grid: {
				        left: '3%',
				        right: '3%',
				        bottom: '3%',
				        top: '10%',
				        containLabel: true
				    },
				    xAxis: [
				        {
				            type: 'category',
				            axisLabel: {
		                        formatter: function(value) {
		                           var res = value;
		                           if(res.length > 4) {
		                               res = res.substring(0, 3) + "..";
		                           }
		                           return res;
		                       }
		                   },
				           data: names
				        }
				    ],
				    yAxis: [
				        {
				            type: 'value'
				        }
				    ],
				    series: [
				        {
				            name: '近一月请求量',
				            type: 'bar',
				            barWidth: '50%',
				            data: values,
				            itemStyle: {
				            	normal: {
				            		label: {
				            			show: true,
				            			position: 'top',
				                        textStyle: {
				                        	color: 'black',
				                        	fontSize: 12
				                        }
				            		}
				            	}
				            }
				        }
				    ]
				};
			myChart.setOption(option);
		}
	})
}
function fwzysygfsdRank(){
	$.ajax({
		type : "post",
		url : "/ydxxzxt/tjReport/fwzysygfsdRank",
		async : true,
		dataType : "json",
		success : function(data) {
			var names = [];
			var values = [];
			for (var i = 0; i < data.length; i++) {
				names.push(data[i].TJRQ);
				values.push(data[i].COUNT);
			}
			var myChart = echarts.init(document.getElementById('fwzysygfsdRank'));
			option = {
				    color: ['#3D9970'],
				    tooltip: {
				        trigger: 'axis',
				        axisPointer: {
				            type: 'shadow'
				        }
				    },
				    grid: {
				        left: '3%',
				        right: '3%',
				        bottom: '3%',
				        top: '10%',
				        containLabel: true
				    },
				    xAxis: [
				        {
				            type: 'category',
				            axisLabel: {
		                        formatter: function(value) {
		                        	var res = value;
			                        res = res.substring(4, 6) + '-' 
			                              + res.substring(6, 8) + '_'
			                           	  + res.substring(8, 10) + '时';
			                        return res;
		                       }
		                   },
				           data: names
				        }
				    ],
				    yAxis: [
				        {
				            type: 'value'
				        }
				    ],
				    series: [
				        {
				            name: '服务资源被调用量',
				            type: 'bar',
				            barWidth: '50%',
				            data: values,
				            itemStyle: {
				            	normal: {
				            		label: {
				            			show: true,
				            			position: 'top',
				                        textStyle: {
				                        	color: 'black',
				                        	fontSize: 12
				                        }
				            		}
				            	}
				            }
				        }
				    ]
				};
			myChart.setOption(option);
		}
	})
}
function fwzyyclRank(){
	$.ajax({
		type : "post",
		url : "/ydxxzxt/tjReport/fwzyyclRank",
		async : true,
		dataType : "json",
		success : function(data) {
			var names = [];
			var values = [];
			for (var i = 0; i < data.length; i++) {
				names.push(data[i].FFMS);
				values.push(data[i].COUNT);
			}
			var myChart = echarts.init(document.getElementById('fwzyyclRank'));
			option = {
				    color: ['#39CCCC'],
				    tooltip: {
				        trigger: 'axis',
				        axisPointer: {
				            type: 'shadow'
				        }
				    },
				    grid: {
				        left: '3%',
				        right: '3%',
				        bottom: '3%',
				        top: '10%',
				        containLabel: true
				    },
				    xAxis: [
				        {
				            type: 'category',
				            axisLabel: {
		                        formatter: function(value) {
		                           var res = value;
		                           if(res.length > 4) {
		                               res = res.substring(0, 3) + "..";
		                           }
		                           return res;
		                       }
		                   },
				           data: names
				        }
				    ],
				    yAxis: [
				        {
				            type: 'value'
				        }
				    ],
				    series: [
				        {
				            name: '近一月异常量',
				            type: 'bar',
				            barWidth: '50%',
				            data: values,
				            itemStyle: {
				            	normal: {
				            		label: {
				            			show: true,
				            			position: 'top',
				                        textStyle: {
				                        	color: 'black',
				                        	fontSize: 12
				                        }
				            		}
				            	}
				            }
				        }
				    ]
				};
			myChart.setOption(option);
		}
	})
}
function fwzyxysjRank(){
	$.ajax({
		type : "post",
		url : "/ydxxzxt/tjReport/fwzyxysjRank",
		async : true,
		dataType : "json",
		success : function(data) {
			var names = [];
			var values = [];
			for (var i = 0; i < data.length; i++) {
				names.push(data[i].FFMS);
				values.push(data[i].PJXYSJ);
			}
			var myChart = echarts.init(document.getElementById('fwzyxysjRank'));
			option = {
				    color: ['#775f96'],
				    tooltip: {
				        trigger: 'axis',
				        axisPointer: {
				            type: 'shadow'
				        }
				    },
				    grid: {
				        left: '3%',
				        right: '3%',
				        bottom: '3%',
				        top: '10%',
				        containLabel: true
				    },
				    xAxis: [
				        {
				            type: 'category',
				            axisLabel: {
		                        formatter: function(value) {
		                           var res = value;
		                           if(res.length > 4) {
		                               res = res.substring(0, 3) + "..";
		                           }
		                           return res;
		                       }
		                   },
				           data: names
				        }
				    ],
				    yAxis: [
				        {
				            type: 'value'
				        }
				    ],
				    series: [
				        {
				            name: '响应时间',
				            type: 'bar',
				            barWidth: '50%',
				            data: values,
				            itemStyle: {
				            	normal: {
				            		label: {
				            			show: true,
				            			position: 'top',
				            			formatter: '{value}s',
				                        textStyle: {
				                        	color: 'black',
				                        	fontSize: 12
				                        },
				                        formatter:function(a){
				                        	return a.data + 's';
				                        }
				            		}
				            	}
				            }
				        }
				    ]
				};
			myChart.setOption(option);
		}
	})
}