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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css">
<title>My Profile</title>
<script type="text/javascript">
	window.onload = function(){
		
		var fileElement = document.getElementById("btnUpload");
		fileElement.onclick = function(){
			fnFileUpload();
		};
		
		document.getElementById("cn").onclick = function(){
			//document.getElementById("cn").style = "border:1";
			//$('follower').border('5px red .6');
		};
		document.getElementById("follower").onclick = function(){
			location = "<%=request.getContextPath()%>/html/user/follower.jsp";
		};
		document.getElementById("following").onclick = function(){
			location = "<%=request.getContextPath()%>/html/user/following.jsp";
		};		
		fnCheckUserId();
	};
	
	function fnCheckUserId(e){
			

		innerlog("<img src='<%=request.getContextPath()%>/img/loading.gif' width='15'>");
		
		var userId = window.localStorage.getItem("userId");
		var uri = "<%=request.getContextPath()%>/mm/1/user/" + userId + "/profile.json";
			
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4 && xhr.status == 200){
				innerlog("");
				var obj = JSON.parse(xhr.responseText); 
				
				innerlog("following", obj.followingCnt);
				innerlog("follower", obj.followerCnt);				
				innerlog("regDt", fnDay(obj.regDt));
				//innerlog("cn", obj.cn);
				document.getElementById("cn").value = obj.cn;
				innerlog("userId", obj.userId);
				innerlog("userNm", obj.userNm);
				innerlog("webUrl", obj.webUrl);				
				innerlog("messageCnt", obj.messageCnt);
				
				if(obj.userImg == null || obj.userImg.length < 1) {
					obj.userImg = "<%=request.getContextPath()%>/img/noimg.png";
				} else {
					obj.userImg = "http://" + obj.userImg;
				}				
				innerlog("imgDiv", "<img src='" + obj.userImg + "' width='50'>");
			};
		};
				
		xhr.open("POST", uri);	
		xhr.setRequestHeader("Content-Type", "application/json");
		xhr.send(null); 
	};	
	// 파일 업로드	
	function fnFileUpload(){	
		// img/{userId}/uploadprofile.json
		var userId = window.localStorage.getItem("userId");
		var posturl = "/mm/1/img/" + userId + "/uploadprofile.json";
		
		var imgE = document.getElementById("imgDiv");
		imgE.innerHTML = "<img src='<%=request.getContextPath()%>/img/loading.gif' width='15'>";
		
		var fileE = document.getElementById("fileId");				
 				
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4 && xhr.status == 200){
				imgE.innerHTML = "";
				var obj = JSON.parse(xhr.responseText); 
				if(obj.imgUrl == null || obj.imgUrl.length < 1) {
					obj.imgUrl = "<%=request.getContextPath()%>/img/noimg.png";
				} else {
					obj.imgUrl = "http://" + obj.imgUrl;
				}				
				//var msg = "<table width='100%'><tr class='td_white'><td width='50' align=center  class='td_img'>" + 
				//	      "<img src='http://" + obj.imgUrl + "' width='50'></td></table>";
				var msg = "<img align='center' src='" + obj.imgUrl + "' width='50'>";
				//imgE.insertAdjacentHTML("afterend", msg);
				//imgE.innerHTML(msg);
				innerlog("imgDiv", msg);
			};
			if(xhr.readyState == 4 && xhr.status == 404){
				document.getElementById("imgDiv").innerHTML = "Cannot Find it!";
			};			
		};
 		xhr.upload.onprogress = function(e){			
 			if(e.lengthComputable){
				var value = Math.round((e.loaded /e.total) * 100);
				innerlog(value + "%");
			}; 
		}; 
		
		xhr.open("POST", "<%=request.getContextPath()%>" + posturl);		
		xhr.setRequestHeader("X-File-Name", fileE.files[0].name);
		xhr.send(fileE.files[0]);		
	};
	
</script>
</head>
<body>
<div id="content">
	<div id="log"></div>
	<table width='95%'>
	<tr>
		<td height="50" class="td_white">
		<table width='100%'>
		<tr class='td_white'>
			<td width='50' height='50' align=center  class='td_img'>
			<div id="imgDiv"></div>
			</td>	
			<td>
			<div id="userId"></div>
			</td>	
			<td>
			<div id="userNm"></div>
			</td>						
		</tr>
		</table>	
		</td>
	</tr>
	<tr>
		<td bgcolor="#eeeeee" class="td_white">
			<input type="file" id="fileId"><button id="btnUpload">나의사진등록</button>
		</td>
	</tr>
	<tr>
		<td valign="top" height="40"  class="td_white">
		<b>자기소개</b> : <input type="text" id="cn" style='border:0' readonly="readonly">
		</td>
	</tr>
	<tr>
		<td valign="top" height="40" class="td_white"><b>웹</b><div id="webUrl"></div><br>
		</td>
	</tr>
	<tr>
		<td valign="top" height="40" class="td_white"><b>위치</b><br>
		</td>
	</tr>
	<tr>
		<td valign="top" height="40" class="td_white"><b>등록글</b><div id="messageCnt"></div><br>
		</td>
	</tr>
	<tr>
		<td valign="top" height="40" class="td_white"><b>팔로잉</b><div id="following"></div><br>
		</td>
	</tr>
	<tr>
		<td valign="top" height="40" class="td_white"><b>팔로워</b><div id="follower"></div><br>
		</td>
	</tr>
	<tr>
		<td valign="top" height="40" class="td_white"><b>가입일</b><div id="regDt"></div><br>
		</td>
	</tr>
	</table>
</div>
</body>
</html>