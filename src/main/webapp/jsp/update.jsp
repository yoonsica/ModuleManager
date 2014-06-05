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
<link href="${basePath }bootstrap/css/bootstrap.css"
	rel="stylesheet">
<script src="${basePath }bootstrap/js/jquery-1.8.3.min.js"></script>
<script src="${basePath }bootstrap/js/bootstrap.min.js"></script>
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
    	   $("#cancel").click(function(){
    	    window.location.href = "${basePath}jsp/index.jsp";
    	  });
    	});
    </script>
  </head>
  
  <body>
  <div class="container">
  <div style="margin-bottom:5px; background-color: #028002;width: 100%;color: white;font-size:20px;font-weight: 800;height: 30px;line-height: 30px;">
			更新模块
	</div>
  	<form id="form" class="form-horizontal" action="${basePath }modules/updateModule" method="post" enctype="multipart/form-data">  
    	<div class="control-group">
		    <label for="moduleId" class="control-label">模块ID</label>
		    <div class="controls">
		    	<div class="row">
    		    	<div class="span3"><input type="text" name="moduleId" value="${module.id }" placeholder="模块ID项请填写模块英文名称"/></div>
    		    	<div class="span4">模块ID项请填写模块英文名称</div>
		   		</div>
		    </div>
		</div>
    	<div class="control-group">
		    <label for="moduleName" class="control-label">模块描述</label>
		    <div class="controls">
			    <div class="row">
			    	<div class="span3">
			    		<input type="text" name="moduleName" value="${module.name }" placeholder="模块描述项填写模块中文名称">
					</div>
					<div class="span4">
				    	 模块描述项填写模块中文名称
				    </div>
				</div>
		    </div>
		</div>
    	
    	<c:forEach var="project" items="${module.projects }">
    		<c:if test="${project.type=='ejb' }">
	    		<div class="control-group">
	    			<label for="ejb" class="control-label">ejb工程</label>
	    			<div class="controls">
	    				<div class="row">
			    			<input type="file" id="ejb" name="ejb" style="display: none;"/>
			    			<div class="span3"><input type="text" name="ejbloc" value="${project.name }"/></div>
			    			<div class="span4"><input type="button" id="selejbfile" onclick="reselejbfile()" value="重新选择ejb工程" /></div>
		    			</div>
	    			</div>
	    		</div>
    		</c:if>
    		<c:if test="${project.type=='web' }">
	    		<div class="control-group">
	    			<label for="web" class="control-label">web工程</label>
	    			<div class="controls">
		    			<div class="row">
		    				<input type="file" name="web" style="display: none;"/>
	    			    	<div class="span3"><input type="text" name="webloc" value="${project.name }"/></div>
	    			    	<div class="span4"><input type="button" id="selwebfile" onclick="reselwebfile()" value="重新选择web工程"/></div>
		    			</div>
	    			</div>
	    		</div>
    		</c:if>
    	</c:forEach>
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
