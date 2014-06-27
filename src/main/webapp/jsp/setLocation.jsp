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
    
    <title>设置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script src="${basePath }static/easyui/jquery-1.7.2.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#submitBtn").click(function(){
				$.ajax({
					type: "POST",  
	               	url: "${basePath}modules/setLocation",
	               	data:$("#locationForm").serialize(),
	              	async: false,  
	               	cache: false,  
		            success:function(data){
	               		alert(data);
	               		window.location.href = "${basePath}jsp/index.jsp";
		             }  
				});
			});
			$("#cancleBtn").click(function(){
				window.location.href = "${basePath}jsp/index.jsp";
			});
		});
	</script>
  </head>
  
  <body >
  	<form id="locationForm">
  	<div style="margin-left:auto; margin-right:auto; width:500px; ">
         <table>
	  		<tr>
	  			<td>jboss根目录</td><td><input name="jbossLocation" value="${jbossLocation }" style="width:211px;"/></td>
	  		</tr>
	  		<tr>
	  			<td>模块库</td><td><input name="modulesLocation" value="${modulesLocation }" style="width:211px;"/></td>
	  		</tr>
	  		<tr>
	  			<td><input type="button" id="submitBtn" value="确定"></td><td><input type="button" id="cancleBtn" value="取消"/></td>
	  		</tr>	
	  	</table>
  	</div>
	  	
  	</form>
  </body>
</html>
