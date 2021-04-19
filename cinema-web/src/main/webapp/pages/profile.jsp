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
        <h1>Foglalások</h1>
        <table class="table">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Cím</th>
                <th scope="col">Terem</th>
                <th scope="col">Dátum</th>
                <th scope="col">Óra</th>
                <th scope="col">Jegy</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="playtime" items="${requestScope.usersPlaytimes}">
                <tr>
                    <td>${playtime.movie_name}</td>
                    <td>${playtime.room_name}</td>
                    <td>${playtime.playTimeDate}</td>
                    <td>${playtime.playTimeHours}</td>
                    <td><a href="/reservation?ptid=${playtime.id}"> Módosít </a></td>
                    <td><a href="/deleteres?ptid=${playtime.id}"> Törlés </a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>

</body>
</html>
