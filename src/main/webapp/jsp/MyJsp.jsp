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
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<!-- Loading Bootstrap -->
<link href="${basePath }bootstrap/css/bootstrap.css"
	rel="stylesheet">
<script src="${basePath }bootstrap/js/jquery-1.8.3.min.js"></script>
<script src="${basePath }bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
	function selectAll(){
		$("input[name='moduleId']").each(function(){this.checked=true;}); 
		$("#allSelect").attr("value","取消全选");
		$("#allSelect").attr("onclick","unSelectAll()");
	}
	function unSelectAll(){
		$("input[name='moduleId']").each(function(){this.checked=false;});
		$("#allSelect").attr("value","全选");
		$("#allSelect").attr("onclick","selectAll()");
	}
	function add(){
		location.href = "${basePath }jsp/upload.jsp";
	}
	function deleteModule(moduleId){
		location.href = "${basePath }modules/deleteModule/"+moduleId;
	}
	
	function update(moduleId){
		location.href = "${basePath }modules/toUpdateModule/"+moduleId;
	}
	
	function multiUnDeploy(){
		 var chk_value =[];    
		  $('input[name="moduleId"]:checked').each(function(){    
		   chk_value.push($(this).val());    
		  });    
		  alert(chk_value.length==0 ?'你还没有选择任何内容！':chk_value);
		  $.ajax({
				type : "POST",
				url : "${basePath }modules/multiUnDeploy/" + chk_value,
				success : function(data) {
					location.href = "${basePath }modules/status";
				}
			});
		//location.href = "${basePath }modules/multiUnDeploy/";
	}
	
	function deploy(btn, moduleId) {
		$(btn).attr('disabled', true);
		$.ajax({
			type : "POST",
			url : "${basePath }modules/deploy/" + moduleId,
			success : function(data) {
				$(btn).attr('value', '卸载');
				$(btn).attr("onclick", "undeploy(this,'"+moduleId+"')");
				$(btn).attr('disabled', false);
				$("#" + moduleId).text("是");
			}
		});
	}
	function undeploy(btn, moduleId) {
		$(btn).attr('disabled', true);
		$.ajax({
			type : "POST",
			url : "${basePath }modules/undeploy/" + moduleId,
			success : function(data) {
				$(btn).attr('value', '装载');
				$(btn).attr("onclick", "deploy(this,'"+moduleId+"')");
				$(btn).attr('disabled', false);
				$("#" + moduleId).text("否");
			}
		});
	}
</script>
</head>

<body>
<div class="container">
<form action="${basePath }modules/multiDeploy/">
	<div id="tableDiv" style="min-height: 180px;">
		<div style="margin-bottom:5px; background-color: #028002;width: 100%;color: white;font-size:20px;font-weight: 800;height: 30px;line-height: 30px;">
			模块列表&nbsp;<input type="button" value="添加" onclick="add()"/>&nbsp;<input type="submit" value="批量部署"/>&nbsp;<input type="button" value="批量卸载" onclick="multiUnDeploy()"/>
		</div>
		<table class="table table-bordered">
			<thead>
				<tr>
					<th><input id="allSelect" type="button" value="全选" onclick="selectAll()"/></th>
					<th>模块名</th>
					<th>访问地址</th>
					<th>是否已经加载</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="dataBody">
				<c:forEach var="module" items="${moduleList }">
					<tr>
						<td><input type="checkbox" name="moduleId" value="${module.id }"/></td>
						<td>${module.name }</td>
						<td>${module.url }</td>
						<c:if test="${module.loaded=='false' }">
							<td id="${module.id }">否</td>
							<td><input type="button" value="装载"
								onclick="deploy(this,'${module.id}')" />
								<input type="button" value="修改" onclick="update('${module.id }')" />
								<input type="button" value="删除" onclick="deleteModule('${module.id }')" />
							</td>
						</c:if>
						<c:if test="${module.loaded=='true' }">
							<td id="${module.id }">是</td>
							<td><input type="button" value="卸载"
								onclick="undeploy(this,'${module.id}')" />
								<input type="button" value="修改" onclick="update('${module.id }')" />
								<input type="button" value="删除" onclick="deleteModule('${module.id }')" />
							</td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	</form>
</div>
</body>
</html>
