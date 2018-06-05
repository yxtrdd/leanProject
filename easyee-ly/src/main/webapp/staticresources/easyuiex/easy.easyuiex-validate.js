/**
 * EasyUIEx validate
 * 
 * Version 2.0.0
 * 
 * http://easyproject.cn 
 * https://github.com/ushelp
 * 
 * Copyright 2014 Ray [ inthinkcolor@gmail.com ]
 * 
 * Dependencies: jQuery EasyUI
 * 
 */
/*
 * ################ EasyUiEx Validator function
 */
$(function() {
	/*
	 * ################# Custom validator
	 */

	$.extend($.fn.validatebox.defaults.rules, {
		/**
		 * 远程验证加强版（额外参数只适用于validatebox）
		 * param[0]:url
		 * param[1]:main parameter key
		 * param[2]:valid message
		 * param[*]:other parameter key and value(eval(取值表达式))(从第4个参数开始以键值对配置)
		 */
		remotePlus: { 
	    	validator: function(value, param){
	    		var data = {};
	    		data[param[1]] = value;
	    		for (var i = 3; i < param.length; i += 2) {
	    			data[param[i]] = eval(param[i + 1]);
				}
	    		var result = $.ajax({url:param[0],data:data,type:"post",dataType:"text",async:false,cache:false}).responseText;
	    		return result == "true";
	    	},
	    	message: '{2}.'
	    },    
 
		/**
		 * Comparing equals whit other input
		 */
		equals : {
			validator : function(value, param) {
				return value == eval(param[0]);
			},
			message : '{1}.'
		},
		/**
		 * Fixed length 
		 */
		fixLength : {
			validator : function(value, param) {
				//var len = value.replace(/[\u0391-\uFFE5]/g,"11").length;	// oracle字符集编码决定
				return value.length == param[0];
			},
			message : '该输入项长度应为{0}.'
		},
		/**
		 * Minimum length 
		 */
		minLength : {
			validator : function(value, param) {
				//var len = value.replace(/[\u0391-\uFFE5]/g,"11").length;	// oracle字符集编码决定
				return value.length >= param[0];
			},
			message : '该输入项长度不能小于{0}.'
		},
		/**
		 * maximum length 
		 */
		maxLength : {
			validator : function(value, param) {
				//var len = value.replace(/[\u0391-\uFFE5]/g,"11").length;	// oracle字符集编码决定
				return value.length <= param[0];
			},
			message : '该输入项长度不能大于{0}.'
		},
		/**
		 * must char 
		 */
		char : {
			validator : function(value, param) {
				return /^[a-zA-Z]+$/i.test(value);
			},
			message : '请输入字母.'
		},
		/**
		 * must number 
		 */
		number : {
			validator : function(value, param) {
				return /^[0-9]+$/i.test(value);
			},
			message : '请输入数字.'
		},
		/**
		 * required
		 */
		required : {
			validator : function(value, param) {
				var param0 = eval(param[0]);
				return param0 != '';
			},
			message : '{1}.'
		},
		/**
		 * the max day of month
		 */
		maxDay : {
			validator : function(value, param) {
				var month = eval(param[1]);
				var year = eval(param[0]);
				var day = EasyEE.getLastDayOfMonth(year,month);
				return parseInt(value) <= day;
			},
			message : '日期超过范围.'
		},
	    /**
	     * 与某日期(时间)比较
	     */
	    compdate : {
	    	validator: function(value, param){ 
	    		var param0 = eval(param[0]);
	    		if(param0 != ''){
	    			if(param[1] == '>'){
	    				return value > param0;
	    			} else if(param[1] == '='){
	    				return value = param0;
	    			} else if(param[1] == '<'){
	    				return value < param0;
	    			} else if(param[1] == '>='){
	    				return value >= param0;
	    			} else if(param[1] == '<='){
	    				return value <= param0;
	    			}
	    		}
	    		return false;
	    	},
	    	message: '{2}.'
	    },
	    fileSize : {
			validator : function(value, param) {
				// IE 目前无法在前端用纯js判断文件大小，改后台验证
				if (/msie/.test(navigator.userAgent.toLowerCase())) {
                	return true;
                }
				var size = param[0];
                var unit = param[1];
                if (!size || isNaN(size) || size == 0) {
                    $.error('验证文件大小的值不能为 "' + size + '"');
                } else if (!unit) {
                    $.error('请指定验证文件大小的单位');
                }
                var index = -1;
                var unitArr = new Array("bytes", "kb", "mb", "gb", "tb", "pb", "eb", "zb", "yb");
                for (var i = 0; i < unitArr.length; i++) {
                    if (unitArr[i] == unit.toLowerCase()) {
                        index = i;
                        break;
                    }
                }
                if (index == -1) {
                    $.error('请指定正确的验证文件大小的单位：["bytes", "kb", "mb", "gb", "tb", "pb", "eb", "zb", "yb"]');
                }
                // 转换为bytes公式
                var formula = 1;
                while (index > 0) {
                    formula = formula * 1024;
                    index--;
                }
                // this为页面上能看到文件名称的文本框，而非真实的file
                // $(this).next()是file元素
                // 不支持IE
                return $(this).next().get(0).files[0].size < parseFloat(size) * formula;
			},
			message : '文件大小限 {0}{1}.'
		}
		
	});

	
})