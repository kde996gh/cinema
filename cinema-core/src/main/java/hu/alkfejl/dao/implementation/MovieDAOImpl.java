package hu.alkfejl.dao.implementation;

import hu.alkfejl.config.CinemaConfiguration;
import hu.alkfejl.dao.interfaces.MovieDAO;
import hu.alkfejl.model.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class MovieDAOImpl implements MovieDAO {

    private static final String SELECT_ALL_MOVIES = "SELECT * FROM MOVIE";
    private static final String UPDATE_MOVIE = "UPDATE MOVIE SET title=?, lengthMin=?, ageLimit=?, director=?, actors=?, description=?, coverImage=?, movieType=? WHERE id=?";
    private static final String INSERT_MOVIE = "INSERT INTO MOVIE (title, lengthMin, ageLimit, director, actors, description, coverImage, movieType) VALUES (?,?,?,?,?,?,?,?)";
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
    @Override
    public ObservableList<Movie> listMovies() {

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_MOVIES);
        ) {
            ObservableList<Movie> movies = FXCollections.observableArrayList();

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
    public Movie save(Movie movie){
        try(
                PreparedStatement stmt = movie.getId() <= 0 ? conn.prepareStatement(INSERT_MOVIE, Statement.RETURN_GENERATED_KEYS) : conn.prepareStatement(UPDATE_MOVIE)

        ){
            if(movie.getId() > 0){
                stmt.setInt(9 ,movie.getId());
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


        }catch (SQLException s){
            s.printStackTrace();
            return null;

        }
        return movie;

    }

}
