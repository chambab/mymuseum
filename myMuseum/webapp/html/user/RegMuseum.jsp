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

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>

<title>Register User</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css">
<script type="text/javascript">
	window.onload = function(){
		
		var userId = window.localStorage.getItem("userId");
		document.getElementById("regId").value = userId;
		
		document.getElementById("btnSave").onclick = fnRegisterUser;
		document.getElementById("userId").onblur = function(){
			var strUserId = document.getElementById("userId").value;
			/*
			var len = strUserId.length;
			if(strUserId.length > 2) {
				fnCheckUserId();
			};
			*/
			fnCheckUserId();
		};
		
		document.getElementById("btnGps").onclick = function(){
			innerlog("outGpsInfo","<img src='<%=request.getContextPath()%>/img/spinner-small.gif'>");
			window.navigator.geolocation.getCurrentPosition(success, fail, {
				 enableHighAccuracy:true,
				 maximumAge:0
			});				
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
		xhr.open("POST", "<%=request.getContextPath()%>/mm/1/user/makeMuseum.json");	
		xhr.setRequestHeader("Content-Type", "application/json");		
		xhr.send(data);		
	};
	function fnGetAddress(latlng){
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4 && xhr.status == 200){					
				innerlog("result", xhr.responseText);
			};
		};			
		xhr.open("GET", "http://maps.google.com/maps/api/geocode/json?latlng=40.714224,-73.961452&sensor=true_or_false");	
		xhr.setRequestHeader("Content-Type", "application/json");		
		xhr.send(data);		
	};
			
/* 		$.ajax({
			type: 'POST',
			url:  '/mmm/mm/1/user/makeuser.json',
			data: data,
			success: fnRegSuccess,
			dataType: 'application/json'
		});
	};
	function fnRegSuccess(data) {		
		$('result').html(data);
	} */
	function fnCheckUserId(e){
						
		var idcheckE = document.getElementById("idcheck")
		idcheckE.innerHTML = "<img src='<%=request.getContextPath()%>/img/loading.gif' width='15'>";
		
		var userId = document.getElementById("userId").value;
		var uri = "<%=request.getContextPath()%>/mm/1/user/" + userId + "/user.json";
		
/* 		$.ajax({
			type: 'GET',
			url:  uri,									
			success: function(data, status){
					alert(data);
			},
			error: function(xhr,status,e){       //에러 발생시 처리함수
				alert('Error');
			},
			dataType: 'json'
		});   */ 
		
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
	
	function success(position){
		innerlog("outGpsInfo", position.coords.latitude + "," + position.coords.longitude);
		document.getElementById("latitude").value = position.coords.latitude;
		document.getElementById("longitude").value = position.coords.longitude;
		document.getElementById("compass").value = position.coords.accuracy;
	};
	function fail(error){
		innerlog("outGpsInfo","위치정보 찾기 오류");
	};
</script>
</head>
<body>
<div id="content">
	<form id="regForm" name="regForm">
	<table>
	<tr>
		<td>
		<input id="regId" name="regId" type="text" size="15" readonly>
		</td>
	</tr>	
	<tr>
		<td>
		<input id="userId" name="userId" type="text" size="15" placeholder="미술관이름 (ID) (예:한강역나의미술관)">
		<div id="idcheck"></div>		
		</td>
	</tr>
	<tr>	
		<td>
		<input name="userNm" type="text" size="20" placeholder="미술관주제 (예:한강역의 친구만들기)">
		</td>
	</tr>
	<tr>
		<td>
		<input id="mmCategory" name="cn" type="hidden" size="20" placeholder="미술관분류 (예:#친구 #한강역)">
		<label><input type='radio' name='openYnTmp' value='Y' checked="checked">공개</label><label><input type='radio' name='openYnTmp' value='N'>비공개</label>
		</td>
	</tr>
	<tr>	
		<td colspan="2" align="left">
		<table><tr><td valign='center'>
		<img src="<%=request.getContextPath()%>/img/process.png" id="btnGps"></td><td><div id="outGpsInfo"></div>
		<input type=hidden id="latitude" name="latitude"><input type=hidden id="longitude" name="longitude"><input type=hidden id="compass" name="compass">
		</td>
		</tr></table>		
		</td>
	</tr>	
	</table>	
	</form>
	<button id="btnSave">미술관생성</button>
	<div id="log"></div>
	<div id="result"></div>
	<div id="imgDiv"></div>
	<table><tr height='200'></tr><td></td></table>
</div>	
</body>
</html>