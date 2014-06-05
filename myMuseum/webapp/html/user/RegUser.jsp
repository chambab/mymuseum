<%@page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="HandheldFriendly" content="True"> 
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="width=device-width, target-densitydpi=160dpi, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"> 
<meta name="format-detection" content="telephone=no, email=no"> 
<meta name="apple-mobile-web-app-capable" content="no"> 

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>

<title>Register User</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css">

<script type="text/javascript">
	window.onload = function(){
		
		document.getElementById("btnHome").onclick = function(){
			location = "<%=request.getContextPath()%>/index.jsp";		
		};
		document.getElementById("btnSave").onclick = fnRegisterUser;
		document.getElementById("userId").onblur = function(){
			/*
			var strUserId = document.getElementById("userId").value;			
			if(strUserId.length > 2) {
				fnCheckUserId();
			};
			*/
			fnCheckUserId();
		};
	};
	function fnRegisterUser(){
				
		innerlog("<img src='<%=request.getContextPath()%>/img/loading.gif' width='15'>");
		
		var formE = $(document.regForm);				
		var data = JSON.stringify(formE.serializeObject());
		
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4 && xhr.status == 200){					
				innerlog("result", xhr.responseText);
			};
		};			
		xhr.open("POST", "<%=request.getContextPath()%>/mm/1/user/makeuser.json");	
		xhr.setRequestHeader("Content-Type", "application/json");		
		xhr.send(data);		
	};
		
	function fnCheckUserId(e){

		
		var idcheckE = document.getElementById("idcheck")
		idcheckE.innerHTML = "<img src='<%=request.getContextPath()%>/img/loading.gif' width='15'>";
		
		var userId = document.getElementById("userId").value;
		var uri = "<%=request.getContextPath()%>/mm/1/user/" + userId + "/user.json";
		
		
 		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4 && xhr.status == 200){
				//idcheckE.innerHTML = xhr.responseText;		
				if(xhr.responseText.indexOf("not exist", 0) >= 0) {
					idcheckE.innerHTML = "<img src='<%=request.getContextPath()%>/img/accept.png'>";
				} else {
					idcheckE.innerHTML = "<img src='<%=request.getContextPath()%>/img/remove.png'>";
				};
			};
		};
				
		xhr.open("GET", uri);	
		xhr.setRequestHeader("Content-Type", "application/json");
		xhr.send(null); 
		
				
	};	
	

</script>
</head>
<body>
<div id="content">
	<form id="regForm" name="regForm">
	<table>
	<tr>
		<td>
		<input id="userId" name="userId" type="text" size="10" placeholder="사용자ID">
		<div id="idcheck"></div></td>		
	</tr>
	<tr>
		<td>
		<input name="usrPw1" type="password" size="15" placeholder="비밀번호(암호화하여 관리함)">
		</td>
	</tr>
	<tr>	
		<td>
		<input name="usrPw2" type="password" size="15" placeholder="비밀번호확인">
		</td>
	</tr>
	<tr>
		<td>
		<input name="usrEmail" type="text" size="20"  placeholder="email">
		</td>
	</tr>	
	</table>	
	</form>
	<button id="btnSave">Save</button><button id="btnHome">Home</button>
	<div id="log"></div>
	<div id="result"></div>
	<div id="imgDiv"></div>
</div>	
</body>
</html>