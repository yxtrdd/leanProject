/**
 * 在IE下，如果在readonly的input里面键入backspace键，会触发history.back()
 * 用以下jquery代码修正之
 */
$("input[readOnly]").keydown(function(e) {
	e.preventDefault();
});


/**
 * EasyEE全局处理函数
 */
// ###### 工具函数

/**
 * 判断数组中是否包含指定值
 */
EasyEE.has = function(array, value) {
	var exists = false;
	$.each(array, function(i, v) {
		if (v == value) {
			exists = true;
			return false;
		}
	});
	return exists;
}

/**
 * 获取某月的最后一天
 * @param y 年份
 * @param m 月份
 * @return number day
 */
EasyEE.getLastDayOfMonth = function(y,m){
	var day = 31;
	var month = parseInt(m);
	//var largeMonth = [1,3,5,7,8,10,12];	// 大月
	var smallMonth = [4,6,9,11];	// 小月
	if(month == 2){	//平月
		var year = parseInt(y);
		if((year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)){	//	闰年
			day = 29;
		} else {
			day = 28;
		}
	} else if($.inArray(month, smallMonth) > -1){
		day = 30;
	}
	return day;
}
/**
 * 年份combo数据
 * @param before 前几年
 * @param after 后几年
 * @param bool 是否选中当前年,默认选中
 */
EasyEE.dataYear = function(befoe, after, bool){
	var data = [];
	var currYear = new Date().getFullYear();
	for (var i = befoe; i > 0; i--) {
		var value = currYear - i;
		var datum = {value:value,text:value};
		data.push(datum);
	}
	data.push({value:currYear,text:currYear,selected:bool});
	for (var i = 0; i < after; i++) {
		var value = currYear + i + 1;
		var datum = {value:value,text:value};
		data.push(datum);
	}
	return data;
}
/**
 * 月份combo数据
 * @param bool 是否选中当前月
 */
EasyEE.dataMonth = function(bool){
	var data = [];
	var currMonth = new Date().getMonth() + 1;
	for (var i = 1; i < 13; i++) {
		var value = (i < 10 ? '0' + i : i);
		var datum = {value:value,text:value};
		if(bool == true && currMonth == i){
			datum.selected = true;
		}
		data.push(datum);
	}
	return data;
}

/**
 * 判断是否是JSON对象
 */
EasyEE.isJson=function (obj) {
	var isjson = typeof (obj) == "object"
			&& Object.prototype.toString.call(obj).toLowerCase() == "[object object]"
			&& !obj.length;
	return isjson;
}

/**
 * 通过后台获取json数据包，回调函数callback可选
 */
EasyEE.getJson = function(url, param, async, callback){
	$.ajax({
		url : url,
		type : "post",
		data : (param ? param : {}),
		dataType : "json",
		async : async,
		cache : true,
		error : function() {
			uiEx.msg(uiEx.ajaxError, "bottomRight");
		},
		success : function(data){
			if(callback){
				callback(data);
			}
			return data;
		}
	});
}

/**
 * 将键转换为值
 * @param key 
 * @param data json格式数组
 * @returns
 */
EasyEE.convert = function(key, data) {
	if (!data || !data.length || data.length == 0)
		return key;
	for (var i = 0; i < data.length; i++) {
		if (data[i].value == key)
			return data[i].text;
	}
	return key;
}

/**
 * @param datagridSelector 列表ID
 * @param url 请求地址
 * @param param 参数对象 
 */
EasyEE.excel = function(datagridSelector, url, param){
	// fields and titles
	var fts = uiEx.getShowFieldAndTitle(datagridSelector);
	param.fields = fts.fields.join(',');
	param.titles = fts.titles.join(',');
	
	var temp = document.createElement("form");        
    temp.action = 'toForward?forward=' + url;        
    temp.method = "post";        
    temp.style.display = "none";
    
    for (var p in param) {        
        var opt = document.createElement("textarea");        
        opt.name = p;        
        opt.value = param[p];        
        temp.appendChild(opt);        
    }
    
    document.body.appendChild(temp);        
    temp.submit();   
}


/**
 * JSON返回状态列表
 * 参考：cn.easyproject.easyee.sh.base.util.StatusCode
 */
