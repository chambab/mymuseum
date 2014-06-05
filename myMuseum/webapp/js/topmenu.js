
window.onload = gfnOnload;

/**
 * onload 함수
 */
function gfnOnload(){
	
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