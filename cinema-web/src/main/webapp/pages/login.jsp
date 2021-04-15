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
    <form action="../LoginController" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input required name="username" type="text" class="form-control" id="username"
                   placeholder="Username"/>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input required name="password" type="password" class="form-control" id="password"
                   placeholder="Password"/>
        </div>
        <button id="submit" type="submit" class="btn btn-primary">Bejelentkezés</button>
    </form>

</div>


</body>
</html>
