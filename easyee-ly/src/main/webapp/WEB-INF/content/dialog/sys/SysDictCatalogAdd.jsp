<!--SysDictCatalog Add Page, generated by EasyAuto -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="s"%>

<%--  JavaScript  --%>

<script type="text/javascript">
$(function() {
	/*
	 * CRUD
	 */
	//Create or Update
	SysDictCatalog.add = function() {
		var tt = $("#SysDictCatalogTree");
		var url = "SysDictCatalog/save";
		uiEx.submitURLAjax("#SysDictCatalogAddForm", url, function(data) {
			data = eval("(" + data + ")");
			// Refresh tree
			if (data.statusCode==200) {
				// refresh root node
				tt.tree('reload',tt.tree('getRoot').target);
				uiEx.closeDialog("#SysDictCatalogAddDialog");
			} 
		},{});
	}
});


</script>

<%-- 2. Dialog --%>
<div id="SysDictCatalogAddDialog" class="easyui-dialog form-layout" style="width:550px;height:auto;" resizable="true" closed="true" buttons="#SysDictCatalogAdd-dlg-buttons">
	<div class="ftitle">字典分类<s:message code="label.info"></s:message></div>
	<form id="SysDictCatalogAddForm" method="post" >
		<input name="parentId" type="hidden"/>
		<table class="table_xz" cellpadding="0" cellspacing="0" width="100%">
			<tr>
    			<td class="td_bg">上级分类</td>
    			<td><input class="textbox-gray" name="parentName" readonly="readonly"></td>
    			<td class="td_bg">分类名称</td>
    			<td><input class="easyui-textbox easyui-validatebox" name="catalogName" data-options="required:true,validType:{maxLength:[100],remotePlus:['SysDictCatalog/unique','catalogName','分类名称已存在']}"></td>
    		</tr>
    		<tr>
	   			<td class="td_bg">排序号</td>
	   			<td><input class="easyui-textbox easyui-numberspinner" name="sortNum" value="1" data-options="min:1,max:100,editable:false"></td>
	   			<td class="td_bg">备注</td>
	   			<td><input class="easyui-textbox easyui-validatebox" name="remark" data-options="validType:{maxLength:[200]}"></td>
	   		</tr>
    	</table>
	</form>
</div>
<div id="SysDictCatalogAdd-dlg-buttons">
	<a class="easyui-linkbutton qd" onclick="SysDictCatalog.add()" id="SysDictCatalogAddSaveBtn"><s:message code="label.add"></s:message></a>
	<a class="easyui-linkbutton qd" onclick="javascript:uiEx.closeDialog('#SysDictCatalogAddDialog')"><s:message code="label.cancel"></s:message></a>
</div>