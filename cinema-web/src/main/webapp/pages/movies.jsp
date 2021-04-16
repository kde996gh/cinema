<%--
  Created by IntelliJ IDEA.
  User: Deniel
  Date: 2021. 04. 15.
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="common/header.jsp"/>
    <title>Filmek</title>
</head>
<body>
<jsp:include page="common/menu.jsp"/>

<div class="container">
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Borítókép</th>
            <th scope="col">Cím</th>
            <th scope="col">Hossz (percben)</th>
            <th scope="col">Korhatár</th>
            <th scope="col">Rendező</th>
            <th scope="col">Szereplők</th>
            <th scope="col">Leíárs</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${requestScope.movies}">
            <tr>
                <td>
                    <a href="/movies?movieName=${item.title}">
                    <img src="data:image/jpg;base64,${item.coverImage}"
                         onclick=""
                         width="100" height="100"/>
                    </a>



                </td>
                <td>${item.title}</td>
                <td>${item.lengthMin}</td>
                <td>${item.ageLimit}</td>
                <td>${item.director}</td>
                <td>${item.actors}</td>
                <td>${item.description}</td>

            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
</body>
</html>
