package hu.alkfejl.servlets;

import hu.alkfejl.dao.implementation.UserDAOImpl;
import hu.alkfejl.dao.interfaces.UserDAO;
import hu.alkfejl.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {

    UserDAO dao = UserDAOImpl.getInstance();
    String message = "";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        message = "";
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        if (dao.emailExistCheck(req.getParameter("email"))) {
            message = "Az email cím már foglalt!";
            req.setAttribute("message", message);
            getServletContext().getRequestDispatcher("/pages/registration.jsp").forward(req, resp);
        } else {
            User user = new User();
            user.setUserName(req.getParameter("realname"));
            user.setPassword(req.getParameter("password"));
            user.setEmail(req.getParameter("email"));
            dao.addNewUser(user);
            req.setAttribute("message", message);

            getServletContext().getRequestDispatcher("/pages/login.jsp").forward(req, resp);
        }


    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = (String) req.getSession().getAttribute("email");

        if (email != null) {
            message = "Jelentkezz ki ha új felhasználót szeretnél regisztrálni!";

        } else {
            message = "";
        }

        req.setAttribute("message", message);
        getServletContext().getRequestDispatcher("/pages/registration.jsp").forward(req, resp);


    }
}
