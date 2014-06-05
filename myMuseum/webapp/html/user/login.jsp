<%@page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>

<meta name="HandheldFriendly" content="True"> 
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="width=device-width, target-densitydpi=160dpi, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"> 
<meta name="format-detection" content="telephone=no, email=no"> 
<meta name="apple-mobile-web-app-capable" content="no"> 
    
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css">
<script type="text/javascript">
	window.onload = function(){
		document.getElementById("btnLogin").onclick = fnUserLogin;
		document.getElementById("btnLogout").onclick = fnUserLogout;
		document.getElementById("btnReg").onclick = fnRegUser;
		
		var userId = localStorage.getItem("userId");
		if(userId.length > 0){			
			document.getElementById("menu1").style.display = "none";
			document.getElementById("menu2").style.display = "block";			
		} else {
			document.getElementById("menu1").style.display = "block";
			document.getElementById("menu2").style.display = "none";				
		};
	};
	function fnRegUser(){
		location = "<%=request.getContextPath()%>/html/user/RegUser.jsp";
	};
	function fnUserLogout(){
		window.localStorage.setItem("userId", "");
		location = "<%=request.getContextPath()%>/html/search/mmm.jsp";		
	};
	function fnUserLogin() {
		
		innerlog("<img src='<%=request.getContextPath()%>/img/loading.gif' width='15'>");
		
		var formE = $(document.loginForm);				
		var data = JSON.stringify(formE.serializeObject());
		
   		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4 && xhr.status == 200){					
				//innerlog(xhr.responseText);
				var obj = JSON.parse(xhr.responseText); 
				//log(obj.userId);
				if(obj.userId == "FAIL") {
					alert("존재하지 않는 사용자 입니다");
					innerlog("");
				} else {
					fnSaveStorage(obj.userId);
				}
			} else if(xhr.readyState == 4 && xhr.status != 200){
				alert("ee");
			};
		};			
		xhr.open("POST", "<%=request.getContextPath()%>/mm/1/user/loginuser.json");	
		xhr.setRequestHeader("Content-Type", "application/json");		
		xhr.send(data);   
		
/*    		$.ajax({
			type: 'POST',
			url:  '/mmm/mm/1/user/loginuser.json',						
			data: data,
			success: fnRegSuccess,
			error: function(xhr,status,e){       //에러 발생시 처리함수
				alert('Error');
			},
			dataType: 'json'
		});   */ 	
		
	};

	function fnSaveStorage(userId){
		window.localStorage.setItem("userId", userId);
		location = "<%=request.getContextPath()%>/html/search/mmm.jsp";
	};
	function fnRegSuccess(data) {		
		$('log').html(data);
	} 	
	
</script>

</head>
<body>
<div id="content">
	<form id="loginForm" name="loginForm">
	<table>
	<tr>
		<td>
		<input id="userId" name="userId" type="text" size="15" placeholder="사용자ID">
		</td>
	</tr>
	<tr>	
		<td>
		<input name="userPw" type="password" size="15" placeholder="비밀번호">
		</td>
	</tr>	
	</table>
	</form>
	<div id="menu1">
	<button id="btnLogin" class="text">Login</button>
	<button id="btnReg" class="text">회원가입</button>
	</div>
	<div id="menu2"><button id="btnLogout" class="text">Logout</button></div>
		
	<div id="log"></div>
</div>
</body>
</html>