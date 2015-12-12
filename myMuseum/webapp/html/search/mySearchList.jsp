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
 		//	Follow 추가
 		document.getElementById("bntFollow").onclick = function(){
 			var subUserId = localStorage.getItem("userId");
 			bntE = document.getElementById("bntFollow");
 			if(bntE.innerHTML == "Follow"){
 				fnInsertFollow(getParam("msgUserId"), subUserId);
 			} else {
 				fnDeleteFollow(getParam("msgUserId"), subUserId);
 			};
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
		
        // 사용자여부에 따라서 버튼 수정
        var subUserId = localStorage.getItem("userId");
        //setParam("msgUserId", data.userId);
        
		// follow 기능 추가
/*         if(subUserId == data.userId){
			document.getElementById("menu1").style.display = "none";
			document.getElementById("menu2").style.display = "block";        	
        }else{
        	if(subUserId.length > 0) {
        		fnIsFollow(data.userId, subUserId);
        	}
			document.getElementById("menu1").style.display = "block";
			document.getElementById("menu2").style.display = "none";	        	
        }; */
        
		
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
			obj[i].msgCn = obj[i].msgCn.replace(/\n/g, "<br>");
			
			document.getElementById("msg").innerHTML += 
/* 				"<table border=0>" +
                "<tr height='60' class='td_white' valign=top><td width='30' align='top'  class='td_list'><td>" +
                "<table border=0><tr><td align=center valign=top class='td_img1' width='50' height='50'>" +
                "<img valign='top' align='center' src=\'" + obj[i].userImg + "\' width='50'>" +
                "</td></tr></table></td>" + 
                "<td width='100%' valign='top' class='td_list'  onclick='javascript:fnViewMessage(\"" + 
        		       obj[i].msgId + "\",\"" + obj[i].userId + "\")'>" +
                "<table width='100%' border=0>" +
                "<tr height=35 ><td  align=left class='td_info'>" + obj[i].userInfo + " " + fnDay(obj[i].regDt) + "</td></tr>" +
                "<tr valign=top ><td>" + obj[i].msgCn + "</td></tr>" + 
                "<tr height=35><td align=right>" + imgCntV + " " + viewCntV + " " + rplCntV + "</td></tr>" + 
                "</table><tr></table>"; */
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
	// Follow 여부확인
	function fnIsFollow(mstUserId, subUserId){		
		var strUri = '<%=request.getContextPath()%>/mm/1/rel/' + mstUserId + '/' + subUserId + '/isfollow.json';
 		$.ajax({
			type: 'GET',
			url:  strUri,									
			success: function(data){
				if(data.length > 0) {
					document.getElementById("bntFollow").innerHTML = "Unfollow"; 
				} else {
					document.getElementById("bntFollow").innerHTML = "Follow";
				}					
			},
			error: function(xhr,status,e){       //에러 발생시 처리함수
				alert('Error');
			},
			dataType: 'json'
		}); 		
	};
	// Follow 추가
	function fnInsertFollow(mstUserId, subUserId){		
		var strUri = '<%=request.getContextPath()%>/mm/1/rel/' + mstUserId + '/' + subUserId + '/follow.json';
 		$.ajax({
			type: 'GET',
			url:  strUri,									
			success: function(data){				
					document.getElementById("bntFollow").innerHTML = "Unfollow"; 
			},
			error: function(xhr,status,e){       //에러 발생시 처리함수
				alert('Error');
			},
			dataType: 'json'
		}); 		
	};
	// UnFollow 추가
	function fnDeleteFollow(mstUserId, subUserId){		
		var strUri = '<%=request.getContextPath()%>/mm/1/rel/' + mstUserId + '/' + subUserId + '/unfollow.json';
 		$.ajax({
			type: 'GET',
			url:  strUri,									
			success: function(data){				
					document.getElementById("bntFollow").innerHTML = "Follow"; 
			},
			error: function(xhr,status,e){       //에러 발생시 처리함수
				alert('Error');
			},
			dataType: 'json'
		}); 		
	};	
	function fnViewMessage(msgId, userId){		
				
		setParam("msgId", msgId);
		location = "<%=request.getContextPath()%>/html/touch/viewMuseum.jsp";
		
	};
</script>
</head>
<body leftmargin='0' topmargin='0'>
<div id="content" style="border: 0px solid #ccc;">
<table cellpadding='0' cellspacing='0' width='100%' border="0" sytle="border-radius: 7px;">
<tr height="38" bgcolor="#215A8C" class="tophead">
	<td colspan='2' align="left" valign='center'>&nbsp;<a href="javascript:fnGohome()"><img id="btnFind" src="<%=request.getContextPath()%>/img/mymuseum.png" width="110"></a></td>	
	<!--  td align="right"><img id="btnNew" src="<%=request.getContextPath()%>/img/edit.png" width="24" height="24">&nbsp;</td -->
</tr>
<tr width='100%' height="35" bgcolor='f7f7f7'>	
	<td valign="left" >&nbsp;&nbsp;&nbsp;<font style='color:#4081c2; font-family:verdana,돋움;font-size:14px;'><span id='idDiv'></span></font></td>
	<td align="right" valign="center">
		<!-- table border="1" style="overflow:auto;">
		<tr>
			<td align='right' bgcolor=white>
			<div id="menu1" style='display:none;'>		
				<button id="bntFollow">Follow</button>
			</div>
			</td>
		</tr>
		</table -->
	<button id="btnImg"><font style='font-family:verdana;font-size:14px;'>그림으로보기</font></button>
	<button id="btnNew"><font style='font-family:verdana;font-size:14px;'>글쓰기</font></button>
	</td>		 
</tr>
<tr height='1' bgcolor='#eeeeee' width='100%'>
	<td  colspan='2'></td>
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