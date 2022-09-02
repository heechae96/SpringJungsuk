<!-- isErrorPage="true" : 이 페이지는 에러가 발생했을때 보여주는 페이지 -->
<%-- <%@ page contentType="text/html;charset=utf-8" isErrorPage="true"%> --%>
<!-- jsp특징때문에 상태코드가 500으로 변한것.  isErrorPage="false"로 하면 400으로 나온다 -->
<%@ page contentType="text/html;charset=utf-8" isErrorPage="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>error400.jsp</title>
</head>
<body>
<h1>예외가 발생했습니다.</h1>
<%-- 발생한 예외 : ${ex}<br> --%>
발생한 예외 : ${pageContext.exception}<br>
<%-- 예외 메시지 : ${ex.message}<br> --%>
예외 메시지 : ${pageContext.exception.message}<br>
<ol>
<c:forEach items="${ex.stackTrace}" var="i">
	<li>${i.toString()}</li>
</c:forEach>
</ol>
</body>
</html>