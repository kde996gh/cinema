package hu.alkfejl.controller.room;

import hu.alkfejl.App;
import hu.alkfejl.controller.Utils;
import hu.alkfejl.dao.implementation.ReservationDAOImpl;
import hu.alkfejl.dao.implementation.RoomDAOImpl;
import hu.alkfejl.dao.interfaces.ReservationDAO;
import hu.alkfejl.dao.interfaces.RoomDAO;
import hu.alkfejl.model.Room;
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

public class RoomWindowController implements Initializable {

    RoomDAO roomDAO = new RoomDAOImpl();
    private List<Room> all;

    public void backToMainWindow(ActionEvent actionEvent) {
        App.loadFXML("/fxml/main_window.fxml");

    }

    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TableColumn<Room, String> nameColumn;

    @FXML
    private TableColumn<Room, Integer> rowNumberColumn;

    @FXML
    private TableColumn<Room, Integer> colNumberColumn;

    @FXML
    private TableColumn<Room, Integer> seatNumberColumn;

    @FXML
    private TableColumn<Room, Void> actionsColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTable();
        roomTable.getItems().setAll(roomDAO.listRooms());
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        rowNumberColumn.setCellValueFactory(new PropertyValueFactory<>("rowNumber"));
        colNumberColumn.setCellValueFactory(new PropertyValueFactory<>("colNumber"));
        seatNumberColumn.setCellValueFactory(new PropertyValueFactory<>("seatNumber"));

        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Törlés");
            private final Button editButton = new Button("Módosítás");

            {
                deleteButton.setOnAction(event -> {
                    Room room = getTableRow().getItem();
                    deleteRoom(room);// törlés
                    refreshTable(); // táblafrissites
                });

                editButton.setOnAction(event -> {
                    Room c = getTableRow().getItem();
                    editRoom(c);
                    refreshTable();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox();
                    container.getChildren().addAll(editButton, deleteButton);
                    container.setSpacing(10.0);
                    setGraphic(container);
                }
            }
        });
    }

    private void refreshTable() {
        all = roomDAO.listRooms();
        roomTable.getItems().setAll(all);
    }

    private void deleteRoom(Room room) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Biztosan törlöd a következő termet? " + room.getName(), ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(ButtonType.YES)) {
                roomDAO.delete(room);
            }
        });
        Utils.showInfo("Sikeres törlés!");
    }

    private void editRoom(Room room) {
        FXMLLoader fxmlLoader = App.loadFXML(("/fxml/room/room_add_edit.fxml"));
        RoomAddEditController controller = fxmlLoader.getController();
        controller.setRoom(room);
    }

    public void onAddOrEditRoom() {
        FXMLLoader fxmlLoader = App.loadFXML(("/fxml/room/room_add_edit.fxml"));
        RoomAddEditController controller = fxmlLoader.getController();
        controller.setRoom(new Room());
    }
}
