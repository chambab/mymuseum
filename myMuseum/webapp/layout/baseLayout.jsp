<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="HandheldFriendly" content="True"> 
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="width=device-width, target-densitydpi=160dpi, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"> 
<meta name="format-detection" content="telephone=no, email=no"> 
<meta name="apple-mobile-web-app-capable" content="no"> 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<title><tiles:insertAttribute name="title" ignore="true" /></title>
</head>
<body  bgcolor='white' leftmargin="0" topmargin="0" border='1'>
<div id="content">
<table width='100%' cellpadding='0' cellspacing='0'>
<tr>
	<td><tiles:insertAttribute name="menu" /></td>
</tr>
<tr>
	<td><tiles:insertAttribute name="body" /></td>
</tr>
<tr>
	<td><tiles:insertAttribute name="footer" /></td>
</tr>
</table>
</div>
</body>
</html>