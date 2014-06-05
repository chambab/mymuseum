<%@page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta content='width=320; height=device-height; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;' name='viewport' />
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<title>mmm!</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>

<script type="text/javascript">
	window.onload = function(){	
		
		innerlog("museDiv","<img src='<%=request.getContextPath()%>/img/spinner-small.gif'>");
		
		if(window.localStorage.getItem("userId")) {
			document.getElementById("aLogin").innerHTML = window.localStorage.getItem("userId");
		}
		document.getElementById("btnSearch").onclick = function(){
			var query = document.getElementById("inpSearch").value;
			fnSearch(query);
		};	
		
		document.getElementById("queryMuseum").oninput = function(){
			
			var queryMuseum = document.getElementById("queryMuseum").value;			

			if(queryMuseum.length > 1) {
				innerlog("museDiv","<img src='<%=request.getContextPath()%>/img/spinner-small.gif'>");
				var strUri = '<%=request.getContextPath()%>/mm/1/museum/museumReallist.json';

		 		$.ajax({
					type: 'POST',
					url:  strUri,									
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
		 
		
		fnSelectMuseum();
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
		var museumContent = "<table width='100%'><tr><td height='1' width='50%'  class='td_info_main'>";
		var myContent = "<table width='100%'><tr><td height='1' width='50%' class='td_info_main'>";
		
		for(var i=0; i < data.length; i++) {
			
			if(data[i].seCode == '002') {							
				museumContent += "ⓜ <a href='javascript:fnSearch(\"" + data[i].userId + "\")'>" + 
				                 data[i].userId + "</a>(" + data[i].mcnt + ")&nbsp;" + 
				                 "</td><td height='23' class='td_info_main'>" + data[i].userNm + "</td></tr><tr><td height='23' class='td_info_main'>";
			} else {
				myContent += "ⓜ <a href='javascript:fnSearch(\"" + data[i].userId + "\")'>" + 
                				 data[i].userId + "</a>(" + data[i].mcnt + ")&nbsp;" + 
				              "</td><td height='23' class='td_info_main'>" + data[i].userNm + "</td></tr><tr><td height='23' class='td_info_main'>";				
			}
				
		}
		//$(museumContent).appendTo("#museDiv");
		museumContent += "</td></tr></table>";
		museuE.innerHTML = museumContent;
		myE.innerHTML = myContent;
	};
	function fnSearch(query){		
		setParam("query", query);
		location = "./mySearchList.jsp";		
	};	
</script>
</head>
<body leftmargin='0' topmargin='0'>
<div id="content">
<table width='100%'  background='/img/pattern2.jpg' cellpadding='0' cellspacing='5'>
<tr><td bgcolor='white'>
<table width='100%'>
<tr>
	<!-- td align="center">나의작은 미술관 <font style='font-family: verdana; font-size:11pt'>MYMUSEUM</font></td -->
	<td align='center' valign='center'>
	<table width='100%'>
	<tr>
	<td align='left'><img src='<%=request.getContextPath()%>/img/mymuseum100.png' width='130'></td>
	<td align='right'><a id="aLogin" href="<%=request.getContextPath()%>/html/user/login.jsp">로그인</a> | <a href="<%=request.getContextPath()%>/indexm.html">홈으로</a></td>
	</tr>
	</table>
	</td>
</tr>
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
<tr height='50'><td></td></tr>
</table>
<table width='100%'> 
<tr>
	<td class="top_space">&nbsp;ⓜ&nbsp;<input type='text' name='queryMuseum' id='queryMuseum' style='border:0;width:200px' placeholder='실시간미술관검색 (Click)'></td>
</tr>
<tr>
	<td>
	<div id='MyMuseDiv'></div>
	</td>
</tr>
</table>
<table width='100%'>
<tr>
	<td class="top_space"></td>
</tr>
<tr>
	<td>
	<div id='museDiv'></div>
	</td>
</tr>
</table>
</td></tr></table>
</div>
</body>
</html>
