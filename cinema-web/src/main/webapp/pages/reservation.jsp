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
<c:if test="${requestScope.message != ''}">
    ${requestScope.message}
</c:if>
<c:if test="${requestScope.message == ''}">
    <div class="container">
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
            <br>

            <p id="price"><c:out value="${requestScope.ticket.price}"/></p>
            <br>
            <p id="lower_price"><c:out value="${requestScope.ticket.lowerPrice}"/></p>
            <br>
            <label for="lowerPriceCheck">Checkbox:</label>
            <input type="checkbox" id="lowerPriceCheck">


            <p id="finalPrice">
            </p>
        </div>

        <div class="form-group">
            <form action="/reservation" method="post">
                <input type="hidden" id="formPtId" name="playTimeId"
                       value="<c:out value="${requestScope.playtime.id}"/>"/>
                <input type="hidden" id="formSeatArray" name="seatPicked" value=""/>
                <input type="hidden" id="finalPriceToServlet" name="finalPrice" value=""/>
                <input type="hidden" id="finalSumPrice" name="finalSumPrice" value=""/>
                <button id="submit" type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>

    </div>
</c:if>

<script type="text/javascript">
    let glob = [];
    let fprice;
    let formSeatArray = document.getElementById('formSeatArray');
    let formFinalPrice = document.getElementById('finalPriceToServlet');
    let finalSumPrice = document.getElementById('finalSumPrice');

    let lower_check_box = document.getElementById('lowerPriceCheck')

    let price = document.getElementById('price').innerHTML;
    let lower_price = document.getElementById('lower_price').innerHTML;

    let finalPrice = document.getElementById('finalPrice');
    formFinalPrice.value = priceCheck();

    function pickSeatFunction(id, seat_id) {
        let currElem = document.getElementById(id);
        if (currElem.style.backgroundColor === "yellow") {
            currElem.style.backgroundColor = "green"
            currElem.style.backgroundColor = "green"
            for (let i = 0; i < glob.length; i++) {
                if (glob[i] === seat_id) {
                    glob.splice(i, 1);
                }
            }
        } else {
            glob.push(seat_id);
            currElem.style.backgroundColor = "yellow"
        }
        //check();
        formSeatArray.value = check();
        finalPriceCounter();
        finalSumPrice.value = fprice;
        //console.log(a + " " + p);
        //   sendBackPrice = priceCheck()
    }

    $(lower_check_box).change(function () {
        formFinalPrice.value = priceCheck()

        finalPriceCounter();
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
            fprice = parseInt(lower_price) * glob.length;
            // sendBackPrice = parseInt(lower_price);
        } else {
            fprice = parseInt(price) * glob.length;
            //  sendBackPrice = parseInt(price);

        }
        console.log("final proce: " + fprice);

        finalPrice.innerHTML = "Végösszeg: " + fprice;
    }

    function check() {
        return glob;
    }


</script>
</body>

</html>
