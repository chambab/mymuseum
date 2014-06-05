<%@page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta content='width=320; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;' name='viewport' />
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<title>MyMuseum</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript">
	window.onload = function(){	
		document.getElementById("pageIndex").value = "";		
		
		document.getElementById("load1").onclick = function(){
			fnloadClick("POST");
		};
		fnloadClick("POST");
	};
	function fnloadClick(mthd){
		
		innerlog("log","<img src='<%=request.getContextPath()%>/img/spinner-small.gif'>");
		
		var pageIndexE = document.getElementById("pageIndex");
		var pageIndex = pageIndexE.value;
		
		if(pageIndex.length < 1) pageIndex = 1;
		
		var userId = window.localStorage.getItem("userId");
		
		var uri = "<%=request.getContextPath()%>/mm/2/msg/" + userId + "/" + pageIndex + "/FollowMuseumMsg.json";
		
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4 && xhr.status == 200){
				innerlog("log","");
				var objOrigin = JSON.parse(xhr.responseText); 
				//
				//	page 처리
				if(objOrigin.maxPage >= objOrigin.nextPage) {
					pageIndexE.value = objOrigin.nextPage;
				} else {
					document.getElementById("load1").style.display = "none";
					//pageIndexE.style.display = "none";
				};
				
				var obj = objOrigin.objects;
				var imgCntV = "";
				var viewCntV = "";
				var rplCntV = "";
				//if(mthd == "POST") {
				//	document.getElementById("msg").innerHTML = "";
				//};
				for(var i=0; i < obj.length; i++) {
					if(obj[i].userImg == null || obj[i].userImg.length < 1) {
						obj[i].userImg = "<%=request.getContextPath()%>/img/noimg.png";
					} else {
						obj[i].userImg = "http://" + obj[i].userImg;
					}
					//
					//	이미지 갯수
					imgCntV = obj[i].imgCnt;
					if(imgCntV > 0){
						imgCntV = "<br><img src='/img/photo.png' width='12'> " +  obj[i].imgCnt;
					} else {
						imgCntV = "";
					};
					//
					//	View Cnt
					viewCntV = obj[i].viewCnt;
					if(viewCntV > 0){
						viewCntV = "view(" + obj[i].viewCnt + ")";
					} else {
						viewCntV = "";
					};
					rplCntV = obj[i].rplCnt;
					if(rplCntV > 0){
						rplCntV = "re(" + obj[i].rplCnt + ")";
					} else {
						rplCntV = "";
					};					
					
					obj[i].msgCn = obj[i].msgCn.replace(/\n/g, "<br>");
					
					document.getElementById("msg").innerHTML += 
						"<table>" +
                        "<tr height='60' class='td_white'><td width='30' align='top'  class='td_list'>" +
                        "<table><tr><td align=center valign=center class='td_img1' width='50' height='50'>" +
                        "<img align='center' src=\'" + obj[i].userImg + "\' width='50'>" +
                        "</td></tr></table>" + 
                        "</td><td width='100%' valign='top' class='td_list'  onclick='javascript:fnViewMessage(\"" + 
                		       obj[i].msgId + "\",\"" + obj[i].userId + "\")'>" +
                        "<table width='100%'><tr><td align=left class='td_info'>" + obj[i].userId + "</td><td align='right' class='td_info'>" + fnDay(obj[i].regDt) + "</td></tr></table>" +                        
                        obj[i].msgCn + imgCntV + " " + viewCntV + " " + rplCntV + "</td></tr><tr><td class='td_white' colspan='2'>" +                        
                        "</td></tr></table>";                       
				}
				
			};
		};			
		xhr.open(mthd, uri);		
		xhr.send(null); 
		
	};
	function fnViewMessage(msgId, userId){
		
				setParam("userId", userId);
				setParam("msgId", msgId);
				//top.main.location = "./viewFollowMuseum.jsp";
				//top.main.location = "./viewMuseum.jsp";
				parent.window.location = "./viewMuseum.jsp";
				
			};	
</script>
</head>
<body leftmargin='0' topmargin='0'>
<div id="content">

	<div id="msg"></div><br>
	<div align="center">
	<table>	
	<tr><td align='center'><div id="log"></div></td></tr>
	<tr><td class='td_info'>
	더보기 <input type='text' id='pageIndex' style='border:0;width:20px'>
	<img align='center' valign='center' id="load1" src="<%=request.getContextPath()%>/img/refresh.png">
	</td>
	</table>
	</div>
</div>
</body>
</html>