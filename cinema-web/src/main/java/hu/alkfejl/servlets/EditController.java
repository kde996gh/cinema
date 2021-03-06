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
        message = "";
        int playtimeid = Integer.parseInt(req.getParameter("ptid"));

        String email = (String) req.getSession().getAttribute("email");

        PlayTime playTime = playtimedao.getPlayTimeById(playtimeid);

        List<Seat> seats = seatDAO.getPlayTimeSeats(playtimeid);

        Room currentRoom = roomDAO.getRoomByName(playTime.getRoomName());

        Ticket ticket = ticketDAO.getTicketByType(playTime.getTicketType());

        List<Reservation> resses = reservationDAO.listReservations();
        String seatsString = "";
        for (Reservation r : resses) {
            if (r.getEmail().equals(email) && r.getPlaytimeId() == playtimeid) {
                seatsString += r.getReservedSeat();
                break;
            }
        }

        message = "";
        req.setAttribute("message", message);
        req.setAttribute("seats", seats);
        req.setAttribute("room", currentRoom);
        req.setAttribute("playtime", playTime);
        req.setAttribute("ticket", ticket);
        req.setAttribute("seatsString", seatsString);

        getServletContext().getRequestDispatcher("/pages/edit_reservation.jsp").forward(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        String email = (String) req.getSession().getAttribute("email");
        
        if (email != null && !req.getParameter("seatPicked").equals("")) {
            String[] seatsPicked = req.getParameterValues("seatPicked");
            int ptid = Integer.parseInt(req.getParameter("playTimeId"));
            int sumPrice = Integer.parseInt(req.getParameter("sumPrice"));

            Reservation rOld = reservationDAO.getReservationByIdEmail(ptid, email);


            String seatPickedOld = rOld.getReservedSeat();

            String seatsPickedString1 = "";
            for (int i = 0; i < seatsPicked.length; i++) {
                if (i == seatsPicked.length - 1) {
                    seatsPickedString1 += seatsPicked[i];
                } else {
                    seatsPickedString1 += seatsPicked[i] + ",";
                }
            }

            String[] splitedSeats = seatsPickedString1.split(",");
            String[] splitedSeatsOld = seatPickedOld.split(",");

            reservationDAO.deleteReservationByUser(email, ptid);

            for (String splitedSeat : splitedSeatsOld) {
                seatDao.updateOnDelete(ptid, Integer.parseInt(splitedSeat));
            }

            PlayTime currPt = playtimedao.getPlayTimeById(ptid);


            Reservation r = new Reservation();
            r.setMovieName(currPt.getMovieName());
            r.setPlaytimeDate(currPt.getPlayTimeDate() + " " + currPt.getPlayTimeHours());
            r.setPlaytimeId(ptid);
            r.setPriceSum(sumPrice);
            r.setEmail(email);
            r.setReservedSeat(seatsPickedString1);
            reservationDAO.save(r);

            for (String splitedSeat : splitedSeats) {
                seatDao.reserve(ptid, Integer.parseInt(splitedSeat));
            }
            message = "Sikeres m??dos??t??s!";

        } else {
            message = "Nem siker??lt a m??dos??t??s!";

        }
        req.setAttribute("message", message);
        getServletContext().getRequestDispatcher("/pages/reservation.jsp").forward(req, resp);

    }


}
