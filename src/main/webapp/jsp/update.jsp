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
<script src="${basePath }static/bootstrap/js/jquery-1.8.3.min.js"></script>
<style type="text/css">
    .base-div { 
    	width: 800px; 
    	height: 600px; 
    	left: 50%; 
    	margin: 0 0 0 -400px; 
    	position: absolute; 
    }
    </style>
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
    	  $("#submitBtn").click(function(){
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
    	   $("#cancleBtn").click(function(){
    	    window.location.href = "${basePath}jsp/index.jsp";
    	  });
    	});
    </script>
  </head>
  
  <body>
  <div class="base-div">
  	<form id="form" action="${basePath }modules/updateModule" method="post" enctype="multipart/form-data">  
    	<table width="100%">
  		<tr><td colspan="3"><div style="margin-bottom:5px; background: url('${basePath}static/easyui/themes/default/images/panel_title.png') repeat;width: 100%;font-size:20px;font-weight: 800;height: 30px;line-height: 30px;">
			更新模块
	</div></td></tr>
  			<tr>
  				<td><label for="moduleId">模块ID</label></td>
  				<td><input type="text" name="moduleId" id="moduleId" value="${module.id }" style="width: 200;"/></td>
  				<td><div>模块ID项请填写模块英文名称</div></td>
  			</tr>
  			<tr>
  				<td><label for="moduleName">模块描述</label></td>
  				<td><input type="text" name="moduleName" value="${module.name }" style="width: 200;"></td>
  				<td><div>模块描述项填写模块中文名称</div></td>
  			</tr>
  			<c:forEach var="project" items="${module.projects }">
    			<c:if test="${project.type=='ejb' }">
		  			<tr>
		  				<td><label for="ejb" >ejb工程</label></td>
		  				<td><input type="file" id="ejb" name="ejb" style="display: none;"/><input type="text" name="ejbloc" value="${project.name }" style="width: 200;"/></td>
		  				<td><input type="button" id="selejbfile" onclick="reselejbfile()" value="重新选择ejb工程" /></td>
		  			</tr>
  				</c:if>
  				<c:if test="${project.type=='web' }">
		  			<tr>
		  				<td><label for="web" class="control-label">web工程</label></td>
		  				<td><input type="file" name="web" style="display: none;"/><input type="text" name="webloc" value="${project.name }" style="width: 200;"/></td>
		  				<td><input type="button" id="selwebfile" onclick="reselwebfile()" value="重新选择web工程"/></td>
		  			</tr>
	  			</c:if>
    		</c:forEach>
  		</table>
    	
	    <div align="center" style="margin-top: 10px;"><input type="submit" id="submitBtn" value="确定"/>&nbsp;&nbsp;&nbsp;<input type="button" id="cancleBtn" value="取消"/></div>
    </form> 
  	</div>
  </body>
</html>
