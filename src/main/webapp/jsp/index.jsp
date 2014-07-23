<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("basePath", basePath);
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>输变电可视化项目模块管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="${basePath }static/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${basePath }static/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${basePath }static/css/common.css">
<script type="text/javascript" src="${basePath }static/easyui/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${basePath }static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${basePath }static/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(function(){
			$('#test').datagrid({
				title:'模块列表',
				iconCls:'icon-save',
				width:870,
				height:533,
				fitColumns: true,
				url:"modules/moduleList",
				frozenColumns:[[
	                {field:'ck',checkbox:true}
				]],
				columns:[[
					{field:'id',title:'id',width:40,sortable:true,hidden:true},
					{field:'name',title:'模块名',width:65},
					{field:'loaded',title:'加载情况',width:35},
					{field:'url',title:'访问地址',width:250}
				]],
				rownumbers:true,
				pagination:true,
				toolbar:[{
					id:'btnadd',
					text:'添加',
					iconCls:'icon-add',
					handler:function(){
						$('#btnsave').linkbutton('enable');
						window.location.href = "${basePath }jsp/upload.jsp";
					}
				},{
					id:'btnadd',
					text:'编辑',
					iconCls:'icon-edit',
					handler:function(){
						$('#btnsave').linkbutton('enable');
						var rows = $('#test').datagrid('getSelections');//获得选中行
						if(rows.length>1){
							alert("不能同时编辑多个模块");
							return false;
						}
						window.location.href = "${basePath}modules/toUpdateModule/"+rows[0].id;
					}
				},{
					id:'btndelete',
					text:'删除',
					iconCls:'icon-remove',
					handler:function(){
						$('#btndelete').linkbutton('enable');
						var rows = $('#test').datagrid('getSelections');//获得选中行
						var idArray = new Array();
						for(var i=0; i<rows.length; i++){
							alert(rows[i].id);
						    idArray.push(rows[i].id);
						}
						$.ajax({  
			               type: "POST",  
			               url: "modules/deleteModules",
			               data:"idArray="+idArray,
			               async: false,  
			               cache: false,  
			               success:function(data){
			               		alert(data);
			               		location.reload();
			               }  
	        			});
					}
				},'-',{
					id:'btnedit',
					text:'装载',
					iconCls:'icon-edit',
					handler:function(){
						$('#btnedit').linkbutton('enable');
						var rows = $('#test').datagrid('getSelections');//获得选中行
						var idArray = new Array();
						for(var i=0; i<rows.length; i++){
						    idArray.push(rows[i].id);
						}
						$.ajax({  
			               type: "POST",  
			               url: "modules/deployModules",
			               data:"idArray="+idArray,
			               async: false,  
			               cache: false,  
			               success:function(data){
			               		alert(data);
			               		location.reload();
			               }  
	        			});
					}
				},{
					id:'btnup',
					text:'卸载',
					iconCls:'icon-up',
					handler:function(){
						$('#btnup').linkbutton('enable');
						var rows = $('#test').datagrid('getSelections');//获得选中行
						var idArray = new Array();
						for(var i=0; i<rows.length; i++){
						    idArray.push(rows[i].id);
						}
						$.ajax({  
			               type: "POST",  
			               url: "modules/undeployModules",
			               data:"idArray="+idArray,
			               async: false,  
			               cache: false,  
			               success:function(data){
			               		alert(data);
			               		location.reload();
			               }  
	        			});
					}
				},{
					id:'btnSet',
					text:'设置',
					iconCls:'icon-85',
					handler:function(){
						$('#btnup').linkbutton('enable');
						window.location.href = "${basePath}modules/toSetLocation";
					}
				}
				]
			});
			var p = $('#test').datagrid('getPager');
			$(p).pagination({
				pageSize:10,
				onBeforeRefresh:function(){
					alert('before refresh');
				}
			});
		});
</script>
</head>

<body >
<div style="margin-left:auto; margin-right:auto;width: 900px;">
<table id="test"></table>
</div>
</body>
</html>
