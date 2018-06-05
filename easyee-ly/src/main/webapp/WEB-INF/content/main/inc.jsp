<%@ page import="org.apache.shiro.subject.support.DefaultSubjectContext"%>
<%@ page import="org.apache.shiro.SecurityUtils"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" content="ie=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<base href="<%=basePath%>">

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="sh,easyee,javaee,framework,java">
<meta http-equiv="description" content="EasyEE-SH basic framework by EasyProject">

<%-- EasyEE CSS --%>
<%--
<link rel="stylesheet" type="text/css" href="styles.css">
<link href='http://fonts.googleapis.com/css?family=Roboto+Condensed:400,700|Montserrat:400,700|Roboto:400,700,900' rel='stylesheet' type='text/css'>
 -->

<%-- EasyUI CSS --%>
<link rel="stylesheet" type="text/css" href="staticresources/easyui/themes/default/easyui.css" id="themeLink">
<link rel="stylesheet" type="text/css" href="staticresources/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="staticresources/easyui/portal.css">

<%-- 全局变量 --%>
<script type="text/javascript"> 
	var EasyEE = {};
	EasyEE.path = '<%=path%>';
	EasyEE.basePath = '<%=basePath%>';
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

<%-- EasyEE 全局JS文件 --%>
<script type="text/javascript" src="staticresources/easyee/lang/easyee-zh_CN.js"></script>
<script type="text/javascript" src="staticresources/easyee/easyee-sm.main.js"></script>
 
<%-- jquery Cookie plugin --%>
<script type="text/javascript" src="staticresources/easyee/jquery.cookie.js"></script>
