/***-----初始化动态加载layer所依赖css文件/js文件--------****/
$("#layer-script-link").remove();
$("<script id='layer-script-link'>").attr({type:"text/javascript",src:path+"/plugin/layer/layer.js"}).appendTo("head");

/**
 * 提示信息弹窗，带按钮的
 * @param content
 */
function layAlert(content,type){
	var _map={"warn":0,"success":1,"fail":2,"question":3};
	var _icon=_map[type]!=undefined?_map[type]:1;
	layer.alert(content,{
		title:[''],
		icon:_icon,//可切换0、1、2、3。。。
		closeBtn:1,//可切换0、1、2、3。。。
		skin:'layer-ext-moon'
	});
}


/**
 * 弹窗显示提示信息
 */
function layMessage(content){
    layer.msg(content,{
	    icon:0,time:2000,btn:['知道了']
	});
}
/***
 * 加载提示：type：0、1、2、3
 * 使用layClose关闭
 * @param type
 */
function layLoad(type){
	var index=layer.load(type);
	return index;
}
/**
 * 关闭lay层
 * @param index；层id
 */
function layClose(index){
	layer.close(index);
}

/***
 * confirm实例
//
layer.confirm("确定执行本次操作？",{
	btn:['确定','放弃']
},function(){
	//点击确定后进行的操作
},function(){
	//点击放弃后进行的操作
});

**/
