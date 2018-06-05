<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ page import="org.apache.shiro.authc.AuthenticationException" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<base href="<%=basePath%>">

<title>管理系统平台模板</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="sh,easyee,javaee,framework,java">
<meta http-equiv="description" content="EasyEE-SM basic framework by EasyProject">
 
<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700|Montserrat:400,700|Roboto:400,700,900" rel="stylesheet"> 
<!-- EasyUI CSS -->
<link rel="stylesheet" type="text/css" href="staticresources/easyui/themes/metro-blue/easyui.css" id="themeLink">
<link rel="stylesheet" type="text/css" href="staticresources/easyui/themes/icon.css">
<style type="text/css">
* {
	font-size: 14px;
	font-family: '微软雅黑','Open Sans', Arial, Helvetica, sans-serif; 
}
body {
	background-color:#f5f5f5;
}
.logo {
	width:1000px;
	margin:0 auto;
	padding:10px 0 20px 0;
}
.dl_k {
	background-color:#fff;
	padding:15px 25px;
}
.panel-header {
	background:none;
	border:0px;
}
.panel-body {
	background:none;
	border:0px;
}
.panel-title {
  font-size: 20px;
  font-weight: bold;
  color: #0d64b6;
  height: 30px;
  line-height: 30px;
  text-align: center
}
.footer {
	margin: 30px auto;
	text-align:center;
}
.title{ margin-bottom: 20px;text-align: center;}
.fname {
	color: #0084FF;
	font-weight: normal;
	font-family: 'Oxygen', 微软雅黑; 
}
.dl_bg {
	width:100%;
	height:auto;
	text-align: center; 
	overflow:auto;
	background:url(staticresources/images/dl_bg.jpg) no-repeat top center;
}
.dl_dk {
	width:1000px;
	margin:0 auto;
}
.dl_dk1 {
	width:440px;
	float:right;
	padding:50px 0;
}
.dl_tm{
	background-color:rgba(255,255,255,0.3);
	padding:20px;
}
.textbox {
	border:1px solid #ccc;
}
.textbox input {
	line-height:30px;
}
.sr_dk {
	padding:10px 0 10px 0;
}
.sr_dk table {
	width:100%;
}
.sr_dk table input{
	border:1px solid #ccc;
	padding:0 10px 0 40px;
	color:#333;
}
.sr_dk .ib1 input {
	background:url(staticresources/images/yh.png) no-repeat 8px 8px;
	padding:0 0 0 38px;
}
.sr_dk .ib2 input {
	background:url(staticresources/images/mm.png) no-repeat 8px 8px;
	padding:0 0 0 38px;
}
.dl_an {
	margin:3px 0 0 0;
}
.dl_an a:hover,.dl_an a:link{
	text-decoration:none;
}
.button1 {
	background:#ffa852;
	color:#fff;
	font-size:16px;
	line-height:40px;
	padding:8px 64px;
	margin:0 0 0 7px;
}
.button2 {
	background:#5e96ce;
	color:#fff;
	font-size:16px;
	line-height:40px;
	padding:8px 66px;
	margin:0 0 0 7px;
}
</style>

<!-- EasyUI JS -->
<script type="text/javascript" src="staticresources/easyui/jquery.min.js"></script>
<script type="text/javascript" src="staticresources/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="staticresources/easyui/locale/easyui-lang-zh_CN.js"></script>

<!-- EasyUIEx -->
<link rel="stylesheet" type="text/css" href="staticresources/easyuiex/css/easyuiex.css">
<script type="text/javascript" src="staticresources/easyuiex/easy.easyuiex.js"></script>
<script type="text/javascript" src="staticresources/easyuiex/easy.easyuiex-validate.js"></script>
<!-- EasyUIEx的默认消息语言为中文，使用其他语言需要导入相应语言文件 -->
<script type="text/javascript" src="staticresources/easyuiex/lang/easy.easyuiex-lang-zh_CN.js"></script>
<%-- jquery Cookie plugin --%>
<script type="text/javascript" src="staticresources/easyee/jquery.cookie.js"></script>

<!-- 自定义页面相关JS -->
<script type="text/javascript" src="script/login.js"></script>


<%--
<script type="text/javascript">
 	//主题切换
	$(function(){
		$("#themeCombobox").combobox({
			  onChange : function(newValue,oldValue){
				  document.getElementById("themeLink").href="<%=basePath%>easyui/themes/"+ newValue + "/easyui.css";
				}
		});
	})
</script>
 --%>
 <%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<%-- <%
Object exceptionClassName=session.getAttribute("shiroLoginFailure"); 
if(exceptionClassName!=null){
	 String authExp = exceptionClassName.toString(); 
	 // you can do something...
	 out.println(exceptionClassName);
	 session.removeAttribute("shiroLoginFailure");
}
%> --%>
<!-- 登录消息提示JS -->
<c:if test="${!empty MSG}">

	<script type="text/javascript">
		$(function() {
			uiEx.alert("${MSG }", "info");
		});
		
		
	</script>
	
	<c:remove var="MSG" scope="session"/>
</c:if>
<script>
function showHTML(){
	 var w = window.open();
    w.document.body.innerText = document.documentElement.outerHTML;
};
 

$(function() {
	//showHTML();
	//alert($.fn.easyui);
})
</script>
</head>

<body>
	<div class="logo"><img src="staticresources/images/logo-1.png" /></div>
	<div class="dl_bg">
		
		<c:if test="${!empty IPLock}">
			<span style="color:#ff0000; font-weight:bold">您的 IP 地址由于连续登录错误过多，已被锁定 2 小时，请稍后再试。</span>
		</c:if>
		<div class="dl_dk">
			<div class="dl_dk1">
				<div class="dl_tm">
					  <div class="dl_k">
							<div class="easyui-panel" title="优速科技后台管理系统">
									<div class="sr_dk">
										<form id="loginForm" class="easyui-form" method="post"
											data-options="novalidate:true" action="toLogin">
											
											<table cellpadding="5" style="">
												<!--  
												<tr>
													<td width="90">Theme：</td>
													<td><input id="themeCombobox" class="easyui-combobox" data-options="" /></td>
												</tr>
												-->
												<tr>
													<td class="ib1"><input class="easyui-textbox" type="text"
														name="name" id="username" style="height:40px;width:334px;"
														data-options="validType:[],required:true,prompt:'用户名'" value="admin"></input></td>
												</tr>
												<tr>
													<td class="ib2"><input class="easyui-textbox" type="text"
												name="password" style="height: 40px; width: 334px;"
												data-options="required:true,prompt:'密码'" value="admin123"></input></td>
												</tr>
											  
											
											</table>
										</form>
										<div class="dl_an">
											<a href="javascript:void(0)" class="button1"
											id="loginBtn" iconCls="icon-man">登陆</a> <a
											href="javascript:void(0)" iconCls="icon-clear"
											class="button2" onclick="uiEx.clearForm('#loginForm')">重置</a>
										</div>
					
									</div>
			                 </div>

					</div>
				</div>
			</div>
		</div>
		
	</div>
	
	<div class="footer">
			<p>
				© 2017  优速科技工作室
			</p>
			<!-- 
			<p>
				联系、反馈、定制、培训/Contact, Feedback, Custom, Train Email：<a
					href="mailto:inthinkcolor@gmail.com">inthinkcolor@gmail.com</a>
			</p>
			 -->
	</div>

</body>
</html>
