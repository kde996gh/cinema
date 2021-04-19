package hu.alkfejl.servlets;

import hu.alkfejl.dao.implementation.PlayTimeDAOImpl;
import hu.alkfejl.dao.implementation.ReservationDAOImpl;
import hu.alkfejl.dao.implementation.SeatDAOImpl;
import hu.alkfejl.dao.interfaces.PlayTimeDAO;
import hu.alkfejl.dao.interfaces.ReservationDAO;
import hu.alkfejl.dao.interfaces.SeatDAO;
import hu.alkfejl.model.PlayTime;
import hu.alkfejl.model.Reservation;
import hu.alkfejl.model.Seat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "DeleteController", urlPatterns = "/deleteres")
public class DeleteController extends HttpServlet {
    SeatDAO seatdao = SeatDAOImpl.getInstance();
    List<Seat> seats = seatdao.getAllSeats();
    PlayTimeDAO playtimedao = PlayTimeDAOImpl.getInstance();

    ReservationDAO rdao = ReservationDAOImpl.getInstance();
    String isDeleted = "";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ptid = req.getParameter("ptid");
        String email = (String) req.getSession().getAttribute("email");

        List<Reservation> resses = rdao.listReservations();


        if (ptid != null && !ptid.isEmpty()) {
            int intptid = Integer.parseInt(ptid);

            PlayTime pt = playtimedao.getPlayTimeById(intptid);

            String playDate = pt.getPlayTimeDate() + " " + pt.getPlayTimeHours();

            if (timeCheck(playDate)) {
                List<Integer> seatNumbers = new ArrayList<>();
                String seats = "";

                for (Reservation r : resses) {
                    if (r.getEmail().equals(email) && r.getPlaytime_id() == intptid) {
                        seats += r.getReserved_seat();
                        break;
                    }
                }
                String[] splitedSeats = seats.split(",");

                for (String splitedSeat : splitedSeats) {
                    seatdao.updateOnDelete(intptid, Integer.parseInt(splitedSeat));

                }

                rdao.deleteReservationByUser(email, intptid);
                isDeleted = "Sikeres törlés!";
            } else {
                isDeleted = "Sajnos már nem mondhatod le a foglalást, csak 24 órával előbb mint az előadás kezdete!";
            }

        } else {
            isDeleted = "";
        }

        req.setAttribute("message", isDeleted);
        req.setAttribute("isDeleted", isDeleted);
        getServletContext().getRequestDispatcher("/pages/reservation.jsp").forward(req, resp);


    }

    public boolean timeCheck(String playTime_time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        String currentDate = formatter.format(date);

        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m");

        LocalDateTime dateTime1 = LocalDateTime.parse(playTime_time, dateformatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(currentDate, dateformatter);
        // System.out.println("dateTime1 " + dateTime1);
        //System.out.println("dateTime2 " + dateTime2);

        long diffInMinutes = Math.abs(java.time.Duration.between(dateTime1, dateTime2).toMinutes());
        // System.out.println("percek hátra: " + diffInMinutes);

        if (diffInMinutes >= 1440) {
            return true;
        } else {
            return false;
        }

    }

}
