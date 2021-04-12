package hu.alkfejl.dao.interfaces;

import hu.alkfejl.model.Movie;
import javafx.collections.ObservableList;

import java.util.List;

public interface MovieDAO {

    public ObservableList<Movie> listMovies();

    public Movie save(Movie movie);

    public void delete(Movie movie);

    int getIdByTitle(String title);

    public ObservableList<String> listByName();

    String findMovieNameById(int movie_id);


    }
