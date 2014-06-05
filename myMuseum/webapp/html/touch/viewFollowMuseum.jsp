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

<title>MyMuseum</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css">
<script type="text/javascript">
	window.onload = function(){	
		
		var msgId = getParam("msgId");		
		
		var strUri = '<%=request.getContextPath()%>/mm/1/msg/' + msgId + '/message.json';
 		$.ajax({
			type: 'GET',
			url:  strUri,									
			success: fnGetMessageSuccess,
			error: function(xhr,status,e){       //에러 발생시 처리함수
				alert('Error');
			},
			dataType: 'json'
		}); 
 		
/*    		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4 && xhr.status == 200){					
				//innerlog(xhr.responseText);
				var obj = JSON.parse(xhr.responseText); 
			};
		};	 */		
	};
	function fnGetMessageSuccess(data, status) {	
		
		data.msgCn = data.msgCn.replace(/\n/g, "<br>");
		
		document.getElementById("msgCn").innerHTML =  
		"<table>" +
        "<tr height='60' class='td_white'>" +
        "<td width='100%' valign='top' class='td_list'>" +
        "<table width='100%'><tr><td align=left class='td_info'>" + 
        data.userId + "</td><td align='right' class='td_info'>" + 
        data.regDt + "</td></tr></table>" +                        
        data.msgCn + "</td></tr></table>";		                                              
	}

</script>
</head>
<body  bgcolor='white' leftmargin="0" topmargin="0">
<div id="content">	
<table><tr height='3'></tr></table>
<table width='100%' bgcolor=#eeeeee>
<tr>
	<td height='100' valign='top' bgcolor=white><div id='msgCn' name="msgCn"></div></td>
</tr>
</table>   
	<div id="msg"></div><br>
	<div align="center">
	<!-- div id="log"></div -->
	<img id="load1" src="<%=request.getContextPath()%>/img/refresh.png">
	</div>	
</div>
</body>
</html>