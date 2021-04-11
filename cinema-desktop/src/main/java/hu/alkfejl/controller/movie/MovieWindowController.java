package hu.alkfejl.controller.movie;

import hu.alkfejl.App;
import hu.alkfejl.dao.implementation.MovieDAOImpl;
import hu.alkfejl.model.Movie;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class MovieWindowController implements Initializable {


    @FXML
    private GridPane gridPane;
    @FXML
    private TextField searchByTitle;

    private List<Movie> movies;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //gridPane.add(x, 0, 0);

        movies = MovieDAOImpl.getInstance().listMovies();
        imageDrawer(movies);

    }

    private void imageDrawer(List<Movie> movies){
        Button[] movieButton = new Button[movies.size()];
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        int col = 0;
        int row = 0;
        for (int i = 0; i < movies.size(); i++) {
            String coverImagePath = movies.get(i).getCoverImage();
            ImageView imgV = new ImageView();
            try {
                imgV.setImage(getImageFromBase64String(coverImagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }

            imgV.setFitWidth(125d);
            imgV.setFitHeight(160d);

            movieButton[i] = new Button("", imgV);
            movieButton[i].setMinWidth(125d);
            movieButton[i].setMinHeight(160d);

            gridPane.add(movieButton[i], col, row);
            col++;

            if (col == 3) {
                col = 0;
                row++;
            }
            Movie currMovieToSend = movies.get(i);

            movieButton[i].setOnAction(actionEvent -> {
                FXMLLoader fxmlLoader = App.loadFXML(("/fxml/movie/movie_add_edit.fxml"));
                MovieEditController controller = fxmlLoader.getController();
                controller.setMovie(currMovieToSend);
            });
        }
    }

    private Image getImageFromBase64String(String newValue) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(newValue));
        return new Image(inputStream);
    }

    public void addNewMovie() {
        FXMLLoader fxmlLoader = App.loadFXML(("/fxml/movie/movie_add_edit.fxml"));
        MovieEditController controller = fxmlLoader.getController();
        controller.setMovie(new Movie());
    }

    public void onSearch(KeyEvent keyEvent) {

        List<Movie> filtered = movies.stream().filter(movie -> movie.getTitle().toLowerCase().contains(searchByTitle.getText().toLowerCase())).collect(Collectors.toList());
        if(!filtered.equals(movies)) {
            gridPane.getChildren().clear();
            imageDrawer(filtered);
        }
        else{
            gridPane.getChildren().clear();
            imageDrawer(movies);
        }
    }
    public void backToMainWindow(ActionEvent actionEvent) {
        App.loadFXML("/fxml/main_window.fxml");
    }

}
