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
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "EditServlet", urlPatterns = "/editres")
public class EditController extends HttpServlet {

    PlayTimeDAO playtimedao = PlayTimeDAOImpl.getInstance();
    SeatDAO seatDAO = SeatDAOImpl.getInstance();
    RoomDAO roomDAO = RoomDAOImpl.getInstance();
    TicketDAO ticketDAO = TicketDAOImpl.getInstance();
    ReservationDAO reservationDAO = ReservationDAOImpl.getInstance();
    SeatDAOImpl seatDao = SeatDAOImpl.getInstance();
    String message = "";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int playtimeid = Integer.parseInt(req.getParameter("ptid"));

        String email = (String) req.getSession().getAttribute("email");

        PlayTime playTime = playtimedao.getPlayTimeById(playtimeid);

        List<Seat> seats = seatDAO.getPlayTimeSeats(playtimeid);

        Room currentRoom = roomDAO.getRoomByName(playTime.getRoom_name());

        Ticket ticket = ticketDAO.getTicketByType(playTime.getTicket_type());

        List<Reservation> resses = reservationDAO.listReservations();
        String seatsString = "";
        for (Reservation r : resses) {
            if (r.getEmail().equals(email) && r.getPlaytime_id() == playtimeid) {
                seatsString += r.getReserved_seat();
                break;
            }
        }


        message="";
            req.setAttribute("message", message);
            req.setAttribute("seats", seats);
            req.setAttribute("room", currentRoom);
            req.setAttribute("playtime", playTime);
            req.setAttribute("ticket", ticket);
            req.setAttribute("seatsString", seatsString);

        // System.out.println(playtimeid + " +++PLAYTIMEID");

        getServletContext().getRequestDispatcher("/pages/edit_reservation.jsp").forward(req, resp);






    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int sumPrice = Integer.parseInt(req.getParameter("sumPrice"));
        int ptid = Integer.parseInt(req.getParameter("playTimeId"));
        String[] seatsPicked = req.getParameterValues("seatPicked");

        System.out.println("sumprice :"+ sumPrice);
        System.out.println("seatsPicked :"+ Arrays.toString(seatsPicked));

    }
}
