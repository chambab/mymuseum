<%@page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="HandheldFriendly" content="True"> 
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="width=device-width, height=device-height, target-densitydpi=160dpi, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"> 
<meta name="format-detection" content="telephone=no, email=no"> 
<meta name="apple-mobile-web-app-capable" content="no"> 

<title>mmm!</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>

<script type="text/javascript">
	window.onload = function(){	

		
		
		innerlog("museDiv","<img src='<%=request.getContextPath()%>/img/spinner-small.gif'>");
		
		if(window.localStorage.getItem("userId")) {
			//document.getElementById("aLogin").innerHTML = window.localStorage.getItem("userId");
			document.getElementById("aLogin").innerHTML = "LOGOUT";
		}
		
		document.getElementById("btnSearch").onclick = function(){
			var query = document.getElementById("inpSearch").value;
			fnSearch(query);
		};
		
		document.getElementById("btnLogin").onclick = fnUserLogin;
		
		document.getElementById("queryMuseum").oninput = function(){
			
			var queryMuseum = document.getElementById("queryMuseum").value;			

			if(queryMuseum.length > 1) {
				innerlog("museDiv","<img src='<%=request.getContextPath()%>/img/spinner-small.gif'>");
				var strUri = '<%=request.getContextPath()%>/mm/1/museum/museumReallist.json';

		 		$.ajax({
					type: 'POST',
					url:  strUri,									
					cache: false,
					success: fnLoadMuseumList,
					error: function(xhr,status,e){       //에러 발생시 처리함수
						alert('Error');
					},
					data: "queryMuseum=" + queryMuseum,
					dataType: 'json'
				}); 
			}
			if(queryMuseum.length == 0) {
				fnSelectMuseum();
			};
		}; 
		 
		var userId = window.localStorage.getItem("userId");
		if(userId != null && userId.length > 0) {
			$('#mDiv').fadeIn('slow');
			fnSelectMuseum();
		} else {
			$('#loginDiv').fadeIn('slow');
		}
		
		
	};
	// 최초조회
	function fnSelectMuseum(){
		
		var userId = window.localStorage.getItem("userId");
		var strUri = "";
		if(userId != null && userId.length > 0) {
			strUri = '<%=request.getContextPath()%>/mm/1/museum/' + userId + '/museumPubliclist.json';
		} else {
			strUri = '<%=request.getContextPath()%>/mm/1/museum/museumlist.json';	
		}
		
 		$.ajax({
			type: 'GET',
			url:  strUri,									
			cache: false,
			success: fnLoadMuseumList,
			error: function(xhr,status,e){       //에러 발생시 처리함수
				alert('Error');
			},			
			dataType: 'json'
		});			
	};
	function fnLoadMuseumList(data){
		
		var museuE = document.getElementById("museDiv");
		var myE = document.getElementById("MyMuseDiv");
		var museumContent = "<table width='100%' cellpadding='0' cellspacing='0' style='background:rgba(255,255,255,0.3);'><tr><td height='1' width='50%'  class='td_info_main' style='background:rgba(255,255,255,0.3);'>";
		var myContent = "<table width='100%' cellpadding='0' cellspacing='0'  style='background:rgba(255,255,255,0.6);'><tr><td height='1' width='50%' class='td_info_main' style='background:rgba(255,255,255,0.3);'>";
		
		for(var i=0; i < data.museum.length; i++) {
			
			if(data.museum[i].seCode == '002') {							
				museumContent += "ⓜ <a href='javascript:fnSearch(\"" + data.museum[i].userId + "\")'>" + 
				                 data.museum[i].userId + "</a>(" + data.museum[i].mcnt + ")&nbsp;" + 
				                 "</td><td height='23' class='td_info_main' style='background:rgba(255,255,255,0.3);'>" + data.museum[i].userNm + "</td></tr><tr><td height='23' class='td_info_main' style='background:rgba(255,255,255,0.3);'>";
			} else {
				myContent += "ⓜ <a href='javascript:fnSearch(\"" + data.museum[i].userId + "\")'>" + 
                				 data.museum[i].userId + "</a>(" + data.museum[i].mcnt + ")&nbsp;" + 
				              "</td><td height='23' class='td_info_main' style='background:rgba(255,255,255,0.3);'>" + data.museum[i].userNm + "</td></tr><tr><td height='23' class='td_info_main' style='background:rgba(255,255,255,0.3);'>";				
			}
				
		}
		//$(museumContent).appendTo("#museDiv");
		museumContent += "</td></tr></table>";
		museuE.innerHTML = museumContent;
		myE.innerHTML = myContent;
		
		var cntContents = "";
		for(var i=0; i < data.user.length; i ++) {
			if(data.user[i].userSrCode == '001') {
				cntContents += "@" + data.user[i].userCnt + " ";
			} else {
				cntContents += data.user[i].userCnt + " ";
			}
		}
		var cntE = document.getElementById("cntDiv");
		cntE.innerHTML = cntContents;
		
	};
	function fnSearch(query){		
		setParam("query", query);
		location = "./mySearchList.jsp";		
	};	
	function fnUserLogin() {
		
		
		
		// innerlog("<img src='<%=request.getContextPath()%>/img/loading.gif' width='15'>");
 		
		var formE = $(document.loginForm);				
		var data = JSON.stringify(formE.serializeObject());
		
   		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4 && xhr.status == 200){					
				
				var obj = JSON.parse(xhr.responseText); 
			 	
				if(obj.userId == "FAIL") {
					alert("존재하지 않는 사용자 입니다");
					// innerlog("");
				} else {
					fnSaveStorage(obj.userId);
				}
			} else if(xhr.readyState == 4 && xhr.status != 200){
				alert("ee");
			};
		};			
		xhr.open("POST", "<%=request.getContextPath()%>/mm/1/user/loginuser.json");	
		xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");		
		xhr.send(data);
		
		
		$('#loginDiv').fadeOut('slow');
		
	};
	function fnSaveStorage(userId){
		window.localStorage.setItem("userId", userId);
		$('#mDiv').fadeIn('slow');
		fnSelectMuseum();
	};	
	function fnUserLogout(){
		window.localStorage.setItem("userId", "");
		location = "<%=request.getContextPath()%>/html/search/mmm.jsp";		
	};	
	function fnRegUser(){
		location = "<%=request.getContextPath()%>/html/user/RegUser.jsp";
	};	
