<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="common/header.jsp"/>
    <title>Film</title>
</head>
<body>
<jsp:include page="common/menu.jsp"/>

<div class="container">

    <p>
        <c:out value="${requestScope.movie.title}"/>
    </p>

    <img src="data:image/jpg;base64,${requestScope.movie.coverImage}"
         onclick=""
         width="100" height="100" alt="cover"/>
    <p>
        Hossz: <c:out value="${requestScope.movie.lengthMin}"/>
    </p>
    <p>
        Hossz: <c:out value="${requestScope.movie.ageLimit}"/>
    </p>
    <p>
        Hossz: <c:out value="${requestScope.movie.director}"/>
    </p>
    <p>
        Hossz: <c:out value="${requestScope.movie.actors}"/>
    </p>
    <p>
        Hossz: <c:out value="${requestScope.movie.description}"/>
    </p>
</div>
<div id="playtimes">
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
        <c:forEach var="playtime" items="${requestScope.playTimes}">
            <tr>
                <td>${playtime.movieName}</td>
                <td>${playtime.roomName}</td>
                <td>${playtime.playTimeDate}</td>
                <td>${playtime.playTimeHours}</td>
                <td><a href="/reservation?ptid=${playtime.id}"> Foglal </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


</body>
</html>
