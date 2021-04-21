package hu.alkfejl.controller.playtime;

import hu.alkfejl.App;
import hu.alkfejl.controller.Utils;
import hu.alkfejl.controller.movie.MovieEditController;
import hu.alkfejl.controller.room.RoomAddEditController;
import hu.alkfejl.dao.implementation.PlayTimeDAOImpl;
import hu.alkfejl.dao.interfaces.PlayTimeDAO;
import hu.alkfejl.model.Movie;
import hu.alkfejl.model.PlayTime;
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

public class PlaytimeController implements Initializable {

    PlayTimeDAO playtimedao = new PlayTimeDAOImpl();
    private List<PlayTime> all;

    @FXML
    private TableView<PlayTime> playTimeTable;

    @FXML
    private TableColumn<PlayTime, String> movieNameColumn;
    @FXML
    private TableColumn<PlayTime, String> roomColumn;
    @FXML
    private TableColumn<PlayTime, Integer> ticketTypeColumn;
    @FXML
    private TableColumn<PlayTime, String> dateColumn;
    @FXML
    private TableColumn<PlayTime, String> timeColumn;
    @FXML
    private TableColumn<PlayTime, Void> actionsColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //   playTimeTable.getItems().setAll()
        refreshTable();
        playTimeTable.getItems().setAll(all);

        movieNameColumn.setCellValueFactory(new PropertyValueFactory<>("movieName"));
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        ticketTypeColumn.setCellValueFactory(new PropertyValueFactory<>("ticketType"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("playTimeDate"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("playTimeHours"));

        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Törlés");
            private final Button editButton = new Button("Módosítás");

            {
                deleteButton.setOnAction(event -> {
                    PlayTime playtime = getTableRow().getItem();
                    //System.out.println("megnyomták a törlés gombot");
                    deletePlayTime(playtime);// törlés
                    refreshTable(); // táblafrissites
                });

                editButton.setOnAction(event -> {
                    PlayTime playtime = getTableRow().getItem();
                    editPlayTime(playtime);
                    // System.out.println("megnyomták a módosítás gombot");

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
        all = playtimedao.listPlayTimes();
        playTimeTable.getItems().setAll(all);
    }

    private void deletePlayTime(PlayTime playtime) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Biztosan törlöd a következő termet? ", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(buttonType.YES)) {
                playtimedao.delete(playtime);
                playtimedao.deleteRoomSeat(playtime);
            }
        });
        Utils.showInfo("Sikeres törlés!");

    }

    private void editPlayTime(PlayTime playtime) {
        FXMLLoader fxmlLoader = App.loadFXML(("/fxml/playtime/playtime_add_edit.fxml"));
        PlayTimeAddEditController controller = fxmlLoader.getController();
        controller.setPlayTime(playtime);
    }


    public void backToMainWindow() {
        App.loadFXML("/fxml/main_window.fxml");

    }

    public void newPlayTime() {
        FXMLLoader fxmlLoader = App.loadFXML(("/fxml/playtime/playtime_add_edit.fxml"));
        PlayTimeAddEditController controller = fxmlLoader.getController();
        controller.setPlayTime(new PlayTime());

    }
}
