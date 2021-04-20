package hu.alkfejl.controller.reservation;

import hu.alkfejl.App;
import hu.alkfejl.controller.movie.MovieEditController;
import hu.alkfejl.dao.implementation.*;
import hu.alkfejl.dao.interfaces.*;
import hu.alkfejl.model.PlayTime;
import hu.alkfejl.model.Reservation;
import hu.alkfejl.model.Room;
import hu.alkfejl.model.Seat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class ReservationEditController implements Initializable {
    @FXML
    private GridPane gridPane;

    PlayTimeDAO playtimedao = PlayTimeDAOImpl.getInstance();
    //  SeatDAO seatDAO = SeatDAOImpl.getInstance();
    RoomDAO roomDAO = RoomDAOImpl.getInstance();
    //TicketDAO ticketDAO = TicketDAOImpl.getInstance();
    ReservationDAO reservationDAO = ReservationDAOImpl.getInstance();
    SeatDAOImpl seatDao = SeatDAOImpl.getInstance();
    //  MovieDAO moviedao = MovieDAOImpl.getInstance();
    //  UserDAO userDao = UserDAOImpl.getInstance();
    private Room room;

    private Reservation res;

    private PlayTime playtime;

    private List<Seat> seats;

    List<Integer> seatsList = new ArrayList<>();

    public void setReservation(Reservation res) {

        this.res = res;
        this.playtime = playtimedao.getPlayTimeById(res.getPlaytime_id());
        this.room = roomDAO.getRoomByName(playtime.getRoom_name());
        this.seats = seatDao.getPlayTimeSeats(res.getPlaytime_id());

        String[] splitedSeats = res.getReserved_seat().split(",");
        int[] intSeats = Stream.of(splitedSeats).mapToInt(Integer::parseInt).toArray();
        for (String s : splitedSeats) {
            seatsList.add(Integer.parseInt(s));
        }


        int col = room.getColNumber();
        int row = room.getRowNumber();

        Button[] movieButton = new Button[seats.size()];
//        System.out.println("meret: " + movieButton.length);
//        System.out.println("col: " + col);
//        System.out.println("row: " + row);
        int rowHelper = 0;
        int colHelper = 0;
        for (int i = 0; i < seats.size(); i++) {
            String s = String.valueOf(seats.get(i).getSeat_id());
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
            } else if (seatsList.contains(seats.get(i).getSeat_id())) {
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
                    System.out.println("LISTA: " + seatsList);
                } else if (movieButton[index].getId().equals("yellow")) {
                    movieButton[index].setStyle("-fx-background-color: #5fbc5f");
                    movieButton[index].setId("green");
                    seatsList.remove(Integer.valueOf(Integer.parseInt(movieButton[index].textProperty().getValue())));
                    System.out.println("LISTA: " + seatsList);
                    //System.out.println("liststring : " + listString);

                } else if (movieButton[index].getId().equals("blue")) {
                    movieButton[index].setStyle("-fx-background-color: #5fbc5f");
                    movieButton[index].setId("green");
                    seatsList.remove(Integer.valueOf(Integer.parseInt(movieButton[index].textProperty().getValue())));
                } else if (movieButton[index].getId().equals("red")) {
                    System.out.println("ez foglalt");
                }

            });

            gridPane.add(movieButton[i], colHelper, rowHelper);


        }

     //   this.res.setReserved_seat(listString);


        //System.out.println("res adatok: " + res.getEmail() + ", seats: "  + res.getReserved_seat());
        // System.out.println("res col: " + room.getColNumber());
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //int col = room.getColNumber();
        //int row = room.getRowNumber();
        //  System.out.println("col: " + room.getColNumber());
        //   System.out.println("row: " + row);
        /*

        Button[] movieButton = new Button[col * row];
        System.out.println("meret: " + movieButton.length);
        System.out.println("col: " + col);
        System.out.println("row: " + row);
*/
    /*    for (int i = 0; i < seats.size(); i++) {
            String s = String.valueOf(seats.get(i).getSeat_id());
            System.out.println("Stringben a székid: " + s);
            // movieButton[i] = new Button(s);
        }*/
    }


    public void onCancel(ActionEvent actionEvent) {
        App.loadFXML("/fxml/reservation/reservation_window.fxml");

    }

    public void onSave(ActionEvent actionEvent) {
        String[] old = this.res.getReserved_seat().split(",");
        for (String string : old) {
           // System.out.println("Splitted seat acc:  " + integer);
            seatDao.updateOnDelete(this.res.getPlaytime_id(), Integer.parseInt(string));
        }
        reservationDAO.deleteReservationByUser(this.res.getEmail(), this.res.getPlaytime_id());

        for (Integer integer : seatsList) {
            seatDao.reserve(this.res.getPlaytime_id(), integer);
            // System.out.println("ezaz: " + splitedSeats[i]);
        }

        this.res.setReserved_seat(toList(seatsList));
        reservationDAO.save(this.res);
        System.out.println();
    }
}
