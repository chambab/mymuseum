<%@page contentType="text/html; charset=utf-8" %>
<script type="text/javascript">
	window.onload = function(){
		var userId = window.localStorage.getItem("userId");
		
		document.getElementById("btnNew").onclick = function(){
			parent.main.location = "<%=request.getContextPath()%>/html/touch/touchm.jsp";
		};
		if(userId == null) {			
			document.getElementById("btnJoin").innerHTML = "사용자등록";
			document.getElementById("btnJoin").onclick = function(){
				parent.main.location = "<%=request.getContextPath()%>/html/user/RegUser.jsp";
			};			
		}else {
			document.getElementById("btnJoin").innerHTML = "미술관등록";
			document.getElementById("btnJoin").onclick = function(){
				parent.main.location = "<%=request.getContextPath()%>/html/user/RegMuseum.jsp";
			};	
		};
	
		document.getElementById("btnFind").onclick = function(){
			parent.main.location = "<%=request.getContextPath()%>/html/search/search.jsp";
		};		
		document.getElementById("btnMine").onclick = function(){
			parent.main.location = "<%=request.getContextPath()%>/html/touch/myMuseum.jsp";
		};	
		document.getElementById("btnAll").onclick = function(){			
			parent.main.location = "<%=request.getContextPath()%>/html/touch/myFollowMuseum.jsp";
		};	
		document.getElementById("btnMyInfo").onclick = function(){
			parent.main.location = "<%=request.getContextPath()%>/html/user/myprofile.jsp";
		};		
		document.getElementById("btnMyMuseum").onclick = function(){
			parent.main.location = "<%=request.getContextPath()%>/html/museum/listMuseum.jsp";
		};		
		document.getElementById("btnMain").onclick = function(){							
			document.getElementById("menu1").style.display = "block";
			document.getElementById("menu2").style.display = "none";				
		};	
		document.getElementById("btnProfile").onclick = function(){	
			document.getElementById("menu1").style.display = "none";
			document.getElementById("menu2").style.display = "block";

		};		
		parent.main.location = "<%=request.getContextPath()%>/html/touch/myFollowMuseum.jsp";
		

	};

</script>

<table cellpadding='0' cellspacing='0' width='100%'>
<tr height="38" bgcolor='#215A8C'>
	<td align="left">&nbsp;<img id="btnFind" src="<%=request.getContextPath()%>/img/zoom.png" width="24" height="24"></td>
	<td align="center"><a href="<%=request.getContextPath()%>/index.jsp" target="top"><b><font style='color:white; font-family:돋움;'>나의작은미술관</font></b></a></td>
	<td align="right"><img id="btnNew" src="<%=request.getContextPath()%>/img/edit.png" width="24" height="24">&nbsp;</td>
</tr>
<tr width='100%' height="39" bgcolor='f7f7f7'>		
	<td colspan='3' valign="center" >
	<div id="menu1" style='display:block'>
	<button id="btnAll">전체글</button>
	<button id="btnMine">나의글</button>
	<button id="btnDM">쪽지</button>	
	<button id="btnMyMuseum">나의미술관</button>	
	<!-- button id="btnEnd">종료글</button -->	
	<img align="center" id="btnProfile" src="<%=request.getContextPath()%>/img/next.png" width="24" height="24"></div>
	<div id="menu2" style='display:none'>
	<button id="btnMyInfo">나의정보</button>		
	<button id="btnJoin">미술관생성</button>	
	<img align="center" id="btnMain" src="<%=request.getContextPath()%>/img/next.png" width="24" height="24"></div>	
	</td>		 
</tr>
<tr height='1' bgcolor='#eeeeee' width='100%'>
	<td  colspan='3'></td>
</tr>
</table>