package hu.alkfejl.dao.implementation;

import hu.alkfejl.config.CinemaConfiguration;
import hu.alkfejl.dao.interfaces.MovieDAO;
import hu.alkfejl.model.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class MovieDAOImpl implements MovieDAO {

    private static final String SELECT_ALL_MOVIES = "SELECT * FROM MOVIE";
    private String connectionURL;
    private Connection conn;
    private static MovieDAOImpl instance = new MovieDAOImpl();

    public static MovieDAOImpl getInstance() {
        return instance;
    }


    ///QUERIES


    public MovieDAOImpl() {
        connectionURL = CinemaConfiguration.getValue("db.url");
        try {
            conn = DriverManager.getConnection(connectionURL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ObservableList<Movie> listMovies() {

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_MOVIES);
        ) {
            ObservableList<Movie> movies = FXCollections.observableArrayList();

            while (rs.next()) {
                Movie currMovie = new Movie();

                currMovie.setId(rs.getInt("id"));
                currMovie.setTitle(rs.getString("title"));
                currMovie.setLength(rs.getInt("length"));
                currMovie.setAgeLimit(rs.getInt("ageLimit"));
                currMovie.setDirector(rs.getString("director"));
                currMovie.setActors(rs.getString("actors"));
                currMovie.setDescription(rs.getString("description"));
                currMovie.setCoverImage(rs.getString("coverImage"));
                currMovie.setMovieType(rs.getInt("movieType"));

                movies.add(currMovie);
            }
            return movies;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


}
