package hu.alkfejl.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProfileServlet", urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {
String message = "";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = (String) req.getSession().getAttribute("email");
        if(email == null){
            message = "Ez az oldal csak bejelentkezés után érhető el!";
        }else{
            message="";
        }

        req.setAttribute("message", message);
        getServletContext().getRequestDispatcher("/pages/profile.jsp").forward(req, resp);

    }
}
