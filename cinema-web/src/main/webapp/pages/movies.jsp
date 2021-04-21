<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="common/header.jsp"/>
    <title>Filmek</title>
</head>
<body>
<jsp:include page="common/menu.jsp"/>
<form action="/movies" method="post">
    <div class="form-group form-check">
        <input type="text" class="form-control" id="searchInput" name="searchInput" placeholder="Keresés">
    </div>
    <button type="submit" class="btn btn-primary">Keresés</button>
</form>

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
            <th scope="col">Leírás</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${requestScope.movies}">
            <tr>
                <td>
                    <a href="/movie?movid=${item.id}">
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
