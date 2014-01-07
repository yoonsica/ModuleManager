<%@ page language="java" contentType="text/html; charset=utf8"  
    pageEncoding="UTF8"%>  
<%@ page import="java.lang.Exception"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html> 
<head><title>Exception!</title></head> 
<body> 
<% Exception ex = (Exception)request.getAttribute("exception"); %> 
<H2>Exception: <% ex.getMessage();%></H2> 
<P/> 
<% ex.printStackTrace(new java.io.PrintWriter(out)); %> 
</body> 
</html>