EasyEE.StatusCode = {
	OK : 200, // 操作正常
	ERROR : 500, // 操作失败
	TIMEOUT : 301, // 用户超时
	NO_PERMISSSION : 401 // 权限不足
}

/**
 * 全局Ajax处理 - 对请求返回的数据进行状态判断 - 全局统一消息提示
 */
$(document).ajaxSuccess(function(event, xhr, settings, plainData) {
	var data = plainData;
	if (!EasyEE.isJson(data)) {
		try {
			data = eval("(" + plainData + ")");
		} catch (e) {
		}
	}
	
	if (data.statusCode) {
		if (data.statusCode == EasyEE.StatusCode.NO_PERMISSSION) {
			// 权限不足
			if (data.locationUrl) {
				// 未登录，跳转到指定页面
				window.location.href = data.locationUrl;
			}
			uiEx.alert(data.msg, "warning");
		} else if (data.statusCode == EasyEE.StatusCode.ERROR) {
			// 操作失败
			if (data.msg) {
				uiEx.msg(data.msg);
			} else {
				uiEx.msg(EasyEE.msg.failure);
			}
		} else if (data.statusCode == EasyEE.StatusCode.TIMEOUT) {
			// 用户超时
			if (data.msg) {
				uiEx.alert(data.msg, "error");
			} else {
				uiEx.alert(EasyEE.msg.timeout);
			}
			if (data.locationUrl) {
				// 未登录，跳转到指定页面
				window.location.href = data.locationUrl;
			}
		} else {
			// 200 正常
			if (data.msg) {
				uiEx.msg(data.msg);
			}
		}
		if(data.callback){
			data.callback();
		}
	}

}).ajaxError(function(event, jqXHR, options, errorMsg) {
//		console.info(event);
//		console.info(jqXHR);
//		console.info(options);
//		console.info(errorMsg);
	if (jqXHR.status == 404) {
		// 404
		uiEx.msg(EasyEE.msg.ajaxNotFound);
	} else if (jqXHR.status == 500) {
		// 服务器错误
			uiEx.msg(EasyEE.msg.ajaxServerError);
		} else {
			uiEx.msg(EasyEE.msg.ajaxOtherError);
		}
 
	});

/**
 * 由于easyui的form表单提交采用的是iframe，而非ajax，所以需要针对form再进行一次全局消息处理
 * uiEx.formSubmitSuccess类似于jQuery的全局ajaxSuccess函数
 * 能够为form表单提交成功的succes事件注册一个系统全局的必须执行函数
 */
uiEx.formSubmitSuccess = function(plainData) {
	var data = plainData;
	if (!EasyEE.isJson(data)) {
		try {
			data = eval("(" + plainData + ")");
		} catch (e) {

		}
	}

	if (data.statusCode) {
		// cn.easyproject.easyee.sh.base.util.StatusCode.OK...
		if (data.statusCode == EasyEE.StatusCode.NO_PERMISSSION) {
			// 权限不足
			if (data.locationUrl) {
				// 未登录，跳转到指定页面
				window.location.href = data.locationUrl;
			}
			uiEx.alert(data.msg, "warning");
		} else if (data.statusCode == EasyEE.StatusCode.ERROR) {
			// 操作失败
			if (data.msg) {
				uiEx.msg(data.msg);
			} else {
				uiEx.msg(EasyEE.msg.serverError);
			}
		} else if (data.statusCode == EasyEE.StatusCode.TIMEOUT) {
			// 用户超时
			if (data.msg) {
				uiEx.alert(data.msg, "error");
			} else {
				uiEx.alert(EasyEE.msg.timeout);
			}
			if (data.locationUrl) {
				// 未登录，跳转到指定页面
				window.location.href = data.locationUrl;
			}
		} else {
			// 200 正常
			if (data.msg) {
				uiEx.msg(data.msg);
			}
		}
	}
	if(data.callback){
		data.callback();
	}
}

/**
 * 对Date的扩展，将 Date 转化为指定格式的String
 * 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
 * 例子： 
 * (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
 * (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
 */
Date.prototype.Format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds() // 毫秒
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	}
	return fmt;
}
