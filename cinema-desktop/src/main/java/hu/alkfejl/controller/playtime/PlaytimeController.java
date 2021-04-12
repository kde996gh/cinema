package hu.alkfejl.controller.playtime;

import hu.alkfejl.App;
import hu.alkfejl.controller.movie.MovieEditController;
import hu.alkfejl.model.Movie;
import hu.alkfejl.model.PlayTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

public class PlaytimeController {
    public void backToMainWindow() {
        App.loadFXML("/fxml/main_window.fxml");

    }

    public void newPlayTime(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = App.loadFXML(("/fxml/playtime/playtime_add_edit.fxml"));
        PlayTimeAddEditController controller = fxmlLoader.getController();
        controller.setPlayTime(new PlayTime());

    }
}
