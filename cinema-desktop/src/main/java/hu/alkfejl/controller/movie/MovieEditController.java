package hu.alkfejl.controller.movie;

import hu.alkfejl.App;
import hu.alkfejl.dao.implementation.MovieDAOImpl;
import hu.alkfejl.dao.interfaces.MovieDAO;
import hu.alkfejl.model.Movie;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.converter.NumberStringConverter;

import java.io.*;
import java.net.URL;
import java.util.Base64;
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
    private ImageView coverImage;
    @FXML
    private Button newCoverImage;

    private Movie movie;
    private MovieDAO moviedao = new MovieDAOImpl();
    private StringProperty newImage = new SimpleStringProperty();


    public void setMovie(Movie m) {
        this.movie = m;

        title.textProperty().bindBidirectional(movie.titleProperty());
        length.textProperty().bindBidirectional(movie.lengthMinProperty(), new NumberStringConverter());
        movieType.textProperty().bindBidirectional(movie.movieTypeProperty(), new NumberStringConverter());
        ageLimit.textProperty().bindBidirectional(movie.ageLimitProperty(), new NumberStringConverter());
        director.textProperty().bindBidirectional(movie.directorProperty());
        actors.textProperty().bindBidirectional(movie.actorsProperty());
        description.textProperty().bindBidirectional(movie.descriptionProperty());

        newImage.bindBidirectional(movie.coverImageProperty());
        if (newImage.getValue() != null) {
            try {

                coverImage.setImage(getImageFromBase64String(newImage.getValue()));
            } catch (IOException e) {
                System.out.println("Hiba");
                e.printStackTrace();
            }
        }
        newImage.addListener((observable, oldV, newV) -> {
            try {
                coverImage.setImage(getImageFromBase64String(newV));
            } catch (IOException e) {
                System.out.println("Hiba az addlistenerben!");
                e.printStackTrace();
            }
        });
    }

    public void backToMovies() {
        App.loadFXML("/fxml/movie/movie_window.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newCoverImage.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.png, *.jpg", "*.png", "*.jpg"));
            File file = fileChooser.showOpenDialog(null);

            newImage.set(encodeFileToBase64Binary(file));
        });


    }

    private static String encodeFileToBase64Binary(File file) {
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.getEncoder().encode(bytes), "UTF-8");
        } catch (IOException e) {
            System.err.println("Hiba!");
        }

        return encodedfile;
    }

    private Image getImageFromBase64String(String newValue) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(newValue));
        return new Image(inputStream);
    }

    @FXML
    public void saveMovie() {
        movie = moviedao.save(movie);
        App.loadFXML("/fxml/movie/movie_window.fxml");
    }
}
