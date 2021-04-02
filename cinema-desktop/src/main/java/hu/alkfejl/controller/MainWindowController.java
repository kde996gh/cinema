package hu.alkfejl.controller;

import hu.alkfejl.App;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainWindowController implements Initializable {





    @FXML
    public void switchToHalls(ActionEvent actionEvent) {
        App.loadFXML("/fxml/halls_window.fxml");
    }

    @FXML
    public void switchToMovies(ActionEvent actionEvent) {
        App.loadFXML("/fxml/movies_window.fxml");
    }

    @FXML
    public void switchToTickets(ActionEvent actionEvent) {
        App.loadFXML("/fxml/tickets_window.fxml");
    }

    @FXML
    public void switchToReservations(ActionEvent actionEvent) {
        App.loadFXML("/fxml/reservations_window.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}