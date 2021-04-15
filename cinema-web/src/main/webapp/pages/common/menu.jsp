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
            <li class="nav-item"><a class="nav-link" href="home.jsp">Főoldal</a></li>
            <li class="nav-item"><a class="nav-link" href="movies.jsp">Filmek</a></li>
            <li class="nav-item"><a class="nav-link" href="showtimes.jsp">Vetítések</a></li>
            <li class="nav-item"><a class="nav-link" href="login.jsp">Bejelentkezés</a></li>
            <li class="nav-item"><a class="nav-link" href="registration.jsp">Regisztráció</a></li>

                        <c:if test="${11 > 1}">
                            <li class="nav-item dropdown ml-auto">
                                <a class='nav-link dropdown-toggle' href='#' id='navbarDropdownMenuLink' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>
                                        :(
                                </a>
                                <div class='dropdown-menu dropdown-menu-right' aria-labelledby='navbarDropdownMenuLink'>
                                    <a class='dropdown-item' href='../LogoutController'>Kijelentkezés</a>
                                </div>
                            </li>
                        </c:if>


        </ul>
    </div>
</nav>