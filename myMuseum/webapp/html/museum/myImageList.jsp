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
		innerlog("<img src='<%=request.getContextPath()%>/img/loading.gif' width='15'>");
		var uri = "<%=request.getContextPath()%>/mm/1/msg/" + userId + "/" + pageIndex + "/MyImages.json";
		
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
			
				var imgContent;
				for(var i=0; i < obj.length; i++) {
		        	imgContent = "<table width='100%'><tr><td align=center valign='top' class='td_img'>" +
		            		     "<img src='http://" + obj[i].imgUrl + "' width='100%'>" +
		                         "</td></tr></table>" + 
		                         "<table width='100%' bgcolor='#eeeeee' cellpadding='4' cellspacing='0'>" +
		                         "<tr height='40' class='top_space'><td align='left'>" +
		                         obj[i].userInfo + "&nbsp;view(" + obj[i].viewCnt + ")</td><td align='right'>" + fnDay(obj[i].regDt) + "</td></tr></table>" +
		                         "<table><tr height='2'><td></td></tr></table>"; 
		        	$(imgContent).appendTo("#imgDiv");			        			                                  
				}
				
			};
		};			
		xhr.open(mthd, uri);		
		xhr.send(null); 
		
	};
	function fnViewMessage(msgId, userId){
				
		
		setParam("userId", userId);
		setParam("msgId", msgId);
		//top.main.location = "./viewMuseum.jsp";
		parent.window.location = "./viewMuseum.jsp";
		
	};
</script>
</head>
<body leftmargin='0' topmargin='0'>
<div id="content">
<table width='100%' cellpadding='5'>
<tr height='10'><td></td></tr>
<tr>
	<td align='center' height='10' valign='top' bgcolor=white><div id='imgDiv'></div></td>
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