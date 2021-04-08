package hu.alkfejl.controller.movie;

import hu.alkfejl.App;
import hu.alkfejl.dao.implementation.MovieDAOImpl;
import hu.alkfejl.model.Movie;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MovieWindowController implements Initializable {

    public void backToMainWindow(ActionEvent actionEvent) {
        App.loadFXML("/fxml/main_window.fxml");
    }

    // @FXML
    //   private ScrollPane scrollPane;
    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane movieListPane;
    @FXML
    private FlowPane menuFlowPane;

    //gridPane.add(movieBtn[i], col, row);


    Button x = new Button("FIKA");

    private ObservableList<Movie> movies;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //gridPane.add(x, 0, 0);

        movies = MovieDAOImpl.getInstance().listMovies();

        Button[] movieButton = new Button[movies.size()];
        int col = 0;
        int row = 0;
        for (int i = 0; i < movies.size(); i++) {
            String coverImagePath = movies.get(i).getCoverImage();

            Image image = new Image("file:" + coverImagePath);
            ImageView imgV = new ImageView(image);
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
            int currentIndex = i;

            /*
            FXMLLoader fxmlLoader = App.loadFXML(("/fxml/room/room_add_edit.fxml"));
            RoomAddEditController controller = fxmlLoader.getController();
            controller.setRoom(room);*/

            Movie currMovieToSend = movies.get(i);

            movieButton[i].setOnAction(actionEvent -> {
                FXMLLoader fxmlLoader = App.loadFXML(("/fxml/movie/edit_movie.fxml"));
                MovieEditController controller = fxmlLoader.getController();
                controller.setMovie(currMovieToSend);
            });

        }
    }

}
