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
    <div class="card flex-row flex-wrap">
        <div class="card-header border-0">
            <img src="data:image/jpg;base64,${requestScope.movie.coverImage}" alt="">
        </div>
        <div class="card-block px-2">
            <h4 class="card-title"><c:out value="${requestScope.movie.title}"/></h4>
            <p class="card-text">
                Hossz: <c:out value="${requestScope.movie.lengthMin}"/>
            </p>
            <p class="card-text">
                Korhatár: <c:out value="${requestScope.movie.ageLimit}"/>
            </p>
            <p class="card-text">
                Rendező: <c:out value="${requestScope.movie.director}"/>
            </p>
            <p class="card-text">
                Színészek: <c:out value="${requestScope.movie.actors}"/>
            </p>
            <p class="card-text">
                Leírás: <c:out value="${requestScope.movie.description}"/>
            </p>
        </div>
    </div>
    <br>
</div>

<div class="container">


</div>
<div class="container">
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
                <td><a href="/reservation?ptid=${playtime.id}" class="btn btn-primary"> Foglal </a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


</body>
</html>
