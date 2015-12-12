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

<title>MyMuseum</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css">
<script type="text/javascript">
	window.onload = function(){	
		
		msgId = getParam("msgId");		
		
		var strUri = '<%=request.getContextPath()%>/mm/1/msg/' + msgId + '/message.json';
 		$.ajax({
			type: 'GET',
			url:  strUri,									
			success: fnGetMessageSuccess,
			error: function(xhr,status,e){       //에러 발생시 처리함수
				alert('Error');
			},
			dataType: 'json'
		}); 
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
 		
 		document.getElementById("btnRpl").onclick = function(){
 			fnReplyMessage();
 		};
 			
	};
	//
	//	URL 변경
	function replaceURLWithHTMLLinks(text) {
	    var exp = /(\b(https?|ftp|file):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/i;
	    return text.replace(exp,"<a href='$1'>$1</a>"); 
	}	
	//
	//	해당 메시지를 조회함
	function fnGetMessageSuccess(data, status) {	
				
		if(data.userImg == null || data.userImg.length < 1) {
			data.userImg = "<%=request.getContextPath()%>/img/noimg.png";
		} else {
			data.userImg = "http://" + data.userImg;
		}
		
		data.msgCn = replaceURLWithHTMLLinks(data.msgCn);
		
		data.msgCn = data.msgCn.replace(/\n/g, "<br>");
		
		document.getElementById("msgCn").innerHTML =  
	    "<div class='top_space'><table width='100%'><tr><td align=center valign='top' class='td_img' width='50' height='50'>" +
        "<img src=\'" + data.userImg + "\' width='50'>" +
        "</td><td width='2'></td><td align=left class='td_info'>" +
        "<img src='/img/info.png' width='24' height='24'><br>" + 
        data.userInfo + "<br>" + fnDay(data.regDt) + "</td></tr><tr height='3'><td></td></tr></table>" +
		"<table width='100%'>" +
        "<tr height='60'>" +
        "<td width='100%' valign='top'>" +
        /*
        "<table width='100%'><tr><td align=left class='td_info'>" + 
        data.userInfo + "</td><td align='right' class='td_info'>" + 
        data.regDt + "</td></tr></table>"  */                        
        data.msgCn + "</td></tr></table></div>";	
        
        // 사용자여부에 따라서 버튼 수정
        var subUserId = localStorage.getItem("userId");
        setParam("msgUserId", data.userId);
        //fnIsFollow(data.userId, subUserId);
        
        var imgContent;
        for(var i=0; i < data.imageList.length; i++){
        	//imgContent = "<img src='http://" + data.imageList[i].imgUrl + "' width='300'>";
        	imgContent = "<table width='100%'><tr><td align=center valign='top' class='td_img'>" +
            		     "<img src='http://" + data.imageList[i].imgUrl + "' width='100%'>" +
                         "</td></tr></table>"; 
        	$(imgContent).appendTo("#imgDiv");
        };
               
               
        
        if(subUserId == data.userId){
			document.getElementById("menu1").style.display = "none";
			document.getElementById("menu2").style.display = "block";        	
        }else{
        	if(subUserId.length > 0) {
        		fnIsFollow(data.userId, subUserId);
        	}
			document.getElementById("menu1").style.display = "block";
			document.getElementById("menu2").style.display = "none";	        	
        };
        
        //
        //	Reply
        var rplContent = "";
        var dataR;
        for(var i=0; i < data.replyList.length; i++){
        	dataR = data.replyList[i];
			if(dataR.userImg == null || dataR.userImg.length < 1) {
				dataR.userImg = "<%=request.getContextPath()%>/img/noimg.png";
			} else {
				dataR.userImg = "http://" + dataR.userImg;
			}	
			dataR.rplMsgCn = dataR.rplMsgCn.replace(/\n/g, "<br>");
        	rplContent = "<table width='100%'><tr><td align='center' width='30' class='td_list'>" +
                         "<table><tr><td align=center valign='top' class='td_img' width='40'>" +
                         "<img src=\'" + dataR.userImg + "\' width='40'></td></tr></table></td>" +
                         "<td align=left valign='top' class='td_list'>" +
                         "<table width='100%'><tr><td align=left class='td_info'>@" +                          
                         dataR.userId + "</td><td align='right' class='td_info'>" + 
                         fnDay(dataR.regDt) + "</td></tr></table>" + 
                         dataR.rplMsgCn + "</td></tr>" + 
                         "<tr><td class='td_white' colspan='2'></td></tr></table>"; 
                         
			$(rplContent).appendTo("#rplDiv");        	
        }
	}
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
	//	Reply 메시지  등록
	function fnReplyMessage(){	
		//alert("진행중입니다 잠시만 기다려주세요");
		//return false;
		
		var userId = window.localStorage.getItem("userId");
		var strUri = '<%=request.getContextPath()%>/mm/1/msg/makereply.json';
		var rplMsgCn = document.getElementById("replyMsg").value;
		document.getElementById("replyMsg").value = "";		
 		$.ajax({
			type: 'POST',
			url:  strUri,									
			success: function(data){				
					
				if(data.userImg == null) {
					data.userImg = "<%=request.getContextPath()%>/img/mysun.jpg";
				} else {
					data.userImg = "http://" + data.userImg;
				}		
				
		        var rplContent =    "<table width='100%'><tr><td align='center' width='30' class='td_list'>" +
					                "<table><tr><td align=center valign='top' class='td_img' width='40'>" +
					                "<img src=\'" + data.userImg + "\' width='40'></td></tr></table></td>" +
					                "<td align=left valign='top' class='td_list'>" +
					                "<table width='100%'><tr><td align=left class='td_info'>@" +                          
					                data.userId + "</td><td align='right' class='td_info'>" + 
					                "</td></tr></table>" + 
					                data.rplMsgCn + "</td></tr>" + 
					                "<tr><td class='td_white' colspan='2'></td></tr></table>"; 
   				$(rplContent).appendTo("#rplDiv");					
			},
			data: "msgId=" + msgId + "&userId=" + userId + "&rplMsgCn=" + rplMsgCn,
			dataType: 'json'
		}); 		
	};
</script>
</head>
<body  bgcolor='white' leftmargin='0' topmargin='4'>
<div id="content" style="border: 1px solid #ccc;" >	
<table cellpadding='0' cellspacing='0' width='100%' sytle="border-radius: 7px;">
<tr height="38"  class="tophead">
	<td align="left">&nbsp;<img id="btnFind" src="<%=request.getContextPath()%>/img/mymuseum.png"  width="110"></td>
	<td align="left"><a href="javascript:fnGohome()"><b><font style='color:white; font-family:돋움;'>나의작은미술관</font></b></a></td>
	<td align="right"><img id="btnNew" src="<%=request.getContextPath()%>/img/edit.png" width="24" height="24">&nbsp;</td>
</tr>
</table>
<table><tr height='1'></tr></table>
<table width='100%'>
<tr>
	<td height='100' valign='top' bgcolor=white><div id='msgCn' name="msgCn"></div></td>
</tr>
</table>
<table width='100%'>
<tr>
	<td align='right' bgcolor=white>
	<div id="menu1" style='display:none'>		
		<button id="bntFollow">Follow</button>
	</div>
	<div id="menu2" style='display:block'>
	<button id="bntEdit">수정</button>
	<button id="bntDelete">삭제</button></div>
	</td>
</tr>
</table>
<table width='100%' cellpadding='5'>
<tr height='1'>
	<td align='center' height='1' valign='top' bgcolor=white><div id='rplDiv'></div></td>
</tr>
</table> 
<table width='100%'>
<tr>
	<td bgcolor='#eeeeee' align='center'>
		<textarea id="replyMsg"  rows="2"
			name="replyMsg" placeholder="리플(클릭)" style='border:0;width:97%;' maxlength="3000"></textarea>
		<!-- input type='text' id='replyMsg' name='replyMsg' style='border:0;width:97%;' placeholder='리플(클릭)' -->
	</td>
</tr>
<tr>
	<td bgcolor='white' align='right'>
		<button id='btnRpl'>리플등록</button>
	</td>
</tr>
</table>
<table width='100%' cellpadding='5'>
<tr height='10'><td></td></tr>
<tr>
	<td align='center' height='10' valign='top' bgcolor=white><div id='imgDiv'></div></td>
</tr>
</table>   
<div id="msg"></div><br>
<div align="center">	
<img id="load1" src="<%=request.getContextPath()%>/img/refresh.png">
</div>	
</div>
<div class='top_gray'>
<table width='100%'>
<tr height='10'><td align='left'>
<img id="btnFind" src="<%=request.getContextPath()%>/img/mymuseum.png" width="100">
</td></tr>
</table>
</div>
</body>
</html>