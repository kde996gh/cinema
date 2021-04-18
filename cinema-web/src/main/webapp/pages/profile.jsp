<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="common/header.jsp"/>
    <title>Adatlap</title>
</head>
<body>
<jsp:include page="common/menu.jsp"/>

<c:if test="${requestScope.message != ''}">
    ${requestScope.message}
</c:if>
<c:if test="${requestScope.message == ''}">
    <div class="container">
    </div>
</c:if>

</body>
</html>
