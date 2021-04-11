package hu.alkfejl.controller.playtime;

import hu.alkfejl.App;
import javafx.event.ActionEvent;

public class PlaytimeController {
    public void backToMainWindow() {
        App.loadFXML("/fxml/main_window.fxml");

    }

    public void newPlayTime(ActionEvent actionEvent) {

    }
}
