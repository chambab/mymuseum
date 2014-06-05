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
		
		innerlog("log","<img src='<%=request.getContextPath()%>/img/spinner-small.gif'>");	
		var pageIndexE = document.getElementById("pageIndex");
		var pageIndex = pageIndexE.value;		
		if(pageIndex.length < 1) pageIndex = 1;
		
		//var query = encodeURI(getParam("query"));
		var query = getParam("query");	
		document.getElementById("idDiv").innerHTML = "ⓜ" + query;
		
		var strUri = '<%=request.getContextPath()%>/mm/1/search/search.json';
 		$.ajax({
			type: 'POST',
			url:  strUri,									
			success: fnloadClick,
			error: function(xhr,status,e){       //에러 발생시 처리함수
				alert('Error');
			},
			data: "query=" + query + "&pageIndex=" + pageIndex,
			dataType: 'json'
		}); 
 		
		document.getElementById("load1").onclick = function(){
			fnloadClick("POST");
		};		
		document.getElementById("btnNew").onclick = function(){
			setParam("targetMuseumId", "@"+getParam("query"));			
			parent.window.location = "<%=request.getContextPath()%>/html/touch/touchm.jsp";
		};
		document.getElementById("btnImg").onclick = function(){
			setParam("query", getParam("query"));
			location = "<%=request.getContextPath()%>/html/museum/myMuseumImageList.jsp";
		};		
	};
	function fnloadClick(obj){
		
		innerlog("log","");
		var pageIndexE = document.getElementById("pageIndex");
		var objOrigin = obj;
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
			
			//	REply Cnt
			rplCntV = obj[i].rplCnt;
			if(rplCntV > 0){
				rplCntV = "re(" + obj[i].rplCnt + ")";
			} else {
				rplCntV = "";
			};			
			document.getElementById("msg").innerHTML += 
				"<table>" +
                "<tr height='60' class='td_white'><td width='30' align='top'  class='td_list'>" +
                "<table><tr><td align=center valign=center class='td_img1' width='50' height='50'>" +
                "<img align='center' src=\'" + obj[i].userImg + "\' width='50'>" +
                "</td></tr></table>" + 
                "</td><td width='100%' valign='top' class='td_list'  onclick='javascript:fnViewMessage(\"" + 
        		       obj[i].msgId + "\",\"" + obj[i].userId + "\")'>" +
                "<table width='100%'><tr><td align=left class='td_info'>" + obj[i].userInfo + "</td><td align='right' class='td_info'>" + fnDay(obj[i].regDt) + "</td></tr></table>" +                        
                obj[i].msgCn + imgCntV + " " + viewCntV + " " + rplCntV +  "</td></tr><tr><td class='td_white' colspan='2'>" +                        
                "</td></tr></table>";                       
		}

	};
	function fnViewMessage(msgId, userId){		
				
		setParam("msgId", msgId);
		location = "<%=request.getContextPath()%>/html/touch/viewMuseum.jsp";
		
	};
</script>
</head>
<body leftmargin='0' topmargin='0'>
<div id="content">
<table cellpadding='0' cellspacing='0' width='100%'>
<tr height="38" bgcolor='#215A8C'>
	<td align="left" valign='center'>&nbsp;<a href="javascript:fnGohome()"><img id="btnFind" src="<%=request.getContextPath()%>/img/mymuseum.png" width="110"></a></td>	
	<td align="right"><img id="btnNew" src="<%=request.getContextPath()%>/img/edit.png" width="24" height="24">&nbsp;</td>
</tr>
<tr width='100%' height="35" bgcolor='f7f7f7'>		
	<td valign="left" >&nbsp;&nbsp;&nbsp;<font style='color:#4081c2; font-family:돋움;font-size:15px;'><span id='idDiv'></span></font></td>
	<td align='right'><button id="btnImg">사진보기 </button></td>		 
</tr>
<tr height='1' bgcolor='#eeeeee' width='100%'>
	<td  colspan='3'></td>
</tr>
</table>
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