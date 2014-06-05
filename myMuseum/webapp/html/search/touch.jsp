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
	var bClick;
	var clickPos;
	var bMobile;
	window.onload = function(){			
	};
  	window.onmousedown = function(e){
  		bMobile = false;
		bClick = true;
		clickPos = e;
		setTimeout("fnStartSearch()",2000);	
		//alert("mousedown");
	};  	
 	window.onmouseup = function(e){
		//alert("mouseup");
		bClick = false;
		clickPos = null;
		$('#posDiv').fadeOut('slow');
	}; 
	window.ontouchstart = function(e){
		//alert("touchstart");
		bMobile = true;
		bClick = true;
		//clickPos = e;		
		if(e.touches){
			clickPos = e.touches[0];
			//alert(e.touches[0].clientX);
			//alert("a");
		} else {
			//alert(e.clientX);
			clickPos = e;
		};
		setTimeout("fnStartSearch()",2000);			
		
	};
	window.ontouchend = function(){
		bClick = false;
		clickPos = null;
		$('#posDiv').fadeOut('slow');
	};	
/* 	window.ondrag = function(){
		alert("drag");
	}; */
/* 	window.onmouseover = function(e){
		bClick = true;
		clickPos = e;
		setTimeout("fnStartSearch()",2000);
	}; */
	function fnStartSearch(){
		//alert(bClick);
		//alert(clickPos.clientX);
		if(bClick == true) {			
			//var posDivE = document.getElementById("posDiv");
			//posDivE.innerHTML = "<img src='<%=request.getContextPath()%>/img/spinner-small.gif'>";
			// innerlog("posDiv","<img src='<%=request.getContextPath()%>/img/spinner-small.gif'>");
			//posDivE.style.top = clickPos.y;
			//posDivE.style.left = clickPos.x;			
		
			//alert(posDivE.style.top);
			$('#posDiv').fadeIn('slow');
			if(bMobile == true){
				$("#posDiv").animate({
					marginLeft : clickPos.clientX - 50,
					marginTop : clickPos.clientY - 50
				});				
			} else {
				$("#posDiv").animate({
					marginLeft : clickPos.x - 50,
					marginTop : clickPos.y - 50
				});
			}
			innerlog("posDiv","<img src='<%=request.getContextPath()%>/img/progress.gif' width='100'>");
		}
	}
</script>
</head>
<body>
<div id="posDiv" style="position:relative; left:0; top:0;"></div>
</body>
</html>
