<!DOCTYPE html> 
<html>
<head> 
<%@page import="java.util.*" %>
<meta charset="UTF-8">
<title>dssssssssssss</title>
</head>
<body>
3333333
<input id="name" type="text" value=${name} />
<input id="name" type="text" value=${name2} />
<input id="name" type="text" value=${name3} />
<input id="name" type="text" value=${name4} />

${userlist}
    
	<h1>LOGIN</h1>
	<form action="/jf/thairice/t3user/doLogin" method="post">
<!-- 	<p>userId:<input type="text" name="account"></p>
	<p>pass:<input type="password" name="password"></p> -->
	<p><input type="submit" value="login"></p>
	</form>
</body>
</html>