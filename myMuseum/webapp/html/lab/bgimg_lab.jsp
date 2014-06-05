<%@page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="HandheldFriendly" content="True"> 
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="width=device-width, target-densitydpi=160dpi, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"> 
<meta name="format-detection" content="telephone=no, email=no"> 
<meta name="apple-mobile-web-app-capable" content="no"> 

<title>bgimg_lab</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css">  

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>

<body style="background-image:url('./bg.jpg')">
<table cellpadding='1' cellspacing='1' width='500'
style="-webkit-border-radius: 5px;">
<tr height='50'">
	<td bgcolor='white' style="-webkit-border-radius: 5px;">
	<div><font color=white>aaaaaaaa</font></div>
	</td>
</tr>
<tr height='50'>
	<td bgcolor='white'>
	<div><font color='black'>bbbbbbb</font></div>
	</td>
</tr>
<tr height='50' style="filter:alpha(opacity=30);opacity:0.3; -moz-opacity:0.3;-webkit-border-radius: 5px;" >
	<td bgcolor='white'>bbbbbbb
	</td>
</tr>
<tr height='50' style="background:rgba(255,0,0,0.1);-webkit-border-radius:5px;" >
	<td>ccccccccccc
	</td>
</tr>
</table>
</body>
</html>