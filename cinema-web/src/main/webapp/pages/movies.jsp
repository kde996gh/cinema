<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="common/header.jsp"/>
    <title>Filmek</title>
</head>
<body>
<jsp:include page="common/menu.jsp"/>
<h1 class="container container2">Filmek</h1>

<div class="container container2">
    <form action="/movies" method="post">
        <div class="form-group form-check">
            <input type="text" class="form-control" id="searchInput" name="searchInput" placeholder="Keresés">
        </div>
        <button type="submit" class="btn btn-primary">Keresés</button>
    </form>
</div>

<div class="container container2">
    <c:forEach var="item" items="${requestScope.movies}">
        <div class="card" style="width: 18rem;">
            <a href="/movie?movid=${item.id}">
                <img class="card-img-top imgRadius" src="data:image/jpg;base64,${item.coverImage}" alt="Card image cap">
            </a>
            <div class="card-body">
                <h3 class="card-text"> ${item.title} </h3>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
