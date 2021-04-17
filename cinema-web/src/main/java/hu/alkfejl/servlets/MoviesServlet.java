package hu.alkfejl.servlets;

import hu.alkfejl.dao.implementation.MovieDAOImpl;
import hu.alkfejl.dao.interfaces.MovieDAO;
import hu.alkfejl.model.Movie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MoviesServlet", urlPatterns = "/movies")
public class MoviesServlet extends HttpServlet {
    private List<Movie> movies;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("movieName") != null){
            String movieName = req.getParameter("movieName");
           // System.out.println(movieName);
        }

        MovieDAO movideDao = MovieDAOImpl.getInstance();
        List<Movie> all = movideDao.listMovies();
      //  System.out.println(all);

        req.setAttribute("movies", all);

        getServletContext().getRequestDispatcher("/pages/movies.jsp").forward(req, resp);


    }
}
