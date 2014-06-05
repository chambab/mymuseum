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

<title>Touch</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css">  

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript">
	window.onload = function(){
		
		var fileElement = document.getElementById("btnUpload");
		fileElement.onclick = function(){
			fnFileUpload();
		};
		var btnSaveE = document.getElementById("btnSave").onclick = fnMsgSave;
		fnlocalStorage();
	};
	
	function fnlocalStorage(){
		if(window.localStorage.getItem("userId")) {
			document.getElementById("ouserId").value = window.localStorage.getItem("userId");
		}
	}
	function fnMsgSave(){
		
		document.getElementById("log").innerHTML = "<img src='<%=request.getContextPath()%>/img/loading.gif' width='15'>";
		
		var formE = $(document.msgForm);				
		var data = JSON.stringify(formE.serializeObject());
		
   		var xhr1 = new XMLHttpRequest();
		xhr1.onreadystatechange = function() {
			if(xhr1.readyState == 4 && xhr1.status == 200){					
				//document.getElementById("log").innerHTML = xhr1.responseText;				
				document.getElementById("log").innerHTML = "등록완료";
			};
			if(xhr1.readyState == 4 && xhr1.status != 200){					
				innerlog("등록실패");
			};			
		};			
		xhr1.open("POST", "<%=request.getContextPath()%>/mm/1/msg/makemsg.json");	
		xhr1.setRequestHeader("Content-Type", "application/json;charset=UTF-8");		
		xhr1.send(data);	

/* 		$.ajax({
			type: 'POST',
			url:  '/mmm/mm/1/msg/makemsg.json',						
			data: data,
			success: function(){
				alert('success');
			},
			error: function(xhr,status,e){       //에러 발생시 처리함수
				alert('Error');
			},
			dataType: 'json'
		}); */   	
	};
	// 파일 업로드	
	function fnFileUpload(){	
		var imgE = document.getElementById("imgDiv");
		imgE.innerHTML = "<img src='<%=request.getContextPath()%>/img/loading.gif' width='15'>";
		
		var fileE = document.getElementById("fileId");				
 				
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4 && xhr.status == 200){
				var imgIdE = document.getElementById("imgId");				
				
				imgE.innerHTML = "";
				//document.getElementById("imgDiv").innerHTML = "<img src='" + xhr.responseText + "'>";
				//document.getElementById("imgDiv").innerHTML = xhr.responseText;
				var obj = JSON.parse(xhr.responseText); 
				var msg = "<table width='100%'><tr class='td_white'><td width='100' align=center  class='td_img'>" + 
					      "<img src='http://" + obj.imgUrl + "' width='100'></td>" +
				          "<td align=left valign='top'  class='td_info'>" + obj.imgUrl + "<br><input type='text' name='imgId' value='" + obj.imgId + "' width='50' style='border:0;width:80px' readonly='readonly'></td>" +
				          "</tr></table>";
				var divFileMsg = "<input type='hidden' name='imgUrl' value='" + obj.imgUrl + "' style='border:0;width:0px' readonly='readonly'>" +
				                 "<input type='hidden' name='imgId' value='" + obj.imgId + "' style='border:0;width:0px' readonly='readonly'>";
				if(imgIdE == null) {
					divFileMsg = "<input type='hidden' name='imgUrl' value='' style='border:0;width:0px' readonly='readonly'>" +
					             "<input type='hidden' name='imgId' value='IMSI' style='border:0;width:0px' readonly='readonly'>" +
					             divFileMsg;
				}
				                 
				imgE.insertAdjacentHTML("afterend", msg);
				$(divFileMsg).appendTo("#divFile");
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
		
		xhr.open("POST", "<%=request.getContextPath()%>/mm/1/img/upload.json");		
		xhr.setRequestHeader("X-File-Name", fileE.files[0].name);
		xhr.send(fileE.files[0]);		
	};
	
	function onHashTag(element) {		
		var taE = document.querySelector("textarea");		
		taE.value += element.innerText;		
	}
	
</script>
</head>
<body leftmargin="0" topmargin="0">	
<div id="content">
<table cellpadding='0' cellspacing='0' width='100%'>
<tr height="38" bgcolor='#215A8C'>
	<td align="left">&nbsp;<img id="btnFind" src="<%=request.getContextPath()%>/img/zoom.png" width="24" height="24"></td>
	<td align="center"><a href="javascript:fnGohome()"><b><font style='color:white; font-family:돋움;'>나의작은미술관</font></b></a></td>
	<td align="right"><img id="btnNew" src="<%=request.getContextPath()%>/img/edit.png" width="24" height="24">&nbsp;</td>
</tr>
</table>
<table><tr height='1'></tr></table>
<form id="msgForm" name="msgForm">	
<table>
<tr>
	<td>
	<div class="tweetbox">
	<table width="100%"><tr>
	<td align="left">ⓣ 	<input type="text" id="userId" name="userId" style="border:1;width:130px"  placeholder="수신자/미술관" ></td>
	<td align="right">
	ⓕ<input type="text" id="ouserId" name="writerId" style="border:0;width:80px" readonly="readonly">
	</td>		
	</tr></table>
	</div></td>		
</tr>
<tr>
	<td valign="top">
	<div class="tweetbox">				
	<textarea id="txtid" class="tweet_input" name="msgCn" rows="4" cols="44" placeholder="글 등록하기 (Tip. #해쉬태그 $상품명 검색어)" maxlength="1000"></textarea></div>	
	</td>		
</tr>
<tr>
	<td>
	<div id="divFile"></div>
	</td>
</tr>
</table>		
</form>	
<table>
<tr>
	<td align="left" height='10'>	
	<table><tr><td><button id="btnSave">글저장</button></td><td><div id="log"></div></td></tr></table>		
	</td>
</tr>
<tr>
	<td align="left">	
	<table><tr><td align='center' valign='center' class='td_info'>
	<input type="file" id="fileId" multiple="multiple"><img align='center' id="btnUpload" src="<%=request.getContextPath()%>/img/add.png">
	</td></tr></table>
	<div id="imgDiv"></div>		
	</td>
</tr>
</table>		
</div>
</body>
</html>