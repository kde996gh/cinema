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
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        User user = new User();
        user.setUserName(req.getParameter("realname"));
        user.setPassword(req.getParameter("password"));
        user.setEmail(req.getParameter("email"));

        dao.addNewUser(user);
        resp.sendRedirect("/login");


    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = (String) req.getSession().getAttribute("email");
        System.out.println("email : " + email);
        if(email != null){
            System.out.println("bejöttem!");
            message = "Jelentkezz ki ha új felhasználót szeretnél regisztrálni!";

        }else{
            message="";
        }

        req.setAttribute("message", message);
        getServletContext().getRequestDispatcher("/pages/registration.jsp").forward(req, resp);


    }
}
