$(function() {
	/**
	 * EasyUI初始化
	 */
	$("#username").textbox('textbox').focus();
	
	/*
	 * 登录检测和提交
	 */
	$("#loginBtn").on("click",function(){
		
		if(uiEx.validate("#loginForm")){
			
		  uiEx.submitForm('#loginForm');
					
		}
		
		
	});
	
	
	$(":input.textbox-text").keydown(function(event){
		if(event.keyCode==13){
			var nxtIdx = $(":input.textbox-text,A.easyui-linkbutton").index(this);
	  		 $(":input.textbox-text,A.easyui-linkbutton").eq(nxtIdx+1).focus();
		}
	});

	
	/**
	 * easyui主题切换下拉菜单
	 */
	
	$("#themeCombobox")
			.combobox(
					{
						editable : false,
						panelHeight : "auto",
						valueField : "value",
						textField : 'text',
						url : EasyEE.basePath
								+ "staticresources/easyee/json/easyui.theme.combobox.json",
						/*
						 * "data": [{ "value":"default", "text":"default",
						 * "selected":true },{ "value":"black", "text":"black"
						 * },{ "value":"bootstrap", "text":"bootstrap" },{
						 * "value":"gray", "text":"gray" },{ "value":"metro",
						 * "text":"metro" }],
						 */
						onSelect : function(selObj) {
							if(document.getElementById("themeLink")){
								document.getElementById("themeLink").href = EasyEE.basePath
										+ "staticresources/easyui/themes/"
										+ selObj.value
										+ "/easyui.css";
							}
						},
						// 加载成功后设置默认值
						onLoadSuccess : function() {
							var defaultTheme = "metro-blue";
							
							var theme=$.cookie('ui_theme');
							if(theme){
								defaultTheme = theme;
							}
							
							
							 $("#themeCombobox").combobox("setValue",
							 defaultTheme);
							 document.getElementById("themeLink").href=EasyEE.basePath+"staticresources/easyui/themes/"+defaultTheme+"/easyui.css";
						}
					});
	

})