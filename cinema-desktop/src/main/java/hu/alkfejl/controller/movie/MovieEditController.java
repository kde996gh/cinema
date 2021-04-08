package hu.alkfejl.controller.movie;

import hu.alkfejl.App;
import hu.alkfejl.dao.implementation.MovieDAOImpl;
import hu.alkfejl.model.Movie;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.ResourceBundle;


public class MovieEditController implements Initializable {

    @FXML
    private TextField title;
    @FXML
    private TextField length;
    @FXML
    private TextField movieType;
    @FXML
    private TextField ageLimit;
    @FXML
    private TextField director;
    @FXML
    private TextArea actors;
    @FXML
    private TextArea description;
    @FXML
    private Pane coverImagePane;



    private ObservableList<Movie> movies;

    private Movie movie;


    public void setMovie(Movie m) {
        this.movie = m;

        title.textProperty().bindBidirectional(movie.titleProperty());
        length.textProperty().bindBidirectional(movie.lengthProperty(), new NumberStringConverter());
        movieType.textProperty().bindBidirectional(movie.movieTypeProperty(), new NumberStringConverter());
        ageLimit.textProperty().bindBidirectional(movie.ageLimitProperty(), new NumberStringConverter());
        director.textProperty().bindBidirectional(movie.directorProperty());
        actors.textProperty().bindBidirectional(movie.actorsProperty());
        description.textProperty().bindBidirectional(movie.descriptionProperty());

        Image image = new Image("file:" + movie.getCoverImage());
        ImageView imgV = new ImageView(image);
        imgV.setFitWidth(125d);
        imgV.setFitHeight(125d);

        coverImagePane.getChildren().add(imgV);
    }




    public void backToMovies() {
        App.loadFXML("/fxml/movie/movie_window.fxml");
    }

    public void saveMovie() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
