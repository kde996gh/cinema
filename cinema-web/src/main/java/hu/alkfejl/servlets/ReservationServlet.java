package hu.alkfejl.servlets;

import hu.alkfejl.dao.implementation.*;
import hu.alkfejl.dao.interfaces.*;
import hu.alkfejl.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "ReservationServlet", urlPatterns = "/reservation")
public class ReservationServlet extends HttpServlet {
    PlayTimeDAO playtimedao = PlayTimeDAOImpl.getInstance();
    SeatDAO seatDAO = SeatDAOImpl.getInstance();
    RoomDAO roomDAO = RoomDAOImpl.getInstance();
    TicketDAO ticketDAO = TicketDAOImpl.getInstance();
    ReservationDAO reservationDAO = ReservationDAOImpl.getInstance();
    SeatDAOImpl seatDao = SeatDAOImpl.getInstance();




    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");


        int priceInt = Integer.parseInt(req.getParameter("finalPrice"));
        String email = (String) req.getSession().getAttribute("email");
        int ptid = Integer.parseInt(req.getParameter("playTimeId"));
        String[] seatsPicked = req.getParameterValues("seatPicked");
        String seatsPickedString = seatsPicked[0];
        //int arrayyé konvertálás
        ArrayList<Integer> seatsPickedIntArray = new ArrayList<>();
        String[] arrOfStr = seatsPickedString.split(",");

        for (String s : arrOfStr) {
            seatsPickedIntArray.add(Integer.parseInt(s));
        }

        for (Integer integer : seatsPickedIntArray) {
            Reservation r = new Reservation();
            r.setPlaytime_id(ptid);
            r.setPrice(priceInt);
            r.setEmail(email);
            r.setReserved_seat(integer);
            reservationDAO.save(r);
            seatDao.reserve(ptid, integer);
        }




        //seateknél update


    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int playtimeid = Integer.parseInt(req.getParameter("ptid"));
        String message = "";
        // PlayTime pt = null;
        //  PlayTime obj = (PlayTime) req.getAttribute("ptid");
        // System.out.println(obj.getMovie_name());

        // az adott vetítéshez tartozó terem székei
        String email = (String) req.getSession().getAttribute("email");


        PlayTime playTime = playtimedao.getPlayTimeById(playtimeid);

        List<Seat> seats = seatDAO.getPlayTimeSeats(playtimeid);

        Room currentRoom = roomDAO.getRoomByName(playTime.getRoom_name());

        Ticket ticket = ticketDAO.getTicketByType(playTime.getTicket_type());

        boolean alreadyBooked = reservationDAO.checkIfAlreadyBooked(email, playtimeid);
        System.out.println("alreadyBooked??? : " + alreadyBooked + ", email: " +email + ", ptid: " +playtimeid);

        if (alreadyBooked) {
            message = "Már foglaltál erre a filmre, a profilodon megtekinheted vagy módosíthatod!";
            req.setAttribute("message", message);

        }else {
            req.setAttribute("message", message);
            req.setAttribute("seats", seats);
            req.setAttribute("room", currentRoom);
            req.setAttribute("playtime", playTime);
            req.setAttribute("ticket", ticket);
        }

        // System.out.println(playtimeid + " +++PLAYTIMEID");

        getServletContext().getRequestDispatcher("/pages/reservation.jsp").forward(req, resp);


    }
}
