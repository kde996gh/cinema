package hu.alkfejl.controller;

import hu.alkfejl.App;
import hu.alkfejl.dao.implementation.RoomDAOImpl;
import hu.alkfejl.dao.interfaces.RoomDAO;
import hu.alkfejl.model.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RoomWindowController implements Initializable{

    RoomDAO roomDAO = new RoomDAOImpl();
    private List<Room> all;


    public void backToMainWindow(ActionEvent actionEvent) {
        App.loadFXML("/fxml/main_window.fxml");

    }

    @FXML
    private TableView<Room> contactTable;

    @FXML
    private TableColumn<Room, String> nameColumn;

    @FXML
    private TableColumn<Room, Integer> rowNumberColumn;

    @FXML
    private TableColumn<Room, Integer> rowSeatsColumn;

    @FXML
    private TableColumn<Room, Integer> seatSumColumn;

    @FXML
    private TableColumn<Room, Void> actionsColumn;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        contactTable.getItems().setAll(roomDAO.findAll());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        rowNumberColumn.setCellValueFactory(new PropertyValueFactory<>("rowNumber"));
        rowSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("rowSeats"));
    }
}
