package hu.alkfejl.dao.implementation;

import hu.alkfejl.config.CinemaConfiguration;
import hu.alkfejl.dao.interfaces.MovieDAO;
import hu.alkfejl.model.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAOImpl implements MovieDAO {

    private static final String SELECT_ALL_MOVIES = "SELECT * FROM MOVIE";
    private static final String UPDATE_MOVIE = "UPDATE MOVIE SET title=?, lengthMin=?, ageLimit=?, director=?, actors=?, description=?, coverImage=?, movieType=? WHERE id=?";
    private static final String INSERT_MOVIE = "INSERT INTO MOVIE (title, lengthMin, ageLimit, director, actors, description, coverImage, movieType) VALUES (?,?,?,?,?,?,?,?)";
    private static final String DELETE_MOVIE = "DELETE FROM MOVIE WHERE id=?";
    private static final String SELECT_ONLY_TITLES = "SELECT title FROM MOVIE";
    private final String connectionURL = CinemaConfiguration.getValue("db.url");
    private static MovieDAOImpl instance;


    public static MovieDAOImpl getInstance() {
        if (instance == null) {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            instance = new MovieDAOImpl();
        }
        return instance;
    }

    public MovieDAOImpl() {
    }

    @Override
    public List<Movie> listMovies() {
        try (Connection conn = DriverManager.getConnection(connectionURL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_MOVIES);
        ) {
            List<Movie> movies = new ArrayList<>();

            while (rs.next()) {
                Movie currMovie = new Movie();

                currMovie.setId(rs.getInt("id"));
                currMovie.setTitle(rs.getString("title"));
                currMovie.setLengthMin(rs.getInt("lengthMin"));
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

    @Override
    public ObservableList<String> listByName() {
        ObservableList<String> result = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection(connectionURL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ONLY_TITLES)
        ) {
            while (rs.next()) {
                String a = rs.getString("title");
                result.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Movie save(Movie movie) {
        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement stmt = movie.getId() <= 0 ? conn.prepareStatement(INSERT_MOVIE, Statement.RETURN_GENERATED_KEYS) : conn.prepareStatement(UPDATE_MOVIE)
        ) {
            if (movie.getId() > 0) {
                stmt.setInt(9, movie.getId());
            }
            stmt.setString(1, movie.getTitle());
            stmt.setInt(2, movie.getLengthMin());
            stmt.setInt(3, movie.getAgeLimit());
            stmt.setString(4, movie.getDirector());
            stmt.setString(5, movie.getActors());
            stmt.setString(6, movie.getDescription());
            stmt.setString(7, movie.getCoverImage());
            stmt.setInt(8, movie.getMovieType());
            stmt.executeUpdate();

            if (movie.getId() <= 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    movie.setId(rs.getInt(1));
                }
            }


        } catch (SQLException s) {
            s.printStackTrace();
            return null;

        }
        return movie;
    }

    @Override
    public void delete(Movie movie) {
        try (Connection conn = DriverManager.getConnection(connectionURL);
             PreparedStatement stmt = conn.prepareStatement(DELETE_MOVIE)) {
            stmt.setInt(1, movie.getId());
            stmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public Movie findMovie(int id) {
        List<Movie> movieList = this.listMovies();
        for (Movie it : movieList) {
            if (it.getId() == id) {
                return it;
            }
        }
        return null;
    }
}

