package hu.alkfejl.controller.reservation;

import hu.alkfejl.App;
import hu.alkfejl.controller.Utils;
import hu.alkfejl.controller.room.RoomAddEditController;
import hu.alkfejl.dao.implementation.PlayTimeDAOImpl;
import hu.alkfejl.dao.implementation.ReservationDAOImpl;
import hu.alkfejl.dao.interfaces.PlayTimeDAO;
import hu.alkfejl.dao.interfaces.ReservationDAO;
import hu.alkfejl.model.Reservation;
import hu.alkfejl.model.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class ReservationWindowController implements Initializable {

    ReservationDAO reservationDao = ReservationDAOImpl.getInstance();
    private List<Reservation> reservationList;

    @FXML
    private TableView<Reservation> reserve_table;

    @FXML
    private TableColumn<Reservation, Integer> playtimeid_col;

    @FXML
    private TableColumn<Reservation, String> email_col;

    @FXML
    private TableColumn<Reservation, String> movie_col;

    @FXML
    private TableColumn<Reservation, Integer> price_col;

    @FXML
    private TableColumn<Reservation, String> seats_col;

    @FXML
    private TableColumn<Reservation, String> date_col;

    @FXML
    private TableColumn<Reservation, Void> actions_col;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTable();

        reserve_table.getItems().setAll(reservationDao.listReservations());
        playtimeid_col.setCellValueFactory(new PropertyValueFactory<>("playtime_id"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        movie_col.setCellValueFactory(new PropertyValueFactory<>("movie_name"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("price_sum"));
        seats_col.setCellValueFactory(new PropertyValueFactory<>("reserved_seat"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("playtimedate"));

        actions_col.setCellFactory(param -> new TableCell<>(){
            private final Button deleteButton = new Button("Törlés");
            private final Button editButton = new Button("Módosítás");

            {
                deleteButton.setOnAction(event -> {
                    Reservation res = getTableRow().getItem();
                    deleteReservation(res);// törlés
                    refreshTable(); // táblafrissites
                });

                editButton.setOnAction(event ->{
                    Reservation res = getTableRow().getItem();
                    editReservation(res);
                    refreshTable();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setGraphic(null);
                }else{
                    VBox container = new VBox();
                    container.getChildren().addAll(editButton, deleteButton);
                    container.setSpacing(10.0);
                    setGraphic(container);
                }
            }
        });


    }

    private void editReservation(Reservation res) {
        FXMLLoader fxmlLoader = App.loadFXML(("/fxml/reservation/reservation_edit.fxml"));
        ReservationEditController controller = fxmlLoader.getController();
        controller.setReservation(res);
    }

    private void deleteReservation(Reservation res) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,"Biztosan törlöd a foglalást?", ButtonType.YES, ButtonType.NO);
            confirm.showAndWait().ifPresent(buttonType -> {
                if(buttonType.equals(ButtonType.YES)){
                    if(timeCheck(res.getPlaytimedate())){
                        reservationDao.deleteReservationByUser(res.getEmail(), res.getPlaytime_id());
                        Utils.showInfo("Sikeres törlés!");
                    }
                    else{
                        Utils.showWarning("Nem törölhető, csak 24 órával a vetítés előtt!");
                    }
                 //   roomDAO.delete(room);
                }
            });
    }


    public void backToMainWindow(ActionEvent actionEvent) {
        App.loadFXML("/fxml/main_window.fxml");

    }
    private void refreshTable() {
        reservationList = reservationDao.listReservations();
        reserve_table.getItems().setAll(reservationList);
    }

    public boolean timeCheck(String playTime_time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        String currentDate = formatter.format(date);

        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m");

        LocalDateTime dateTime1 = LocalDateTime.parse(playTime_time, dateformatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(currentDate, dateformatter);
        // System.out.println("dateTime1 " + dateTime1);
        //System.out.println("dateTime2 " + dateTime2);

        long diffInMinutes = Math.abs(java.time.Duration.between(dateTime1, dateTime2).toMinutes());
        // System.out.println("percek hátra: " + diffInMinutes);

        if (diffInMinutes >= 1440) {
            return true;
        } else {
            return false;
        }

    }
}
