<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
<link href="${basePath }Flat-UI-master/bootstrap/css/bootstrap.css"
	rel="stylesheet">
<!-- Loading Flat UI -->
<link href="${basePath }Flat-UI-master/css/flat-ui.css" rel="stylesheet">

<link rel="shortcut icon"
	href="${basePath }Flat-UI-master/images/favicon.ico">
<script src="${basePath }Flat-UI-master/js/jquery-1.8.3.min.js"></script>
<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
      <script src="${basePath }Flat-UI-master/js/html5shiv.js"></script>
    <![endif]-->
<script type="text/javascript">
	function add(){
		location.href = "modules/addModule";
	}
	function deleteModule(moduleId){
		alert("${basePath }modules/deleteModule/"+moduleId)
		location.href = "${basePath }modules/deleteModule/"+moduleId;
	}
	function deploy(btn, moduleId) {
		alert(moduleId);
		$(btn).attr('disabled', true);
		$.ajax({
			type : "POST",
			url : "modules/deploy/" + moduleId,
			success : function(data) {
				$(btn).attr('value', '卸载');
				$(btn).attr("onclick", "undeploy(this,'${module.id}')");
				$(btn).attr('disabled', false);
				$("#" + moduleId).text("是");
			}
		});
	}
	function undeploy(btn, moduleId) {
		alert(moduleId);
		$(btn).attr('disabled', true);
		$.ajax({
			type : "POST",
			url : "modules/undeploy/" + moduleId,
			success : function(data) {
				$(btn).attr('value', '装载');
				$(btn).attr("onclick", "deploy(this,'${module.id}')");
				$(btn).attr('disabled', false);
				$("#" + moduleId).text("否");
			}
		});
	}
</script>
</head>

<body>
<div class="container">
	<div id="tableDiv" style="min-height: 180px;">
		<dl class="palette palette-emerald">
			<dt>设备列表<input type="button" value="添加模块" onclick="add()"/></dt>
		</dl>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>模块名</th>
					<th>访问地址</th>
					<th>是否已经加载</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="dataBody">
				<c:forEach var="module" items="${moduleList }">
					<tr>
						<td>${module.name }</td>
						<td>${module.url }</td>
						<c:if test="${module.loaded=='false' }">
							<td id="${module.id }">否</td>
							<td><input type="button" value="装载"
								onclick="deploy(this,'${module.id}')" />
								<%--<input type="button" value="修改" onclick="update('${module.id }')" />
								--%><input type="button" value="删除" onclick="deleteModule('${module.id }')" />
							</td>
						</c:if>
						<c:if test="${module.loaded=='true' }">
							<td id="${module.id }">是</td>
							<td><input type="button" value="卸载"
								onclick="undeploy(this,'${module.id}')" />
								<input type="button" value="删除" onclick="deleteModule('${module.id }')" />
							</td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
</body>
</html>
