<%@ page import="hu.alkfejl.model.Seat" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="common/header.jsp"/>
    <title>Filmek</title>
</head>
<body>
<jsp:include page="common/menu.jsp"/>
<c:if test="${requestScope.seatsString != ''}">
    <p id="reservedSeats">${requestScope.seatsString}</p>
</c:if>
<c:if test="${requestScope.message != ''}">
    ${requestScope.message}
</c:if>
<c:if test="${requestScope.message == ''}">
    <div class="container">
        <div class="content">
            <c:forEach var="seat" items="${requestScope.seats}">
                <c:if test="${(seat.seat_id-1) % requestScope.room.colNumber == 0}">
                    <br>
                </c:if>
                <c:if test="${seat.taken == 0}">
                    <button class="notTaken" id="id${seat.seat_id}"
                            onclick="pickSeatFunction('id'+${seat.seat_id} , ${seat.seat_id})">
                            ${seat.seat_id}
                    </button>
                </c:if>
                <c:if test="${seat.taken == 1}">
                    <button class="taken" id="id${seat.seat_id}"
                            onclick="pickSeatFunction('id'+${seat.seat_id} , ${seat.seat_id})"
                    >
                            ${seat.seat_id}
                    </button>
                </c:if>

            </c:forEach>
            <br>

            <p id="price"><c:out value="${requestScope.ticket.price}"/></p>
            <br>
            <p id="lower_price"><c:out value="${requestScope.ticket.lowerPrice}"/></p>
            <br>
            <label for="lowerPriceCheck">Checkbox:</label>
            <br>

            <input type="checkbox" id="lowerPriceCheck">
            <br>

            Végösszeg:
            <p id="finalPrice">
            </p>
        </div>

        <div class="form-group">
            <form action="/editres" method="post">
                <input type="hidden" id="formPtId" name="playTimeId"
                       value="<c:out value="${requestScope.playtime.id}"/>"/>
                <input type="hidden" id="formSeatArray" name="seatPicked" value=""/>
                <input type="hidden" id="formSeatArrayOld" name="seatPickedOld" value=""/>
                <input type="hidden" id="formSumPrice" name="sumPrice" value=""/>
                <button id="submit" type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>

    </div>
</c:if>

<script type="text/javascript">
    let picked_seats = [];
    let formSeatArray = document.getElementById('formSeatArray'); // itt megy vissza a szervernek a választott szék/ek
    let formSeatArrayOld = document.getElementById('old'); // itt megy vissza a szervernek a választott szék/ek
    //  let formFinalPrice = document.getElementById('finalPriceToServlet');
    let formSumPrice = document.getElementById('formSumPrice');
    let lower_check_box = document.getElementById('lowerPriceCheck')

    let price = document.getElementById('price').innerHTML;
    let lower_price = document.getElementById('lower_price').innerHTML;

    let finalPrice = document.getElementById('finalPrice');


    // formFinalPrice.value = priceCheck();

    //let fprice = picked_seats.length *  parseInt(price);
    //formSumPrice.value = fprice;


    function pickSeatFunction(id, seat_id) {
        let currElem = document.getElementById(id);
        if (currElem.style.backgroundColor === "yellow" || currElem.style.backgroundColor === "blue") {
            currElem.style.backgroundColor = "green"
            for (let i = 0; i < picked_seats.length; i++) {
                if (picked_seats[i] == seat_id)
                    picked_seats.splice(i, 1);
            }
        } else if (currElem.style.backgroundColor === "green") {
            picked_seats.push(seat_id);
            currElem.style.backgroundColor = "yellow"
        }

        formSeatArray.value = check();
        formSumPrice.value = priceCheck() * picked_seats.length
        finalPrice.innerHTML = priceCheck() * picked_seats.length;
    }

    $(lower_check_box).change(function () {
        formSumPrice.value = priceCheck() * picked_seats.length
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

    let reservedSeats = document.getElementById('reservedSeats').innerHTML;
    let reservedSeatsArray = reservedSeats.split(",");


    for (let i = 0; i < reservedSeatsArray.length; i++) {
        picked_seats.push(reservedSeatsArray[i]);
        let idBuilder = "id" + reservedSeatsArray[i];
        let currElem = document.getElementById(idBuilder);
        currElem.style.backgroundColor = "blue";
    }
    var x = document.getElementsByClassName("notTaken");
    var i;
    for (i = 0; i < x.length; i++) {
        x[i].style.backgroundColor = "green";
    }


    formSumPrice.value = parseInt(price) * picked_seats.length
    finalPrice.innerHTML = parseInt(price) * picked_seats.length;
    console.log(" picked seats: " + picked_seats)
    formSeatArray.value = picked_seats;

    formSeatArrayOld.value = picked_seats;

    console.log(" lowerpice :" + parseInt(lower_price));
    console.log(" lowerpice :" + parseInt(price));
    console.log(" seatspicked :" + picked_seats);
    console.log(" seatspicked :" + picked_seats.length);

</script>
</body>

</html>
