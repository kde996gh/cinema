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
@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        UserDAO userDAO = UserDAOImpl.getInstance();

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = userDAO.loginCheck(email, password);

        if( user == null){
            resp.sendRedirect("/login");
            return;
        }

        req.getSession().setAttribute("email", user.getEmail());
        resp.sendRedirect("/");

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            getServletContext().getRequestDispatcher("/pages/login.jsp").forward(req, resp);


    }
}
