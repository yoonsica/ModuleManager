<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>文件上传</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<!-- Loading Bootstrap -->
<link href="${basePath }static/bootstrap/css/bootstrap.css"
	rel="stylesheet">
<script src="${basePath }static/bootstrap/js/jquery-1.8.3.min.js"></script>
<script src="${basePath }static/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript">
    $(document).ready(function(){
    	  $("#btn").click(function(){
    	    var str = $("input[name='ejb']").val();
    	    var reg = new RegExp(".jar$");
    	    if(!reg.test(str)){
    	    	alert("ejb工程请选择jar文件！");
    	    	return false;
    	    }
    	    if(!new RegExp(".war$").test($("input[name='web']").val())){
    	    	alert("web工程请选择war文件!");
    	    	return false;
    	    }
    	    $("#form").submit();
    	  });
    	   $("#cancel").click(function(){
    	    window.location.href = "${basePath}jsp/index.jsp";
    	  });
    	});
    </script>
    <style type="text/css">
    </style>
  </head>
  
  <body >
  <div class="container">
  <div style="margin-bottom:5px;background-color: #028002;width: 100%;color: white;font-size:20px;font-weight: 800;height: 30px;line-height: 30px;">
			添加模块
	</div>
  	<form id="form" class="form-horizontal" action="${basePath }modules/addModule" method="post" enctype="multipart/form-data">  
    	<div class="control-group">
		    <label for="moduleId" class="control-label">模块ID</label>
		    <div class="controls">
		    	<div class="row">
				    <div class="span3"><input type="text" name="moduleId" id="moduleId" /></div>
		    		<div class="span4">模块ID项请填写模块英文名称</div>
		    	</div>
		    </div>
		</div>
		<div class="control-group">
		    <label for="moduleName" class="control-label">模块描述</label>
		    <div class="controls">
		    	<div class="row">
				    <div class="span3"><input type="text" name="moduleName" placeholder="模块描述项填写模块中文名称"></div>
		    		<div class="span4">模块描述项填写模块中文名称</div>
		    	</div>
		    </div>
		</div>
		<div class="control-group">
		    <label for="ejb" class="control-label">ejb工程</label>
		    <div class="controls">
		    	<div class="row">
				    <div class="span3"><input type="file" name="ejb" placeholder="ejb工程请选择jar类型文件"/></div>
		    		<div class="span4">ejb工程请选择jar类型文件</div>
		    	</div>
		    </div>
		</div>
		<div class="control-group">
		    <label for="web" class="control-label">web工程</label>
		    <div class="controls">
		    	<div class="row">
				    <div class="span3"><input type="file" name="web" placeholder="web工程请选择war类型文件"/></div>
		    		<div class="span4">web工程请选择war类型文件</div>
		    	</div>
		    </div>
		</div>
		<div class="control-group">
	    	<div class="controls">
	    		<button type="submit" class="btn btn-success" id="btn" >
				  提交
				</button> 
				<button type="cancel" class="btn btn-success" id="btn" >
				  取消
				</button> 
			</div>
    	</div>
    </form> 
    </div>
  </body>
</html>
