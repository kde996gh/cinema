package hu.alkfejl.controller;

import hu.alkfejl.App;
import javafx.event.ActionEvent;

public class MoviesWindowController {
    public void backToMainWindow(ActionEvent actionEvent) {
        App.loadFXML("/fxml/main_window.fxml");
    }
}
