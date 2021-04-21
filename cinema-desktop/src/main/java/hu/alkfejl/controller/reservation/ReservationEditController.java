package hu.alkfejl.controller.reservation;

import hu.alkfejl.App;
import hu.alkfejl.controller.Utils;
import hu.alkfejl.dao.implementation.*;
import hu.alkfejl.dao.interfaces.*;
import hu.alkfejl.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationEditController implements Initializable {
    @FXML
    private GridPane gridPane;

    @FXML
    private CheckBox discountCheck;

    @FXML
    private Label priceLabel;

    PlayTimeDAO playtimedao = PlayTimeDAOImpl.getInstance();
    //  SeatDAO seatDAO = SeatDAOImpl.getInstance();
    RoomDAO roomDAO = RoomDAOImpl.getInstance();
    TicketDAO ticketDAO = TicketDAOImpl.getInstance();
    ReservationDAO reservationDAO = ReservationDAOImpl.getInstance();
    SeatDAOImpl seatDao = SeatDAOImpl.getInstance();
    //  MovieDAO moviedao = MovieDAOImpl.getInstance();
    //  UserDAO userDao = UserDAOImpl.getInstance();

    private List<Reservation> reservationList;

    private Room room;

    private Reservation res;

    private PlayTime playtime;

    private Ticket ticket;

    private List<Seat> seats;

    List<Integer> seatsList = new ArrayList<>();

    int price;

    public void setReservation(Reservation res) {

        this.res = res;
        this.playtime = playtimedao.getPlayTimeById(res.getPlaytimeId());
        this.room = roomDAO.getRoomByName(playtime.getRoomName());
        this.seats = seatDao.getPlayTimeSeats(res.getPlaytimeId());
        this.ticket = ticketDAO.getTicketByType(this.playtime.getTicketType());

        String[] splitedSeats = res.getReservedSeat().split(",");
        for (String s : splitedSeats) {
            seatsList.add(Integer.parseInt(s));
        }
        price = seatsList.size() * (this.ticket.getPrice());
        priceLabel.setText("Ár: " + price);


        int col = room.getColNumber();
        int row = room.getRowNumber();

        Button[] movieButton = new Button[seats.size()];
//        System.out.println("meret: " + movieButton.length);
//        System.out.println("col: " + col);
//        System.out.println("row: " + row);
        int rowHelper = 0;
        int colHelper = 0;
        for (int i = 0; i < seats.size(); i++) {
            String s = String.valueOf(seats.get(i).getSeatId());
            System.out.println("Stringben a székid: " + s);
            movieButton[i] = new Button(s);
            colHelper++;
            if (i % col == 0) {
                rowHelper++;
                colHelper = 0;
            }
            if (seats.get(i).getTaken() == 0) {
                movieButton[i].setStyle("-fx-background-color: #5fbc5f");
                movieButton[i].setId("green");
            } else if (seatsList.contains(seats.get(i).getSeatId())) {
                movieButton[i].setStyle("-fx-background-color: #7a7aff");
                movieButton[i].setId("blue");
            } else {
                movieButton[i].setStyle("-fx-background-color: #fd6a6a");
                movieButton[i].setId("red");
            }

            int index = i;
            movieButton[i].setOnAction(actionEvent -> {
                if (movieButton[index].getId().equals("green")) {
                    movieButton[index].setStyle("-fx-background-color: #c3be4d");
                    movieButton[index].setId("yellow");
                    seatsList.add(Integer.parseInt(movieButton[index].textProperty().getValue()));
                    priceUpdate();
                    // System.out.println("LISTA: " + seatsList);
                } else if (movieButton[index].getId().equals("yellow")) {
                    movieButton[index].setStyle("-fx-background-color: #5fbc5f");
                    movieButton[index].setId("green");
                    seatsList.remove(Integer.valueOf(Integer.parseInt(movieButton[index].textProperty().getValue())));
                    priceUpdate();
                    //System.out.println("LISTA: " + seatsList);
                    //System.out.println("liststring : " + listString);

                } else if (movieButton[index].getId().equals("blue")) {
                    movieButton[index].setStyle("-fx-background-color: #5fbc5f");
                    movieButton[index].setId("green");
                    seatsList.remove(Integer.valueOf(Integer.parseInt(movieButton[index].textProperty().getValue())));
                    priceUpdate();
                } else if (movieButton[index].getId().equals("red")) {
                    //System.out.println("ez foglalt");
                }

            });
            gridPane.add(movieButton[i], colHelper, rowHelper);
        }

        //Ár számítás
        discountCheck.setOnAction(actionEvent -> {
            priceUpdate();
        });

    }

    private String toList(List<Integer> intlist) {
        String listString = "";
        for (int i = 0; i < intlist.size(); i++) {
            if (i == intlist.size() - 1) {
                listString += intlist.get(i);
            } else {
                listString += intlist.get(i) + ",";
            }
        }
        return listString;
    }

    public void priceUpdate() {
        if (discountCheck.isSelected()) {
            price = seatsList.size() * (this.ticket.getLowerPrice());

        } else {
            price = seatsList.size() * (this.ticket.getPrice());
        }
        priceLabel.setText("Ár: " + price);
        //System.out.println("Price: " + price);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    public void onCancel(ActionEvent actionEvent) {
        App.loadFXML("/fxml/reservation/reservation_window.fxml");

    }

    public void onSave(ActionEvent actionEvent) {
        String[] old = this.res.getReservedSeat().split(",");
        for (String string : old) {
            // System.out.println("Splitted seat acc:  " + integer);
            seatDao.updateOnDelete(this.res.getPlaytimeId(), Integer.parseInt(string));
        }
        reservationDAO.deleteReservationByUser(this.res.getEmail(), this.res.getPlaytimeId());

        for (Integer integer : seatsList) {
            seatDao.reserve(this.res.getPlaytimeId(), integer);
        }

        this.res.setReservedSeat(toList(seatsList));
        this.res.setPriceSum(price);
        reservationDAO.save(this.res);
        Utils.showInfo("Sikeres módosítás!");
        App.loadFXML("/fxml/reservation/reservation_window.fxml");

    }


}
