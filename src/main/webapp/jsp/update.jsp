<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>更新模块</title>
    
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
<script src="${basePath }Flat-UI-master/js/bootstrap.min.js"></script>
<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
<!--[if lt IE 9]>
      <script src="${basePath }Flat-UI-master/js/html5shiv.js"></script>
    <![endif]-->
    <script type="text/javascript">
    function reselejbfile(){
    	$("#selejbfile").hide();
    	$("input[name='ejbloc']").hide();
    	$("input[name='ejb']").show();
    }
    function reselwebfile(){
    	$("#selwebfile").hide();
    	$("input[name='webloc']").hide();
    	$("input[name='web']").show();
    }
    $(document).ready(function(){
    	  $("#btn").click(function(){
    		  alert("submit");
    	    var str = $("input[name='ejb']").val();
    	    var reg = new RegExp(".jar$");
    	    if(str!=''){
    	    	if(!reg.test(str)){
        	    	alert("ejb工程请选择jar文件！");
        	    	return false;
        	    }
    	    }
    	    if($("input[name='web']").val()!=''){
    	    	if(!new RegExp(".war$").test($("input[name='web']").val())){
        	    	alert("web工程请选择war文件!");
        	    	return false;
        	    }
    	    }
    	    $("#form").submit();
    	  });
    	});
    </script>
  </head>
  
  <body>
  <div class="container">
  <dl class="palette palette-emerald">
			<dt>添加模块</dt>
	</dl>
  	<div style="max-width: 500px;min-width: 500px;float:left;">
  	<form id="form" action="${basePath }modules/updateModule" method="post" enctype="multipart/form-data">  
    	模块ID<input type="text" name="moduleId" class="form-control" value="${module.id }"/>
    	模块描述<input type="text" name="moduleName" class="form-control" value="${module.name }">
    	<c:forEach var="project" items="${module.projects }">
    		<c:if test="${project.type=='ejb' }">ejb工程 <input type="file" name="ejb" class="form-control" style="display: none;"/><input type="text" name="ejbloc" class="form-control" value="${project.name }"/><input type="button" id="selejbfile" onclick="reselejbfile()" value="重新选择ejb工程" /> </c:if>
    		<c:if test="${project.type=='web' }"><br/>web工程<input type="file" name="web" class="form-control" style="display: none;"/><input type="text" name="webloc" class="form-control" value="${project.name }"/><input type="button" id="selwebfile" onclick="reselwebfile()" value="重新选择web工程"/></c:if>
    	</c:forEach>
       
    </form> 
    <button class="btn btn-default btn-wide" id="btn" >
		  提交
		</button> 
  	</div>
  	<div style="float: left;margin: 20px,0,0,20px;">
  	<ul>
  		<li>模块ID项请填写模块英文名称</li>
  		<li>模块描述项填写模块中文名称</li>
  		<li>ejb工程请选择jar类型文件</li>
  		<li>web工程请选择war类型文件</li>
  	</ul>
  	</div>
    </div>
  </body>
</html>
