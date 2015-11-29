<%@page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery2/jquery.query-2.1.7.js"></script>
<body>
TEST : <input type="date" name="birthdate">

<div id="target">
	Click Here
</div>

<script>
	$("#target").click(function() {
		
		alert("Test");
	});
</script>
</body>
</html>