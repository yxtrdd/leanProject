<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%-- 1. 页面Datagrid初始化相关JS --%>
<%-- JS代码必须包含在页面中，引入外部JS文件会导致表格界面在未完成初始化前就显示，出现短暂的未初始化界面 --%>

<script type="text/javascript">
$(function() {
	/*
	 * 执行增改
	 */
	//执行添加或修改
	sysUser.add = function() {
		var dg=$("#sysUserDataGrid");
		var url = "SysUser/save";
		uiEx.submitURLAjax("#sysUserAddForm", url, function(data) {
			data = eval("(" + data + ")");
		
			if (data.statusCode==200) {
				//uiEx.reloadSelTab("#tabs");
				//dg.datagrid('reload'); //刷新表格，在第当前页
				
				dg.datagrid({"pageNumber":data.page});//刷新表格
				//dg.datagrid('load'); //刷新表格
				uiEx.closeDialog("#sysUserAddDialog");
			} 
		},{"rows":dg.datagrid("options").pageSize});
	}
});

</script>
<!-- 添加或修改Dialog -->
<div id="sysUserAddDialog" class="easyui-dialog form-layout" style="width:550px;height:auto;padding:0px 0px;" 
	resizable="true" closed="true" buttons="#sysUserAdd-dlg-buttons">
	<div class="ftitle">用户信息</div>
	<form id="sysUserAddForm" method="post">
		<input name="userId" type="hidden">
		<table cellpadding="0" class="table_xz" cellspacing="0" width="100%">
    		<tr>
    			<td class="td_bg">用户名:</td>
    			<td><input class="easyui-textbox easyui-validatebox" name="name"
			required="true"
			data-options="" style="width: 200px"><span class="required">*</span></td>
    		</tr>
    		<tr>
    			<td class="td_bg">真实姓名:</td>
    			<td><input class="easyui-textbox easyui-validatebox" name="realName"
			style="width: 200px"></td>
    		</tr>
    		<tr>
    			<td class="td_bg">密码:</td>
    			<td><input type="password" class="easyui-textbox easyui-validatebox" name="password"
			id="password" required="true" validType="minLength['6']" style="width: 200px"><span class="required">*</span></td>
    		</tr>
    		<tr>
    			<td class="td_bg">确认密码:</td>
    			<td><input type="password" class="easyui-textbox easyui-validatebox" name="confirmPwd"
					required="true" validType="equals['$(\'#password\').val()','两次密码不一致.']" style="width: 200px"><span class="required">*</span></td>
    		</tr>
    		<tr>
    			<td class="td_bg">状态:</td>
    			<td>
					<input type="radio" name="status" value="0" id="on" checked="checked"  />
					<label for="on">启用</label> 
					<input type="radio" name="status" value="1" id="off" />
					<label for="off">禁用</label>
				</td>
    		</tr>
    		<tr>
    			<td class="td_bg">角色:</td>
    			<td><div id="sysUserAddRoles"></div></td>
    		</tr>
    	</table>
	</form> 
</div>
<div id="sysUserAdd-dlg-buttons">
	<a class="easyui-linkbutton qd" id="sysUserAddSaveBtn" onclick="sysUser.add()">添加</a> 
	<a class="easyui-linkbutton qd" onclick="javascript:uiEx.closeDialog('#sysUserAddDialog');uiEx.closeDialog('#DwtreeDialog')">取消</a>
</div>
 

