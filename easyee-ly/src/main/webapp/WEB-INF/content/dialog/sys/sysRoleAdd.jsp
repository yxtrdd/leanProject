<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%-- 1. 页面Datagrid初始化相关JS --%>
<%-- JS代码必须包含在页面中，引入外部JS文件会导致表格界面在未完成初始化前就显示，出现短暂的未初始化界面 --%>

<script type="text/javascript">
$(function() {
	
	/*
	 * 执行增改
	 */
	 sysRole.add=function(){
		 var permissions=uiEx.getCheckedInfos("#sysRoleAddPermissions",["id","type"]);
		 var menus="";
		 var operations="";
		 // 初始化要提交的菜单权限和操作权限
		 $.each(permissions,function(i,v){
		 	if(v.type=='menu'){
		 		menus+=v.id+"#";
		 	}else{
		 		operations+=v.id+"#";
		 	}
		 });
	 	var dg=$("#sysRoleDataGrid");
		var url = "SysRole/save";
	 	uiEx.submitURLAjax("#sysRoleAddForm",url, function(data) {
			data = eval("(" + data + ")");
			if (data.statusCode==200) {
				//uiEx.reloadSelTab("#tabs");
				//dg.datagrid('reload'); //刷新表格，在第当前页
				
				dg.datagrid({"pageNumber":data.page});//刷新表格
				//dg.datagrid('load'); //刷新表格
				uiEx.closeDialog("#sysRoleAddDialog");
			} 
		}, {"menus":menus,"operations":operations,"rows":dg.datagrid("options").pageSize});
	 }
	 
});

</script>
<!-- 修改或修改Dialog -->
<div id="sysRoleAddDialog" class="easyui-dialog form-layout" 
	style="width:550px;height:410px;padding:0px 0px;" resizable="true" closed="true"
	buttons="#sysRoleAdd-dlg-buttons">
	<div class="ftitle">角色信息</div>
	<form id="sysRoleAddForm" method="post">
			<input type="hidden" name="roleId"/>
			 <table cellpadding="0" class="table_xz" cellspacing="0" width="100%">
	    		<tr>
	    			<td class="td_bg">角色名:</td>
	    			<td><input class="easyui-textbox easyui-validatebox" name="name"
				required="true"
				data-options="" style="width:200px"><span class="required">*</span></td>
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
	    			<td class="td_bg">菜单备注:</td>
	    			<td><input class="easyui-textbox" multiline="true" style="height:22px;width: 200px;" name="remark"></td>
	    		</tr>
	    		<tr>
	    			<td class="td_bg">权限：</td>
	    			<td>
							<%--  <input id="sysRoleAddPermissions" name="sysMenuPermission.sysMenuPermission.menuPermissionId" multiple  class="easyui-combotree" style="width:200px;"/> --%>
							
							<ul id="sysRoleAddPermissions" ></ul>
					</td>
	    		</tr>
	    	</table>
	</form>
</div>
<div id="sysRoleAdd-dlg-buttons">
	<a class="easyui-linkbutton qd" id="sysRoleAddSaveBtn" 
		onclick="sysRole.add()">添加</a> <a class="easyui-linkbutton qd"
		
		onclick="javascript:uiEx.closeDialog('#sysRoleAddDialog')">取消</a>
</div>