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

@WebServlet(name = "ReservationServlet", urlPatterns = "/reservation")
public class ReservationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        String[] seatsPicked = req.getParameterValues("seatPicked");
        String price = req.getParameter("finalPrice");
        String email = (String) req.getSession().getAttribute("email");

//        int contactId = 0;
//        int contactId = 0;
//        try {
//            contactId = Integer.parseInt(req.getParameter("finalPrice"));
//        } catch (NumberFormatException ex){
//            ex.printStackTrace();
//        }

        System.out.println("ezaz????? :PLS :))))) " + Arrays.toString(seatsPicked) + "   ara: " + price + "   , " + email);

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int playtimeid = Integer.parseInt(req.getParameter("ptid"));
       // PlayTime pt = null;
      //  PlayTime obj = (PlayTime) req.getAttribute("ptid");
       // System.out.println(obj.getMovie_name());

        // az adott vetítéshez tartozó terem székei

        PlayTimeDAO playtimedao = PlayTimeDAOImpl.getInstance();
        PlayTime playTime = playtimedao.getPlayTimeById(playtimeid);

        SeatDAO seatDAO = SeatDAOImpl.getInstance();
        List<Seat> seats = seatDAO.getPlayTimeSeats(playtimeid);

        RoomDAO roomDAO = RoomDAOImpl.getInstance();
        Room currentRoom = roomDAO.getRoomByName(playTime.getRoom_name());

        TicketDAO ticketDAO = TicketDAOImpl.getInstance();
        Ticket ticket = ticketDAO.getTicketByType(playTime.getTicket_type());


        req.setAttribute("seats", seats);
        req.setAttribute("room", currentRoom);
        req.setAttribute("playtime", playTime);
        req.setAttribute("ticket", ticket);


       // System.out.println(playtimeid + " +++PLAYTIMEID");

        getServletContext().getRequestDispatcher("/pages/reservation.jsp").forward(req, resp);



    }
}
