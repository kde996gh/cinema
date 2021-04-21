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
import java.util.List;

@WebServlet(name = "ReservationServlet", urlPatterns = "/reservation")
public class ReservationServlet extends HttpServlet {
    PlayTimeDAO playtimedao = PlayTimeDAOImpl.getInstance();
    SeatDAO seatDAO = SeatDAOImpl.getInstance();
    RoomDAO roomDAO = RoomDAOImpl.getInstance();
    TicketDAO ticketDAO = TicketDAOImpl.getInstance();
    ReservationDAO reservationDAO = ReservationDAOImpl.getInstance();
    SeatDAOImpl seatDao = SeatDAOImpl.getInstance();
    MovieDAO moviedao = MovieDAOImpl.getInstance();
    UserDAO userDao = UserDAOImpl.getInstance();
    String message = "";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        String email = (String) req.getSession().getAttribute("email");


        if (email != null) {
            int priceInt = Integer.parseInt(req.getParameter("finalPrice"));
            int ptid = Integer.parseInt(req.getParameter("playTimeId"));
            int summa = Integer.parseInt(req.getParameter("finalSumPrice"));

            String[] seatsPicked = req.getParameterValues("seatPicked");

            String seatsPickedString1 = "";
            for (int i = 0; i < seatsPicked.length; i++) {
                if (i == seatsPicked.length - 1) {
                    seatsPickedString1 += seatsPicked[i];
                } else {
                    seatsPickedString1 += seatsPicked[i] + ",";
                }
            }
            String[] splitedSeats = seatsPickedString1.split(",");

            System.out.println("EZA  STRING:" + seatsPickedString1);

            PlayTime currPt = playtimedao.getPlayTimeById(ptid);
          //  User user = userDao.getUserByEmail(email);

            Reservation r = new Reservation();
            r.setMovie_name(currPt.getMovie_name());
            r.setPlaytimedate(currPt.getPlayTimeDate() + " " + currPt.getPlayTimeHours());
            r.setPlaytime_id(ptid);
            r.setPrice_sum(priceInt);
            r.setEmail(email);
            r.setPrice_sum(summa);
            r.setReserved_seat(seatsPickedString1);
            reservationDAO.save(r);

            for (String splitedSeat : splitedSeats) {
                seatDao.reserve(ptid, Integer.parseInt(splitedSeat));
                // System.out.println("ezaz: " + splitedSeats[i]);
            }

            message = "Sikeres foglalás!";

        } else {
            message = "Jelentkezz be a foglaláshoz!";

        }


        //seateknél update
        //String url = "/pages/reservation?ptid="+ptid;
        req.setAttribute("message", message);
        getServletContext().getRequestDispatcher("/pages/reservation.jsp").forward(req, resp);

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int playtimeid = Integer.parseInt(req.getParameter("ptid"));

        String email = (String) req.getSession().getAttribute("email");


        PlayTime playTime = playtimedao.getPlayTimeById(playtimeid);

        List<Seat> seats = seatDAO.getPlayTimeSeats(playtimeid);

        Room currentRoom = roomDAO.getRoomByName(playTime.getRoom_name());

        Ticket ticket = ticketDAO.getTicketByType(playTime.getTicket_type());

        boolean alreadyBooked = reservationDAO.checkIfAlreadyBooked(email, playtimeid);
        System.out.println("alreadyBooked??? : " + alreadyBooked + ", email: " + email + ", ptid: " + playtimeid);

        if (alreadyBooked) {
            message = "Már foglaltál erre a filmre, a profilodon megtekinheted vagy módosíthatod!";
            req.setAttribute("message", message);

        } else {
            message = "";
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
