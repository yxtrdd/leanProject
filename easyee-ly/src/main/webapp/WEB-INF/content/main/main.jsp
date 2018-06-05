<%@ page import="org.apache.shiro.subject.support.DefaultSubjectContext" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" content="ie=edge"/>
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <base href="<%=basePath%>">
    
    <title>管理系统平台模板</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="sh,easyee,javaee,framework,java">
	<meta http-equiv="description" content="EasyEE-SH basic framework by EasyProject">

<link rel="stylesheet" type="text/css" href="staticresources/style/easyee.main.css">

<%-- EasyUI CSS --%>
<link rel="stylesheet" type="text/css" href="staticresources/easyui/themes/default/easyui.css" id="themeLink">
<link rel="stylesheet" type="text/css" href="staticresources/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="staticresources/easyui/portal.css">

<%-- 全局变量 --%>
<script type="text/javascript"> 
	var EasyEE = {};
	EasyEE.path = '<%=path%>';
	EasyEE.basePath = '<%=basePath%>';
	EasyEE.menuTreeJson=${menuTreeJson};
	function S(data){
		alert(JSON.stringify(data));
	}; 
</script>
<%-- EasyUI JS & Extension JS--%>
<script type="text/javascript" src="staticresources/easyui/jquery.min.js"></script>
<script type="text/javascript" src="staticresources/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="staticresources/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="staticresources/easyui/datagrid-dnd.js"></script>
<script type="text/javascript" src="staticresources/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="staticresources/easyui/jquery.portal.js"></script>
<script type="text/javascript" src="staticresources/easyui/treegrid-dnd.js"></script>

<%-- EasyUIEx --%>
<link rel="stylesheet" type="text/css" href="staticresources/easyuiex/css/easyuiex.css">
<script type="text/javascript" src="staticresources/easyuiex/easy.easyuiex.js"></script>
<script type="text/javascript" src="staticresources/easyuiex/easy.easyuiex-validate.js"></script>
<%-- 使用 EasyUIEx的 easy.jquery.edatagrid.js 代替 jquery.edatagrid.js --%>
<script type="text/javascript" src="staticresources/easyuiex/easy.jquery.edatagrid.js"></script>
<%-- EasyUIEx的默认消息语言为中文，使用其他语言需要导入相应语言文件 --%>
<script type="text/javascript" src="staticresources/easyuiex/lang/easy.easyuiex-lang-zh_CN.js"></script>

<%-- Echarts --%>
<script type="text/javascript" src="staticresources/echarts/themes/default.js"></script>
<script type="text/javascript" src="staticresources/echarts/themes/blue.js"></script>
<script type="text/javascript" src="staticresources/echarts/themes/dark.js"></script>
<script type="text/javascript" src="staticresources/echarts/themes/gray.js"></script>
<script type="text/javascript" src="staticresources/echarts/themes/green.js"></script>
<script type="text/javascript" src="staticresources/echarts/themes/helianthus.js"></script>
<script type="text/javascript" src="staticresources/echarts/themes/infographic.js"></script>
<script type="text/javascript" src="staticresources/echarts/themes/macarons.js"></script>
<script type="text/javascript" src="staticresources/echarts/themes/red.js"></script>
<script type="text/javascript" src="staticresources/echarts/themes/shine.js"></script>
<script type="text/javascript" src="staticresources/echarts/echarts-all.js"></script>

<%-- EasyEE 全局JS文件 --%>
<script type="text/javascript" src="staticresources/easyee/lang/easyee-zh_CN.js"></script>
<script type="text/javascript" src="staticresources/easyee/easyee-sm.main.js"></script>

<%-- 自定义页面相关JS --%>
<script type="text/javascript" src="script/main/main.js"></script>

<%-- jquery Cookie plugin --%>
<script type="text/javascript" src="staticresources/easyee/jquery.cookie.js"></script>

</head>
  
