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
    <h1>Foglalások</h1>
    <br>

    <div class="container">
        <table class="table">
            <thead class="thead-dark">
            <tr>
                <th scope="col">Film cím</th>
                <th scope="col">Dátum</th>
                <th scope="col">Foglalt helyek</th>
                <th scope="col">Összeg (Forint)</th>
                <th scope="col">Módosítás</th>
                <th scope="col">Törlés</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="reserve" items="${requestScope.userReservations}">
                <tr>
                    <td>${reserve.movieName}</td>
                    <td>${reserve.playtimeDate}</td>
                    <td>${reserve.reservedSeat}</td>
                    <td>${reserve.priceSum}</td>
                    <td><a href="/editres?ptid=${reserve.playtimeId}"> Módosít </a></td>
                    <td><a href="/deleteres?ptid=${reserve.playtimeId}"> Törlés </a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>

</body>
</html>
