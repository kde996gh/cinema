<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="common/header.jsp"/>
    <title>Bejelentkezés</title>
</head>
<body>
<jsp:include page="common/menu.jsp"/>

<div class="container container2">
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
