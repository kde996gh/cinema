package hu.alkfejl.controller;

import hu.alkfejl.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {


    @FXML
    public void switchToHalls(ActionEvent actionEvent) {
        App.loadFXML("/fxml/room/room_window.fxml");
    }

    @FXML
    public void switchToMovies(ActionEvent actionEvent) {
        App.loadFXML("/fxml/movie/movie_window.fxml");
    }

    @FXML
    public void switchToTickets(ActionEvent actionEvent) {
        App.loadFXML("/fxml/ticket/ticket_window.fxml");
    }

    @FXML
    public void switchToReservations(ActionEvent actionEvent) {
        App.loadFXML("/fxml/reservation/reservation_window.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void switchToPlayTimes(ActionEvent actionEvent) {
        App.loadFXML("/fxml/playtime/playtime_window.fxml");

    }
}