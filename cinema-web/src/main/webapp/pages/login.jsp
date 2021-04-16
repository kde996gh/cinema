<%--
  Created by IntelliJ IDEA.
  User: Deniel
  Date: 2021. 04. 15.
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="common/header.jsp"/>
    <title>Bejelentkezés</title>
</head>
<body>
<jsp:include page="common/menu.jsp"/>

<div class="container">
    <form action="/login" method="post">
        <div class="form-group">
            <label for="email">Email cím</label>
            <input required name="email" type="text" class="form-control" id="email"
                   placeholder="email"/>
        </div>
        <div class="form-group">
            <label for="password">Jelszó</label>
            <input required name="password" type="password" class="form-control" id="password"
                   placeholder="Password"/>
        </div>
        <button id="submit" type="submit" class="btn btn-primary">Bejelentkezés</button>
    </form>

</div>


</body>
</html>
