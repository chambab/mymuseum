<%@page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<title>Search</title>
<head>
<meta charset="UTF-8">
<meta content='width=320; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;' name='viewport' />
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<title>Search</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/common.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>

<script type="text/javascript">
	window.onload = function(){		
		document.getElementById("btnSearch").onclick = function(){
			var query = document.getElementById("inpSearch").value;
			fnSearch(query);
		};		
	};
	function fnSearch(query){		
		setParam("query", query);
		top.main.location = "./mySearchList.jsp";		
	};
</script>
</head>
<body>
<div id="content">
<table>
<tr>
	<td align="center">
		<input type="text" name="search" id="inpSearch" placeholder="Search What?" autocomplete="on">
		<button id="btnSearch">Search</button>		
	</td>
</tr>
</table>
</div>
</body>
</html>
