<%@page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<style>
</style>
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
		var targetMuseumId = getParam("targetMuseumId");
		if(targetMuseumId.length > 0) {
			document.getElementById("txtid").value = targetMuseumId + " ";
			setParam("targetMuseumId","");
		}
	}
	function fnMsgSave(){

		var txtidE = document.getElementById("txtid");
		if(txtidE.value.length < 1) {
			alert("글내용을 작성해 주세요");
			return false;
		};

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
		// DEBUG
		//xhr1.open("POST", "<%=request.getContextPath()%>/mm/1/msg/makemsg.json");
		//xhr1.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		// xhr1.send(data);	

		$.ajax({
						type: 'POST',
						url:  'https://hooks.slack.com/services/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',
						data: '{"text":"New Story is touched on your museum"}',
						success: function(){
						},
						error: function(xhr,status,e){       //에러 발생시 처리함수
						},
						dataType: 'json'
		});
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
		if(fileE.files.length < 1) {
			alert("이미지를 먼저 선택해 주세요");
			imgE.innerHTML = "";
			return false;
		};



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
				          "<td align=left valign='top'  class='td_info'>" + obj.imgNm + " - <input type='text' name='imgId' value='" + obj.imgId + "' width='100' style='border:0;width:160px; font-size:10px;' readonly='readonly'></td>" +
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

				fileE.value = "";
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

		//
		//	Slicing a file
/* 		var blob = fileE.files[0];
		const BYTES_PER_CHUNK = 100000; // 300KB
		const SIZE = blob.size;

		var start = 0;
		var end = BYTES_PER_CHUNK;

		while(start < SIZE){
			if('mozSlice' in blob){
				var chunk = blob.mozSlice(start, end);
			} else {
				var chunk = blob.webkitSlice(start, end);
			}

			xhr.send(chunk);
			start = end;
			end = start + BYTES_PER_CHUNK;
		}; */



	};

	function onHashTag(element) {
		var taE = document.querySelector("textarea");
		taE.value += element.innerText;
	}

</script>
</head>
<body leftmargin="0" topmargin="0">
<div id="content" style="border: 0px solid #ccc;" >
<table border=0 cellpadding='0' cellspacing='0' width='100%'>
<tr bgcolor='#215A8C' class="tophead">
	<td colspan='2' align="left" valign='center'>&nbsp;<a href="javascript:fnGohome()"><img id="btnFind" src="<%=request.getContextPath()%>/img/mymuseum.png" width="110"></a></td>
	<!-- td align="left">&nbsp;<img id="btnFind" src="<%=request.getContextPath()%>/img/zoom.png" width="24" height="24"></td -->
	<!--  td align="center"><a href="javascript:fnGohome()"><b><font style='color:white; font-family:돋움;'>나의작은미술관</font></b></a></td>
	<td align="right"><img id="btnNew" src="<%=request.getContextPath()%>/img/edit.png" width="24" height="24">&nbsp;</td -->
</tr>
</table>
<form id="msgForm" name="msgForm">
<input type="hidden" id="ouserId" name="writerId">
<table width=100% border=0 style="padding:0px;" bgcolor=#eeeeee>
<tr>
	<td valign="top">
	<div class="tweetbox" style="border: 0px solid red; width: 96%;" align=center>
	<textarea id="txtid" class="tweet_input" rows="10"
	name="msgCn" placeholder="글 등록하기 (Tip. @수신자ID #해쉬태그 $상품명 검색어)" maxlength="3500"></textarea></div>
	</td>
</tr>
<tr>
	<td>
	<div id="divFile"></div>
	</td>
</tr>
</table>
</form>
<table width='100%' cellpadding='0' cellspacing='0'>
<tr>
	<td align="left" height='10'>
	<table><tr height='10'><td><button id="btnSave">글저장</button></td><td><div id="log"></div></td></tr></table>
	</td>
</tr>
<tr>
	<td align="left"  height='10'>
	<table width='100%'  cellpadding='0' cellspacing='0'><tr><td align='left' valign='center' class='td_info'>
	<input type="file" id="fileId" multiple="multiple"></td><td align='right'><img align='center' id="btnUpload" src="<%=request.getContextPath()%>/img/add.png">&nbsp;&nbsp;
	</td></tr></table>
	<div id="imgDiv"></div>
	</td>
</tr>
</table>
</div>
</body>
</html>
