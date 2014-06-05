<%@page contentType="text/html; charset=utf-8" %>
<html>
<head>
<title>MY MUSEUM </title>
<meta content='width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;' name='viewport' />
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
</head>
<body>
<table width=100% cellpadding=1 bgcolor="eeeeee">
<tr>
	<td width=5% bgcolor=white>분류</td>
	<td width=10% bgcolor=white>Resource</td>
	<td width=15% bgcolor=white>Description</td>
	<td width=5% bgcolor=white>Methods</td>
	<td width=10% bgcolor=white>URI</td>
	<td width=5% bgcolor=white align=center>Format</td>
	<td width=5% bgcolor=white align=center>Auth</td>
	<td width=10% bgcolor=white>Required</td>
	<td width=10% bgcolor=white>Optional</td>
</tr>
	<td width=5% bgcolor=white>user</td>
	<td width=10% bgcolor=white>User</td>
	<td width=15% bgcolor=white>사용자정보조회</td>
	<td width=5% bgcolor=white>GET</td>
	<td width=10% bgcolor=white>
		<a href="<%=request.getContextPath()%>/mm/1/user/user?userId=chambab" method="GET">1/user/user</a></td>
	<td width=5% bgcolor=white align=center>XML</td>
	<td width=5% bgcolor=white align=center>N</td>
	<td width=10% bgcolor=white>userId</td>
	<td width=10% bgcolor=white></td>
</tr>
</tr>
	<td width=5% bgcolor=white>user</td>
	<td width=10% bgcolor=white>Collection<User></td>
	<td width=15% bgcolor=white>Followers 목록조회</td>
	<td width=5% bgcolor=white>GET</td>
	<td width=10% bgcolor=white>
		<a href="<%=request.getContextPath()%>/mm/1/user/followers?userId=chambab" method="GET">1/user/followers</a></td>
	<td width=5% bgcolor=white align=center>XML</td>
	<td width=5% bgcolor=white align=center>N</td>
	<td width=10% bgcolor=white>userId</td>
	<td width=10% bgcolor=white></td>
</tr>
</table>
</body>
</html>

<script>
function openUrl(var strUrl) {
	this.
}
</script>



