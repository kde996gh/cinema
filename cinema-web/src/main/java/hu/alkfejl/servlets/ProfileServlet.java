package hu.alkfejl.servlets;

import hu.alkfejl.dao.implementation.PlayTimeDAOImpl;
import hu.alkfejl.dao.implementation.ReservationDAOImpl;
import hu.alkfejl.dao.interfaces.PlayTimeDAO;
import hu.alkfejl.dao.interfaces.ReservationDAO;
import hu.alkfejl.model.PlayTime;
import hu.alkfejl.model.Reservation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProfileServlet", urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {
    ReservationDAO reservationdao = ReservationDAOImpl.getInstance();
    PlayTimeDAO playtimedao = PlayTimeDAOImpl.getInstance();
    String message = "";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = (String) req.getSession().getAttribute("email");
        if (email == null) {
            message = "Ez az oldal csak bejelentkezés után érhető el!";
        } else {

            List<Reservation> userReservations = reservationdao.getUserReservations(email);

            List<Integer> userPtResIds = new ArrayList<>();
            for (Reservation r : userReservations) {
                if (!userPtResIds.contains(r.getPlaytimeId())) {
                    userPtResIds.add(r.getPlaytimeId());
                }
            }

            List<PlayTime> usersPlaytimes = new ArrayList<>();
            for (Integer integer : userPtResIds) {
                usersPlaytimes.add(playtimedao.getPlayTimeById(integer));
            }

            req.setAttribute("usersPlaytimes", usersPlaytimes);
            req.setAttribute("userReservations", userReservations);


            System.out.println("Ez a foglalások számai:  " + userPtResIds);


            message = "";
        }


        req.setAttribute("message", message);
        getServletContext().getRequestDispatcher("/pages/profile.jsp").forward(req, resp);

    }
}
