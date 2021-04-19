package hu.alkfejl.servlets;

import hu.alkfejl.dao.implementation.PlayTimeDAOImpl;
import hu.alkfejl.dao.implementation.TicketDAOImpl;
import hu.alkfejl.dao.interfaces.PlayTimeDAO;
import hu.alkfejl.dao.interfaces.TicketDAO;
import hu.alkfejl.model.PlayTime;
import hu.alkfejl.model.Ticket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShowTimesServlet", urlPatterns = "/showtimes")
public class ShowTimesServlet extends HttpServlet {
    PlayTimeDAO playtimedao = PlayTimeDAOImpl.getInstance();
    TicketDAO ticketdao = TicketDAOImpl.getInstance();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<PlayTime> playtimes = playtimedao.listPlayTimes();
        List<Ticket> tickets = ticketdao.findAllTicket();
        req.setAttribute("playtimes", playtimes);
        req.setAttribute("tickets", tickets);

        getServletContext().getRequestDispatcher("/pages/showtimes.jsp").forward(req, resp);


    }
}