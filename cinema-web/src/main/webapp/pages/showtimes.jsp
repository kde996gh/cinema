<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <jsp:include page="common/header.jsp"/>
    <title>Műsorlista</title>
</head>
<body>
<jsp:include page="common/menu.jsp"/>
<div class="container">
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Cím</th>
            <th scope="col">Terem</th>
            <th scope="col">Jegy típus</th>
            <th scope="col">Dátum</th>
            <th scope="col">Óra</th>
            <th scope="col">Jegy</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach var="pt" items="${requestScope.playtimes}">
            <tr>
                <td>${pt.movieName}</td>
                <td>${pt.roomName}</td>
                <td>${pt.ticketType}</td>
                <td>${pt.playTimeDate}</td>
                <td>${pt.playTimeHours}</td>
                <td><a href="/reservation?ptid=${pt.id}"> Foglal </a>

            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
<div class="ticketContainer">
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Típus</th>
            <th scope="col">Ár (Forint)</th>
            <th scope="col">Kedvezményes ár (Forint)</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="tickets" items="${requestScope.tickets}">
            <tr>
                <td>${tickets.ticketType}</td>
                <td>${tickets.price}</td>
                <td>${tickets.lowerPrice}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
</body>
</html>
