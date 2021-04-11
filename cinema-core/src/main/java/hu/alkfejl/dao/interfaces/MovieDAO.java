package hu.alkfejl.dao.interfaces;

import hu.alkfejl.model.Movie;
import javafx.collections.ObservableList;

public interface MovieDAO {

    public ObservableList<Movie> listMovies();

    public Movie save(Movie movie);

    public void delete(Movie movie);



    }
