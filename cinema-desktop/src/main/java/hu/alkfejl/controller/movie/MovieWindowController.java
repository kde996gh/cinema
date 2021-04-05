package hu.alkfejl.controller.movie;

import hu.alkfejl.App;
import javafx.event.ActionEvent;

public class MovieWindowController {
    public void backToMainWindow(ActionEvent actionEvent) {
        App.loadFXML("/fxml/main_window.fxml");
    }
}
