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
import java.util.stream.Collectors;

@WebServlet(name = "MoviesServlet", urlPatterns = "/movies")
public class MoviesServlet extends HttpServlet {
    MovieDAO movideDao = MovieDAOImpl.getInstance();
    private List<Movie> movies = movideDao.listMovies();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String searchInput = req.getParameter("searchInput");
        System.out.println("kereső szó: " + searchInput);
        List<Movie> filtered = movies.stream().filter(movie -> (movie.getTitle().toLowerCase().contains(searchInput.toLowerCase()))
                || (movie.getActors().toLowerCase().contains(searchInput.toLowerCase()))
                || (movie.getDirector().toLowerCase().contains(searchInput.toLowerCase()))
                || (movie.getDescription().toLowerCase().contains(searchInput.toLowerCase()))
        ).collect(Collectors.toList());

        req.setAttribute("movies", filtered);
        getServletContext().getRequestDispatcher("/pages/movies.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        MovieDAO movideDao = MovieDAOImpl.getInstance();
        List<Movie> all = movideDao.listMovies();

        req.setAttribute("movies", all);

        getServletContext().getRequestDispatcher("/pages/movies.jsp").forward(req, resp);


    }
}
