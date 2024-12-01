<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%@ page session="false" %>
<%response.setStatus(404);
Logger logger = LoggerFactory.getLogger("404.jsp");

%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>404 - 页面不存在</title>
</head>

<body>
	<h2>404 - 页面不存在.</h2>

</body>
</html>