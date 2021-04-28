<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-dark">
    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown"
            aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
        <ul class="navbar-nav w-100">
            <li class="nav-item"><a class="nav-link" href="/">Főoldal</a></li>
            <li class="nav-item"><a class="nav-link" href="/movies">Filmek</a></li>
            <li class="nav-item"><a class="nav-link" href="/showtimes">Vetítések</a></li>

            <c:if test="${sessionScope.email != null}">
                <li class="nav-item dropdown ml-auto">
                    <a class='nav-link dropdown-toggle' href='#' id='navbarDropdownMenuLink' data-toggle='dropdown'
                       aria-haspopup='true' aria-expanded='false'>
                            ${sessionScope.email}
                    </a>
                    <div class='dropdown-menu dropdown-menu-right' aria-labelledby='navbarDropdownMenuLink'>
                        <a class='dropdown-item' href='/logout'>Kijelentkezés</a>
                        <a class='dropdown-item' href='/profile'>Profilom</a>
                    </div>
                </li>
            </c:if>
            <c:if test="${sessionScope.email == null}">
                <li class="nav-item"><a class="nav-link" href="/login">Bejelentkezés</a></li>
                <li class="nav-item"><a class="nav-link" href="/registration">Regisztráció</a></li>
            </c:if>
        </ul>
    </div>
</nav>