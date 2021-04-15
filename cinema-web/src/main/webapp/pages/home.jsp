<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
  Created by IntelliJ IDEA.
  User: Deniel
  Date: 2021. 04. 15.
  Time: 21:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="common/header.jsp"/>
    <title>Filmek</title>
</head>
<body>
<div class="container">

    <jsp:include page="common/menu.jsp"/>
    <div class="content">

    </div>
</div>
<c:if test="${2>1}">
    <li class="nav-item dropdown ml-auto">
        <a class='nav-link dropdown-toggle' href='#' id='navbarDropdownMenuLink' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>
                ${cookie.username.value}
        </a>
        <div class='dropdown-menu dropdown-menu-right' aria-labelledby='navbarDropdownMenuLink'>
            <a class='dropdown-item' href='../LogoutController'>Kijelentkezés</a>
        </div>
    </li>
</c:if>
</body>
</html>
