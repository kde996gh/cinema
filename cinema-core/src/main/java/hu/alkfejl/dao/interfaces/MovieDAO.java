package hu.alkfejl.dao.interfaces;

import hu.alkfejl.model.Movie;
import javafx.collections.ObservableList;


public interface MovieDAO {

    ObservableList<Movie> listMovies();

    Movie save(Movie movie);

    void delete(Movie movie);

    ObservableList<String> listByName();

    Movie findMovie(int id);
}
