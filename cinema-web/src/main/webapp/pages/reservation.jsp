<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html>
<head>
    <jsp:include page="common/header.jsp"/>
    <title>Filmek</title>
</head>
<body>
<jsp:include page="common/menu.jsp"/>
<c:if test="${requestScope.message != ''}">
    <h1 class="container container2">${requestScope.message}</h1>
</c:if>
<c:if test="${requestScope.message == ''}">
    <div class="container d-flex justify-content-center">
        <div class="content">
            <c:forEach var="seat" items="${requestScope.seats}">
                <c:if test="${(seat.seatId-1) % requestScope.room.colNumber == 0}">
                    <br>
                </c:if>
                <c:if test="${seat.taken == 0}">
                    <button class="notTaken" id="id${seat.seatId}"
                            onclick="pickSeatFunction('id'+${seat.seatId} , ${seat.seatId})">
                            ${seat.seatId}
                    </button>
                </c:if>
                <c:if test="${seat.taken == 1}">
                    <button class="taken">
                            ${seat.seatId}
                    </button>
                </c:if>
            </c:forEach>
        </div>
        <div class="card flex-row flex-wrap">
            <div class="card-block px-2">
                <h4 class="card-title"><c:out value="${requestScope.playtime.movieName}"/></h4>
                <label for="price">Ár: </label>
                <p class="card-text" id="price"><c:out value="${requestScope.ticket.price}"/></p>
                <label for="lowerPrice">Kedvezményes Ár: </label>
                <p class="card-text" id="lowerPrice"><c:out value="${requestScope.ticket.lowerPrice}"/></p>
                <label for="lowerPriceCheck">Kedvezmény </label>
                <input type="checkbox" id="lowerPriceCheck"><br>
                <label for="finalPrice">Végösszeg: </label>
                <p class="card-text" id="finalPrice"></p>
                <div class=" form-group">
                    <form action="/reservation" method="post">
                        <input type="hidden" id="formPtId" name="playTimeId"
                               value="<c:out value="${requestScope.playtime.id}"/>"/>
                        <input type="hidden" id="formSeatArray" name="seatPicked" value=""/>
                        <input type="hidden" id="finalPriceToServlet" name="finalPrice" value=""/>
                        <input type="hidden" id="finalSumPrice" name="finalSumPrice" value=""/>
                        <button id="submit" type="submit" class="btn btn-primary">Foglal</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</c:if>
<script type="text/javascript">
    let picked_seats = [];
    let formSeatArray = document.getElementById('formSeatArray');
    let formFinalPrice = document.getElementById('finalPriceToServlet');
    let finalSumPrice = document.getElementById('finalSumPrice');
    let lower_check_box = document.getElementById('lowerPriceCheck')
    let price = document.getElementById('price').innerHTML;
    let lower_price = document.getElementById('lowerPrice').innerHTML;
    let finalPrice = document.getElementById('finalPrice');
    formFinalPrice.value = priceCheck();

    function pickSeatFunction(id, seat_id) {
        let currElem = document.getElementById(id);
        if (currElem.style.backgroundColor === "yellow") {
            currElem.style.backgroundColor = "green"
            currElem.style.color = "white"
            for (let i = 0; i < picked_seats.length; i++) {
                if (picked_seats[i] === seat_id) {
                    picked_seats.splice(i, 1);
                }
            }
        } else {
            picked_seats.push(seat_id);
            currElem.style.backgroundColor = "yellow"
            currElem.style.color = "black"

        }
        formSeatArray.value = check();
        finalSumPrice.value = priceCheck() * picked_seats.length
        finalPrice.innerHTML = priceCheck() * picked_seats.length;
    }

    $(lower_check_box).change(function () {
        finalSumPrice.value = priceCheck() * picked_seats.length
        finalPrice.innerHTML = priceCheck() * picked_seats.length;

    });

    function priceCheck() {
        if (lower_check_box.checked === true) {
            return parseInt(lower_price);
        } else {
            return parseInt(price);
        }
    }

    function finalPriceCounter() {
        if (lower_check_box.checked === true) {
            return parseInt(lower_price) * picked_seats.length;
        } else {
            return parseInt(price) * picked_seats.length;
        }
    }

    function check() {
        return picked_seats;
    }





</script>
</body>

</html>
