package hu.alkfejl.servlets;

import hu.alkfejl.dao.implementation.MovieDAOImpl;
import hu.alkfejl.dao.implementation.PlayTimeDAOImpl;
import hu.alkfejl.dao.interfaces.MovieDAO;
import hu.alkfejl.dao.interfaces.PlayTimeDAO;
import hu.alkfejl.model.Movie;
import hu.alkfejl.model.PlayTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MovieServlet", urlPatterns = "/movie")
public class MovieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("movid") != null) {
            int movid = Integer.parseInt(req.getParameter("movid"));
            String message = "";
            MovieDAO movideDao = MovieDAOImpl.getInstance();
            Movie movie = movideDao.findMovie(movid);

            if (movie != null) {
                req.setAttribute("movie", movie);
                PlayTimeDAO ptdao = PlayTimeDAOImpl.getInstance();

                List<PlayTime> playTimesResult = ptdao.getMoviePlayTimes(movie.getTitle());
                req.setAttribute("playTimes", playTimesResult);

            } else {
                message = "Nincs találat!";
                req.setAttribute("message", message);
            }
        } else {
            System.out.println("Nem létezik!");
        }

        getServletContext().getRequestDispatcher("/pages/movie.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
