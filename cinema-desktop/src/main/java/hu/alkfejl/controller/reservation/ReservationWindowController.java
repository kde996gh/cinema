package hu.alkfejl.controller.reservation;

import hu.alkfejl.App;
import javafx.event.ActionEvent;

public class ReservationWindowController {
    public void backToMainWindow(ActionEvent actionEvent) {
        App.loadFXML("/fxml/main_window.fxml");

    }
}
