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
		
		fnMyFollowerList();
	};
	function fnMyFollowerList(){
		
		innerlog("log","<img src='<%=request.getContextPath()%>/img/spinner-small.gif'>");
		
	
		var userId = window.localStorage.getItem("userId");
		innerlog("<img src='<%=request.getContextPath()%>/img/loading.gif' width='15'>");
		var uri = "<%=request.getContextPath()%>/mm/1/user/" + userId + "/follower.json";
		
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4 && xhr.status == 200){
				innerlog("log","");
				var objOrigin = JSON.parse(xhr.responseText); 
				
				var obj = objOrigin;

				
				var imgContent;
				for(var i=0; i < obj.length; i++) {
					if(obj[i].userImg == null || obj[i].userImg.length < 1) {
						obj[i].userImg = "<%=request.getContextPath()%>/img/noimg.png";
					} else {
						obj[i].userImg = "http://" + obj[i].userImg;
					}					
		        	imgContent = "<table>" +
                    "<tr height='60' class='td_white'><td width='30' align='top'  class='td_list'>" +
                    "<table><tr><td align=center valign='top' class='td_img1' width='50' height='50'>" +
                    "<img align='center' src=\'" + obj[i].userImg + "\' width='50'>" +
                    "</td></tr></table>" + 
                    "</td><td width='100%' valign='top' class='td_list' >" +
                    "<table width='100%'><tr><td valign='top' align=left class='td_info'>" + 
                    obj[i].userId + "<br>" + obj[i].userNm + "<br>등록글 : " + obj[i].msgCnt + "</td><td align='right' valign='top' class='td_info'>" + 
                    fnDay(obj[i].followDt) + "</td></tr></table>" +                        
                    "</td></tr><tr><td class='td_white' colspan='2'></tr></table>"; 
                    
		        	$(imgContent).appendTo("#imgDiv");			        			                                  
				}
				
			};
		};			
		xhr.open("POST", uri);		
		xhr.send(null); 
		
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
</div>
</body>
</html>