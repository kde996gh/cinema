package hu.alkfejl.controller.movie;

import hu.alkfejl.dao.implementation.MovieDAOImpl;
import hu.alkfejl.model.Movie;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieEditController  {

    @FXML
    private TextField title;

    @FXML
    private TextArea description;

    private ObservableList<Movie> movies;

    private Movie movie;


    public void setMovie(Movie m) {
        System.out.println("Megnyomták az editet!");
        this.movie = m;
      // title.setText(movie.getTitle());
       // description.setText(movie.getDescription());
        title.textProperty().bindBidirectional(movie.titleProperty());//szoba nevének betölése 2way bindinggel
        description.textProperty().bindBidirectional(movie.descriptionProperty());
    }


    public void initialize(Movie m) {
        movies = MovieDAOImpl.getInstance().listMovies();

    }
}