<%-- 将布局放在body --%>
<body class="easyui-layout" >
	<%-- ##################布局部分################## --%>
	<%-- 将布局放在DIV --%>
	<%-- 
	<div style="text-align: center;overflow:auto;width:100%;height:100%;margin: 10px auto;">
 	--%>
	
	<%-- Head --%>
	<div id="easysshtop" class="top" data-options="region:'north',split:true,collapsible:true">
		<div style="float:left;	padding:10px 0 0 0;	width:300px;">
			<img src="staticresources/images/logomain.png">
		</div>
		<div style="float: right; padding-top:4px;line-height: 30px;color:#fff;text-align: right; ">
			<%request.setAttribute("now", new java.util.Date());%>
			<span>欢迎</span>
			<span style="font-weight:bold; color:#fff;">${CDWBM.qymc}</span>
			<span style="font-weight:bold; color:#fff;">[${USER.realName}]</span> 
			<span id="showtime">
				<fmt:formatDate value="${now}" pattern="yyyy年MM月dd日 HH:mm:ss"/>
		 	</span>
		 	<!-- 换行 -->
			<br/>
			<input id="themeCombobox" class="easyui-combobox" data-options="width:100,hasDownArrow:false,icons:[{'iconCls':'icon-palette'}]" /> 
			<a id="btnChangePwd" class="easyui-linkbutton bt_org3">更改密码</a> 
		
			<a id="btnExit" href="logout" class="easyui-linkbutton bt_org">注销</a>
		
		<%-- 
			<a id="btnLoginRefresh" href="reLogin" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">重登系统</a> 
		--%>
		
		</div>
	</div>
	
	<%-- Menu --%>
	<div data-options="region:'west',title:'功能菜单',split:true" style="width:180px;">
		<div style="margin:10px 0;"></div>
		<ul id="menu" class="easyui-tree" data-options="animate:true,lines:false">
	</div>
		
	<%-- Content --%>
	<div data-options="region:'center',split:true"  >
		<div class="easyui-tabs" id="tabs" data-options="fit:true">
		<!-- 	
			<div title="最近报表"  iconCls="icon-chart_bar" data-options="href:'jsp/echarts/doChart.jsp'"></div> 
			<div title="欢迎"  iconCls="icon-book"  data-options="fit:true, content:'<iframe src=\'doc/easyee-sm-readme-single_zh_CN.html?v=1\' frameborder=\'0\' style=\'height:500px;width:100%;\'  ></iframe>'"></div> 
			<div title="Tab3" iconCls="icon-reload" closable="true" style="padding:20px;display:none;">tab3</div> 
		-->
			<div title="欢迎使用" iconCls="icon-chart_bar"  style="padding:20px 20px 0 20px; height:513px;overflow:hidden; color:red;text-align:center; background:#00FFFFFF;" >
				<div id="content" class="welcome"></div>
			</div>
		</div>
	</div>
	
	<%-- Right --%>
	<%-- 
	  <div data-options="region:'east',title:'East',split:true" style="width:100px;"></div> 
	--%>
	
	<%-- Footter --%>
	<div data-options="region:'south',split:false" class="foot" style="padding:3px 0; text-align:center;">		
		© 2017 优速科技工作室  版权所有  
		<!-- 单位编码:${sessionScope.CDWBM}  &nbsp;&nbsp;&nbsp;&nbsp;  单位名称:${sessionScope.CDWMC}  -->
		<!--  
		 <a href="http://easyproject.cn/"
					style="color: #8EBB31;font-weight: bold;text-decoration: underline;">EasyProject</a>
		联系、反馈、定制、培训 (Contact, Feedback, Custom, Train) Email：<a
					href="mailto:inthinkcolor@gmail.com">inthinkcolor@gmail.com</a>
		-->
	</div>


	<%-- ##################Tab选项卡的右键菜单，不能删除################## --%>
	<div id="tabsMenu" class="easyui-menu" style="width:120px;display:none;">
		<div name="close"   data-options="iconCls:'icon-close'">关闭标签</div>  
		<div name="other"   data-options="">关闭其他标签</div>  
		<div name="all"  data-options="">关闭所有标签</div>
		<div class="menu-sep"></div>
		<div name="closeRight">关闭右侧标签</div>
		<div name="closeLeft">关闭左侧标签</div>
		<div class="menu-sep"></div>
		<div name="refresh"  data-options="iconCls:'icon-reload'">刷新标签</div> 
	</div>

	<%-- ##################自定义部分################## --%>
	<%-- 密码修改Dialog --%>
	<div id="dialogChangePwd" class="easyui-dialog form-layout" title="密码修改"
		style="width:400px;height:height:200px;" data-options="iconCls:'icon-edit',resizable:true,modal:true,closed:true">

		<form id="formChangePwd" action="SysUser/changePwd" method="post">
			<table class="table_xz" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td  class="td_bg">原密码:</td>
					<td  ><input id="upwd" name="password" class="easyui-textbox"
						type="password"
						data-options="required:true,validType:'minLength[6]'"></input> <%-- 					<input  id="upwd" name="upwd" class="easyui-validatebox" type="password" data-options="required:true,validType:'minLength[6]'"></input> --%>
					</td>
				</tr>
				<tr>
					<td class="td_bg">新密码:</td>
					<td><input id="newpwd" name="newPwd" class="easyui-textbox"
						type="password"
						data-options="required:true,validType:'minLength[6]'"></input></td>
				</tr>
				<tr>
					<td class="td_bg">新密码确认:</td>
					<td><input id="renewpwd" name="confirmPwd"
						class="easyui-textbox" type="password"
						data-options="required:true,validType:{minLength:[6],equals:['$(\'#newpwd\').val()','与新密码不一致']}"></input></td>
				</tr>
				
			</table>
			
		</form>
		<div class="chang-k" id="changPw">
			 <a href="javascript:void(0)" id="submitChangPwd" class="easyui-linkbutton qd">更新密码</a>
			 <a href="javascript:void(0)" class="easyui-linkbutton qd" onclick="uiEx.clearForm('#formChangePwd')">清除</a>
	    </div>	
	</div>
	
</body>
</html>