</script>
</head>
<body style="background-image:url('/img/bg/bg16.jpg')">
<div id="content" style="border: 0px solid #ccc;">
<table width='100%' height='600'
       cellpadding='1' cellspacing='5' bgcolor='#eeeeee' style="background:rgba(255,255,255,0.2)">     
<tr><td align='center' valign='top'>
<table width='100%'>
<tr>
	<!-- td align="center">나의작은 미술관 <font style='font-family: verdana; font-size:11pt'>MYMUSEUM</font></td -->
	<td align='center' valign='center'>
	<table width='100%' style="background:rgba(255,255,255,0.6);-webkit-border-radius: 5px;-webkit-box-shadow:0px 0px 2px rgba(0,0,0,0.4);">
	<tr>
	<td align='left' width='20'><img align='center' src='<%=request.getContextPath()%>/img/m.png' width='20'></td>
	<td align='left'></font><div id="cntDiv"></div></td>
	<td align='right'><a id="aLogin" href="<%=request.getContextPath()%>/html/user/login.jsp">로그인</a> | <a href="<%=request.getContextPath()%>/indexm.html">홈으로</a></td>
	</tr>
	</table>
	</td>
</tr>
<tr height='10'><td></td></tr>
</table>
<table width='95%' cellpadding='0' cellspacing='0'>
<tr>
	<td align='center'><img src="<%=request.getContextPath()%>/img/mmm.gif" width='260'></td>
</tr>
</table>
<div id='mDiv'  style='display:none'>
<table><tr height='30'><td></td></tr></table>
<table cellpadding='0' cellspacing='0' width='100%' style="-webkit-border-radius: 5px;-webkit-box-shadow:0px 0px 2px rgba(0,0,0,0.4);"> 
<tr>
	<td class="top_space" style="background:rgba(255,255,255,0.4);-webkit-border-radius: 5px;">&nbsp;ⓜ&nbsp;<input type='text' name='queryMuseum' id='queryMuseum' style='background:rgba(255,255,255,0.4); border:0;width:200px' placeholder='실시간미술관검색 (Click)'></td>
</tr>
</table>
<table cellpadding='1' cellspacing='1' width='100%' style='-webkit-box-shadow:0px 0px 2px rgba(0,0,0,0.4);'>
<tr>
	<td align='center' style="background:rgba(255,255,255,0.4);">
	<div id='MyMuseDiv'></div>
	</td>
</tr>
</table>
<table  cellpadding='1' cellspacing='1' width='100%' style='-webkit-box-shadow:0px 0px 2px rgba(0,0,0,0.4);'>
<tr>
	<td style='background:rgba(255,255,255,0.1);'></td>
</tr>
<tr>
	<td align='center' style="background:rgba(255,255,255,0.4);">
	<div id='museDiv'></div>
	</td>
</tr>
</table>
</div>
<div id='loginDiv'  style='display:none'>
<table><tr height='100'><td></td></tr></table>
<table cellpadding='5' cellspacing='2' width='50%' style="background:rgba(255,255,255,0.6);-webkit-border-radius: 5px;-webkit-box-shadow:0px 0px 2px rgba(0,0,0,0.4);">
<tr>
	<td align='center'>
	<table>
	<tr>
		<td>
			<form id="loginForm" name="loginForm">
			<table>
			<tr><td height='20'>로그인 | <a href='javascript:fnRegUser()'>회원가입</a></td></tr>
			<tr>
				<td>
				<input type="text"  id='userId' name='userId' style='background:rgba(255,255,255,0.6);-webkit-box-shadow:0px 0px 2px rgba(0,0,0,0.4) border:0;width:120px' placeholder='ID'>
				</td>
			</tr>
			<tr>
				<td>
				<input type="password"  name='userPw' style='background:rgba(255,255,255,0.6);-webkit-box-shadow:0px 0px 2px rgba(0,0,0,0.4) border:0;width:120px' placeholder='Passwd'>
				</td>
			</tr>
			</table>
			</form>
		</td>
		<td valign='center' align='center'>
			<table>
			<tr><td height='20'></td></tr>
			<tr>			
				<td rowspan='2'>
				<button id="btnLogin" class="text">Login</button>
				</td>		
			</tr>	
			</table>
		</td>
	</tr>
	</table>	
	</td>
</tr>
</table>
</div>
</td></tr></table>
<table border='0' width='100%'>
<tr>
	<td align="center">
		<input type="text" size="30" name="search" id="inpSearch" placeholder="#해쉬태그, $상품, 아이디 찾기~" autocomplete="on">		
	</td>
</tr>
<tr>
	<td align="center" >
		<button id="btnSearch">검색</button>
	</td>
</tr>
</table>
</div>
</body>
</html>
