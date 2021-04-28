package hu.alkfejl.controller.reservation;

import hu.alkfejl.App;
import hu.alkfejl.controller.Utils;
import hu.alkfejl.dao.implementation.ReservationDAOImpl;
import hu.alkfejl.dao.implementation.SeatDAOImpl;
import hu.alkfejl.dao.interfaces.ReservationDAO;
import hu.alkfejl.model.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class ReservationWindowController implements Initializable {

    ReservationDAO reservationDao = ReservationDAOImpl.getInstance();
    SeatDAOImpl seatDao = SeatDAOImpl.getInstance();

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

    @FXML
    private TextField searchText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTable();

        reserve_table.getItems().setAll(reservationDao.listReservations());
        playtimeid_col.setCellValueFactory(new PropertyValueFactory<>("playtimeId"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        movie_col.setCellValueFactory(new PropertyValueFactory<>("movieName"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("priceSum"));
        seats_col.setCellValueFactory(new PropertyValueFactory<>("reservedSeat"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("playtimeDate"));

        actions_col.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Törlés");
            private final Button editButton = new Button("Módosítás");

            {
                deleteButton.setOnAction(event -> {
                    Reservation res = getTableRow().getItem();
                    deleteReservation(res);// törlés
                    refreshTable(); // táblafrissites
                });

                editButton.setOnAction(event -> {
                    Reservation res = getTableRow().getItem();
                    editReservation(res);
                    refreshTable();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    VBox container = new VBox();
                    container.setAlignment(Pos.CENTER);
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
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Biztosan törlöd a foglalást?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(ButtonType.YES)) {
                reservationDao.deleteReservationByUser(res.getEmail(), res.getPlaytimeId());
                String[] old = res.getReservedSeat().split(",");
                for (String string : old) {
                    seatDao.updateOnDelete(res.getPlaytimeId(), Integer.parseInt(string));
                }
                Utils.showInfo("Sikeres törlés!");

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


    public void onSearch(KeyEvent keyEvent) {
        List<Reservation> filtered = reservationList.stream().filter(reservation -> (reservation.getEmail().toLowerCase().contains(searchText.getText().toLowerCase()))
                || (reservation.getMovieName().toLowerCase().contains(searchText.getText().toLowerCase()))
        ).collect(Collectors.toList());
        reserve_table.getItems().setAll(filtered);
    }
}
